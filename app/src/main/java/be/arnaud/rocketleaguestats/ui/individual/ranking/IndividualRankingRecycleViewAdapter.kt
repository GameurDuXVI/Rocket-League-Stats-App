package be.arnaud.rocketleaguestats.ui.individual.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import be.arnaud.rocketleaguestats.api.PlayList
import be.arnaud.rocketleaguestats.api.Rank
import be.arnaud.rocketleaguestats.api.models.profilesegment.ProfileRankingSegment
import be.arnaud.rocketleaguestats.databinding.ItemIndividualRankingBinding

class IndividualRankingRecycleViewAdapter(val fragment: Fragment, val data: List<ProfileRankingSegment>): RecyclerView.Adapter<IndividualRankingRecycleViewAdapter.ViewHolder>() {

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
        holder.binding.individualRankingValue.text = item.stats.rating.value.toInt().toString()

        val rank = Rank.fromId(item.stats.tier.value.toInt())
        holder.binding.individualRankingIcon.setImageResource(rank.drawableId)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}