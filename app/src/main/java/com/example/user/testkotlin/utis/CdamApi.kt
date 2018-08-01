package cn.yintech.cdam.data.remote

import cn.yintech.cdam.data.model.*
import cn.yintech.cdam.data.remote.response.ApiResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.*

/**
 *
 * Created by licheng on 2018/5/5.
 */
interface CdamApi {

    @GET("v1/salt")
    fun getSalt(): Flowable<ApiResponse<StringModel?>>

    @GET("v1/token")
    fun refreshToken(): Single<ApiResponse<StringModel?>>

    // version
    @GET("v1/version/check")
    fun checkVersion(): Flowable<ApiResponse<VersionModel?>>

    // sms
    @GET("v1/sms/send/{flowType}")
    fun getVerificationCode(@Path("flowType") flowType: String,
                            @Query("mobile") mobile: String
    ): Flowable<ApiResponse<Boolean?>>

    // auth
    @PUT("auth/update/password")
    fun modifyPassword(@Body modification: Map<String, String>
    ): Flowable<ApiResponse<Boolean?>>

    @POST("auth/login")
    fun login(@Body loginRequest: Map<String, String>
    ): Flowable<ApiResponse<UserModel?>>

    @POST("v1/user/reg")
    fun register(@Body body: Map<String, String>
    ): Flowable<ApiResponse<TokenModel?>>

    @POST("v1/user/password/forget")
    fun recoverPassword(@Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    // cert
    @POST("v1/auth/accreditedInvestor")
    fun certInvestorConfirm(): Flowable<ApiResponse<String?>>

    @GET("v1/auth/status")
    fun certStatus(): Flowable<ApiResponse<StringModel?>>

    @POST("v1/auth/IDCard")
    fun certIDCard(@Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    @GET("v1/auth/IDCard")
    fun getIDCard(): Flowable<ApiResponse<IDCardModel?>>

    @POST("v1/auth/extroInfo")
    fun certCollectInfo(@Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    /**
     * 提交评测结果
     */
    @POST("v1/auth/riskAssessment")
    fun certRiskAssessment(@Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    // fund
    @GET("v1/fund")
    fun getFundsWrapInfo(@Query("cursor") cursor: String,
                         @Query("pageSize") pageSize: Int
    ): Flowable<ApiResponse<FundsWrapModel?>>

    @GET("v1/fund/{fundId}/detailVoucher")
    fun getFundDetailVoucher(@Path("fundId") fundId: Long
    ): Flowable<ApiResponse<FundDetailVoucherModel?>>

    @GET("v1/fund/{id}")
    fun getFundDetail(@Path("id") fundId: Long,
                      @Query("voucherNo") voucherNo: String
    ): Flowable<ApiResponse<FundDetailModel?>>

    @GET("v1/fund/{id}/attachment")
    fun getFundAccessory(@Path("id") fundId: Long
    ): Flowable<ApiResponse<FundAccessoryGroupsWrapModel?>>

    @GET("v1/fund/{id}/announcement")
    fun getFundAnnouncement(@Path("id") fundId: Long,
                            @Query("cursor") cursor: String?,
                            @Query("pageSize") pageSize: Int
    ): Flowable<ApiResponse<FundAnnouncementWrapModel?>>

    @GET("v1/video/{videoType}")
    fun getVideoList(@Path("videoType") videoType: String,
                     @Query("pageSize") pageSize: Int,
                     @Query("cursor") cursor: String?
    ): Flowable<ApiResponse<VideoListModel?>>

    @GET("v1/video/{videoType}/{id}")
    fun getVideo(@Path("videoType") videoType: String,
                 @Path("id") id: Long,
                 @Query("voucherNo") voucherNo: String?
    ): Flowable<ApiResponse<VideoModel?>>

    @GET("v1/video/{videoId}/detailVoucher")
    fun getVideoDetailVoucher(@Path("videoId") videoId: Long
    ): Flowable<ApiResponse<VideoDetailVoucherModel?>>

    //mine
    @GET("v1/auth/status")
    fun getAuthState(): Flowable<ApiResponse<MineSettingAuthStatusModel?>>

    @GET("v1/auth/riskAssessment")
    fun getRiskAssessment(): Flowable<ApiResponse<MineSettingRiskAssessmentModel?>>

    @POST("v1/user/password/modify")
    fun submitNewPassword(@Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    @GET("v1/my/order")
    fun getUserOrdersList(): Flowable<ApiResponse<UserOrdersListModel?>>

    @GET("v1/my/position ")
    fun getUserPositonList(): Flowable<ApiResponse<UserPositionListModel?>>

    @GET("v1/my/position/{fundId}")
    fun getUserPositions(@Path("fundId") fundId: Long): Flowable<ApiResponse<UserPositionsModel?>>

    @GET("v1/my/message")
    fun getMessageList(@Query("pageSize") pageSize: Int,
                       @Query("cursor") cursor: String?
    ): Flowable<ApiResponse<MessageListModel?>>

    @GET("v1/my/message/unreadCount")
    fun getUnreadCount(): Flowable<ApiResponse<MessagesUnreadModel?>>

    @PUT("v1/my/message/readAll")
    fun getReadAll(): Flowable<ApiResponse<Int?>>


    // purchase order
    @POST("v1/order/{fundId}/{orderType}")
    fun getOrderCreationPreInfo(@Path("fundId") fundId: Long,
                                @Path("orderType") orderType: String
    ): Flowable<ApiResponse<OrderCreationPreModel?>>

    @GET("v1/order/{orderId}/process")
    fun getOrderProcess(@Path("orderId") orderId: Long
    ): Flowable<ApiResponse<OrderProcessModel>>

    @POST("v1/fund/{fundId}/fee")
    fun getFundFee(@Path("fundId") fundId: Long,
                   @Body body: Map<String, String>
    ): Flowable<ApiResponse<StringModel?>>

    @POST("v1/order/{orderId}/bankCard")
    fun addBankCard(@Path("orderId") orderId: Long,
                    @Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    @PUT("v1/order/{orderId}/bankCard")
    fun updateBankCard(@Path("orderId") orderId: Long,
                       @Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    @POST("v1/order/{orderId}/investorConfirm")
    fun confirmInvestorStatement(@Path("orderId") orderId: Long
    ): Flowable<ApiResponse<String?>>

    @POST("/v1/order/{orderId}/amount")
    fun createPurchaseAmount(@Path("orderId") orderId: Long,
                             @Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    @PUT("/v1/order/{orderId}/amount")
    fun updatePurchaseAmount(@Path("orderId") orderId: Long,
                             @Body body: Map<String, String>
    ): Flowable<ApiResponse<String?>>

    // avatar
    @PUT("users/v1/avatar")
    fun notifyAvatarUpdate(): Single<ApiResponse<Boolean?>>

    @GET("v1/aliyun/sts/common")
    fun getAliOSSSTSForRead(): Flowable<ApiResponse<AliOSSSTSModel?>>

    @GET("v1/aliyun/sts/order")
    fun getAliOSSSTSForPurchaseWrite(@Query("orderId") orderId: Long,
                                     @Query("assetCertCount") assetCertCount: Int,
                                     @Query("voucherCount") voucherCount: Int,
                                     @Query("cardPos") cardPos: Boolean,
                                     @Query("cardNeg") cardNeg: Boolean
    ): Flowable<ApiResponse<AliOSSWriteOrderModel?>>

    @POST("v1/order/{orderId}/certsAndIDCard")
    fun notifyCertsAndIDCardCreated(@Path("orderId") orderId: Long,
                                    @Body body: AssetProofCreatedModel
    ): Single<ApiResponse<String?>>

    @PUT("v1/order/{orderId}/certsAndIDCard")
    fun notifyCertsAndIDCardUpdated(@Path("orderId") orderId: Long,
                                    @Body body: AssetProofUpdatedModel
    ): Single<ApiResponse<String?>>

    @POST("/v1/order/{orderId}/paymentVoucher")
    fun notifyPaymentVoucherCreated(@Path("orderId") orderId: Long,
                                    @Body body: TransferCertCreatedModel
    ): Single<ApiResponse<String?>>

    @PUT("/v1/order/{orderId}/paymentVoucher")
    fun notifyPaymentVoucherUpdated(@Path("orderId") orderId: Long,
                                    @Body body: TransferCertUpdatedModel
    ): Single<ApiResponse<String?>>

    // contact
    @POST("v1/fund/{fundId}/contract")
    fun getContacts(@Path("fundId") fundId: Long,
                    @Body body: Map<String, Array<String>>
    ): Flowable<ApiResponse<FundContractWrapModel>>

}