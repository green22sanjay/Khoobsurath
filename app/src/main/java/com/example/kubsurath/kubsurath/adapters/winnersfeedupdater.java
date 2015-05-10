package com.example.kubsurath.kubsurath.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.kubsurath.kubsurath.Appcontroler.AppController;
import com.example.kubsurath.kubsurath.R;
import com.example.kubsurath.kubsurath.datmodels.winnersData;

import java.util.List;

/**
 * Created by dinesh on 24-Apr-15.
 */
public class winnersfeedupdater extends BaseAdapter {
    private static final String TAG = FeedListAdapter.class.getSimpleName();
    private Activity activity;
    private LayoutInflater inflater;
    private List<winnersData> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public winnersfeedupdater(Activity activity, List<winnersData> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.winnersitem, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);

        TextView posttitle = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);

        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);

        winnersData item = feedItems.get(position);

        name.setText(item.getUsrname());
        posttitle.setText(item.getUsremail());


        // Converting timestamp into x ago format


        // user profile pic
        profilePic.setImageUrl(item.getPropic(), imageLoader);



        return convertView;
    }

}
