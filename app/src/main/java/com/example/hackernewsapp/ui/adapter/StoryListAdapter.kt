package com.example.hackernewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewsapp.R
import com.example.hackernewsapp.databinding.StoryItemBinding
import com.example.hackernewsapp.model.StoryModel
import java.text.SimpleDateFormat
import java.util.*

class StoryListAdapter(private val listOfStories: List<StoryModel>) : RecyclerView.Adapter<StoryListAdapter.ItemViewHolder>() {
    class ItemViewHolder(var binding: StoryItemBinding): RecyclerView.ViewHolder(binding.root)

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
        with(holder){
            with(position){
                holder.binding.apply {
                    title.text = itemView.context.getString(R.string.title, listOfStories[position].title)
                    author.text = itemView.context.getString(R.string.author, listOfStories[position].author)
                    date.text = itemView.context.getString(R.string.date, simpleDateFormat.format(listOfStories[position].time))
                    numbersOfLike.text = itemView.context.getString(R.string.numbersOfLike, listOfStories[position].score)
                }
            }
        }
    }
}