package cs3500.music.provider.view;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import cs3500.music.provider.model.IMusicEditorModel;
import cs3500.music.provider.model.Note;

/**
 * View model to get information for the views.
 */
public class ViewModel {

  private IMusicEditorModel<Note> theModel;

  /**
   * Constructor that takes in the actual model.
   */
  public ViewModel(IMusicEditorModel<Note> theModel) {
    this.theModel = theModel;
  }

  /**
   * Gets this piece's map of integers to notes.
   * <p>
   * EDIT: Changed to public such that it can be overriden by extending classes. (Jan Zimmermann)
   *
   * @return this piece's map of integers to notes.
   */
  public TreeMap<Integer, ArrayList<Note>> getPiece() {
    return this.theModel.getPiece();
  }

  /**
   * Gets this piece's tempo as an int.
   * <p>
   * EDIT: Changed to public such that it can be overriden by extending classes. (Jan Zimmermann)
   *
   * @return this piece's tempo
   */
  public int getTempo() {
    return this.theModel.getTempo();
  }

  /**
   * Obtains the lowest note in this piece.
   * <p>
   * EDIT: Changed to public such that it can be overriden by extending classes. (Jan Zimmermann)
   *
   * @return the lowest note in this piece
   */
  public Note getLowestNote() {
    Note lowest = null;
    for (Map.Entry<Integer, ArrayList<Note>> entry : this.getPiece().entrySet()) {
      if (entry.getValue().size() != 0 && entry.getValue() != null) {
        for (Note n : entry.getValue()) {
          if (lowest == null || n.lowerNote(lowest)) {
            lowest = n;
          }
        }
      }
    }
    return lowest;
  }

  /**
   * Obtains the highest note in this piece.
   * <p>
   * EDIT: Changed to public such that it can be overriden by extending classes. (Jan Zimmermann)
   *
   * @return the highest note in this piece
   */
  public Note getHighestNote() {
    Note highest = null;
    for (Map.Entry<Integer, ArrayList<Note>> entry : this.getPiece().entrySet()) {
      if (entry.getValue().size() != 0 && entry.getValue() != null) {
        for (Note n : entry.getValue()) {
          if (highest == null || n.higherNote(highest)) {
            highest = n;
          }
        }
      }
    }
    return highest;
  }

}
