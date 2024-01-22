package ru.aapodomatko.interestingplaces.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import ru.aapodomatko.interestingplaces.R
import ru.aapodomatko.interestingplaces.models.places.Image

class DescriptionImageSlider(private val listImages: ArrayList<Image>, viewPager2: ViewPager2)
    : RecyclerView.Adapter<DescriptionImageSlider.DescriptionImageSliderViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DescriptionImageSliderViewHolder {
        return DescriptionImageSliderViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.description_image_container, parent, false
        ))
    }

    override fun onBindViewHolder(
        holder: DescriptionImageSliderViewHolder,
        position: Int
    ) {
        val image = listImages[position]
        holder.itemView.apply {
            val imageView = findViewById<ImageView>(R.id.image_view_description)
            Glide.with(this).load(image.image).into(imageView)
        }

    }


    override fun getItemCount(): Int {
        return listImages.size
    }

    class DescriptionImageSliderViewHolder(view: View) : RecyclerView.ViewHolder(view)
}