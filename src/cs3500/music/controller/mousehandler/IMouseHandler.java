package cs3500.music.controller.mousehandler;

import cs3500.music.model.event.note.INote;
import cs3500.music.view.playable.IGuiView;

/**
 * A MouseHandler Interface.
 */
public interface IMouseHandler {


  /**
   * Assign a Runnable Function that should be run when the given note is clicked upon.
   *
   * @param note     the note that must be clicked to run the runnables
   * @param runnable the runnable to be run.
   */
  void takeMouseFunction(INote note, Runnable runnable);

  /**
   * Takes the view such that the Mouse Handler can determine whether a note was clicked on.
   *
   * @param view the GUI View.
   */
  void takeView(IGuiView view);

  /**
   * Takes a function that should be run if the mouse was clicked but no note was hit.
   *
   * @param runnable the runnable that should be run.
   */
  void takeMissedTargetFunction(Runnable runnable);
}
