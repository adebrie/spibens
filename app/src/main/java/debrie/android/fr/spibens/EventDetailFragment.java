package debrie.android.fr.spibens;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

/**
 * A fragment representing a single Event detail screen.
 * This fragment is either contained in a {@link EventListActivity}
 * in two-pane mode (on tablets) or a {@link EventDetailActivity}
 * on handsets.
 */
public class EventDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_NAME = "item_name";
    public static final String ARG_ITEM_DESCRIPTION = "item_description";
    public static final String ARG_ITEM_LOCATION= "item_location";
    public static final String ARG_ITEM_DATE = "item_date";
    public static final String ARG_ITEM_TYPE = "item_type";

    /**
     * The dummy content this fragment is presenting.
     */
    private EventContent.EventItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("eventsList");

        mItem = new EventContent.EventItem(getArguments().get(ARG_ITEM_NAME).toString(),getArguments().get(ARG_ITEM_DATE).toString(),getArguments().get(ARG_ITEM_LOCATION).toString(),getArguments().get(ARG_ITEM_DESCRIPTION).toString(),null );


        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getName());
            ImageView imageView =(ImageView) appBarLayout.findViewById(R.id.detail_pic);
            StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://spibens-331c8.appspot.com/").child("eventFlyer");

            Glide.with(this.getContext())
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(mItem.getName()+".jpg"))
                    .into(imageView);

        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.event_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.event_detail)).setText(mItem.getDescription());
            ((TextView) rootView.findViewById(R.id.event_date)).setText(mItem.getDate());
            ((TextView) rootView.findViewById(R.id.event_location)).setText(mItem.getLocation());

        }


        return rootView;
    }
}
