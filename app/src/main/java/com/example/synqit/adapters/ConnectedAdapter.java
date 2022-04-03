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
import com.example.synqit.databinding.RowConnectedBinding;
import com.example.synqit.databinding.RowMostPopularBinding;
import com.example.synqit.fragments.connectionsfragment.ConnectionsFragmentViewModel;
import com.example.synqit.ui.addlink.AddLinkViewModel;

import java.util.ArrayList;

public class ConnectedAdapter extends RecyclerView.Adapter<ConnectedAdapter.connectViewHolder> implements Filterable {

    private Context context;
    private ArrayList<ConnectionsFragmentViewModel> arrayList;
    private ArrayList<ConnectionsFragmentViewModel> backup;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public ConnectedAdapter(Context context, ArrayList<ConnectionsFragmentViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        backup = new ArrayList<>(arrayList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onFavoriteItemClick(int position);
        void onItemDeleteClick(int position);
    }


    @NonNull
    @Override
    public connectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowConnectedBinding rowConnectedBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_connected, parent, false);
        return new ConnectedAdapter.connectViewHolder(rowConnectedBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull connectViewHolder holder, int position) {
        ConnectionsFragmentViewModel connectionsFragmentViewModel = arrayList.get(position);
        holder.bind(connectionsFragmentViewModel,context);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class connectViewHolder extends RecyclerView.ViewHolder {
        private RowConnectedBinding rowConnectedBinding;
        private OnItemClickListener listener;
        public connectViewHolder(@NonNull RowConnectedBinding rowConnectedBinding, final OnItemClickListener listener) {
            super(rowConnectedBinding.getRoot());
            this.rowConnectedBinding = rowConnectedBinding;
            this.listener = listener;
        }

        public void bind(ConnectionsFragmentViewModel connectionsFragmentViewModel, Context context){
            this.rowConnectedBinding.setViewModel(connectionsFragmentViewModel);
            rowConnectedBinding.executePendingBindings();
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.img_demo);

            Glide.with(rowConnectedBinding.ivImage.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(connectionsFragmentViewModel.getConnectedData().getDisplayPicture()).into(rowConnectedBinding.ivImage);

            if (connectionsFragmentViewModel.getConnectedData().isFavorite()){
                rowConnectedBinding.btnFavorite.setImageResource(R.drawable.ic_favotite_fill);
            }else {
                rowConnectedBinding.btnFavorite.setImageResource(R.drawable.ic_favorite);
            }

            if (connectionsFragmentViewModel.getConnectedData().isDeleted()){
                itemView.setVisibility(View.GONE);
            }else {
                itemView.setVisibility(View.VISIBLE);
            }
            if (connectionsFragmentViewModel.getConnectedData().getPlan()>0){
                rowConnectedBinding.tvProBusiness.setVisibility(View.VISIBLE);
            }else {
                rowConnectedBinding.tvProBusiness.setVisibility(View.GONE);
            }

            rowConnectedBinding.btnFavorite.setOnClickListener(new View.OnClickListener() {
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

            rowConnectedBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<ConnectionsFragmentViewModel> filteredData = new ArrayList<>();
            if (keyword.toString().isEmpty()){
                filteredData.addAll(backup);
            }else {
                for (ConnectionsFragmentViewModel obj : backup){
                        if (obj.getConnectedData().getDisplayName().toLowerCase().contains(keyword.toString().toLowerCase())) {
                            filteredData.add(obj);
                        }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredData;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((ArrayList<ConnectionsFragmentViewModel>)results.values);
            notifyDataSetChanged();
        }
    };
}
