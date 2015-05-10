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
import com.example.kubsurath.kubsurath.datmodels.FeedItem;
import com.example.kubsurath.kubsurath.imageLoader.FeedImageView;

import java.util.List;

public class FeedListAdapter  extends BaseAdapter {
    private static final String TAG = FeedListAdapter.class.getSimpleName();
    private Activity activity;
    private LayoutInflater inflater;
    private List<FeedItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
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
            convertView = inflater.inflate(R.layout.feed_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);

            TextView posttitle = (TextView) convertView
                .findViewById(R.id.txtStatusMsg);
        TextView hashtag = (TextView) convertView.findViewById(R.id.txtUrl);
        NetworkImageView profilePic = (NetworkImageView) convertView
                .findViewById(R.id.profilePic);
        FeedImageView feedImageView = (FeedImageView) convertView
                .findViewById(R.id.feedImage1);
        TextView txtdesc =(TextView) convertView.findViewById(R.id.textdesc);
        FeedItem item = feedItems.get(position);

        name.setText(item.getName());
        posttitle.setText(item.getPosttile());
        hashtag.setText(item.getPosthashtag());
        txtdesc.setText(item.getTextdec());

        // Converting timestamp into x ago format


        // user profile pic
        profilePic.setImageUrl(item.getPropic(), imageLoader);

        // Feed image
        if (item.getPosturl() != null) {
            feedImageView.setImageUrl(item.getPosturl(), imageLoader);
            feedImageView.setVisibility(View.VISIBLE);
            feedImageView
                    .setResponseObserver(new FeedImageView.ResponseObserver() {
                        @Override
                        public void onError() {
                        }

                        @Override
                        public void onSuccess() {
                        }
                    });
        } else {
            feedImageView.setVisibility(View.GONE);
        }

        return convertView;
    }

}
