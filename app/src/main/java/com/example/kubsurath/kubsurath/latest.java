package com.example.kubsurath.kubsurath;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kubsurath.kubsurath.Appcontroler.AppController;
import com.example.kubsurath.kubsurath.adapters.FeedListAdapter;
import com.example.kubsurath.kubsurath.datmodels.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class latest extends Fragment {

    private static final String TAG = latest.class.getSimpleName();
    private ListView listView;
    Activity mActivity;
    private FeedListAdapter listAdapter;
    private List<FeedItem> feedItems;
    private String URL_FEED = "http://andlog.jobstracer.com/postlikeloader.php";

    Toolbar mToolbar;

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RelativeLayout view = (RelativeLayout)inflater.inflate(R.layout.fragment_latest, container, false);
        setHasOptionsMenu(true);

        mToolbar = (Toolbar) view.findViewById(R.id.app_bar);
        ActionBarActivity activity = (ActionBarActivity) getActivity();
        activity.setSupportActionBar(mToolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        listView = (ListView) view.findViewById(R.id.list);

        feedItems = new ArrayList<FeedItem>();
        listAdapter = new FeedListAdapter(getActivity(), feedItems);

        listView.setAdapter(listAdapter);

   /*     Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Entry entry = cache.get(URL_FEED);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else { */
            // making fresh volley request and getting json
            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

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

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();

                item.setName(feedObj.getString("name"));

                // Image might be null sometimes
                String image = feedObj.isNull("posttilepicurl") ? null : feedObj
                        .getString("posttilepicurl");
                item.setPosturl(image);
                item.setPosttile(feedObj.getString("posttitle"));
                item.setPropic(feedObj.getString("propicurl"));
                item.setTextdec(feedObj.getString("7"));

                // url might be null sometimes
                String feedUrl = feedObj.isNull("posthashtag") ? null : feedObj
                        .getString("posthashtag");
                item.setPosthashtag(feedUrl);

                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override

    public void onCreateOptionsMenu(

            Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.fragment_menu, menu);



    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.Search:
                break;
            case R.id.Add:
                break;
            case R.id.Refresh:
                break;
        }
        return true;

        }




}

