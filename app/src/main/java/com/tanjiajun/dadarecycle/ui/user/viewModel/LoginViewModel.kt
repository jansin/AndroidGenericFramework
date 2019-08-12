package com.tanjiajun.dadarecycle.ui.user.viewModel

import android.text.Editable
import android.view.View
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tanjiajun.dadarecycle.BR
import com.tanjiajun.dadarecycle.data.repository.UserRepository
import com.tanjiajun.dadarecycle.ui.BaseViewModel

/**
 * Created by TanJiaJun on 2019-08-02.
 */
class LoginViewModel(
        private val repository: UserRepository
) : BaseViewModel() {

    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val _loginEnable = MutableLiveData<Boolean>()
    @get:Bindable
    val loginEnable: LiveData<Boolean> = _loginEnable

    val loginSuccess = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    fun checkLoginEnable() {
        _loginEnable.value = !phoneNumber.value.isNullOrEmpty() && !password.value.isNullOrEmpty()
        notifyPropertyChanged(BR.loginEnable)
    }

    fun login() =
            launch({
                val phoneNumber = phoneNumber.value
                val password = password.value

                if (!phoneNumber.isNullOrEmpty() && !password.isNullOrEmpty()) {
                    repository.login(phoneNumber, password).let {
                        loginSuccess.value = true
                    }
                }
            }, {
                loginSuccess.value = false
                errorMessage.value = it.message
            })

    interface Handlers {

        fun onPhoneNumberAfterTextChanged(editable: Editable)

        fun onPasswordAfterTextChanged(editable: Editable)

        fun onLoginClick(view: View)

    }

}