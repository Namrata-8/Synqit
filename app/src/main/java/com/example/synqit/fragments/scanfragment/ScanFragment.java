package com.example.synqit.fragments.scanfragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.WINDOW_SERVICE;
import static com.example.synqit.utils.CommonUtils.getSelectedCard;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.synqit.BuildConfig;
import com.example.synqit.R;
import com.example.synqit.databinding.FragmentScanBinding;
import com.example.synqit.fragments.businessfragment4.model.AddImageResponse;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.fragments.homefragment.model.ParamGetSubscribedList;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.DashboardViewModel;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.ui.proupgrade.ProUpgradeActivity;
import com.example.synqit.utils.SessionManager;
import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ScanFragment extends Fragment implements ScanFragmentNavigator {

    private FragmentScanBinding fragmentScanBinding;
    private ScanFragmentViewModel scanFragmentViewModel;
    /*private CardData cardData;*/
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private String selectedImagePath = "";
    private File selectedImageFile;
    private ArrayList<DashboardViewModel> arrayList;
    private List<CardData> cardDataList;
    private boolean isPlanActive = false;
    private String mCurrentPhotoPathOrdinance;

    @Override
    public void onResume() {
        super.onResume();
        if (getSelectedCard(getActivity()) != null) {
            isPlanActive = false;
            if (getSelectedCard(getActivity()).getPlan() == 0) {
                fragmentScanBinding.tvProBusiness.setVisibility(View.VISIBLE);
                fragmentScanBinding.ivAddImage.setVisibility(View.GONE);
            } else {
                isPlanActive = true;
                fragmentScanBinding.tvProBusiness.setVisibility(View.GONE);
                fragmentScanBinding.ivAddImage.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentScanBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_scan, container, false);
        fragmentScanBinding.setViewModel(new ScanFragmentViewModel(this));
        fragmentScanBinding.executePendingBindings();
        scanFragmentViewModel = new ViewModelProvider(this).get(ScanFragmentViewModel.class);

        if (getSelectedCard(getActivity()) != null) {
            CardData cardData = getSelectedCard(getActivity());
            if (cardData.getQrCode() != null) {
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.ic_add_profile);
                Glide.with(fragmentScanBinding.ivImageQr.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(getSelectedCard(getActivity()).getCoverPicture()).into(fragmentScanBinding.ivImageQr);
                Log.e("QrTExt", cardData.getQrCode());
                if (cardData.getQrCode().equals("noQr")) {
                    fragmentScanBinding.tvCopy.setText("https://google.com");
                }else {
                    fragmentScanBinding.tvCopy.setText("https://google.com");
                }
            } else {
                Log.e("QrTExt", "Empty");
                fragmentScanBinding.tvCopy.setText("https://google.com");
            }
        }

        WindowManager manager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int dimen = width < height ? width : height;
        dimen = dimen * 3 / 4;
        qrgEncoder = new QRGEncoder("https://google.com", null, QRGContents.Type.TEXT, dimen);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            fragmentScanBinding.ivQr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Log.e("Tag", e.toString());
        }

        fragmentScanBinding.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.setContentView(R.layout.dialog_copy_link);

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });

        if (getSelectedCard(getActivity()) != null) {
            if (getSelectedCard(getActivity()).getPlan() == 0) {
                isPlanActive = false;
                fragmentScanBinding.tvProBusiness.setVisibility(View.VISIBLE);
                fragmentScanBinding.ivAddImage.setVisibility(View.GONE);
            } else {
                isPlanActive = true;
                fragmentScanBinding.tvProBusiness.setVisibility(View.GONE);
                fragmentScanBinding.ivAddImage.setVisibility(View.VISIBLE);
            }
        }

        fragmentScanBinding.tvProBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ProUpgradeActivity.class).putExtra("AddNewCard", false).putExtra("ISFromSplash", false).putExtra("ISFromScan", true));
            }
        });

        fragmentScanBinding.ivAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                givePermission();
            }
        });

        fragmentScanBinding.rlPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlanActive){
                    givePermission();
                }else {
                    startActivity(new Intent(getActivity(), ProUpgradeActivity.class).putExtra("AddNewCard", false).putExtra("ISFromSplash", false).putExtra("ISFromScan", true));
                }
            }
        });

        scanFragmentViewModel.onUpdateCard().observe(getViewLifecycleOwner(), new Observer<InsertCardResponse>() {
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
                        selectedImageFile.delete();
                    }
                }
            }
        });

        scanFragmentViewModel.addLogoApi().observe(getViewLifecycleOwner(), new Observer<AddImageResponse>() {
            @Override
            public void onChanged(AddImageResponse addImageResponse) {
                if (addImageResponse != null){
                    if (addImageResponse.isSuccess()){
                        if (getSelectedCard(getActivity()) != null) {
                            Log.e("UploadedImage", addImageResponse.getData().getFileUrl());
                            CardData cardData = getSelectedCard(getActivity());
                            ParamInsertCard paramInsertCard = new ParamInsertCard(cardData.getParentUserID(), cardData.getUserID(), cardData.isBusiness(),
                                    cardData.getDisplayName(), cardData.getDob(), cardData.getGender(), cardData.getBusinessType(), cardData.getDisplayPicture(),
                                    cardData.getCoverPicture(), cardData.getPlan(), addImageResponse.getData().getFileUrl(), cardData.getCountry(), cardData.getCity(),
                                    cardData.isPrivate(), cardData.isDirect(), cardData.getProfileName(), cardData.getBio(), cardData.getThemeColor(), cardData.isIconColor(), cardData.getEmail(), cardData.getMobileNumber());
                            scanFragmentViewModel.updateCard(paramInsertCard, getActivity());
                        }
                    }
                }
            }
        });

        return fragmentScanBinding.getRoot();
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

    /*public void doTakePhoto() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);
    }*/
    public void doTakePhoto() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoURI;
            try {
                photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider" , createImageFileOrdinance());
                //photoURI = FileProvider.getUriForFile(WBAddNewProductActivity.this, "com.kreativesquadz.keralacrops.provider", createImageFileOrdinance());
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
        if (!storageDir.exists()){
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (data != null) {
                    Bitmap bitmapImage = BitmapFactory.decodeFile(CropImage.getActivityResult(data).getUri().getPath());

                    fragmentScanBinding.ivImageQr.setImageBitmap(bitmapImage);

                    saveToFile(bitmapImage);
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
                    selectedImagePath = metaCursor.getString(0);
                    selectedImageFile = new File(selectedImagePath);

                    SessionManager.writeString(getActivity(), SessionManager.LOGO_IMAGE_PATH, selectedImagePath);
                    Log.e("selectedLogoImgPath", selectedImagePath);
                    if (selectedImageFile.exists()){
                        if (getSelectedCard(getActivity()) != null) {
                            if (getSelectedCard(getActivity()).getUserUID() != null) {
                                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), selectedImageFile);
                                MultipartBody.Part body = MultipartBody.Part.createFormData("Image", selectedImageFile.getName(), requestFile);
                                RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), getSelectedCard(getActivity()).getUserUID());
                                scanFragmentViewModel.addLogo(body, userId, getActivity());
                            } else {
                                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), selectedImageFile);
                                MultipartBody.Part body = MultipartBody.Part.createFormData("Image", selectedImageFile.getName(), requestFile);
                                RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), getSelectedCard(getActivity()).getUserID());
                                scanFragmentViewModel.addLogo(body, userId, getActivity());
                            }
                        }
                    }

                }
            } finally {
                metaCursor.close();
            }
        }
    }

    private void performCrop(Uri uri) {
        // take care of exceptions
        try {
            CropImage.activity(uri).setRequestedSize(500, 500).setGuidelines(CropImageView.Guidelines.ON)
                    .start(getContext(), this);
        } catch (ActivityNotFoundException activityNotFound) {
            Log.e("activityNotFound", activityNotFound.getMessage());
            activityNotFound.fillInStackTrace();
        }

    }
}