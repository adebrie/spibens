package debrie.android.fr.spibens;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private FirebaseAuth mAuth;
    private User u;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_profile_send_register:

                final RadioGroup studies = (RadioGroup) findViewById(R.id.studies);
                final RadioGroup section = (RadioGroup) findViewById(R.id.section);
                final EditText year = (EditText)findViewById(R.id.editText2);

                mAuth = FirebaseAuth.getInstance();
                profileRef = FirebaseDatabase.getInstance().getReference("membersList").child(mAuth.getCurrentUser().getUid());

                switch (section.getCheckedRadioButtonId()){
                    case 1 :
                        u.setSection("Developmental Biology");
                        break;
                    case 2 :
                        u.setSection("Ecology and Evolutionary Biology");
                        break;
                    case 3 :
                        u.setSection("Functional Genomics");
                        break;
                    case 4 :
                        u.setSection("Neuroscience");
                        break;
                }

                u.setStartingYear(year.getText().toString());

                profileRef.setValue(u.toMap());

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onStudiesRadioButtonClick(View view){
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.radioPHD:
                u.setStudies("PHD");
                break;
            case R.id.radioPostDoc:
                u.setStudies("PostDoc");
                break;
            case R.id.radioITA:
                u.setStudies("ITA");
        }

    }

    public void onSectionRadioButtonClick(View v){
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()){
            case R.id.radioDevelopmental:
                u.setSection("Developmental Biology");
                break;
            case R.id.radioEcology:
                u.setSection("Ecology and Evolutionary Biology");
                break;
            case R.id.radioFunctional:
                u.setSection("Functional Genomics");
                break;
            case R.id.radioNeuroscience:
                u.setSection("Neuroscience");
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar_register);
        setSupportActionBar(myToolbar);

    }

}
