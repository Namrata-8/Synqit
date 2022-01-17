package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;

public class YourLinkAdapter extends RecyclerView.Adapter<YourLinkAdapter.yourLinkViewHolder> {

    private Context context;

    public YourLinkAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public yourLinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_link, parent, false);
        return new YourLinkAdapter.yourLinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull yourLinkViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class yourLinkViewHolder extends RecyclerView.ViewHolder {
        public yourLinkViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
