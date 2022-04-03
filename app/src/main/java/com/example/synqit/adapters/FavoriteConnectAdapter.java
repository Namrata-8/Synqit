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
import com.example.synqit.databinding.RowConnectedBinding;
import com.example.synqit.databinding.RowFavoriteConnectBinding;
import com.example.synqit.fragments.connectionsfragment.ConnectionsFragmentViewModel;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.utils.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;

public class FavoriteConnectAdapter extends RecyclerView.Adapter<FavoriteConnectAdapter.favoriteViewHolder> {

    private Context context;
    private ArrayList<ConnectionsFragmentViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public FavoriteConnectAdapter(Context context, ArrayList<ConnectionsFragmentViewModel> arrayList) {
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
    public favoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowFavoriteConnectBinding rowFavoriteConnectBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_favorite_connect, parent, false);
        return new FavoriteConnectAdapter.favoriteViewHolder(rowFavoriteConnectBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull favoriteViewHolder holder, int position) {
        ConnectionsFragmentViewModel connectionsFragmentViewModel = arrayList.get(position);
        holder.bind(connectionsFragmentViewModel, context);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class favoriteViewHolder extends RecyclerView.ViewHolder {
        private RowFavoriteConnectBinding rowFavoriteConnectBinding;
        private OnItemClickListener listener;
        public favoriteViewHolder(@NonNull RowFavoriteConnectBinding rowFavoriteConnectBinding, final OnItemClickListener listener) {
            super(rowFavoriteConnectBinding.getRoot());
            this.rowFavoriteConnectBinding = rowFavoriteConnectBinding;
            this.listener = listener;
        }

        public void bind(ConnectionsFragmentViewModel connectionsFragmentViewModel, Context context){
            this.rowFavoriteConnectBinding.setViewModel(connectionsFragmentViewModel);
            rowFavoriteConnectBinding.executePendingBindings();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.img_demo);

            Glide.with(rowFavoriteConnectBinding.ivFavorite.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(connectionsFragmentViewModel.getFavoriteData().getDisplayPicture()).into(rowFavoriteConnectBinding.ivFavorite);
        }
    }
}
