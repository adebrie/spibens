package debrie.android.fr.spibens;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.SQLOutput;


public class LauncherActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAuth = FirebaseAuth.getInstance();
        switch (item.getItemId()){
            case R.id.action_myprofile:
                Intent i = new Intent(LauncherActivity.this, ProfileActivity.class);
                i.putExtra("id", mAuth.getCurrentUser().getUid());
                startActivity(i);
                return true;
            case R.id.action_logout:
                mAuth.signOut();
                startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setContentView(R.layout.activity_launcher);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_launcher);
        setSupportActionBar(myToolbar);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser u = firebaseAuth.getCurrentUser();
                if (u != null ) {
                    //Logged in
                    System.out.println("Signed in");
                    System.out.println(u.getUid());

                } else {
                    //Logged out
                    startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                }
            }
        };

        ImageView sports = (ImageView) findViewById(R.id.imageView3);
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LauncherActivity.this, GamesActivity.class));
            }
        });

//        ImageView logout = (ImageView) findViewById(R.id.logout);
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
//            }
//        });

        ImageView members = (ImageView) findViewById(R.id.members);
        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (LauncherActivity.this, MembersActivity.class);
                startActivity(i);
            }
        });

        ImageView login = (ImageView) findViewById(R.id.imageView6);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LauncherActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        ImageView happyH = (ImageView) findViewById(R.id.happyhour);
        happyH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LauncherActivity.this, HappyHourActivity.class);
                startActivity(i);
            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_launcher);
        setSupportActionBar(myToolbar);
    }
}
