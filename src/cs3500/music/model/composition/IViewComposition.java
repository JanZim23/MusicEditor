package cs3500.music.model.composition;

import java.util.List;

import cs3500.music.model.event.IEvent;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.pitch.IPitch;

/**
 * To Represent an object with Model access.
 */
public interface IViewComposition {
  /**
   * to get the list of notes in a composition.
   *
   * @return the notes in the composition
   */
  List<INote> getNotes();

  List<IEvent> getEvents();

  /**
   * To get the note at the given pitch and timecode. Returns null if there is none.
   *
   * @param pitch    The pitch to search at
   * @param timecode timecode to search at. (in beats)
   * @return the Note or Null
   */
  INote getNoteAt(IPitch pitch, int timecode);

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
   * Get the amount of beats in a measure.
   *
   * @return the amount of beats in a measure.
   */
  int getMeasure();

  /**
   * Get the tempo of this composition.
   *
   * @return the tempo of this {@code IComposition}
   */
  int getTempo();

  /**
   * To get the length of the composition.
   *
   * @return the amount of beats in the composition.
   */
  int getLength();

}
