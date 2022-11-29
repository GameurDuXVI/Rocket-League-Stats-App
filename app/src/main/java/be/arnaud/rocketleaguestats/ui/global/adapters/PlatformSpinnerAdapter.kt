package be.arnaud.rocketleaguestats.ui.global.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.Platform


class PlatformSpinnerAdapter(context: Context) :
    ArrayAdapter<Platform>(context, R.id.spinner_platform_text, Platform.values()) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView?: LayoutInflater.from(context).inflate(R.layout.spinner_row_platform, parent, false)
        val currentItem = getItem(position)!!
        if (currentItem == Platform.ALL){
            view.findViewById<TextView>(R.id.spinner_platform_text)?.text = context.resources.getText(R.string.all_platforms)
        } else {
            view.findViewById<TextView>(R.id.spinner_platform_text)?.text = currentItem.displayName
            view.findViewById<ImageView>(R.id.spinner_platform_icon)?.setImageResource(currentItem.drawableResource)
        }
        view.tag = currentItem
        return view
    }
}