package debrie.android.fr.spibens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * An activity representing a list of Events. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EventDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EventListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private StorageReference storageReference;
    private String eventsType;
    private DatabaseReference SubscribeRef;
    boolean done=false;
    private boolean starred;
    private DatabaseReference eventsRef;
    List<EventContent.EventItem> items;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.happyh, menu);
        if(!done) {
            try{
            SubscribeRef = FirebaseDatabase.getInstance().getReference("membersList").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(eventsType);
            SubscribeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue().equals(true)) {
                        starred = true;
                        invalidateOptionsMenu();
                        done = true;
                    } else {
                        starred = false;
                        invalidateOptionsMenu();
                        done = true;
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });}catch (NullPointerException e){
                System.out.println("oupppps");
            }
        }
        if(starred){
            menu.getItem(0).setIcon(R.drawable.ic_action_star);
            starred=true;
        }else{
            menu.getItem(0).setIcon(R.drawable.ic_action_star_border);
            starred=false;
        }
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        items = new ArrayList<EventContent.EventItem>();
        eventsType = getIntent().getStringExtra("eventsType");
        switch (eventsType){
            case "sports" :
                getSupportActionBar().setTitle("Games");
                break;
            case "happyhour" :
                getSupportActionBar().setTitle("Happy Hour");
                break;
        }

        storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://spibens-331c8.appspot.com/").child("eventFlyer");

        eventsRef = FirebaseDatabase.getInstance().getReference("eventsList");
        
        eventsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> eventmap = (Map<String, Object>) dataSnapshot.getValue();

                if(eventmap.get("type").equals(eventsType)) {
                    EventContent.EventItem event = new EventContent.EventItem(eventmap.get("name").toString(), eventmap.get("date").toString(), eventmap.get("location").toString(), eventmap.get("description").toString(), Integer.parseInt(dataSnapshot.getKey()));
                    items.add(event);
                    invalidateView();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.event_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void invalidateView(){
        View recyclerView = findViewById(R.id.event_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(items));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<EventContent.EventItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<EventContent.EventItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mNameView.setText(mValues.get(position).getName());
            holder.mDateView.setText(mValues.get(position).getDate());
            holder.mLocationView.setText(mValues.get(position).getLocation());
            Glide.with(getApplication()).using(new FirebaseImageLoader()).load(storageReference.child(mValues.get(position).getName()+".jpg")).into(holder.mEventpicView);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(EventDetailFragment.ARG_ITEM_NAME, holder.mItem.getName());
                        arguments.putString(EventDetailFragment.ARG_ITEM_DESCRIPTION, holder.mItem.getDescription());
                        arguments.putString(EventDetailFragment.ARG_ITEM_LOCATION, holder.mItem.getLocation());
                        arguments.putString(EventDetailFragment.ARG_ITEM_DATE, holder.mItem.getDate());
                        arguments.putString(EventDetailFragment.ARG_ITEM_TYPE, eventsType);
                        EventDetailFragment fragment = new EventDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.event_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, EventDetailActivity.class);
                        intent.putExtra(EventDetailFragment.ARG_ITEM_NAME, holder.mItem.getName());
                        intent.putExtra(EventDetailFragment.ARG_ITEM_DESCRIPTION, holder.mItem.getDescription());
                        intent.putExtra(EventDetailFragment.ARG_ITEM_LOCATION, holder.mItem.getLocation());
                        intent.putExtra(EventDetailFragment.ARG_ITEM_DATE, holder.mItem.getDate());
                        intent.putExtra(EventDetailFragment.ARG_ITEM_TYPE, eventsType);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mEventpicView;
            public final TextView mNameView;
            public final TextView mLocationView;
            public final TextView mDateView;
            public EventContent.EventItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mEventpicView = (ImageView) view.findViewById(R.id.eventpic);
                mNameView = (TextView) view.findViewById(R.id.name);
                mLocationView = (TextView) view.findViewById(R.id.location);
                mDateView = (TextView) view.findViewById(R.id.date);;
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mNameView.getText() + "'";
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_favorite420:
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                if (!starred) {
                    SubscribeRef.setValue(true);
                    item.setIcon(R.drawable.ic_action_star);
                    fm.subscribeToTopic("Sports");
                    Toast.makeText(getApplicationContext(), "Subscribed to "+eventsType, Toast.LENGTH_SHORT).show();
                }
                else{
                    SubscribeRef.setValue(false);
                    item.setIcon(R.drawable.ic_action_star_border);
                    fm.unsubscribeFromTopic("Sports");
                    Toast.makeText(getApplicationContext(), "Unsubscribed from "+eventsType, Toast.LENGTH_SHORT).show();
                }
                starred = !starred;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
