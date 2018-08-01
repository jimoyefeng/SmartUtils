package com.example.user.testkotlin.utis

import android.os.Bundle
import android.text.SpannableString
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import cn.yintech.cdam.Configs
import cn.yintech.cdam.R
import cn.yintech.cdam.base.BaseFragment
import cn.yintech.cdam.data.NetApi
import cn.yintech.cdam.helper.FragmentID
import cn.yintech.cdam.helper.RxHelper
import cn.yintech.cdam.helper.SmsCodeHelper
import cn.yintech.cdam.helper.TextViewHelper
import cn.yintech.cdam.helper.dialog.DialogHelper
import cn.yintech.cdam.helper.enums.SmsCodeType
import cn.yintech.cdam.utils.ToastUtils
import com.example.user.testkotlin.R
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.view_auth_registration.*
import kotlinx.android.synthetic.main.view_auth_sms_code.*

/**
 *
 * Created by licheng on 2018/5/7.
 */
class PasswordRecoverFragment : BaseFragment() {
    override fun getFragmentID(): FragmentID {
        return FragmentID.PASSWORD_RECOVERY
    }

    companion object {
        val TAG = this::class.java.simpleName!!
    }

    private val disposables = CompositeDisposable()
    private lateinit var smsCodeHelper: SmsCodeHelper

    private lateinit var etMobile: AutoCompleteTextView
    private lateinit var etPassword: AutoCompleteTextView
    private lateinit var etPasswordConfirm: AutoCompleteTextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_password_recover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etMobile = cet_auth_mobile.input
        etPassword = cet_auth_password.input
        etPasswordConfirm = cet_auth_password_confirm.input

        TextViewHelper.setForMobile(etMobile)
        TextViewHelper.setForPassword(etPassword)
        TextViewHelper.setForPassword(etPasswordConfirm)

        // mobile
        etMobile.requestFocus()

        // sms code
        smsCodeHelper = SmsCodeHelper(context!!, btn_sms_code, SmsCodeType.FORGET_PASSWORD)
        btn_sms_code.setOnClickListener { getSmsCode() }
        TextViewHelper.setForEnable(btn_sms_code, arrayOf(etMobile))

        ll_protocol.visibility = View.GONE

        // register
        btn_submit.text = SpannableString(getString(R.string.common_submit))
        btn_submit.setOnClickListener {
            enableInput(false)
            if(!attemptRecover()) {
                enableInput(true)
            }
        }

        TextViewHelper.setForEnable(btn_submit, arrayOf(etMobile, et_sms_code, etPassword, etPasswordConfirm))

        tv_contact_customer_service.text = Configs.ADVISER_TEL
        tv_contact_customer_service.setOnClickListener { DialogHelper.telConsultation(activity!!).show() }
    }

    override fun onDestroyView() {
        disposables.clear()
        smsCodeHelper.clearTask()
        super.onDestroyView()
    }

    private fun getSmsCode() {
        // 验证手机号有效性
        if (!TextViewHelper.verifyMobile(etMobile)) {
            return
        }
        smsCodeHelper.startTask(etMobile.text.toString())
    }

    private fun attemptRecover(): Boolean {
        // 验证信息
        if (!TextViewHelper.verifyMobile(etMobile)
                || !TextViewHelper.verifySmsCode(et_sms_code)
                || !TextViewHelper.verifyPassword(etPassword)) {
            return false
        }

        // 密码不一致
        if(!TextUtils.equals(etPassword.text, etPasswordConfirm.text)) {
            ToastUtils.showShort(R.string.password_inconsistent)
            return false
        }

        // 网路请求 展示加载动画 成功后跳转
        val loading = DialogHelper.loading(context!!)
        val mobile = etMobile.text.toString()
        val password = etPassword.text.toString().toUpperCase()
        val smsCode = et_sms_code.text.toString()
        val d = NetApi.recoverPassword(mobile, password, smsCode)
                .compose(RxHelper.applyLoading(loading))
                .doAfterTerminate { enableInput(true) }
                .subscribe({ _ ->
                    ToastUtils.showShort(getString(R.string.password_recover_success))
                    notifyActivityNext()
                }, { t ->
                    ToastUtils.showShort("${t.message}")
                })

        disposables.add(d)
        return true
    }

    private fun enableInput(enabled:Boolean) {
        etMobile.isEnabled = enabled
        et_sms_code.isEnabled = enabled
        etPassword.isEnabled = enabled
        etPasswordConfirm.isEnabled = enabled
    }


}