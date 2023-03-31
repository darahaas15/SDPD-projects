package androidsamples.java.dicegames;

import android.util.Log;

import androidx.lifecycle.ViewModel;

public class WalletViewModel extends ViewModel {

  private static final String TAG = "WalletViewModel";
  private static final int INCREMENT = 5;
  private static final int BONUS = 10;
  private static final int WIN_VALUE = 6;

  private int mSingleSixCount;
  private int mTotalDiceCount;
  private int doubleSixCount;
  private int doubleOtherCount;

  private int mPreviousValue;

  private int mBalance;
  Die mDie;
  /**
   * The no argument constructor.
   */
  public WalletViewModel() {
    mDie = new Die6();
    mBalance = 0;
  }

  public int balance() {
    return mBalance;
  }

  /**
   * Rolls the {@link Die} in the wallet and implements the changes accordingly.
   */
  public void rollDie() {
    mPreviousValue = mDie.value();
    mDie.roll();
    Log.d(TAG, "Die rolled: " + mDie.value());
    if (mDie.value() == WIN_VALUE) {
      mBalance += INCREMENT;
      Log.d(TAG, "New balance: " + mBalance);
    }
  }

  /**
   * Reports the current value of the {@link Die}.
   *
   */
  public int dieValue() {
    return mDie.value();
  }

  /**
   * Reports the number of single (or first) sixes rolled so far.
   *
   */
  public int singleSixes() {
    //Everytime the die is rolled, check if the value is 6
    if (mDie.value() == WIN_VALUE) {
      mSingleSixCount++;
    }
    return mSingleSixCount;
  }

  /**
   * Reports the total number of dice rolls so far.
   *
   */
  public int totalRolls() {
    mTotalDiceCount++;
    return mTotalDiceCount;
  }

  /**
   * Reports the number of times two sixes were rolled in a row.
   *
   */
  public int doubleSixes() {
    if (mDie.value() == WIN_VALUE && mPreviousValue == WIN_VALUE) {
      doubleSixCount++;
      mBalance += BONUS;
    }
    return doubleSixCount;
  }

  /**
   * Reports the number of times any other number was rolled twice in a row.
   *
   */
  public int doubleOthers() {
    if (mDie.value() != WIN_VALUE && mPreviousValue != WIN_VALUE && mDie.value() == mPreviousValue) {
      doubleOtherCount++;
      mBalance -= INCREMENT;
    }
    return doubleOtherCount;
  }

  /**
   * Reports the value of the die on the previous roll.
   *
   */
  public int previousRoll() {
    return mPreviousValue;
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    Log.d(TAG, "onCleared");
  }
}
