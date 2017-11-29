package cs3500.music.model.event.note;

import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.model.event.note.pitch.Pitch;

/**
 * Created by Jan on 28.11.2016.
 */
public class NoteFactoryImpl implements INoteFactory {

  @Override
  public INote build(IPitchFactory pitchFactory, int pitch, int start,
                     int duration, int instrument, int volume) {
    return new Note(pitchFactory, new Pitch(pitch), start, duration, instrument, volume);
  }
}
