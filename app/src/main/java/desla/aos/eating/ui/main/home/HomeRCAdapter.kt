package desla.aos.eating.ui.main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.PostsResponse
import desla.aos.eating.databinding.ItemRvHomeBinding
import desla.aos.eating.ui.main.view.client.ViewActivity
import desla.aos.eating.ui.main.view.host.ViewHostActivity
import desla.aos.eating.util.getActivity
import java.text.SimpleDateFormat
import java.util.*

class HomeRCAdapter (
        private val postList : List<PostsResponse.Data>,
        private val interfaceHomeLike: InterfaceHomeLike?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    interface InterfaceHomeLike {
        fun clickLike(isLike: Boolean, id: Int)
    }

    companion object
    {
        private const val VIEW_TYPE_DATA = 0
        private const val VIEW_TYPE_LIKE_BOTTOM = 1
    }

    override fun getItemCount() = postList.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  = when (viewType) {
        VIEW_TYPE_DATA -> HomeViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_rv_home,
                        parent,
                        false
                )
        )
        VIEW_TYPE_LIKE_BOTTOM ->
        {//inflates progressbar layout
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transparent_bottom,parent,false)
            BottomViewHolder(view)
        }

        else -> throw IllegalArgumentException("Different View type")

    }

    override fun getItemViewType(position: Int): Int
    {
        if(position == itemCount-1) return VIEW_TYPE_LIKE_BOTTOM


        return VIEW_TYPE_DATA
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.homeBinding.post = postList[position]

            holder.homeBinding.homeLike.isSelected = postList[position].favorite
            holder.homeBinding.homeLike.setOnClickListener {
                val isLike = !(postList[position].favorite)
                holder.homeBinding.homeLike.isSelected = isLike

                interfaceHomeLike?.clickLike(isLike, postList[position].postId)

                postList[position].favorite = isLike
            }

            holder.homeBinding.thumb.clipToOutline = true


            holder.homeBinding.layout.setOnClickListener {
                val context = it.context

                if(postList[position].mine){
                    val detailViewIntent = Intent(context, ViewHostActivity::class.java)
                    detailViewIntent.putExtra("data", postList[position])
                    context.getActivity()?.startActivity(detailViewIntent)
                }else{
                    val detailViewIntent = Intent(context, ViewActivity::class.java)
                    detailViewIntent.putExtra("data", postList[position])
                    context.getActivity()?.startActivity(detailViewIntent)
                }


            }


        }

    }


    inner class HomeViewHolder(
            val homeBinding: ItemRvHomeBinding
    ) : RecyclerView.ViewHolder(homeBinding.root)

    inner class BottomViewHolder(
            itemView: View
    ) : RecyclerView.ViewHolder(itemView)




}