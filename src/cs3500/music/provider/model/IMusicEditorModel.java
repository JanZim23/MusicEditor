package cs3500.music.provider.model;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Interface representing the model for a simple music editor. It is parametrized over the
 * note type K to allow for whatever implementation is used.
 * Contains some simple methods that should be possible in a music editor.
 */
public interface IMusicEditorModel<K> {

  /**
   * Gets this piece's map of integers to notes.
   *
   * @return this piece's map of integers to notes.
   */
  TreeMap<Integer, ArrayList<K>> getPiece();

  /**
   * Gets this piece's tempo as an int.
   *
   * @return this piece's tempo
   */
  int getTempo();

  /**
   * Adds a single note to this piece of music. Sets the map to contain that note at every
   * beat it is played or sustained on.
   *
   * @param note the note to be added to this piece of music
   * @throws IllegalArgumentException if the note to be added overlaps with an existing note
   */
  void addNote(K note) throws IllegalArgumentException;

  /**
   * Removes a single note from this piece of music.
   *
   * @param note the note to be removed from this piece of music
   * @return true if the remove is successful, false otherwise
   */
  boolean removeNote(K note);

  /**
   * Edits a single note in this piece of music. Essentially replaces the note with a new note
   *
   * @param toEdit  the note to be edited and replaced
   * @param newNote the note to replace the given note with
   * @throws IllegalArgumentException if the note to be edited is not in this piece of music OR if
   *                                  the new note is not valid
   */
  void editNote(K toEdit, K newNote) throws IllegalArgumentException;

  /**
   * Adds another piece of music to this one by playing it directly after the last note of this
   * piece. Modifies this piece of music to be the two combined pieces.
   *
   * @param other the piece of music to be added to this piece
   */
  void appendPiece(IMusicEditorModel<K> other);

  /**
   * Adds another piece of music to this one by layering the two on top of each other. Any
   * overlapping notes from the second piece will be removed in favor of this piece's notes.
   *
   * @param other the piece of music to be added to this piece
   */
  void overlayPiece(IMusicEditorModel<K> other);

}
