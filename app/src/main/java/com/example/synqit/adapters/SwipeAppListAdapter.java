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
import com.example.synqit.databinding.RowAppLinkBinding;
import com.example.synqit.ui.addlink.AddLinkViewModel;
import com.example.synqit.ui.addlink.model.CommonLinkData;

import java.util.ArrayList;
import java.util.List;

public class SwipeAppListAdapter extends RecyclerView.Adapter<SwipeAppListAdapter.swipeAppListViewHolder> {

    private Context context;
    private List<CommonLinkData> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public SwipeAppListAdapter(Context context, List<CommonLinkData> arrayList) {
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
    public swipeAppListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowAppLinkBinding rowAppLinkBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_app_link, parent, false);
        return new swipeAppListViewHolder(rowAppLinkBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull swipeAppListViewHolder holder, int position) {
        CommonLinkData commonLinkData = arrayList.get(position);
        holder.bind(commonLinkData);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class swipeAppListViewHolder extends RecyclerView.ViewHolder {
        private RowAppLinkBinding rowAppLinkBinding;
        private OnItemClickListener listener;
        public swipeAppListViewHolder(@NonNull RowAppLinkBinding rowAppLinkBinding, final OnItemClickListener listener) {
            super(rowAppLinkBinding.getRoot());
            this.rowAppLinkBinding = rowAppLinkBinding;
            this.listener = listener;
        }

        public void bind(CommonLinkData commonLinkData){
            rowAppLinkBinding.tvName.setText(commonLinkData.getTitle());

            if (commonLinkData.isAvailableForPro()){
                rowAppLinkBinding.tvProBusiness.setVisibility(View.VISIBLE);
            }else {
                rowAppLinkBinding.tvProBusiness.setVisibility(View.GONE);
            }
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_facebook);
            Glide.with(rowAppLinkBinding.ivSocialSite.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(commonLinkData.getLogoColor()).into(rowAppLinkBinding.ivSocialSite);

            rowAppLinkBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemAddLinkClick(position);
                        }
                    }
                }
            });
        }
    }
}
