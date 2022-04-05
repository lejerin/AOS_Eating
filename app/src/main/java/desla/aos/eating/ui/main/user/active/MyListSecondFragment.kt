package desla.aos.eating.ui.main.user.active

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.databinding.FragmentActiveListBinding
import desla.aos.eating.databinding.FragmentVpMylistBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.ui.main.MainViewModel
import desla.aos.eating.ui.main.home.HomeRCAdapter
import desla.aos.eating.ui.map.MapSearchRCAdapter
import kotlinx.android.synthetic.main.fragment_active_list.*
import kotlinx.android.synthetic.main.fragment_map_search.*


class MyListSecondFragment : BaseFragment<FragmentVpMylistBinding>(){

    override val layoutResourceId: Int
        get() = R.layout.fragment_vp_mylist

    val viewModel by activityViewModels<MainViewModel>()

    private val TAG = "SettingFragment"

    private var post = mutableListOf<PostsResponse.Data>()


    private fun ininRc(){
        viewDataBinding.vpRv.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewDataBinding.vpRv.setHasFixedSize(true)
        viewDataBinding.vpRv.adapter = HomeRCAdapter(post, null)
    }

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        ininRc()

        viewModel.myList2.observe(this, Observer {
            post.clear()
            post.addAll(it)
            viewDataBinding.vpRv.adapter?.notifyDataSetChanged()
        })


        viewModel.getMyList(1)

    }

    override fun initDataBinding() {



    }

    override fun initAfterBinding() {

    }



}