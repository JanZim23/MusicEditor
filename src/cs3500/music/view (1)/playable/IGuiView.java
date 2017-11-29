package cs3500.music.view.playable;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.INoteFactory;

/**
 * To represent an interface for a GUI view.
 */
public interface IGuiView extends IPlayableView {

  /**
   * Take the keyboard listener to be called on key events.
   *
   * @param listener Key Listener.
   */
  void takeKeyboardListener(KeyListener listener);

  /**
   * Takes a mouse Listener.
   *
   * @param listener mouse listener to call on mouse events.
   */
  void takeMouseListener(MouseListener listener);

  /**
   * Get the note object drawn at the given coordinates.
   *
   * @param x x coordinate.
   * @param y y coordinate.
   * @return the note at the coordinates or null if none exists.
   */
  INote getNoteAt(int x, int y);

  /**
   * Selects the given note visually.
   *
   * @param note the note to select.
   */
  void select(INote note);

  /**
   * To get a Note representation of the note the use created in the displayed Note Editor.
   *
   * @param noteFactory the implementation of a Note Factory to produce the Note.
   * @return an INote with the given data.
   */
  INote getCreatorNote(INoteFactory noteFactory);

  /**
   * Add a Runnable that should be run when the note creator's form's button is pressed.
   */
  void addNoteCreatorListener(ActionListener listener);

  /**
   * To Display an error message to the user. This will open in a pop-up
   */
  void displayErrorMessage(String message);

  /**
   * To ReFocus the composition window. Such that Mouse & Key Listeners will fire key events.
   * Calling this functions will remove focus from input-fields in the View.
   */
  void focus();

  /**
   * Scroll the window horizontally by the given amount of beats.
   * HINT: Use negative numbers to scroll left.
   */
  void scrollHorizontal(int amount);

}
