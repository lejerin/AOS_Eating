package desla.aos.eating.ui.main.home

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.MyApplication
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.network.ServerApi
import desla.aos.eating.data.repository.HomeRepository
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.main.MainActivity

class HomeFragment :  BaseFragment<FragmentHomeBinding>(), HomeRCAdapter.InterfaceHomeLike {

    override val layoutResourceId: Int
        get() = R.layout.fragment_home

    private lateinit var factory: HomeViewModelFactory
    private lateinit var viewModel: HomeViewModel

    private val TAG = "HomeFragment"

    private val posts: MutableList<PostsResponse.Data> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val server = ServerApi()
        val repository = HomeRepository(server)
        factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)




        viewModel.getPost()

    }

    override fun onResume() {
        super.onResume()

        viewModel.setAddress(MyApplication.prefs.getString("address", ""))
        viewModel.setDistance(MyApplication.prefs.getInt("distance", 1000))
        viewModel.category =  MyApplication.prefs.getString("category", "0-1-2-3-4-5-6-7-8-9")
    }

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(true)

        initRc()
        viewDataBinding.vm = viewModel
    }

    override fun initDataBinding() {

        viewModel.postList.observe(this, Observer {
            posts.clear()
            posts.addAll(it)
            posts.forEach {
                Log.i("eunjin", it.orderTime)
            }
            viewDataBinding.rcHome.adapter?.notifyDataSetChanged()
        })

        viewDataBinding.btnMap.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_mapActivity)
        }

        viewModel.address.observe(this, Observer {
            viewDataBinding.tvAddressHome.text = it
        })
        viewModel.distance.observe(this, Observer {
            viewDataBinding.tvDistance.text = "${it}M"
        })


    }

    override fun initAfterBinding() {

        viewDataBinding.fabPost.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_postActivity)
        }



        viewDataBinding.btnFilter.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_filterFragment)
        }


        viewDataBinding.btnSearch.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_searchFragment)
        }

    }


    private fun initRc(){

        if(viewDataBinding.rcHome.adapter == null){
            viewDataBinding.rcHome.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            viewDataBinding.rcHome.setHasFixedSize(true)
            viewDataBinding.rcHome.adapter = HomeRCAdapter(posts, this)
        }


    }

    override fun clickLike(isLike: Boolean, id: Int) {

        viewModel.goFavorite(isLike, id)

    }


}