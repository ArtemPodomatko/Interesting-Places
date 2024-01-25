package ru.aapodomatko.interestingplaces.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import ru.aapodomatko.interestingplaces.R
import ru.aapodomatko.interestingplaces.databinding.FragmentFavoriteBinding
import ru.aapodomatko.interestingplaces.ui.adapters.InterestingPlacesAdapter
import ru.aapodomatko.interestingplaces.viewModels.FavoriteViewModel

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var favoriteAdapter: InterestingPlacesAdapter
    private val viewModel by viewModels<FavoriteViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        viewModel.placesFavorite.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                mBinding.favorite.visibility = View.VISIBLE
                mBinding.favoritePreview.visibility = View.GONE
                mBinding.favoriteRecyclerView.visibility = View.VISIBLE
                mBinding.favoriteItemsCount.visibility = View.VISIBLE
            } else {
                mBinding.favorite.visibility = View.GONE
                mBinding.favoritePreview.visibility = View.VISIBLE
                mBinding.favoriteRecyclerView.visibility = View.GONE
                mBinding.favoriteItemsCount.visibility = View.GONE
            }
            favoriteAdapter.differ.submitList(it)
            favoriteAdapter.setDeleteIconClickListener { item ->
                viewModel.deleteFavoriteItem(item)
                val position = it.indexOf(item)
                if (position != -1) {
                    favoriteAdapter.notifyItemRemoved(position)
                    favoriteAdapter.notifyItemRangeChanged(position, it.size)
                }
            }
            mBinding.favoriteItemsCount.text = it.size.toString()
        }

        favoriteAdapter.setOnClickListener {
            val bundle = bundleOf("result" to it)
            findNavController().navigate(
                R.id.action_favoriteFragment_to_descriptionFragment,
                bundle
                )
        }
    }

    private fun initAdapter() {
        favoriteAdapter = InterestingPlacesAdapter(isFavoriteFragment = true)
        mBinding.favoriteRecyclerView.apply {
            adapter = favoriteAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }



}