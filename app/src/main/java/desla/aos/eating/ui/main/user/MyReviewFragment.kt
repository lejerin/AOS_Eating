package desla.aos.eating.ui.main.user

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.License
import desla.aos.eating.databinding.FragmentMyReviewBinding
import desla.aos.eating.databinding.FragmentOpensourceBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.ui.main.MainViewModel
import desla.aos.eating.ui.map.MapSearchRCAdapter


class MyReviewFragment : BaseFragment<FragmentMyReviewBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_my_review

    val viewModel by activityViewModels<MainViewModel>()

    private val TAG = "SettingFragment"

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        viewDataBinding.vm = viewModel

    }

    override fun initDataBinding() {

        viewModel.review.observe(viewLifecycleOwner, Observer {
            viewDataBinding.data = it
        })

        viewModel.getReview()

    }

    override fun initAfterBinding() {

    }



}