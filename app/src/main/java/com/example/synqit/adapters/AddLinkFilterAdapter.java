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

import com.example.synqit.R;
import com.example.synqit.databinding.ItemCountryGenderBinding;
import com.example.synqit.databinding.RowAddLinkFilterBinding;
import com.example.synqit.fragments.businessfragment3.BusinessFragment3ViewModel;
import com.example.synqit.ui.addlink.AddLinkViewModel;

import java.util.ArrayList;

public class AddLinkFilterAdapter extends RecyclerView.Adapter<AddLinkFilterAdapter.countryViewHolder> {

    private Context context;
    private ArrayList<AddLinkViewModel> arrayList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public AddLinkFilterAdapter(Context context, ArrayList<AddLinkViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public countryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowAddLinkFilterBinding rowAddLinkFilterBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_add_link_filter, parent, false);
        return new countryViewHolder(rowAddLinkFilterBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull countryViewHolder holder, int position) {
        AddLinkViewModel AddLinkViewModel = arrayList.get(position);
        holder.bind(AddLinkViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class countryViewHolder extends RecyclerView.ViewHolder {
        private RowAddLinkFilterBinding rowAddLinkFilterBinding;

        public countryViewHolder(@NonNull RowAddLinkFilterBinding rowAddLinkFilterBinding, final OnItemClickListener listener) {
            super(rowAddLinkFilterBinding.getRoot());
            this.rowAddLinkFilterBinding = rowAddLinkFilterBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rowAddLinkFilterBinding.ivSelected.getVisibility() == View.VISIBLE) {
                        rowAddLinkFilterBinding.ivSelected.setVisibility(View.GONE);
                        rowAddLinkFilterBinding.rlItem.setBackgroundResource(R.drawable.bg_country_gender_unselected_card);
                    }else {
                        rowAddLinkFilterBinding.ivSelected.setVisibility(View.VISIBLE);
                        rowAddLinkFilterBinding.rlItem.setBackgroundResource(R.drawable.bg_country_gender_selected_card);
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemClick(position);
                            }
                        }
                    }
                }
            });
        }

        public void bind(AddLinkViewModel addLinkViewModel) {
            this.rowAddLinkFilterBinding.setViewModel(addLinkViewModel);
            rowAddLinkFilterBinding.executePendingBindings();
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
