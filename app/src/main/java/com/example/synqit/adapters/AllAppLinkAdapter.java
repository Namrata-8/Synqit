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
import com.example.synqit.databinding.RowLinkSearchBinding;
import com.example.synqit.databinding.RowMostPopularBinding;
import com.example.synqit.fragments.businessfragment3.BusinessFragment3ViewModel;
import com.example.synqit.ui.addlink.AddLinkViewModel;

import java.util.ArrayList;

public class AllAppLinkAdapter extends RecyclerView.Adapter<AllAppLinkAdapter.mostPopularViewHolder> implements Filterable {

    private Context context;
    private ArrayList<AddLinkViewModel> arrayList;
    private ArrayList<AddLinkViewModel> backup;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public AllAppLinkAdapter(Context context, ArrayList<AddLinkViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        backup = new ArrayList<>(arrayList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemAddLinkClick(int position);
    }

    @NonNull
    @Override
    public mostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowLinkSearchBinding rowLinkSearchBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_link_search, parent, false);
        return new mostPopularViewHolder(rowLinkSearchBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull mostPopularViewHolder holder, int position) {
        AddLinkViewModel addLinkViewModel = arrayList.get(position);
        holder.bind(addLinkViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class mostPopularViewHolder extends RecyclerView.ViewHolder {
        private RowLinkSearchBinding rowLinkSearchBinding;
        private OnItemClickListener listener;

        public mostPopularViewHolder(@NonNull RowLinkSearchBinding rowLinkSearchBinding, final OnItemClickListener listener) {
            super(rowLinkSearchBinding.getRoot());
            this.rowLinkSearchBinding = rowLinkSearchBinding;
            this.listener = listener;
        }

        public void bind(AddLinkViewModel addLinkViewModel){
            this.rowLinkSearchBinding.setViewModel(addLinkViewModel);
            rowLinkSearchBinding.executePendingBindings();

            if (addLinkViewModel.getCommonLinkData().isAvailableForPro()){
                rowLinkSearchBinding.tvProBusiness.setVisibility(View.VISIBLE);
            }else {
                rowLinkSearchBinding.tvProBusiness.setVisibility(View.GONE);
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_facebook);
            Glide.with(rowLinkSearchBinding.ivSocialSite.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(addLinkViewModel.getCommonLinkData().getLogoColor()).into(rowLinkSearchBinding.ivSocialSite);

            rowLinkSearchBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onItemAddLinkClick(position);
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
            ArrayList<AddLinkViewModel> filteredData = new ArrayList<>();
            if (keyword.toString().isEmpty()){
                filteredData.addAll(backup);
            }else {
                for (AddLinkViewModel obj : backup){
                    if (obj.getCommonLinkData().getTitle().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filteredData.add(obj);
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredData;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayList.clear();
            arrayList.addAll((ArrayList<AddLinkViewModel>)results.values);
            notifyDataSetChanged();
        }
    };
}
