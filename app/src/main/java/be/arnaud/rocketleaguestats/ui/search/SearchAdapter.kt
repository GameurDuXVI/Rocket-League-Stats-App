package be.arnaud.rocketleaguestats.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.models.search.SearchData


class SearchAdapter(context: Context, items: List<SearchData>) :
    ArrayAdapter<SearchData>(
        context,
        R.id.leaderboard_stats_name,
        items) {

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

        val platform = Platform.fromType(currentItem.platformSlug)

        if (platform != null) {
            val imageView = ImageView(context)
            imageView.setImageResource(platform.drawableResource)
            imageView.adjustViewBounds = true
            val scale = context.resources.displayMetrics.density
            imageView.maxHeight = (25 * scale + 0.5f).toInt()
            imageView.maxWidth = (25 * scale + 0.5f).toInt()
            view.findViewById<LinearLayout>(R.id.search_platform_icon_container).addView(imageView)

            val space = Space(context)
            view.findViewById<LinearLayout>(R.id.search_platform_icon_container).addView(space)
            val layout = space.layoutParams
            layout.height = (25 * scale + 0.5f).toInt()
            layout.width = (10 * scale + 0.5f).toInt()
        }
        view.tag = currentItem
        return view
    }

    /*
    <ImageView
        android:id="@+id/search_icon"
        android:layout_width="wrap_content"
        android:layout_height="28dp"
        android:maxWidth="28dp"
        android:adjustViewBounds="true"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toRightOf="@+id/search_name"
        />
     */

    /*data class SearchItem(val data: ArrayList<SearchData>) {
        companion object {
            fun arrange(items: List<SearchData>): List<SearchItem> {
                val arranged = ArrayList<SearchItem>()
                for (item in items) {
                    var found = false
                    for (searchItem in arranged) {
                        if (searchItem.data.any() {arrangedItem -> arrangedItem.}) {
                            searchItem.data.add(item)
                            found = true

                        }
                    }
                    if (!found) {
                        arranged.add(SearchItem(ArrayList(listOf(item))))
                    }
                }
                return arranged
            }
        }
    }*/
}