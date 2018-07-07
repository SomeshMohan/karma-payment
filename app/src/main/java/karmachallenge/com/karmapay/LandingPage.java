package karmachallenge.com.karmapay;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import karmachallenge.com.karmapay.ui.landingpage.LandingPageFragment;
import karmachallenge.com.karmapay.ui.register.User;

public class LandingPage extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBar actionBar;
    private FirebaseAuth mAuth;
    private TextView nameTextView;
    private TextView emailTextView;
    private Toolbar toolbar;
    private LandingPageViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_page_activity);
        initializeFirebase();
        bindViews();
        bindListeners();
        initalizeViewModel();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LandingPageFragment.newInstance())
                    .commitNow();
        }
    }
    private void initalizeViewModel() {
        viewModel = ViewModelProviders.of(this).get(LandingPageViewModel.class);
        viewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                nameTextView.setText(user.getFirstName() +" " +user.getLastName());
                emailTextView.setText(user.getContact());
            }
        });
    }
    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
    }
    private void bindViews() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        nameTextView = navigationView.findViewById(R.id.name_textview);
        emailTextView = navigationView.findViewById(R.id.email_textview);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }
    private void bindListeners() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.logout:
                        logout();
                        break;
                    case R.id.profile:
                        gotoProfile();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        mAuth.signOut();
        gotoLoginPage();
    }

    private void gotoLoginPage() {
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    private void gotoProfile() {
        /*Intent profileIntent = new Intent(LandingPage.this, ProfileActivity.class);
        startActivity(profileIntent);*/
    }
}
