package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.synqit.R;
import com.example.synqit.databinding.RowCardBinding;
import com.example.synqit.fragments.homefragment.HomeFragmentViewModel;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.DashboardNavigator;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.color.MaterialColors;
import com.google.gson.Gson;

import java.util.ArrayList;

public class CardDrawerAdapter extends RecyclerView.Adapter<CardDrawerAdapter.cardDrawerViewHolder> {

    private Context context;
    private ArrayList<DashboardViewModel> arrayList;
    private LayoutInflater layoutInflater;
    private OnItemChangeListener mListener;
    //private int checkedPosition = 0;

    public CardDrawerAdapter(Context context, ArrayList<DashboardViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void swap(ArrayList<DashboardViewModel> updatedList) {
        arrayList.clear();
        arrayList.addAll(updatedList);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemChangeListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public cardDrawerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowCardBinding rowCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_card, parent, false);
        return new CardDrawerAdapter.cardDrawerViewHolder(rowCardBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull cardDrawerViewHolder holder, int position) {
        DashboardViewModel dashboardViewModel = arrayList.get(position);
        holder.bind(dashboardViewModel);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemChangeListener {
        void onItemClick(int position);

        void onSwipe(int position, boolean isSwipe);

        void onEditClick(int position);
        void onViewClick(int position);
    }

    public class cardDrawerViewHolder extends RecyclerView.ViewHolder {
        private RowCardBinding rowCardBinding;
        private OnItemChangeListener listener;

        public cardDrawerViewHolder(@NonNull RowCardBinding rowCardBinding, final OnItemChangeListener listener) {
            super(rowCardBinding.getRoot());
            this.rowCardBinding = rowCardBinding;
            this.listener = listener;

        }

        public void bind(DashboardViewModel dashboardViewModel) {
            this.rowCardBinding.setViewModel(dashboardViewModel);
            rowCardBinding.executePendingBindings();

            if (dashboardViewModel.isBusiness){
                rowCardBinding.tvCardName.setTextColor(MaterialColors.getColor(rowCardBinding.tvCardName, R.attr.text_color));
                rowCardBinding.tvName.setTextColor(MaterialColors.getColor(rowCardBinding.tvName, R.attr.text_color));
                rowCardBinding.tvBusinessType.setTextColor(MaterialColors.getColor(rowCardBinding.tvBusinessType, R.attr.text_color));
                rowCardBinding.selectLayout.setBackgroundResource(R.drawable.bg_card_business);
            }else {
                rowCardBinding.tvCardName.setTextColor(MaterialColors.getColor(rowCardBinding.tvCardName, R.attr.btn_text_color));
                rowCardBinding.tvName.setTextColor(MaterialColors.getColor(rowCardBinding.tvName, R.attr.btn_text_color));
                rowCardBinding.tvBusinessType.setTextColor(MaterialColors.getColor(rowCardBinding.tvBusinessType, R.attr.btn_text_color));
                rowCardBinding.selectLayout.setBackgroundResource(R.drawable.bg_card_individual);
            }

            /*if (checkedPosition == -1) {
                rowCardBinding.btnCheck.setVisibility(View.GONE);
                rowCardBinding.swipeLayout.close(false);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    rowCardBinding.btnCheck.setVisibility(View.VISIBLE);
                    rowCardBinding.swipeLayout.close(true);
                } else {
                    rowCardBinding.btnCheck.setVisibility(View.GONE);
                    rowCardBinding.swipeLayout.close(false);
                }
            }*/

            rowCardBinding.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //rowCardBinding.btnCheck.setVisibility(View.VISIBLE);
                    /*if (checkedPosition != getAdapterPosition()) {*/
                        //notifyItemChanged(checkedPosition);
                        int position = getAdapterPosition();
                        //checkedPosition = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    /*}*/
                }
            });

            rowCardBinding.swipeLayout.setSwipeListener(new SwipeRevealLayout.SimpleSwipeListener() {
                @Override
                public void onClosed(SwipeRevealLayout view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSwipe(position, false);
                        }
                    }
                }

                @Override
                public void onOpened(SwipeRevealLayout view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onSwipe(position, true);
                        }
                    }
                }

            });

            rowCardBinding.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClick(position);
                        }
                    }
                }
            });

            rowCardBinding.btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onViewClick(position);
                        }
                    }
                }
            });
        }

        public RowCardBinding getRowCardBinding() {
            return rowCardBinding;
        }
    }
}
