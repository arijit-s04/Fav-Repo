package com.example.arijit.githubbrowser.models

import com.squareup.moshi.Json

data class BranchData(
    @Json(name = "name") val branchName: String
)
