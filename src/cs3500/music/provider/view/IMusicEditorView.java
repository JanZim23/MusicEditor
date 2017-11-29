package cs3500.music.provider.view;

/**
 * An interface for the view of a music editor. Has a method to display the music editor
 * based on a given model that will differ based on the implementation of this interface.
 */
public interface IMusicEditorView {

  /**
   * Displays the given model in whatever way is designated by the concrete implementation of this
   * interface.
   */
  void display();

  /**
   * Gets the information from the piece of music to use in this view.
   *
   * @param viewModel the viewModel to use for information
   */
  void accessNotes(ViewModel viewModel);

}