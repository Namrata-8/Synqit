package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.databinding.ItemBusinessCardBinding;
import com.example.synqit.fragments.businessfragment2.BusinessFragment2ViewModel;

import java.util.ArrayList;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.businessViewHolder> {

    private Context context;
    private ArrayList<BusinessFragment2ViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;
    private int checkedPosition = 0;

    public BusinessAdapter(Context context, ArrayList<BusinessFragment2ViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public businessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemBusinessCardBinding itemBusinessCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_business_card, parent, false);
        return new businessViewHolder(itemBusinessCardBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull businessViewHolder holder, int position) {
        BusinessFragment2ViewModel fragment2ViewModel = arrayList.get(position);
        holder.bind(fragment2ViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void onItemAddClick(int position);

        /*void onItemRemoveClick(int position);*/
    }

    public class businessViewHolder extends RecyclerView.ViewHolder {
        private ItemBusinessCardBinding itemBusinessCardBinding;
        private OnItemClickListener listener;

        public businessViewHolder(@NonNull ItemBusinessCardBinding itemBusinessCardBinding, final OnItemClickListener listener) {
            super(itemBusinessCardBinding.getRoot());
            this.itemBusinessCardBinding = itemBusinessCardBinding;
            this.listener = listener;

        }

        public void bind(BusinessFragment2ViewModel fragment2ViewModel) {
            this.itemBusinessCardBinding.setViewModel(fragment2ViewModel);
            itemBusinessCardBinding.executePendingBindings();

            if (checkedPosition == -1) {
                itemBusinessCardBinding.ivCheckIcon.setVisibility(View.GONE);
                itemBusinessCardBinding.rlCardIndividual.setBackgroundResource(R.drawable.bg_corner_unselected_card);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    itemBusinessCardBinding.ivCheckIcon.setVisibility(View.VISIBLE);
                    itemBusinessCardBinding.rlCardIndividual.setBackgroundResource(R.drawable.bg_corner_selected_card);
                } else {
                    itemBusinessCardBinding.ivCheckIcon.setVisibility(View.GONE);
                    itemBusinessCardBinding.rlCardIndividual.setBackgroundResource(R.drawable.bg_corner_unselected_card);
                }
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemBusinessCardBinding.ivCheckIcon.setVisibility(View.VISIBLE);
                    itemBusinessCardBinding.rlCardIndividual.setBackgroundResource(R.drawable.bg_corner_selected_card);
                    if (checkedPosition != getAdapterPosition()) {
                        notifyItemChanged(checkedPosition);
                        checkedPosition = getAdapterPosition();
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemAddClick(position);
                            }
                        }
                    }
                    /*if (itemBusinessCardBinding.ivCheckIcon.getVisibility() == View.VISIBLE) {
                        itemBusinessCardBinding.ivCheckIcon.setVisibility(View.GONE);
                        itemBusinessCardBinding.rlCardIndividual.setBackgroundResource(R.drawable.bg_corner_unselected_card);
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemRemoveClick(position);
                            }
                        }
                    } else {
                        itemBusinessCardBinding.ivCheckIcon.setVisibility(View.VISIBLE);
                        itemBusinessCardBinding.rlCardIndividual.setBackgroundResource(R.drawable.bg_corner_selected_card);
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemAddClick(position);
                            }
                        }
                    }*/
                }
            });
        }

        public ItemBusinessCardBinding getItemBusinessCardBinding() {
            return itemBusinessCardBinding;
        }
    }
}
