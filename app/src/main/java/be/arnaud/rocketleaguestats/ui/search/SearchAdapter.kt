package be.arnaud.rocketleaguestats.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.models.search.SearchData
import be.arnaud.rocketleaguestats.databinding.ItemIndividualStatsBinding
import be.arnaud.rocketleaguestats.databinding.ItemSearchBinding
import be.arnaud.rocketleaguestats.databinding.ItemSearchSeparatorBinding


class SearchAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {

    val fragment: Fragment
    private var allItems: List<Item>
    private val callback: ((obj: Item) -> Unit)?

    constructor(fragment: Fragment, historyItems: List<Item>, items: List<Item>, callback: ((obj: Item) -> Unit)?){
        val historySeparator = Item("", fragment.context!!.resources.getString(R.string.recently_viewed), "", true)
        val itemsSeparator = Item("", fragment.context!!.resources.getString(R.string.results), "", true)
        this.fragment = fragment
        this.allItems = emptyList()
        this.callback = callback

        if (historyItems.isNotEmpty()) {
            this.allItems += listOf(historySeparator) + historyItems
        }
        if (items.isNotEmpty()) {
            if (historyItems.isNotEmpty()){
                this.allItems += listOf(itemsSeparator)
            }
            this.allItems += items
        }
    }

    class ViewHolder: RecyclerView.ViewHolder {
        val binding: ItemSearchBinding
        constructor(binding: ItemSearchBinding, callback: (obj: Int) -> Unit): super(binding.root){
            this.binding = binding
            binding.root.setOnClickListener {
                callback(adapterPosition)
            }
        }
    }
    class SeparatorViewHolder(val binding: ItemSearchSeparatorBinding): RecyclerView.ViewHolder(binding.root)

    class Item(val identifier: String, val username: String, val platform: String){
        var separator: Boolean = false

        constructor(identifier: String, username: String, platform: String, separator: Boolean) : this(identifier, username, platform) {
            this.separator = separator
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (allItems[position].separator) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0) {
            return ViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(fragment.requireContext()), parent, false)) {
                callback?.let { it1 -> it1(allItems[it]) }
            }
        }
        return SeparatorViewHolder(ItemSearchSeparatorBinding.inflate(LayoutInflater.from(fragment.requireContext()), parent, false))
    }

    override fun onBindViewHolder(h: RecyclerView.ViewHolder, position: Int) {
        val item = allItems[position]
        if (item.separator) {
            val holder = h as SeparatorViewHolder
            holder.binding.searchSeparatorText.text = item.username
        } else {
            val holder = h as ViewHolder
            val platform = Platform.fromType(item.platform)

            holder.binding.searchName.text = item.username
            if (platform != null) {
                holder.binding.searchPlatformIcon.setImageResource(platform.drawableResource)
            }
        }
    }

    override fun getItemCount(): Int {
        return allItems.size
    }
}