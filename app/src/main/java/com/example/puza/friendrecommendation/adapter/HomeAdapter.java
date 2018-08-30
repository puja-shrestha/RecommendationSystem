package com.example.puza.friendrecommendation.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.puza.friendrecommendation.R;
import com.example.puza.friendrecommendation.fragments.MoreFragment;
import com.example.puza.friendrecommendation.fragments.UserDetailsFragment;
import com.example.puza.friendrecommendation.model.User;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    private List<User> itemList;
    Activity context;
    ProgressDialog progressDialog;
    Fragment fragment;
    private User items;
    SharedPreferences pref; // 0 - for private mode
    SharedPreferences.Editor editor;

    public HomeAdapter(FragmentManager manager) {
        super();
    }


    public HomeAdapter(Activity context, List<User> itemList) {
        this.itemList = itemList;
        this.context = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, description, btn;
        private ImageView image;
        private CardView cardView;

        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            image = (ImageView) view.findViewById(R.id.image);
            btn = (Button) view.findViewById(R.id.btn);
            cardView = (CardView) view.findViewById(R.id.dateRecycler);

        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_user, parent, false);
        pref= context.getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        items = itemList.get(position);
        final String name = items.getFirst_name()+ " " + items.getLast_name();
        holder.name.setText(name);
        holder.description.setText(items.getDescription());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor.putString("name", name);
                editor.putString("email", items.getEmail());
                editor.putString("mobile", items.getMobile());
                editor.putString("address", items.getAddress());
                editor.putString("dob", items.getDob());
                editor.putString("description", items.getDescription());
                editor.commit();
                transport("card");
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v("itemList", itemList.size()+"");
        return itemList.size();
    }


    private void transport(String fragmentName){
        fragment = null;
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();

        switch (fragmentName) {
            case "card":
                fragment = new UserDetailsFragment();
                break;
        }

        if (fragment != null){
            fragmentManager.beginTransaction().replace(R.id.frame_container,fragment).addToBackStack(null).commit();
        }
    }

}
