package be.arnaud.rocketleaguestats.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.search.SearchData

class SearchAdapter(context: Context, private val items: List<SearchData>) :
    ArrayAdapter<SearchData>(
        context,
        R.id.leaderboard_stats_name,
        items.distinctBy { searchData -> searchData.platformUserIdentifier }) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView?: LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)
        val currentItem = getItem(position)!!
        view.findViewById<TextView>(R.id.search_name)?.text = currentItem.platformUserHandle
        view.tag = currentItem
        return view
    }
}