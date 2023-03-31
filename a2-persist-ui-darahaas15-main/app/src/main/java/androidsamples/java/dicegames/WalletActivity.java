package androidsamples.java.dicegames;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.app.AppCompatActivity;



public class WalletActivity extends AppCompatActivity {

  private static final String TAG = "WalletActivity";

  private TextView mTxtBalance;
  private TextView mTxtSingleSixCount;
  private TextView mTxtTotalDiceCount;
  private TextView mPreviousValue;
  private TextView mTxtDoubleSixCount;
  private TextView mTxtDoubleOtherCount;
  private WalletViewModel mWalletVM;
  private Button mBtnDie;

  private static final int SHORT_DELAY = 500; // 0.5 seconds

  private static final String KEY_BALANCE = "KEY_BALANCE";
  private static final String KEY_DIE_VALUE = "KEY_DIE_VALUE";


  private final int WIN_VALUE = 6;
  private final int INCREMENT = 5;

  public void showToastMessage(String text, int duration){
    final Toast toast = Toast.makeText(WalletActivity.this, text, Toast.LENGTH_SHORT);
    toast.show();
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
      @Override
      public void run() {
        toast.cancel();
      }
    }, duration);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wallet);

    mTxtBalance = findViewById(R.id.txt_coins);
    mTxtSingleSixCount = findViewById(R.id.txt_single_sixes);
    mTxtTotalDiceCount = findViewById(R.id.txt_total_rolls);
    mPreviousValue = findViewById(R.id.txt_prev_roll);
    mTxtDoubleSixCount = findViewById(R.id.txt_double_sixes);
    mTxtDoubleOtherCount = findViewById(R.id.txt_double_others);
    mBtnDie = (Button) findViewById(R.id.btn_die);


    mWalletVM = new ViewModelProvider(this).get(WalletViewModel.class);

    //Update UI
    mTxtBalance.setText(Integer.toString(mWalletVM.balance()));
    mBtnDie.setText(Integer.toString(mWalletVM.dieValue()));
    mTxtSingleSixCount.setText(Integer.toString(mWalletVM.singleSixes()));
    mTxtTotalDiceCount.setText(Integer.toString(mWalletVM.totalRolls()));
    mPreviousValue.setText(Integer.toString(mWalletVM.previousRoll()));
    mTxtDoubleSixCount.setText(Integer.toString(mWalletVM.doubleSixes()));
    mTxtDoubleOtherCount.setText(Integer.toString(mWalletVM.doubleOthers()));

  /*  if (savedInstanceState != null) {
      mBalance = savedInstanceState.getInt(KEY_BALANCE);
      int dieValue = savedInstanceState.getInt(KEY_DIE_VALUE);
      mTxtBalance.setText(Integer.toString(mBalance));
      mBtnDie.setText(Integer.toString(dieValue));
      Log.d(TAG, "Restored balance = " + mBalance + " and die value = " + dieValue);
    } */

    mBtnDie.setOnClickListener(new View.OnClickListener() {

      @SuppressLint("SetTextI18n")
      @Override
      public void onClick(View v) {
        mWalletVM.rollDie();
        if (mWalletVM.dieValue() == WIN_VALUE) {
          showToastMessage("Congratulations!", 500);
        }

        if (mWalletVM.dieValue() == WIN_VALUE && mWalletVM.previousRoll() == WIN_VALUE) {
          showToastMessage("Double Six! :)", 500);
        }

        if (mWalletVM.dieValue() != WIN_VALUE && mWalletVM.previousRoll() != WIN_VALUE && mWalletVM.dieValue() == mWalletVM.previousRoll()) {
          showToastMessage("Double " + mWalletVM.dieValue() + " :(", 500);
        }

        //Update UI
        mTxtBalance.setText(Integer.toString(mWalletVM.balance()));
        mBtnDie.setText(Integer.toString(mWalletVM.dieValue()));
        mTxtSingleSixCount.setText(Integer.toString(mWalletVM.singleSixes()));
        mTxtTotalDiceCount.setText(Integer.toString(mWalletVM.totalRolls()));
        mPreviousValue.setText(Integer.toString(mWalletVM.previousRoll()));
        mTxtDoubleSixCount.setText(Integer.toString(mWalletVM.doubleSixes()));
        mTxtDoubleOtherCount.setText(Integer.toString(mWalletVM.doubleOthers()));
      }
    });
  }

 /* @Override
  protected void onSaveInstanceState(@NonNull Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putInt(KEY_BALANCE, mBalance);
    outState.putInt(KEY_DIE_VALUE, mDie.value());
    Log.d(TAG, "Saved: balance = " + mBalance + ", die = " + mDie.value());
  }*/
}