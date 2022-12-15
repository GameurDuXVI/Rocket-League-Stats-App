package be.arnaud.rocketleaguestats.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.models.search.SearchData
import be.arnaud.rocketleaguestats.databinding.ItemIndividualStatsBinding
import be.arnaud.rocketleaguestats.databinding.ItemSearchBinding


class SearchAdapter(val fragment: Fragment, val data: List<SearchData>, private val callback: ((obj: SearchData) -> Unit)?): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    class ViewHolder: RecyclerView.ViewHolder {
        val binding: ItemSearchBinding
        constructor(binding: ItemSearchBinding, callback: (obj: Int) -> Unit): super(binding.root){
            this.binding = binding
            binding.root.setOnClickListener {
                callback(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(fragment.requireContext()), parent, false)) {
            callback?.let { it1 -> it1(data[it]) }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val platform = Platform.fromType(item.platformSlug)

        holder.binding.searchName.text = item.platformUserHandle
        if (platform != null) {
            holder.binding.searchPlatformIcon.setImageResource(platform.drawableResource)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}