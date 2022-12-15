package be.arnaud.rocketleaguestats.ui.individual.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import be.arnaud.rocketleaguestats.api.models.profilesegment.ProfileSegmentStat
import be.arnaud.rocketleaguestats.databinding.ItemIndividualStatsBinding

class IndividualStatsRecycleViewAdapter(val fragment: Fragment, val data: List<Item>): RecyclerView.Adapter<IndividualStatsRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemIndividualStatsBinding): RecyclerView.ViewHolder(binding.root)

    class Item(val data: ProfileSegmentStat, val resourceId: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemIndividualStatsBinding.inflate(LayoutInflater.from(fragment.requireContext()), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        holder.binding.individualStatsType.text = fragment.requireContext().resources.getText(item.resourceId)
        holder.binding.individualStatsValue.text = item.data.value.toInt().toString()
    }

    override fun getItemCount(): Int {
        return data.size
    }
}