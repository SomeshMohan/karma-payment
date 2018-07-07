package karmachallenge.com.karmapay;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import karmachallenge.com.karmapay.ui.register.User;
public class LandingPageViewModel extends ViewModel
{
    private final FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private MutableLiveData<User> user;
    public LiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<User>();
            loadUsers();
        }
        return user;
    }

    private void loadUsers() {
        CollectionReference reference =  mFirestore.collection("users");
        Query userQuery = reference.whereEqualTo("uid", mAuth.getCurrentUser().getUid());
        userQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("dfdf","dfdf");
                for(DocumentSnapshot doc : task.getResult()){
                    try {
                        user.setValue(doc.toObject(User.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
