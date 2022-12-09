package be.arnaud.rocketleaguestats.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.RestApi
import be.arnaud.rocketleaguestats.databinding.FragmentSearchBinding
import be.arnaud.rocketleaguestats.ui.MainActivity


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private var currentQuery: String = ""
    private lateinit var loadingHeader: View
    private lateinit var noDataHeader: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        loadingHeader = LayoutInflater.from(context).inflate(R.layout.footer_loading, null)
        noDataHeader = LayoutInflater.from(context).inflate(R.layout.footer_no_data, null)

        (activity as MainActivity).setSearchQuery(currentQuery)

        binding.searchListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val item = (binding.searchListView.adapter as SearchAdapter).getItem(position)!!
                val bundle = Bundle()
                bundle.putString("identifier", item.platformUserIdentifier)
                bundle.putString("platform", item.platformSlug)
                (activity as MainActivity).navigate(R.id.nav_individual, bundle)
            }

        return binding.root
    }

    override fun onDestroyView() {
        (activity as MainActivity).hideSearchView()
        super.onDestroyView()
    }

    @MainThread
    fun query(query: String) {
        binding.searchListView.adapter = SearchAdapter(activity!!, emptyList())

        if (query.isEmpty()) {
            return
        }
        setLoadingHeader()
        currentQuery = query
        RestApi.search(query) { data ->
            if (currentQuery == query) {
                if (data.isEmpty()) {
                    setNoDataHeader()
                } else {
                    removeHeader()
                }
                binding.searchListView.adapter = SearchAdapter(activity!!, data)
            }
        }
    }

    private fun setLoadingHeader() {
        removeHeader()
        binding.searchHeader.addView(loadingHeader)
    }

    private fun setNoDataHeader() {
        removeHeader()
        binding.searchHeader.addView(noDataHeader)
    }

    private fun removeHeader() {
        binding.searchHeader.removeAllViews()
    }
}