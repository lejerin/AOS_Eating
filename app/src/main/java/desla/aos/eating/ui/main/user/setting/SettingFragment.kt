package desla.aos.eating.ui.main.user.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import desla.aos.eating.R
import desla.aos.eating.databinding.FragmentSettingBinding
import desla.aos.eating.ui.SplashActivity
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.custom.NoFuncDialog
import desla.aos.eating.ui.login.LoginActivity
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.ui.main.MainViewModel


class SettingFragment : BaseFragment<FragmentSettingBinding>(), NoFuncDialog.InterfaceYesDialog {

    override val layoutResourceId: Int
        get() = R.layout.fragment_setting

    val viewModel by activityViewModels<MainViewModel>()

    private val TAG = "SettingFragment"

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        viewDataBinding.vm = viewModel

        viewDataBinding.layoutOpensource.setOnClickListener {
            //startActivity(Intent(it.context, ReviewActivity::class.java))
            findNavController().navigate(R.id.action_settingFragment_to_opensourceFragment)
        }

        viewDataBinding.layoutLogout.setOnClickListener {
            val noFuncDialog = NoFuncDialog(it.context)
            noFuncDialog.startDouble(true, this)
        }

    }

    override fun initDataBinding() {



    }

    override fun initAfterBinding() {

    }

    override fun clickYes() {
        activity?.startActivity(Intent(activity, SplashActivity::class.java))
        activity?.finish()
    }


}