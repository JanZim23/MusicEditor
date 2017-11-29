package cs3500.music.model.composition;

import java.util.List;

import cs3500.music.model.event.IEvent;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.pitch.IPitch;

/**
 * To represent different compositions.
 */
public interface IComposition extends IViewComposition {

  /**
   * To get the length of the composition.
   *
   * @return the amount of beats in the composition.
   */
  int getLength();

  /**
   * Get the amount of beats in a measure.
   *
   * @return the amounf of beats in a measure.
   */
  int getMeasure();

  /**
   * Set the amount of beats in a measure.
   *
   * @param measure the amount of beats in the measure.
   */
  void setMeasure(int measure);

  /**
   * Get the tempo of this composition.
   *
   * @return the tempo of this {@code IComposition}
   */
  int getTempo();

  /**
   * Sets the tempo for this {@code IComposition}.
   */
  void setTempo(int tempo);

  /**
   * To get the lowest pitch of the composition.
   *
   * @return the Pitch representation of the lowest pitch in the composition
   */
  IPitch getLowestPitch();

  /**
   * To get the highest pitch of the composition.
   *
   * @return the highest pitch found in the composition
   */
  IPitch getHighestPitch();

  /**
   * To add a note to the composition.
   *
   * @param note the note to be added
   * @throws IllegalArgumentException if there is already a note that intersecs it
   */
  void addNote(INote note) throws IllegalArgumentException;

  /**
   * Add several Notes to the composition.
   *
   * @param notes notes
   */
  void addNotes(INote... notes);

  void addEvent(IEvent event);

  /**
   * To delete a note from the composition.
   *
   * @param note the note to be deleted.
   */
  void deleteNote(INote note);

  /**
   * To get a list of notes in the composition.
   *
   * @return a list of notes in the composition
   */
  List<INote> getNotes();

  /**
   * To get the note at the given pitch and timecode. Returns null if there is none.
   *
   * @param pitch    The pitch to search at
   * @param timecode timecode to search at.
   * @return the Note or Null
   */
  INote getNoteAt(IPitch pitch, int timecode);

  /**
   * To add all notes from a composition <b>after</b> this composition.
   *
   * @param comp the composition to be added.
   */
  void addComposition(IComposition comp);

  /**
   * To overlay all notes from a composition layered with this one.
   * The method will add all notes to the composition and wont terminate if a note intersects with
   * an existing one, but it will still throw an exception.
   *
   * @param comp the composition to be overlayed
   * @throws IllegalArgumentException If there are notes that couldnt be added to the composition
   */
  void overlayComposition(IComposition comp) throws IllegalArgumentException;

}
