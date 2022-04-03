package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.synqit.R;
import com.example.synqit.databinding.ItemBusinessCardBinding;
import com.example.synqit.databinding.RowLinkBinding;
import com.example.synqit.fragments.businessfragment2.BusinessFragment2ViewModel;
import com.example.synqit.fragments.homefragment.HomeFragmentViewModel;
import com.example.synqit.utils.SessionManager;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

public class YourLinkAdapter extends RecyclerView.Adapter<YourLinkAdapter.yourLinkViewHolder> {

    private Context context;
    private ArrayList<HomeFragmentViewModel> arrayList;
    private LayoutInflater layoutInflater;
    private OnItemChangeListener mListener;

    public YourLinkAdapter(Context context, ArrayList<HomeFragmentViewModel> arrayList) {
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
        RowLinkBinding rowLinkBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_link, parent, false);
        return new YourLinkAdapter.yourLinkViewHolder(rowLinkBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull yourLinkViewHolder holder, int position) {
        HomeFragmentViewModel homeFragmentViewModel = arrayList.get(position);
        holder.bind(homeFragmentViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class yourLinkViewHolder extends RecyclerView.ViewHolder {
        private RowLinkBinding rowLinkBinding;
        private OnItemChangeListener listener;
        public yourLinkViewHolder(@NonNull RowLinkBinding rowLinkBinding, final OnItemChangeListener listener) {
            super(rowLinkBinding.getRoot());
            this.rowLinkBinding = rowLinkBinding;
            this.listener = listener;
        }

        public void bind(HomeFragmentViewModel homeFragmentViewModel){
            this.rowLinkBinding.setViewModel(homeFragmentViewModel);
            rowLinkBinding.executePendingBindings();
            if (homeFragmentViewModel.getSubscribedData().isDisabled()){
                rowLinkBinding.switchButton.setChecked(false);
            }else {
                rowLinkBinding.switchButton.setChecked(true);
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_facebook);

            if (SessionManager.readBoolean(context, SessionManager.IS_LIGHT_DARK, false)) {
                Glide.with(rowLinkBinding.ivSocialSite.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(homeFragmentViewModel.getSubscribedData().getLogoDark()).into(rowLinkBinding.ivSocialSite);
            }else {
                Glide.with(rowLinkBinding.ivSocialSite.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(homeFragmentViewModel.getSubscribedData().getLogoLight()).into(rowLinkBinding.ivSocialSite);
            }

            rowLinkBinding.switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    if (isChecked){
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemEnabled(position);
                            }
                        }
                    }else {
                        if (listener != null) {
                            int position = getAdapterPosition();
                            if (position != RecyclerView.NO_POSITION) {
                                listener.onItemDisabled(position);
                            }
                        }
                    }
                }
            });

            rowLinkBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });

            rowLinkBinding.swipeLayout.setSwipeListener(new SwipeRevealLayout.SimpleSwipeListener(){
                @Override
                public void onClosed(SwipeRevealLayout view) {
                    rowLinkBinding.bgConstraint.setBackgroundResource(R.drawable.bg_row_link);
                }

                @Override
                public void onOpened(SwipeRevealLayout view) {
                    rowLinkBinding.bgConstraint.setBackgroundResource(R.drawable.bg_row_link_selected);
                }

                @Override
                public void onSlide(SwipeRevealLayout view, float slideOffset) {
                    rowLinkBinding.bgConstraint.setBackgroundResource(R.drawable.bg_row_link_selected);
                }
            });
        }

        public RowLinkBinding getRowLinkBinding(){
            return rowLinkBinding;
        }
    }

    public interface OnItemChangeListener{
        void onItemEnabled(int position);
        void onItemDisabled(int position);
        void onDeleteClick(int position);
    }
}
