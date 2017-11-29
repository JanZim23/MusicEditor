package cs3500.music.provider.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import cs3500.music.provider.model.Note;
import cs3500.music.provider.model.Pitch;

/**
 * The textual view of a music editor.
 */
public class TextMusicEditorView implements IMusicEditorView {

  private String text;

  /**
   * Constructor for a textual music editor view.
   * <p>
   * //EDIT: Changed to public (Jan Zimmermann)
   */
  public TextMusicEditorView() {
    this.text = "";
  }

  /**
   * Displays the given model in whatever way is designated by the concrete implementation of this
   * interface. This view will just display the textual view of this model.
   */
  @Override
  public void display() {
    System.out.print(this.text);
  }

  /**
   * Gets the information from the piece of music to use in this view.
   *
   * @param viewModel the viewModel to use for information
   */
  @Override
  public void accessNotes(ViewModel viewModel) {
    this.text = this.musicAsString(viewModel);
  }

  private String musicAsString(ViewModel viewModel) {

    TreeMap<Integer, ArrayList<Note>> notes = viewModel.getPiece();

    if (notes.isEmpty()) {
      return "";
    }

    StringBuilder sheetMusic = new StringBuilder();

    int beatDigits = Integer.toString(notes.lastKey()).length();

    String pitchRange = this.rangeOfPitches(viewModel.getLowestNote(),
            viewModel.getHighestNote(), beatDigits);

    sheetMusic.append(pitchRange);

    for (int i = 0; i < notes.lastKey() + 1; i++) {
      sheetMusic.append("\n");

      String beatNum =
              new String(new char[beatDigits - Integer.toString(i).length()]).replace('\0', ' ');

      sheetMusic.append(beatNum).append(Integer.toString(i));

      ArrayList<Note> toFind;

      if (notes.get(i) != null) {
        toFind = notes.get(i);
        sheetMusic.append(notesAtBeat(i, pitchRange.trim().split(" +"), toFind));
      }
    }
    return sheetMusic.toString();
  }

  /**
   * Returns a line of the range of pitches in the piece.
   *
   * @param lowest   the lowest note to represent in pitches
   * @param highest  the highest note to represent in pitches
   * @param startGap the gap at the start for the beats
   * @return a single line String that contains the range of pitches in this song
   */
  private String rangeOfPitches(Note lowest, Note highest, int startGap) {
    StringBuilder pitches = new StringBuilder();

    String leadingSpaces = new String(new char[startGap]).replace('\0', ' ');

    pitches.append(leadingSpaces);

    int curLowest = lowest.getPitch().ordinal();

    for (int i = lowest.getOctave(); i < highest.getOctave() + 1; i++) {
      for (int j = curLowest; j < Pitch.values().length; j++) {
        if (j == highest.getPitch().ordinal() + 1 && i == highest.getOctave()) {
          break;
        }

        String pitchOctave = Pitch.values()[j].toString() + Integer.toString(i);
        String toAdd;

        if (pitchOctave.length() == 2) {
          toAdd = "  " + pitchOctave + " ";
        } else if (pitchOctave.length() == 3) {
          toAdd = " " + pitchOctave + " ";
        } else {
          toAdd = " " + pitchOctave;
        }
        pitches.append(toAdd);
      }
      curLowest = 0;
    }

    return pitches.toString();
  }

  /**
   * Gets the notes at the given current beat and returns them as a String.
   *
   * @param curBeat        the current beat of the piece
   * @param rangeOfPitches an array of pitches that represents the range of pitches in the piece
   * @param notes          the ArrayList of notes that are present at the current beat
   * @return a single line String that contains the notes at their respective pitch in the piece
   */
  private String notesAtBeat(int curBeat, String[] rangeOfPitches, ArrayList<Note> notes) {
    int length = rangeOfPitches.length * 5;

    String lineOfSpaces = new String(new char[length]).replace('\0', ' ');

    StringBuilder lineOfNotes = new StringBuilder(lineOfSpaces);

    for (Note n : notes) {
      String pitchOctave = n.getPitch().toString() + Integer.toString(n.getOctave());

      int pitchOctaveIndex = Arrays.asList(rangeOfPitches).indexOf(pitchOctave);

      if (curBeat == n.getStartBeat()) {
        lineOfNotes.setCharAt((pitchOctaveIndex) * 5 + 2, '+');
      } else {
        lineOfNotes.setCharAt((pitchOctaveIndex) * 5 + 2, '|');
      }
    }

    return lineOfNotes.toString();
  }

  /**
   * Getter for the private text field.
   */

  public String getText() {
    return this.text;
  }
}
