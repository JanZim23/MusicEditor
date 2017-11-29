package cs3500.music.provider.model;

/**
 * Represents the enumerated pitch values a note can hold.
 */
public enum Pitch {
  C,

  Cs,

  D,

  Ds,

  E,

  F,

  Fs,

  G,

  Gs,

  A,

  As,

  B;

  /**
   * Returns this pitch as a String, replacing s with #.
   *
   * @return the String representation of this pitch
   */
  @Override
  public String toString() {
    return this.name().replace('s', '#');
  }


}
