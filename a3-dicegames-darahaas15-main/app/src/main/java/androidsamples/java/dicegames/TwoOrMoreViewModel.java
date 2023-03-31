package androidsamples.java.dicegames;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.lifecycle.ViewModel;

/**
 * A {@link ViewModel} for the gambling game that allows the user to choose a game type, set a wager, and then play.
 */
public class TwoOrMoreViewModel extends ViewModel {

  private Integer coins;
  private Integer wager;
  private GameType type;
  public List<Die> dies;
  public Set<Integer> cntr;

  /**
   * No argument constructor.
   */
  public TwoOrMoreViewModel() {
    cntr = new HashSet<>();
    dies = new ArrayList<>();
    type = GameType.NONE;
    wager = null;
    coins = 0;
  }

  /**
   * Reports the current balance.
   *
   * @return the balance
   */
  public int balance() {
    return coins;
  }

  /**
   * Sets the balance to the given amount.
   *
   * @param balance the given amount
   */
  public void setBalance(int balance) {
    coins = balance;
  }

  /**
   * Reports the current game type as one of the values of the {@code enum} {@link GameType}.
   *
   * @return the current game type
   */
  public GameType gameType() {
    return type;
  }

  /**
   * Sets the current game type to the given value.
   *
   * @param gameType the game type to be set
   */
  public void setGameType(GameType gameType) {
    type = gameType;
  }

  /**
   * Reports the wager amount.
   *
   * @return the wager amount
   */
  public int wager() {
    return wager;
  }

  /**
   * Sets the wager to the given amount.
   *
   * @param wager the amount to be set
   */
  public void setWager(int wager) {
    this.wager = wager;
  }

  /**
   * Reports whether the wager amount is valid for the given game type and current balance.
   * For {@link GameType#TWO_ALIKE}, the balance must be at least twice as much, for {@link GameType#THREE_ALIKE}, at least thrice as much, and for {@link GameType#FOUR_ALIKE}, at least four times as much.
   * The wager must also be more than 0.
   *
   * @return {@code true} iff the wager set is valid
   */
  public boolean isValidWager() {
    if (wager ==null || wager <= 0)
      return false;
    else if (type == GameType.TWO_ALIKE && coins < 2 * wager)
      return false;
    else if (type == GameType.THREE_ALIKE && coins < 3 * wager)
      return false;
    else if (type == GameType.FOUR_ALIKE && coins < 4 * wager)
      return false;
    else return true;
  }

  /**
   * Returns the current values of all the dice.
   *
   * @return the values of dice
   */
  public List<Integer> diceValues() {
   List<Integer> values = new ArrayList<>();
    for (Die i : dies) {
      values.add(i.value());
    }
    return values;
  }

  /**
   * Adds the given {@link Die} to the game.
   *
   * @param d the Die to be added
   */
  public void addDie(Die d) {
    dies.add(d);
  }

  /**
   * Simulates playing the game based on the type and the wager and reports the result as one of the values of the {@code enum} {@link GameResult}.
   *
   * @return result of the current game
   * @throws IllegalStateException if the wager or the game type was not set to a proper value.
   */
  public GameResult play() throws IllegalStateException {
    // TODO implement method
    if (wager == null) {
      throw new IllegalStateException("Wager not set, can't play!");
    }
    if (type == GameType.NONE) {
      throw new IllegalStateException("Game Type not set, can't play!");
    }
    if (type == GameType.TWO_ALIKE && dies.size() < 2) {
      throw new IllegalStateException("Not enough dice, can't play!");
    }
    if (type == GameType.THREE_ALIKE && dies.size() < 3) {
      throw new IllegalStateException("Not enough dice, can't play!");
    }
    if (type == GameType.FOUR_ALIKE && dies.size() < 4) {
      throw new IllegalStateException("Not enough dice, can't play!");
    }

    for (Die i : dies) {
      i.roll();
    }
    for (Die d : dies) {
      cntr.add(d.value());
    }
    if (type == GameType.FOUR_ALIKE && cntr.size() > 1) {
      coins -= 4 * wager;
      return GameResult.LOSS;
    } else if (type == GameType.THREE_ALIKE && cntr.size() > 2) {
      coins -= 3 * wager;
      return GameResult.LOSS;
    } else if (type == GameType.TWO_ALIKE && cntr.size() > 3) {
      coins -= 2 * wager;
      return GameResult.LOSS;
    } else {
      if (type == GameType.TWO_ALIKE) {
        coins += 2 * wager;
        return GameResult.WIN;
      } else if (type == GameType.THREE_ALIKE) {
        coins += 3 * wager;
        return GameResult.WIN;
      } else if (type == GameType.FOUR_ALIKE) {
        coins += 4 * wager;
        return GameResult.WIN;
      }
    }
    return GameResult.WIN;
  }

  public void clearList() {
    dies.clear();
    cntr.clear();
  }
}