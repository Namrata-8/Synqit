package com.example.synqit.fragments.homefragment;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.R;
import com.example.synqit.adapters.YourLinkAdapter;
import com.example.synqit.databinding.FragmentHomeBinding;
import com.example.synqit.fragments.businessfragment4.model.InsertCardData;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.fragments.homefragment.model.ParamGetSubscribedList;
import com.example.synqit.fragments.homefragment.model.ParamUpdateSocialMedia;
import com.example.synqit.fragments.homefragment.model.SubScribeListResponse;
import com.example.synqit.ui.addlink.AddLinkActivity;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.utils.LoadingDialog;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements HomeFragmentNavigator {

    private YourLinkAdapter yourLinkAdapter;
    private FragmentHomeBinding fragmentHomeBinding;
    private HomeFragmentViewModel homeFragmentViewModel;
    private boolean isPrivate = false;
    private ArrayList<DashboardViewModel> arrayList;
    private List<CardData> cardDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        fragmentHomeBinding.setViewModel(new HomeFragmentViewModel(this));
        fragmentHomeBinding.executePendingBindings();

        homeFragmentViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        fragmentHomeBinding.rvYourLink.setNestedScrollingEnabled(false);

        if (getSelectedCard(getActivity()) != null) {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.demo2);

            RequestOptions requestOptions2 = new RequestOptions();
            requestOptions2.placeholder(R.drawable.ic_add_profile);

            Glide.with(fragmentHomeBinding.ivCover.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(getSelectedCard(getActivity()).getCoverPicture()).into(fragmentHomeBinding.ivCover);

            Glide.with(fragmentHomeBinding.ivLogo.getContext())
                    .setDefaultRequestOptions(requestOptions2)
                    .load(getSelectedCard(getActivity()).getDisplayPicture()).into(fragmentHomeBinding.ivLogo);
            fragmentHomeBinding.tvName.setText(new Gson().fromJson(SessionManager.readSelectedCardData(getActivity(), SessionManager.Selected_Card_Data, ""), CardData.class).getDisplayName());

            Log.e("PrivatePublic", getSelectedCard(getActivity()).isPrivate() + " \n" + getSelectedCard(getActivity()).isDirect());

            if (getSelectedCard(getActivity()).isPrivate()) {
                isPrivate = true;
                fragmentHomeBinding.tvPrivatePublic.setText("Private");
            } else {
                isPrivate = false;
                fragmentHomeBinding.tvPrivatePublic.setText("Public");
            }

            fragmentHomeBinding.switchPrivatePublic.setChecked(getSelectedCard(getActivity()).isDirect());

            if (getSelectedCard(getActivity()).getPlan() > 0){
                fragmentHomeBinding.tvProBusiness.setVisibility(View.VISIBLE);
            }else {
                fragmentHomeBinding.tvProBusiness.setVisibility(View.GONE);
            }
        }

        fragmentHomeBinding.rvYourLink.setHasFixedSize(true);
        fragmentHomeBinding.rvYourLink.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        loadUserLinks();

        homeFragmentViewModel.onUpdateCard().observe(getViewLifecycleOwner(), new Observer<InsertCardResponse>() {
            @Override
            public void onChanged(InsertCardResponse insertCardResponse) {
                arrayList=new ArrayList<>();
                if (insertCardResponse != null) {
                    Toast.makeText(getActivity(), insertCardResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    if (insertCardResponse.isSuccess()){
                        cardDataList = new ArrayList<>();
                        cardDataList = insertCardResponse.getData();
                        for (int i = 0; i < cardDataList.size(); i++){
                            CardData cardData = cardDataList.get(i);
                            DashboardViewModel dashboardViewModel = new DashboardViewModel(cardData);
                            arrayList.add(dashboardViewModel);
                        }
                        DashboardActivity.cardDrawerAdapter.swap(arrayList);
                        SessionManager.saveSelectedCardData(getActivity(), SessionManager.Selected_Card_Data, new Gson().toJson(insertCardResponse.getData().get(SessionManager.readInt(getActivity(), SessionManager.SELECTED_CARD_POSITION, 0))));
                    }
                }
            }
        });

        homeFragmentViewModel.onUpdateSocialMedia().observe(getViewLifecycleOwner(), new Observer<SubScribeListResponse>() {
            @Override
            public void onChanged(SubScribeListResponse subScribeListResponse) {
                if (subScribeListResponse != null) {
                    Toast.makeText(getActivity(), subScribeListResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("SubScribeListResponse", new Gson().toJson(subScribeListResponse).toString());
                }
            }
        });

        fragmentHomeBinding.switchPrivatePublic.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (getSelectedCard(getActivity()) != null) {
                    CardData cardData = getSelectedCard(getActivity());
                    ParamInsertCard paramInsertCard = new ParamInsertCard(cardData.getParentUserID(), cardData.getUserID(), cardData.isBusiness(),
                            cardData.getDisplayName(), cardData.getDob(), cardData.getGender(), cardData.getBusinessType(), cardData.getDisplayPicture(),
                            cardData.getCoverPicture(), cardData.getPlan(), cardData.getQrCode(), cardData.getCountry(), cardData.getCity(),
                            cardData.isPrivate(), isChecked, cardData.getProfileName(), cardData.getBio(), cardData.getThemeColor(), cardData.isIconColor(), cardData.getEmail(), cardData.getMobileNumber());
                    homeFragmentViewModel.updateCard(paramInsertCard, getActivity());
                }
            }
        });

        /*fragmentHomeBinding.tvPrivatePublic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPrivate) {
                    isPrivate = false;
                    fragmentHomeBinding.tvPrivatePublic.setText("Public");
                    if (getSelectedCard(getActivity()) != null) {
                        CardData cardData = getSelectedCard(getActivity());
                        ParamInsertCard paramInsertCard = new ParamInsertCard(cardData.getParentUserID(), cardData.getUserID(), cardData.isBusiness(),
                                cardData.getDisplayName(), cardData.getDob(), cardData.getGender(), cardData.getBusinessType(), cardData.getDisplayPicture(),
                                cardData.getCoverPicture(), cardData.getPlan(), cardData.getQrCode(), cardData.getCountry(), cardData.getCity(),
                                false, cardData.isDirect(), cardData.getProfileName(), cardData.getBio(), cardData.getThemeColor(), cardData.isIconColor(), cardData.getEmail(), cardData.getMobileNumber());
                        homeFragmentViewModel.updateCard(paramInsertCard, getActivity());
                    }
                } else {
                    isPrivate = true;
                    fragmentHomeBinding.tvPrivatePublic.setText("Private");
                    if (getSelectedCard(getActivity()) != null) {
                        CardData cardData = getSelectedCard(getActivity());
                        ParamInsertCard paramInsertCard = new ParamInsertCard(cardData.getParentUserID(), cardData.getUserID(), cardData.isBusiness(),
                                cardData.getDisplayName(), cardData.getDob(), cardData.getGender(), cardData.getBusinessType(), cardData.getDisplayPicture(),
                                cardData.getCoverPicture(), cardData.getPlan(), cardData.getQrCode(), cardData.getCountry(), cardData.getCity(),
                                true, cardData.isDirect(), cardData.getProfileName(), cardData.getBio(), cardData.getThemeColor(), cardData.isIconColor(), cardData.getEmail(), cardData.getMobileNumber());
                        homeFragmentViewModel.updateCard(paramInsertCard, getActivity());
                    }
                }
            }
        });*/

        fragmentHomeBinding.tvDirectInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoBottomSheet();
            }
        });

        return fragmentHomeBinding.getRoot();
    }

    private void openInfoBottomSheet() {
        final Dialog dialog2 = new Dialog(getActivity());
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog2.setCancelable(true);
        dialog2.setCanceledOnTouchOutside(true);
        dialog2.setContentView(R.layout.dialog_info_add_link);

        MaterialButton btnDone = dialog2.findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });

        dialog2.show();
        dialog2.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void loadUserLinks() {
        /*if (getActivity().getWindow().getDecorView().isShown()) {*/
            if (getSelectedCard(getActivity()) != null) {
                ParamGetSubscribedList paramGetSubscribedList;
                if (getSelectedCard(getActivity()).getUserUID() != null) {
                    paramGetSubscribedList = new ParamGetSubscribedList(getSelectedCard(getActivity()).getUserUID());
                } else {
                    paramGetSubscribedList = new ParamGetSubscribedList(getSelectedCard(getActivity()).getUserID());
                }
                homeFragmentViewModel.getUserLinkLiveData(paramGetSubscribedList, getActivity()).observe(getViewLifecycleOwner(), new Observer<ArrayList<HomeFragmentViewModel>>() {
                    @Override
                    public void onChanged(ArrayList<HomeFragmentViewModel> homeFragmentViewModels) {
                        if (!homeFragmentViewModels.isEmpty()) {
                            yourLinkAdapter = new YourLinkAdapter(getActivity(), homeFragmentViewModels);
                            fragmentHomeBinding.rvYourLink.setAdapter(yourLinkAdapter);
                            yourLinkAdapter.setOnItemClickListener(new YourLinkAdapter.OnItemChangeListener() {
                                @Override
                                public void onItemEnabled(int position) {
                                    Log.e("Enabled Click", homeFragmentViewModels.get(position).getSubscribedData().getId() + "\n" +
                                            homeFragmentViewModels.get(position).getSubscribedData().getLinkTitle() + "\n" + homeFragmentViewModels.get(position).getSubscribedData().getUsername() + "\n" + false);
                                    ParamUpdateSocialMedia paramUpdateSocialMedia = new ParamUpdateSocialMedia(homeFragmentViewModels.get(position).getSubscribedData().getId(), homeFragmentViewModels.get(position).getSubscribedData().getLinkTitle(),
                                            homeFragmentViewModels.get(position).getSubscribedData().getUsername(), false);
                                    homeFragmentViewModel.updateSocialMedia(paramUpdateSocialMedia, getActivity());
                                }

                                @Override
                                public void onItemDisabled(int position) {
                                    Log.e("Disabled Click", homeFragmentViewModels.get(position).getSubscribedData().getId() + "\n" +
                                            homeFragmentViewModels.get(position).getSubscribedData().getLinkTitle() + "\n" + homeFragmentViewModels.get(position).getSubscribedData().getUsername() + "\n" + true);
                                    ParamUpdateSocialMedia paramUpdateSocialMedia = new ParamUpdateSocialMedia(homeFragmentViewModels.get(position).getSubscribedData().getId(), homeFragmentViewModels.get(position).getSubscribedData().getLinkTitle(),
                                            homeFragmentViewModels.get(position).getSubscribedData().getUsername(), true);
                                    homeFragmentViewModel.updateSocialMedia(paramUpdateSocialMedia, getActivity());
                                }

                                @Override
                                public void onDeleteClick(int position) {

                                }
                            });
                        }
                    }
                });
            }
        /*}*/
    }

    @Override
    public void gotoAddLink() {
        startActivity(new Intent(getActivity(), AddLinkActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoadingDialog loadingDialog = new LoadingDialog(getActivity());
        loadingDialog.dismissDialog();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getActivity().isFinishing()){
            LoadingDialog loadingDialog = new LoadingDialog(getActivity());
            loadingDialog.dismissDialog();
        }
    }
}

