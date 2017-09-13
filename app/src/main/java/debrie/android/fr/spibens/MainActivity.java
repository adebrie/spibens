package debrie.android.fr.spibens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private StorageReference storageReference;
    private DatabaseReference eventsRef;
    List<EventContent.EventItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upcoming Events");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://spibens-331c8.appspot.com/").child("eventFlyer");

        eventsRef = FirebaseDatabase.getInstance().getReference("eventsList");
        items = new ArrayList<EventContent.EventItem>();

        eventsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> eventmap = (Map<String, Object>) dataSnapshot.getValue();

                System.out.println(eventmap);
                    EventContent.EventItem event = new EventContent.EventItem(eventmap.get("name").toString(),
                            eventmap.get("date").toString(),
                            eventmap.get("location").toString(),
                            eventmap.get("description").toString(),
                            Integer.parseInt(dataSnapshot.getKey()));
                    items.add(event);
                    invalidateView();

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
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        System.out.println("COUCOUCOUCOU");

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (id == R.id.nav_search) {

        } else if (id == R.id.nav_happyhour) {
            Intent i = new Intent(MainActivity.this, EventListActivity.class);
            i.putExtra("eventsType","happyhour");
            startActivity(i);
        } else if (id == R.id.nav_sport) {
            Intent i = new Intent(MainActivity.this, EventListActivity.class);
            i.putExtra("eventsType","sports");
            startActivity(i);

        } else if (id == R.id.nav_profile) {
            Intent i = new Intent(MainActivity.this, ProfileActivity.class);
            i.putExtra("id", mAuth.getCurrentUser().getUid());
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter2(items));
    }

    private void invalidateView(){
        View recyclerView = findViewById(R.id.event_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }


    public class SimpleItemRecyclerViewAdapter2
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter2.ViewHolder> {

        private final List<EventContent.EventItem> mValues;

        public SimpleItemRecyclerViewAdapter2(List<EventContent.EventItem> items) {
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

                    Context context = v.getContext();
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra(EventDetailFragment.ARG_ITEM_NAME, holder.mItem.getName());
                    intent.putExtra(EventDetailFragment.ARG_ITEM_DESCRIPTION, holder.mItem.getDescription());
                    intent.putExtra(EventDetailFragment.ARG_ITEM_LOCATION, holder.mItem.getLocation());
                    intent.putExtra(EventDetailFragment.ARG_ITEM_DATE, holder.mItem.getDate());
                    intent.putExtra(EventDetailFragment.ARG_ITEM_TYPE, "test");

                    context.startActivity(intent);

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


}
