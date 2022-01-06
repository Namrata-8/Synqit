package com.example.synqit.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.customeviews.TextViewRegular;
import com.example.synqit.customeviews.TextViewSemiBold;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.countryViewHolder> {

    private Context context;
    private List<String> countryList;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CountryAdapter(Context context, List<String> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public countryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_country_gender, parent, false);
        return new CountryAdapter.countryViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull countryViewHolder holder, int position) {
        holder.tvTitle.setText(countryList.get(position));
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public class countryViewHolder extends RecyclerView.ViewHolder {

        private TextViewRegular tvTitle;
        private ImageView ivSelected;
        private RelativeLayout rlItem;

        public countryViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            rlItem = itemView.findViewById(R.id.rlItem);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivSelected = itemView.findViewById(R.id.ivSelected);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
