package com.industrialmaster.rudhiraya;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonorsRecyclerViewAdapter extends RecyclerView.Adapter<DonorsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImageGender = new ArrayList<>();
    private ArrayList<String> mImageAge = new ArrayList<>();
    private ArrayList<String> mImageBloodgroup = new ArrayList<>();

    public DonorsRecyclerViewAdapter( Context mContext, ArrayList<String> mImages, ArrayList<String> mImageNames, ArrayList<String> mImageGender, ArrayList<String> mImageAge, ArrayList<String> mImageBloodgroup) {
        this.mImages = mImages;
        this.mContext = mContext;
        this.mImageNames = mImageNames;
        this.mImageGender = mImageGender;
        this.mImageAge = mImageAge;
        this.mImageBloodgroup = mImageBloodgroup;
    }

    @NonNull
    @Override
    public DonorsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patients_layout_list_item,viewGroup,false);

        DonorsRecyclerViewAdapter.ViewHolder holder = new DonorsRecyclerViewAdapter.ViewHolder(view);

        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull DonorsRecyclerViewAdapter.ViewHolder viewHolder, int i) {


        //viewHolder.patientImageView.setBackgroundResource(mImages.get());

        /*Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(i))
                .into(viewHolder.patientImageView);*/

        Picasso.get().load(mImages.get(i)).memoryPolicy(MemoryPolicy.NO_CACHE).into(viewHolder.patientImageView);

        viewHolder.patientName.setText(mImageNames.get(i));
        viewHolder.patientBloodGroup.setText(mImageGender.get(i));
        viewHolder.patientAge.setText(mImageAge.get(i));
        viewHolder.patientBloodGroup.setText(mImageBloodgroup.get(i));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"Clicked",Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        ImageView patientImageView;
        TextView patientName,patientAge,patientBloodGroup,patientGender;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            patientImageView = itemView.findViewById(R.id.patient_pro_pic);
            patientName = itemView.findViewById(R.id.patient_profile_name);
            patientAge = itemView.findViewById(R.id.patient_age);
            patientBloodGroup = itemView.findViewById(R.id.patient_blood_type);
            patientGender = itemView.findViewById(R.id.patient_gender);
            parentLayout = itemView.findViewById(R.id.parent_layout);




        }
    }



}
