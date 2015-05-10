package com.example.kubsurath.kubsurath;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kubsurath.kubsurath.datmodels.LoginRecyclerDataItems;
import com.example.kubsurath.kubsurath.datmodels.LoginRecyclerViewadapter;
import com.example.kubsurath.kubsurath.sessionmanager.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinesh on 10-Apr-15.
 */
public class AfterLoginFragment extends Fragment {
    private String[] items;
    RecyclerView r_view;
    String text[] = {"Profile","Friends","Chats","Your Posts","Favorite","View Points","Account Settings","Winner Rules","Terms & Policies","Rate Us","Logout","Report a Problem"};
    int imgs[] = {R.drawable.ic_profilelogin_circle,
            R.drawable.ic_login_friends,
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
    private SessionManager session;
    private LoginRecyclerViewadapter m_recycler;
    private List<LoginRecyclerDataItems> mLoginRecyclerDataItems = new ArrayList<LoginRecyclerDataItems>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        m_recycler =new LoginRecyclerViewadapter(getActivity(),mLoginRecyclerDataItems,text,imgs);

        r_view =(RecyclerView)view.findViewById(R.id.RecyclerView);
        r_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        r_view.setHasFixedSize(true);
        r_view.setAdapter(m_recycler);

        return view;

    }


}

