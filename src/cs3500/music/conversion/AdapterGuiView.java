package cs3500.music.conversion;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.INoteFactory;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.provider.view.ICombinedView;
import cs3500.music.view.playable.IGuiView;

/**
 * To adapt the GUI view implementation of the view provider.
 */
public class AdapterGuiView extends AdapterView implements IGuiView {


  private ICombinedView aView;

  public AdapterGuiView(ICombinedView aView, IPitchFactory pitchFactory) {
    super(aView, pitchFactory);
    this.aView = aView;
  }



  @Override
  public void setPlaybackPosition(int timecode) {
    aView.jumpToPos(timecode);
  }

  /**
   * Starts playback at timecode 0.
   */
  @Override
  public void startPlayback() {
    aView.start();
  }

  /**
   * Pauses playback at current timecode.
   */
  @Override
  public void pausePlayback() {
    aView.reset();
  }

  /**
   * Resumes playback at current timecode.
   */
  @Override
  public void resumePlayback() {
    aView.start();
  }

  @Override
  public void refresh() {
    super.refresh();
    this.aView.reset();
  }

  /**
   * Take the keyboard listener to be called on key events.
   *
   * @param listener Key Listener.
   */
  @Override
  public void takeKeyboardListener(KeyListener listener) {
    aView.addKeyListener(listener);
  }

  /**
   * Takes a mouse Listener.
   *
   * @param listener mouse listener to call on mouse events.
   */
  @Override
  public void takeMouseListener(MouseListener listener) {
    aView.addMouseListener(listener);
  }

  /**
   * Get the note object drawn at the given coordinates.
   *
   * @param x x coordinate.
   * @param y y coordinate.
   * @return the note at the coordinates or null if none exists.
   */
  @Override
  public INote getNoteAt(int x, int y) {
    cs3500.music.provider.model.Note note = aView.getNoteAt(x, y);
    if (note == null) {
      return null;
    }
    return new AdapterNote(note, pitchFactory);
  }

  /**
   * Selects the given note visually.
   *
   * @param note the note to select.
   */
  @Override
  public void select(INote note) {
    //No Such Functionality
  }

  /**
   * To get a Note representation of the note the use created in the displayed Note Editor.
   *
   * @param noteFactory the Note Factory to produce the Note.
   * @return an INote with the given data.
   */
  @Override
  public INote getCreatorNote(INoteFactory noteFactory) {
    INote note = new AdapterNote(new cs3500.music.provider.model.Note(
            aView.getSelectedPitch(),
            aView.getSelectedOctave(),
            aView.getCurrentDuration(),
            aView.getCurrentStartBeat(),
            aView.getCurrentInstrument(),
            64), pitchFactory);
    System.out.println(note.toString());
    return note;
  }

  /**
   * Add a Runnable that should be run when the note creator form's button is pressed.
   */
  @Override
  public void addNoteCreatorListener(ActionListener listener) {
    this.aView.addActionListener(listener);
  }

  /**
   * To Display an error message to the user.
   */
  @Override
  public void displayErrorMessage(String message) {
    //NO Such function.
  }

  /**
   * To ReFocus the composition window. Such that Mouse & Key Listeners will fire key events.
   */
  @Override
  public void focus() {
    //No such function
  }

  /**
   * Scroll the window horizontally by the given amount.
   */
  @Override
  public void scrollHorizontal(int amount) {
    for (int i = 0; i < this.viewComposition.getMeasure(); i++) {
      if (amount < 0) {
        aView.scrollLeft();
        continue;
      }
      aView.scrollRight();
    }
  }
}
