package cs3500.music.view.playable;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import cs3500.music.model.composition.IViewComposition;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.INoteFactory;

/**
 * Class representing the interactive view of the MusicEditor.
 */
public class GuiAudioView extends AbstractPlayableView implements IGuiView {


  /**
   * Represents the GUI.
   */
  private GuiView gui;
  /**
   * Represents Midi.
   */
  private MidiView midi;


  /**
   * To Construct a GuiAudio view using a Guiview and a MidiView.
   *
   * @param gui  the guiview to use.
   * @param midi the misi view to use.
   */
  public GuiAudioView(GuiView gui, MidiView midi) {
    this.gui = gui;
    this.midi = midi;
    this.paused = true;
  }


  @Override
  public void takeKeyboardListener(KeyListener listener) {
    this.gui.takeKeyboardListener(listener);
  }

  /**
   * Takes a mouse handler.
   *
   * @param listener mouse handler to call on mouse events.
   */
  @Override
  public void takeMouseListener(MouseListener listener) {
    this.gui.takeMouseListener(listener);
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
    return this.gui.getNoteAt(x, y);
  }

  /**
   * Selects the given note visually.
   *
   * @param note the note to select.
   */
  @Override
  public void select(INote note) {
    this.gui.select(note);
  }

  /**
   * INote representative of the GUI Note Creator Note.
   *
   * @return an INote with the attributes of Note Creator
   */
  @Override
  public INote getCreatorNote(INoteFactory noteFactory) {
    return this.gui.getCreatorNote(noteFactory);
  }

  @Override
  public void addNoteCreatorListener(ActionListener listener) {
    this.gui.addNoteCreatorListener(listener);
  }

  @Override
  public void displayErrorMessage(String message) {
    this.gui.displayErrorMessage(message);
  }

  @Override
  public void focus() {
    this.gui.focus();
  }

  @Override
  public void scrollHorizontal(int amount) {
    this.gui.scrollHorizontal(amount);
  }

  @Override
  public void showDisplay() {
    takeCurrentMicros(currentMicros);
    gui.showDisplay();
    midi.showDisplay();

  }



  /**
   * To take the ViewComposition.
   */
  @Override
  public void takeViewComposition(IViewComposition viewComposition) {
    this.viewComposition = viewComposition;
    this.gui.takeViewComposition(viewComposition);
    this.midi.takeViewComposition(viewComposition);
  }

  /**
   * Refreshed the View to display current Data.
   */
  @Override
  public void refresh() {
    this.gui.refresh();
  }


  @Override
  public void setPlaybackPosition(int timecode) {
    this.takeCurrentMicros(timecode * this.viewComposition.getTempo());
    this.gui.setPlaybackPosition(timecode);
    this.midi.setPlaybackPosition(timecode);
  }

  @Override
  protected void takeCurrentMicros(long micros) {
    this.gui.takeCurrentMicros(micros);
    this.midi.takeCurrentMicros(micros);
    this.currentMicros = micros;
  }

  /**
   * Starts playback at timecode 0.
   */
  @Override
  public void startPlayback() {
    this.paused = false;
    this.timer.cancel();
    this.setPlaybackPosition(0);
    this.startTicks();
    this.gui.startPlayback();
    this.midi.startPlayback();
  }

  /**
   * Pauses playback at current timecode.
   */
  @Override
  public void pausePlayback() {
    if (this.paused) {
      return;
    }
    this.paused = true;
    this.timer.cancel();
    this.gui.pausePlayback();
    this.midi.pausePlayback();
  }

  /**
   * Resumes playback at current timecode.
   */
  @Override
  public void resumePlayback() {
    if (!this.paused) {
      return;
    }
    this.paused = false;
    this.startTicks();
    this.gui.resumePlayback();
    this.midi.resumePlayback();
  }
}
