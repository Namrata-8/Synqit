package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.customeviews.TextViewRegular;
import com.example.synqit.databinding.ItemBusinessCardBinding;
import com.example.synqit.databinding.ItemCountryGenderBinding;
import com.example.synqit.fragments.businessfragment2.BusinessFragment2ViewModel;
import com.example.synqit.fragments.businessfragment3.BusinessFragment3ViewModel;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.countryViewHolder> {

    private Context context;
    private ArrayList<BusinessFragment3ViewModel> arrayList;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CountryAdapter(Context context, ArrayList<BusinessFragment3ViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public countryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemCountryGenderBinding itemCountryGenderBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_country_gender, parent, false);
        return new countryViewHolder(itemCountryGenderBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull countryViewHolder holder, int position) {
        BusinessFragment3ViewModel businessFragment3ViewModel = arrayList.get(position);
        holder.bind(businessFragment3ViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class countryViewHolder extends RecyclerView.ViewHolder {
        private ItemCountryGenderBinding itemCountryGenderBinding;

        public countryViewHolder(@NonNull ItemCountryGenderBinding itemCountryGenderBinding, final OnItemClickListener listener) {
            super(itemCountryGenderBinding.getRoot());
            this.itemCountryGenderBinding = itemCountryGenderBinding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemCountryGenderBinding.ivSelected.getVisibility() == View.VISIBLE) {
                        itemCountryGenderBinding.ivSelected.setVisibility(View.GONE);
                        itemCountryGenderBinding.rlItem.setBackgroundResource(R.drawable.bg_country_gender_unselected_card);
                    }else {
                        itemCountryGenderBinding.ivSelected.setVisibility(View.VISIBLE);
                        itemCountryGenderBinding.rlItem.setBackgroundResource(R.drawable.bg_country_gender_selected_card);
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

        public void bind(BusinessFragment3ViewModel fragment3ViewModel) {
            this.itemCountryGenderBinding.setViewModel(fragment3ViewModel);
            itemCountryGenderBinding.executePendingBindings();
        }

        public ItemCountryGenderBinding getItemCountryGenderBinding() {
            return itemCountryGenderBinding;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
