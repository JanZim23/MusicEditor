package cs3500.music.view;

import cs3500.music.model.composition.IViewComposition;

/**
 * Basic interface for a View that displays a composition.
 */
public interface IView {

  /**
   * Displays the currently known data.
   */
  void showDisplay();

  /**
   * To take the ViewComposition.
   */
  void takeViewComposition(IViewComposition viewComposition);

  /**
   * Refreshed the View to display current Data.
   */
  void refresh();

}
