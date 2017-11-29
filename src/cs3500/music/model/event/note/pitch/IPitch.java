package cs3500.music.model.event.note.pitch;

/**
 * To Represent an IPitch.
 */
public interface IPitch {

  /**
   * To get the single integer pitch representation of this pitch.
   * Starting C0 at 0...
   *
   * @return the single integer representing the pitch
   */
  int getPitch();
}
