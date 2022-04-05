package desla.aos.eating.ui.main.user

import android.animation.ValueAnimator
import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import desla.aos.eating.MyApplication
import desla.aos.eating.R
import desla.aos.eating.databinding.FragmentHomeBinding
import desla.aos.eating.databinding.FragmentUserBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.ui.main.MainViewModel
import desla.aos.eating.ui.map.MapActivity
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment :  BaseFragment<FragmentUserBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_user

    val viewModel by activityViewModels<MainViewModel>()

    private val TAG = "HomeFragment"

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(true)

        viewModel.getUser()
    }


    override fun initDataBinding() {

        //임시
        viewDataBinding.btnSettingUser.setOnClickListener {
            //startActivity(Intent(it.context, ReviewActivity::class.java))
            findNavController().navigate(R.id.action_userFragment_to_settingFragment)
        }

        viewDataBinding.layoutAct.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_activeListFragment)
        }

        viewDataBinding.layoutReview.setOnClickListener {
            findNavController().navigate(R.id.action_userFragment_to_myReviewFragment)
        }

        viewDataBinding.nickName = MyApplication.prefs.getString("nickname", "")

        viewModel.user.observe(viewLifecycleOwner, Observer {
            viewDataBinding.user = it

            initAnimation(it.sugarScore / 100.0f)
        })

        viewModel.review.observe(viewLifecycleOwner, Observer {
            viewDataBinding.data = it
        })

        viewModel.getReview()


    }

    private fun initAnimation(v: Float){
        val anim = ValueAnimator.ofFloat(1.0f, v)
        anim.duration = 700
        anim.addUpdateListener { animation ->

            try {
                val set = ConstraintSet()
                set.clone(user_const)
                set.setHorizontalBias(R.id.red, animation.animatedValue as Float)
                set.applyTo(user_const)
            }catch (e: Exception){

            }


        }
        anim.start()
    }

    override fun initAfterBinding() {

    }


}