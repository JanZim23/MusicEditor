Music Editor implementation by Jan Zimmermann & Christian Kreiling



Interfaces:
- IViewComposition

- INote
- INoteFactory
- IPitch
- IPitchFactory

- IView
- IGuiView
- IPlayableView



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

IView is an interface that unites all the views possible with our Program. It allows for any form of 
representation of our Model's data.

IPlayableView is an interface that extends IView, but adds methods that support synchronized
playback across multiple views of a single composition.

IGuiView extends IPlayableView as the abstraction of methods shared by any graphical view. It is
implemented by both the GuiAudioView and GuiView classes.

Classes:
- PitchQuality (Enum)

- GuiAudioView
- GuiView
- GuiPanel
- MidiView
- ConsoleView

PitchQuality is a fixed enum that represents all kinds of Pitch Quality from C to B. Contains helper
methods that translate the qualitz into different formats such as pitch integer, name, and enum.


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



--------------------------------- CHANGES ----------------------------------------

Moved to IPitchFactory and INoteFactory to loosen coupling of Model and View

