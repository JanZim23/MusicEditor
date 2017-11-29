package cs3500.music.conversion;

import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.provider.model.Note;
import cs3500.music.provider.model.Pitch;

/**
 * To Mimic the tasks of the Providers Note implementation.
 */
public class ProviderNote extends Note {


  private INote mirror;
  private IPitchFactory pitchFactory;

  /**
   * Constructor for note.
   */
  public ProviderNote(INote mirror, IPitchFactory pitchFactory) {
    super(Pitch.C, 0, 0, 0, 0, 0);
    this.mirror = mirror;
    this.pitchFactory = pitchFactory;
  }

  /**
   * Getter for the startBeat field.
   *
   * @return this notes starting beat
   */
  @Override
  public int getStartBeat() {
    return mirror.getStart();
  }

  /**
   * Getter for the length field.
   *
   * @return this notes length in beats
   */
  public int getLength() {
    return mirror.getDuration();
  }

  /**
   * Getter for the pitch field.
   *
   * @return this notes pitch.
   */
  public Pitch getPitch() {
    int pitch = this.mirror.getPitch().getPitch() % 12;
    switch (pitch) {
      case 0:
        return Pitch.C;
      case 1:
        return Pitch.Cs;
      case 2:
        return Pitch.D;
      case 3:
        return Pitch.Ds;
      case 4:
        return Pitch.E;
      case 5:
        return Pitch.F;
      case 6:
        return Pitch.Fs;
      case 7:
        return Pitch.G;
      case 8:
        return Pitch.Gs;
      case 9:
        return Pitch.A;
      case 10:
        return Pitch.As;
      case 11:
        return Pitch.B;
      default:
        throw new IllegalArgumentException("Given pitch is out of bound: " + pitch);
    }
  }

  /**
   * Getter for the octave field.
   *
   * @return this notes octave
   */
  public int getOctave() {
    return (this.mirror.getPitch().getPitch() - (this.mirror.getPitch().getPitch() % 12)) / 12;
  }

  /**
   * Getter for the instrument field.
   *
   * @return this notes instrument
   */
  public int getInstrument() {
    return mirror.getInstrument();
  }

  /**
   * Getter for the volume field.
   *
   * @return this volume octave
   */
  public int getVolume() {
    return mirror.getVolume();
  }


  /**
   * Compares this note to another object for equality.
   *
   * @param that the object to be compared to this card for equality
   * @return true if this note has the same properties as the given object
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }

    if (!(that instanceof Note)) {
      return false;
    }

    return this.getStartBeat() == ((Note) that).getStartBeat()
            && this.getLength() == ((Note) that).getLength()
            && this.getOctave() == ((Note) that).getOctave()
            && this.getPitch() == ((Note) that).getPitch();
  }

  /**
   * Hashes this note using java's built in hash code functions.
   *
   * @return the hashcode associated with this note's fields
   */
  @Override
  public int hashCode() {
    return Integer.hashCode(this.getLength())
            + Integer.hashCode(this.getOctave()) + this.getPitch().hashCode();
  }

  /**
   * Compares this note to another note and checks if this note has a higher pitch.
   *
   * @param o the note to compare against this one
   * @return true if this note is a higher pitch than the given one, false otherwise
   */
  public boolean higherNote(Note o) {
    if (this.equals(o) || this.getOctave() < o.getOctave()) {
      return false;
    }

    return this.getOctave() > o.getOctave() || this.getPitch().ordinal() > o.getPitch().ordinal();
  }

  /**
   * Compares this note to another note and checks if this note has a lower pitch.
   *
   * @param o the note to compare against this one
   * @return true if this note is a lower pitch than the given one, false otherwise
   */
  public boolean lowerNote(Note o) {
    if (this.equals(o) || this.getOctave() > o.getOctave()) {
      return false;
    }

    return this.getOctave() < o.getOctave() || this.getPitch().ordinal() < o.getPitch().ordinal();
  }

}
