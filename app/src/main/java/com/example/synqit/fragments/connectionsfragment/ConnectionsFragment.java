package com.example.synqit.fragments.connectionsfragment;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.synqit.BuildConfig;
import com.example.synqit.R;
import com.example.synqit.adapters.ConnectedAdapter;
import com.example.synqit.adapters.FavoriteConnectAdapter;
import com.example.synqit.customeviews.TextViewSemiBold;
import com.example.synqit.databinding.FragmentConnectionsBinding;
import com.example.synqit.fragments.connectionsfragment.model.ParamGetConnection;
import com.example.synqit.ui.createconnection.CreateConnectionActivity;
import com.example.synqit.ui.createconnection.model.InsertConnectionResponse;
import com.example.synqit.ui.createconnection.model.ParamCreateConnection;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.utils.SessionManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.skyhope.textrecognizerlibrary.TextScanner;
import com.skyhope.textrecognizerlibrary.callback.TextExtractCallback;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ConnectionsFragment extends Fragment implements ConnectionsFragmentNavigator {

    String mCurrentPhotoPathOrdinance;
    private FragmentConnectionsBinding fragmentConnectionsBinding;
    private ConnectionsFragmentViewModel connectionsFragmentViewModel;
    private RecyclerView rvFavorite, rvConnections;
    private ExtendedFloatingActionButton btnAdd;
    private ConnectedAdapter connectedAdapter;
    private String selectedCoverImgPath = "";
    private File selectedCoverImgFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentConnectionsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_connections, container, false);
        fragmentConnectionsBinding.setViewModel(new ConnectionsFragmentViewModel(this));
        fragmentConnectionsBinding.executePendingBindings();

        connectionsFragmentViewModel = new ViewModelProvider(this).get(ConnectionsFragmentViewModel.class);

        if (!SessionManager.readString(getActivity(), SessionManager.parentUserID, "").equals("")) {
            ParamGetConnection paramGetConnection = new ParamGetConnection(SessionManager.readString(getActivity(), SessionManager.parentUserID, ""));
            connectionsFragmentViewModel.getConnections(paramGetConnection, getActivity());
        }

        connectionsFragmentViewModel.getConnectedConnections().observe(getViewLifecycleOwner(), new Observer<ArrayList<ConnectionsFragmentViewModel>>() {
            @Override
            public void onChanged(ArrayList<ConnectionsFragmentViewModel> connectionsFragmentViewModels) {
                if (!connectionsFragmentViewModels.isEmpty()) {
                    ConnectedAdapter connectedAdapter = new ConnectedAdapter(getActivity(), connectionsFragmentViewModels);
                    fragmentConnectionsBinding.rvConnections.setAdapter(connectedAdapter);

                    connectedAdapter.setOnItemClickListener(new ConnectedAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                        }

                        @Override
                        public void onFavoriteItemClick(int position) {
                            if (connectionsFragmentViewModels.get(position).getConnectedData().isManualConnect()) {
                                ParamCreateConnection paramCreateConnection = new ParamCreateConnection();
                                paramCreateConnection.setId(connectionsFragmentViewModels.get(position).getConnectedData().getId());
                                paramCreateConnection.setUserUID(connectionsFragmentViewModels.get(position).getConnectedData().getUserUID());
                                paramCreateConnection.setPartnerUID(connectionsFragmentViewModels.get(position).getConnectedData().getPartnerUID());
                                paramCreateConnection.setEmail(connectionsFragmentViewModels.get(position).getConnectedData().getEmail());
                                paramCreateConnection.setDisplayName(connectionsFragmentViewModels.get(position).getConnectedData().getDisplayName());
                                paramCreateConnection.setWebsite(connectionsFragmentViewModels.get(position).getConnectedData().getWebsite());
                                paramCreateConnection.setLocation(connectionsFragmentViewModels.get(position).getConnectedData().getLocation());
                                paramCreateConnection.setSocialMedia(connectionsFragmentViewModels.get(position).getConnectedData().getSocialMedia());
                                paramCreateConnection.setMobileNumber(connectionsFragmentViewModels.get(position).getConnectedData().getMobileNumber());
                                paramCreateConnection.setJobTitle(connectionsFragmentViewModels.get(position).getConnectedData().getJobTitle());
                                paramCreateConnection.setNotes(connectionsFragmentViewModels.get(position).getConnectedData().getNotes());
                                paramCreateConnection.setDisplayPicture(connectionsFragmentViewModels.get(position).getConnectedData().getDisplayPicture());
                                paramCreateConnection.setCoverPicture(connectionsFragmentViewModels.get(position).getConnectedData().getCoverPicture());
                                paramCreateConnection.setGender(connectionsFragmentViewModels.get(position).getConnectedData().getGender());
                                paramCreateConnection.setDeleted(connectionsFragmentViewModels.get(position).getConnectedData().isDeleted());
                                paramCreateConnection.setFavorite(!connectionsFragmentViewModels.get(position).getConnectedData().isFavorite());
                                paramCreateConnection.setBusiness(connectionsFragmentViewModels.get(position).getConnectedData().isBusiness());
                                paramCreateConnection.setBlocked(connectionsFragmentViewModels.get(position).getConnectedData().isBlocked());
                                paramCreateConnection.setVerified(connectionsFragmentViewModels.get(position).getConnectedData().isVerified());
                                paramCreateConnection.setManualConnect(connectionsFragmentViewModels.get(position).getConnectedData().isManualConnect());
                                paramCreateConnection.setPlan(connectionsFragmentViewModels.get(position).getConnectedData().getPlan());
                                paramCreateConnection.setConnectedDate(connectionsFragmentViewModels.get(position).getConnectedData().getConnectedDate());
                                paramCreateConnection.setDob(connectionsFragmentViewModels.get(position).getConnectedData().getDob());

                                connectionsFragmentViewModel.updateConnect(paramCreateConnection, getActivity());
                            }
                        }

                        @Override
                        public void onItemDeleteClick(int position) {

                        }
                    });
                }
            }
        });
        connectionsFragmentViewModel.getFavoriteConnections().observe(getViewLifecycleOwner(), new Observer<ArrayList<ConnectionsFragmentViewModel>>() {
            @Override
            public void onChanged(ArrayList<ConnectionsFragmentViewModel> connectionsFragmentViewModels) {
                if (!connectionsFragmentViewModels.isEmpty()) {
                    fragmentConnectionsBinding.tvFavorite.setVisibility(View.VISIBLE);
                    fragmentConnectionsBinding.rvFavorite.setAdapter(new FavoriteConnectAdapter(getActivity(), connectionsFragmentViewModels));
                } else {
                    fragmentConnectionsBinding.tvFavorite.setVisibility(View.GONE);
                }
            }
        });

        connectionsFragmentViewModel.getUpdateConnectResponseMutableLiveData().observe(getViewLifecycleOwner(), new Observer<InsertConnectionResponse>() {
            @Override
            public void onChanged(InsertConnectionResponse insertConnectionResponse) {
                if (insertConnectionResponse != null) {
                    if (insertConnectionResponse.isSuccess()) {
                        //startActivity(new Intent(getActivity(), DashboardActivity.class).putExtra("ISFromConnection", true).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        if (!SessionManager.readString(getActivity(), SessionManager.parentUserID, "").equals("")) {
                            ParamGetConnection paramGetConnection = new ParamGetConnection(SessionManager.readString(getActivity(), SessionManager.parentUserID, ""));
                            connectionsFragmentViewModel.getConnections(paramGetConnection, getActivity());
                        }
                    } else {
                        Toast.makeText(getActivity(), insertConnectionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        fragmentConnectionsBinding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                hideKeyboard();
                //connectedAdapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fragmentConnectionsBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_sheet_add_connections);

                TextViewSemiBold tvCreateManualy = dialog.findViewById(R.id.tvCreateManualy);
                TextViewSemiBold tvScanBusinessCard = dialog.findViewById(R.id.tvScanBusinessCard);
                tvCreateManualy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(), CreateConnectionActivity.class).putExtra("ScannedText", (Serializable) new ArrayList<String>()));
                    }
                });

                tvScanBusinessCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        givePermission();
                    }
                });


                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });

        return fragmentConnectionsBinding.getRoot();
    }

    public void givePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // only for gingerbread and newer versions
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                selectimage();
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
        } else {
            selectimage();
        }
    }

    public void selectimage() {

        final String[] items = {"take Photo", "Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    doTakePhoto();
                } else if (item == 1) {
                    doTakeGallery();
                }
            }

        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void doTakePhoto() {
        /*Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);*/
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoURI;
            try {
                //  photoURI = FileProvider.getUriForFile(EditProfileActivity.this, BuildConfig.APPLICATION_ID , createImageFileOrdinance());
                photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", createImageFileOrdinance());
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivityForResult(cameraIntent, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            startActivityForResult(cameraIntent, 0);
        }
    }

    private File createImageFileOrdinance() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPathOrdinance = "file:" + image.getAbsolutePath();
        return image;
    }

    private void doTakeGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
    }

    private void saveToFile(Bitmap bitmapImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Uri path = Uri.parse(MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapImage, "Title_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()), null));
        String[] projection = new String[]{"_data"};
        ContentResolver cr = getActivity().getContentResolver();
        Cursor metaCursor = cr.query(path, projection, null, null, null);
        if (metaCursor != null) {
            try {
                if (metaCursor.moveToFirst()) {
                    selectedCoverImgPath = metaCursor.getString(0);
                    selectedCoverImgFile = new File(selectedCoverImgPath);
                    getScannedText(Uri.fromFile(selectedCoverImgFile));

                    Log.e("selectedCoverImgPath", selectedCoverImgPath);
                }
            } finally {
                metaCursor.close();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (data != null) {
                    Bitmap bitmapImage = BitmapFactory.decodeFile(CropImage.getActivityResult(data).getUri().getPath());
                    saveToFile(bitmapImage);
                    //getScannedText(Uri.fromFile(new File(CropImage.getActivityResult(data).getUri().getPath())));
                }
                break;

            case 1: {
                if (resultCode == RESULT_OK) {
                    try {
                        performCrop(data.getData());

                    } catch (Exception e) {
                        Log.e("!!!!", e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
            break;
            case 0: {
                if (resultCode == RESULT_OK) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        /*performCrop(data.getData());*/

                        performCrop(Uri.parse(mCurrentPhotoPathOrdinance));

                    } else {
                        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                        performCrop(Uri.fromFile(file));
                    }
                }
            }
            break;
        }
    }

    private void getScannedText(Uri uri) {
        TextScanner.getInstance(getActivity())
                .init()
                .load(uri)
                .getCallback(new TextExtractCallback() {
                    @Override
                    public void onGetExtractText(List<String> textList) {
                        // Here ypu will get list of text
                        String email = "";
                        String phone = "";

                        final StringBuilder text = new StringBuilder();
                        for (String s : textList) {
                            text.append(s).append("\n");
                        }

                        Log.e("ScannedText", text.toString() + "\n" + textList.size() + "\n ***");
                        if (selectedCoverImgFile.exists()) {
                            selectedCoverImgFile.delete();
                        }
                        startActivity(new Intent(getActivity(), CreateConnectionActivity.class).putExtra("ScannedText", (Serializable) textList));
                    }
                });
    }

    private void performCrop(Uri uri) {
        try {
            CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(16, 9)
                    .start(getContext(), this);
        } catch (ActivityNotFoundException activityNotFound) {
            Log.e("activityNotFound", activityNotFound.getMessage());
            activityNotFound.fillInStackTrace();
        }
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch(Exception ignored) {
        }
    }
}