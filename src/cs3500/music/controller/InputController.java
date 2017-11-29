package cs3500.music.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs3500.music.controller.keyboardhandler.KeyboardHandler;
import cs3500.music.controller.mousehandler.MouseHandler;
import cs3500.music.model.composition.IComposition;
import cs3500.music.model.composition.ViewComposition;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.NoteFactoryImpl;
import cs3500.music.view.playable.IGuiView;

/**
 * A Controller that can take keyboard input from the user.
 */
public class InputController implements IController {

  /**
   * The referenced keyboard handler.
   */
  private KeyboardHandler keyboardHandler = new KeyboardHandler();

  /**
   * The mouse handler to handle mouse events.
   */
  private MouseHandler mouseHandler = new MouseHandler();

  /**
   * The model to operate on.
   */
  private IComposition model;

  /**
   * The View to display and relay data to.
   */
  private IGuiView view;

  /**
   * The view composition to allow read only access to the model.
   */
  private ViewComposition viewComposition;

  /**
   * To keep track of the currently selected note.
   */
  private INote selected;

  /**
   * To Construct an Input Controller Using a model and IGUIView.
   *
   * @param model the ICompoition model.
   * @param view  the view to display data
   */
  public InputController(IComposition model, IGuiView view) {
    this.model = model;
    this.view = view;
    this.viewComposition = new ViewComposition(model);
  }


  /**
   * Run the Model and View.
   */
  @Override
  public void run() {
    view.takeViewComposition(this.viewComposition);
    this.view.takeKeyboardListener(this.keyboardHandler);
    this.view.takeMouseListener(mouseHandler);
    this.mouseHandler.takeView(this.view);
    createMouseRunnables();
    createKeyboardRunnables();
    createNoteCreatorRunnable();
    this.view.showDisplay();
  }

  /**
   * Generates the MouseRunnables that are designed to take in Mouse actions.
   */
  private void createMouseRunnables() {
    for (INote note : this.viewComposition.getNotes()) {
      this.mouseHandler.takeMouseFunction(note, new Runnable() { //Runnable is appropriate here
        @Override
        public void run() {
          selectNote(note);
        }
      });
    }
    this.mouseHandler.takeMissedTargetFunction(new Runnable() {
      @Override
      public void run() {
        selectNote(null);
      }
    });
  }

  /**
   * Generates the NoteCreator that will be referenced whenever a user wants to add a note.
   */
  private void createNoteCreatorRunnable() {
    this.view.addNoteCreatorListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          model.addNote(view.getCreatorNote(new NoteFactoryImpl()));
        } catch (Exception ex) {
          view.displayErrorMessage(ex.getMessage());
        }
        view.refresh();
        createMouseRunnables();
      }
    });
  }

  /**
   * <p>Generates the KeyboardRunnables that are designed to take in different Keyboard actions.</p>
   * Key:
   * <ul>
   * <li>Delete: Deletes a note.</li>
   * <li>p: Pauses what is playing.</li>
   * <li>r: Resumes what is playing.</li>
   * <li>s: Starts playing the composition from the first beat.</li>
   * </ul>
   */
  private void createKeyboardRunnables() {
    this.keyboardHandler.takeKeyboardFunction(8, new Runnable() {
      @Override
      public void run() {
        model.deleteNote(selected);
        view.refresh();
      }
    });
    this.keyboardHandler.takeKeyboardFunction(83, new Runnable() {
      @Override
      public void run() {
        view.startPlayback();
        view.refresh();
      }
    });
    this.keyboardHandler.takeKeyboardFunction(80, new Runnable() {
      @Override
      public void run() {
        view.pausePlayback();
        view.refresh();
      }
    });
    this.keyboardHandler.takeKeyboardFunction(82, new Runnable() {
      @Override
      public void run() {
        view.resumePlayback();
        view.refresh();
      }
    });
    this.keyboardHandler.takeKeyboardFunction(37, new Runnable() {
      @Override
      public void run() {
        view.scrollHorizontal(-viewComposition.getMeasure());
      }
    });
    this.keyboardHandler.takeKeyboardFunction(39, new Runnable() {
      @Override
      public void run() {
        view.scrollHorizontal(viewComposition.getMeasure());
      }
    });
    this.keyboardHandler.takeKeyboardFunction(36, new Runnable() {
      @Override
      public void run() {
        view.pausePlayback();
        view.setPlaybackPosition(0);
      }
    });
    this.keyboardHandler.takeKeyboardFunction(35, new Runnable() {
      @Override
      public void run() {
        view.pausePlayback();
        view.setPlaybackPosition(viewComposition.getLength() * viewComposition.getTempo());
      }
    });
  }

  /*
  Key:
  Delete: 8
  Space: 32
  p: 80
  r:82
  s:83
   */

  /**
   * Selects the given note, saving it as the selected note in order to be referenced later by the
   * controller (if the user chooses).
   */
  private void selectNote(INote note) {
    this.selected = note;
    this.view.select(note);
    this.view.refresh();
  }


}
