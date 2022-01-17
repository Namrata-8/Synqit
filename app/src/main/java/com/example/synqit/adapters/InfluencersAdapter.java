package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;

public class InfluencersAdapter extends RecyclerView.Adapter<InfluencersAdapter.influencersViewHolder> {

    private Context context;

    public InfluencersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public influencersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_influencers, parent, false);
        return new InfluencersAdapter.influencersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull influencersViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class influencersViewHolder extends RecyclerView.ViewHolder {
        public influencersViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
