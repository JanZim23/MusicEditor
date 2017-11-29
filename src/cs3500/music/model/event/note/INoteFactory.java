package cs3500.music.model.event.note;

import cs3500.music.model.event.note.pitch.IPitchFactory;

/**
 * To Create an INote.
 */
public interface INoteFactory {

  /**
   * To Create an INote from the given variables.
   *
   * @param pitch      the pitch, notes above C0 in int
   * @param start      start beat of the note
   * @param duration   duration in beats of the note
   * @param instrument instrument id
   * @param volume     volume/velocity
   * @return INote with given data.
   */
  INote build(IPitchFactory pitchFactory, int pitch, int start, int duration, int instrument, int volume);

}
