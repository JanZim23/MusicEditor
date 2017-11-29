package cs3500.music.model.event.note;

import cs3500.music.model.event.IEvent;
import cs3500.music.model.event.note.pitch.IPitch;

/**
 * To Represent a Note in a composition with pitch, start and duration, instrument and Volume.
 * Tightly Coupled with IPitch.
 * Factory: INoteFactory
 * To get started in Connecting this View To another Model you will need to create an
 * Implementation of this Interface. Ideally you will want an Implementation that satisfies your
 * interface for a Note as Well as ours. If this doesnt work since methods you declared have
 * different parameters or return types then you will have to create an adapter.
 * HINT: Write an implementation for INoteFactory that creates an INote of you type, our view
 * can then use that factory to create INotes for you!
 */
public interface INote extends IEvent {

  /**
   * To get the object representation of the pitch of the Note. Steps from C0.
   * .
   *
   * @return the pitch of the note.
   */
  IPitch getPitch();

  /**
   * To get the start timecode of the note in the composition.
   *
   * @return the int represeing the start timecode.
   */
  int getStart();

  /**
   * To get the duration of the note in beats.
   *
   * @return the duration.
   */
  int getDuration();

  /**
   * To get the instrument value of this note.
   *
   * @return the instrument number
   */
  int getInstrument();


  /**
   * To get the volume.
   *
   * @return volume of this note.
   */
  int getVolume();

}
