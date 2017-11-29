package cs3500.music.provider.view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.provider.model.Note;

/**
 * A panel that holds and paints notes.
 */
class NotePanel extends JPanel {

  private TreeMap<Integer, ArrayList<Note>> notes;

  private List<String> pitches;

  private int rows;

  private int curCol;

  private int curLinePos;

  /**
   * Constructor for the note panel.
   */
  NotePanel() {
    super();
    this.notes = new TreeMap<>();
  }

  /**
   * Draws the notes as rectangles inside this panel.
   *
   * @param g the graphics thing
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    this.drawNotes(g);
    this.paintLines(g);
    this.drawCurLine(g);
  }

  /**
   * Draws the necessary line in this view.
   *
   * @param g the graphics thing
   */
  private void paintLines(Graphics g) {
    g.setColor(Color.BLACK);
    int x1 = 0;
    int x2 = 0;

    for (int i = 0; i < VisualMusicEditorView.BEATS_ON_SCREEN / 4; i++) {
      g.drawLine(x1, 0, x2, pitches.size() * VisualMusicEditorView.MEASURE_HEIGHT);
      x1 += VisualMusicEditorView.MEASURE_WIDTH;
      x2 += VisualMusicEditorView.MEASURE_WIDTH;
    }

    int y1 = 0;
    int y2 = 0;

    for (int j = 0; j < this.rows + 1; j++) {
      g.drawLine(0, y1, this.getWidth(), y2);
      y1 += VisualMusicEditorView.MEASURE_HEIGHT;
      y2 += VisualMusicEditorView.MEASURE_HEIGHT;
    }
  }

  /**
   * Draws all of the notes in this view.
   *
   * @param g the graphics thing
   */
  private void drawNotes(Graphics g) {
    for (int i = this.curCol; i < curCol + VisualMusicEditorView.BEATS_ON_SCREEN; i++) {
      if (this.notes.get(i) != null) {
        for (Note n : this.notes.get(i)) {
          int startX = (VisualMusicEditorView.MEASURE_WIDTH / 4) *
                  ((i - this.curCol) % VisualMusicEditorView.BEATS_ON_SCREEN);
          int startY = this.pitches.indexOf(n.getPitch().toString() +
                  Integer.toString(n.getOctave())) * VisualMusicEditorView.MEASURE_HEIGHT;
          Color c;
          if (n.getStartBeat() == i) {
            c = Color.BLACK;
          } else {
            c = Color.ORANGE;
          }
          g.setColor(c);
          g.fillRect(startX, startY, VisualMusicEditorView.MEASURE_WIDTH / 4,
                  VisualMusicEditorView.MEASURE_HEIGHT);
        }
      }
    }
  }

  /**
   * Draws the red line that keeps track of the song during play.
   *
   * @param g the graphics thing
   */
  private void drawCurLine(Graphics g) {
    g.setColor(Color.red);
    g.drawLine(curLinePos * (VisualMusicEditorView.MEASURE_WIDTH / 4), 0,
            curLinePos * (VisualMusicEditorView.MEASURE_WIDTH / 4),
            pitches.size() * VisualMusicEditorView.MEASURE_HEIGHT);
  }

  /**
   * Sets this panel's map of beats to notes to the given map.
   *
   * @param notes the map of notes to set for this panel
   */
  public void setNotes(TreeMap<Integer, ArrayList<Note>> notes) {
    this.notes = notes;
  }

  /**
   * Sets this panel's pitches to the given list.
   *
   * @param pitches the list of pitches in this piece
   */
  void setPitches(List<String> pitches) {
    this.pitches = pitches;
  }

  /**
   * Sets this panel's rows to the given int.
   *
   * @param rows the number of rows in this piece
   */
  void setRows(int rows) {
    this.rows = rows;
  }

  /**
   * Sets this panel's columns to the given int.
   *
   * @param cols the number of columns in this piece
   */
  void setCols(int cols) {
    this.curCol = cols;
  }

  /**
   * Getter for the current line position.
   *
   * @return the current line position
   */
  int getCurLinePos() {
    return this.curLinePos;
  }

  /**
   * Sets this panel's current line position to the given int.
   *
   * @param curLinePos the current line position to set
   */
  void setCurLinePos(int curLinePos) {
    this.curLinePos = curLinePos;
    this.repaint();
  }
}
