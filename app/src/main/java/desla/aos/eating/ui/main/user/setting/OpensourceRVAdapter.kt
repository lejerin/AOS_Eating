package desla.aos.eating.ui.main.user.setting

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
import desla.aos.eating.databinding.ItemRvLicenseBinding
import desla.aos.eating.databinding.ItemRvMapSearchBinding


class OpensourceRVAdapter (
    private val list : List<License>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun getItemCount() = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_rv_license,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.binding.license = list[position]

        }

    }

    inner class HomeViewHolder(
        val binding: ItemRvLicenseBinding
    ) : RecyclerView.ViewHolder(binding.root)


}