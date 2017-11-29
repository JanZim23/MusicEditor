package cs3500.music.model.event;

/**
 * Created by Jan on 09.12.2016.
 */
public interface IRepetition extends IEvent{

  IEvent getSection(int i);

  int getSectionCount();
}
