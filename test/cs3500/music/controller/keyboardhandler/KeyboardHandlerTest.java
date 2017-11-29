package cs3500.music.controller.keyboardhandler;

import org.junit.Test;

import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import static junit.framework.TestCase.assertTrue;


/**
 * Class that tests the KeyboardHandler class.
 */
public class KeyboardHandlerTest {

  private boolean wasRun;

  @Test
  public void testKeyboardHandler() {
    KeyboardHandler handler = new KeyboardHandler();


    for (int i = 0; i < 100; i++) {
      wasRun = false;

      handler.takeKeyboardFunction(i, new Runnable() {
        @Override
        public void run() {
          wasRun = true;
        }
      });

      handler.keyPressed(new KeyEvent(new JPanel(), 0, (long) 0, 0, i));

      assertTrue(wasRun);
    }
  }



}