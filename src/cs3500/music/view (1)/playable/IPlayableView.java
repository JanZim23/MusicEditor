package cs3500.music.view.playable;

/**
 * Interface for an interactive view of an IComposition.
 */
public interface IPlayableView extends cs3500.music.view.IView {


  /**
   * Set playback position to given timecode.
   *
   * @param timecode beat index to jump to.
   */
  void setPlaybackPosition(int timecode);

  /**
   * Starts playback at timecode 0.
   */
  void startPlayback();


  /**
   * Pauses playback at current timecode.
   */
  void pausePlayback();

  /**
   * Resumes playback at current timecode.
   */
  void resumePlayback();
}
