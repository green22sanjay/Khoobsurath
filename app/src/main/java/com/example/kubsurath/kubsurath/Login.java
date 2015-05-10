package com.example.kubsurath.kubsurath;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kubsurath.kubsurath.Appcontroler.AppController;
import com.example.kubsurath.kubsurath.datmodels.LoginRecyclerDataItems;
import com.example.kubsurath.kubsurath.datmodels.LoginRecyclerViewadapter;
import com.example.kubsurath.kubsurath.sessionmanager.SessionManager;
import com.example.kubsurath.kubsurath.sqlhandler.SQLiteHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 31-Mar-15.
 */
public class Login extends Fragment {

    private ListView lvHomePage;
    private static final String TAG = MainActivity.class.getSimpleName();
    private SessionManager session;
    private String[] items;
    RecyclerView r_view;
    String text[] = {"Profile",
            "Friends",
            "Chats",
            "Your Posts",
            "Favorite",
            "View Points",
            "Account Settings",
            "Winner Rules",
            "Terms & Policies",
            "Rate Us",
            "Logout",
            "Report a Problem"};
    int imgs[] = {R.drawable.ic_profilelogin_circle,
            R.drawable.ic_login_friends,
            R.drawable.ic_login_chat,
            R.drawable.ic_login_your_post,
            R.drawable.ic_login_favorite,
            R.drawable.ic_login_view_points,
            R.drawable.ic_login_actount_settings,
            R.drawable.ic_login_winner_rules,
            R.drawable.ic_login_terms_and_polocies,
            R.drawable.ic_login_rate_us,
            R.drawable.ic_login_logout,
            R.drawable.ic_login_report_problem};
    private LoginRecyclerViewadapter m_recycler;
    private SQLiteHandler db;
    private ProgressDialog pDialog;
    private String proname;
    private String proemail;
    private String prourl;

  //
  //  private List<LoginRecyclerDataItems> mLoginRecyclerDataItems ;
    private List<LoginRecyclerDataItems> mLoginRecyclerDataItems = new ArrayList<LoginRecyclerDataItems>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = new View(getActivity());
        session = new SessionManager(getActivity());
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            v = inflater.inflate(R.layout.afterloginfragment, container, false);
            m_recycler = new LoginRecyclerViewadapter(getActivity(),mLoginRecyclerDataItems,text, imgs);

            r_view = (RecyclerView) v.findViewById(R.id.RecyclerView);
            r_view.setLayoutManager(new LinearLayoutManager(getActivity()));
            r_view.setHasFixedSize(true);
            r_view.setItemAnimator(new DefaultItemAnimator());

            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("AfterLogin", 0);
            String getloginid = pref.getString("Loginid", null);

            String urlFrImage = "http://andlog.jobstracer.com/propicupdater.php?id="+getloginid;
          AppController.getInstance().getRequestQueue().getCache().invalidate(urlFrImage,true);

            Log.e(TAG,urlFrImage);
          /* Cache cache = AppController.getInstance().getRequestQueue().getCache();

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


            final GestureDetector mGestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            r_view.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
               @Override
                  public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    View child = r_view.findChildViewUnder(e.getX(), e.getY());


                                                  if (child != null && mGestureDetector.onTouchEvent(e)) {
                                                      SharedPreferences pref = getActivity().getSharedPreferences("AfterLogin", 0);
                                                      switch (r_view.getChildPosition(child)) {

                                                          case 0:

                                                              String getloginid = pref.getString("Loginid", null);

                                                            //  String urlJsonObj = "http://api.androidhive.info/volley/person_object.json";
                                                            //  String urlFrImage= urlJsonObj+"?id="+getloginid;

                                                              break;

                                                          case 11:
                                                              pDialog = new ProgressDialog(getActivity());
                                                              pDialog.setCancelable(false);
                                                              pDialog.setMessage("Logging Out ...");
                                                              showDialog();
                                                              session.setLogin(false);
                                                            //  SharedPreferences prefr = getActivity().getApplicationContext().getSharedPreferences("AfterLogin", 0); // 0 - for private mode
                                                              SharedPreferences.Editor editor = pref.edit();
                                                              editor.clear();
                                                              editor.apply();
                                                              db = new SQLiteHandler(getActivity().getApplicationContext());
                                                              db.deleteUsers();

                                                              // Launching the login activity
                                                              Intent intent = new Intent(getActivity(), MainActivity.class);
                                                              startActivity(intent);
                                                              break;


                                                      }




                                                  }

                                                  return false;
                                              }


                                              @Override
                                              public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                                              }
                                          }
            );


            r_view.setAdapter(m_recycler);

        } else {

            v = inflater.inflate(R.layout.login_fragment, container, false);


            items = getActivity().getResources().getStringArray(R.array.adobe_products);

            ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
            lvHomePage = (ListView) v.findViewById(R.id.listLogin);
            lvHomePage.setAdapter(aa);
            lvHomePage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    switch (position) {

                        // Open HomeFragment.java
                        case 0:
                            Intent i = new Intent(getActivity(), RegisterActivity.class);
                            startActivity(i);
                            break;
                        // Open PlaceOrderFragment.java
                        case 1:
                            Intent i2 = new Intent(getActivity(), LoginActivity.class);
                            startActivity(i2);
                            break;

                        default:
                            break;


                    }

                }

            });


        }
        return v;
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override

    public void onResume (){
        super.onResume();
        mLoginRecyclerDataItems.clear();
    }

    public static long getMinutesDifference(long timeStart,long timeStop){
        long diff = timeStop - timeStart;
        long diffMinutes = diff / (60 * 1000);

        return  diffMinutes;
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("category");
            if (null == mLoginRecyclerDataItems) {
                mLoginRecyclerDataItems = new ArrayList<LoginRecyclerDataItems>();
            }

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                LoginRecyclerDataItems itemsl = new LoginRecyclerDataItems();

                itemsl.setProName(feedObj.getString("name"));

                // Image might be null sometimes


                itemsl.setProEmail(feedObj.getString("email"));

                String feedUrl = feedObj.isNull("propicurl") ? null : feedObj
                        .getString("propicurl");

                itemsl.setProPicImg(feedUrl);


                // url might be null sometimes


                    mLoginRecyclerDataItems.add(itemsl);
               // Toast.makeText(getActivity(), itemsl.getProPicImg(), Toast.LENGTH_LONG).show();

              /*  }
                catch (Exception or){
                    Toast.makeText(getActivity(),ila,Toast.LENGTH_LONG).show();
                    Log.e(TAG, "add data exception", or);
                }*/
            }

            m_recycler.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();

        }

    }


}