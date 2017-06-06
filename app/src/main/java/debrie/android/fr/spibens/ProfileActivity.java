package debrie.android.fr.spibens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference profileRef;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final TextView mail = (TextView) findViewById(R.id.mail_text);
        final TextView name = (TextView) findViewById(R.id.name_text);
        final TextView lab = (TextView) findViewById(R.id.lab_text);
        final TextView room = (TextView) findViewById(R.id.room_text);
        final TextView worksOn = (TextView) findViewById(R.id.workson_text);
        final TextView skills = (TextView) findViewById(R.id.skills_text);
        final TextView lang = (TextView) findViewById(R.id.lang_text);


        final Long id = getIntent().getLongExtra("id", 1);

        profileRef = FirebaseDatabase.getInstance().getReference("membersList").child(id.toString());

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
                .child( id + ".jpg");
        ImageView im = (ImageView) findViewById(R.id.profileImage);
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(im);

        //OnClickListener for EDIT button
        Button b = (Button) findViewById(R.id.updateProfile);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this, ProfileEditActivity.class);
                i.putExtra("id", id);
                startActivity(i);
            }
        });

    }
}
