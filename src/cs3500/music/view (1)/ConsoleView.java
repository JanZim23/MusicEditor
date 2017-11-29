package cs3500.music.view;

import cs3500.music.model.composition.IViewComposition;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.pitch.IPitch;
import cs3500.music.model.event.note.pitch.IPitchFactory;

/**
 * Console View.
 */
public class ConsoleView implements IView {

  /**
   * The charackter to use on line breaks.
   */
  private static final String LINE_DELIMETER = "\n";
  private IPitchFactory pitchFactory;

  /**
   * to keep track of the read only model.
   */
  private IViewComposition viewComposition;

  public ConsoleView(IPitchFactory pitchFactory) {
    this.pitchFactory = pitchFactory;
  }

  /**
   * Displays the currently known data.
   */
  @Override
  public void showDisplay() {
    if (this.viewComposition == null) {
      throw new IllegalStateException("ViewComposition Not Supplied");
    }
    System.out.println(this.getState());
  }

  @Override
  public void takeViewComposition(IViewComposition viewComposition) {
    this.viewComposition = viewComposition;
  }

  /**
   * Refreshed the View to display current Data.
   */
  @Override
  public void refresh() {
    showDisplay();
  }

  /**
   * To get the minimum length of the timecode for displaying.
   *
   * @return the amount of digits in the composition length.
   */
  private int timecodeMinLength() {
    return (this.viewComposition.getLength() + "").length(); //I hack sometimes
  }

  /**
   * To get a String representation of the Compositions state.
   *
   * @return a string representing all notes in the composition.
   */
  public String getState() {
    if (this.viewComposition.getLength() == 0) {
      return "";
    }
    String output = this.getHeader() + LINE_DELIMETER;
    for (int i = 0; i < this.viewComposition.getLength(); i++) {
      output = output + this.getStateAt(i) + LINE_DELIMETER;
    }
    return output;
  }

  /**
   * To get the heading line of text with note labels for state output.
   *
   * @return String list of notes between highest and lowest.
   */
  private String getHeader() {
    String output = "";
    for (int i = 0; i < this.timecodeMinLength(); i++) {
      output = " " + output;
    }

    for (int i = this.viewComposition.getLowestPitch().getPitch();
         i <= this.viewComposition.getHighestPitch().getPitch(); i++) {
      String pitch = this.pitchFactory.build(i).toString();
      if (pitch.length() == 3) {
        output = output + " " + pitch + " ";
      } else if (pitch.length() == 2) {
        output = output + "  " + pitch + " ";
      } else {
        throw new IllegalStateException("Invalid Length of Note Identification");
      }
    }
    return output;
  }

  /**
   * Get the state of the composition at a given time index.
   *
   * @param timecode the timecode to check at.
   * @return A string representation of the state at that timecode,
   */
  private String getStateAt(int timecode) {
    String output = "";
    for (int i = (timecode + "").length(); i < this.timecodeMinLength(); i++) {
      output = " " + output;
    }
    output = output + timecode;
    for (int i = this.viewComposition.getLowestPitch().getPitch();
         i <= this.viewComposition.getHighestPitch().getPitch(); i++) {
      IPitch pitch = pitchFactory.build(i);
      output = output + "  ";
      INote noteAt = this.viewComposition.getNoteAt(pitch, timecode);
      if (noteAt != null) {
        if (noteAt.getStart() == timecode) {
          output = output + "X";
        } else {
          output = output + "|";
        }
      } else {
        output = output + " ";
      }
      output = output + "  ";
    }
    return output;
  }


}
