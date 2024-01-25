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

class InterestingPlacesAdapter(private val isFavoriteFragment: Boolean) : RecyclerView.Adapter<InterestingPlacesAdapter.InterestingPlacesViewHolder>() {
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
            val iconDelete = findViewById<TextView>(R.id.icon_delete_favorite_item)
            Glide.with(this).load(place.images.first().image).into(placeImage)
            placeImage.clipToOutline = true
            placeTitle.text = place.title.replaceFirstChar { it.uppercase() }
            if (isFavoriteFragment) {
                iconDelete.visibility = View.VISIBLE
            } else {
                iconDelete.visibility = View.GONE
            }

            setOnClickListener {
                onItemClickListener?.let { it(place) }
            }
            iconDelete.setOnClickListener {
                onIconDeleteClickListener?.let {
                    it(place)
                    clearAnimation()
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    class InterestingPlacesViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private var onItemClickListener: ((Result) -> Unit)? = null
    private var onIconDeleteClickListener: ((Result) -> Unit)? = null

    fun setOnClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }
    fun setDeleteIconClickListener(listener: (Result) -> Unit) {
        onIconDeleteClickListener = listener
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