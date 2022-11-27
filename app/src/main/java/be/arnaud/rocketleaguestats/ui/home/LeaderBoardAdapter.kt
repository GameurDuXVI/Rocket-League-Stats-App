package be.arnaud.rocketleaguestats.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.leaderboard.LeaderBoardItem
import kotlin.math.roundToInt
import kotlin.math.roundToLong


class LeaderBoardAdapter: ArrayAdapter<LeaderBoardItem> {

    val items: List<LeaderBoardItem>
    var page: Int

    constructor(context: Context, items: List<LeaderBoardItem>, page: Int): super(context, R.id.spinner_platform_text, items.distinctBy { leaderBoardItem -> leaderBoardItem.id }){
        this.page = page
        this.items = items
    }

    override fun addAll(vararg items: LeaderBoardItem?) {
        super.addAll(items.filter { leaderBoardItem -> this.items.none { lbi -> lbi.id == leaderBoardItem?.id } })
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView?: LayoutInflater.from(context).inflate(R.layout.leaderboard_item, parent, false)
        val currentItem = getItem(position)!!
        view.findViewById<TextView>(R.id.leaderboard_rank)?.text = (position + 1).toString()
        view.findViewById<TextView>(R.id.leaderboard_name)?.text = currentItem.owner.metadata.platformUserHandle
        view.findViewById<TextView>(R.id.leaderboard_value)?.text = currentItem.value.roundToLong().toString()
        view.tag = currentItem
        return view
    }
}