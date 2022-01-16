package com.example.synqit.fragments.businessfragment4;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.databinding.FragmentBusiness4Binding;
import com.example.synqit.fragments.businessfragment4.model.AddLogoResponse;
import com.example.synqit.fragments.businessfragment4.model.UploadCoverPhotoResponse;
import com.example.synqit.ui.proupgrade.ProUpgradeActivity;
import com.example.synqit.utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BusinessFragment4 extends Fragment implements BusinessFragment4Navigator {

    private FragmentBusiness4Binding fragmentBusiness4Binding;
    private BusinessFragment4ViewModel fragment4ViewModel;

    private String mCurrentPhotoPathOrdinance;
    private Bitmap Profilebitmap = null;
    private boolean isLogoImg = false;

    private String selectedLogoImgPath = "";
    private String selectedCoverImgPath = "";
    private File selectedLogoImgFile;
    private File selectedCoverImgFile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentBusiness4Binding = DataBindingUtil.inflate(inflater, R.layout.fragment_business4, container, false);
        fragmentBusiness4Binding.setViewModel(new BusinessFragment4ViewModel(this));
        fragmentBusiness4Binding.executePendingBindings();
        initViewModel();

        return fragmentBusiness4Binding.getRoot();
    }

    private void initViewModel() {
        fragment4ViewModel = new ViewModelProvider(this).get(BusinessFragment4ViewModel.class);

        fragment4ViewModel.addLogoApi().observe(getViewLifecycleOwner(), new Observer<AddLogoResponse>() {
            @Override
            public void onChanged(AddLogoResponse addLogoResponse) {
                if (addLogoResponse != null){
                    if (!addLogoResponse.isSuccess()){
                        SessionManager.writeString(getActivity(), SessionManager.LOGO_FILE_URL, addLogoResponse.getFileUrl());
                        if (selectedCoverImgFile.exists() && !SessionManager.readString(getActivity(), SessionManager.BASIC_REGISTER_ID, "").equals("")){
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), selectedCoverImgFile);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("Cover", selectedCoverImgFile.getName(), requestFile);
                            RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), SessionManager.readString(getActivity(), SessionManager.BASIC_REGISTER_ID, ""));

                            fragment4ViewModel.uploadCoverPhoto(body, userId);
                        }
                    }
                }
            }
        });

        fragment4ViewModel.uploadCoverPhotoApi().observe(getViewLifecycleOwner(), new Observer<UploadCoverPhotoResponse>() {
            @Override
            public void onChanged(UploadCoverPhotoResponse uploadCoverPhotoResponse) {
                if (uploadCoverPhotoResponse != null){
                    if (!uploadCoverPhotoResponse.isSuccess()){
                        SessionManager.writeString(getActivity(), SessionManager.COVER_FILE_URL, uploadCoverPhotoResponse.getFileUrl());

                        startActivity(new Intent(getActivity(), ProUpgradeActivity.class));
                    }
                }
            }
        });
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
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent,0);
    }

    private void doTakeGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "Select File"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (data != null) {
                    Bitmap bitmapImage = BitmapFactory.decodeFile(CropImage.getActivityResult(data).getUri().getPath());
                    if (isLogoImg) {
                        fragmentBusiness4Binding.profileImage.setImageBitmap(bitmapImage);
                    }else {
                        fragmentBusiness4Binding.ivUploadCoverPhoto.setImageBitmap(bitmapImage);
                    }
                    saveToFile(bitmapImage);
                }
                break;

            case 1: {
                if (resultCode == RESULT_OK) {
                    try {
                        performCrop(data.getData());
                        /*Log.e("GalleryPhoto", data.getData()+"\n"+data.getData().getPath());
                        Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                        if (isLogoImg) {
                            uploadProfileImage = data.getData().toString();
                            fragmentBusiness4Binding.profileImage.setImageBitmap(bitmapImage);
                        }else {
                            uploadCoverImage = data.getData().toString();
                            fragmentBusiness4Binding.ivUploadCoverPhoto.setImageBitmap(bitmapImage);
                        }*/
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
                        performCrop(data.getData());
                        /*Bitmap bitmapImage = null;
                        try {
                            Log.e("CameraPhoto", mCurrentPhotoPathOrdinance);
                            bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPathOrdinance));
                            if (isLogoImg) {
                                uploadProfileImage = mCurrentPhotoPathOrdinance;
                                fragmentBusiness4Binding.profileImage.setImageBitmap(bitmapImage);
                            }else {
                                uploadCoverImage = mCurrentPhotoPathOrdinance;
                                fragmentBusiness4Binding.ivUploadCoverPhoto.setImageBitmap(bitmapImage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    } else {
                        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                        performCrop(Uri.fromFile(file));
                    }
                }
            }
            break;
        }
    }

    private void saveToFile(Bitmap bitmapImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        Uri path = Uri.parse(MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmapImage, "Title", null));
        String[] projection = new String[]{"_data"};
        ContentResolver cr = getActivity().getContentResolver();
        Cursor metaCursor = cr.query(path, projection, null, null, null);
        if (metaCursor != null) {
            try {
                if (metaCursor.moveToFirst()) {
                    if (isLogoImg) {
                        selectedLogoImgPath = metaCursor.getString(0);
                        selectedLogoImgFile = new File(selectedLogoImgPath);

                        SessionManager.writeString(getActivity(), SessionManager.LOGO_IMAGE_PATH, selectedLogoImgPath);

                        Log.e("selectedLogoImgPath", selectedLogoImgPath);
                    }else {
                        selectedCoverImgPath = metaCursor.getString(0);
                        selectedCoverImgFile = new File(selectedCoverImgPath);

                        SessionManager.writeString(getActivity(), SessionManager.COVER_IMAGE_PATH, selectedCoverImgPath);
                        Log.e("selectedCoverImgPath", selectedCoverImgPath);
                    }
                }
            }finally {
                metaCursor.close();
            }
        }
    }

    private void performCrop(Uri uri) {
        // take care of exceptions
        if (isLogoImg){
            try {
                CropImage.activity(uri).setRequestedSize(500, 500).setGuidelines(CropImageView.Guidelines.ON)
                        .start(getContext(), this);
            } catch (ActivityNotFoundException activityNotFound) {
                Log.e("activityNotFound", activityNotFound.getMessage());
                activityNotFound.fillInStackTrace();
            }
        }else {
            try {
                CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(16, 9)
                        .start(getContext(), this);
            } catch (ActivityNotFoundException activityNotFound) {
                Log.e("activityNotFound", activityNotFound.getMessage());
                activityNotFound.fillInStackTrace();
            }
        }
    }

    @Override
    public void addLogo() {
        isLogoImg = true;
        givePermission();
        fragmentBusiness4Binding.profileImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void uploadCoverPhoto() {
        isLogoImg = false;
        givePermission();
        fragmentBusiness4Binding.tvUploadImgText.setVisibility(View.GONE);
    }

    @Override
    public void onFinish() {
        //startActivity(new Intent(getActivity(), ProUpgradeActivity.class));
        Log.e("Fragment3Data", SessionManager.readString(getActivity(), SessionManager.COMPANY_NAME, "*") +
                "\n" + SessionManager.readString(getActivity(), SessionManager.FULL_NAME, "*") + "\n" +
                SessionManager.readString(getActivity(), SessionManager.COUNTRY_NAME,"*"));
        if (selectedLogoImgPath.length() == 0){
            Toast.makeText(getActivity(), "Please Upload Profile Image", Toast.LENGTH_SHORT).show();
        }else if (selectedCoverImgPath.length() == 0){
            Toast.makeText(getActivity(), "Please Upload Cover Image", Toast.LENGTH_SHORT).show();
        }else {
           if (selectedLogoImgFile.exists() && !SessionManager.readString(getActivity(), SessionManager.BASIC_REGISTER_ID, "").equals("")){
               RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), selectedLogoImgFile);
               MultipartBody.Part body = MultipartBody.Part.createFormData("Profile", selectedLogoImgFile.getName(), requestFile);
               RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), SessionManager.readString(getActivity(), SessionManager.BASIC_REGISTER_ID, ""));

               fragment4ViewModel.addLogo(body, userId);
           }
        }
    }

    @Override
    public void onSkipNow() {
        startActivity(new Intent(getActivity(), ProUpgradeActivity.class));
    }
}