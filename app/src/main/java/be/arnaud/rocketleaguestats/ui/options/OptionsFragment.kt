package be.arnaud.rocketleaguestats.ui.options

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.databinding.FragmentOptionsBinding
import be.arnaud.rocketleaguestats.db.AppDatabase
import be.arnaud.rocketleaguestats.db.DbUtils


class OptionsFragment : Fragment() {

    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)

        // Enable button if any history item is found
        DbUtils.getAllSearchHistories(context!!) { items ->
            if (items.isNotEmpty()) {
                binding.optionCacheButton.isEnabled = true
            }
        }

        // Config click listener of the button
        binding.optionCacheButton.setOnClickListener {
            DbUtils.clearAllSearchHistory(context!!)
            binding.optionCacheButton.isEnabled = false
            Toast.makeText(context, context?.getText(R.string.option_cache_cleared), LENGTH_SHORT).show()
        }

        return binding.root
    }
}