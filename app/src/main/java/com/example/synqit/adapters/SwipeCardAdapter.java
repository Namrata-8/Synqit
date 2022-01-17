package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;

public class SwipeCardAdapter extends RecyclerView.Adapter<SwipeCardAdapter.swipeCardViewHolder> {

    private Context context;

    public SwipeCardAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public swipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_swipe_card, parent, false);
        return new SwipeCardAdapter.swipeCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull swipeCardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class swipeCardViewHolder extends RecyclerView.ViewHolder {
        public swipeCardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
