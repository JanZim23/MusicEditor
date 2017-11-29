package cs3500.music.model.event.note.pitch;

/**
 * To represent a pitch.
 */
public class Pitch implements IPitch {

  /**
   * The integer representation of the pitch starting at C0.
   */
  private final int pitch;

  /**
   * To construct a pitch from a quality object and an octave identification.
   *
   * @param quality quality of the note.
   * @param octave  octave identification of the note.
   */
  public Pitch(PitchQuality quality, int octave) {
    if (octave < 0) {
      throw new IllegalArgumentException("Negative Octaves Not Allowed");
    }
    this.pitch = (octave * 12) + quality.toPitchInt();
  }

  /**
   * To construct a pitch from a single integer representing its pitch.
   *
   * @param pitch the int representing pitch starting at c0
   */
  public Pitch(int pitch) {
    this.pitch = pitch;
  }


  /**
   * To get the octave identification this Pitch is in.
   *
   * @return the octave number the pitch is in.
   */
  private int getOctave() {
    return (this.pitch - (this.pitch % 12)) / 12;
  }

  /**
   * To get the quality of the Pitch.
   *
   * @return the quality object of the
   */
  private PitchQuality getQuality() {
    return PitchQuality.pitchQualityFromInt(this.pitch % 12);
  }

  /**
   * To get the single integer pitch representation of this pitch.
   * Starting C0 at 0...
   *
   * @return the single integer representing the pitch
   */
  public int getPitch() {
    return this.pitch;
  }

  /**
   * To check whether two pitches are equal.
   * A Pitch is equal if its absolute integer representations match.
   *
   * @param o object to compare it with.
   * @return whether the pitches equal.
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof IPitch) {
      return ((IPitch) o).getPitch() == this.getPitch();
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
    return this.getPitch();
  }

  /**
   * To get a String representation of the pitch.
   *
   * @return a string of the quality and Octave
   */
  @Override
  public String toString() {
    return this.getQuality().toString() + this.getOctave();
  }

}
