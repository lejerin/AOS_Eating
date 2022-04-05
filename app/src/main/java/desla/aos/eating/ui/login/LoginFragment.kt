package desla.aos.eating.ui.login

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.R
import desla.aos.eating.databinding.FragmentLoginBinding
import desla.aos.eating.ui.base.BaseFragment

class LoginFragment :  BaseFragment<FragmentLoginBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_login

    val viewModel by activityViewModels<LoginViewModel>()

    private val TAG = "LoginFragment"

    override fun initStartView() {

        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {



    }

    override fun initAfterBinding() {

    }




}