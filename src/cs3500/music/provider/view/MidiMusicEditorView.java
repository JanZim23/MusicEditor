package cs3500.music.provider.view;

import java.util.ArrayList;

import javax.sound.midi.Instrument;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;

import cs3500.music.provider.model.Note;

/**
 * The midi view for the music editor. Plays the given song using java's midi.
 */
public class MidiMusicEditorView implements IMusicEditorView {

  private final Synthesizer synth;

  private final Receiver receiver;

  private ViewModel theModel;

  /**
   * Constructor for the midi view.
   */
  public MidiMusicEditorView() {
    Synthesizer tempSynth = null;
    Receiver tempReciever = null;

    try {
      tempSynth = MidiSystem.getSynthesizer();
      tempReciever = tempSynth.getReceiver();
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

    this.synth = tempSynth;

    try {
      if (this.synth != null) {
        this.synth.open();
      }
    } catch (MidiUnavailableException e) {
      e.printStackTrace();
    }

    this.receiver = tempReciever;
  }

  /**
   * Convenience constructor for tests.
   */

  public MidiMusicEditorView(Synthesizer synth, Receiver rec) {
    this.synth = synth;
    this.receiver = rec;
  }

  /**
   * Displays the given model in whatever way is designated by the concrete implementation of this
   * interface.
   */
  @Override
  public void display() {
    for (int curBeat = 0; curBeat < this.theModel.getPiece().lastKey(); curBeat++) {
      ArrayList<Note> notesAtBeat = this.theModel.getPiece().get(curBeat);
      try {
        this.playNotes(notesAtBeat, curBeat);
      } catch (InvalidMidiDataException e) {
        e.printStackTrace();
      }

      try {
        Thread.sleep(this.theModel.getTempo() / 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    this.receiver.close();
    this.synth.close();
  }

  /**
   * Plays all the notes at the given beat. Only plays the notes that start at that beat, sustained
   * notes do not need to be replayed.
   *
   * @param notes   a list of all the notes in this view
   * @param curBeat the current beat of the piece
   * @throws InvalidMidiDataException if the MidiMessage fails to send
   */
  void playNotes(ArrayList<Note> notes, int curBeat) throws InvalidMidiDataException {
    if (notes != null) {
      for (Note n : notes) {
        if (n.getStartBeat() == curBeat) {
          this.playNote(n);
        }
      }
    }
  }

  /**
   * Plays a single note by sending its start and stop messages to the receiver. Also handles
   * changing of instruments.
   *
   * @param n the note to be played
   * @throws InvalidMidiDataException if the MidiMessaage is invalid
   */
  private void playNote(Note n) throws InvalidMidiDataException {
    Instrument[] instruments = this.synth.getDefaultSoundbank().getInstruments();

    //EDIT: Removed -1 from n.getInstrument()
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON, Math.min(n.getInstrument(), 15),
            this.getMidiPitch(n), n.getVolume());
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF, Math.min(n.getInstrument(), 15),
            this.getMidiPitch(n), n.getVolume());

    // sets the instrument
    this.synth.getChannels()[Math.min(n.getInstrument() - 1, 15)].
            programChange(instruments[n.getInstrument() - 1].getPatch().getProgram());

    // sets the volume
    this.synth.getChannels()[Math.min(n.getInstrument() - 1, 15)].
            controlChange(7, n.getVolume());

    this.receiver.send(start, -1);
    this.receiver.send(stop,
            this.synth.getMicrosecondPosition() + (theModel.getTempo() * n.getLength()));
  }

  /**
   * Gets the information from the piece of music to use in this view.
   *
   * @param viewModel the viewModel to use for information
   */
  @Override
  public void accessNotes(ViewModel viewModel) {
    this.theModel = viewModel;
  }

  /**
   * Returns the given Note's midi pitch to use for this view.
   *
   * @param n the note to use to obtain its midi pitch
   * @return the respective midi pitch in the range [0 - 127] of the given note
   */
  private int getMidiPitch(Note n) {
    int midiPitch = n.getPitch().ordinal() + (12 * n.getOctave());
    if (midiPitch > 127 || midiPitch < 0) {
      throw new IllegalArgumentException("Malformed note for midi pitch.");
    } else {
      return midiPitch;
    }
  }
}
