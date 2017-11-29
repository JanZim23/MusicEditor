package cs3500.music.conversion;

import java.util.ArrayList;
import java.util.TreeMap;

import cs3500.music.model.composition.IViewComposition;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.provider.model.Note;
import cs3500.music.provider.view.ViewModel;

/**
 * To mimic the tasks of the providers ViewModel.
 */
public class ProviderViewModel extends ViewModel {

  private IPitchFactory pitchFactory;
  private IViewComposition viewComposition;

  /**
   * To Construct a Provider view Model.
   * @param viewComposition the view comp to use.
   * @param pitchFactory the pitch factory to use.
   */
  public ProviderViewModel(IViewComposition viewComposition, IPitchFactory pitchFactory) {
    super(null);
    this.viewComposition = viewComposition;

    this.pitchFactory = pitchFactory;
  }

  /**
   * Gets this piece's map of integers to notes.
   *
   * @return this piece's map of integers to notes.
   */
  public TreeMap<Integer, ArrayList<Note>> getPiece() {

    TreeMap<Integer, ArrayList<Note>> treeMap = new TreeMap<>();

    for (INote note : this.viewComposition.getNotes()) {
      for (int i = 0; i < note.getDuration(); i++) {
        ArrayList<Note> current = treeMap.get(note.getStart() + i);
        if (current == null) {
          treeMap.put(note.getStart() + i, new ArrayList<>());
          current = treeMap.get(note.getStart() + i);
        }
        current.add(new ProviderNote(note, this.pitchFactory));
      }
    }

    return new TreeMap<>(treeMap);
  }

  /**
   * Gets this piece's tempo as an int.
   *
   * @return this piece's tempo
   */
  public int getTempo() {
    return this.viewComposition.getTempo();
  }

  /**
   * Obtains the lowest note in this piece.
   *
   * @return the lowest note in this piece
   */
  public Note getLowestNote() {
    return new ProviderNote(
            new cs3500.music.model.event.note.Note(pitchFactory, viewComposition.getLowestPitch(), 0, 0), pitchFactory);
  }

  /**
   * Obtains the highest note in this piece.
   *
   * @return the highest note in this piece
   */
  public Note getHighestNote() {
    return new ProviderNote(
            new cs3500.music.model.event.note.Note(pitchFactory, viewComposition.getHighestPitch(), 0, 0),
            pitchFactory);

  }

}
