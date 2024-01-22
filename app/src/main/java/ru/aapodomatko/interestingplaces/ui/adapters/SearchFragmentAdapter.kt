package ru.aapodomatko.interestingplaces.ui.adapters

import android.text.Html
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
import ru.aapodomatko.interestingplaces.models.search.Result

class SearchFragmentAdapter : RecyclerView.Adapter<SearchFragmentAdapter.SearchViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchViewHolder {
        return SearchViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.search_item_container, parent, false
        ))
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.itemView.apply {
            val titleTextView = findViewById<TextView>(R.id.search_item_title)
            val imageView = findViewById<ImageView>(R.id.search_item_image)
            val descriptionTextView = findViewById<TextView>(R.id.search_item_description)
            Glide.with(this).load(item.first_image.image).into(imageView)
            titleTextView.text = item.title.replaceFirstChar { it.uppercase() }
            descriptionTextView.text = Html.fromHtml(item.description, Html.FROM_HTML_MODE_LEGACY)

            setOnClickListener {
                onItemClickListener?.let { it(item) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener
    }

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view)

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