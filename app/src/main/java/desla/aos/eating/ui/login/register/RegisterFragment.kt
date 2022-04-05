package desla.aos.eating.ui.login.register

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.fragment.app.activityViewModels
import desla.aos.eating.R
import desla.aos.eating.databinding.FragmentRegisterBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.login.LoginViewModel

class RegisterFragment :  BaseFragment<FragmentRegisterBinding>() {


    override val layoutResourceId: Int
        get() = R.layout.fragment_register

    val viewModel by activityViewModels<LoginViewModel>()

    private val TAG = "RegisterFragment"

    override fun initStartView() {

        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {



    }

    override fun initAfterBinding() {
        viewDataBinding.profileImg.background = ShapeDrawable(OvalShape())
        viewDataBinding.profileImg.clipToOutline = true
    }



}