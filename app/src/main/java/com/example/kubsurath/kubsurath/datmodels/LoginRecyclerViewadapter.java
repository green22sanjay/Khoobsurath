package com.example.kubsurath.kubsurath.datmodels;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.kubsurath.kubsurath.Addpropic;
import com.example.kubsurath.kubsurath.Appcontroler.AppController;
import com.example.kubsurath.kubsurath.R;

import java.util.List;

/**
 * Created by dinesh on 08-Apr-15.
 */
public class LoginRecyclerViewadapter extends RecyclerView.Adapter<LoginRecyclerViewadapter.myholder>
{
    private Activity activity;
    private static final String TAG = LoginRecyclerViewadapter.class.getSimpleName();
    String texts[];
    int ids[];
    final static int VIEW_HEADER=0;
    final static int VIEW_ROW=1;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private List<LoginRecyclerDataItems> mLoginRecyclerDataItems;
    private Context mContext;





    private String jsonResponse;
    private String proname;
    private String proemail;
    private String prourl;


    public LoginRecyclerViewadapter(Context context,List<LoginRecyclerDataItems> mLoginRecyclerDataItems,String[] texts, int[] ids) {
        this.mContext = context;
        this.mLoginRecyclerDataItems =mLoginRecyclerDataItems;
        this.texts = texts;
        this.ids = ids;
    }


    @Override
    public int getItemViewType(int position) {
        if(position==0)
            return VIEW_HEADER;
        else
            return VIEW_ROW;
    }

    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater lf=LayoutInflater.from(parent.getContext());
        View v;

        if(viewType==VIEW_HEADER) {
            v = lf.inflate(R.layout.login_header_view, parent, false);
            myholder m=new myholder(v,VIEW_HEADER);
            return m;
        }
        else {
            v = lf.inflate(R.layout.login_row_view, parent, false);

            myholder m=new myholder(v,VIEW_ROW);
            return m;

        }

    }

    public int getCountss() {
        return mLoginRecyclerDataItems.size();
    }


    public Object getItemss(int location) {
        return mLoginRecyclerDataItems.get(location);
    }

    @Override
    public long getItemId(int lone) {
        return lone;
    }

    @Override
    public void onBindViewHolder(myholder holder, int position) {
        if(position!=0) {
            holder.iconImg.setImageResource(ids[position-1]);

            holder.name.setText(texts[position-1]);
        }
        else
        {

            try {

               // LoginRecyclerDataItems itemr= mLoginRecyclerDataItems.get(position);
                LoginRecyclerDataItems itemr = mLoginRecyclerDataItems.get(position);
                String prurl=itemr.getProPicImg();

                    try {
                      holder.cimage.setImageUrl(itemr.getProPicImg(), imageLoader);
                        holder.cimage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intentl = new Intent(mContext, Addpropic.class);
                                mContext.startActivity(intentl);
                            }
                        });
                        holder.cimage.setErrorImageResId(R.drawable.ic_action_action_account_child);
                        holder.cimage.setDefaultImageResId(R.drawable.ic_action_action_account_child);
                 //      holder.cimage.setImageUrl(itemr.getProPicImg(),imageLoader);
                    //  holder.cimage.setErrorImageResId(R.drawable.ic_action_action_account_child);
                        holder.email.setText(itemr.getProEmail());
                        holder.name.setText(itemr.getProName());
                    }catch (Exception le){
                        Log.e(TAG,prurl,le);
                    }

            }catch (Exception lr){

                Log.e(TAG,"recyclelist cannoot add data",lr);

            }


        }
    }

    @Override
    public int getItemCount() {

        return ids.length+1;
    }

    public static class myholder extends RecyclerView.ViewHolder
    {
        NetworkImageView cimage;
        ImageView iconImg;
        TextView name;
        TextView email;


        public myholder(View itemView,int id) {
            super(itemView);
            if(id==VIEW_HEADER)
            {
                cimage= (NetworkImageView) itemView.findViewById(R.id.Proimage);
                email= (TextView) itemView.findViewById(R.id.email);
                name=(TextView)itemView.findViewById(R.id.name);
            }
            else
            {
                iconImg= (ImageView)itemView.findViewById(R.id.icon);
                name=(TextView)itemView.findViewById(R.id.text);
            }

        }
    }

}