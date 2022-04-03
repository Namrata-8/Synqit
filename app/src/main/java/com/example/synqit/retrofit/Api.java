package com.example.synqit.retrofit;

import com.example.synqit.fragments.businessfragment2.model.BusinessResponse;
import com.example.synqit.fragments.businessfragment4.model.AddImageResponse;
import com.example.synqit.fragments.businessfragment4.model.InsertCardResponse;
import com.example.synqit.fragments.businessfragment4.model.ParamInsertCard;
import com.example.synqit.fragments.connectionsfragment.model.ConnectionResponse;
import com.example.synqit.fragments.connectionsfragment.model.ParamUpdateUserConnect;
import com.example.synqit.fragments.connectionsfragment.model.UpdateConnectResponse;
import com.example.synqit.fragments.homefragment.model.ParamGetSubscribedList;
import com.example.synqit.fragments.homefragment.model.ParamUpdateSocialMedia;
import com.example.synqit.fragments.homefragment.model.SubScribeListResponse;
import com.example.synqit.fragments.insightfragment.model.GetProfileVisitResponse;
import com.example.synqit.fragments.insightfragment.model.ParamGetProfileVisit;
import com.example.synqit.ui.activateproduct.model.ActivateProductResponse;
import com.example.synqit.ui.addlink.model.AddLinkResponse;
import com.example.synqit.ui.addlink.model.AddSocialMediaResponse;
import com.example.synqit.ui.addlink.model.ParamAddSocialMedia;
import com.example.synqit.ui.createconnection.model.InsertConnectionResponse;
import com.example.synqit.ui.createconnection.model.ParamCreateConnection;
import com.example.synqit.fragments.connectionsfragment.model.ParamGetConnection;
import com.example.synqit.ui.dashboard.model.CardResponse;
import com.example.synqit.ui.dashboard.model.ParamGetCard;
import com.example.synqit.ui.forgotpass.model.ForgotPassUserResponse;
import com.example.synqit.ui.forgotpass.model.ParamGetUserForgotPass;
import com.example.synqit.ui.howtouse.model.InstructionResponse;
import com.example.synqit.ui.login.model.LoginResponse;
import com.example.synqit.ui.login.model.ParamLogin;
import com.example.synqit.ui.login.model.ParamSocialLogin;
import com.example.synqit.ui.login.model.SocialLoginResponse;
import com.example.synqit.ui.otpverification.model.GetOtpResponse;
import com.example.synqit.ui.otpverification.model.ParamGetOtp;
import com.example.synqit.ui.otpverification.model.ParamVerifyOtp;
import com.example.synqit.ui.otpverification.model.VerifyOtpResponse;
import com.example.synqit.ui.proupgrade.model.FullRegisterResponse;
import com.example.synqit.ui.proupgrade.model.ParamFullRegister;
import com.example.synqit.ui.proupgrade.model.PlansResponse;
import com.example.synqit.ui.register.model.BasicRegisterResponse;
import com.example.synqit.ui.register.model.ParamBasicRegister;
import com.example.synqit.ui.resetpass.ResetPasswordViewModel;
import com.example.synqit.ui.resetpass.model.ParamResetPass;
import com.example.synqit.ui.resetpass.model.ResetPassResponse;
import com.example.synqit.ui.support.model.CreateTicketResponse;
import com.example.synqit.ui.support.model.FaqListResponse;
import com.example.synqit.ui.support.model.ParamCreateTicket;
import com.example.synqit.ui.support.model.ParamSupportList;
import com.example.synqit.ui.support.model.SupportListResponse;

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

    @POST("Login/getUserForgotPassword")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<ForgotPassUserResponse> getForgotUserPassword(@Body ParamGetUserForgotPass params);

    @POST("Login/ForgotPassword")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<ResetPassResponse> getResetPassword(@Body ParamResetPass params);

    @POST("Settings/CreateNewTicket")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<CreateTicketResponse> createNewTicket(@Body ParamCreateTicket params);

    @GET("BusinessTypes/GetBusinessTypes")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<BusinessResponse> getBusinesses();

    @POST("Cards/GetCards")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<CardResponse> getCard(@Body ParamGetCard params);

    @POST("Connect/GetBlockedList")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<ConnectionResponse> getBlockedConnection(@Body ParamGetConnection params);

    @POST("Connect/GetConnectionList")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<ConnectionResponse> getConnection(@Body ParamGetConnection params);

    @GET("Plans/GetPlanList")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<PlansResponse> getPlans();

    @POST("Register/GetOTP")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<GetOtpResponse> getOtp(@Body ParamGetOtp params);

    @POST("Register/VerifyOTP")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<VerifyOtpResponse> verifyOtp(@Body ParamVerifyOtp params);

    @Multipart
    @POST("Images/Upload")
    Call<AddImageResponse> uploadCoverPhoto(@Part MultipartBody.Part Cover, @Part("UserUID") RequestBody UserUID);

    @Multipart
    @POST("Images/Upload")
    Call<AddImageResponse> uploadImage(@Part MultipartBody.Part Profile, @Part("UserUID") RequestBody UserUID);

    @POST("Register/RegisterUser")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<FullRegisterResponse> fullRegistration(@Body ParamFullRegister params);

    @POST("Cards/InsertCard")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<InsertCardResponse> insertCard(@Body ParamInsertCard params);

    @POST("Cards/UpdateCard")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<InsertCardResponse> updateCard(@Body ParamInsertCard params);

    @POST("Taps/GetProfileVisit")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<GetProfileVisitResponse> profileVisit(@Body ParamGetProfileVisit params);

    @POST("SocialMedia/UpdateSocialMedia")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<SubScribeListResponse> updateSocialMedia(@Body ParamUpdateSocialMedia params);

    @POST("Login")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<LoginResponse> loginUser(@Body ParamLogin params);

    @POST("Register/RegisterLoginUserGoogle")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<SocialLoginResponse> googleLoginUser(@Body ParamSocialLogin params);

    @POST("Register/RegisterLoginUserFacebook")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<SocialLoginResponse> facebookLoginUser(@Body ParamSocialLogin params);

    @GET("Category/GetLinkStore")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<AddLinkResponse> getLinks();

    @POST("Settings/GetProductList")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<ActivateProductResponse> getActivateProducts();

    @POST("Settings/GetBasicInstructionList")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<InstructionResponse> getInstructions();

    @POST("Settings/GetFAQList")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<FaqListResponse> getFaqsList();

    @POST("Connect/InsertManualConnection")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<InsertConnectionResponse> createConnection(@Body ParamCreateConnection params);

    @POST("Connect/InsertUpdateUserConnect")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<UpdateConnectResponse> updateUserConnect(@Body ParamUpdateUserConnect params);

    @POST("Connect/UpdateManualConnection")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<InsertConnectionResponse> updateManualConnection(@Body ParamCreateConnection params);

    @POST("SocialMedia/SubscribeSocialMedia")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<AddSocialMediaResponse> subscribeSocialMedia(@Body ParamAddSocialMedia params);

    @POST("SocialMedia/GetSubscribedList")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<SubScribeListResponse> getSubscribedList(@Body ParamGetSubscribedList params);

    @POST("Settings/GetSupportList")
    @Headers({"Accept:application/json", "Content-Type:application/json"})
    Call<SupportListResponse> getSupportList(@Body ParamSupportList params);
}
