package cs3500.music.conversion;

import cs3500.music.model.composition.IViewComposition;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.provider.view.IMusicEditorView;
import cs3500.music.view.IView;

/**
 * To adapt the view implementations of the providers.
 */
public class AdapterView implements IView {

  protected IPitchFactory pitchFactory;
  protected IViewComposition viewComposition;
  private IMusicEditorView aView;

  public AdapterView(IMusicEditorView aView, IPitchFactory pitchFactory) {
    this.aView = aView;
    this.pitchFactory = pitchFactory;
  }

  /**
   * Displays the currently known data.
   */
  @Override
  public void showDisplay() {
    aView.display();
  }

  /**
   * To take the ViewComposition.
   */
  @Override
  public void takeViewComposition(IViewComposition viewComposition) {
    this.viewComposition = viewComposition;
    aView.accessNotes(new ProviderViewModel(viewComposition, pitchFactory));
  }

  /**
   * Refreshed the View to display current Data.
   */
  @Override
  public void refresh() {
    //No Such functionality.
    this.takeViewComposition(this.viewComposition);
  }
}
