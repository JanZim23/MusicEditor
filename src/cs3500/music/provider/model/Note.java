package cs3500.music.provider.model;

/**
 * Represents a single note. Each note has an Pitch, an integer octave (1 - 10), an integer
 * length (in number of beats), and an integer start time (the beat the note starts).
 * Changes to the model from last assignment:
 * - Added fields for instrument and volume in order to be able to play back notes in MIDI.
 * - Added getters for these new fields.
 * EDIT: Removed Final modifier (Jan Zimmermann)
 */
public class Note implements Comparable<Note> {

  /**
   * This note's pitch.
   */
  private Pitch pitch;

  /**
   * This note's octave as an integer, must be between 1 and 10 inclusive.
   */
  private int octave;

  /**
   * The length in beats of this note's sustain. Must be greater than or equal to 0.
   */
  private int length;

  /**
   * The beat that this note starts on. Must be greater than or equal to 0.
   */
  private int startBeat;

  /**
   * The instrument number for this note to be used by midi.
   */
  private int instrument;

  /**
   * The volume of this note from [0 - 127], used by the midi synthesizer.
   */
  private int volume;


  /**
   * Constructor for note.
   */
  public Note(Pitch pitch, int octave, int length, int startBeat, int instrument, int volume) {
    if (startBeat < 0 || octave < 0 || octave > 10 || length < 0 || volume < 0 || volume > 127) {
      throw new IllegalArgumentException("Invalid note.");
    }
    this.pitch = pitch;
    this.octave = octave;
    this.length = length;
    this.startBeat = startBeat;
    this.volume = volume;
    this.instrument = instrument;
  }

  /**
   * Getter for the startBeat field.
   *
   * @return this notes starting beat
   */
  public int getStartBeat() {
    return this.startBeat;
  }

  /**
   * Getter for the length field.
   *
   * @return this notes length in beats
   */
  public int getLength() {
    return this.length;
  }

  /**
   * Getter for the pitch field.
   *
   * @return this notes pitch.
   */
  public Pitch getPitch() {
    return this.pitch;
  }

  /**
   * Getter for the octave field.
   *
   * @return this notes octave
   */
  public int getOctave() {
    return this.octave;
  }

  /**
   * Getter for the instrument field.
   *
   * @return this notes instrument
   */
  public int getInstrument() {
    return this.instrument;
  }

  /**
   * Getter for the volume field.
   *
   * @return this volume octave
   */
  public int getVolume() {
    return this.volume;
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

    return this.startBeat == ((Note) that).startBeat && this.length == ((Note) that).length
            && this.octave == ((Note) that).octave && this.pitch == ((Note) that).pitch;
  }

  /**
   * Hashes this note using java's built in hash code functions.
   *
   * @return the hashcode associated with this note's fields
   */
  @Override
  public int hashCode() {
    return Integer.hashCode(this.length) + Integer.hashCode(this.octave) + this.pitch.hashCode();
  }

  /**
   * Compares this note to another note and checks if this note has a higher pitch.
   *
   * @param o the note to compare against this one
   * @return true if this note is a higher pitch than the given one, false otherwise
   */
  public boolean higherNote(Note o) {
    if (this.equals(o) || this.octave < o.octave) {
      return false;
    }

    return this.octave > o.octave || this.pitch.ordinal() > o.pitch.ordinal();
  }

  /**
   * Compares this note to another note and checks if this note has a lower pitch.
   *
   * @param o the note to compare against this one
   * @return true if this note is a lower pitch than the given one, false otherwise
   */
  public boolean lowerNote(Note o) {
    if (this.equals(o) || this.octave > o.octave) {
      return false;
    }

    return this.octave < o.octave || this.pitch.ordinal() < o.pitch.ordinal();
  }

  /**
   * To Compage this.
   */
  @Override
  public int compareTo(Note o) {
    return this.startBeat - o.startBeat;
  }

}
