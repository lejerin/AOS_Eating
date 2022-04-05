package desla.aos.eating.ui.login

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import desla.aos.eating.ui.base.BaseViewModel
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import desla.aos.eating.MyApplication
import desla.aos.eating.data.repository.LoginRepository
import desla.aos.eating.util.getActivity
import desla.aos.eating.util.hideKeyboard
import desla.aos.eating.util.showNoFuncDoialog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginViewModel(val repository: LoginRepository) : BaseViewModel()  {

    private val TAG = "LoginViewModel"

    private val _isLogin = MutableLiveData<Int>()
    val isLogin : LiveData<Int>
        get() = _isLogin


    var nickname = ""
    var kakao_id = ""

    fun close(v: View){
        v.context.getActivity()?.onBackPressed()
    }

    fun showNoFunc(v: View){
        v.showNoFuncDoialog(v)
    }

    fun startMapFragment(v: View){
        MyApplication.prefs.setString("nickname", nickname)
        _isLogin.value = 1
    }

    private fun sendLogin() {


        val params:HashMap<String, Any> = HashMap<String, Any>()
        params["kakaoId"] = kakao_id


        Log.i("eunjin", "로그인 시작 ${params}" )

        val disposable = repository.postLogin(
            params
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if(it.isSuccessful){
                    if(it.body()?.status == "OK"){
                        Log.i("eunjin", "로그인 성공 ${it.code()}" )
                        MyApplication.prefs.setUserLocation(it.body()?.data?.address!!,it.body()?.data?.latitude!!.toString(),
                            it.body()?.data?.longitude!!.toString())
                        _isLogin.value = 2
                    }else{
                        Log.i("eunjin", "로그인 실패1: 회원가입 필요" )
                        _isLogin.value = 0
                    }

                }else{
                    Log.i("eunjin", "로그인 실패2" )
                    _isLogin.value = 0
                }

            }, {

                Log.i("eunjin", "가입 실패3" )
            })
        addDisposable(disposable)

    }

    fun kakaoSignIn(v: View){
        // 로그인 공통 callback 구성

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->

            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
                _message.value = "로그인 실패"
            }

            else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                getKakaoUserId()
            }

        }

        val context = v.context

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (LoginClient.instance.isKakaoTalkLoginAvailable(context)) {
            LoginClient.instance.loginWithKakaoTalk(context, callback = callback)
        } else {
            LoginClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }


    private fun getKakaoUserId(){
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.e(TAG, "토큰 정보 보기 실패", error)
                _message.value = "로그인 실패 : {$error.message.toString()}"
            }
            else if (tokenInfo != null) {
                Log.i(TAG, "토큰 정보 보기 성공" +
                        "\n회원번호: ${tokenInfo.id}" +
                        "\n만료시간: ${tokenInfo.expiresIn} 초")


                kakao_id = "${tokenInfo.id}"
                sendLogin()

            }
        }
    }

    val clicksListener = object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    if (v != null) {
                        startMapFragment(v)
                        v.hideKeyboard()
                    }
                }
                else -> return false
            }
            return true
        }
    }







}