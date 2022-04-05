package desla.aos.eating.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import desla.aos.eating.R
import desla.aos.eating.data.model.License
import desla.aos.eating.data.model.MapSearch
import desla.aos.eating.databinding.ItemRvMapSearchBinding

class MapSearchRCAdapter(
    private val addressList: MutableList<MapSearch>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    //ClickListener
    interface OnItemClickListener {
        fun onClick(position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener

    fun setLocationItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount() = addressList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_rv_map_search,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is HomeViewHolder)
        {
            holder.mapBinding.address = addressList[position]
            holder.itemView.setOnClickListener {
                itemClickListener.onClick(position)
            }
        }

    }

    inner class HomeViewHolder(
        val mapBinding: ItemRvMapSearchBinding
    ) : RecyclerView.ViewHolder(mapBinding.root)


}