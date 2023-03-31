package androidsamples.java.dicegames;

import java.util.Random;

/**
 * An implementation of a six-faced {@link Die} using {@link Random}.
 */
public class Die6 implements Die {
  Random rand;
  int value;

  public Die6() {
    rand = new Random();
  }

  @Override
  public void roll() {
    value = 1 + rand.nextInt(6);
  }

  @Override
  public int value() {
    return value;
  }
}
