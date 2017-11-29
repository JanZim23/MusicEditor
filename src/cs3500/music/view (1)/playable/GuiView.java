package cs3500.music.view.playable;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.music.model.composition.IViewComposition;
import cs3500.music.model.event.note.INote;
import cs3500.music.model.event.note.INoteFactory;
import cs3500.music.model.event.note.pitch.IPitch;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.model.event.note.pitch.PitchQuality;

/**
 * A GuiView that can display a composition and show its playback as well as have a form to add new
 * notes to the model.
 */
public class GuiView extends JFrame implements IGuiView {

  /**
   * Minimum width of window.
   */
  public static final int WINDOW_MIN_WIDTH = 800;

  /**
   * Maximum Width of Window.
   */
  public static final int WINDOW_MAX_WIDTH = 1200;

  /**
   * Maximum height of window.
   */
  public static final int WINDOW_MAX_HEIGHT = 800;

  /**
   * The Height of a single note bar.
   */
  public static final int NOTE_HEIGHT = 15;
  /**
   * The width of one beat.
   */
  public static final int NOTE_WIDTH = 10;

  /**
   * the padding of where the the note grid starts from the top.
   */
  public static final int TOP_PADDING = 65;

  /**
   * The padding of where the note grid starts from the left.
   */

  public static final int LEFT_PADDING = 50;
  /**
   * The GuiPanel to draw the composition graphics on.
   */
  private final JPanel displayPanel;
  /**
   * The calculated window height.
   */
  private int window_height;
  /**
   * The calculated window width.
   */
  private int window_width;
  /**
   * The ViewComposition to access read only data in the model.
   */
  private IViewComposition viewComposition;

  /**
   * The current micro second timecode of the current playback.
   */
  private long currentMicros;

  /**
   * The note in the view that should be highlighted.
   */
  private INote selected = null;

  /**
   * The current playback status.
   */
  private String status;

  /**
   * The Note creator quality selector component.
   */
  private JComboBox noteQualitySelector;

  /**
   * The note creator Octave Input Component.
   */
  private JTextField noteOctaveInput;

  /**
   * The note creator beat Input Component.
   */
  private JTextField noteBeatInput;

  /**
   * The note creator duration Input Component.
   */
  private JTextField noteDurationInput;

  /**
   * The note creator velocity Input Component.
   */
  private JTextField noteVelocityInput;

  /**
   * The note creator instrument Input Component.
   */
  private JTextField noteInstrumentInput;

  /**
   * The note creator submit button Component.
   */
  private JButton submitButton;

  /**
   * The scollpane to use as a scrolling component to host the composition.
   */
  private JScrollPane scrollPane;

  /**
   * Pitch Factory.
   */
  private IPitchFactory pitchFactory;

  /**
   * Creates new GuiView.
   *
   * @param pitchFactory The Pitch Factory used to created notes. The Objects created by this
   *                     factory will be used to get the Note Names through the notes toString()
   *                     method.
   */
  public GuiView(IPitchFactory pitchFactory) {
    this.pitchFactory = pitchFactory;

    this.displayPanel = new JPanel() {
      /**
       * To paint the component.
       *
       * @param g the graphics object to paint on.
       */
      @Override
      public void paintComponent(Graphics g) {
        // Handle the default painting
        super.paintComponent(g);
        drawGui(g);
      }
    };


    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    this.scrollPane = new JScrollPane(this.displayPanel);
    this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    this.getContentPane().add(scrollPane);
    this.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
    this.status = "Stopped";
    this.submitButton = new JButton("Add");
  }

  /**
   * To return the preferred window size.
   */
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(100, 100);
  }

  /**
   * Displays the currently known data.
   */
  @Override
  public void showDisplay() {
    if (this.viewComposition == null) {
      throw new IllegalStateException("ViewComposition Not Supplied");
    }
    this.setWindowConstraints();
    this.drawNoteCreator();
    this.pack();
    this.setFocusable(true);
    this.displayPanel.setFocusable(true);
    this.setVisible(true);
  }

  /**
   * To set the window and panel sizes to accuratly host the data in the current viewComposition.
   */
  private void setWindowConstraints() {
    int preferredWidth = LEFT_PADDING + NOTE_WIDTH * this.viewComposition.getLength();
    int preferredHeight = TOP_PADDING + (NOTE_HEIGHT + 1)
            * (this.viewComposition.getHighestPitch().getPitch()
            - this.viewComposition.getLowestPitch().getPitch());

    this.window_width = Math.min(WINDOW_MAX_WIDTH, Math.max(WINDOW_MIN_WIDTH, preferredWidth));
    this.window_height = Math.min(WINDOW_MAX_HEIGHT, preferredHeight);

    // Dimension object necessary for setting minimum size of the open window
    Dimension dimensions = new Dimension(this.window_width, this.window_height);

    Dimension preferredDimension = new Dimension(preferredWidth, preferredHeight);

    Dimension windowDimension = new Dimension(this.window_width + 20, this.window_height + 100);

    this.displayPanel.setPreferredSize(preferredDimension);

    this.scrollPane.setPreferredSize(dimensions);
    this.scrollPane.setSize(dimensions);

    this.setResizable(false);
    this.pack();
    this.setPreferredSize(windowDimension);
    this.setMinimumSize(windowDimension);
  }


  /**
   * To take the ViewComposition.
   */
  @Override
  public void takeViewComposition(IViewComposition viewComposition) {
    this.viewComposition = viewComposition;
  }

  /**
   * Refreshed the View to display current Data.
   */
  @Override
  public void refresh() {
    this.displayPanel.repaint();
    this.scrollPane.repaint();
    this.repaint();
    this.setWindowConstraints();
  }

  /**
   * To add a Key Listener to the GUI.
   *
   * @param listener the listener to call on key events
   */
  @Override
  public void takeKeyboardListener(KeyListener listener) {
    this.addKeyListener(listener);
    this.displayPanel.addKeyListener(listener);
  }

  /**
   * Takes a mouse handler.
   *
   * @param listener mouse handler to call on mouse events.
   */
  @Override
  public void takeMouseListener(MouseListener listener) {
    this.displayPanel.addMouseListener(listener);
  }

  /**
   * Get the note object drawn at the given coordinates.
   *
   * @param x x coordinate.
   * @param y y coordinate.
   * @return the note at the coordinates or null if none exists.
   */
  @Override
  public INote getNoteAt(int x, int y) {
    for (INote note : this.viewComposition.getNotes()) {
      int pitch = note.getPitch().getPitch();
      int noteX1 = LEFT_PADDING + note.getStart() * NOTE_WIDTH;
      int noteY1 = TOP_PADDING + (this.viewComposition.getHighestPitch().getPitch() - pitch)
              * NOTE_HEIGHT;

      int noteX2 = noteX1 + note.getDuration() * NOTE_WIDTH;
      int noteY2 = noteY1 + NOTE_HEIGHT;
      //System.out.println(noteX1 + " - " + noteX2 + " - " + noteY1 + " - " + noteY2);
      if (x < noteX2
              && x > noteX1
              && y < noteY2
              && y > noteY1) {
        return note;
      }
    }

    return null;
  }

  /**
   * Selects the given note visually.
   *
   * @param note the note to select.
   */
  @Override
  public void select(INote note) {
    this.selected = note;
    this.displayPanel.repaint();
  }

  /**
   * INote representative of the GUI Note Creator Note.
   *
   * @return an INote with the attributes of Note Creator
   * @throws IllegalArgumentException if a value entered is malformatted. HINT: Surround the method
   *                                  calling of this in try&catch and then call the {@link
   *                                  IGuiView#displayErrorMessage(String message)} with the given
   *                                  exception message.
   */
  @Override
  public INote getCreatorNote(INoteFactory noteFactory) throws IllegalArgumentException {
    if (!(this.noteQualitySelector.getSelectedItem() instanceof PitchQuality)) {
      throw new IllegalStateException();
    }
    IPitch pitch;
    try {
      pitch = this.pitchFactory.buildFromQuality((PitchQuality) this.noteQualitySelector.getSelectedItem(),
              Integer.parseInt(this.noteOctaveInput.getText()));
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid Pitch!");
    }
    int beat;
    try {
      beat = Integer.parseInt(this.noteBeatInput.getText());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid Start Beat");
    }
    int duration;
    try {
      duration = Integer.parseInt(this.noteDurationInput.getText());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid Duration");
    }
    int instrument;
    try {
      instrument = Integer.parseInt(this.noteInstrumentInput.getText());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid Instrument");
    }
    int velocity;
    try {
      velocity = Integer.parseInt(this.noteVelocityInput.getText());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid Velocity");
    }

    return noteFactory.build(pitchFactory, pitch.getPitch(), beat, duration, instrument, velocity);
  }

  @Override
  public void addNoteCreatorListener(ActionListener listener) {
    this.submitButton.addActionListener(listener);
  }

  @Override
  public void displayErrorMessage(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void focus() {
    this.requestFocus();
  }

  @Override
  public void scrollHorizontal(int amount) {
    this.scrollPane.getHorizontalScrollBar()
            .setValue(this.scrollPane.getHorizontalScrollBar().getValue() + amount * NOTE_WIDTH);
  }


  /**
   * Takes the current displayable position in the composition in micros.
   *
   * @param micros micros second location in composition.
   */
  protected void takeCurrentMicros(long micros) {
    this.currentMicros = micros;

    double timecode = (double) this.currentMicros / (double) this.viewComposition.getTempo();
    int x = LEFT_PADDING + (int) (timecode * NOTE_WIDTH);

    if (x > this.window_width + this.scrollPane.getHorizontalScrollBar().getValue()) {
      this.scrollPane.getHorizontalScrollBar().setValue(x);
    }
    this.displayPanel.repaint();
  }

  @Override
  public void setPlaybackPosition(int timecode) {
    this.takeCurrentMicros(timecode * this.viewComposition.getTempo());
  }


  @Override
  public void startPlayback() {
    this.status = "Playing";
  }

  @Override
  public void pausePlayback() {
    this.status = "Paused";
  }


  @Override
  public void resumePlayback() {
    this.status = "Playing";
  }

  /**
   * Draws the Gui On the given Graphics Object. Is called from the GuiPanel Class.
   * Only accesible in this package.
   *
   * @param graphics graphics object.
   */
  protected void drawGui(Graphics graphics) {
    this.drawState(graphics);
    this.drawCursorLine(graphics);
    this.drawMicroPosition(graphics);
    this.drawPlaybackStatus(graphics);
    this.drawWindowSize(graphics);
  }

  /**
   * Displays the window size.
   *
   * @param graphics graphics object.
   */
  private void drawWindowSize(Graphics graphics) {
    graphics.drawString("View Size: " + this.window_width + " x " + window_height, 5, 15);
  }

  /**
   * Displays the Note Creator form on the Gui.
   */
  private void drawNoteCreator() {
    //TODO
    JPanel noteCreator = new JPanel();

    JLabel pitchQualityLabel = new JLabel("Pitch Quality:");
    noteCreator.add(pitchQualityLabel);

    noteQualitySelector = new JComboBox(PitchQuality.values());
    noteCreator.add(noteQualitySelector);

    JLabel pitchOctaveLabel = new JLabel("Pitch Octave:");
    noteCreator.add(pitchOctaveLabel);

    noteOctaveInput = new JTextField(3);
    noteCreator.add(noteOctaveInput);

    JLabel pitchBeatLabel = new JLabel("Beat:");
    noteCreator.add(pitchBeatLabel);

    noteBeatInput = new JTextField(5);
    noteCreator.add(noteBeatInput);

    JLabel pitchDurationLabel = new JLabel("Duration:");
    noteCreator.add(pitchDurationLabel);

    noteDurationInput = new JTextField(5);
    noteCreator.add(noteDurationInput);

    JLabel pitchInstrumentLabel = new JLabel("Instrument:");
    noteCreator.add(pitchInstrumentLabel);

    noteInstrumentInput = new JTextField(5);
    noteCreator.add(noteInstrumentInput);

    JLabel pitchVelocityLabel = new JLabel("Velocity:");
    noteCreator.add(pitchVelocityLabel);

    noteVelocityInput = new JTextField(5);
    noteCreator.add(noteVelocityInput);

    noteCreator.add(submitButton);
    this.getContentPane().add(noteCreator);
  }

  /**
   * Draws the micro second position cursor on the gui.
   *
   * @param graphics graphics object.
   */
  private void drawMicroPosition(Graphics graphics) {
    graphics.setColor(Color.BLACK);
    graphics.drawString("Micro Seconds: " + this.currentMicros, 5, 45);
  }

  /**
   * Draws the playback status string.
   *
   * @param graphics graphics object.
   */
  private void drawPlaybackStatus(Graphics graphics) {
    graphics.setColor(Color.BLACK);
    graphics.drawString(this.status, 5, 30);
  }

  /**
   * In charge of drawing the state, beat by beat, from left to right.
   *
   * @param graphics The {@code Graphics} to be drawn on and returned
   */
  private void drawState(Graphics graphics) {
    this.drawPitchValues(graphics);
    this.drawHorizontalLines(graphics);

    for (INote note : this.viewComposition.getNotes()) {
      int pitch = note.getPitch().getPitch();
      int x = LEFT_PADDING + note.getStart() * NOTE_WIDTH;
      int y = TOP_PADDING + (this.viewComposition.getHighestPitch().getPitch() - pitch)
              * NOTE_HEIGHT;
      this.drawNote(x, y, note.getDuration(), note.equals(this.selected), graphics);
    }
    for (int i = 0; i <= this.viewComposition.getLength(); i++) {
      drawLine(i, graphics);
    }
  }

  /**
   * Draws a single note on the graphics object given its data.
   *
   * @param x        x-coordinate of the note.
   * @param y        y-coordinate of the note.
   * @param duration duration in beats of the note.
   * @param selected whether the note should be highlighted.
   * @param graphics graphics object.
   */
  private void drawNote(int x, int y, int duration, boolean selected, Graphics graphics) {
    graphics.setColor(Color.GREEN);
    graphics.fillRect(x, y, NOTE_WIDTH, NOTE_HEIGHT);
    if (duration > 0) {
      if (selected) {
        graphics.setColor(Color.CYAN);
      } else {
        graphics.setColor(Color.BLUE);
      }
      graphics.fillRect(x + NOTE_WIDTH, y, NOTE_WIDTH * (duration - 1), NOTE_HEIGHT);
    }
  }

  /**
   * Draws the red cursor line, signifying what notes are being played.
   *
   * @param graphics Graphics to be modified
   */
  private void drawCursorLine(Graphics graphics) {
    double timecode = (double) this.currentMicros / (double) this.viewComposition.getTempo();
    int x = LEFT_PADDING + (int) (timecode * NOTE_WIDTH);
    //System.out.print("Timecode:"+timecode+" x:"+x);
    int y2 = TOP_PADDING + NOTE_HEIGHT * (this.viewComposition.getHighestPitch().getPitch()
            - this.viewComposition.getLowestPitch().getPitch() + 1);
    graphics.setColor(Color.RED);
    graphics.drawLine(x, TOP_PADDING, x, y2);
  }

  /**
   * Called in the {@code drawStateAt} method, this method checks to see if a line should be
   * drawn at the given timecode, and draws it on the given {@code Graphics}.
   *
   * @param timecode the given beat
   * @param graphics the {@code Graphics} to be drawn on
   */
  private void drawLine(int timecode, Graphics graphics) {
    if (timecode % viewComposition.getMeasure() == 0) {
      int x1 = LEFT_PADDING + timecode * NOTE_WIDTH;
      int y1 = TOP_PADDING;
      int x2 = LEFT_PADDING + timecode * NOTE_WIDTH;
      int y2 = TOP_PADDING + NOTE_HEIGHT * (this.viewComposition.getHighestPitch().getPitch()
              - this.viewComposition.getLowestPitch().getPitch() + 1);
      graphics.setColor(Color.BLACK);
      graphics.drawLine(x1, y1, x2, y2);
      graphics.drawString(Integer.toString(timecode), x1, y1 - 5);
    }
  }

  /**
   * Draws the pitch values on the left pane.
   *
   * @param graphics {@code Graphics} to be drawn on top of
   */
  private void drawPitchValues(Graphics graphics) {
    for (int i = this.viewComposition.getLowestPitch().getPitch();
         i <= this.viewComposition.getHighestPitch().getPitch(); i++) {
      String pitch = this.pitchFactory.build(i).toString();
      int x = LEFT_PADDING / 2;
      int y = TOP_PADDING + ((this.viewComposition.getHighestPitch().getPitch() - i) * NOTE_HEIGHT)
              + 15;
      graphics.setColor(Color.BLACK);
      graphics.drawString(pitch, x, y);
    }
  }

  /**
   * Draws the horizontal lines necessary to differentiate each note from each other.
   *
   * @param graphics {@code Graphics} object to have lines added to it
   */
  private void drawHorizontalLines(Graphics graphics) {
    for (int i = 0; i < this.viewComposition.getHighestPitch().getPitch()
            - this.viewComposition.getLowestPitch().getPitch() + 2;
         i += 1) {
      int x1 = LEFT_PADDING;
      int y1 = TOP_PADDING + i * this.NOTE_HEIGHT;
      int x2 = LEFT_PADDING + this.viewComposition.getLength() * NOTE_WIDTH;
      int y2 = y1;
      graphics.drawLine(x1, y1, x2, y2);
      graphics.setColor(Color.BLACK);
    }
  }

}
