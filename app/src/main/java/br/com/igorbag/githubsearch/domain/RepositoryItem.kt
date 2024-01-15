package br.com.igorbag.githubsearch.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryItem(
    val name: String,
    @SerializedName("html_url")
    val htmlUrl: String
) : Parcelable