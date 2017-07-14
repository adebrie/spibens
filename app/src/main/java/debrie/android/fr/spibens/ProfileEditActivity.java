package debrie.android.fr.spibens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileEditActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference profileRef;
    private User u;
    private Map uMap;

    EditText mail, name, lab, room, worksOn, skills, lang;

    String id;



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
                u.setRoom(room.getText().toString());
                u.setName(name.getText().toString());
                u.setEmail(mail.getText().toString());
                u.setWorksOn(worksOn.getText().toString());
                u.setLab(lab.getText().toString());

                profileRef = FirebaseDatabase.getInstance().getReference("membersList");
                profileRef.child(String.valueOf(id)).setValue(u);
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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 234);
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
//        getActionBar().setDisplayHomeAsUpEnabled(true);


        mail = (EditText) findViewById(R.id.mail_text);
        name = (EditText) findViewById(R.id.name_text);
        lab = (EditText) findViewById(R.id.lab_text);
        room = (EditText) findViewById(R.id.room_text);
        worksOn = (EditText) findViewById(R.id.workson_text);
        skills = (EditText) findViewById(R.id.skills_text);
        lang = (EditText) findViewById(R.id.lang_text);

        id = getIntent().getStringExtra("id");

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
                        .child(String.valueOf(id)+ ".jpg");

                mStorageRef.putFile(file);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
