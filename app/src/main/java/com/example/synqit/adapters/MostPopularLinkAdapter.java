package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;

public class MostPopularLinkAdapter extends RecyclerView.Adapter<MostPopularLinkAdapter.mostPopularViewHolder> {

    private Context context;

    public MostPopularLinkAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public mostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_most_popular, parent, false);
        return new MostPopularLinkAdapter.mostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull mostPopularViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class mostPopularViewHolder extends RecyclerView.ViewHolder {
        public mostPopularViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
