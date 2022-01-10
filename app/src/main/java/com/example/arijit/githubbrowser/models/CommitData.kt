package com.example.arijit.githubbrowser.models


data class Author(
    val name: String,
    val date: String
)

data class Commit(
    val message: String,
    val author: Author
)


data class CommitData(
    val sha: String,
    val commit: Commit,
    val author: OwnerDetails?
)