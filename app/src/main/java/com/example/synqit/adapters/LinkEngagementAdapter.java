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
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.synqit.R;
import com.example.synqit.databinding.RowLinkBinding;
import com.example.synqit.databinding.RowLinkEngagementBinding;
import com.example.synqit.fragments.homefragment.HomeFragmentViewModel;
import com.example.synqit.fragments.insightfragment.InsightFragmentViewModel;
import com.example.synqit.fragments.insightfragment.model.ViewsApp;
import com.example.synqit.utils.SessionManager;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

public class LinkEngagementAdapter extends RecyclerView.Adapter<LinkEngagementAdapter.yourLinkViewHolder> {

    private Context context;
    private List<ViewsApp> viewsAppArrayList;
    private LayoutInflater layoutInflater;
    private OnItemChangeListener mListener;

    public LinkEngagementAdapter(Context context, List<ViewsApp> viewsAppArrayList) {
        this.context = context;
        this.viewsAppArrayList = viewsAppArrayList;
    }

    public void setOnItemClickListener(OnItemChangeListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public yourLinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowLinkEngagementBinding rowLinkEngagementBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_link_engagement, parent, false);
        return new LinkEngagementAdapter.yourLinkViewHolder(rowLinkEngagementBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull yourLinkViewHolder holder, int position) {
        ViewsApp viewsApp = viewsAppArrayList.get(position);
        holder.rowLinkEngagementBinding.tvTpas.setText(viewsApp.getTaps() + " taps");
        holder.rowLinkEngagementBinding.tvAppTitle.setText(viewsApp.getAppTitle());
        holder.rowLinkEngagementBinding.tvAppUserName.setText(viewsApp.getUsername());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_facebook);
        if (SessionManager.readBoolean(context, SessionManager.IS_LIGHT_DARK, false)) {
            Glide.with(holder.rowLinkEngagementBinding.ivApp.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(viewsApp.getLogoDark()).into(holder.rowLinkEngagementBinding.ivApp);
        }else {
            Glide.with(holder.rowLinkEngagementBinding.ivApp)
                    .setDefaultRequestOptions(requestOptions)
                    .load(viewsApp.getLogoLight()).into(holder.rowLinkEngagementBinding.ivApp);
        }
    }

    @Override
    public int getItemCount() {
        return viewsAppArrayList.size();
    }

    public class yourLinkViewHolder extends RecyclerView.ViewHolder {
        private RowLinkEngagementBinding rowLinkEngagementBinding;
        private OnItemChangeListener listener;
        public yourLinkViewHolder(@NonNull RowLinkEngagementBinding rowLinkEngagementBinding, final OnItemChangeListener listener) {
            super(rowLinkEngagementBinding.getRoot());
            this.rowLinkEngagementBinding = rowLinkEngagementBinding;
            this.listener = listener;
        }

    }

    public interface OnItemChangeListener{
        void onItemEnabled(int position);
        void onItemDisabled(int position);
        void onDeleteClick(int position);
    }
}
