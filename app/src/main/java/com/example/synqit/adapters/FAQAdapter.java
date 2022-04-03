package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.databinding.RowFaqBinding;
import com.example.synqit.databinding.RowTicketBinding;
import com.example.synqit.ui.support.SupportViewModel;

import java.util.ArrayList;

public class FAQAdapter extends RecyclerView.Adapter<FAQAdapter.connectViewHolder> {

    private Context context;
    private ArrayList<SupportViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public FAQAdapter(Context context, ArrayList<SupportViewModel> arrayList) {
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
        RowFaqBinding rowFaqBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_faq, parent, false);
        return new FAQAdapter.connectViewHolder(rowFaqBinding, mListener);
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
        private RowFaqBinding rowFaqBinding;
        private OnItemClickListener listener;
        public connectViewHolder(@NonNull RowFaqBinding rowFaqBinding, final OnItemClickListener listener) {
            super(rowFaqBinding.getRoot());
            this.rowFaqBinding = rowFaqBinding;
            this.listener = listener;
        }

        public void bind(SupportViewModel supportViewModel){
            this.rowFaqBinding.setViewModel(supportViewModel);
            rowFaqBinding.executePendingBindings();

            rowFaqBinding.tvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(rowFaqBinding.tvDescFaq.getVisibility() == View.VISIBLE){
                        rowFaqBinding.tvDescFaq.setVisibility(View.GONE);
                    }else {
                        rowFaqBinding.tvDescFaq.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}
