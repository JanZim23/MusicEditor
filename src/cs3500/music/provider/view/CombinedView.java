package cs3500.music.provider.view;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.midi.InvalidMidiDataException;

import cs3500.music.provider.model.Note;
import cs3500.music.provider.model.Pitch;

public class CombinedView implements ICombinedView {

  /**
   * The visual and midi views to combine.
   */
  private VisualMusicEditorView visual;
  private MidiMusicEditorView midi;

  /**
   * The ViewModel to use for obtaining information for this view.
   */
  private ViewModel theModel;

  /**
   * Is this view currently paused? If true, yes, if false, no.
   */
  private boolean paused;

  /**
   * The current beat of this view.
   */
  private int curBeat;

  /**
   * A timer to keep track of pausing and playing the notes.
   */
  private Timer theTimer;

  private PlayingTimer theTask;

  /**
   * Indicates whether the piece has been started yet.
   */
  private boolean hasBeenStarted;

  /**
   * Constructor for a combined view.
   *
   * @param visual the visual view to take in
   * @param midi   the midi view to take in
   */
  public CombinedView(VisualMusicEditorView visual, MidiMusicEditorView midi) {
    this.visual = visual;
    this.midi = midi;
    this.curBeat = 0;
    this.paused = true;
    this.theTimer = new Timer();
    this.theTask = new PlayingTimer();
    this.hasBeenStarted = false;
  }

  /**
   * Displays the given model in whatever way is designated by the concrete implementation of this
   * interface.
   */
  @Override
  public void display() {
    this.visual.display();
  }

  /**
   * Gets the information from the piece of music to use in this view.
   *
   * @param viewModel the viewModel to use for information
   */
  @Override
  public void accessNotes(ViewModel viewModel) {
    this.visual.accessNotes(viewModel);
    this.midi.accessNotes(viewModel);
    this.theModel = viewModel;
  }

  /**
   * Moves this combined view to the given position.
   *
   * @param pos the position to move this combined view to
   */
  @Override
  public void jumpToPos(int pos) {
    if (pos <= this.theModel.getPiece().lastKey() && pos >= 0 && this.paused) {
      this.visual.setCurBeat(pos);
    }
  }

  /**
   * Scrolls this combined view to the right.
   */
  @Override
  public void scrollRight() {
    if (this.paused) {
      this.visual.setCurBeat(this.visual.getCurBeat() + 1);
      this.visual.setCurLinePos(this.visual.getCurLinePos() - 1);
    }
  }

  /**
   * Scrolls this combined view to the left.
   */
  @Override
  public void scrollLeft() {
    if (this.visual.getCurBeat() != 0 && this.paused) {
      this.visual.setCurBeat(this.visual.getCurBeat() - 1);
      this.visual.setCurLinePos(this.visual.getCurLinePos() + 1);
    }
  }

  @Override
  public void reset() {
    this.jumpToPos(0);
    this.curBeat = 0;
    this.visual.setCurLinePos(0);
    this.paused = true;
    this.hasBeenStarted = false;
    theTimer.cancel();
  }

  /**
   * Adds a key listener to the visual part of this combined view.
   *
   * @param listener the KeyListener to add
   */
  @Override
  public void addKeyListener(KeyListener listener) {
    this.visual.addKeyListener(listener);
  }

  /**
   * Adds a mouse listener to this combined view
   *
   * @param listener the mouselistener to add.
   */
  @Override
  public void addMouseListener(MouseListener listener) {
    this.visual.addMouseListener(listener);
  }

  /**
   * Returns the currently selected pitch from the pitch combobox.
   *
   * @return the currently selected pitch
   */
  @Override
  public Pitch getSelectedPitch() {
    return this.visual.getSelectedPitch();
  }

  /**
   * Returns the currently selected octave from the octave combobox.
   *
   * @return the currently selected octave
   */
  @Override
  public int getSelectedOctave() {
    return this.visual.getSelectedOctave();
  }

  /**
   * Gets the duration from the text field as an integer.
   *
   * @return the currently entered number for the duration
   */
  @Override
  public int getCurrentDuration() {
    return this.visual.getCurrentDuration();
  }

  /**
   * Gets the instrument selected by the visual view's text field.
   *
   * @return the int representation of the currently selected instrument
   */
  @Override
  public int getCurrentInstrument() {
    return this.visual.getCurrentInstrument();
  }

  /**
   * Obtains the start beat from the text field.
   *
   * @return the currently entered number for a starting beat
   */
  @Override
  public int getCurrentStartBeat() {
    return this.visual.getCurrentStartBeat();
  }

  /**
   * Adds an action listener to the button in the visual view.
   *
   * @param listener the actionlistener
   */
  @Override
  public void addActionListener(ActionListener listener) {
    this.visual.addActionListener(listener);
  }

  /**
   * Plays all the notes at the current beat of this combined view.
   */
  private void playAtBeat() {
    if (this.curBeat <= this.theModel.getPiece().lastKey()) {
      if (this.curBeat % 60 == 0) {
        this.visual.setCurBeat(this.curBeat);
      }
      this.visual.setCurLinePos(curBeat % 60);
      ArrayList<Note> notesAtBeat = this.theModel.getPiece().get(this.curBeat);
      try {
        this.midi.playNotes(notesAtBeat, curBeat);
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }
      this.curBeat++;
    } else {
      this.paused = true;
    }
  }

  /**
   * Starts the playing of this piece.
   */
  public void start() {
    if (curBeat == 0 && !this.hasBeenStarted) {
      this.theTimer = new Timer();
      this.theTask = new PlayingTimer();
      this.theTimer.scheduleAtFixedRate(this.theTask, 0, this.theModel.getTempo() / 1000);
    }
    theTask.playPause();
    this.hasBeenStarted = true;
  }

  /**
   * Gets the note at the given x and y coordinates.
   *
   * @param x the x coord
   * @param y the y coord
   * @return the note at the given coordinates
   */
  @Override
  public Note getNoteAt(int x, int y) {
    return this.visual.getNoteAt(x, y, theModel);
  }

  /**
   * Gets the paused value.
   */
  public boolean getPaused() {
    return this.paused;
  }

  /**
   * A playing timer class to keep track of pausing and playing.
   */
  private class PlayingTimer extends TimerTask {

    /**
     * The action to be performed by this timer task.
     */
    @Override
    public void run() {
      if (!paused) {
        playAtBeat();
      }
    }

    /**
     * Plays or pauses this task depending on the current state.
     */
    void playPause() {
      paused = !paused;
    }
  }
}
