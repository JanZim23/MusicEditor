package cs3500.music.view;

import org.junit.Test;

import cs3500.music.view.playable.MidiView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class for testing the Midi View of our implementation.
 */
public class MidiViewTest {

  @Test
  public void testMidiException() {

    IView midiView = new MidiView();

    try {
      midiView.showDisplay();
      fail();
    } catch (IllegalStateException e) {
      assertEquals(e.getMessage(), "ViewComposition Not Supplied");
    }
  }

}
