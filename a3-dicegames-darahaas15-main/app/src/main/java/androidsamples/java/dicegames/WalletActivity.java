package androidsamples.java.dicegames;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

public class WalletActivity extends AppCompatActivity {
  Button btnDie;
  TextView txtBalance;
  WalletViewModel vm;
  Die mDie;
  static final String KEY_BALANCE="KEY_BALANCE";
  private static final int TWO_REQUEST_CODE = 2;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wallet);

    vm = new ViewModelProvider(this).get(WalletViewModel.class);
    mDie=new Die6();
    btnDie = findViewById(R.id.btn_die);
    txtBalance = findViewById(R.id.txt_balance);
    vm.setDie(mDie);
    btnDie.setOnClickListener(v -> {
      vm.rollDie();
      updateUI();
      if (vm.dieValue() == 6) Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show();
    });

    updateUI();
  }

  void updateUI() {
    btnDie.setText(String.format(Locale.ENGLISH, "%d", vm.dieValue()));
    txtBalance.setText(String.format(Locale.ENGLISH, "%s %d", getString(R.string.coins), vm.balance()));
  }

  public void launchTwo(View view) {
    Intent intent = new Intent(this, TwoOrMoreActivity.class);
    intent.putExtra(KEY_BALANCE,vm.balance());
    startActivityForResult(intent, TWO_REQUEST_CODE);
  }

  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == TWO_REQUEST_CODE && resultCode == RESULT_OK) {
      if (data != null) {
        int balance = data.getIntExtra(TwoOrMoreActivity.KEY_BALANCE_RETURN, 0);
        vm.setBalance(balance);
        updateUI();
      }
    }
  }
}