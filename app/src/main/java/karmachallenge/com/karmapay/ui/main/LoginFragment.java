package karmachallenge.com.karmapay.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import karmachallenge.com.karmapay.LandingPage;
import karmachallenge.com.karmapay.MainActivity;
import karmachallenge.com.karmapay.R;
import karmachallenge.com.karmapay.RegisterActivity;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private LoginViewModel mViewModel;
    private Button registerButton;
    private Button signInButton;
    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth mAuth;
    private View view;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_fragment, container, false);
        configureFirebaseClient();
        bindUIComponents();
        bindListeners();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        // TODO: Use the ViewModel
    }

    private void configureFirebaseClient() {
        mAuth = FirebaseAuth.getInstance();
    }

    private void bindUIComponents() {
        registerButton = view.findViewById(R.id.register_button);
        signInButton = view.findViewById(R.id.firebase_login_button);
        emailEditText = view.findViewById(R.id.username_edittext);
        passwordEditText = view.findViewById(R.id.password_edittext);
    }

    private void bindListeners() {
        registerButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firebase_login_button:
                logInUser();
                break;

            case R.id.register_button:
                gotoRegistrationPage();
                break;
        }
    }

    private void gotoRegistrationPage() {
        Intent registerIntent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void logInUser() {
        showProgressDialog("Please Wait");
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                showPopUp("Error", "Invalid User! Please register first");
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                showPopUp("Error", "Invalid Credential");
                            } catch (Exception e) {
                                showPopUp("Error", task.getException().getMessage() + "");
                            }
                        }
                        hideProgressDialog();
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        Intent landingPageIntent = new Intent(getActivity(), LandingPage.class);
        startActivity(landingPageIntent);
    }

    public void showProgressDialog(String message) {
        ((MainActivity)(getActivity())).showProgressBar(message, getContext());
    }

    public void hideProgressDialog() {
        ((MainActivity)(getActivity())).hideProgress();
    }

    public void showPopUp(String title, String message) {
        ((MainActivity)(getActivity())).showDialog(title, message, getContext());
    }

    public void showToast(String message) {
        ((MainActivity)(getActivity())).showToastMessage(message, getContext());
    }
}
