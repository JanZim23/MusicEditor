package cs3500.music.model.event;

/**
 * Created by Jan on 09.12.2016.
 */
public class Section implements IEvent {

  private int start;
  private int duration;

  public Section(int start, int duration) {
    this.start = start;
    this.duration = duration;
  }

  @Override
  public int getStart() {
    return this.start;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }
}
