package be.arnaud.rocketleaguestats.ui.global.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.PlayList


class PlayListSpinnerAdapter(context: Context) :
    ArrayAdapter<PlayList>(context, R.id.spinner_platform_text, PlayList.values().filter { playList -> playList != PlayList.NONE && playList != PlayList.UN_RANKED_OLD }) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView?: LayoutInflater.from(context).inflate(R.layout.spinner_row_playlist, parent, false)
        val currentItem = getItem(position)!!
        view.findViewById<TextView>(R.id.spinner_playlist_text)?.text = context.resources.getText(currentItem.resourceId)
        view.tag = currentItem
        return view
    }
}