package karmachallenge.com.karmapay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import karmachallenge.com.karmapay.ui.register.SignupFragment;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, SignupFragment.newInstance())
                    .commitNow();
        }
    }
}
