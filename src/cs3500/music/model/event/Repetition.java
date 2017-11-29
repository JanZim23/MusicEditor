package cs3500.music.model.event;

/**
 * Created by Jan on 09.12.2016.
 */
public class Repetition implements IRepetition {

  private int start;

  private int duration;

  private IEvent[] sections;

  public Repetition(int start, int duration, IEvent...sections) {
    this.start = start;
    this.duration = duration;
    this.sections = sections;
  }

  @Override
  public IEvent getSection(int i) {
    return null;
  }

  @Override
  public int getSectionCount() {
    return this.sections.length;
  }

  @Override
  public int getStart() {
    return this.start;
  }

  @Override
  public int getDuration() {
    return this.getDuration();
  }
}
