package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.databinding.RowInfluencersBinding;
import com.example.synqit.ui.addlink.AddLinkViewModel;

import java.util.ArrayList;

public class InfluencersAdapter extends RecyclerView.Adapter<InfluencersAdapter.influencersViewHolder> {

    private Context context;
    private ArrayList<AddLinkViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public InfluencersAdapter(Context context, ArrayList<AddLinkViewModel> arrayList) {
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
    public influencersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowInfluencersBinding rowInfluencersBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_influencers, parent, false);
        return new influencersViewHolder(rowInfluencersBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull influencersViewHolder holder, int position) {
        AddLinkViewModel addLinkViewModel = arrayList.get(position);
        holder.bind(addLinkViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class influencersViewHolder extends RecyclerView.ViewHolder {
        private RowInfluencersBinding rowInfluencersBinding;
        private OnItemClickListener listener;

        public influencersViewHolder(@NonNull RowInfluencersBinding rowInfluencersBinding, final OnItemClickListener listener) {
            super(rowInfluencersBinding.getRoot());
            this.rowInfluencersBinding = rowInfluencersBinding;
            this.listener = listener;
        }

        public void bind(AddLinkViewModel addLinkViewModel){
            this.rowInfluencersBinding.setViewModel(addLinkViewModel);
            rowInfluencersBinding.executePendingBindings();

            if (addLinkViewModel.getCommonLinkData().isAvailableForPro()){
                rowInfluencersBinding.tvProBusiness.setVisibility(View.VISIBLE);
            }else {
                rowInfluencersBinding.tvProBusiness.setVisibility(View.GONE);
            }
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_facebook);
            Glide.with(rowInfluencersBinding.ivSocialSite.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(addLinkViewModel.getCommonLinkData().getLogoColor()).into(rowInfluencersBinding.ivSocialSite);

            rowInfluencersBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
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
