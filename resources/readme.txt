Music Editor implementation by Jan Zimmermann & Christian Kreiling


Usage for Running the Jar:

java -jar OOD-MusicEditor.jar [File] [View] [Package]

[File] = Relative path to the music file.
[View] = The name of the view you would like to see
           can be: midi, gui, guiaudio, console
[Package] = The package of Views you would like to use
            they can be: original, provider
            This will switch inbetween our original Views made by Christian and Jan
            or use the views that where given to us.


InputController keyboard guide:

S: start playback from the beginning
P: pause playback at current location
R: resume playback at current location
Home: Move to beginning of composition
End: Move to end of composition
Left arrow key: Move the window to the left by one measure
Right arrow key: Move the window to the right by one measure
Backspace: Deletes the selected note


Packages:

- Builder           Used for Constructing our model implementation from a file
- Controller        Contains all Controller Classes, these are the intermediary between the Model and the View
- Model             Contains the source of the Model designed by Jan and Christian
- Provider          Contains the Providers Classes
- Provider.Model    Contains the Providers Model classes
- Provider.View     Contains the Providers view classes
- View              The View made by Jan and Christian



Concepts from Christian's Model Implemented into Jan's
- Notes are Immutable, once created they cannot be changed
- Moved from 'Grid' implementation to a simple ArrayList
- Pitch can be represented as a Quality and Octave Number

Interfaces:
- IComposition
- IViewComposition

- INote
- INoteFactory
- IPitch
- IPitchFactory


- IController
- IKeyboardHandler
- IMouseHandler

- IView
- IGuiView
- IPlayableView


The IComposition Interface provides all functionality necessary to manipulate and read a composition.
The Interface does not contain functionality that is necessary to visualize the data.

The IViewComposition is an Interface that has limited access to an IComposition, it will allow read-
only access to other classes, and intended for use by objects that should not have mutating
privileges. The aforementioned IComposition Interface extends this interface to allow for backwards
compatibility.

The INote interface has the basic functionality to manipulate a single notes pitch, duration and
stating time as well as access the existing values of the fields.

INoteFactory is used by the view to create instances of an INote without beeing tightly coupled a an
implementation of it.

IPitch is an interface to represent a Pitch in a musical composition. It gives access to Note
identifiers such as Quality and Octave.

IPitchFactory is an interface to create instances of IPitch without coupling the View to our Model
There are many reasons why our view uses an IPitchFactory but its mostly because the view will have to
display Pitches that are not in the composition.

IController is an interface that unites all the Controllers. The function run() will run the
program.

IMouseHandler and IKeyboardHandler are interfaces that formalize access functions used by the
controller.


IView is an interface that unites all the views possible with our Program. It allows for any form of 
representation of our Model's data.

IPlayableView is an interface that extends IView, but adds methods that support synchronized
playback across multiple views of a single composition.

IGuiView extends IPlayableView as the abstraction of methods shared by any graphical view. It is
implemented by both the GuiAudioView and GuiView classes.

Classes:
- PitchQuality (Enum)
- Pitch
- Note
- Composition
- ViewComposition

- BasicController
- InputController
- KeyboardHandler
- MouseHandler

- GuiAudioView
- GuiView
- GuiPanel
- MidiView
- ConsoleView

--------------------------- MODEL (COMPOSITION)  ------------------------------------

PitchQuality is a fixed enum that represents all kinds of Pitch Quality from C to B. Contains helper
methods that translate the qualitz into different formats such as pitch integer, name, and enum.

Pitch is the class that deals with assigning pitch to a Note. The class contains functionality to
set and edit Octave and Quality of the Pitch, keeping track of it as a single integer starting from 0
(the lowest possible note).

Note is the class representing a single note in the composition; containing information about
start time and duration as integers and Pitch as an IPitch class.

A Composition, implementing the IComposition interface, is a class that keeps track of Notes that are 
added and removed, not allowing duplicate notes or notes that start on the same pitch before the previous 
note ends. It also determines the length of the piece by looking for the note that ends last, determines 
the highest and lowest pitch in the composition by iterating through all notes.

------------------------------ CONTROLLERS ----------------------------------------

BasicController is the implementation of IController within the context of assignment 6.

InputController is the implementation of IController within the context of assignment 7. It is
designed to take in a GuiAudioView and a Composition, and allow for manipulation of the model and
view through keyboard and mouse input within the GUI representation of the model.

--------------------------------- VIEWS ----------------------------------------

Each of the following contain an IViewComposition.

GuiView : Graphical representation of the model. Implements IGuiView, which extends IPlayableView.
GuiPanel : Helper class for creating the graphical representation using Java Swing, containing the
override of the paintComponent method.

MidiView : MIDI representation of the model.

ConsoleView : Displays the model in the console, the x-axis containing note names and the y-axis
containing the beat at which a note is played.

GuiAudioView : Contains both a GuiView and a MidiView, each of which play in sync with each
other. Designed to be used with a controller that supplies input from the keyboard and mouse.


------------------------------  CHANGES TO OUR VIEW  ----------------------------------------

We tried to make our view be as adaptable as possible by creating the classes INoteFactory and IPitchFactory.
These interfaces can be easily implemented to create INote implementation of anyone. Our Views are abstracted to use
factories instead of concrete implementations. This makes it possible for our GUIView to create an INote object with
the NoteCreator in the GUIView. This way we also have implemented a dynamic labeling technique. We figured that perhaps
the folks inheriting our view label their notes differently. Therefore our view takes no action in naming things, it
will only use the IPitchFactory given to it and use the toString() method to label all notes.

Our View is also split into more interfaces than specified in the assignment. Since the MidiView and the GUIView have
many things in common we create a IPlayableView interface, this contains all functionality to tell the view what to play
and when. With methods to pause, start and resume the composition palyback.

We have an interface IGuiView that is implemented by both the GuiAudioview (combined view) and the GuiView since they
also share a lot of functionality. (The GUIAudioView can display things just like the GuiView since it 'is' partially
the GuiView.)



--------------------------- ADAPTING PROVIDER VIEWS ----------------------------------------

Adpating the Model Classes
The views that where provided to us did not have loose coupling between the Model and the View
we therefore received an implementation of Pitch and Note with the view. As well as supplying a ViewModel
implementation that was tightly coupled to the Model Interface IMusicEditorModel.

We solved this problem by creating classes in the form: ProviderCLASSNAME to extend and overwrite the implementation.

We needed to create ProviderNode as well as ProviderViewModel to be able to supply to the View. These classes extend
their respective class and overwrite the methods in order to return data that is actually stored within our INote's
These classes are basically Adapters but the other way around: conforming to their standard instead of ours.

Changes Made to Provider View:
- Changed all methods in ViewModel from default visibility to public such that they can be overridden by an extending
class in another package.

- Added two lines of code that will refresh the VisualMusicEditorView on line 134. These changed have the effect that
when the ViewModel is passed into the view with updated data the Views will fetch this data and Repaint all components
such that it will be displayed.

- Removed final modfier from provider.Note such that it can be extended. (there was no point in having it final)

- Changed TextMusicEditorView constructor visibility to public


The View that our providers gave us was suitable for their program but lacked some abstraction and documentation
for us to be able to create a fully functional program. Since they did not include Interfaces for their notion of a Note
we could not easily create an implementation of that interface to be able to interact with their View. Therefore we had
to improvise a little, but through creating another adapter that extends their class and overwrites their method we were
able to 'fake' their notes and secretly use our implementation. This resulted in us not having to change a single line
of code in the model which we think is pretty cool.

Another thing we noticed is that many classes and their fields lack modifiers. This includes visibility; many of their
methods used the default visibility. We are discouraged to use that since it leaves things ambiguous for others,
this would have been prevented if they used interfaces since that would have forced them to make the methods public.
They also used some methods within the view package as protected which isnt an elegant solution since things should be
abstracted into interfaces and not to package visability.

Some classes where labeled final, I believe that they meant to make the provider.model.note class final such that its
fields cannot be modified: but that is not the effect the final modifier has in that context, therefore we removed it.

Our providers also didnt create an interface for the two GUI Views (VisualView and CombinedView) they both share a lot
of functionality that could have been solved by declaring public methods in an interface but they didnt do that
therefore their Combined view must call package visible functions in the gui view.

Our providers have a default visibility class NotePanel which helps draw things for the GuiView, this class doesnt
actually add much functionality to the JPanel and therefore doesnt really have a purpose. It could as well be an inner
class of the gui view so that it cannot be used by any other views.


--------------------------------- CHANGES ----------------------------------------

Moved to IPitchFactory and INoteFactory to loosen coupling of Model and View
Changed
