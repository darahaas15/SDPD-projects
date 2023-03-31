package androidsamples.java.tictactoe;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GameFragment extends Fragment {
  private static final String TAG = "GameFragment";
  private static final int GRID_SIZE = 9;

  private final Button[] mButtons = new Button[GRID_SIZE];
  private NavController mNavController;

  String gameType = "One-Player";
  String gameID = "";
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
  DocumentReference gameDoc;
  DocumentReference doc = db.collection("users").document(mUser.getUid());
  List<String> positions;
  String status = "playing";

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);

    GameFragmentArgs args = GameFragmentArgs.fromBundle(getArguments());
    gameType = args.getGameType();
    gameID = args.getGameID();

    OnBackPressedCallback callback = new OnBackPressedCallback(true) {
      @Override
      public void handleOnBackPressed() {

        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
            .setTitle(R.string.confirm)
            .setMessage(R.string.forfeit_game_dialog_message)
            .setPositiveButton(R.string.yes, (d, which) -> {

              doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                  if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                      Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                      long losses = (long) document.get("games_lost");
                      doc.update("games_lost", losses + 1);
                    } else {
                      Log.d(TAG, "No such document");
                    }
                  } else {
                    Log.d(TAG, "Get failed with ", task.getException());
                  }
                }
              });
              if (Objects.equals(gameType, "Two-Player")) {
                gameDoc.delete();
              }
              mNavController.popBackStack();
            })
            .setNegativeButton(R.string.cancel, (d, which) -> d.dismiss())
            .create();
        dialog.show();
      }
    };
    requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_game, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    mNavController = Navigation.findNavController(view);

    mButtons[0] = view.findViewById(R.id.button0);
    mButtons[1] = view.findViewById(R.id.button1);
    mButtons[2] = view.findViewById(R.id.button2);

    mButtons[3] = view.findViewById(R.id.button3);
    mButtons[4] = view.findViewById(R.id.button4);
    mButtons[5] = view.findViewById(R.id.button5);

    mButtons[6] = view.findViewById(R.id.button6);
    mButtons[7] = view.findViewById(R.id.button7);
    mButtons[8] = view.findViewById(R.id.button8);

    final String[] p = { "player1", "player1" };
    final long[] chances = { 0 };

    if (Objects.equals(gameType, "Two-Player")) {
      gameDoc = db.collection("games").document(gameID);
      gameDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot snapshot,
            @Nullable FirebaseFirestoreException e) {
          if (e != null) {
            Log.w(TAG, "Listen failed.", e);
            return;
          }

          if (snapshot != null && snapshot.exists()) {
            Log.d(TAG, "Current data: " + snapshot.getData());
            List<String> values = new ArrayList<>();
            values = (List<String>) snapshot.get("values");
            Log.d(TAG, Objects.requireNonNull(values).toString());

            updateUI(values);

            p[0] = (String) snapshot.get("move_of");
            chances[0] = (long) snapshot.get("chances");
            if (Objects.equals(status, "playing"))
              p[1] = checkGameEnd(chances[0], p[0], gameType);

          } else {
            Log.d(TAG, "Current data: null");
          }
        }
      });
    }

    for (int i = 0; i < mButtons.length; i++) {
      int current_listener = i;
      mButtons[i].setOnClickListener(v -> {
        Log.d(TAG, "Button " + current_listener + " clicked");
        String buttonText = mButtons[current_listener].getText().toString();
        if (Objects.equals(gameType, "One-Player")) {
          if (buttonText.isEmpty() && Objects.equals(p[0], "player1")) {
            mButtons[current_listener].setText("O");
            mButtons[current_listener].setTextColor(getResources().getColor(R.color.black));
            chances[0]++;
            p[0] = checkGameEnd(chances[0], p[0], gameType);
            for (Button mButton : mButtons) {
              String nextButtonText = mButton.getText().toString();
              if (nextButtonText.isEmpty() && Objects.equals(p[0], "player2")) {
                mButton.setText("X");
                mButton.setTextColor(getResources().getColor(R.color.white));
                chances[0]++;
                p[0] = checkGameEnd(chances[0], p[0], gameType);
                break;
              }
            }
          }
        } else if (Objects.equals(gameType, "Two-Player")) {
          if (buttonText.isEmpty() && Objects.equals(p[0], "player1") && Objects.equals(gameID, mUser.getEmail())) {
            mButtons[current_listener].setText("O");
            mButtons[current_listener].setTextColor(getResources().getColor(R.color.black));
            positions.set(current_listener, "O");
            gameDoc.update("values", positions);
            gameDoc.update("move_of", p[1]);
            gameDoc.update("chances", chances[0] + 1);
          } else if (buttonText.isEmpty() && Objects.equals(p[0], "player2")
              && !Objects.equals(gameID, mUser.getEmail())) {
            mButtons[current_listener].setText("X");
            mButtons[current_listener].setTextColor(getResources().getColor(R.color.white));
            positions.set(current_listener, "X");
            gameDoc.update("values", positions);
            gameDoc.update("move_of", p[1]);
            gameDoc.update("chances", chances[0] + 1);
          }
        }

      });
    }
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_logout, menu);
  }

  public void gameUpDialog(String title, String body, String winner, String playType) {
    final long[] current_balance = { 0 };
    Log.d(TAG, winner);
    status = "finished";

    Map<String, Object> map = new HashMap<>();

    doc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
      @RequiresApi(api = Build.VERSION_CODES.N)
      @Override
      public void onComplete(@NonNull Task<DocumentSnapshot> task) {
        if (task.isSuccessful()) {
          DocumentSnapshot document = task.getResult();
          if (document.exists()) {
            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
            map.put("email", document.get("email"));
            map.put("games_drawn", document.get("games_drawn"));
            map.put("games_lost", document.get("games_lost"));
            map.put("games_won", document.get("games_won"));

            if (Objects.equals(playType, "One-Player")) {
              if (Objects.equals(winner, "player1")) {
                current_balance[0] = (long) map.get("games_won");
                current_balance[0] = current_balance[0] + 1;
                map.replace("games_won", current_balance[0]);
                doc.set(map);
              } else if (Objects.equals(winner, "player2")) {
                current_balance[0] = (long) map.get("games_lost");
                current_balance[0] = current_balance[0] + 1;
                map.replace("games_lost", current_balance[0]);
                doc.set(map);
              } else if (Objects.equals(winner, "")) {
                current_balance[0] = (long) map.get("games_drawn");
                current_balance[0] = current_balance[0] + 1;
                map.replace("games_drawn", current_balance[0]);
                doc.set(map);
              }
            } else {
              if (gameID == mUser.getEmail()) {
                if (Objects.equals(winner, "player1")) {
                  current_balance[0] = (long) map.get("games_won");
                  current_balance[0] = current_balance[0] + 1;
                  map.replace("games_won", current_balance[0]);
                  doc.set(map);
                } else if (Objects.equals(winner, "player2")) {
                  current_balance[0] = (long) map.get("games_lost");
                  current_balance[0] = current_balance[0] + 1;
                  map.replace("games_lost", current_balance[0]);
                  doc.set(map);
                } else if (Objects.equals(winner, "")) {
                  current_balance[0] = (long) map.get("games_drawn");
                  current_balance[0] = current_balance[0] + 1;
                  map.replace("games_drawn", current_balance[0]);
                  doc.set(map);
                }
              } else {
                if (Objects.equals(winner, "player1")) {
                  current_balance[0] = (long) map.get("games_lost");
                  current_balance[0] = current_balance[0] + 1;
                  map.replace("games_lost", current_balance[0]);
                  doc.set(map);
                } else if (Objects.equals(winner, "player2")) {
                  current_balance[0] = (long) map.get("games_won");
                  current_balance[0] = current_balance[0] + 1;
                  map.replace("games_won", current_balance[0]);
                  doc.set(map);
                } else if (Objects.equals(winner, "")) {
                  current_balance[0] = (long) map.get("games_drawn");
                  current_balance[0] = current_balance[0] + 1;
                  map.replace("games_drawn", current_balance[0]);
                  doc.set(map);
                }
              }

            }

            Log.d(TAG, map.toString());
          } else {
            Log.d(TAG, "No such document");
          }
        } else {
          Log.d(TAG, "get failed with ", task.getException());
        }
      }
    });

    AlertDialog dialog = new AlertDialog.Builder(requireActivity())
        .setTitle(title)
        .setMessage(body)
        .setPositiveButton("okay", (d, which) -> {
          // TODO update loss count
          try {
            gameDoc.delete();
          } catch (Exception e) {
            Log.e(TAG, e.toString());
          }
          mNavController.popBackStack();
        })
        .setCancelable(false)
        .create();
    dialog.show();
  }

  public String checkGameEnd(long chances, String p, String playType) {
    Log.d(TAG, chances + p + playType);
    if (mButtons[0].getText().toString().equals(mButtons[1].getText().toString())
        && mButtons[0].getText().toString().equals(mButtons[2].getText().toString())
        && !mButtons[0].getText().toString().isEmpty()) {
      gameUpDialog("Game Up", mButtons[0].getText() + " wins!", p, playType);
      return p;
    } else if (mButtons[3].getText().toString().equals(mButtons[4].getText().toString())
        && mButtons[3].getText().toString().equals(mButtons[5].getText().toString())
        && !mButtons[3].getText().toString().isEmpty()) {
      gameUpDialog("Game Up", mButtons[3].getText() + " wins!", p, playType);
      return p;
    } else if (mButtons[6].getText().toString().equals(mButtons[7].getText().toString())
        && mButtons[6].getText().toString().equals(mButtons[8].getText().toString())
        && !mButtons[6].getText().toString().isEmpty()) {
      gameUpDialog("Game Up", mButtons[6].getText() + " wins!", p, playType);
      return p;
    } else if (mButtons[0].getText().toString().equals(mButtons[3].getText().toString())
        && mButtons[0].getText().toString() == mButtons[6].getText().toString()
        && !mButtons[0].getText().toString().isEmpty()) {
      gameUpDialog("Game Up", mButtons[0].getText() + " wins!", p, playType);
      return p;
    } else if (mButtons[1].getText().toString() == mButtons[4].getText().toString()
        && mButtons[1].getText().toString().equals(mButtons[7].getText().toString())
        && !mButtons[1].getText().toString().isEmpty()) {
      gameUpDialog("Game Up", mButtons[1].getText() + " wins!", p, playType);
      return p;
    } else if (mButtons[2].getText().toString().equals(mButtons[5].getText().toString())
        && mButtons[2].getText().toString().equals(mButtons[8].getText().toString())
        && !mButtons[2].getText().toString().isEmpty()) {
      gameUpDialog("Game Up", mButtons[2].getText() + " wins!", p, playType);
      return p;
    } else if (mButtons[0].getText().toString().equals(mButtons[4].getText().toString())
        && mButtons[0].getText().toString().equals(mButtons[8].getText().toString())
        && !mButtons[0].getText().toString().isEmpty()) {
      gameUpDialog("Game Up", mButtons[0].getText() + " wins!", p, playType);
      return p;
    } else if (mButtons[2].getText().toString().equals(mButtons[4].getText().toString())
        && mButtons[2].getText().toString().equals(mButtons[6].getText().toString())
        && !mButtons[2].getText().toString().isEmpty()) {
      gameUpDialog("Game Up", mButtons[2].getText() + " wins!", p, playType);
      return p;
    } else if (chances == 9) {
      gameUpDialog("Draw", "It is a draw", "", playType);
      return p;
    }
    if (Objects.equals(p, "player1"))
      return "player2";
    else
      return "player1";
  }

  public void updateUI(List<String> values) {
    positions = values;
    for (int i = 0; i < 9; i++) {
      mButtons[i].setText(values.get(i));
    }
  }
}