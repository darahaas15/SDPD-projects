package androidsamples.java.dicegames;

import androidx.lifecycle.ViewModel;

public class WalletViewModel extends ViewModel {

  public Die mDie;
  private Integer coins;
  private Integer prev_roll;
  private Integer temp;
  public Integer needed;
  public Integer add;

  /**
   * The no argument constructor.
   */
  public WalletViewModel() {

    // mDie=new Die6();
    setDie(null);
    setIncrement(5);
    setWinValue(6);
    setBalance(0);
    prev_roll = 0;
    temp = 0;
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
   * @param amount the new balance
   */
  public void setBalance(int amount) {
    coins = amount;
  }

  /**
   * Rolls the {@link Die} in the wallet.
   */
  public void rollDie() throws IllegalStateException {
    if (mDie == null) {
      throw new IllegalStateException();
    }
    prev_roll = temp;
    mDie.roll();
    if (mDie.value() == needed) {
      // if(prev_roll==needed){
      // coins+=add;
      // }
      coins += add;
    }
    // if (mDie.value()==prev_roll && mDie.value()!=needed){
    // coins-=add;
    // }
    temp = mDie.value();
  }

  /**
   * Reports the current value of the {@link Die}.
   *
   * @return current value of the die
   */
  public int dieValue() {
    return temp;
  }

  /**
   * Sets the increment value for earning in the wallet.
   *
   * @param increment amount to add to the balance
   */
  public void setIncrement(int increment) {
    add = increment;
  }

  /**
   * Sets the value which when rolled earns the increment.
   *
   * @param winValue value to be set
   */
  public void setWinValue(int winValue) {
    needed = winValue;
  }

  /**
   * Sets the {@link Die} to be used in this wallet.
   *
   * @param d the Die to use
   */
  public void setDie(Die d) {
    mDie = d;
  }
}
