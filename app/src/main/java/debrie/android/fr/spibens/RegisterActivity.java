package debrie.android.fr.spibens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private StorageReference mStorageRef;
    private DatabaseReference profileRef;
    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        final EditText mail = (EditText) findViewById(R.id.mail_text);
        final EditText name = (EditText) findViewById(R.id.name_text);
        final EditText lab = (EditText) findViewById(R.id.lab_text);
        final EditText room = (EditText) findViewById(R.id.room_text);
        final EditText worksOn = (EditText) findViewById(R.id.workson_text);
        final RadioGroup studies = (RadioGroup) findViewById(R.id.studies);
        final AutoCompleteTextView newskill = (AutoCompleteTextView) findViewById(R.id.skills_text);
        final ArrayAdapter<String> skills = new ArrayAdapter<String>(this, R.layout.activitylist);
        final ArrayAdapter<String> languages = new ArrayAdapter<String>(this, R.layout.activitylist);
        final EditText lang = (EditText) findViewById(R.id.lang_text);


        final FirebaseAuth mauth = FirebaseAuth.getInstance();

        profileRef = FirebaseDatabase.getInstance().getReference("membersList").child(mauth.getCurrentUser().getUid());

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
//                System.out.println("RADIO ID : "+studies.getCheckedRadioButtonId());
                switch (studies.getCheckedRadioButtonId()){
                    case 1 :
                        u.setStudies("PHD");
                        break;
                    case 2 :
                        u.setStudies("Post-Doc");
                        break;
                    case 3 :
                        u.setStudies("ITA");
                        break;
                }

                //profileRef = FirebaseDatabase.getInstance().getReference("membersList");

                //profileRef.child(String.valueOf(id)).setValue(u);


                profileRef.setValue(u);
            }
        });

    }

}
