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

        if (binding.individualStatsRecycleView.layoutManager == null) {
            binding.individualStatsRecycleView.layoutManager =
                LinearLayoutManager(requireContext())
        }

        (activity as MainActivity).setSearchQuery(currentQuery)

        return binding.root
    }

    override fun onDestroyView() {
        (activity as MainActivity).hideSearchView()
        super.onDestroyView()
    }

    @MainThread
    fun query(query: String) {
        println("search with '" + query +"'")

        DbUtils.getAllSearchHistoriesLike(context!!, query) { matchedWithCache ->
            val matchedWithCacheToItem = matchedWithCache.map { d -> SearchAdapter.Item(d.identifier, d.username, d.platform) }

            binding.individualStatsRecycleView.adapter =
                SearchAdapter(this, matchedWithCacheToItem, emptyList()){
                    openIndividual(it.identifier, it.username, it.platform)
                }

            currentQuery = query

            if (query.isEmpty()) {
                return@getAllSearchHistoriesLike
            }

            setLoadingHeader()
            RestApi.search(query) { data ->
                if (currentQuery != query) {
                    return@search
                }

                val dataToItem = data
                    .map { d -> SearchAdapter.Item(d.platformUserIdentifier, d.platformUserHandle, d.platformSlug) }
                val items = matchedWithCacheToItem + dataToItem

                if (items.isEmpty()) {
                    setNoDataHeader()
                } else {
                    removeHeader()
                }

                binding.individualStatsRecycleView.adapter =
                    SearchAdapter(this, matchedWithCacheToItem, dataToItem) {
                        openIndividual(it.identifier, it.username, it.platform)
                    }
            }
        }
    }

    private fun openIndividual(identifier: String, username: String, platform: String){
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