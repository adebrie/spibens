package debrie.android.fr.spibens;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;


public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        ImageView logout = (ImageView) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Launcher.this, LoginActivity.class));
            }
        });

        ImageView members = (ImageView) findViewById(R.id.members);
        members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent (Launcher.this, Members.class);
                startActivity(i);
            }
        });

        ImageView login = (ImageView) findViewById(R.id.imageView6);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Launcher.this, LoginActivity.class);
                startActivity(i);
            }
        });

        ImageView happyH = (ImageView) findViewById(R.id.happyhour);
        happyH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Launcher.this, HappyH.class);
                startActivity(i);
            }
        });

    }
}
