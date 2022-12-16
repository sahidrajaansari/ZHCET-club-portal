package com.example.zcm.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zcm.R;
import com.example.zcm.adapter.ClublistAdapter;
import com.example.zcm.adapter.PostAdapter;
import com.example.zcm.dataholder.Postdata;
import com.example.zcm.model.ModelClub;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class Home extends Fragment {
    RecyclerView recyclerView;
    PostAdapter postAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_club_list,container,false);
        recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        FirebaseRecyclerOptions<Postdata> options=new FirebaseRecyclerOptions.Builder<Postdata>().setQuery(FirebaseDatabase.getInstance().getReference().child("Post").orderByChild("postedAt"),Postdata.class).build();
        postAdapter=new PostAdapter(options,Home.this.getContext());
        recyclerView.setAdapter(postAdapter);
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }
    @Override
    public void onStart(){
        super.onStart();
        postAdapter.startListening();
    }
//    @Override
//    public void onStop(){
//        super.onStop();
//        postAdapter.stopListening();
//    }
}