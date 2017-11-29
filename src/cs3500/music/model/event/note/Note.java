package cs3500.music.model.event.note;

import cs3500.music.model.event.note.pitch.IPitch;
import cs3500.music.model.event.note.pitch.IPitchFactory;

/**
 * Created by Jan on 21.10.2016.
 */
public class Note implements INote {

  /**
   * the integer representation of a pitch.
   */
  private IPitch pitch;
  /**
   * The starting time of the note counted in beats.
   */
  private int start;

  /**
   * The Duration of the Note.
   */
  private int duration;

  private int instrument;

  private int volume;

  private IPitchFactory pitchFactory;


  /**
   * To Create a note.
   *
   * @param pitch the given pitch
   */
  public Note(IPitchFactory pitchFactory, IPitch pitch, int start, int duration) {
    this(pitchFactory, pitch, start, duration, 0, 54);
  }

  /**
   * To Create a note.
   *
   * @param pitch the given pitch
   */
  public Note(IPitchFactory pitchFactory, IPitch pitch, int start, int duration,
              int instrument, int volume) {
    this.pitch = pitch;
    this.start = start;
    this.duration = duration;
    this.instrument = instrument;
    this.volume = volume;
    this.pitchFactory = pitchFactory;
  }

  /**
   * To get the pitch object describing this notes pitch.
   *
   * @return the pitch of the note.
   */
  @Override
  public IPitch getPitch() {
    return pitchFactory.build(this.pitch.getPitch());
  }

  /**
   * To get the start timecode of the note in the composition
   *
   * @return the int represeing the start timecode.
   */
  @Override
  public int getStart() {
    return this.start;
  }


  /**
   * To Construct a Note by defining its Pitch, starttime and duration.
   *
   * @param pitch    the pitch object
   * @param start    the start time index of the note
   * @param duration the duration of the note.
   */

  /**
   * To get the duration of the note in beats.
   *
   * @return the duration.
   */
  @Override
  public int getDuration() {
    return this.duration;
  }

  /**
   * To get the instrument value of this note.
   *
   * @return the instrument number
   */
  @Override
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * To get the volume.
   *
   * @return volume of this note.
   */
  @Override
  public int getVolume() {
    return this.volume;
  }


  /**
   * To get a string representation of the Note.
   *
   * @return string representation of the pitch.
   */
  @Override
  public String toString() {
    return this.pitch.toString();
  }

  /**
   * To check equality between this and another note.
   * A Note is the same if the pitch is the equal, by its own definition,
   * the start timecode is the same and the duration is the same. It does not need to be the
   * same object.
   *
   * @param o the Object to compare this note to.
   * @return whether the notes are considered equal.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof INote) {
      INote note = (INote) o;
      boolean same = this.getPitch().getPitch() == note.getPitch().getPitch()
              && this.getStart() == note.getStart()
              && this.getDuration() == note.getDuration()
              && this.getInstrument() == note.getInstrument()
              && this.getVolume() == note.getVolume();
      return same;
    } else {
      return false;
    }
  }

  /**
   * To get the hashcode of this object.
   *
   * @return a unique int representing this object.
   */
  @Override
  public int hashCode() {
    return Integer.hashCode(this.getInstrument())
            * Integer.hashCode(this.getPitch().getPitch())
            * Integer.hashCode(this.getStart())
            * Integer.hashCode(this.getDuration())
            * Integer.hashCode(this.getVolume());

  }


}
