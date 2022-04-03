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
import com.example.synqit.R;
import com.example.synqit.models.SliderItems;

import java.util.List;

public class ProSliderAdapter extends RecyclerView.Adapter<ProSliderAdapter.SliderViewHolder> {

    private List<SliderItems> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;

    public ProSliderAdapter(List<SliderItems> sliderItems, ViewPager2 viewPager2, Context context) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slide_item_pro_container, parent, false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));
        holder.tvOnBoardTitle.setText(sliderItems.get(position).getTitle());
        holder.tvOnBoardDesc.setText(sliderItems.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivOnBoard;
        private TextView tvOnBoardTitle;
        private TextView tvOnBoardDesc;

        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOnBoard = itemView.findViewById(R.id.ivOnBoard);
            tvOnBoardTitle = itemView.findViewById(R.id.tvOnBoardTitle);
            tvOnBoardDesc = itemView.findViewById(R.id.tvOnBoardDesc);
        }

        void setImage(SliderItems sliderItems) {
            ivOnBoard.setImageResource(sliderItems.getImage());
        }
    }
}
