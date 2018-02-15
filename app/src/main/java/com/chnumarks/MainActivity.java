package com.chnumarks;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;

import com.chnumarks.R;
import com.chnumarks.UtilsKt;
import com.chnumarks.fragments.EditFragment;
import com.chnumarks.fragments.NavigationDrawerFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

/**
 * Created by denak on 15.02.2018.
 */

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth auth;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private NavigationDrawerFragment navigationFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        drawerLayout = findViewById(R.id.main_drawer_layout);
        tabLayout = findViewById(R.id.edit_tab_layout);
        UtilsKt.setLightStatusBar(toolbar, this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            int offset = (int) (25 * getResources().getDisplayMetrics().density + 0.5f);
            toolbar.setPadding(0, offset, 0, 0);
        }
        navigationFragment = new NavigationDrawerFragment();
        navigationFragment.setParameters(toolbar, drawerLayout);
        getSupportFragmentManager().beginTransaction().add(R.id.main_drawer_layout, navigationFragment).commit();

        EditFragment editFragment = new EditFragment();
        editFragment.setUpTabLayout(tabLayout);
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, editFragment).commit();


        /*

         так я в себе запустив це
         StudentListFragment fragment = new StudentListFragment();


        List<StudentInfo> list = new ArrayList<>();
        String names[] = {"Куц Денис","Лабінський Віктор","Лисенко Юлія","Мельничук Андрій","Нікітін Олексій","Ніколаєвич Владислав","Нікорич Василь","Анонімус"};
        int points[] = {    56 ,            21   ,               47,            52      ,          30          ,            15       ,      44          ,   50   };
        int done[] = {    14 ,              5 ,                  11,            13       ,          7          ,              3       ,      10         ,   13 };
        long id[];
        double marks[];
        int temp;


        for (int i = 0; i <names.length ; i++) {
            id = new long[done[i]];
            marks = new double[done[i]];
            temp = points[i] - done[i];
            for (int j = 0; j <done[i] ; j++) {
                id[j] = j;
                if (temp>4){
                    marks[j] = 5;
                    temp -= 4;
                }
                else {
                    marks[j] = 1 + temp;
                    temp = 0;
                }
            }
            list.add(new StudentInfo(names[i],id,marks));
        }





        fragment.list = list;
        getSupportFragmentManager().beginTransaction().add(R.id.rootLayout, fragment).commit();



         */
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.web_client_id))
                    .requestEmail()
                    .build();
            Intent signInIntent = GoogleSignIn.getClient(this, gso).getSignInIntent();
            startActivityForResult(signInIntent, 1);
        } else {
            updateUI(user);
        }
    }

    private void updateUI(FirebaseUser user) {
        navigationFragment.setUser(user);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Task task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = (GoogleSignInAccount) task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                auth.signInWithCredential(credential).addOnCompleteListener(task1 -> {
                    FirebaseUser newUser = auth.getCurrentUser();
                    updateUI(newUser);
                });
            } catch (Throwable e) {

            }
        }
    }
}
