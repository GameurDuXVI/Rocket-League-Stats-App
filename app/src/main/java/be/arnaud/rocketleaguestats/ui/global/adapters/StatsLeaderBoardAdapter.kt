package be.arnaud.rocketleaguestats.ui.global.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.models.leaderboard.LeaderBoardItem
import kotlin.math.roundToLong


class StatsLeaderBoardAdapter(context: Context, private val items: List<LeaderBoardItem>, var page: Int) :
    ArrayAdapter<LeaderBoardItem>(
        context,
        R.id.leaderboard_stats_name,
        items.distinctBy { leaderBoardItem -> leaderBoardItem.id }) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Inflate view
        val view: View = convertView?: LayoutInflater.from(context).inflate(R.layout.item_leaderboard_stats, parent, false)
        // Get current item
        val currentItem = getItem(position)!!
        // Change textview text
        view.findViewById<TextView>(R.id.leaderboard_stats_rank)?.text = (position + 1).toString()
        view.findViewById<TextView>(R.id.leaderboard_stats_name)?.text = currentItem.owner.metadata.platformUserHandle
        view.findViewById<TextView>(R.id.leaderboard_stats_value)?.text = currentItem.value.roundToLong().toString()
        // Set view tag
        view.tag = currentItem
        // Return view
        return view
    }
}