import java.io.FileNotFoundException;
import java.io.FileReader;

import cs3500.music.builder.CompositionBuilder;
import cs3500.music.builder.CompositionFactory;
import cs3500.music.builder.MusicReader;
import cs3500.music.controller.BasicController;
import cs3500.music.controller.IController;
import cs3500.music.controller.InputController;
import cs3500.music.conversion.AdapterGuiView;
import cs3500.music.conversion.AdapterView;
import cs3500.music.model.composition.IComposition;
import cs3500.music.model.event.note.INoteFactory;
import cs3500.music.model.event.note.NoteFactoryImpl;
import cs3500.music.model.event.note.pitch.IPitchFactory;
import cs3500.music.model.event.note.pitch.PitchFactoryImpl;
import cs3500.music.provider.view.CombinedView;
import cs3500.music.provider.view.MidiMusicEditorView;
import cs3500.music.provider.view.TextMusicEditorView;
import cs3500.music.provider.view.VisualMusicEditorView;
import cs3500.music.view.ConsoleView;
import cs3500.music.view.IView;
import cs3500.music.view.playable.GuiAudioView;
import cs3500.music.view.playable.GuiView;
import cs3500.music.view.playable.IGuiView;
import cs3500.music.view.playable.MidiView;

/**
 * Main Class to run the Programm.
 */
public class Main {

  /**
   * Launch the Programm.
   *
   * @param args arguments to run the programm: argument[0] should be the filename and path relative
   *             to the programm argument[1] should be the name of the view These could be:
   *             midi,gui,guiaudio,console
   */
  public static void main(String[] args) {

    IPitchFactory pitchFactory = new PitchFactoryImpl();
    INoteFactory noteFactory = new NoteFactoryImpl();

    IComposition modelRead;

    CompositionBuilder<IComposition> factory = new CompositionFactory(noteFactory, pitchFactory);
    MusicReader reader = new MusicReader();
    Readable fileReader = null;
    try {
      fileReader = new FileReader(args[0]);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    modelRead = reader.parseFile(fileReader, factory);

    IView view;
    IController controller;
    if (args[2].equalsIgnoreCase("original")) {
      if (args[1].equalsIgnoreCase("midi")) {

        view = new MidiView(true, pitchFactory);

      } else if (args[1].equalsIgnoreCase("gui")) {

        view = new GuiView(pitchFactory);

      } else if (args[1].equalsIgnoreCase("guiaudio")) {

        view = new GuiAudioView(new GuiView(pitchFactory), new MidiView(false, pitchFactory));

      } else if (args[1].equalsIgnoreCase("console")) {

        view = new ConsoleView(pitchFactory);

      } else {
        throw new IllegalArgumentException("Invalid View Name");
      }
    } else if (args[2].equalsIgnoreCase("provider")) {
      if (args[1].equalsIgnoreCase("midi")) {

        view = new AdapterView(new MidiMusicEditorView(), pitchFactory);

      } else if (args[1].equalsIgnoreCase("gui")) {

        view = new AdapterView(new VisualMusicEditorView(), pitchFactory);

      } else if (args[1].equalsIgnoreCase("guiaudio")) {

        view = new AdapterGuiView(new CombinedView(new VisualMusicEditorView(),
                new MidiMusicEditorView()), pitchFactory);

      } else if (args[1].equalsIgnoreCase("console")) {

        view = new AdapterView(new TextMusicEditorView(), pitchFactory);

      } else {
        throw new IllegalArgumentException("Invalid View Name");
      }
    } else {
      throw new IllegalArgumentException("Invalid View Package");
    }

    if (view instanceof IGuiView) {
      controller = new InputController(modelRead, (IGuiView) view);
    } else {
      controller = new BasicController(modelRead, view);
    }

    controller.run();


  }
}
