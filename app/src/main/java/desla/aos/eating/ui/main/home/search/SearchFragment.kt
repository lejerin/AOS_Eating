package desla.aos.eating.ui.main.home.search

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import desla.aos.eating.R
import desla.aos.eating.data.model.Post
import desla.aos.eating.databinding.FragmentSearchBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.custom.NoFuncDialog
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.util.showNoFuncDoialog


class SearchFragment :  BaseFragment<FragmentSearchBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_search

    private val TAG = "SearchFragment"

    private val rankList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        rankList.add("햄버거")
        rankList.add("샌드위치")
        rankList.add("곱창")
        rankList.add("떡볶이")
        rankList.add("족발")
        rankList.add("뿌링클")
        rankList.add("보쌈")
        rankList.add("샐러드")
        rankList.add("빙수")
        rankList.add("닭발")

        rankList.shuffle()


    }

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

    }

    override fun initDataBinding() {
        initRcRank()
    }

    override fun initAfterBinding() {

        viewDataBinding.tvAddress.setOnClickListener {
            it.showNoFuncDoialog(it)
        }

        viewDataBinding.btnCloseSearch.setOnClickListener {
            activity?.onBackPressed()
        }

    }

    private fun initRcRank(){
        viewDataBinding.rcRank.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        viewDataBinding.rcRank.setHasFixedSize(true)
        viewDataBinding.rcRank.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        viewDataBinding.rcRank.adapter = SearchRankRCAdapter(rankList)


    }



}