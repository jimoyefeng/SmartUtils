package com.example.user.testkotlin.utis

import android.content.Context
import cn.yintech.cdam.data.local.CacheHelper
import cn.yintech.cdam.data.model.*
import cn.yintech.cdam.data.remote.AliOSSService
import cn.yintech.cdam.data.remote.CdamService
import cn.yintech.cdam.data.remote.NetPretreatmentHelper
import cn.yintech.cdam.data.remote.exception.ApiException
import cn.yintech.cdam.data.remote.exception.ExceptionEngine
import cn.yintech.cdam.data.remote.response.ApiCode
import cn.yintech.cdam.data.remote.response.ApiResponse
import cn.yintech.cdam.helper.PasswordEncryptHelper
import cn.yintech.cdam.helper.RxHelper
import cn.yintech.cdam.helper.enums.ParameterDef
import cn.yintech.cdam.helper.enums.SmsCodeType
import cn.yintech.cdam.utils.EncryptUtils
import com.orhanobut.logger.Logger
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.schedulers.Schedulers
import java.util.logging.Logger

/**
 *
 * Created by licheng on 2018/5/5.
 */
object NetApi {

    fun refreshToken(): Single<ApiResponse<StringModel?>> {
        return CdamService.api
                .refreshToken()
                .compose(applySingle())
    }

    // version
    fun checkVersion(): Flowable<ApiResponse<VersionModel?>> {
        return CdamService.api
                .checkVersion()
                .compose(apply())
    }

    // sms
    fun getVerificationCode(mobile: String, type: SmsCodeType): Flowable<ApiResponse<Boolean?>> {
        return CdamService.api
                .getVerificationCode(type.flowType, mobile)
                .compose(apply())
    }

    // auth
    fun loginWithSalt(mobile: String, password: String): Flowable<ApiResponse<UserModel?>> {
        return CdamService.api
                .getSalt()
                .flatMap { response ->
                    val salt = response.data?.result
                    if (salt == null || salt.isEmpty()) {
                        throw IllegalArgumentException("err: salt is empty!!")
                    }
                    val enPassword = PasswordEncryptHelper.getWhenLogin(password, salt)
                            ?: throw IllegalArgumentException("err: en password is empty!!")
                    val map = hashMapOf(
                            Pair("mobile", mobile),
                            Pair("password", enPassword))
                    return@flatMap CdamService.api.login(map)
                }
                .compose(apply())
    }

    fun register(mobile: String, password: String, smsCode: String): Flowable<ApiResponse<TokenModel?>> {
        return CdamService.api
                .getSalt()
                .flatMap { response ->
                    val salt = response.data?.result
                    if (salt == null || salt.isEmpty()) {
                        throw IllegalArgumentException("err: salt is empty!!")
                    }
                    val enPassword = PasswordEncryptHelper.getWhenSet(password, salt)
                            ?: throw IllegalArgumentException("err: en password is empty!!")
                    val map = hashMapOf(
                            Pair("mobile", mobile),
                            Pair("password", enPassword),
                            Pair("verifyCode", smsCode))
                    return@flatMap CdamService.api.register(map)
                }
                .compose(apply())
    }

    fun recoverPassword(mobile: String, password: String, smsCode: String): Flowable<ApiResponse<String?>> {
        return CdamService.api
                .getSalt()
                .flatMap { response ->
                    val salt = response.data?.result
                    if (salt == null || salt.isEmpty()) {
                        throw IllegalArgumentException("err: salt is empty!!")
                    }
                    val enPassword = PasswordEncryptHelper.getWhenSet(password, salt)
                            ?: throw IllegalArgumentException("err: en password is empty!!")
                    val map = hashMapOf(
                            Pair("mobile", mobile),
                            Pair("newPassword", enPassword),
                            Pair("verifyCode", smsCode))
                    return@flatMap CdamService.api.recoverPassword(map)
                }
                .compose(apply())
    }

    // cert
    fun certStatus(): Flowable<ApiResponse<StringModel?>> {
        return CdamService.api
                .certStatus()
                .compose(apply())
    }

    fun certInvestorConfirm(): Flowable<ApiResponse<String?>> {
        return CdamService.api
                .certInvestorConfirm()
                .compose(apply())
    }

    fun certIDCard(name: String, idcard: String): Flowable<ApiResponse<String?>> {
        val body = hashMapOf(Pair("name", name),
                Pair("idcard", idcard))
        return CdamService.api
                .certIDCard(body)
                .compose(apply())
    }

    fun getIDCard(): Flowable<ApiResponse<IDCardModel?>> {
        return CdamService.api
                .getIDCard()
                .compose(apply())
    }

    fun certCollectInfo(body: Map<String, String>): Flowable<ApiResponse<String?>> {
        return CdamService.api
                .certCollectInfo(body)
                .compose(apply())
    }

    fun certRiskAssessment(body: Map<String, String>): Flowable<ApiResponse<String?>> {
        return CdamService.api
                .certRiskAssessment(body)
                .compose(apply())
    }

    //fund
    fun getFundsWrapInfo(cursor: String, pageSize: Int): Flowable<ApiResponse<FundsWrapModel?>> {
        return CdamService.api
                .getFundsWrapInfo(cursor, pageSize)
                .compose(apply())
    }

    fun getFundDetailVoucher(fundId: Long): Flowable<ApiResponse<FundDetailVoucherModel?>> {
        return CdamService.api
                .getFundDetailVoucher(fundId)
                .compose(apply())
    }

    fun getFundDetail(fundId: Long, voucherNo: String): Flowable<ApiResponse<FundDetailModel?>> {
        return CdamService.api
                .getFundDetail(fundId, voucherNo)
                .compose(apply())
    }

    fun getFundAccessory(fundId: Long): Flowable<ApiResponse<FundAccessoryGroupsWrapModel?>> {
        return CdamService.api
                .getFundAccessory(fundId)
                .compose(apply())
    }

    fun getFundAnnouncement(fundId: Long, cursor: String?, pageSize: Int = 20): Flowable<ApiResponse<FundAnnouncementWrapModel?>> {
        return CdamService.api
                .getFundAnnouncement(fundId, cursor, pageSize)
                .compose(apply())
    }

    fun getOrderCreationPreInfo(fundId: Long, @ParameterDef.OrderType orderType: String): Flowable<ApiResponse<OrderCreationPreModel?>> {
        return CdamService.api
                .getOrderCreationPreInfo(fundId, orderType)
                .compose(apply())
    }

    fun getOrderProcess(orderId: Long): Flowable<ApiResponse<OrderProcessModel?>> {
        return CdamService.api
                .getOrderProcess(orderId)
                .compose(apply())
    }

    fun getFundFee(fundId: Long, amount: String): Flowable<ApiResponse<StringModel?>> {
        val map = mapOf(Pair("amount", amount))
        return CdamService.api
                .getFundFee(fundId, map)
                .compose(apply())
    }

    fun submitPurchaseAmount(@ParameterDef.PurchaseAction action: Long, fundtype:String,fundId: Long, orderId: Long,
                             amount: String, share: String = ""): Flowable<ApiResponse<String?>> {

        val feeRequest = mapOf(Pair("amount", amount),Pair("orderType", fundtype))
        return CdamService.api
                .getFundFee(fundId, feeRequest)
                .flatMap { res ->
                    val fee = res.data!!.result
                    val amountRequest = mapOf(Pair("amount", amount), Pair("fee", fee), Pair("share", share))
                    return@flatMap when (action) {
                        ParameterDef.ACTION_CREATE -> {
                            CdamService.api.createPurchaseAmount(orderId, amountRequest)
                        }
                        ParameterDef.ACTION_UPDATE -> {
                            CdamService.api.updatePurchaseAmount(orderId, amountRequest)
                        }
                        else -> throw IllegalArgumentException("unknown action -> $action")
                    }
                }
                .compose(apply())
    }

    fun submitPurchaseShare(@ParameterDef.PurchaseAction action: Long, fundId: Long, orderId: Long,
                            amount: String, share: String): Flowable<ApiResponse<String?>> {
                    val amountRequest = mapOf(Pair("amount", amount), Pair("fee", ""), Pair("share", share))
                    return when (action) {
                        ParameterDef.ACTION_CREATE -> {
                            CdamService.api.createPurchaseAmount(orderId, amountRequest)
                        }
                        ParameterDef.ACTION_UPDATE -> {
                            CdamService.api.updatePurchaseAmount(orderId, amountRequest)
                        }
                        else -> throw IllegalArgumentException("unknown action -> $action")
                    }

                .compose(apply())
    }

    fun bindBankCard(@ParameterDef.PurchaseAction bindType: Long,
                     orderId: Long, bank: String, bankCardNo: String): Flowable<ApiResponse<String?>> {
        val map = mapOf(Pair("bank", bank),
                Pair("bankCardNo", bankCardNo))
        return when (bindType) {
            ParameterDef.ACTION_CREATE -> {
                CdamService.api.addBankCard(orderId, map).compose(apply())
            }
            ParameterDef.ACTION_UPDATE -> {
                CdamService.api.updateBankCard(orderId, map).compose(apply())
            }
            else -> throw IllegalArgumentException("unknown card bind type -> $bindType")
        }
    }

    fun confirmInvestorStatement(orderId: Long): Flowable<ApiResponse<String?>> {
        return CdamService.api
                .confirmInvestorStatement(orderId)
                .compose(apply())
    }

    // contract
    fun getContacts(fundId: Long, types: Array<String>): Flowable<ApiResponse<FundContractWrapModel?>> {
        val map = mapOf(Pair("types", types))
        return CdamService.api
                .getContacts(fundId, map)
                .compose(apply())
    }

    fun getSingleContact(fundId: Long, @ParameterDef.ContractType type: String): Flowable<ApiResponse<String?>> {
        val map = mapOf(Pair("types", arrayOf(type)))
        return CdamService.api
                .getContacts(fundId, map)
                .flatMap { res ->
                    val fileOssKey = res.data!!.items[0].fileOssKey
                    return@flatMap CdamService.api
                            .getAliOSSSTSForRead()
                            .flatMap { res2 ->
                                AliOSSService.getUrl(res2.data!!, fileOssKey)
                            }
                }
                .compose(apply())
    }


    //video
    fun getVideoList(videoType: String, pageSize: Int = 20, cursor: String?): Flowable<ApiResponse<VideoListModel?>> {
        return CdamService.api
                .getVideoList(videoType, pageSize, cursor)
                .compose(apply())
    }

    fun getVideo(videoType: String, id: Long, voucherNo: String?): Flowable<ApiResponse<VideoModel?>> {
        return CdamService.api
                .getVideo(videoType, id, voucherNo)
                .compose(apply())
    }

    fun detailVoucher(videoId: Long): Flowable<ApiResponse<VideoDetailVoucherModel?>> {
        return CdamService.api
                .getVideoDetailVoucher(videoId)
                .compose(apply())
    }

    //mine

    fun getReadAll(): Flowable<ApiResponse<Int?>> {

        return CdamService.api
                .getReadAll()
                .compose(apply())
    }


    fun getUnreadCount(): Flowable<ApiResponse<MessagesUnreadModel?>> {

        return CdamService.api
                .getUnreadCount()
                .compose(apply())
    }


    fun getMessageList(pageSize: Int, cursor: String?): Flowable<ApiResponse<MessageListModel?>> {

        return CdamService.api
                .getMessageList(pageSize, cursor)
                .compose(apply())
    }


    fun getAuthStatus(): Flowable<ApiResponse<MineSettingAuthStatusModel?>> {
        return CdamService.api
                .getAuthState()
                .compose(apply())
    }

    fun getRiskAssessment(): Flowable<ApiResponse<MineSettingRiskAssessmentModel?>> {
        return CdamService.api
                .getRiskAssessment()
                .compose(apply())
    }

    fun submitNewPassword(oldPassword: String, newPassword: String): Flowable<ApiResponse<String?>> {
        return CdamService.api
                .getSalt()
                .flatMap { response ->
                    val salt = response.data?.result
                    if (salt == null || salt.isEmpty()) {
                        throw IllegalArgumentException("err: salt is empty!!")
                    }
                    val oldPsd = PasswordEncryptHelper.getWhenLogin(oldPassword, salt)
                            ?: throw IllegalArgumentException("err: en password is empty!!")
                    val newPsd = PasswordEncryptHelper.getWhenSet(newPassword, salt)
                            ?: throw IllegalArgumentException("err: en password is empty!!")
                    val map = hashMapOf(
                            Pair("newPassword", newPsd),
                            Pair("oldPassword", oldPsd))
                    return@flatMap CdamService.api.submitNewPassword(map)
                }
                .compose(apply())
    }

    fun getUserPositonList(): Flowable<ApiResponse<UserPositionListModel?>> {
        return CdamService.api
                .getUserPositonList()
                .compose(apply())
    }

    fun getUserPositons(fundId: Long): Flowable<ApiResponse<UserPositionsModel?>> {
        return CdamService.api
                .getUserPositions(fundId)
                .compose(apply())
    }

    fun getUserPositon(): Flowable<ApiResponse<UserOrdersListModel?>> {
        return CdamService.api
                .getUserOrdersList()
                .compose(apply())
    }

    fun uploadCertsAndIDCards(context: Context, @ParameterDef.PurchaseAction uploadAction: Long, orderId: Long,
                              certs: List<ByteArray>, delCertIds: List<Long>,
                              idCardPos: ByteArray, idCardNeg: ByteArray, isIDCardUpdated: Boolean): Single<ApiResponse<String?>> {
        return CdamService.api
                .getAliOSSSTSForPurchaseWrite(orderId, certs.size, 0, isIDCardUpdated, isIDCardUpdated)
                .flatMap { response ->
                    val stsWriteModel = response.data!!
                    val stsModel = AliOSSSTSModel(stsWriteModel.credentials, stsWriteModel.bucket, null,
                            stsWriteModel.region, stsWriteModel.endpoint)
                    val files = mutableListOf<AliOSSFileWrapModel>()
                    certs.forEachIndexed { index, bytes ->
                        files.add(AliOSSFileWrapModel(bytes, stsWriteModel.assetCertKey[index],
                                ParameterDef.UPLOAD_TYPE_CERT, stsModel))
                    }
                    if (isIDCardUpdated) {
                        files.add(AliOSSFileWrapModel(idCardPos, stsWriteModel.cardPosKey,
                                ParameterDef.UPLOAD_TYPE_ID_CARD_POSITIVE, stsModel))
                        files.add(AliOSSFileWrapModel(idCardNeg, stsWriteModel.cardNegKey,
                                ParameterDef.UPLOAD_TYPE_ID_CARD_NEGATIVE, stsModel))
                    }

                    return@flatMap Flowable.fromIterable(files)
                }
                .flatMap { fileWrap -> AliOSSService.uploadFile(context, fileWrap) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .toList()
                .flatMap { resList ->
                    val createModel = AssetProofCreatedModel.parseFromUploadResponse(resList)
                    val updateModel = AssetProofUpdatedModel.parseFromUploadResponse(resList, delCertIds.toMutableList())
                    return@flatMap when (uploadAction) {
                        ParameterDef.ACTION_CREATE -> {
                            CdamService.api.notifyCertsAndIDCardCreated(orderId, createModel)
                        }
                        ParameterDef.ACTION_UPDATE -> {
                            CdamService.api.notifyCertsAndIDCardUpdated(orderId, updateModel)
                        }
                        else -> throw IllegalArgumentException("unknown upload action -> $uploadAction")
                    }
                }
                .compose(applySingle())
    }

    fun uploadTransferCerts(context: Context, @ParameterDef.PurchaseAction uploadAction: Long, orderId: Long,
                            certs: List<ByteArray>, delCertIds: List<Long>): Single<ApiResponse<String?>> {
        return CdamService.api
                .getAliOSSSTSForPurchaseWrite(orderId, 0, certs.size, false, false)
                .flatMap { response ->
                    val stsWriteModel = response.data!!
                    val stsModel = AliOSSSTSModel(stsWriteModel.credentials, stsWriteModel.bucket, null,
                            stsWriteModel.region, stsWriteModel.endpoint)
                    val files = mutableListOf<AliOSSFileWrapModel>()
                    certs.forEachIndexed { index, bytes ->
                        files.add(AliOSSFileWrapModel(bytes, stsWriteModel.voucherKey[index],
                                ParameterDef.UPLOAD_TYPE_PAYMENT_VOUCHER, stsModel))
                    }
                    return@flatMap Flowable.fromIterable(files)
                }
                .flatMap { fileWrap -> AliOSSService.uploadFile(context, fileWrap) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .toList()
                .flatMap { resList ->
                    val createModel = TransferCertCreatedModel.parseFromUploadResponse(resList)
                    val updateModel = TransferCertUpdatedModel.parseFromUploadResponse(resList, delCertIds.toMutableList())
                    return@flatMap when (uploadAction) {
                        ParameterDef.ACTION_CREATE -> {
                            CdamService.api.notifyPaymentVoucherCreated(orderId, createModel)
                        }
                        ParameterDef.ACTION_UPDATE -> {
                            CdamService.api.notifyPaymentVoucherUpdated(orderId, updateModel)
                        }
                        else -> throw IllegalArgumentException("unknown upload action -> $uploadAction")
                    }
                }
                .compose(applySingle())
    }

    fun getAliOSSSTSForRead(): Flowable<ApiResponse<AliOSSSTSModel?>> {
        return CdamService.api
                .getAliOSSSTSForRead()
                .compose(apply())
    }

    fun getAliOSSFileUrlForRead(fileOssKey:String):Flowable<ApiResponse<String?>> {
        return CdamService.api
                .getAliOSSSTSForRead()
                .flatMap { res->
                    return@flatMap AliOSSService.getUrl(res.data!!, fileOssKey)
                }
                .compose(apply())
    }

    fun downloadFileToDisk(url:String):Single<String> {
        return CdamService.fileApi
                .downloadFile(url)
                .flatMap { res->
                    val md5 = EncryptUtils.encryptMD5ToString(url)?:"default"
                    Logger.d("type->${res.contentType()}")
                    CacheHelper.putPDF(md5.toLowerCase(), res.bytes())
                    return@flatMap Single.just(md5)
                }
                .compose(RxHelper.applyIOSchedulerToSingle())
    }

    private fun <T> apply(): FlowableTransformer<ApiResponse<T?>, ApiResponse<T?>> {
        return FlowableTransformer { flowable ->
            flowable.map({ response ->
                if (ApiCode.SUCCESS.code != response.code) {
                    // 业务异常
                    throw ApiException(response)
                }
                response
            })
                    .doOnNext { res ->
                        // 成功则判断是否刷新Token
                        NetPretreatmentHelper.handleFreshToken(res.responseTime)
                    }
                    .onErrorResumeNext { t: Throwable ->
                        // 非业务异常
                        Flowable.error(ExceptionEngine.handleException(t))
                    }
                    .compose(RxHelper.applyIOScheduler())
        }
    }

    private fun <T> applySingle(): SingleTransformer<ApiResponse<T?>, ApiResponse<T?>> {
        return SingleTransformer { single ->
            single.map({ response ->
                if (ApiCode.SUCCESS.code != response.code) {
                    // 业务异常
                    throw ApiException(response)
                }
                return@map response
            })
                    .doOnSuccess { res ->
                        // 成功则判断是否刷新Token
                        if (res?.responseTime != null) {
                            NetPretreatmentHelper.handleFreshToken(res.responseTime)
                        }
                    }
                    .onErrorResumeNext { t: Throwable ->
                        // 非业务异常
                        Single.error(ExceptionEngine.handleException(t))
                    }
                    .compose(RxHelper.applyIOSchedulerToSingle())
        }
    }
}