package com.example.synqit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.R;
import com.example.synqit.databinding.RowSwipeCardBinding;
import com.example.synqit.ui.addlink.AddLinkViewModel;
import com.example.synqit.ui.addlink.model.CommonLinkData;

import java.util.ArrayList;
import java.util.List;

public class SwipeCardAdapter extends RecyclerView.Adapter<SwipeCardAdapter.swipeCardViewHolder> {

    private Context context;
    private ArrayList<AddLinkViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;
    private SwipeAppListAdapter swipeAppListAdapter;

    public SwipeCardAdapter(Context context, ArrayList<AddLinkViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public swipeCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowSwipeCardBinding rowSwipeCardBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_swipe_card, parent, false);
        return new swipeCardViewHolder(rowSwipeCardBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull swipeCardViewHolder holder, int position) {
        AddLinkViewModel addLinkViewModel = arrayList.get(position);
        holder.bind(addLinkViewModel, position);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void onItemAddLinkClick(int position, int positionApp);
    }

    public class swipeCardViewHolder extends RecyclerView.ViewHolder {
        private RowSwipeCardBinding rowSwipeCardBinding;
        private OnItemClickListener listener;

        public swipeCardViewHolder(@NonNull RowSwipeCardBinding rowSwipeCardBinding, final OnItemClickListener listener) {
            super(rowSwipeCardBinding.getRoot());
            this.rowSwipeCardBinding = rowSwipeCardBinding;
            this.listener = listener;
        }

        public void bind(AddLinkViewModel addLinkViewModel, int position) {
            this.rowSwipeCardBinding.setViewModel(addLinkViewModel);
            rowSwipeCardBinding.executePendingBindings();
            swipeAppListAdapter = new SwipeAppListAdapter(context, addLinkViewModel.getCategories().getAppList());
            rowSwipeCardBinding.rvCategoryAppList.setAdapter(swipeAppListAdapter);

            swipeAppListAdapter.setOnItemClickListener(new SwipeAppListAdapter.OnItemClickListener() {
                @Override
                public void onItemAddLinkClick(int positionApp) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemAddLinkClick(positionApp, position);
                        }
                    }
                }
            });
        }
    }
}
