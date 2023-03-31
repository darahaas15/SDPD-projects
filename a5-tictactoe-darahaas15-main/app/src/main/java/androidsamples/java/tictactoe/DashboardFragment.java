package androidsamples.java.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DashboardFragment extends Fragment {

  private static final String TAG = "DashboardFragment";
  private NavController mNavController;
  FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
  FirebaseFirestore db = FirebaseFirestore.getInstance();
  CollectionReference games = db.collection("games");
  TextView wins_count;
  TextView losses_count;
  TextView draws_count;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public DashboardFragment() {
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate");

    setHasOptionsMenu(true); // Needed to display the action menu for this fragment
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
      ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_dashboard, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mNavController = Navigation.findNavController(view);
    if (mUser != null) {

      wins_count = view.findViewById(R.id.txt_score);
      losses_count = view.findViewById(R.id.txt_losses);
      draws_count = view.findViewById(R.id.txt_draws);

      List<String> gameIDs = new ArrayList<>();
      final OpenGamesAdapter adapter = new OpenGamesAdapter(gameIDs);
      RecyclerView rv = view.findViewById(R.id.list);
      rv.setAdapter(adapter);
      rv.setLayoutManager(new LinearLayoutManager(getContext()));

      games.addSnapshotListener(new EventListener<QuerySnapshot>() {
        @Override
        public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
          if (e != null) {
            Log.w(TAG, "Listen failed.", e);
            return;
          }

          List<String> IDs = new ArrayList<>();

          assert snapshot != null;
          for (QueryDocumentSnapshot doc : snapshot) {
            if (doc.get("player1") != null && Objects.equals(doc.get("status"), "waiting")) {
              IDs.add(doc.getId());
            }
          }
          gameIDs.clear();
          gameIDs.addAll(IDs);
          adapter.setGameIDs(gameIDs);
        }
      });

      db.collection("users").document(mUser.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot snapshot,
            @Nullable FirebaseFirestoreException e) {
          if (e != null) {
            Log.w(TAG, "Listen failed.", e);
            return;
          }

          if (snapshot != null && snapshot.exists()) {
            losses_count.setText(String.valueOf((long) snapshot.get("games_lost")));
            draws_count.setText(String.valueOf((long) snapshot.get("games_drawn")));
            wins_count.setText(String.valueOf((long) snapshot.get("games_won")));
          } else {
            Log.d(TAG, "Current data: null");
          }
        }
      });

      // Show a dialog when the user clicks the "new game" button
      view.findViewById(R.id.fab_new_game).setOnClickListener(v -> {

        // A listener for the positive and negative buttons of the dialog
        DialogInterface.OnClickListener listener = (dialog, which) -> {
          String gameType = "No type";
          String gameID = "";
          if (which == DialogInterface.BUTTON_POSITIVE) {
            gameType = getString(R.string.two_player);

            Map<String, Object> map = new HashMap<>();
            final List<String> values = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
              values.add("");
            }
            map.put("player1", mUser.getEmail());
            map.put("player2", "");
            map.put("status", "waiting");
            map.put("values", values);
            map.put("move_of", "player1");
            map.put("chances", 0);
            games.document(Objects.requireNonNull(mUser.getEmail())).set(map);
            gameID = mUser.getEmail();

          } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            gameType = getString(R.string.one_player);
          }
          Log.d(TAG, "New Game: " + gameType);

          // Passing the game type as a parameter to the action
          // extract it in GameFragment in a type safe way
          NavDirections action = DashboardFragmentDirections.actionGame(gameType, gameID);
          mNavController.navigate(action);
        };

        // create the dialog
        AlertDialog dialog = new AlertDialog.Builder(requireActivity())
            .setTitle(R.string.new_game)
            .setMessage(R.string.new_game_dialog_message)
            .setPositiveButton(R.string.two_player, listener)
            .setNegativeButton(R.string.one_player, listener)
            .setNeutralButton(R.string.cancel, (d, which) -> d.dismiss())
            .create();
        dialog.show();
      });

    } else {
      // No user is signed in
      NavDirections action = DashboardFragmentDirections.actionNeedAuth();
      mNavController.navigate(action);
    }

  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu_logout, menu);
    // this action menu is handled in MainActivity
  }
}