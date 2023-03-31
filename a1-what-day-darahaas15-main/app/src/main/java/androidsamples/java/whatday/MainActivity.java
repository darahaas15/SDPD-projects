package androidsamples.java.whatday;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  Button checkButton;
  EditText yearText, monthText, dateText;
  TextView messageText;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    checkButton = (Button) findViewById(R.id.btn_check);
    checkButton.setOnClickListener(new View.OnClickListener(){
      public void onClick(View v){

        dateText = (EditText) findViewById(R.id.editDate);
        monthText = (EditText) findViewById(R.id.editMonth);
        yearText = (EditText) findViewById(R.id.editYear);
        messageText = (TextView) findViewById(R.id.txt_display);

        DateModel.initialize(yearText.getText().toString(), monthText.getText().toString(), dateText.getText().toString());
        messageText.setText(DateModel.getMessage());
      }
    });
  }
}