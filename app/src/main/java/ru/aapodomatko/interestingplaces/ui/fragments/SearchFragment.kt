package ru.aapodomatko.interestingplaces.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.aapodomatko.interestingplaces.databinding.FragmentSearchBinding
import ru.aapodomatko.interestingplaces.ui.adapters.SearchFragmentAdapter
import ru.aapodomatko.interestingplaces.viewModels.SearchFragmentViewModel


@AndroidEntryPoint
class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<SearchFragmentViewModel>()

    private lateinit var searchAdapter: SearchFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        mBinding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.searchAll(query)
                }
                viewModel.searchResponse.observe(viewLifecycleOwner) {
                    searchAdapter.differ.submitList(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

        searchAdapter.setOnClickListener { result ->
            try {
                Intent()
                    .setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setData(Uri.parse(takeIf { URLUtil.isValidUrl(result.item_url) }
                        ?.let {
                            result.item_url
                        } ?: "https://google.com"
                    ))
                    .let {
                        ContextCompat.startActivity(requireContext(), it, null)
                    }
            } catch (e: Exception) {
                Toast.makeText(context,
                    "На вашем устройстве нет подходящего приложения для открытия сайта",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun initAdapter() {
        searchAdapter = SearchFragmentAdapter()
        mBinding.searchRecyclerView.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

}