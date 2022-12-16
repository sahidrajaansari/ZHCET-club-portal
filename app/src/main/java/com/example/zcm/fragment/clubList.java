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
import com.example.zcm.model.ModelClub;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class clubList extends Fragment {
    RecyclerView recyclerView;
    ClublistAdapter clublistAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public clubList() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_club_list,container,false);
        recyclerView=root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        FirebaseRecyclerOptions<ModelClub> options=new FirebaseRecyclerOptions.Builder<ModelClub>().setQuery(FirebaseDatabase.getInstance().getReference().child("clubdata"),ModelClub.class).build();
        clublistAdapter=new ClublistAdapter(options);
        recyclerView.setAdapter(clublistAdapter);
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_club_list, container, false);
        return root;
    }
    @Override
    public void onStart(){
        super.onStart();
        clublistAdapter.startListening();
    }
//    @Override
//    public void onStop(){
//        super.onStop();
//        clublistAdapter.stopListening();
//    }
}