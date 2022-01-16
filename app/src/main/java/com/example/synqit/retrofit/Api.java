package com.example.synqit.retrofit;

import com.example.synqit.fragments.businessfragment2.model.BusinessResponse;
import com.example.synqit.fragments.businessfragment4.model.AddLogoResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamAddLogo;
import com.example.synqit.fragments.businessfragment4.model.ParamUploadCoverPhoto;
import com.example.synqit.fragments.businessfragment4.model.UploadCoverPhotoResponse;
import com.example.synqit.ui.login.model.LoginResponse;
import com.example.synqit.ui.login.model.ParamLogin;
import com.example.synqit.ui.otpverification.model.GetOtpResponse;
import com.example.synqit.ui.otpverification.model.ParamGetOtp;
import com.example.synqit.ui.otpverification.model.ParamVerifyOtp;
import com.example.synqit.ui.otpverification.model.VerifyOtpResponse;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.ui.proupgrade.model.ParamFullRegister;
import com.example.synqit.ui.register.model.BasicRegisterResponse;
import com.example.synqit.ui.register.model.ParamBasicRegister;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    @POST("Register/Register")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<BasicRegisterResponse> basicRegistration(@Body ParamBasicRegister params);

    @GET("BusinessTypes/GetBusinessTypes")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<BusinessResponse> getBusinesses();

    @POST("Register/GetOTP")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<GetOtpResponse> getOtp(@Body ParamGetOtp params);

    @POST("Register/VerifyOTP")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<VerifyOtpResponse> verifyOtp(@Body ParamVerifyOtp params);

    @Multipart
    @POST("Register/UploadCoverImage")
    Call<UploadCoverPhotoResponse> uploadCoverPhoto(@Part MultipartBody.Part Cover, @Part("UserUID") RequestBody UserUID);

    @Multipart
    @POST("Register/UploadProfileImage")
    Call<AddLogoResponse> uploadProfileImage(@Part MultipartBody.Part Profile, @Part("UserUID") RequestBody UserUID);

    @POST("Register/RegisterUser")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<FullRegisterResponse> fullRegistration(@Body ParamFullRegister params);

    @POST("Login")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<LoginResponse> loginUser(@Body ParamLogin params);
}
