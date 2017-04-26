package debrie.android.fr.spibens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class ProfileEditActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference profileRef;
    private User u;
    private Map uMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        final EditText mail = (EditText) findViewById(R.id.mail_text);
        final EditText name = (EditText) findViewById(R.id.name_text);
        final EditText lab = (EditText) findViewById(R.id.lab_text);
        final EditText room = (EditText) findViewById(R.id.room_text);
        final EditText worksOn = (EditText) findViewById(R.id.workson_text);
        final EditText skills = (EditText) findViewById(R.id.skills_text);
        final EditText lang = (EditText) findViewById(R.id.lang_text);


        final Long id = getIntent().getLongExtra("id", 11);

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

        //SEND UPDATE OnClickListener
        final Button b = (Button) findViewById(R.id.updateSend);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = new User();
                u.setRoom(room.getText().toString());
                u.setName(name.getText().toString());
                u.setEmail(mail.getText().toString());
                u.setWorksOn(worksOn.getText().toString());
                u.setLab(lab.getText().toString());

                profileRef = FirebaseDatabase.getInstance().getReference("membersList");

                profileRef.child(String.valueOf(id)).setValue(u);

            }
        });

    }

}
