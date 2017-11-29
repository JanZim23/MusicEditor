package cs3500.music.model.event.note.pitch;

/**
 * A Pitch Quality.
 */
public enum PitchQuality {
  C("C"), CSharp("C#"), D("D"), DSharp("D#"), E("E"), F("F"), FSharp("F#"), G("G"), GSharp("G#"),
  A("A"), ASharp("A#"), B("B");

  /**
   * To Represent the name of a Note.
   */
  private String name;

  /**
   * To Construct a PitchQuality.
   *
   * @param name the name of the note
   */
  PitchQuality(String name) {
    this.name = name;
  }

  /**
   * To get a pitch quality from an integer.
   *
   * @param pitch the pitch quality
   * @return a pitch quality representation of the pitch
   */
  public static PitchQuality pitchQualityFromInt(int pitch) {
    pitch = pitch % 12;
    switch (pitch) {
      case 0:
        return PitchQuality.C;
      case 1:
        return PitchQuality.CSharp;
      case 2:
        return PitchQuality.D;
      case 3:
        return PitchQuality.DSharp;
      case 4:
        return PitchQuality.E;
      case 5:
        return PitchQuality.F;
      case 6:
        return PitchQuality.FSharp;
      case 7:
        return PitchQuality.G;
      case 8:
        return PitchQuality.GSharp;
      case 9:
        return PitchQuality.A;
      case 10:
        return PitchQuality.ASharp;
      case 11:
        return PitchQuality.B;
      default:
        throw new IllegalArgumentException("Given pitch is out of bound: " + pitch);
    }
  }

  public String toString() {
    return this.name;
  }

  /**
   * To get an integer representation of the pitchquality independant of its octave.
   *
   * @return the integer representing the note quality.
   */
  public int toPitchInt() {
    switch (this) {
      case C:
        return 0;
      case CSharp:
        return 1;
      case D:
        return 2;
      case DSharp:
        return 3;
      case E:
        return 4;
      case F:
        return 5;
      case FSharp:
        return 6;
      case G:
        return 7;
      case GSharp:
        return 8;
      case A:
        return 9;
      case ASharp:
        return 10;
      case B:
        return 11;
      default:
        throw new IllegalStateException();
    }
  }
}
