package debrie.android.fr.spibens;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class HappyHourActivity extends AppCompatActivity {

    boolean starred = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.happyh, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happyh);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        final StorageReference mStorageRef;
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://spibens-331c8.appspot.com/").child("HappyHour").child("flyer.png");

        ImageView im = (ImageView) findViewById(R.id.flyer_im);
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(im);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite:
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                if (!starred) {
                    item.setIcon(R.drawable.ic_action_star);
                    fm.subscribeToTopic("HappyHour");
                    Toast.makeText(getApplicationContext(), "Subscribed to HappyHour", Toast.LENGTH_SHORT).show();
                }
                else{
                    item.setIcon(R.drawable.ic_action_star_border);
                    fm.unsubscribeFromTopic("HappyHour");
                    Toast.makeText(getApplicationContext(), "Unsubscribed from HappyHour", Toast.LENGTH_SHORT).show();
                }
                starred = !starred;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
