package androidsamples.java.dicegames;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class TwoOrMoreActivity extends AppCompatActivity {
  static final String KEY_BALANCE_RETURN = "KEY_BALANCE_RETURN";
  Button go, back, info;
  TextView one_die, two_die, three_die, four_die, txtBalance;
  TwoOrMoreViewModel tm;
  EditText wager_txt;
  RadioGroup radioGroup;
  RadioButton radioButton;
  GameResult game_result_toast;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_two_or_more);
    tm = new ViewModelProvider(this).get(TwoOrMoreViewModel.class);
    int balance = getIntent().getIntExtra(WalletActivity.KEY_BALANCE, 0);
    if (tm.balance() == 0)
      tm.setBalance(balance);
    txtBalance = findViewById(R.id.txt_balance_2);

    tm.addDie(new Die6());
    tm.addDie(new Die6());
    tm.addDie(new Die6());
    tm.addDie(new Die6());

    go = findViewById(R.id.btn_go);
    back = findViewById(R.id.btn_back);
    info = findViewById(R.id.btn_info);
    one_die = findViewById(R.id.die_1);
    two_die = findViewById(R.id.die_2);
    three_die = findViewById(R.id.die_3);
    four_die = findViewById(R.id.die_4);
    wager_txt = findViewById(R.id.wager);
    radioGroup = findViewById(R.id.radioGroup);
    txtBalance.setText(String.format(Locale.ENGLISH, "%s %d", getString(R.string.coins), tm.balance()));

    go.setOnClickListener(v -> {
      tm.clearList();
      tm.addDie(new Die6());
      tm.addDie(new Die6());
      tm.addDie(new Die6());
      tm.addDie(new Die6());
      int selected = radioGroup.getCheckedRadioButtonId();
      radioButton = findViewById(selected);
      switch (selected) {
        case R.id.alike_2:
          tm.setGameType(GameType.TWO_ALIKE);
          break;
        case R.id.alike_3:
          tm.setGameType(GameType.THREE_ALIKE);
          break;
        case R.id.alike_4:
          tm.setGameType(GameType.FOUR_ALIKE);
          break;
        case -1:
          Toast.makeText(this, "Select an option", Toast.LENGTH_SHORT).show();
          return;
      }
      if (wager_txt.getText().toString().equals("")) {
        Toast.makeText(this, "Enter a Wager", Toast.LENGTH_SHORT).show();
        return;
      } else
        tm.setWager((Integer.parseInt(wager_txt.getText().toString())));
      if (!tm.isValidWager()) {
        Toast.makeText(this, "Wager is incorrect", Toast.LENGTH_SHORT).show();
      } else {
        game_result_toast = tm.play();
        if (game_result_toast == GameResult.WIN)
          Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        if (game_result_toast == GameResult.LOSS)
          Toast.makeText(this, "Loss!", Toast.LENGTH_SHORT).show();
        wager_txt.setText("");
        updateUI();
      }
    });

    updateUI();
  }

  void updateUI() {
    one_die.setText(String.format(Locale.ENGLISH, "%d", tm.diceValues().get(0)));
    two_die.setText(String.format(Locale.ENGLISH, "%d", tm.diceValues().get(1)));
    three_die.setText(String.format(Locale.ENGLISH, "%d", tm.diceValues().get(2)));
    four_die.setText(String.format(Locale.ENGLISH, "%d", tm.diceValues().get(3)));
    txtBalance.setText(String.format(Locale.ENGLISH, "%s %d", getString(R.string.coins), tm.balance()));
  }

  public void launchOne(View view) {
    Intent change = new Intent(this, WalletActivity.class);
    int tmp = tm.balance();
    change.putExtra(KEY_BALANCE_RETURN, tmp);
    setResult(RESULT_OK, change);
    finish();
  }

  public void launchInfo(View view) {
    Intent third = new Intent(this, InfoActivity.class);
    startActivity(third);
  }
}
