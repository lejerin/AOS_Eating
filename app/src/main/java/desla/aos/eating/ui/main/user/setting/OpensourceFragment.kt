package desla.aos.eating.ui.main.user.setting

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.License
import desla.aos.eating.databinding.FragmentOpensourceBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.ui.main.MainViewModel
import desla.aos.eating.ui.map.MapSearchRCAdapter


class OpensourceFragment : BaseFragment<FragmentOpensourceBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_opensource

    val viewModel by activityViewModels<MainViewModel>()

    private val TAG = "SettingFragment"

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        viewDataBinding.vm = viewModel

    }

    override fun initDataBinding() {

        initRc()

    }

    override fun initAfterBinding() {

    }

    private fun initRc(){

        val licenseList = mutableListOf<License>()

        licenseList.add(License("1. Kakao Open SDK", "https://developers.kakao.com/docs",
            "Copyright Kakao Corp", "Apache License 2.0"))

        licenseList.add(License("2. Gson", "https://github.com/google/gson",
            "Copyright 2008 Google Inc.", "Apache License 2.0"))

        licenseList.add(License("3. Retrofit", "https://square.github.io/retrofit",
            "Copyright 2013 Square, Inc.", "Apache License 2.0"))

        licenseList.add(License("4. RxAndroid", "https://github.com/ReactiveX/RxAndroid",
            "Copyright 2015 The RxAndroid authors", "Apache License 2.0"))


        licenseList.add(License("5. RealtimeBlurView", "https://github.com/mmin18/RealtimeBlurView",
            "Copyright 2016 Tu Yimin", "Apache License 2.0"))

        licenseList.add(License("6. SingleDateAndTimePicker", "https://github.com/florent37/ SingleDateAndTimePicker",
            "Copyright 2016 florent37, Inc.", "Apache License 2.0"))

        viewDataBinding.rvOpensource.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewDataBinding.rvOpensource.setHasFixedSize(true)
        viewDataBinding.rvOpensource.adapter = OpensourceRVAdapter(licenseList)

    }

}