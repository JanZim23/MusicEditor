package cs3500.music.model.event.note.pitch;

/**
 * Created by Jan on 28.11.2016.
 */
public class PitchFactoryImpl implements IPitchFactory {
  /**
   * Build an IPitch using a single integer representing its pitch.
   *
   * @param pitch the int representation of the pitch
   * @return the created IPitch
   */
  @Override
  public IPitch build(int pitch) {
    return new Pitch(pitch);
  }

  @Override
  public IPitch buildFromQuality(PitchQuality quality, int octave) {
    return new Pitch(quality, octave);
  }
}
