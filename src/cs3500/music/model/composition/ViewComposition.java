package cs3500.music.model.composition;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.event.IEvent;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.pitch.IPitch;

/**
 * Created by janzimmermann on 03/11/2016.
 */
public class ViewComposition implements IViewComposition {

  private IComposition composition;

  public ViewComposition(IComposition composition) {
    this.composition = composition;
  }


  /**
   * to get a copy of the list of notes in a composition.
   *
   * @return the notes in the composition
   */
  @Override
  public List<INote> getNotes() {
    return new ArrayList<>(composition.getNotes());
  }

  @Override
  public List<IEvent> getEvents() {
    return new ArrayList<>(this.composition.getEvents());
  }

  /**
   * To get the note at the given pitch and timecode. Returns null if there is none.
   *
   * @param pitch    The pitch to search at
   * @param timecode timecode to search at.
   * @return the Note or Null
   */
  @Override
  public INote getNoteAt(IPitch pitch, int timecode) {
    return composition.getNoteAt(pitch, timecode);
  }

  /**
   * To get the lowest pitch of the composition.
   *
   * @return the Pitch representation of the lowest pitch in the composition
   */
  @Override
  public IPitch getLowestPitch() {
    return this.composition.getLowestPitch();
  }

  /**
   * To get the highest pitch of the composition.
   *
   * @return the highest pitch found in the composition
   */
  @Override
  public IPitch getHighestPitch() {
    return this.composition.getHighestPitch();
  }

  /**
   * Get the amount of beats in a measure.
   *
   * @return the amounf of beats in a measure.
   */
  @Override
  public int getMeasure() {
    return this.composition.getMeasure();
  }

  /**
   * Get the tempo of this composition.
   *
   * @return the tempo of this {@code IComposition}
   */
  @Override
  public int getTempo() {
    return this.composition.getTempo();
  }

  /**
   * To get the length of the composition.
   *
   * @return the amount of beats in the composition.
   */
  @Override
  public int getLength() {
    return this.composition.getLength();
  }


}
