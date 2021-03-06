package karmachallenge.com.karmapay.ui.register;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import karmachallenge.com.karmapay.R;
import karmachallenge.com.karmapay.RegisterActivity;

public class SignupFragment extends Fragment implements View.OnClickListener {

    private SignupViewModel mViewModel;
    private View view;
    private EditText userNameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;
    private Button registerButton;
    private String userName;
    private String password;
    private String confirmPassword;
    private FirebaseAuth mAuth;
    private String userId;
    private String firstName;
    private String lastName;
    private String contactNumber;
    private FirebaseFirestore mFirestore;

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_fragment, container, false);
        configureFirebaseClient();
        bindUIComponents();
        bindListeners();
        return view;
    }

    private void configureFirebaseClient() {
        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignupViewModel.class);
        // TODO: Use the ViewModel
    }

    private void bindUIComponents() {
        userNameEditText = view.findViewById(R.id.username_edittext);
        passwordEditText = view.findViewById(R.id.password_edittext);
        confirmPasswordEditText = view.findViewById(R.id.confirm_password_edittext);
        registerButton = view.findViewById(R.id.firebase_register_button);
        firstNameEditText = view.findViewById(R.id.first_name_edittext);
        lastNameEditText = view.findViewById(R.id.last_name_edittext);
        phoneNumberEditText = view.findViewById(R.id.phone_number_edittext);
    }

    private void bindListeners() {
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firebase_register_button:
                if(isValid()) {
                    registerUser();
                }
                break;
        }
    }

    private void registerUser() {
        mAuth.createUserWithEmailAndPassword(userName, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isComplete()) {
                            if(task.isSuccessful()) {
                                CollectionReference reference = mFirestore.collection("users");
                                userId = mAuth.getCurrentUser().getUid();
                                reference.add(new User(userId, firstName, lastName, contactNumber));
                                showToast("Success");
                            } else {
                                try {
                                    throw task.getException();
                                } catch  (Exception e) {
                                    showPopUp("Error", e.getMessage());
                                }
                            }
                        }
                    }
                });
    }

    private boolean isValid() {
        boolean isValid = true;
        userName = userNameEditText.getText().toString();
        password = passwordEditText.getText().toString();
        confirmPassword = confirmPasswordEditText.getText().toString();
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        contactNumber = phoneNumberEditText.getText().toString();
        if(TextUtils.isEmpty(firstName)) {
            isValid = false;
            firstNameEditText.setError("Required");
        } else if(TextUtils.isEmpty(lastName)) {
            isValid = false;
            lastNameEditText.setError("Required");
        } else if(TextUtils.isEmpty(contactNumber)) {
            isValid = false;
            phoneNumberEditText.setError("Required");
        } else if(TextUtils.isEmpty(userName)) {
            isValid = false;
            userNameEditText.setError("Required");
        } else if(TextUtils.isEmpty(password)) {
            isValid = false;
            passwordEditText.setError("Required");
        } else if(!TextUtils.equals(password, confirmPassword)) {
            isValid = false;
            confirmPasswordEditText.setError("Passwords don't match");
        }
        return isValid;
    }

    public void showProgressDialog(String message) {
        ((RegisterActivity)(getActivity())).showProgressBar(message, getContext());
    }

    public void hideProgressDialog() {
        ((RegisterActivity)(getActivity())).hideProgress();
    }

    public void showPopUp(String title, String message) {
        ((RegisterActivity)(getActivity())).showDialog(title, message, getContext());
    }

    public void showToast(String message) {
        ((RegisterActivity)(getActivity())).showToastMessage(message, getContext());
    }
}
