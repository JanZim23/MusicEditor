package cs3500.music.controller;

import cs3500.music.model.composition.IComposition;
import cs3500.music.model.composition.ViewComposition;
import cs3500.music.view.IView;
import cs3500.music.view.playable.IPlayableView;

/**
 * Created by Christian on 11/1/2016.
 * Basic Controller with functionality to deliver ViewComposition and launch a model with a view.
 */
public class BasicController implements IController {

  /**
   * To keep track of the Model.
   */
  private IComposition model;

  /**
   * To keep track of the view beeing used.
   */
  private IView view;

  /**
   * To Construct a basic controller with a view and Model.
   */
  public BasicController(IComposition model, IView view) {
    this.model = model;
    this.view = view;
  }

  /**
   * Run the Model and View.
   */
  @Override
  public void run() {
    view.takeViewComposition(new ViewComposition(this.model));
    view.showDisplay();

    if (this.view instanceof IPlayableView) {
      ((IPlayableView) this.view).startPlayback();
    }

  }
}
