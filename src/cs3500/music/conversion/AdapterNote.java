package cs3500.music.conversion;

import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.pitch.IPitch;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.provider.model.Note;

/**
 * To adapt the providers implementation of a Note.
 */
public class AdapterNote implements INote {

  private Note aNote;
  private IPitchFactory pitchFactory;

  public AdapterNote(Note aNote, IPitchFactory pitchFactory) {
    this.aNote = aNote;
    this.pitchFactory = pitchFactory;
  }

  /**
   * To get the object representation of the pitch of the Note. Steps from C0.
   *
   * @return the pitch of the note.
   */
  public IPitch getPitch() {
    int pitch;
    switch (this.aNote.getPitch()) {
      case C:
        pitch = 0;
        break;
      case Cs:
        pitch = 1;
        break;
      case D:
        pitch = 2;
        break;
      case Ds:
        pitch = 3;
        break;
      case E:
        pitch = 4;
        break;
      case F:
        pitch = 5;
        break;
      case Fs:
        pitch = 6;
        break;
      case G:
        pitch = 7;
        break;
      case Gs:
        pitch = 8;
        break;
      case A:
        pitch = 9;
        break;
      case As:
        pitch = 10;
        break;
      case B:
        pitch = 11;
        break;
      default:
        throw new IllegalStateException();
    }
    return pitchFactory.build(pitch + 12 * aNote.getOctave());
  }

  /**
   * To get the start timecode of the note in the composition.
   *
   * @return the int represeing the start timecode.
   */
  @Override
  public int getStart() {
    return aNote.getStartBeat();
  }

  /**
   * To get the duration of the note in beats.
   *
   * @return the duration.
   */
  @Override
  public int getDuration() {
    return aNote.getLength();
  }

  /**
   * To get the instrument value of this note.
   *
   * @return the instrument number
   */
  @Override
  public int getInstrument() {
    return aNote.getInstrument();
  }

  /**
   * To get the volume.
   *
   * @return volume of this note.
   */
  @Override
  public int getVolume() {
    return aNote.getVolume();
  }

  @Override
  public String toString() {
    return this.getPitch().toString();
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
      int thisPitch = this.getPitch().getPitch();
      int thisStart = this.getStart();
      int thisDuration = this.getDuration();
      int thisInstrument = this.getInstrument();
      int thisVolume = this.getVolume();


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
