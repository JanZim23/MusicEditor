package cs3500.music.controller.keyboardhandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

/**
 * A Keyboard handler that can have functions installed associated with certain KeyCodes.
 */
public class KeyboardHandler implements KeyListener, IKeyboardHandler {

  private HashMap<Integer, Runnable> listeners = new HashMap<>();

  /**
   * Invoked when a key has been typed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key typed event.
   */
  @Override
  public void keyTyped(KeyEvent e) {
    //Nothing Should Happen Here. Our Handler is Only concerned with Pressed Keys.
  }

  /**
   * Invoked when a key has been pressed.
   * See the class description for {@link KeyEvent} for a definition of
   * a key pressed event.
   */
  @Override
  public void keyPressed(KeyEvent e) {
    System.out.println("Key Pressed:" + e.getKeyCode());
    if (this.listeners.keySet().contains(e.getKeyCode())) {
      listeners.get(e.getKeyCode()).run();
    }
  }

  /**
   * Invoked when a key has been released.
   * See the class description for {@link KeyEvent} for a definition of
   * a key released event.
   */
  @Override
  public void keyReleased(KeyEvent e) {
    //Nothing Should Happen Here. Our Handler is only concerned with Pressed Keys.
  }

  /**
   * Define a function for a key beeing pressed,
   *
   * @param key      int representaiton of key.
   * @param function runnable to run on press.
   */
  @Override
  public void takeKeyboardFunction(int key, Runnable function) {
    this.listeners.put(key, function);
  }
}
