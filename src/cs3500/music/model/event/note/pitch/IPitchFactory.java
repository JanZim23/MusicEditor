package cs3500.music.model.event.note.pitch;

/**
 * A Pitch Factory.
 */
public interface IPitchFactory {

  /**
   * Build an IPitch using a single integer representing its pitch.
   *
   * @param pitch the int representation of the pitch
   * @return the created IPitch
   */
  IPitch build(int pitch);

  /**
   * Build an IPitch using a Pitch Quality and Octave Int.
   *
   * @param quality Pitch Quality
   * @param octave  Pitch octave identification
   * @return an IPitch.
   */
  IPitch buildFromQuality(PitchQuality quality, int octave);

}
