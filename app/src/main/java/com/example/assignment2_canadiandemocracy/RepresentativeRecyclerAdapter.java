package com.example.assignment2_canadiandemocracy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RepresentativeRecyclerAdapter extends RecyclerView.Adapter<RepresentativeRecyclerAdapter.MyViewHolder> {

    private final List<Representative> representatives;
    private LayoutInflater mInflater;

    public RepresentativeRecyclerAdapter(Context context,
                                         List<Representative> representativesList) {
        mInflater = LayoutInflater.from(context);
        this.representatives = representativesList;
    }
    @NonNull
    @Override
    public RepresentativeRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.items,
                viewGroup, false);
        return new MyViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RepresentativeRecyclerAdapter.MyViewHolder viewHolder, int i) {
        Representative representative = representatives.get(i);
        String firstName = representative.getFirst_name();
        String lastName = representative.getLast_name();
        String name = firstName + " " + lastName;
        String partName = representative.getParty_name();
        String website = representative.getPersonal_url();
        String imageUrl = representative.getPhoto_url();
        if (!(imageUrl.equals(""))) {
            System.out.println("Image is: " + imageUrl);
            Picasso.get().load(imageUrl).resize(100,100).into(viewHolder.image);
            //Glide.with(viewHolder.imageView).load(imageUrl).into(viewHolder.imageView);
        }

        viewHolder.name.setText(name);
        viewHolder.partyName.setText(partName);
        viewHolder.personalWebsite.setText(website);
    }

    @Override
    public int getItemCount() {
        return representatives.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView image;
        public final TextView name;
        public final TextView partyName;
        public final TextView personalWebsite;
        final RepresentativeRecyclerAdapter mAdapter;

        public  MyViewHolder(View itemView, RepresentativeRecyclerAdapter mAdapter) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageView);
            this.name = itemView.findViewById(R.id.nameTextView);
            this.partyName = itemView.findViewById(R.id.partyName);
            this.personalWebsite = itemView.findViewById(R.id.personalWebsite);
            this.mAdapter = mAdapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            String personalUrl = representatives.get(mPosition).getPersonal_url();
            if (personalUrl.equals("")) {
                // Show toast message.
                String message = "No website of " + representatives.get(mPosition).getFirst_name();
                Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }
            else {
                //It will open browser based on provided url
                Uri uri = Uri.parse(personalUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                v.getContext().startActivity(intent);
            }

        }
    }
}
