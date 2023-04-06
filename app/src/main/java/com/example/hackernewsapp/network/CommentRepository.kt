package com.example.hackernewsapp.network

import com.example.hackernewsapp.model.CommentModel
import javax.inject.Inject

class CommentRepository @Inject constructor(private val api: NetworkObject) {

    suspend fun getListOfCommentFromId(id: Int): List<CommentModel> {
        val response = api.service.getItemFromId(id).body()?.kids ?: emptyList()
        var listOfComment = response.mapNotNull {
            api.service.getItemFromId(it).body()?.toCommentDomain()
        }
        listOfComment = listOfComment.toMutableList().apply { removeIf { it.dead == true } }
        return listOfComment
    }


}