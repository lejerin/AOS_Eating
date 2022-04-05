package desla.aos.eating.ui.main.user.active

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.databinding.FragmentActiveListBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.ui.main.MainViewModel
import desla.aos.eating.ui.main.home.HomeRCAdapter
import kotlinx.android.synthetic.main.fragment_active_list.*
import kotlinx.android.synthetic.main.fragment_map_search.*


class ActiveListFragment : BaseFragment<FragmentActiveListBinding>(){

    override val layoutResourceId: Int
        get() = R.layout.fragment_active_list

    val viewModel by activityViewModels<MainViewModel>()

    private val TAG = "SettingFragment"

    private var post = mutableListOf<PostsResponse.Data>()

    private val posts1: MutableList<PostsResponse.Data> = mutableListOf()
    private val posts2: MutableList<PostsResponse.Data> = mutableListOf()
    var pos = 0



    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        viewDataBinding.vm = viewModel

    }

    override fun initDataBinding() {


        val pagerAdapter = ScreenSlidePagerAdapter(childFragmentManager)
        viewDataBinding.vpMain.adapter = pagerAdapter

        viewDataBinding.tabLayout.addTab(viewDataBinding.tabLayout.newTab().setText("오픈 목록"))
        viewDataBinding.tabLayout.addTab(viewDataBinding.tabLayout.newTab().setText("참여 목록"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL


        viewDataBinding.vpMain.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewDataBinding.vpMain.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


    }

    override fun initAfterBinding() {

    }


    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {


        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment {

            return if(position > 0){
                MyListSecondFragment()
            }else{
                MyListFirstFragment()
            }

        }

    }


}