package com.example.hackernewsapp.network.dto

import com.example.hackernewsapp.model.CommentModel
import com.example.hackernewsapp.model.StoryModel
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Edoardo Di Chiara
 */
data class ItemDTO(
    @SerializedName("by")
    val by: String,
    @SerializedName("descendants")
    val descendants: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("kids")
    val kids: List<Int>?,
    @SerializedName("score")
    val score: Int,
    @SerializedName("time")
    val time: Long,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String?,
    @SerializedName("parent")
    val parent: Int?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("poll")
    val poll: String?,
    @SerializedName("dead")
    val dead: Boolean?
) {
    fun toStoryDomain(): StoryModel {
        return StoryModel(
            author = this.by,
            totalCommentsCount = this.descendants,
            id = this.id,
            idComments = this.kids.orEmpty(),
            score = this.score,
            time = Date(this.time * 1000),
            title = this.title,
            type = this.type,
            url = this.url ?: ""

        )
    }

    fun toCommentDomain(): CommentModel {
        return CommentModel(
            id = this.id,
            by = this.by,
            subComment = this.kids.orEmpty(),
            score = this.score,
            parent = this.parent ?: 0,
            text = this.text ?: String(),
            time = Date(this.time * 1000),
            type = this.type,
            dead = this.dead
        )
    }
}