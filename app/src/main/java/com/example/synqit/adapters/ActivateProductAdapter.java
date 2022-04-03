package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.databinding.RowActivateProductBinding;
import com.example.synqit.databinding.RowBlockedConnectBinding;
import com.example.synqit.ui.activateproduct.ActivateProductViewModel;
import com.example.synqit.ui.blockedconnections.BlockedConnectionViewModel;

import java.util.ArrayList;

public class ActivateProductAdapter extends RecyclerView.Adapter<ActivateProductAdapter.connectViewHolder> {

    private Context context;
    private ArrayList<ActivateProductViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public ActivateProductAdapter(Context context, ArrayList<ActivateProductViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    @NonNull
    @Override
    public connectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowActivateProductBinding rowActivateProductBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_activate_product, parent, false);
        return new ActivateProductAdapter.connectViewHolder(rowActivateProductBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull connectViewHolder holder, int position) {
        ActivateProductViewModel activateProductViewModel = arrayList.get(position);
        holder.bind(activateProductViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class connectViewHolder extends RecyclerView.ViewHolder {
        private RowActivateProductBinding rowActivateProductBinding;
        private OnItemClickListener listener;
        public connectViewHolder(@NonNull RowActivateProductBinding rowActivateProductBinding, final OnItemClickListener listener) {
            super(rowActivateProductBinding.getRoot());
            this.rowActivateProductBinding = rowActivateProductBinding;
            this.listener = listener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bind(ActivateProductViewModel activateProductViewModel){
            this.rowActivateProductBinding.setViewModel(activateProductViewModel);
            rowActivateProductBinding.executePendingBindings();

        }
    }
}
