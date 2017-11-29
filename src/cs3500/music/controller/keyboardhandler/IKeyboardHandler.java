package cs3500.music.controller.keyboardhandler;

/**
 * An Interface for a Keyboard Handler.
 */
public interface IKeyboardHandler {

  /**
   * To Define a function to perform when a key is pressed.
   *
   * @param key      int representaiton of key.
   * @param function runnable to run on press.
   */
  void takeKeyboardFunction(int key, Runnable function);
}
