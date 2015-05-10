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
import com.example.kubsurath.kubsurath.datmodels.Categoryfeeditems;

import java.util.List;

/**
 * Created by dinesh on 15-Apr-15.
 */
public class CategoryRecyAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private static final String TAG = CategoryRecyAdapter.class.getSimpleName();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private List<Categoryfeeditems> mCategoryfeeditems;
    private Context mContext;

    public CategoryRecyAdapter(Activity activity, List<Categoryfeeditems> mCategoryfeeditems) {
        this.mCategoryfeeditems = mCategoryfeeditems;
        this.activity = activity;
    }

   @Override
    public int getCount() {
        return mCategoryfeeditems.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryfeeditems.get(position);
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
            convertView = inflater.inflate(R.layout.categoryitemview, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.CatTilte);
        Network ImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.CateImg);

        Categoryfeeditems catfeeditems = mCategoryfeeditems.get(position);

        name.setText(catfeeditems.getCatname());
        String se = "http://"+catfeeditems.getCatimg();
        networkImageView.setImageUrl(se,imageLoader);
        networkImageView.setErrorImageResId(R.drawable.ic_action_action_account_child);


        return convertView;
    }
}
