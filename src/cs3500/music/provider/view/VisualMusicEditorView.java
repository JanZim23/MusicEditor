package cs3500.music.provider.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import javax.swing.*;

import cs3500.music.provider.model.Note;
import cs3500.music.provider.model.Pitch;

/**
 * Visual representation of a view that shows all the notes in a grid.
 */
public class VisualMusicEditorView extends JFrame implements IMusicEditorView {

  /**
   * Constants for sizing and drawing.
   */
  final static int MEASURE_WIDTH = 80;
  final static int MEASURE_HEIGHT = 20;
  final static int BEATS_ON_SCREEN = 60;
  /**
   * Swing components.
   */
  private NotePanel notePanel;
  private JPanel pitchPanel;
  private JPanel infoPanel;
  private JComboBox<Pitch> pitches;
  private JComboBox<Integer> octaves;
  private JComboBox<String> instruments;
  private JTextField duration;
  private JTextField startBeat;
  private JButton addNote;
  private JLabel durationPrompt;
  private JLabel startBeatPrompt;
  /**
   * Number of pitches (rows) and measures (cols) in this piece.
   */
  private int rows;
  private int curBeat;


  /**
   * Constructor for the visual music editor view.
   */
  public VisualMusicEditorView() {
    super();
    this.curBeat = 0;
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    this.pitchPanel = new JPanel();
    this.pitchPanel.setLayout(new GridLayout(0, 1));
    this.notePanel = new NotePanel();
    this.notePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    this.infoPanel = new JPanel();
    this.infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 10));
    this.pitches = new JComboBox<>(Pitch.values());
    String[] listOfInstruments = {"Grand Piano", "Electric Piano", "Acoustic Guitar",
            "Electric Guitar", "Violin", "Trumpet", "Saxophone", "Drums", "Banjo"};
    this.instruments = new JComboBox<>(listOfInstruments);
    this.instruments.setPreferredSize(new Dimension(100, 25));
    Integer[] listOfOctaves = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    this.octaves = new JComboBox<>(listOfOctaves);
    this.octaves.setPreferredSize(new Dimension(100, 25));
    this.duration = new JTextField();
    this.duration.setPreferredSize(new Dimension(150, 25));
    this.durationPrompt = new JLabel("Enter duration: ");
    this.startBeat = new JTextField();
    this.startBeat.setPreferredSize(new Dimension(150, 25));
    this.startBeatPrompt = new JLabel("Enter starting beat: ");
    this.addNote = new JButton("Add Note");
    this.addNote.setActionCommand("add note");
    this.addNote.setPreferredSize(new Dimension(100, 25));
  }

  /**
   * Displays the given model in whatever way is designated by the concrete implementation of this
   * interface.
   */
  @Override
  public void display() {
    this.infoPanel.setPreferredSize(new Dimension(MEASURE_WIDTH * (BEATS_ON_SCREEN / 4), 50));
    this.pitches.setPreferredSize(new Dimension(100, 25));
    this.infoPanel.add(this.pitches);
    this.infoPanel.add(this.octaves);
    this.infoPanel.add(this.durationPrompt);
    this.infoPanel.add(this.duration);
    this.infoPanel.add(this.startBeatPrompt);
    this.infoPanel.add(this.startBeat);
    this.infoPanel.add(this.instruments);
    this.infoPanel.add(this.addNote);
    this.setLayout(new BorderLayout());
    this.add(this.pitchPanel, BorderLayout.WEST);
    this.add(this.notePanel, BorderLayout.EAST);
    this.add(this.infoPanel, BorderLayout.SOUTH);
    this.pack();
    this.setVisible(true);
    this.setResizable(false);
    System.out.println("Note panel: " + this.notePanel.getSize());
    System.out.println("Pitch panel: " + this.pitchPanel.getSize());
    System.out.println("Button panel: " + this.infoPanel.getSize());
    System.out.println("Frame: " + this.getSize());
  }

  /**
   * Gets the information from the piece of music to us in this view.
   *
   * @param viewModel the viewModel to use for information
   */
  @Override
  public void accessNotes(ViewModel viewModel) {
    this.resetFocus();
    Note lowestNote = viewModel.getLowestNote();
    Note highestNote = viewModel.getHighestNote();
    TreeMap<Integer, ArrayList<Note>> notes = viewModel.getPiece();
    List<String> pitches = this.getPitches(lowestNote, highestNote);

    this.setRows(pitches.size());
    this.pitchPanel.removeAll();
    this.pitchPanel.revalidate();

    this.addPitches(pitches);
    this.notePanel.setNotes(notes);
    this.notePanel.setPitches(pitches);
    this.notePanel.setRows(this.rows);
    this.notePanel.setCols(this.curBeat);

    this.notePanel.setPreferredSize(new Dimension(MEASURE_WIDTH * (BEATS_ON_SCREEN / 4),
            MEASURE_HEIGHT * this.rows - MEASURE_HEIGHT / 2));

    //Added for Refreshing Purposes (Jan Zimmermann)
    this.notePanel.repaint();
    this.repaint();

  }

  /**
   * Sets the focus of this view to the frame to allow keyboard inputs.
   */
  private void resetFocus() {
    this.setFocusable(true);
    this.requestFocus();
  }

  /**
   * Sets the rows of this view using the range of pitches.
   *
   * @param rows the number of rows to set this view to.
   */
  private void setRows(int rows) {
    this.rows = rows;
  }

  /**
   * Adds the pitches to this view. Creates labels and adds them to the appropriate panels
   * in the grid.
   */
  private void addPitches(List<String> pitches) {
    for (String s : pitches) {
      JLabel pitch = new JLabel(s);
      pitch.setPreferredSize(new Dimension(25, MEASURE_HEIGHT - 10));
      this.pitchPanel.add(pitch);
    }
  }

  /**
   * Obtains the range of pitches from this view, useful in many methods.
   *
   * @param lowest  the lowest pitch note in this view
   * @param highest the highest pitch note in this view
   * @return the range of pitches as a list
   */
  private List<String> getPitches(Note lowest, Note highest) {
    List<String> pitches = new ArrayList<>();

    int curLowest = lowest.getPitch().ordinal();

    for (int i = lowest.getOctave(); i < highest.getOctave() + 1; i++) {
      for (int j = curLowest; j < Pitch.values().length; j++) {
        if (j == highest.getPitch().ordinal() + 1 && i == highest.getOctave()) {
          break;
        }
        String pitchOctave = Pitch.values()[j].toString() + Integer.toString(i);

        pitches.add(pitchOctave);
      }
      curLowest = 0;
    }

    Collections.reverse(pitches);
    return pitches;
  }

  /**
   * Getter for the current beat.
   *
   * @return the current beat of this visual view
   */
  public int getCurBeat() {
    return this.curBeat;
  }

  /**
   * Sets this view's current beat to display.
   *
   * @param beat the beat to set this visual view to display
   */
  void setCurBeat(int beat) {
    this.curBeat = beat;
    this.notePanel.setCols(beat);
    this.notePanel.repaint();
  }

  /**
   * Getter for the current line position of the note panel.
   *
   * @return the current line position of the note panel
   */
  int getCurLinePos() {
    return this.notePanel.getCurLinePos();
  }

  /**
   * Sets the current line position to a new beat position and repaints the note panel.
   *
   * @param curLinePos the new line position to update
   */
  void setCurLinePos(int curLinePos) {
    this.notePanel.setCurLinePos(curLinePos);
  }

  /**
   * Returns the currently selected pitch from the pitch combobox.
   *
   * @return the currently selected pitch
   */
  Pitch getSelectedPitch() {
    return this.pitches.getItemAt(this.pitches.getSelectedIndex());
  }

  /**
   * Returns the currently selected octave from the octave combobox.
   *
   * @return the currently selected octave
   */
  int getSelectedOctave() {
    return this.octaves.getItemAt(this.octaves.getSelectedIndex());
  }

  /**
   * Gets the duration from the text field as an integer.
   *
   * @return the currently entered number for the duration
   */
  int getCurrentDuration() {
    int x = 0;
    try {
      x = Integer.parseInt(this.duration.getText());
    } catch (NumberFormatException e) {
      this.duration.setText("Please enter a number for the duration.");
    }
    return x;
  }

  /**
   * Obtains the currently selected instrument from the combobox.
   *
   * @return the int representation of the currently selected instrument
   */
  int getCurrentInstrument() {
    String selected = this.instruments.getItemAt(this.instruments.getSelectedIndex());
    switch (selected) {
      case "Grand Piano":
        return 1;
      case "Electric Piano":
        return 5;
      case "Acoustic Guitar":
        return 26;
      case "Electric Guitar":
        return 28;
      case "Violin":
        return 41;
      case "Trumpet":
        return 57;
      case "Saxophone":
        return 66;
      case "Drums":
        return 14;
      case "Banjo":
        return 106;
      default:
        throw new IllegalAccessError("You shouldn't be here!");
    }
  }

  /**
   * Obtains the start beat from the text field.
   *
   * @return the currently entered number for a starting beat
   */
  int getCurrentStartBeat() {
    int x = 0;
    try {
      x = Integer.parseInt(this.startBeat.getText());
    } catch (NumberFormatException e) {
      this.duration.setText("Please enter a number for the starting beat.");
    }
    return x;
  }

  /**
   * Adds an action listener to this visual view for the button.
   *
   * @param listener the action listener to add
   */
  void addActionListener(ActionListener listener) {
    this.addNote.addActionListener(listener);
  }

  /**
   * Gets the note that is represented by the where the user clicked. If no such note exists in
   * the viewModel, return null object.
   *
   * @param x        x-coordinate of the click.
   * @param y        y-coordinate of the click.
   * @param theModel Model to be searched.
   * @return the note at the given coordinatess
   */
  public Note getNoteAt(int x, int y, ViewModel theModel) {
    Note lowestNote = theModel.getLowestNote();
    Note highestNote = theModel.getHighestNote();
    List<String> pitches = this.getPitches(lowestNote, highestNote);
    int yConvert = y / MEASURE_HEIGHT;
    int xConvert = ((x - 40) / 20) + this.curBeat;
    if (xConvert < 0) {
      return null;
    }

    if (yConvert > pitches.size()) {
      return null;
    }
    String rawPitch = pitches.get(yConvert - 1);

    String realPitch = "";
    int octave = 0;
    for (int i = 0; i < rawPitch.length(); i++) {
      if (Character.isDigit(rawPitch.charAt(i)) && i == rawPitch.length() - 1) {
        octave = Character.getNumericValue(rawPitch.charAt(i));
        break;
      }
      if (Character.isDigit(rawPitch.charAt(i)) && i == rawPitch.length() - 2) {
        octave = 10;
        break;
      }
      realPitch = realPitch.concat(Character.toString(rawPitch.charAt(i)));
    }

    realPitch = realPitch.replace('#', 's');
    Pitch real = Pitch.valueOf(realPitch);


    TreeMap<Integer, ArrayList<Note>> notes = theModel.getPiece();
    ArrayList<Note> notesAtClick = notes.get(xConvert);
    if (notesAtClick != null) {
      for (Note n : notesAtClick) {
        if (n.getPitch() == real && n.getOctave() == octave) {
          return n;
        }
      }
    }

    return null;
  }
}

