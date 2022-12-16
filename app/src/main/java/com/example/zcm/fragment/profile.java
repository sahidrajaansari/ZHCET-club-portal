package com.example.zcm.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zcm.R;
import com.example.zcm.dataholder.Clubdata;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class profile extends Fragment {
    String Path;
    public ProgressBar progressBar;
    TextView Clubname,description,adminName;
    ImageView selectProfileImage;
    CircleImageView profileImage;
    FirebaseStorage storage;
    FirebaseDatabase database;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    public profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root=(ViewGroup) inflater.inflate(R.layout.fragment_profile,container,false);

        adminName=root.findViewById(R.id.adminName);
        progressBar=root.findViewById(R.id.progressbar);
        Path=getActivity().getIntent().getExtras().getString("number");
        profileImage=root.findViewById(R.id.profileImage);
        Clubname=root.findViewById(R.id.Clubname);
        selectProfileImage=root.findViewById(R.id.selectProfileImage);
        description=root.findViewById(R.id.description);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("clubdata").child(Path);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Clubdata clubdata = snapshot.getValue(Clubdata.class);
                Clubname.setText(clubdata.getClubname());
                description.setText("Club Name :"+clubdata.getClubname()+"\nClub Admin :"+clubdata.getUser());
                description.setVisibility(View.VISIBLE);
                adminName.setText("Club Admin : "+clubdata.getUser());
                if(clubdata.getProfileimage().length()!=0){
                    Glide.with(profileImage.getContext()).load(clubdata.getProfileimage()).into(profileImage);
                }
                if(clubdata.getClubdescription().length()!=0){
                    description.setText(clubdata.getClubdescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        selectProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 11);
            }
        });

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false);
        return root;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==11 && resultCode== getActivity().RESULT_OK){
            progressBar.setVisibility(View.VISIBLE);
            Uri uri = data.getData();
            profileImage.setImageURI(uri);
            final StorageReference storageReference = storage.getReference().child("profile_photo").child(Path);
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Profile Photo Saved", Toast.LENGTH_SHORT).show();
                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("clubdata").child(Path).child("profileimage").setValue(uri.toString());
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            });

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart(){
        super.onStart();
    }
}