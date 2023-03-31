package androidsamples.java.tictactoe;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OpenGamesAdapter extends RecyclerView.Adapter<OpenGamesAdapter.ViewHolder> {

  List<String> gameIDs;

  public OpenGamesAdapter(List<String> gameIDs) {
    this.gameIDs = gameIDs;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_item, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

    holder.mIdView.setText((String.valueOf(position + 1)));
    holder.mContentView.setText(gameIDs.get(position));
  }

  @Override
  public int getItemCount() {
    return gameIDs.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView mIdView;
    public final TextView mContentView;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      mIdView = view.findViewById(R.id.item_number);
      mContentView = view.findViewById(R.id.content);

      mContentView.setOnClickListener(v -> {
        String gameType = "Two-Player";
        String gameID = mContentView.getText().toString();

        NavDirections action = DashboardFragmentDirections.actionGame(gameType, gameID);
        NavController mNavController = Navigation.findNavController(view);
        mNavController.navigate(action);

        FirebaseFirestore.getInstance().collection("games").document(gameID).update("player2",
            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
        FirebaseFirestore.getInstance().collection("games").document(gameID).update("status", "playing");

      });
    }

    @NonNull
    @Override
    public String toString() {
      return super.toString() + " '" + mContentView.getText() + "'";
    }
  }

  public void setGameIDs(List<String> gameIDs) {
    this.gameIDs = gameIDs;
    notifyDataSetChanged();
  }
}