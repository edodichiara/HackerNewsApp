package com.example.hackernewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.StoryItemBinding
import com.example.hackernewsapp.model.StoryModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Edoardo Di Chiara
 */
class StoryListAdapter(
    private val listOfStories: List<StoryModel>,
    private val listOfFavouriteId: List<Int>,
    private val onCheckedToggleButtonClick: (Int) -> Unit,
    private val onUncheckedToggleButtonClick: (Int) -> Unit,
    private val onShowCommentsClick: (Int) -> Unit,
    private val onShowWebsiteCLick: (StoryModel) -> Unit
) : RecyclerView.Adapter<StoryListAdapter.ItemViewHolder>() {
    class ItemViewHolder(var binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = StoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listOfStories.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        with(holder) {
            with(listOfStories[position]) {
                holder.binding.apply {
                    title.text =
                        itemView.context.getString(R.string.title, listOfStories[position].title)
                    author.text =
                        itemView.context.getString(
                            R.string.author,
                            listOfStories[position].type.lowercase().replaceFirstChar { char ->
                                char.titlecase(Locale.getDefault())
                            },
                            listOfStories[position].author
                        )
                    date.text = itemView.context.getString(
                        R.string.date,
                        simpleDateFormat.format(listOfStories[position].time)
                    )
                    numbersOfLike.text = itemView.context.getString(
                        R.string.numbersOfLike,
                        listOfStories[position].score
                    )
                    numbersOfComment.text = itemView.context.getString(
                        R.string.numbers_of_comment,
                        listOfStories[position].totalCommentsCount
                    )
                }
                binding.showNews.setOnClickListener {
                    onShowWebsiteCLick(this)
                }
                binding.showComments.setOnClickListener {
                    onShowCommentsClick(this.id)
                }
                binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
                    when (isChecked) {
                        true -> onCheckedToggleButtonClick(listOfStories[position].id)
                        false -> onUncheckedToggleButtonClick(listOfStories[position].id)
                    }
                }
                binding.toggleButton.isChecked =
                    listOfFavouriteId.contains(listOfStories[position].id)
            }
        }
    }
}