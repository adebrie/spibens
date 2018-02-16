package debrie.android.fr.spibens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.support.v4.app.NavUtils.navigateUpFromSameTask;

/**
 * An activity representing a list of Members. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link MemberDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class  MemberListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private StorageReference storageReference;
    private DatabaseReference SubscribeRef;
    private DatabaseReference memberRef;
    List<MemberContent.MemberItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        items = new ArrayList<MemberContent.MemberItem>();

        storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://spibens-331c8.appspot.com/").child("Members");





        memberRef = FirebaseDatabase.getInstance().getReference("membersList");

        memberRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String, Object> membermap = (Map<String, Object>) dataSnapshot.getValue();

                    MemberContent.MemberItem memberItem = new MemberContent.MemberItem(membermap.get("name").toString(), membermap.get("studies").toString(), membermap.get("section").toString(), membermap.get("worksOn").toString(),membermap.get("room").toString(),membermap.get("lab").toString(),membermap.get("startingYear").toString(),membermap.get("email").toString(),dataSnapshot.getKey().toString());
                    items.add(memberItem);
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


        View recyclerView = findViewById(R.id.member_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.member_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void invalidateView(){
        View recyclerView = findViewById(R.id.member_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(items));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<MemberContent.MemberItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<MemberContent.MemberItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.member_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mNameView.setText(holder.mItem.getName());
            holder.mSectionView.setText(holder.mItem.getSection());
            holder.mStudiesView.setText(holder.mItem.getStudies());
            Glide.with(getApplication()).using(new FirebaseImageLoader()).load(storageReference.child(mValues.get(position).getId()+".jpg")).into(holder.mMemberpicView);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(MemberDetailFragment.ARG_ITEM_NAME, holder.mItem.getName());
                        arguments.putString(MemberDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                        arguments.putString(MemberDetailFragment.ARG_ITEM_SECTION, holder.mItem.getSection());
                        arguments.putString(MemberDetailFragment.ARG_ITEM_STUDIES, holder.mItem.getStudies());
                        arguments.putString(MemberDetailFragment.ARG_ITEM_ROOM, holder.mItem.getRoom());
                        arguments.putString(MemberDetailFragment.ARG_ITEM_LAB, holder.mItem.getLab());
                        arguments.putString(MemberDetailFragment.ARG_ITEM_EMAIL, holder.mItem.getEmail());
                        arguments.putString(MemberDetailFragment.ARG_ITEM_WORKSON, holder.mItem.getWorkson());
                        arguments.putString(MemberDetailFragment.ARG_ITEM_STARTYEAR, (holder.mItem.getStartyear()));
                        MemberDetailFragment fragment = new MemberDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.event_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, MemberDetailActivity.class);
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_ID, holder.mItem.getId());
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_NAME, holder.mItem.getName());
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_SECTION, holder.mItem.getSection());
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_STUDIES, holder.mItem.getStudies());
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_ROOM, holder.mItem.getRoom());
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_LAB, holder.mItem.getLab());
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_EMAIL, holder.mItem.getEmail());
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_WORKSON, holder.mItem.getWorkson());
                        intent.putExtra(MemberDetailFragment.ARG_ITEM_STARTYEAR, holder.mItem.getStartyear());
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
            public final ImageView mMemberpicView;
            public final TextView mNameView;
            public final TextView mSectionView;
            public final TextView mStudiesView;
            public MemberContent.MemberItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mMemberpicView = (ImageView) view.findViewById(R.id.memberspic);
                mNameView = (TextView) view.findViewById(R.id.name);
                mSectionView = (TextView) view.findViewById(R.id.section);
                mStudiesView = (TextView) view.findViewById(R.id.studies);;
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mNameView.getText() + "'";
            }
        }

    }
}
