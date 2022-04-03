package com.example.synqit.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.customeviews.TextViewRegular;
import com.example.synqit.databinding.RowMostPopularBinding;
import com.example.synqit.ui.addlink.AddLinkViewModel;

import java.util.ArrayList;

public class MostPopularLinkAdapter extends RecyclerView.Adapter<MostPopularLinkAdapter.mostPopularViewHolder> {

    private Context context;
    private ArrayList<AddLinkViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public MostPopularLinkAdapter(Context context, ArrayList<AddLinkViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemAddLinkClick(int position);
    }

    @NonNull
    @Override
    public mostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowMostPopularBinding rowMostPopularBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_most_popular, parent, false);
        return new mostPopularViewHolder(rowMostPopularBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull mostPopularViewHolder holder, int position) {
        AddLinkViewModel addLinkViewModel = arrayList.get(position);
        holder.bind(addLinkViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class mostPopularViewHolder extends RecyclerView.ViewHolder {
        private RowMostPopularBinding rowMostPopularBinding;
        private OnItemClickListener listener;

        public mostPopularViewHolder(@NonNull RowMostPopularBinding rowMostPopularBinding, final OnItemClickListener listener) {
            super(rowMostPopularBinding.getRoot());
            this.rowMostPopularBinding = rowMostPopularBinding;
            this.listener = listener;
        }

        public void bind(AddLinkViewModel addLinkViewModel){
            this.rowMostPopularBinding.setViewModel(addLinkViewModel);
            rowMostPopularBinding.executePendingBindings();

            if (addLinkViewModel.getCommonLinkData().isAvailableForPro()){
                rowMostPopularBinding.tvProBusiness.setVisibility(View.VISIBLE);
            }else {
                rowMostPopularBinding.tvProBusiness.setVisibility(View.GONE);
            }
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_facebook);
            Glide.with(rowMostPopularBinding.ivSocialSite.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(addLinkViewModel.getCommonLinkData().getLogoColor()).into(rowMostPopularBinding.ivSocialSite);

            rowMostPopularBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemAddLinkClick(position);
                        }
                    }
                }
            });
        }
    }
}
