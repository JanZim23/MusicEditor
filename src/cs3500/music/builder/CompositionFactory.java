package cs3500.music.builder;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.composition.Composition;
import cs3500.music.model.composition.IComposition;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.INoteFactory;
import cs3500.music.model.event.note.NoteFactoryImpl;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.model.event.note.pitch.PitchFactoryImpl;

/**
 * A Factory to produce an IComposition.
 */
public class CompositionFactory implements CompositionBuilder<IComposition> {
  /**
   * To represent the List of notes in the composition beeing created.
   */
  private List<INote> notes;
  /**
   * the amount of beats in a measure.
   */
  private int measure;
  /**
   * the tempo of the composition.
   */
  private int tempo;

  private INoteFactory noteFactory;

  private IPitchFactory pitchFactory;


  /**
   * To construct a composition Factory.
   */
  public CompositionFactory(INoteFactory noteFactory, IPitchFactory pitchFactory) {
    this.measure = 4;
    this.tempo = 0;
    this.notes = new ArrayList<>();
    this.noteFactory = noteFactory;
    this.pitchFactory = pitchFactory;
  }

  /**
   * Constructs an actual composition, given the notes that have been added.
   *
   * @return The new composition
   */
  @Override
  public IComposition build() {
    Composition composition = new Composition(this.measure, this.tempo,
            new NoteFactoryImpl(), new PitchFactoryImpl());
    composition.addNotes(this.notes);

    return composition;
  }


  /**
   * Sets the tempo of the piece.
   *
   * @param tempo The speed, in microseconds per beat
   * @return This builder
   */
  @Override
  public CompositionBuilder<IComposition> setTempo(int tempo) {
    this.tempo = tempo;
    return this;
  }

  /**
   * Adds a new note to the piece.
   *
   * @param start      The start time of the note, in beats
   * @param end        The end time of the note, in beats
   * @param instrument The instrument number (to be interpreted by MIDI)
   * @param pitch      The pitch (in the range [0, 127], where 60 represents C4, the middle-C on a
   *                   piano)
   * @param volume     The volume (in the range [0, 127])
   */
  @Override
  public CompositionBuilder<IComposition> addNote(int start, int end, int instrument,
                                                  int pitch, int volume) {
    this.notes.add(this.noteFactory.build(pitchFactory, pitch - 12, start, end - start,
            instrument, volume));
    return this;
  }
}
