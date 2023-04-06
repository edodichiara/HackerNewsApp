package com.example.hackernewsapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hackernewsapp.databinding.CommentItemBinding
import com.example.hackernewsapp.model.CommentModel
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

class CommentListAdapter(private val listOfComments: List<CommentModel>) :
    RecyclerView.Adapter<CommentListAdapter.CommentItemViewHolder>() {

    class CommentItemViewHolder(var binding: CommentItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentItemViewHolder {
        val view = CommentItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentItemViewHolder(view)
    }

    override fun getItemCount(): Int = listOfComments.size

    override fun onBindViewHolder(holder: CommentItemViewHolder, position: Int) {
        val simpleDateFormat = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
        with(holder) {
            with(listOfComments[position]) {
                holder.binding.commentText.text = Jsoup.parse(this.text).text()
                if(this.by == ""){
                    binding.author.visibility = View.GONE
                } else {
                    binding.author.visibility= View.VISIBLE
                    binding.author.text = this.by
                }
                holder.binding.dateTime.text = simpleDateFormat.format(this.time)
            }
        }
    }
}