package ru.aapodomatko.interestingplaces.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.aapodomatko.interestingplaces.R
import ru.aapodomatko.interestingplaces.models.places.Result

class InterestingPlacesAdapter : RecyclerView.Adapter<InterestingPlacesAdapter.InterestingPlacesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestingPlacesViewHolder {
        return InterestingPlacesViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.interesting_place_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: InterestingPlacesViewHolder, position: Int) {
        val place = differ.currentList[position]
        holder.itemView.apply {
            val placeImage = findViewById<ImageView>(R.id.interesting_place_image_view)
            val placeTitle = findViewById<TextView>(R.id.place_title)
            Glide.with(this).load(place.images.first().image).into(placeImage)
            placeImage.clipToOutline = true
            placeTitle.text = place.title.replaceFirstChar { it.uppercase() }

            setOnClickListener {
                onItemClickListener?.let { it(place) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class InterestingPlacesViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
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

}