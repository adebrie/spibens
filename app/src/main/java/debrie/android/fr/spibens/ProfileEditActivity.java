package debrie.android.fr.spibens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.Map;

public class ProfileEditActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference profileRef;
    private User u;
    private Map uMap;

    EditText name, lab, room, worksOn, skills, lang;

    String id, email, studies, section, startingYear;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.profileedit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_profile_editsend:
                u = new User();

                // Get data from user input
                u.setRoom(room.getText().toString());
                u.setName(name.getText().toString());
                u.setWorksOn(worksOn.getText().toString());
                u.setLab(lab.getText().toString());
                // Get other data from Intent extra
                u.setEmail(email);
                u.setSection(section);
                u.setStudies(studies);
                u.setStartingYear(startingYear);

                //Send updated info to firebase
                profileRef = FirebaseDatabase.getInstance().getReference("membersList");
                profileRef.child(String.valueOf(id)).setValue(u);

                //Switch to ProfileActivity
                Toast.makeText(getApplicationContext(), "Profile Info Updated", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ProfileEditActivity.this, ProfileActivity.class);
                i.putExtra("id", id);
                startActivity(i);
                break;

            case R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.action_getPicture:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar_edit_profile);
        setSupportActionBar(mytoolbar);
        getActionBar().setDisplayHomeAsUpEnabled(true);


        name = (EditText) findViewById(R.id.name_text);
        lab = (EditText) findViewById(R.id.lab_text);
        room = (EditText) findViewById(R.id.room_text);
        worksOn = (EditText) findViewById(R.id.workson_text);
        skills = (EditText) findViewById(R.id.skills_text);
        lang = (EditText) findViewById(R.id.lang_text);

        name.setHint(getIntent().getStringExtra("name"));
        lab.setHint(getIntent().getStringExtra("lab"));
        room.setHint(getIntent().getStringExtra("room"));
        worksOn.setHint(getIntent().getStringExtra("worksOn"));
        skills.setHint(getIntent().getStringExtra("skills"));
        lang.setHint(getIntent().getStringExtra("lang"));

        // No subscription preferences by default
        u.setHappyhour(false);
        u.setSports(false);


        // Get Intent Extra data
        id = getIntent().getStringExtra("id");
        studies = getIntent().getStringExtra("studies");
        section = getIntent().getStringExtra("section");
        startingYear = getIntent().getStringExtra("startingYear");
        email = getIntent().getStringExtra("email");


        //Download and display Profile Pic
        mStorageRef = FirebaseStorage.getInstance()
                .getReferenceFromUrl("gs://spibens-331c8.appspot.com/")
                .child("Members")
                .child(String.valueOf(id)+ ".jpg");
        ImageView im = (ImageView) findViewById(R.id.profileImageEdit);
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(im);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri file = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), file);
                ImageView imageView = (ImageView) findViewById(R.id.profileImageEdit);
                imageView.setImageBitmap(bitmap);

                mStorageRef = FirebaseStorage.getInstance()
                        .getReferenceFromUrl("gs://spibens-331c8.appspot.com/")
                        .child("Members")
                        .child(String.valueOf(id));


                mStorageRef.putFile(file);

                Toast.makeText(getApplicationContext(), "Picture uploaded", Toast.LENGTH_SHORT).show();
                System.out.println("STORAGE REF "+mStorageRef.toString());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Error onActivityResult");

    }

}
