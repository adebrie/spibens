package debrie.android.fr.spibens;

import android.widget.ArrayAdapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

/**
 * Created by alex on 7/7/17.
 */



public class CustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> names;
    private final ArrayList<String> uids;


    public CustomAdapter(Activity context) {
        super(context, R.layout.activitylist);
        this.context=context;
        names = new ArrayList<>();
        uids = new ArrayList<>();
    }


    public void add(String name, String uid){
        names.add(name);
        uids.add(uid);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.memberitem, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.memberpic);

        System.out.println(position);
        txtTitle.setText(names.get(position));

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://spibens-331c8.appspot.com/")
                .child("Members")
                .child( uids.get(position) + ".jpg");

        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(mStorageRef)
                .into(imageView);
        System.out.println("Glide OK");


        return rowView;

    };
}