package com.example.zcm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zcm.R;
import com.example.zcm.SinglePost;
import com.example.zcm.dataholder.Clubdata;
import com.example.zcm.dataholder.Postdata;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends FirebaseRecyclerAdapter<Postdata,PostAdapter.PostViewHolder> {
    Context context;
    public PostAdapter(@NonNull FirebaseRecyclerOptions<Postdata> options,Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Postdata model) {
//        holder.Clubname.setText(model.getPostedBy());
        holder.time.setText(model.getDate());
        String Path =model.getPostedBy();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("clubdata").child(Path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Clubdata clubdata = snapshot.getValue(Clubdata.class);
                holder.Clubname.setText(clubdata.getClubname());
                Glide.with(holder.profileImage.getContext()).load(clubdata.getProfileimage()).into(holder.profileImage);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Glide.with(holder.postImg.getContext()).load(model.getPostImage()).into(holder.postImg);
        holder.postDescription.setText(model.getPostDescription());
        holder.postDescription.setVisibility(View.VISIBLE);
        holder.postImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, SinglePost.class);
                intent.putExtra("postDescription",model.getPostDescription());
                intent.putExtra("postImg",model.getPostImage());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.demo_club_post,parent,false);
        return new PostViewHolder(view);
    }

    class PostViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profileImage;
        ImageView postImg;
        TextView Clubname,time,postDescription;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            Clubname=itemView.findViewById(R.id.Clubname);
            time=itemView.findViewById(R.id.time);
            postDescription=itemView.findViewById(R.id.postDescription);
            postImg=itemView.findViewById(R.id.postImg);
            profileImage=itemView.findViewById(R.id.profileImage);
        }
    }
}
