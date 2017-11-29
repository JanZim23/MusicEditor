package cs3500.music.model.composition;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.music.model.note.INote;
import cs3500.music.model.note.Note;
import cs3500.music.model.note.pitch.IPitch;
import cs3500.music.model.note.pitch.Pitch;
import cs3500.music.model.note.pitch.PitchQuality;
import cs3500.music.view.ConsoleView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Jan on 07.11.2016.
 */
public class CompositionTest {

  INote n1 = new Note(new Pitch(PitchQuality.A, 4), 0, 1);
  INote n2 = new Note(new Pitch(PitchQuality.C, 4), 2, 3);
  INote n3 = new Note(new Pitch(PitchQuality.B, 4), 4, 2);
  INote n4 = new Note(new Pitch(PitchQuality.F, 4), 1, 4);
  INote n5 = new Note(new Pitch(PitchQuality.E, 4), 10, 8);

  INote n6 = new Note(new Pitch(PitchQuality.FSharp, 5), 4, 5);
  INote n7 = new Note(new Pitch(PitchQuality.GSharp, 3), 7, 7);
  INote n8 = new Note(new Pitch(PitchQuality.E, 5), 5, 2);

  IComposition composition = new Composition(4, 10);
  //IMusicEditor musicEditor = new MusicEditor(composition);

  IComposition comp2 = new Composition(2, 10);
  //IMusicEditor edit2 = new MusicEditor(comp2);

  IPitch g3 = new Pitch(PitchQuality.G, 3);
  IPitch e4 = new Pitch(PitchQuality.E, 4);
  IPitch d4 = new Pitch(PitchQuality.D, 4);


  IComposition compositionExample = new Composition(4, 10);
  ConsoleView consoleView = new ConsoleView();
  //IMusicEditor musicEditorExample = new MusicEditor(compositionExample);

  INote[] exampleNotes = new INote[]{
          new Note(new Pitch(PitchQuality.G, 3), 0, 7),
          new Note(new Pitch(PitchQuality.G, 3), 8, 7),
          new Note(new Pitch(PitchQuality.G, 3), 16, 8),
          new Note(new Pitch(PitchQuality.G, 3), 24, 2),
          new Note(new Pitch(PitchQuality.G, 3), 32, 8),
          new Note(new Pitch(PitchQuality.G, 3), 40, 8),
          new Note(new Pitch(PitchQuality.G, 3), 48, 8),
          new Note(new Pitch(PitchQuality.E, 3), 56, 8),
          new Note(new Pitch(PitchQuality.C, 4), 4, 2),
          new Note(new Pitch(PitchQuality.E, 4), 0, 2),
          new Note(new Pitch(PitchQuality.D, 4), 2, 2),
          new Note(new Pitch(PitchQuality.D, 4), 6, 2),
          new Note(new Pitch(PitchQuality.E, 4), 8, 2),
          new Note(new Pitch(PitchQuality.E, 4), 10, 2),
          new Note(new Pitch(PitchQuality.E, 4), 12, 3),
          new Note(new Pitch(PitchQuality.D, 4), 16, 2),
          new Note(new Pitch(PitchQuality.D, 4), 18, 2),
          new Note(new Pitch(PitchQuality.D, 4), 20, 4),
          new Note(new Pitch(PitchQuality.E, 4), 24, 2),
          new Note(new Pitch(PitchQuality.G, 4), 26, 2),
          new Note(new Pitch(PitchQuality.G, 4), 28, 4),
          new Note(new Pitch(PitchQuality.E, 4), 32, 2),
          new Note(new Pitch(PitchQuality.D, 4), 34, 2),
          new Note(new Pitch(PitchQuality.C, 4), 36, 2),
          new Note(new Pitch(PitchQuality.D, 4), 38, 2),
          new Note(new Pitch(PitchQuality.E, 4), 40, 2),
          new Note(new Pitch(PitchQuality.E, 4), 44, 2),
          new Note(new Pitch(PitchQuality.E, 4), 42, 2),
          new Note(new Pitch(PitchQuality.E, 4), 46, 2),
          new Note(new Pitch(PitchQuality.D, 4), 48, 2),
          new Note(new Pitch(PitchQuality.D, 4), 50, 2),
          new Note(new Pitch(PitchQuality.E, 4), 52, 2),
          new Note(new Pitch(PitchQuality.D, 4), 54, 2),
          new Note(new Pitch(PitchQuality.C, 4), 56, 8)};


  @Test
  public void testExampleComposition() {
    this.compositionExample.addNotes(this.exampleNotes);
    this.consoleView.takeViewComposition(new ViewComposition(this.compositionExample));
    assertEquals(
            "    E3   F3  F#3   G3  G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   " +
                    "G4 \n" +
                    " 0                 X                                            X      " +
                    "           \n" +
                    " 1                 |                                            |      " +
                    "           \n" +
                    " 2                 |                                  X                " +
                    "           \n" +
                    " 3                 |                                  |                " +
                    "           \n" +
                    " 4                 |                        X                          " +
                    "           \n" +
                    " 5                 |                        |                          " +
                    "           \n" +
                    " 6                 |                                  X                " +
                    "           \n" +
                    " 7                                                    |                " +
                    "           \n" +
                    " 8                 X                                            X      " +
                    "           \n" +
                    " 9                 |                                            |      " +
                    "           \n" +
                    "10                 |                                            X      " +
                    "           \n" +
                    "11                 |                                            |      " +
                    "           \n" +
                    "12                 |                                            X      " +
                    "           \n" +
                    "13                 |                                            |      " +
                    "           \n" +
                    "14                 |                                            |      " +
                    "           \n" +
                    "15                                                                     " +
                    "           \n" +
                    "16                 X                                  X                " +
                    "           \n" +
                    "17                 |                                  |                " +
                    "           \n" +
                    "18                 |                                  X                " +
                    "           \n" +
                    "19                 |                                  |                " +
                    "           \n" +
                    "20                 |                                  X                " +
                    "           \n" +
                    "21                 |                                  |                " +
                    "           \n" +
                    "22                 |                                  |                " +
                    "           \n" +
                    "23                 |                                  |                " +
                    "           \n" +
                    "24                 X                                            X      " +
                    "           \n" +
                    "25                 |                                            |      " +
                    "           \n" +
                    "26                                                                     " +
                    "        X  \n" +
                    "27                                                                     " +
                    "        |  \n" +
                    "28                                                                     " +
                    "        X  \n" +
                    "29                                                                     " +
                    "        |  \n" +
                    "30                                                                     " +
                    "        |  \n" +
                    "31                                                                     " +
                    "        |  \n" +
                    "32                 X                                            X      " +
                    "           \n" +
                    "33                 |                                            |      " +
                    "           \n" +
                    "34                 |                                  X                " +
                    "           \n" +
                    "35                 |                                  |                " +
                    "           \n" +
                    "36                 |                        X                          " +
                    "           \n" +
                    "37                 |                        |                          " +
                    "           \n" +
                    "38                 |                                  X                " +
                    "           \n" +
                    "39                 |                                  |                " +
                    "           \n" +
                    "40                 X                                            X      " +
                    "           \n" +
                    "41                 |                                            |      " +
                    "           \n" +
                    "42                 |                                            X      " +
                    "           \n" +
                    "43                 |                                            |      " +
                    "           \n" +
                    "44                 |                                            X      " +
                    "           \n" +
                    "45                 |                                            |      " +
                    "           \n" +
                    "46                 |                                            X      " +
                    "           \n" +
                    "47                 |                                            |      " +
                    "           \n" +
                    "48                 X                                  X                " +
                    "           \n" +
                    "49                 |                                  |                " +
                    "           \n" +
                    "50                 |                                  X                " +
                    "           \n" +
                    "51                 |                                  |                " +
                    "           \n" +
                    "52                 |                                            X      " +
                    "           \n" +
                    "53                 |                                            |      " +
                    "           \n" +
                    "54                 |                                  X                " +
                    "           \n" +
                    "55                 |                                  |                " +
                    "           \n" +
                    "56  X                                       X                          " +
                    "           \n" +
                    "57  |                                       |                          " +
                    "           \n" +
                    "58  |                                       |                          " +
                    "           \n" +
                    "59  |                                       |                          " +
                    "           \n" +
                    "60  |                                       |                          " +
                    "           \n" +
                    "61  |                                       |                          " +
                    "           \n" +
                    "62  |                                       |                          " +
                    "           \n" +
                    "63  |                                       |                          " +
                    "           \n",
            this.consoleView.getState());
  }

  @Test
  public void testComposition() {

    consoleView.takeViewComposition(new ViewComposition(comp2));

    try {
      composition.getLowestPitch();
      fail();
    } catch (IllegalStateException e) {
      assertEquals("No Notes in composition", e.getMessage());
    }
    try {
      composition.getHighestPitch();
      fail();
    } catch (IllegalStateException e) {
      assertEquals("No Notes in composition", e.getMessage());
    }


    composition.addNote(n1);


    try {
      composition.addNote(n1);
      fail();
    } catch (IllegalArgumentException e) {
      assertEquals("A Note already exists at this location.", e.getMessage());
    }

    assertEquals(new Pitch(PitchQuality.A, 4), composition.getLowestPitch());
    assertEquals(new Pitch(PitchQuality.A, 4), composition.getLowestPitch());


    composition.addNote(n2);
    composition.addNote(n3);
    composition.addNote(n4);
    composition.addNote(n5);


    List<INote> notes = new ArrayList<>();
    notes.add(n1);
    notes.add(n2);
    notes.add(n3);
    notes.add(n4);
    notes.add(n5);

    //All Notes where added to the composition?
    assertEquals(notes, composition.getNotes());


    assertEquals(new Pitch(PitchQuality.C, 4), composition.getLowestPitch());
    assertEquals(new Pitch(PitchQuality.B, 4), composition.getHighestPitch());


    assertEquals(18, composition.getLength());
    assertEquals(4, composition.getMeasure());

    //Setting measure
    composition.setMeasure(5);
    assertEquals(5, composition.getMeasure());

    //Pitches exist at timecode
    assertEquals(true, null != composition.getNoteAt(new Pitch(PitchQuality.A, 4), 0));
    assertEquals(true, null != composition.getNoteAt(new Pitch(PitchQuality.C, 4), 3));
    assertEquals(true, null != composition.getNoteAt(new Pitch(PitchQuality.E, 4), 11));
    assertEquals(true, null != composition.getNoteAt(new Pitch(PitchQuality.E, 4), 10));
    assertEquals(true, null != composition.getNoteAt(new Pitch(PitchQuality.B, 4), 5));

    //Pitches exist but not at timecode
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.C, 4), 1));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.C, 4), 5));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.E, 4), 9));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.E, 4), 18));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.B, 4), 0));

    //Pitches dont exist
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.B, 3), 10));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.B, 3), 5));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.ASharp, 5), 5));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.E, 6), 6));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.FSharp, 0), 0));
    assertEquals(true, null == composition.getNoteAt(new Pitch(PitchQuality.B, 1), 0));


    assertEquals(n1, composition.getNoteAt(new Pitch(PitchQuality.A, 4), 0));

    composition.deleteNote(n1);


    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.A, 4), 0));
    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.A, 5), 7));

    //Off by one:
    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.C, 4), 1));

    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.C, 4), 5));
    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.E, 4), 9));
    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.E, 4), 18));

    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.CSharp, 4), 2));
    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.CSharp, 4), 4));

    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.B, 3), 4));
    int comp1length = composition.getLength();

    comp2.addNote(n6);
    comp2.addNote(n7);
    comp2.addNote(n8);


    assertEquals(
            "   G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  " +
                    "F#4   G4  G#4   A4  A#4   B4   C5  C#5   D5  D#5   E5   F5  F#5 \n" +
                    " 0                                          " +
                    "                                                                         \n" +
                    " 1                                          " +
                    "                                                                         \n" +
                    " 2                                          " +
                    "                                                                         \n" +
                    " 3                                          " +
                    "                                                                         \n" +
                    " 4                                          " +
                    "                                                                      X  \n" +
                    " 5                                          " +
                    "                                                            X         |  \n" +
                    " 6                                          " +
                    "                                                            |         |  \n" +
                    " 7  X                                       " +
                    "                                                                      |  \n" +
                    " 8  |                                       " +
                    "                                                                      |  \n" +
                    " 9  |                                       " +
                    "                                                                         \n" +
                    "10  |                                       " +
                    "                                                                         \n" +
                    "11  |                                       " +
                    "                                                                         \n" +
                    "12  |                                       " +
                    "                                                                         \n" +
                    "13  |                                       " +
                    "                                                                         \n",
            this.consoleView.getState());
    ConsoleView comp2ConsoleView = new ConsoleView();
    comp2ConsoleView.takeViewComposition(new ViewComposition(this.comp2));
    assertEquals(
            "   G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4  " +
                    " B4   C5  C#5   D5  D#5   E5   F5  F#5 \n" +
                    " 0                                                                         " +
                    "                                          \n" +
                    " 1                                                                         " +
                    "                                          \n" +
                    " 2                                                                         " +
                    "                                          \n" +
                    " 3                                                                         " +
                    "                                          \n" +
                    " 4                                                                         " +
                    "                                       X  \n" +
                    " 5                                                                         " +
                    "                             X         |  \n" +
                    " 6                                                                         " +
                    "                             |         |  \n" +
                    " 7  X                                                                      " +
                    "                                       |  \n" +
                    " 8  |                                                                      " +
                    "                                       |  \n" +
                    " 9  |                                                                      " +
                    "                                          \n" +
                    "10  |                                                                      " +
                    "                                          \n" +
                    "11  |                                                                      " +
                    "                                          \n" +
                    "12  |                                                                      " +
                    "                                          \n" +
                    "13  |                                                                      " +
                    "                                          \n"
            , comp2ConsoleView.getState());


    composition.addComposition(comp2);

    assertEquals(
            "   G#3   A3  A#3   B3   C4  C#4   D4  D#4   E4   " +
                    "F4  F#4   G4  G#4   A4  A#4   B4   C5  C#5   D5  D#5   E5   F5  F#5 \n" +
                    " 0                                           " +
                    "                                                                        \n" +
                    " 1                                           " +
                    "                                                                        \n" +
                    " 2                                           " +
                    "                                                                        \n" +
                    " 3                                           " +
                    "                                                                        \n" +
                    " 4                                           " +
                    "                                                                     X  \n" +
                    " 5                                           " +
                    "                                                           X         |  \n" +
                    " 6                                           " +
                    "                                                           |         |  \n" +
                    " 7  X                                        " +
                    "                                                                     |  \n" +
                    " 8  |                                        " +
                    "                                                                     |  \n" +
                    " 9  |                                        " +
                    "                                                                        \n" +
                    "10  |                                        " +
                    "                                                                        \n" +
                    "11  |                                       " +
                    "                                                                         \n" +
                    "12  |                                       " +
                    "                                                                         \n" +
                    "13  |                                       " +
                    "                                                                         \n",
            this.consoleView.getState());


    assertEquals(14, comp2.getLength());

    assertEquals(comp2.getLength() + comp1length, composition.getLength());

  }

  @Test
  public void testMusicEditor() {

    ConsoleView compositionConsoleView = new ConsoleView();
    compositionConsoleView.takeViewComposition(new ViewComposition(composition));

    assertEquals("", compositionConsoleView.getState());
    this.composition.addNotes(n5);
    assertEquals(
            "    E4 \n" +
                    " 0     \n" +
                    " 1     \n" +
                    " 2     \n" +
                    " 3     \n" +
                    " 4     \n" +
                    " 5     \n" +
                    " 6     \n" +
                    " 7     \n" +
                    " 8     \n" +
                    " 9     \n" +
                    "10  X  \n" +
                    "11  |  \n" +
                    "12  |  \n" +
                    "13  |  \n" +
                    "14  |  \n" +
                    "15  |  \n" +
                    "16  |  \n" +
                    "17  |  \n", compositionConsoleView.getState());

    assertEquals(n5, composition.getNoteAt(new Pitch(PitchQuality.E, 4), 11));
    assertEquals(n5, composition.getNoteAt(new Pitch(PitchQuality.E, 4), 10));
    assertEquals(n5, composition.getNoteAt(new Pitch(PitchQuality.E, 4), 17));
    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.E, 4), 9));
    assertEquals(null, composition.getNoteAt(new Pitch(PitchQuality.E, 5), 11));


    this.composition.deleteNote(n5);
    assertEquals("", compositionConsoleView.getState());

    //Add Composition

    comp2.addNotes(n1, n2);
    composition.addNotes(n3, n4);

    composition.addComposition(comp2);

    assertEquals(
            "    C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4 \n" +
                    " 0                                                            \n" +
                    " 1                           X                                \n" +
                    " 2                           |                                \n" +
                    " 3                           |                                \n" +
                    " 4                           |                             X  \n" +
                    " 5                                                         |  \n" +
                    " 6                                               X            \n" +
                    " 7                                                            \n" +
                    " 8  X                                                         \n" +
                    " 9  |                                                         \n" +
                    "10  |                                                         \n",
            compositionConsoleView.getState());

    composition.overlayComposition(comp2);
    assertEquals(
            "    C4  C#4   D4  D#4   E4   F4  F#4   G4  G#4   A4  A#4   B4 \n" +
                    " 0                                               X            \n" +
                    " 1                           X                                \n" +
                    " 2  X                        |                                \n" +
                    " 3  |                        |                                \n" +
                    " 4  |                        |                             X  \n" +
                    " 5                                                         |  \n" +
                    " 6                                               X            \n" +
                    " 7                                                            \n" +
                    " 8  X                                                         \n" +
                    " 9  |                                                         \n" +
                    "10  |                                                         \n",
            compositionConsoleView.getState());
  }


  @Test
  public void testNote() {
    assertEquals("A4", n1.toString());
    assertEquals(1, n1.getDuration());
    assertEquals(new Pitch(PitchQuality.A, 4), n1.getPitch());
    assertEquals(0, n1.getStart());
    assertEquals("C4", n2.toString());


  }

  @Test
  public void testPitch() {
    IPitch p1 = new Pitch(10);
    IPitch p2 = new Pitch(9993);
    IPitch p3 = new Pitch(PitchQuality.A, 0);
    IPitch p4 = new Pitch(PitchQuality.C, 0);
    IPitch p5 = new Pitch(PitchQuality.C, 1);

    assertEquals(10, p1.getPitch());
    assertEquals(9993, p2.getPitch());
    assertEquals(9, p3.getPitch());
    assertEquals(0, p4.getPitch());
    assertEquals(12, p5.getPitch());

    assertEquals("A#0", p1.toString());
    assertEquals("A832", p2.toString());
    assertEquals("A0", p3.toString());
    assertEquals("C0", p4.toString());
    assertEquals("C1", p5.toString());
  }
}