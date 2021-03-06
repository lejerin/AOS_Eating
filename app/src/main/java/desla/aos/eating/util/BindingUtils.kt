package desla.aos.eating.util

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import desla.aos.eating.R
import desla.aos.eating.data.model.PostResponse
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.data.model.Review
import desla.aos.eating.ui.main.home.HomeRCAdapter
import desla.aos.eating.ui.main.user.Review4RVAdapter
import desla.aos.eating.ui.main.user.review.ReviewRVAdapter


@BindingAdapter("image")
fun loadImageview(view : ImageView, url: String?){
    if(url != null){
        Glide.with(view)
                .load(url)
                .placeholder(R.color.white)
                .fitCenter()
                .centerCrop()
                .into(view)
    }
}

val img = arrayListOf<Int>(R.drawable.cb_0, R.drawable.cb_1, R.drawable.cb_2,R.drawable.cb_3,
        R.drawable.cb_4, R.drawable.cb_5, R.drawable.cb_6, R.drawable.cb_7, R.drawable.cb_8,
        R.drawable.cb_9)

@BindingAdapter("category_img")
fun category_img(view: ImageView, num: Int){

    view.setImageResource(img[num])

}

@BindingAdapter("deliveryFeeByHost")
fun deliveryFeeByHost(view : TextView, num: Int){
    //배달비비

    view.text =  when(num){
        0 -> "호스트 부담"
        1 -> "1/N"
        else -> "없음"
    }

}

@BindingAdapter("meetLocation")
fun meetLocation(view : TextView, num: Int){

    view.text =  when(num){
        0 -> "호스트 쪽"
        1 -> "중간 지점"
        else -> "장소협의"
    }

}

@BindingAdapter("showCount_num", "showCount_pos")
fun showCount(view : ImageView, num: Int,  pos: Int){

    view.isSelected = num >= pos

}

@BindingAdapter("time")
fun time(view : TextView, oldTime: String){
    view.text = MyTimeUtils.twoDatesBetweenTime(oldTime)
}

@BindingAdapter("img_time_finished")
fun img_time_finished(view: ImageView, oldTime: String){

    if(MyTimeUtils.isBefore(oldTime)){
        view.setColorFilter(Color.parseColor("#808080"), PorterDuff.Mode.MULTIPLY)
    }
}

@BindingAdapter("tv_time_finished")
fun tv_time_finished(view: TextView, oldTime: String){

    if(MyTimeUtils.isBefore(oldTime)){
        view.visibility = View.VISIBLE
    }else{
        view.visibility = View.INVISIBLE
    }
}


@BindingAdapter("items_comment")
fun items_comment(recyclerView: RecyclerView, items: List<Review.Data>?) {

    if(items == null) return

    if(recyclerView.adapter == null){
        val lm = LinearLayoutManager(recyclerView.context)
        val adapter = ReviewRVAdapter(items)
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter
    }

    recyclerView.adapter?.notifyDataSetChanged()

}


@BindingAdapter("items_4review")
fun items_4review(recyclerView: RecyclerView, items: List<Review.Data>?) {

    if(items == null) return

    if(recyclerView.adapter == null){
        val lm = LinearLayoutManager(recyclerView.context)
        val adapter = Review4RVAdapter(items)
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter
    }

    recyclerView.adapter?.notifyDataSetChanged()

}




