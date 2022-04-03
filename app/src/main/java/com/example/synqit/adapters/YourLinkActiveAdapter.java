package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.synqit.R;
import com.example.synqit.databinding.RowLinkBinding;
import com.example.synqit.databinding.RowProfileLinkBinding;
import com.example.synqit.fragments.homefragment.HomeFragmentViewModel;
import com.example.synqit.ui.addlink.AddLinkActivity;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.utils.SessionManager;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

public class YourLinkActiveAdapter extends RecyclerView.Adapter<YourLinkActiveAdapter.yourLinkViewHolder> {

    private Context context;
    private ArrayList<DashboardViewModel> arrayList;
    private LayoutInflater layoutInflater;
    private OnItemChangeListener mListener;

    public YourLinkActiveAdapter(Context context, ArrayList<DashboardViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
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
        RowProfileLinkBinding rowProfileLinkBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_profile_link, parent, false);
        return new YourLinkActiveAdapter.yourLinkViewHolder(rowProfileLinkBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull yourLinkViewHolder holder, int position) {
        DashboardViewModel dashboardViewModel = arrayList.get(position);
        holder.bind(dashboardViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class yourLinkViewHolder extends RecyclerView.ViewHolder {
        private RowProfileLinkBinding rowProfileLinkBinding;
        private OnItemChangeListener listener;
        public yourLinkViewHolder(@NonNull RowProfileLinkBinding rowProfileLinkBinding, final OnItemChangeListener listener) {
            super(rowProfileLinkBinding.getRoot());
            this.rowProfileLinkBinding = rowProfileLinkBinding;
            this.listener = listener;
        }

        public void bind(DashboardViewModel dashboardViewModel){
            this.rowProfileLinkBinding.setViewModel(dashboardViewModel);
            rowProfileLinkBinding.executePendingBindings();

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_facebook);
            if(SessionManager.readBoolean(context, SessionManager.IS_LIGHT_DARK, false)) {
                Glide.with(rowProfileLinkBinding.ivSocialSite.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(dashboardViewModel.getSubscribedData().getLogoDark()).into(rowProfileLinkBinding.ivSocialSite);
            }else {
                Glide.with(rowProfileLinkBinding.ivSocialSite.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(dashboardViewModel.getSubscribedData().getLogoLight()).into(rowProfileLinkBinding.ivSocialSite);
            }
        }

    }

    public interface OnItemChangeListener{
        void onItemEnabled(int position);
        void onItemDisabled(int position);
        void onDeleteClick(int position);
    }
}
