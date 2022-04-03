package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.synqit.models.SliderItems;
import com.example.synqit.R;

import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    private List<SliderItems> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;

    public SliderAdapter(List<SliderItems> sliderItems, ViewPager2 viewPager2, Context context) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_container, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        /*holder.tvOnBoardTitle.setText(sliderItems.get(position).getTitle());*/
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        /*private GifImageView ivOnBoard;*/
        private LottieAnimationView lottieOnBoard;
        /*private TextView tvOnBoardTitle;*/

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            /*ivOnBoard = itemView.findViewById(R.id.ivOnBoard);*/
            lottieOnBoard = itemView.findViewById(R.id.lottieOnBoard);
            /*tvOnBoardTitle = itemView.findViewById(R.id.tvOnBoardTitle);*/
        }

        void setImage(SliderItems sliderItems) {
            lottieOnBoard.setAnimation(sliderItems.getImage());
        }
    }
}
