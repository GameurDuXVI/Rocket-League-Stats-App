package be.arnaud.rocketleaguestats.ui.individual.ranking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import be.arnaud.rocketleaguestats.api.PlayList
import be.arnaud.rocketleaguestats.api.Rank
import be.arnaud.rocketleaguestats.api.models.profilesegment.ProfileSegment
import be.arnaud.rocketleaguestats.databinding.ItemIndividualRankingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL

class IndividualRankingRecycleViewAdapter(val fragment: Fragment, val data: List<ProfileSegment>): RecyclerView.Adapter<IndividualRankingRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemIndividualRankingBinding): RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemIndividualRankingBinding.inflate(LayoutInflater.from(fragment.requireContext()), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        val playlist = PlayList.fromId(item.attributes?.playlistId)
        if (playlist != null) {
            holder.binding.individualRankingPlaylist.text = fragment.requireContext().resources.getText(playlist.resourceId)
        }

        holder.binding.individualRankingDivision.text = item.stats.division.metadata.name
        holder.binding.individualRankingValue.text = item.stats.rating.value.toString()

        val rank = Rank.fromId(item.stats.tier.value)
        holder.binding.individualRankingIcon.setImageResource(rank.drawableId)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}