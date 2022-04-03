package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.databinding.RowPlansBinding;
import com.example.synqit.ui.proupgrade.ProUpgradeViewModel;

import java.util.ArrayList;

public class PlansAdapter extends RecyclerView.Adapter<PlansAdapter.plansViewHolder> {

    private Context context;
    private ArrayList<ProUpgradeViewModel> arrayList;
    private LayoutInflater layoutInflater;
    private OnItemChangeListener mListener;
    private int checkedPosition = 0;

    public PlansAdapter(Context context, ArrayList<ProUpgradeViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemChangeListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public plansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowPlansBinding rowPlansBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_plans, parent, false);
        return new PlansAdapter.plansViewHolder(rowPlansBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull plansViewHolder holder, int position) {
        ProUpgradeViewModel proUpgradeViewModel = arrayList.get(position);
        holder.bind(proUpgradeViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemChangeListener {
        void onItemClick(int position, int plan);
    }

    public class plansViewHolder extends RecyclerView.ViewHolder {
        private RowPlansBinding rowPlansBinding;
        private OnItemChangeListener listener;

        public plansViewHolder(@NonNull RowPlansBinding rowPlansBinding, final OnItemChangeListener listener) {
            super(rowPlansBinding.getRoot());
            this.rowPlansBinding = rowPlansBinding;
            this.listener = listener;
        }

        public void bind(ProUpgradeViewModel proUpgradeViewModel) {
            this.rowPlansBinding.setViewModel(proUpgradeViewModel);
            rowPlansBinding.executePendingBindings();

            if (checkedPosition == -1) {
                rowPlansBinding.rlCardPlan.setBackgroundResource(R.drawable.bg_corner_unselected_card_pro);
                rowPlansBinding.ivCheckIcon.setVisibility(View.GONE);
                rowPlansBinding.tvDiscount.setBackgroundResource(R.drawable.bg_discount_unselected);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    rowPlansBinding.rlCardPlan.setBackgroundResource(R.drawable.bg_corner_selected_card_pro);
                    rowPlansBinding.ivCheckIcon.setVisibility(View.VISIBLE);
                    rowPlansBinding.tvDiscount.setBackgroundResource(R.drawable.bg_discount_selected);
                } else {
                    rowPlansBinding.rlCardPlan.setBackgroundResource(R.drawable.bg_corner_unselected_card_pro);
                    rowPlansBinding.ivCheckIcon.setVisibility(View.GONE);
                    rowPlansBinding.tvDiscount.setBackgroundResource(R.drawable.bg_discount_unselected);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rowPlansBinding.rlCardPlan.setBackgroundResource(R.drawable.bg_corner_selected_card_pro);
                    rowPlansBinding.ivCheckIcon.setVisibility(View.VISIBLE);
                    rowPlansBinding.tvDiscount.setBackgroundResource(R.drawable.bg_discount_selected);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position, Integer.parseInt(arrayList.get(position).getId()));
                            }
                        }
                    }
                }
            });
        }
    }
}
