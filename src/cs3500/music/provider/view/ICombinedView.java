package cs3500.music.provider.view;


import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import cs3500.music.provider.model.Note;
import cs3500.music.provider.model.Pitch;

public interface ICombinedView extends IMusicEditorView {

  /**
   * Moves this combined view to the given position.
   *
   * @param pos the position to move this combined view to
   */
  void jumpToPos(int pos);

  /**
   * Scrolls this combined view to the right.
   */
  void scrollRight();

  /**
   * Scrolls this combined view to the left.
   */
  void scrollLeft();

  /**
   * Starts the playing of this piece.
   */
  void start();

  /**
   * Resets the piece of music.
   */
  void reset();

  /**
   * Adds a key listener to the visual part of this combined view.
   *
   * @param listener the keylistener to add
   */
  void addKeyListener(KeyListener listener);

  /**
   * Adds a mouse listener to the visual part of the combined view.
   *
   * @param listener the mouselistener to add.
   */
  void addMouseListener(MouseListener listener);

  /**
   * Returns the currently selected pitch from the pitch combobox.
   *
   * @return the currently selected pitch
   */
  Pitch getSelectedPitch();

  /**
   * Returns the currently selected octave from the octave combobox.
   *
   * @return the currently selected octave
   */
  int getSelectedOctave();

  /**
   * Gets the duration from the text field as an integer.
   *
   * @return the currently entered number for the duration
   */
  int getCurrentDuration();

  /**
   * Obtains the currently selected instrument from the combobox.
   *
   * @return the int representation of the currently selected instrument
   */
  int getCurrentInstrument();

  /**
   * Obtains the start beat from the text field.
   *
   * @return the currently entered number for a starting beat
   */
  int getCurrentStartBeat();

  /**
   * Adds an action listener to the button in the visual view.
   *
   * @param listener the actionlistener
   */
  void addActionListener(ActionListener listener);

  /**
   * Gets the note given at the x y positions of the mouse click.
   *
   * @param x x position of the click.
   * @param y y position of the click.
   * @return Note represented at this position.
   */
  Note getNoteAt(int x, int y);

}
