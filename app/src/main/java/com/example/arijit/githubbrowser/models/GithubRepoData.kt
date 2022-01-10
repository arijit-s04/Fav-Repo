package com.example.arijit.githubbrowser.models

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OwnerDetails(
    @Json(name = "id") val ownerId: Int,
    @Json(name = "login") val login: String,
    @Json(name = "avatar_url") val dpUrl: String?
): Parcelable

@Parcelize
data class GithubRepoData(
    val id: Int = 0,
    val name: String,
    val owner: OwnerDetails,
    @Json(name = "html_url") val url:String,
    val description: String?
): Parcelable {
    override fun toString(): String {
        return "Id: $id Name: $name Owner: ${owner.login}"
    }
}