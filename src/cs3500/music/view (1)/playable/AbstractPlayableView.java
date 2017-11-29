package cs3500.music.view.playable;

import java.util.Timer;
import java.util.TimerTask;

import cs3500.music.model.composition.IViewComposition;

/**
 * Created by Jan on 09.12.2016.
 */
public abstract class AbstractPlayableView implements IPlayableView{


  /**
   * To have a non-changable Tick Rate in Milliseconds.
   */
  private static final long TICKRATE = 100;

  /**
   * A Timer to create scheduoled events.
   */
  protected Timer timer = new Timer();


  /**
   * To keep track of whether the playback is currently paused.
   */
  protected boolean paused;

  /**
   * The current syncronized microsecond location in the composition.
   */
  protected long currentMicros = 0;


  /**
   * The unmodifiable model to be represented by this View.
   */
  protected IViewComposition viewComposition;


   protected void startTicks() {
    this.timer = new Timer();
    this.timer.scheduleAtFixedRate(new TimerTask() {
      /**
       * The action to be performed by this timer task.
       */
      @Override
      public void run() {
        currentMicros = currentMicros + TICKRATE * 1000;
        takeCurrentMicros(currentMicros);
        if (currentMicros > viewComposition.getLength() * viewComposition.getTempo()) {
          timer.cancel();
        }

      }
    }, 0, TICKRATE);
  }

  /**
   * Takes the current displayable position in the composition in micros.
   *
   * @param micros microsecond location in composition.
   */

  protected void takeCurrentMicros(long micros) {
    this.currentMicros = micros;
  }

}
