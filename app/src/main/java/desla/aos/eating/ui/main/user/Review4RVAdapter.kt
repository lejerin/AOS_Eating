package desla.aos.eating.ui.main.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.License
import desla.aos.eating.data.model.MapSearch
import desla.aos.eating.data.model.Review
import desla.aos.eating.databinding.ItemRvLicenseBinding
import desla.aos.eating.databinding.ItemRvMapSearchBinding
import desla.aos.eating.databinding.ItemRvReviewBinding


class Review4RVAdapter (
    private val list : List<Review.Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemCount() = if(list.size >= 4) 4 else list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_rv_review,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.binding.review = list[position]

        }

    }

    inner class HomeViewHolder(
        val binding: ItemRvReviewBinding
    ) : RecyclerView.ViewHolder(binding.root)


}