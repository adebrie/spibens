package debrie.android.fr.spibens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class Launcher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

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
