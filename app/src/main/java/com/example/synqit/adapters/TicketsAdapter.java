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

import com.example.synqit.R;
import com.example.synqit.databinding.RowHowToUseBinding;
import com.example.synqit.databinding.RowTicketBinding;
import com.example.synqit.ui.howtouse.HowToUseViewModel;
import com.example.synqit.ui.support.SupportViewModel;

import java.util.ArrayList;

public class TicketsAdapter extends RecyclerView.Adapter<TicketsAdapter.connectViewHolder> {

    private Context context;
    private ArrayList<SupportViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public TicketsAdapter(Context context, ArrayList<SupportViewModel> arrayList) {
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
        RowTicketBinding rowTicketBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_ticket, parent, false);
        return new TicketsAdapter.connectViewHolder(rowTicketBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull connectViewHolder holder, int position) {
        SupportViewModel supportViewModel = arrayList.get(position);
        holder.bind(supportViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class connectViewHolder extends RecyclerView.ViewHolder {
        private RowTicketBinding rowTicketBinding;
        private OnItemClickListener listener;
        public connectViewHolder(@NonNull RowTicketBinding rowTicketBinding, final OnItemClickListener listener) {
            super(rowTicketBinding.getRoot());
            this.rowTicketBinding = rowTicketBinding;
            this.listener = listener;
        }

        public void bind(SupportViewModel supportViewModel){
            this.rowTicketBinding.setViewModel(supportViewModel);
            rowTicketBinding.executePendingBindings();
        }
    }
}
