package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.databinding.RowBlockedConnectBinding;
import com.example.synqit.databinding.RowConnectedBinding;
import com.example.synqit.fragments.connectionsfragment.ConnectionsFragmentViewModel;
import com.example.synqit.ui.blockedconnections.BlockedConnectionViewModel;

import java.util.ArrayList;

public class BlockedConnectionAdapter extends RecyclerView.Adapter<BlockedConnectionAdapter.connectViewHolder> {

    private Context context;
    private ArrayList<BlockedConnectionViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public BlockedConnectionAdapter(Context context, ArrayList<BlockedConnectionViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onFavoriteItemClick(int position);
    }


    @NonNull
    @Override
    public connectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowBlockedConnectBinding rowBlockedConnectBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_blocked_connect, parent, false);
        return new BlockedConnectionAdapter.connectViewHolder(rowBlockedConnectBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull connectViewHolder holder, int position) {
        BlockedConnectionViewModel blockedConnectionViewModel = arrayList.get(position);
        holder.bind(blockedConnectionViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class connectViewHolder extends RecyclerView.ViewHolder {
        private RowBlockedConnectBinding rowBlockedConnectBinding;
        private OnItemClickListener listener;
        public connectViewHolder(@NonNull RowBlockedConnectBinding rowBlockedConnectBinding, final OnItemClickListener listener) {
            super(rowBlockedConnectBinding.getRoot());
            this.rowBlockedConnectBinding = rowBlockedConnectBinding;
            this.listener = listener;
        }

        public void bind(BlockedConnectionViewModel blockedConnectionViewModel){
            this.rowBlockedConnectBinding.setViewModel(blockedConnectionViewModel);
            rowBlockedConnectBinding.executePendingBindings();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.img_demo);

            Glide.with(rowBlockedConnectBinding.ivImage.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(blockedConnectionViewModel.getBlockConnectedData().getDisplayPicture()).into(rowBlockedConnectBinding.ivImage);

            rowBlockedConnectBinding.btnUnLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onFavoriteItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
