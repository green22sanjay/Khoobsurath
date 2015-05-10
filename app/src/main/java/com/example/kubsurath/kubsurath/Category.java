package com.example.kubsurath.kubsurath;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kubsurath.kubsurath.Appcontroler.AppController;
import com.example.kubsurath.kubsurath.adapters.CategoryRecyAdapter;
import com.example.kubsurath.kubsurath.datmodels.Categoryfeeditems;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 31-Mar-15.
 */
public class Category extends Fragment {

    private static final String TAG = Category.class.getSimpleName();
    private ListView listView;
    Activity mActivity;
    private CategoryRecyAdapter catAdapter;
    private List<Categoryfeeditems> mCategoryfeeditems ;

    Drawable drawable;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout view = (RelativeLayout)inflater.inflate(R.layout.category_fragment, container, false);
        View vl = View.inflate(getActivity(), R.layout.cathead, null);
        listView = (ListView) view.findViewById(R.id.list);
        listView.addHeaderView(vl);
        mCategoryfeeditems = new ArrayList<Categoryfeeditems>();
        catAdapter = new CategoryRecyAdapter(getActivity(), mCategoryfeeditems);
        listView.setClickable(true);
       // listView.setBackgroundResource(R.drawable.round_background_green);
      //  listView.addHeaderView(View.inflate(getActivity(),R.layout.cathead,view));


       String urlFrImage = "http://andlog.jobstracer.com/category.php";
    /*    Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(urlFrImage);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException f) {
                    f.printStackTrace();
                }
            } catch (UnsupportedEncodingException f) {
                f.printStackTrace();
            }

        } else { */
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    urlFrImage, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            // Adding request to volley request queue
            AppController.getInstance().addToRequestQueue(jsonReq);


        return view;
    }
    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("category");
            if (null == mCategoryfeeditems) {
                mCategoryfeeditems = new ArrayList<Categoryfeeditems>();
            }

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                Categoryfeeditems itemsl = new Categoryfeeditems();
                itemsl.setCatid(feedObj.getString("orderid"));
                itemsl.setCatname(feedObj.getString("categoryname"));
                itemsl.setCatimg(feedObj.getString("categoryimg"));

                // Image might be null sometimes
             /*   String ila =feedObj.getString("status");

                itemsl.setProEmail(feedObj.getString("status"));
                itemsl.setProPicImg(feedObj.getString("profilePic")); */


                // url might be null sometimes


                mCategoryfeeditems.add(itemsl);
                // Toast.makeText(getActivity(), itemsl.getProPicImg(), Toast.LENGTH_LONG).show();

              /*  }
                catch (Exception or){
                    Toast.makeText(getActivity(),ila,Toast.LENGTH_LONG).show();
                    Log.e(TAG, "add data exception", or);
                }*/
            }

            // notify data changes to list adapater

        } catch (JSONException e) {
            e.printStackTrace();

        }
        listView.setAdapter(catAdapter);
    }

}