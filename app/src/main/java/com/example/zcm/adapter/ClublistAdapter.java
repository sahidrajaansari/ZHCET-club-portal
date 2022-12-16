package com.example.zcm.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zcm.R;
import com.example.zcm.model.ModelClub;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClublistAdapter extends FirebaseRecyclerAdapter<ModelClub,ClublistAdapter.ClubViewHolder> {

    public ClublistAdapter(@NonNull FirebaseRecyclerOptions<ModelClub> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ClubViewHolder holder, int position, @NonNull ModelClub model) {
        String check="1";
            holder.Clubname.setText(model.getClubname());
            holder.admin.setText(model.getUser());
            if(model.getStatus().equals(check)){
//                holder.Clubname.setText(model.getClubname());
//                holder.admin.setText(model.getUser());
                holder.status.setText("Status :"+" Validated");
            }
            else{
                holder.status.setText("Status :"+" Pending");
            }
        Glide.with(holder.profileImage.getContext()).load(model.getProfileimage()).into(holder.profileImage);
    }

    @NonNull
    @Override
    public ClubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.clublist_model,parent,false);
        return new ClubViewHolder(view);
    }


    class ClubViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profileImage;
        TextView Clubname,admin,status;
        public ClubViewHolder(@NonNull View itemView) {
            super(itemView);
//            profileImage=(ImageView)itemView.findViewById();
            profileImage=itemView.findViewById(R.id.profileImage);
            Clubname=itemView.findViewById(R.id.Clubname);
            admin=itemView.findViewById(R.id.admin);
            status=itemView.findViewById(R.id.status);
        }
    }
}
