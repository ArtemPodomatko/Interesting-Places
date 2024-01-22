package ru.aapodomatko.interestingplaces.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import dagger.hilt.android.AndroidEntryPoint
import ru.aapodomatko.interestingplaces.R
import ru.aapodomatko.interestingplaces.databinding.FragmentMainBinding
import ru.aapodomatko.interestingplaces.ui.adapters.ImagesSliderAdapter
import ru.aapodomatko.interestingplaces.ui.adapters.InterestingPlacesAdapter
import ru.aapodomatko.interestingplaces.viewModels.MainFragmentViewModel
import kotlin.math.abs

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel by viewModels<MainFragmentViewModel>()

    private lateinit var interestingPlacesAdapter: InterestingPlacesAdapter
    private lateinit var placeImagesViewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageSliderAdapter: ImagesSliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initPlaceImagesViewPager2()
        setUpTransformer()

        interestingPlacesAdapter.setOnClickListener {
            val bundle = bundleOf("result" to it)
            view.findNavController().navigate(
                R.id.action_mainFragment_to_descriptionFragment,
                bundle
            )
        }

        imageSliderAdapter.setOnClickListener {
            val bundle = bundleOf("result" to it)
            view.findNavController().navigate(R.id.action_mainFragment_to_descriptionFragment, bundle)
        }

        placeImagesViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 5000)
            }
        })


        viewModel.interestingPlacesLiveData.observe(viewLifecycleOwner) {
            interestingPlacesAdapter.differ.submitList(it)
            imageSliderAdapter.differ.submitList(it.filter { it.favoriteCount > 100 })
        }
    }


    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 5000)
    }

    private val runnable = Runnable {
        placeImagesViewPager2.currentItem = placeImagesViewPager2.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        placeImagesViewPager2.setPageTransformer(transformer)

    }

    private fun initAdapter() {
        interestingPlacesAdapter = InterestingPlacesAdapter()
        mBinding.interestingPlacesRecyclerView.apply {
            adapter = interestingPlacesAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun initPlaceImagesViewPager2() {
        placeImagesViewPager2 = mBinding.interstingPlacesViewPager
        handler = Handler(Looper.myLooper()!!)

        imageSliderAdapter = ImagesSliderAdapter(placeImagesViewPager2)
        placeImagesViewPager2.apply {
            adapter = imageSliderAdapter
            offscreenPageLimit = 2
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }


    }


}

