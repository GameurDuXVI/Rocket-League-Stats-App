package be.arnaud.rocketleaguestats.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.RestApi
import be.arnaud.rocketleaguestats.databinding.FragmentSearchBinding
import be.arnaud.rocketleaguestats.db.DbUtils
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

        // Define a layout manager is not exists
        if (binding.individualStatsRecycleView.layoutManager == null) {
            binding.individualStatsRecycleView.layoutManager =
                LinearLayoutManager(requireContext())
        }

        // Set search query
        (activity as MainActivity).setSearchQuery(currentQuery)

        return binding.root
    }

    override fun onDestroyView() {
        // Hide search view if this fragment is destroyed
        (activity as MainActivity).hideSearchView()
        super.onDestroyView()
    }

    @MainThread
    fun query(query: String) {
        println("search with '$query'")

        // Get all history items
        DbUtils.getAllSearchHistoriesLike(context!!, query) { matchedWithCache ->
            val matchedWithCacheToItem = matchedWithCache.map { d -> SearchAdapter.Item(d.identifier, d.username, d.platform) }

            // Show history items to ui
            binding.individualStatsRecycleView.adapter =
                SearchAdapter(this, matchedWithCacheToItem, emptyList()){
                    openIndividual(it.identifier, it.platform)
                }

            // Set current query
            currentQuery = query

            // If query is empty, return
            if (query.isEmpty()) {
                return@getAllSearchHistoriesLike
            }

            // Set loading header
            setLoadingHeader()
            // Search query on the api
            RestApi.search(query) { data ->
                // If the search is no more relevant because user typed faster than result cam, return
                if (currentQuery != query) {
                    return@search
                }

                // Map search items
                val dataToItem = data
                    .map { d -> SearchAdapter.Item(d.platformUserIdentifier, d.platformUserHandle, d.platformSlug) }
                val items = matchedWithCacheToItem + dataToItem

                // Show empty header if no search items are found
                if (items.isEmpty()) {
                    setNoDataHeader()
                } else {
                    removeHeader()
                }

                // Apply search data to the ui
                binding.individualStatsRecycleView.adapter =
                    SearchAdapter(this, matchedWithCacheToItem, dataToItem) {
                        openIndividual(it.identifier, it.platform)
                    }
            }
        }
    }

    private fun openIndividual(identifier: String, platform: String){
        val bundle = Bundle()
        bundle.putString("identifier", identifier)
        bundle.putString("platform", platform)
        (activity as MainActivity).navigate(R.id.nav_individual, bundle)
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