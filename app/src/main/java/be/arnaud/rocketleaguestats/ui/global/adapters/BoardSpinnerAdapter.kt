package be.arnaud.rocketleaguestats.ui.global.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.models.LeaderBoard


class BoardSpinnerAdapter(context: Context) :
    ArrayAdapter<LeaderBoard.Board>(context, R.id.spinner_platform_text, LeaderBoard.Board.values().filter { board -> board != LeaderBoard.Board.NONE }) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView?: LayoutInflater.from(context).inflate(R.layout.spinner_row_board, parent, false)
        val currentItem = getItem(position)!!
        view.findViewById<TextView>(R.id.spinner_board_text)?.text = context.resources.getText(currentItem.resourceId)
        view.tag = currentItem
        return view
    }
}