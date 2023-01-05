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
        // Inflate view
        val view: View = convertView?: LayoutInflater.from(context).inflate(R.layout.spinner_row_board, parent, false)
        // Get current item
        val currentItem = getItem(position)!!
        // Change textview text
        view.findViewById<TextView>(R.id.spinner_board_text)?.text = context.resources.getText(currentItem.resourceId)
        // Change view tag
        view.tag = currentItem
        // Return view
        return view
    }
}