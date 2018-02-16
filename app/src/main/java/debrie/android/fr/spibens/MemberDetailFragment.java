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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A fragment representing a single Member detail screen.
 * This fragment is either contained in a {@link MemberListActivity}
 * in two-pane mode (on tablets) or a {@link MemberDetailActivity}
 * on handsets.
 */
public class MemberDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */

    public static final String ARG_ITEM_NAME = "item_name";
    public static final String ARG_ITEM_SECTION = "item_section";
    public static final String ARG_ITEM_STUDIES= "item_studies";
    public static final String ARG_ITEM_WORKSON = "item_worksOn";
    public static final String ARG_ITEM_LAB = "item_lab";
    public static final String ARG_ITEM_ROOM = "item_room";
    public static final String ARG_ITEM_STARTYEAR = "item_startyear";
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_EMAIL = "item_email";

    /**
     * The dummy content this fragment is presenting.
     */
    private MemberContent.MemberItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MemberDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println(getArguments());
        System.out.println(getArguments().get(ARG_ITEM_ID).toString());
        System.out.println(getArguments().get(ARG_ITEM_STARTYEAR).toString());

        mItem = new MemberContent.MemberItem(
                getArguments().get(ARG_ITEM_NAME).toString(),
                getArguments().get(ARG_ITEM_STUDIES).toString(),
                getArguments().get(ARG_ITEM_SECTION).toString(),
                getArguments().get(ARG_ITEM_WORKSON).toString(),
                getArguments().get(ARG_ITEM_ROOM).toString(),
                getArguments().get(ARG_ITEM_LAB).toString(),
                getArguments().get(ARG_ITEM_STARTYEAR).toString(),
                getArguments().get(ARG_ITEM_EMAIL).toString(),
                getArguments().get(ARG_ITEM_ID).toString() );


        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mItem.getName());
            ImageView imageView =(ImageView) appBarLayout.findViewById(R.id.member_detail_pic);
            StorageReference storageReference= FirebaseStorage.getInstance().getReferenceFromUrl("gs://spibens-331c8.appspot.com/").child("Members");

            Glide.with(this.getContext())
                    .using(new FirebaseImageLoader())
                    .load(storageReference.child(mItem.getId()+".jpg"))
                    .into(imageView);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.member_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.member_section)).setText("Section : "+mItem.getSection());
            ((TextView) rootView.findViewById(R.id.member_studies)).setText("Studies : "+mItem.getStudies());
            ((TextView) rootView.findViewById(R.id.member_startyear)).setText("Starting year : "+mItem.getStartyear());
            ((TextView) rootView.findViewById(R.id.member_workson)).setText("Works on : "+mItem.getWorkson());
            ((TextView) rootView.findViewById(R.id.member_room)).setText("Room : "+mItem.getRoom());
            ((TextView) rootView.findViewById(R.id.member_email)).setText("Contact : "+mItem.getEmail());
            ((TextView) rootView.findViewById(R.id.member_lab)).setText("Lab : "+mItem.getLab());
        }

        return rootView;
    }
}
