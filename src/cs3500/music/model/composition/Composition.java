package cs3500.music.model.composition;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.event.IEvent;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.INoteFactory;
import cs3500.music.model.event.note.NoteFactoryImpl;
import cs3500.music.model.event.note.pitch.IPitch;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.model.event.note.pitch.PitchFactoryImpl;

/**
 * To represent a composition of notes. Dope amirite
 */
public class Composition implements IComposition {

  /**
   * To represent all the notes in the composition.
   */
  private List<INote> notes;


  private List<IEvent> events;


  /**
   * To represent the amount of measures in a bar.
   */
  private int measure;

  private int tempo;

  private INoteFactory noteFactory;

  private IPitchFactory pitchFactory;

  public Composition() {
    this(4, 0, new NoteFactoryImpl(), new PitchFactoryImpl());
  }

  /**
   * To Construct a Composition.
   */
  public Composition(int measure, int tempo, INoteFactory noteFactory, IPitchFactory pitchFactory) {
    this.measure = measure;
    this.tempo = tempo;
    this.notes = new ArrayList<>();
    this.events = new ArrayList<>();

    this.noteFactory = noteFactory;
    this.pitchFactory = pitchFactory;
  }

  /**
   * To get the length of the composition.
   *
   * @return integer representation of the timecode of the ending of the last note.
   */
  @Override
  public int getLength() {
    int length = 0;
    for (INote note : this.getNotes()) {
      if (length < (note.getStart() + note.getDuration())) {
        length = note.getStart() + note.getDuration();
      }
    }
    return length;
  }

  /**
   * Get the Measure, the amount of beats per measure.
   *
   * @return integer of measure.
   */
  @Override
  public int getMeasure() {
    return this.measure;
  }

  /**
   * Set the measure of this composition.
   *
   * @param measure the amount of beats in the measure.
   */
  @Override
  public void setMeasure(int measure) {
    this.measure = measure;
  }

  /**
   * Get the tempo of this composition.
   *
   * @return the tempo of this {@code IComposition}
   */
  @Override
  public int getTempo() {
    return this.tempo;
  }

  /**
   * Sets the tempo for this {@code IComposition}.
   */
  @Override
  public void setTempo(int tempo) {
    this.tempo = tempo;
  }

  /**
   * Iterated through the array of notes to find the one with the highest pitch.
   *
   * @return the note with the highest pitch
   */
  @Override
  public IPitch getHighestPitch() {
    IPitch highest = null;
    for (INote note : this.getNotes()) {
      if (highest != null) {
        if (highest.getPitch() < note.getPitch().getPitch()) {
          highest = this.pitchFactory.build(note.getPitch().getPitch());
        }
      } else {
        highest = this.pitchFactory.build(note.getPitch().getPitch());
      }
    }
    if (highest == null) {
      throw new IllegalStateException("No Notes in composition");
    }
    return highest;
  }

  /**
   * Iterated through the array of notes to find the one with the lowest pitch.
   *
   * @return the note object with the lowest pitch
   */
  @Override
  public IPitch getLowestPitch() {
    IPitch lowest = null;
    for (INote note : this.getNotes()) {
      if (lowest != null) {
        if (lowest.getPitch() > note.getPitch().getPitch()) {
          lowest = this.pitchFactory.build(note.getPitch().getPitch());
        }
      } else {
        lowest = this.pitchFactory.build(note.getPitch().getPitch());
      }
    }
    if (lowest == null) {
      throw new IllegalStateException("No Notes in composition");
    }
    return lowest;
  }

  /**
   * To add a note to this composition.
   *
   * @param note the note to be added
   * @throws IllegalArgumentException if there is a note intersecting the given note
   */
  @Override
  public void addNote(INote note) throws IllegalArgumentException {
    for (int i = note.getStart(); i < note.getStart() + note.getDuration(); i++) {
      INote noteAt = this.getNoteAt(this.pitchFactory.build(note.getPitch().getPitch()), i);
      if (noteAt != null && noteAt.getInstrument() == note.getInstrument()) {
        throw new IllegalArgumentException("A Note already exists at this location.");
      }
    }
    this.notes.add(note);
  }

  /**
   * Add several Notes to the composition.
   *
   * @param notes notes
   */
  @Override
  public void addNotes(INote... notes) {
    for (INote n : notes) {
      this.addNote(n);
    }
  }

  @Override
  public void addEvent(IEvent event) {
    this.events.add(event);
  }

  /**
   * Add several Notes to the composition.
   *
   * @param notes notes
   */
  public void addNotes(List<INote> notes) {
    for (INote n : notes) {
      this.addNote(n);
    }
  }


  /**
   * To delete a note from the list of notes in the composition.
   *
   * @param note the note to be deleted.
   */
  @Override
  public void deleteNote(INote note) {
    this.notes.remove(note);
  }

  /**
   * To get a list represenation of the notes in the composition.
   *
   * @return a List containing all notes in the composition.
   */
  @Override
  public List<INote> getNotes() {
    return this.notes;
  }

  @Override
  public List<IEvent> getEvents() {
    return this.events;
  }

  @Override
  public INote getNoteAt(IPitch pitch, int timecode) {
    for (INote note : this.notes) {
      if (note.getPitch().equals(pitch) && (timecode >= note.getStart())
              && (timecode < note.getStart() + note.getDuration())) {
        return note;
      }
    }
    return null;
  }

  /**
   * To add a composition after this one has ended by adding all the notes to the end.
   *
   * @param comp the composition to be added.
   */
  @Override
  public void addComposition(IComposition comp) {
    int length = this.getLength();
    for (INote note : comp.getNotes()) {
      this.addNote(this.noteFactory.build(pitchFactory, note.getPitch().getPitch(),
              note.getStart() + length, note.getDuration(),
              note.getInstrument(), note.getVolume()));
    }
  }

  /**
   * To overlay a composition over this one by adding all notes to it one by one.
   *
   * @param comp the composition to be overlayed
   * @throws IllegalArgumentException If notes intersect with ones already present.
   */
  @Override
  public void overlayComposition(IComposition comp) throws IllegalArgumentException {
    boolean propagate = false;
    for (INote note : comp.getNotes()) {
      try {
        this.addNote(this.noteFactory.build(pitchFactory, note.getPitch().getPitch(),
                note.getStart(), note.getDuration(),
                note.getInstrument(), note.getVolume()));
      } catch (IllegalArgumentException e) {
        propagate = true;
      }
    }
    if (propagate) {
      throw new IllegalArgumentException("Some Notes could not be added since " +
              "they intersect with existing notes");
    }
  }

}
