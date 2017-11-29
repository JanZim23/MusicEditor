package cs3500.music.view.playable;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.model.composition.IViewComposition;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.pitch.IPitchFactory;

/**
 * A skeleton for MIDI playback.
 */
public class MidiView extends AbstractPlayableView implements IPlayableView {
  private IViewComposition viewComposition;

  private Synthesizer synth;
  private Receiver receiver;

  private long currentMicros;

  private boolean standAlone;

  private IPitchFactory pitchFactory;

  /**
   * To construct a MidiView.
   * Protected because you should be using the other constructor
   */
  protected MidiView(IPitchFactory pitchFactory) {
    this.initSynthReceiver();
    this.currentMicros = 0;
    this.standAlone = false;
    this.pitchFactory = pitchFactory;
  }

  /**
   * To construct a MidiView.
   *
   * @param standAlone if true, the MidiView will suspend the thread so long as the composition is
   *                   playing. If false, another part of the program will have to do this or else
   *                   the program will close before any notes can be played.
   */
  public MidiView(boolean standAlone, IPitchFactory pitchFactory) {
    this.initSynthReceiver();
    this.currentMicros = 0;
    this.standAlone = standAlone;
    this.pitchFactory = pitchFactory;
  }

  /**
   * Will Reset the Synth and Reciever. This will allow for a new stream of notes to be added to
   * the midi reciever.
   */
  private void initSynthReceiver() {
    try {
      this.synth = MidiSystem.getSynthesizer();
      this.receiver = synth.getReceiver();
      this.synth.open();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }
  }

  /**
   * To play all of the notes in the given viewComposition.
   */
  private void playNotes(long starttime) {
    MidiChannel[] midiChannels = this.synth.getChannels();
    Instrument[] instruments = this.synth.getDefaultSoundbank().getInstruments();
    midiChannels[0].programChange(0);
    this.synth.loadInstrument(instruments[0]);
    for (INote note : viewComposition.getNotes()) {
      long noteStarttime = this.beatToMicroSecond(note.getStart());
      if (noteStarttime < starttime) {
        continue;
      }
      this.playNote(noteStarttime - starttime, note.getPitch().getPitch(),
              note.getInstrument(), note.getVolume(), this.beatToMicroSecond(note.getDuration()));
    }
    this.receiver.close();


    if (this.standAlone) {
      try {
        Thread.sleep((this.beatToMicroSecond(this.viewComposition.getLength()) - starttime) / 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Relevant classes and methods from the javax.sound.midi library:
   * <ul>
   * <li>{@link MidiSystem#getSynthesizer()}</li>
   * <li>{@link Synthesizer}
   * <ul>
   * <li>{@link Synthesizer#open()}</li>
   * <li>{@link Synthesizer#getReceiver()}</li>
   * <li>{@link Synthesizer#getChannels()}</li>
   * </ul>
   * </li>
   * <li>{@link Receiver}
   * <ul>
   * <li>{@link Receiver#send(MidiMessage, long)}</li>
   * <li>{@link Receiver#close()}</li>
   * </ul>
   * </li>
   * <li>{@link MidiMessage}</li>
   * <li>{@link ShortMessage}</li>
   * <li>{@link MidiChannel}
   * <ul>
   * <li>{@link MidiChannel#getProgram()}</li>
   * <li>{@link MidiChannel#programChange(int)}</li>
   * </ul>
   * </li>
   * </ul>
   *
   * @see <a href="https://en.wikipedia.org/wiki/General_MIDI">
   *   https://en.wikipedia.org/wiki/General_MIDI
   * </a>
   */
  /**
   * To play a note with the given data.
   *
   * @param starttime microsecs at which the note starts
   * @param pitch     the pitch of the note
   * @param velocity  the velocity of the note (volume)
   * @param duration  the duration in micros of the note.
   */
  public void playNote(long starttime, int pitch, int instrument, int velocity, long duration) {
    //ShortMessage(Command, Channel, Pitch, Velocity)
    try {
      MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, instrument, pitch, velocity);
      MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, instrument, pitch, velocity);
      this.receiver.send(start, starttime);
      this.receiver.send(stop, starttime + duration);
    } catch (InvalidMidiDataException e) {
      throw new IllegalArgumentException("Invalid note playback data given.");
    }
  }

  private long beatToMicroSecond(int beat) {
    return viewComposition.getTempo() * beat;
  }

  /**
   * Displays the currently known data.
   */
  @Override
  public void showDisplay() {
    if (this.viewComposition == null) {
      throw new IllegalStateException("ViewComposition Not Supplied");
    }
  }

  /**
   * To take the ViewComposition.
   */
  @Override
  public void takeViewComposition(IViewComposition viewComposition) {
    this.viewComposition = viewComposition;
  }

  /**
   * Refreshed the View to display current Data.
   */
  @Override
  public void refresh() {
    resumePlayback();
  }

  /**
   * Takes the current displayable position in the composition in micros.
   *
   * @param micros micros second location in composition.
   */
  protected void takeCurrentMicros(long micros) {
    this.currentMicros = micros;
  }


  @Override
  public void setPlaybackPosition(int timecode) {
    this.takeCurrentMicros(timecode * this.viewComposition.getTempo());
  }

  /**
   * Starts playback at timecode 0.
   */
  @Override
  public void startPlayback() {
    this.pausePlayback();
    this.takeCurrentMicros(0);
    this.playNotes(this.currentMicros);
  }

  /**
   * Pauses playback at current timecode.
   */
  @Override
  public void pausePlayback() {
    this.synth.close();
    this.initSynthReceiver();
  }

  /**
   * Resumes playback at current timecode.
   */
  @Override
  public void resumePlayback() {
    this.synth.close();
    this.initSynthReceiver();
    this.playNotes(this.currentMicros);
  }
}
