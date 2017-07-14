package debrie.android.fr.spibens;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewOverlay;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Canvas;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.sql.SQLOutput;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference profileRef;
    private FirebaseAuth mAuth;
    private User u;
    private String id;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile_edit:
                Intent i = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                System.out.println("USER ID "+id);
                i.putExtra("id", id);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.profile, menu);
        //If user is watching his own profile, show EDIT button
        if (mAuth.getCurrentUser().getUid().equals(id)){
            menu.findItem(R.id.action_profile_edit).setVisible(true);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar_profile);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();



        final TextView mail = (TextView) findViewById(R.id.mail_text);
        final TextView name = (TextView) findViewById(R.id.name_text);
        final TextView lab = (TextView) findViewById(R.id.lab_text);
        final TextView room = (TextView) findViewById(R.id.room_text);
        final TextView worksOn = (TextView) findViewById(R.id.workson_text);
        final TextView skills = (TextView) findViewById(R.id.skills_text);
        final TextView lang = (TextView) findViewById(R.id.lang_text);


        id = getIntent().getStringExtra("id");
        profileRef = FirebaseDatabase.getInstance().getReference("membersList").child(String.valueOf(id));

        profileRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Pulls data from database
                u = new User();

                try {
                    u.setEmail(dataSnapshot.child("email").getValue().toString());
                    u.setName(dataSnapshot.child("name").getValue().toString());
                    u.setLab(dataSnapshot.child("lab").getValue().toString());
                    u.setRoom(dataSnapshot.child("room").getValue().toString());
                    u.setWorksOn(dataSnapshot.child("worksOn").getValue().toString());

                    ArrayList<String> langList = new ArrayList<String>();
                    ArrayList<String> skillList = new ArrayList<String>();

                    for (DataSnapshot snap : dataSnapshot.child("languages").getChildren()) {
                        langList.add(snap.getValue().toString());
                    }
                    u.setLanguages(langList);
                    for (DataSnapshot snap : dataSnapshot.child("skills").getChildren()) {
                        skillList.add(snap.getValue().toString());
                    }
                    u.setSkills(skillList);

                    //Displays data
                    name.setText(u.getName());
                    lab.setText(u.getLab());
                    room.setText(u.getRoom());
                    mail.setText(u.getEmail());
                    worksOn.setText(u.getWorksOn());
                    lang.setText(u.getLanguages().toString());
                    skills.setText(u.getSkills().toString());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //Download and display Profile Pic
        mStorageRef = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://spibens-331c8.appspot.com/")
                .child("Members")
                .child( id );

        View vw = findViewById(R.id.header);

        CircleImageView im = (CircleImageView) findViewById(R.id.profilepic);

        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .centerCrop()
                .dontAnimate()
                .into(im);




    }
}
