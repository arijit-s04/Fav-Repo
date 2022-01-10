package com.example.arijit.githubbrowser.models

data class IssueData(
    val id: Int,
    val title: String,
    val user: OwnerDetails?
)