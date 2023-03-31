package androidsamples.java.tictactoe;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginFragment extends Fragment {

    EditText mEmail;
    EditText mPassword;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser mUser = mAuth.getCurrentUser();
    NavController mNavController;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = view.findViewById(R.id.edit_email);
        mPassword = view.findViewById(R.id.edit_password);

        view.findViewById(R.id.btn_log_in).setOnClickListener(v -> {

            mNavController = Navigation.findNavController(v);
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();
            if (email.equals("") || password.equals("")) {
                Toast.makeText(getActivity(), "Login failed.", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> map = new HashMap<>();
                map.put("games_drawn", 0);
                map.put("games_lost", 0);
                map.put("games_won", 0);

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(requireActivity(),
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    mUser = mAuth.getCurrentUser();
                                    map.put("email", mUser.getEmail());

                                    db.collection("users").document(mUser.getUid()).set(map);

                                    NavDirections action = LoginFragmentDirections.actionLoginSuccessful();
                                    mNavController.navigate(action);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthWeakPasswordException e) {
                                        Toast.makeText(getActivity(),
                                                "Enter a password of length greater than six characters.",
                                                Toast.LENGTH_LONG).show();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        Toast.makeText(getActivity(), "Incorrect credentials.", Toast.LENGTH_LONG)
                                                .show();
                                    } catch (Exception e) {
                                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                                                requireActivity(), new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            // Sign in success, update UI with the signed-in user's
                                                            // information
                                                            Log.d(TAG, "signInWithEmail:success");
                                                            mUser = mAuth.getCurrentUser();

                                                            NavDirections action = LoginFragmentDirections
                                                                    .actionLoginSuccessful();
                                                            mNavController.navigate(action);
                                                        } else {
                                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                                            Toast.makeText(getActivity(), "Login failed.",
                                                                    Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    }
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                }
                            }
                        });
            }
        });

        return view;
    }
}