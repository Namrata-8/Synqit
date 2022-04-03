package com.example.synqit.ui.createconnection;

import static com.example.synqit.utils.CommonUtils.getSelectedCard;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.synqit.R;
import com.example.synqit.databinding.ActivityCreateConnectionBinding;
import com.example.synqit.fragments.businessfragment4.model.AddImageResponse;
import com.example.synqit.ui.createconnection.model.InsertConnectionResponse;
import com.example.synqit.ui.createconnection.model.ParamCreateConnection;
import com.example.synqit.ui.dashboard.DashboardActivity;
import com.example.synqit.ui.dashboard.model.CardData;
import com.example.synqit.utils.SessionManager;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateConnectionActivity extends AppCompatActivity implements CreateConnectionNavigator{

    private ActivityCreateConnectionBinding activityCreateConnectionBinding;
    private CreateConnectionViewModel createConnectionViewModel;
    private String name = "";
    private String emailAddress = "";
    private String company = "";
    private String website = "";
    private String location = "";
    private String socialMedia = "";
    private String mobileNo = "";
    private String countryCode = "";
    private String jobTitle = "";
    private String notes = "";
    private String logoFileUrl = "";
    private String coverFileUrl = "";
    private boolean isLogoImg = false;
    private String selectedLogoImgPath = "";
    private String selectedCoverImgPath = "";
    private File selectedLogoImgFile;
    private File selectedCoverImgFile;

    private List<String> ScannedText = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(SessionManager.readBoolean(this, SessionManager.IS_LIGHT_DARK, false)){
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        super.onCreate(savedInstanceState);
        activityCreateConnectionBinding = DataBindingUtil.setContentView(this, R.layout.activity_create_connection);
        activityCreateConnectionBinding.setViewModel(new CreateConnectionViewModel(this));
        activityCreateConnectionBinding.executePendingBindings();
        initViewModel();

        ScannedText = (List<String>) getIntent().getSerializableExtra("ScannedText");
        if (!ScannedText.isEmpty()){
            for (String s : ScannedText) {
                String regex = ".*(\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b).*";
                Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(s);
                if (m.matches()) {
                    if( m.group(1) != null) {
                        activityCreateConnectionBinding.etEmail.setText(m.group(1));
                        break;
                    }
                }
            }
            for (String s : ScannedText) {
                String regexPhone = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
                Pattern pPhone = Pattern.compile(regexPhone, Pattern.CASE_INSENSITIVE);
                Matcher mPhone = pPhone.matcher(s);
                if (mPhone.matches()) {
                    if (mPhone.group(1) != null) {
                        activityCreateConnectionBinding.etMobile.setText(mPhone.group(1));
                        break;
                    }
                }
            }
            for (String s : ScannedText) {
                String regexWeb = "^((https?|ftp|smtp):\\/\\/)?(www.)?[a-z0-9]+\\.[a-z]+(\\/[a-zA-Z0-9#]+\\/?)*$";
                Pattern pWeb = Pattern.compile(regexWeb, Pattern.CASE_INSENSITIVE);
                Matcher mWeb = pWeb.matcher(s);
                if (mWeb.matches()) {
                    if (mWeb.group(1) != null) {
                        activityCreateConnectionBinding.etWebsite.setText(mWeb.group(1));
                        break;
                    }
                }
            }
            /*for (String s : ScannedText) {
                String regexName = "^[A-Z][-a-zA-Z]+$";
                Pattern pName = Pattern.compile(regexName, Pattern.CASE_INSENSITIVE);
                Matcher mName = pName.matcher("Harsh");
                if (mName.matches()) {
                    Toast.makeText(CreateConnectionActivity.this,  mName.group(1) + "\n **" , Toast.LENGTH_SHORT).show();
                    if (mName.group(1) != null) {
                        activityCreateConnectionBinding.etName.setText(mName.group(1));
                        break;
                    }
                }
            }*/
        }

    }

    private void initViewModel() {
        createConnectionViewModel = new ViewModelProvider(this).get(CreateConnectionViewModel.class);

        createConnectionViewModel.addLogoApi().observe(this, new Observer<AddImageResponse>() {
            @Override
            public void onChanged(AddImageResponse addImageResponse) {
                if (addImageResponse != null){
                    if (addImageResponse.isSuccess()){
                        logoFileUrl = addImageResponse.getData().getFileUrl();
                        if (selectedCoverImgPath.length() != 0 && !SessionManager.readString(CreateConnectionActivity.this, SessionManager.BR_basicRegistratinUID, "").equals("")){
                            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), selectedCoverImgFile);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("Image", selectedCoverImgFile.getName(), requestFile);
                            RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), SessionManager.readString(CreateConnectionActivity.this, SessionManager.BR_basicRegistratinUID, ""));

                            createConnectionViewModel.uploadCoverPhoto(body, userId, CreateConnectionActivity.this);
                        }else {
                            if (getSelectedCard(CreateConnectionActivity.this) != null) {
                                CardData cardData = getSelectedCard(CreateConnectionActivity.this);
                                ParamCreateConnection paramCreateConnection = new ParamCreateConnection("", "", cardData.getParentUserID(), emailAddress, name, website, location,
                                        socialMedia, "+" + countryCode + mobileNo, jobTitle, notes, logoFileUrl, coverFileUrl, "", false, false, false, false, false, true, cardData.getPlan(), new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()), "");
                                Log.e("ParamCreateConnection", cardData.getUserID() + "\n" + cardData.getParentUserID() + "\n" + emailAddress + "\n" + company + "\n" + website + "\n" + location + "\n" +
                                        socialMedia + "\n" + "+" + countryCode + mobileNo + "\n" + jobTitle + "\n" + notes + "\n" + logoFileUrl + "\n" + coverFileUrl + "\n" + false + "\n" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()));
                                createConnectionViewModel.createConnection(paramCreateConnection, CreateConnectionActivity.this);
                            }
                        }
                    }
                }
            }
        });

        createConnectionViewModel.uploadCoverPhotoApi().observe(this, new Observer<AddImageResponse>() {
            @Override
            public void onChanged(AddImageResponse uploadCoverPhotoResponse) {
                if (uploadCoverPhotoResponse != null){
                    if (uploadCoverPhotoResponse.isSuccess()){
                        coverFileUrl = uploadCoverPhotoResponse.getData().getFileUrl();

                        if (!logoFileUrl.equals("") && !coverFileUrl.equals("")){
                            if (getSelectedCard(CreateConnectionActivity.this) != null) {
                                CardData cardData = getSelectedCard(CreateConnectionActivity.this);
                                ParamCreateConnection paramCreateConnection = new ParamCreateConnection("", "", cardData.getParentUserID(), emailAddress, name, website, location,
                                        socialMedia, "+"+countryCode+mobileNo, jobTitle, notes, logoFileUrl, coverFileUrl, "", false, false, false, false, false, true, cardData.getPlan(), new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()), "");
                                Log.e("ParamCreateConnection", cardData.getUserID() + "\n" + cardData.getParentUserID() + "\n" + emailAddress + "\n" + company+ "\n" + website+ "\n" + location+ "\n" +
                                        socialMedia+ "\n" + "+"+countryCode+mobileNo+ "\n" + jobTitle+ "\n" + notes+ "\n" + logoFileUrl+ "\n" + coverFileUrl+ "\n" + false+ "\n" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()));
                                createConnectionViewModel.createConnection(paramCreateConnection, CreateConnectionActivity.this);
                            }
                        }
                    }
                }
            }
        });

        createConnectionViewModel.onCreateConnection().observe(this, new Observer<InsertConnectionResponse>() {
            @Override
            public void onChanged(InsertConnectionResponse insertConnectionResponse) {
                if (insertConnectionResponse != null){
                    if (insertConnectionResponse.isSuccess()){
                        startActivity(new Intent(CreateConnectionActivity.this, DashboardActivity.class).putExtra("ISFromConnection", true).putExtra("NfcData", "").putExtra("IsFromSettings", false));
                        finish();
                    }else {
                        Toast.makeText(CreateConnectionActivity.this, insertConnectionResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void givePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // only for gingerbread and newer versions
            if (ContextCompat.checkSelfPermission(CreateConnectionActivity.this, Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(CreateConnectionActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_GRANTED) {
                selectimage();
            } else {
                ActivityCompat.requestPermissions(CreateConnectionActivity.this, new String[]{
                        Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 123);
            }
        } else {
            selectimage();
        }
    }

    public void selectimage() {

        final String[] items = {"take Photo", "Gallery", "Cancel"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(CreateConnectionActivity.this);

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
                        activityCreateConnectionBinding.imgProfile.setImageBitmap(bitmapImage);
                    }else {
                        activityCreateConnectionBinding.ivUploadCover.setImageBitmap(bitmapImage);
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
        Uri path = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmapImage, "Title_"+new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()), null));
        String[] projection = new String[]{"_data"};
        ContentResolver cr = getContentResolver();
        Cursor metaCursor = cr.query(path, projection, null, null, null);
        if (metaCursor != null) {
            try {
                if (metaCursor.moveToFirst()) {
                    if (isLogoImg) {
                        selectedLogoImgPath = metaCursor.getString(0);
                        selectedLogoImgFile = new File(selectedLogoImgPath);

                        SessionManager.writeString(CreateConnectionActivity.this, SessionManager.LOGO_IMAGE_PATH, selectedLogoImgPath);

                        Log.e("selectedLogoImgPath", selectedLogoImgPath);
                    }else {
                        selectedCoverImgPath = metaCursor.getString(0);
                        selectedCoverImgFile = new File(selectedCoverImgPath);

                        SessionManager.writeString(CreateConnectionActivity.this, SessionManager.COVER_IMAGE_PATH, selectedCoverImgPath);
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
                        .start(this);
            } catch (ActivityNotFoundException activityNotFound) {
                Log.e("activityNotFound", activityNotFound.getMessage());
                activityNotFound.fillInStackTrace();
            }
        }else {
            try {
                CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(16, 9)
                        .start(this);
            } catch (ActivityNotFoundException activityNotFound) {
                Log.e("activityNotFound", activityNotFound.getMessage());
                activityNotFound.fillInStackTrace();
            }
        }
    }

    @Override
    public void goToBack() {
        finish();
    }

    @Override
    public void uploadCoverImg() {
        isLogoImg = false;
        givePermission();
    }

    @Override
    public void uploadProfileImg() {
        isLogoImg = true;
        givePermission();
    }

    @Override
    public void onClickSocialMedia() {

    }

    @Override
    public void onCreateConnection() {
        countryCode = activityCreateConnectionBinding.countryCodePicker.getSelectedCountryCode();
        name = activityCreateConnectionBinding.etName.getText().toString();
        emailAddress = activityCreateConnectionBinding.etEmail.getText().toString();
        company = activityCreateConnectionBinding.etCompany.getText().toString();
        website = activityCreateConnectionBinding.etWebsite.getText().toString();
        location = activityCreateConnectionBinding.etLocation.getText().toString();
        socialMedia = activityCreateConnectionBinding.etSocialMedia.getText().toString();
        mobileNo = activityCreateConnectionBinding.etMobile.getText().toString();
        jobTitle = activityCreateConnectionBinding.etJobTitle.getText().toString();
        notes = activityCreateConnectionBinding.etNote.getText().toString();

        if (activityCreateConnectionBinding.etName.getText().toString().length() == 0){
            activityCreateConnectionBinding.etName.setError(getString(R.string.error_full_name));
            activityCreateConnectionBinding.etName.requestFocus();
        }/*else if (activityCreateConnectionBinding.etEmail.getText().toString().length() == 0){
            activityCreateConnectionBinding.etEmail.setError(getString(R.string.error_email));
            activityCreateConnectionBinding.etEmail.requestFocus();
        }else if (!CommonUtils.isEmailValid(emailAddress)){
            activityCreateConnectionBinding.etEmail.setError(getString(R.string.error_valid_email));
            activityCreateConnectionBinding.etEmail.requestFocus();
        }else if (activityCreateConnectionBinding.etCompany.getText().toString().length() == 0){
            activityCreateConnectionBinding.etCompany.setError(getString(R.string.error_comp_name));
            activityCreateConnectionBinding.etCompany.requestFocus();
        }else if (activityCreateConnectionBinding.etWebsite.getText().toString().length() == 0){
            activityCreateConnectionBinding.etWebsite.setError(getString(R.string.error_website));
            activityCreateConnectionBinding.etWebsite.requestFocus();
        }else if (activityCreateConnectionBinding.etLocation.getText().toString().length() == 0){
            activityCreateConnectionBinding.etLocation.setError(getString(R.string.error_location));
            activityCreateConnectionBinding.etLocation.requestFocus();
        }else if (activityCreateConnectionBinding.etSocialMedia.getText().toString().length() == 0){
            activityCreateConnectionBinding.etSocialMedia.setError(getString(R.string.error_social_media));
            activityCreateConnectionBinding.etSocialMedia.requestFocus();
        }else if (activityCreateConnectionBinding.etMobile.getText().toString().length() > 0 && activityCreateConnectionBinding.etMobile.getText().toString().length() != 10){
            activityCreateConnectionBinding.etMobile.setError(getString(R.string.error_enter_mobile));
            activityCreateConnectionBinding.etMobile.requestFocus();
        }else if (activityCreateConnectionBinding.etJobTitle.getText().toString().length() == 0){
            activityCreateConnectionBinding.etJobTitle.setError(getString(R.string.error_job_title));
            activityCreateConnectionBinding.etJobTitle.requestFocus();
        }else if (activityCreateConnectionBinding.etNote.getText().toString().length() == 0){
            activityCreateConnectionBinding.etNote.setError(getString(R.string.error_note));
            activityCreateConnectionBinding.etNote.requestFocus();
        }else if (selectedLogoImgPath.length() == 0){
            Toast.makeText(CreateConnectionActivity.this, "Please Upload Profile Image", Toast.LENGTH_SHORT).show();
        }else if (selectedCoverImgPath.length() == 0){
            Toast.makeText(CreateConnectionActivity.this, "Please Upload Cover Image", Toast.LENGTH_SHORT).show();
        }*/else {
            if (selectedLogoImgPath.length() != 0 && !SessionManager.readString(CreateConnectionActivity.this, SessionManager.BR_basicRegistratinUID, "").equals("")){
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), selectedLogoImgFile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("Image", selectedLogoImgFile.getName(), requestFile);
                RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), SessionManager.readString(CreateConnectionActivity.this, SessionManager.BR_basicRegistratinUID, ""));

                createConnectionViewModel.addLogo(body, userId, CreateConnectionActivity.this);
            }else if (selectedCoverImgPath.length() != 0 && !SessionManager.readString(CreateConnectionActivity.this, SessionManager.BR_basicRegistratinUID, "").equals("")){
                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), selectedCoverImgFile);
                MultipartBody.Part body = MultipartBody.Part.createFormData("Image", selectedCoverImgFile.getName(), requestFile);
                RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), SessionManager.readString(CreateConnectionActivity.this, SessionManager.BR_basicRegistratinUID, ""));
                createConnectionViewModel.uploadCoverPhoto(body, userId, CreateConnectionActivity.this);
            }else {
                if (getSelectedCard(CreateConnectionActivity.this) != null) {
                    CardData cardData = getSelectedCard(CreateConnectionActivity.this);
                    ParamCreateConnection paramCreateConnection = new ParamCreateConnection("", "", cardData.getParentUserID(), emailAddress, name, website, location,
                            socialMedia, "+"+countryCode+mobileNo, jobTitle, notes, logoFileUrl, coverFileUrl, "", false, false, false, false, false, true, cardData.getPlan(), new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()), "");
                    Log.e("ParamCreateConnection", cardData.getUserID() + "\n" + cardData.getParentUserID() + "\n" + emailAddress + "\n" + company+ "\n" + website+ "\n" + location+ "\n" +
                            socialMedia+ "\n" + "+"+countryCode+mobileNo+ "\n" + jobTitle+ "\n" + notes+ "\n" + logoFileUrl+ "\n" + coverFileUrl+ "\n" + false+ "\n" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date()));
                    createConnectionViewModel.createConnection(paramCreateConnection, CreateConnectionActivity.this);
                }
            }
        }
    }
}