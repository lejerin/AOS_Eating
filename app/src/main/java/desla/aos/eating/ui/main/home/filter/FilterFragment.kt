package desla.aos.eating.ui.main.home.filter

import android.R.array
import android.os.Build
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import desla.aos.eating.MyApplication
import desla.aos.eating.R
import desla.aos.eating.databinding.FragmentFilterBinding
import desla.aos.eating.ui.base.BaseFragment
import desla.aos.eating.ui.custom.CustomFilterButton
import desla.aos.eating.ui.main.MainActivity
import desla.aos.eating.ui.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_filter.*


class FilterFragment :  BaseFragment<FragmentFilterBinding>() {

    override val layoutResourceId: Int
        get() = R.layout.fragment_filter

    val viewModel by activityViewModels<MainViewModel>()

    private val TAG = "FilterFragment"

    var distance = 0
    var category = ""

    override fun initStartView() {
        (activity as MainActivity).setVisibilityBottomNavigation(false)

        category = MyApplication.prefs.getString("category", "0-1-2-3-4-5-6-7-8-9")
        distance = MyApplication.prefs.getInt("distance", 950)

    }


    override fun initDataBinding() {


        viewDataBinding.tvProgress.text = "${(distance)}m"

        viewDataBinding.appCompatSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                distance = progress * 10

                viewDataBinding.tvProgress.text = "${(distance)}m"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        initClick()
        checkActive()

    }

    override fun initAfterBinding() {

        viewDataBinding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }



        viewDataBinding.btnFinish.setOnClickListener {
            val arr = mutableListOf<String>()

            if(btn1.getSelected()) arr.add("0")
            if(btn2.getSelected()) arr.add("1")
            if(btn3.getSelected()) arr.add("2")
            if(btn4.getSelected()) arr.add("3")
            if(btn5.getSelected()) arr.add("4")
            if(btn6.getSelected()) arr.add("5")
            if(btn7.getSelected()) arr.add("6")
            if(btn8.getSelected()) arr.add("7")
            if(btn9.getSelected()) arr.add("8")
            if(btn10.getSelected()) arr.add("9")

            category = arr.joinToString(separator = "-")

            MyApplication.prefs.setString("category", category)
            MyApplication.prefs.setInt("distance", distance)

            findNavController().popBackStack()

        }
    }



    private fun checkActive(){
        val arr = category.split("-")

        if(arr.contains("0")){
            clicked(btn1, 0)
        }
        if(arr.contains("1")){
            clicked(btn2, 1)
        }
        if(arr.contains("2")){
            clicked(btn3, 2)
        }
        if(arr.contains("3")){
            clicked(btn4, 3)
        }
        if(arr.contains("4")){
            clicked(btn5, 4)
        }
        if(arr.contains("5")){
            clicked(btn6, 5)
        }
        if(arr.contains("6")){
            clicked(btn7, 6)
        }
        if(arr.contains("7")){
            clicked(btn8, 7)
        }
        if(arr.contains("8")){
            clicked(btn9, 8)
        }
        if(arr.contains("9")){
            clicked(btn10, 8)
        }

    }

    private fun initClick(){
        btn1.setOnClickListener {
            clicked(it, 0)
        }

        btn2.setOnClickListener {
            clicked(it, 1)
        }

        btn3.setOnClickListener {
            clicked(it, 2)
        }
        btn4.setOnClickListener {
            clicked(it, 3)
        }

        btn5.setOnClickListener {
            clicked(it, 4)
        }

        btn6.setOnClickListener {
            clicked(it, 5)
        }

        btn7.setOnClickListener {
            clicked(it, 6)
        }

        btn8.setOnClickListener {
            clicked(it, 7)
        }
        btn9.setOnClickListener {
            clicked(it, 8)
        }

        btn10.setOnClickListener {
            clicked(it, 9)
        }
    }





    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewDataBinding.appCompatSeekBar.setProgress(distance / 10, true)

        }else{
            viewDataBinding.appCompatSeekBar.progress = distance/10
        }
    }


    private fun clicked(view: View, num: Int){
        (view as CustomFilterButton).let {
            it.setNotSelected(!it.getSelected())
        }

    }


}