package desla.aos.eating.ui.login

import android.content.Intent
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.MyApplication
import desla.aos.eating.R
import desla.aos.eating.data.network.KakaoApi
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repository.LoginRepository
import desla.aos.eating.databinding.ActivityLoginBinding
import desla.aos.eating.ui.base.BaseActivity
import desla.aos.eating.ui.map.MapFragment
import desla.aos.eating.ui.login.register.RegisterFragment
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.ui.map.MapActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_login

    private lateinit var viewModel: LoginViewModel


    override fun initStartView() {

        val repository = LoginRepository(ServerApi())
        val factory = LoginViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun initDataBinding() {
        viewModel.isLogin.observe(this, Observer { isLogin ->

            when (isLogin) {
                0 -> {
                    supportFragmentManager.commit {
                        setReorderingAllowed(true) //애니메이션 최적화를 위한 설정
                        add<RegisterFragment>(R.id.fcv_login, "register")
                        addToBackStack(null)
                    }
                }
                1 -> {
                    //백스택 지우기
                    MyApplication.prefs.setString("id", viewModel.kakao_id)
                    MyApplication.prefs.setString("name", viewModel.nickname)
                    val intent = Intent(this, MapActivity::class.java)
                    intent.putExtra("isRegister", true)
                    startActivity(intent)
                    finish()

                }
                2 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }

        })
    }

    override fun initAfterBinding() {

    }


}