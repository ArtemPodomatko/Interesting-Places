package ru.aapodomatko.interestingplaces.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import ru.aapodomatko.interestingplaces.databinding.FragmentDescriptionBinding
import ru.aapodomatko.interestingplaces.models.places.Image
import ru.aapodomatko.interestingplaces.ui.adapters.DescriptionImageSlider
import kotlin.math.abs

@AndroidEntryPoint
class DescriptionFragment : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    private val mBinding get() = _binding!!
    private val bundleArgs: DescriptionFragmentArgs by navArgs()

    private lateinit var imageAdapter: DescriptionImageSlider
    private lateinit var descriptionViewPager2: ViewPager2
    private lateinit var listImages: ArrayList<Image>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDescriptionViewPager()
        setUpTransformer()

        val placeArgs = bundleArgs.result

        placeArgs.let { placeArgs ->
            placeArgs.images.first().image.let {
                Glide.with(this).load(placeArgs.images.first().image).into(mBinding.descriptionPlaceImage)
            }
            mBinding.descriptionPlaceImage.clipToOutline = true
            mBinding.descriptionTitle.text = placeArgs.title.replaceFirstChar { it.uppercase() }
            mBinding.contentDescription.text = Html.fromHtml(placeArgs.description, Html.FROM_HTML_MODE_LEGACY)
            mBinding.countFavorite.text = placeArgs.favoriteCount.toString()
            mBinding.placeAddress.text = placeArgs.address
            mBinding.placeSchedule.text = placeArgs.timetable
            if (placeArgs.timetable == "") {
                mBinding.placeSchedule.text = "Отсутствует"
            }
            val uri = Uri.parse(placeArgs.foreignUrl)
            val domain = uri.host
            mBinding.placeSite.text = domain
            if (placeArgs.foreignUrl == "") {
                mBinding.placeSite.text = "Отсутствует"
            }
        }

        mBinding.iconBack.setOnClickListener {
            findNavController().navigateUp()
        }

        if (placeArgs.foreignUrl == "") {
            mBinding.goSiteBtn.isEnabled = false
        }

        mBinding.goSiteBtn.setOnClickListener {
            try {
                Intent()
                    .setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    .setData(Uri.parse(takeIf { URLUtil.isValidUrl(placeArgs.foreignUrl) }
                        ?.let {
                            placeArgs.foreignUrl
                        } ?: "https://google.com"
                    ))
                    .let {
                        ContextCompat.startActivity(requireContext(), it, null)
                    }
            } catch (e: Exception) {
                Toast.makeText(
                    context, "На вашем устройстве нет подходящего приложения для открытия сайта", Toast.LENGTH_LONG
                ).show()
            }

        }

        mBinding.subwayName.text = placeArgs.subway
        if (placeArgs.subway == "") {
            mBinding.subwayName.text = "Отсутствует"
        }

    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        descriptionViewPager2.setPageTransformer(transformer)

    }
    private fun initDescriptionViewPager() {
        descriptionViewPager2 = mBinding.descriptionViewPager
        listImages = bundleArgs.result.images

        imageAdapter = DescriptionImageSlider(listImages, descriptionViewPager2)
        descriptionViewPager2.apply {
            adapter = imageAdapter
            offscreenPageLimit = 1
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }







}