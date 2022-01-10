package com.example.arijit.githubbrowser.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.arijit.githubbrowser.models.GithubRepoData
import com.example.arijit.githubbrowser.models.OwnerDetails

@Entity(tableName = "Fav_Repo")
data class DatabaseRepoObject constructor(
    @PrimaryKey val id: Int = 0,
    val repo_name: String,
    val owner: String,
    @ColumnInfo(name = "repo_url") val repoUrl: String,
    val description: String?
)

fun List<DatabaseRepoObject>.asDomainModel(): List<GithubRepoData> {
    return map {
        GithubRepoData(
            id = it.id,
            name = it.repo_name,
            description = it.description,
            owner = OwnerDetails(0, it.owner, ""),
            url = it.repoUrl
        )
    }
}

fun GithubRepoData.asDatabaseModel(): DatabaseRepoObject =
    DatabaseRepoObject(
        id = id,
        repo_name = name,
        owner = owner.login,
        description = description,
        repoUrl = url
    )
