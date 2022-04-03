package com.example.synqit.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.databinding.RowActivateProductBinding;
import com.example.synqit.databinding.RowHowToUseBinding;
import com.example.synqit.ui.activateproduct.ActivateProductViewModel;
import com.example.synqit.ui.addlink.AddLinkActivity;
import com.example.synqit.ui.howtouse.HowToUseViewModel;

import java.util.ArrayList;

public class HowToUseAdapter extends RecyclerView.Adapter<HowToUseAdapter.connectViewHolder> {

    private Context context;
    private ArrayList<HowToUseViewModel> arrayList;
    private OnItemClickListener mListener;
    private LayoutInflater layoutInflater;

    public HowToUseAdapter(Context context, ArrayList<HowToUseViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onFavoriteItemClick(int position);
    }


    @NonNull
    @Override
    public connectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowHowToUseBinding rowHowToUseBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_how_to_use, parent, false);
        return new HowToUseAdapter.connectViewHolder(rowHowToUseBinding, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull connectViewHolder holder, int position) {
        HowToUseViewModel howToUseViewModel = arrayList.get(position);
        holder.bind(howToUseViewModel);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_sheet_htu);



                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class connectViewHolder extends RecyclerView.ViewHolder {
        private RowHowToUseBinding rowHowToUseBinding;
        private OnItemClickListener listener;
        public connectViewHolder(@NonNull RowHowToUseBinding rowHowToUseBinding, final OnItemClickListener listener) {
            super(rowHowToUseBinding.getRoot());
            this.rowHowToUseBinding = rowHowToUseBinding;
            this.listener = listener;
        }

        public void bind(HowToUseViewModel howToUseViewModel){
            this.rowHowToUseBinding.setViewModel(howToUseViewModel);
            rowHowToUseBinding.executePendingBindings();

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.ic_demo_how_use);

            Glide.with(rowHowToUseBinding.ivIconBusiness.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(howToUseViewModel.getInstructionData().getMedia()).into(rowHowToUseBinding.ivIconBusiness);
        }
    }
}
