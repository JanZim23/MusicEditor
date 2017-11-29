package cs3500.music.controller.mousehandler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

import cs3500.music.model.event.note.INote;
import cs3500.music.view.playable.IGuiView;

/**
 * MouseHandler implementation to keep track of a Map of runnables to be run on Mouse Input.
 */
public class MouseHandler implements MouseListener, IMouseHandler {

  /**
   * HashMap of functions associated with notes.
   */
  private HashMap<INote, Runnable> functions = new HashMap<>();

  /**
   * To keep track of the view.
   */
  private IGuiView view;

  /**
   * To keep track of a function that should be run if no note was hit.
   */
  private Runnable missedTarget;

  /**
   * Invoked when the mouse button has been clicked (pressed
   * and released) on a component.
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    System.out.println(e.getX() + "-" + e.getY());

    view.focus();

    INote noteAt = this.view.getNoteAt(e.getX(), e.getY());

    if (noteAt != null) {

      for (INote n : this.functions.keySet()) {
        if (noteAt.equals(n)) {
          this.functions.get(n).run();
          return;
        }
      }

      if (this.functions.containsKey(noteAt)) {
        System.out.println(noteAt.toString());
        this.functions.get(noteAt).run();
        return;
      } else {
        System.out.println("Note " + noteAt.toString() + " doesnt have a function.");
      }
    } else {
      missedTarget.run();
    }
  }

  /**
   * Invoked when a mouse button has been pressed on a component.
   */
  @Override
  public void mousePressed(MouseEvent e) {
    //Nothing should happen here.
  }

  /**
   * Invoked when a mouse button has been released on a component.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    //Nothing should happen here.
  }

  /**
   * Invoked when the mouse enters a component.
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    //Nothing should happen here.
  }

  /**
   * Invoked when the mouse exits a component.
   */
  @Override
  public void mouseExited(MouseEvent e) {
    //Nothing should happen here.
  }

  /**
   * To take a runnbale that should be run when the given note is pressed.
   *
   * @param note     the note that must be clicked to run the runnables
   * @param runnable the runnable to be run.
   */
  @Override
  public void takeMouseFunction(INote note, Runnable runnable) {
    this.functions.put(note, runnable);
  }

  /**
   * A Funciton to take the view.
   *
   * @param view the GUI View.
   */
  @Override
  public void takeView(IGuiView view) {
    this.view = view;
  }

  /**
   * To take the missed target function.
   *
   * @param runnable the runnable that should be run.
   */
  @Override
  public void takeMissedTargetFunction(Runnable runnable) {
    this.missedTarget = runnable;
  }


}
