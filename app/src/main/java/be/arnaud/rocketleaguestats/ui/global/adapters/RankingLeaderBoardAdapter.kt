package be.arnaud.rocketleaguestats.ui.global.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.leaderboard.LeaderBoardItem
import kotlin.math.roundToLong


class RankingLeaderBoardAdapter(context: Context, private val items: List<LeaderBoardItem>, var page: Int) :
    ArrayAdapter<LeaderBoardItem>(
        context,
        R.id.leaderboard_ranking_name,
        items.distinctBy { leaderBoardItem -> leaderBoardItem.id }) {

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
        val view: View = convertView?: LayoutInflater.from(context).inflate(R.layout.item_leaderboard_ranking, parent, false)
        val currentItem = getItem(position)!!
        view.findViewById<TextView>(R.id.leaderboard_ranking_rank)?.text = (position + 1).toString()
        view.findViewById<TextView>(R.id.leaderboard_ranking_name)?.text = currentItem.owner.metadata.platformUserHandle
        view.findViewById<TextView>(R.id.leaderboard_ranking_value)?.text = currentItem.value.roundToLong().toString()
        view.tag = currentItem
        return view
    }
}