package ru.aapodomatko.interestingplaces.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import ru.aapodomatko.interestingplaces.R
import ru.aapodomatko.interestingplaces.models.places.Result

class ImagesSliderAdapter(private val viewPager2: ViewPager2)
    : RecyclerView.Adapter<ImagesSliderAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context).inflate(
         R.layout.interesting_places_image_container, parent, false
        ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       val placeImage = differ.currentList[position]
       holder.itemView.apply {
           val imageView = findViewById<ImageView>(R.id.image_view)
           val textView = findViewById<TextView>(R.id.image_slider_title)
           Glide.with(this).load(placeImage.images.first().image).into(imageView)
           textView.text = placeImage.shortTitle
           setOnClickListener { onItemClickListener?.let { it(placeImage) } }
       }
       if (position == differ.currentList.size - 1) {
           viewPager2.post(runnable)
       }
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }

    private val runnable = Runnable {
        val newList = ArrayList(differ.currentList)
        val firstItem = differ.currentList[0]
        newList.removeAt(0)
        newList.add(firstItem)
        differ.submitList(newList)

    }

    private val callback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, callback)

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}