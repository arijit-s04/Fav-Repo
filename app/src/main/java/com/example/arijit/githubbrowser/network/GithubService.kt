package com.example.arijit.githubbrowser.network

import com.example.arijit.githubbrowser.models.BranchData
import com.example.arijit.githubbrowser.models.CommitData
import com.example.arijit.githubbrowser.models.GithubRepoData
import com.example.arijit.githubbrowser.models.IssueData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.github.com/"

const val ERROR_404 = 404

interface GithubService {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoFromNetwork(
        @Path("owner")owner: String,
        @Path("repo") repo: String
    ): GithubRepoData

    @GET("repos/{owner}/{repo}/branches")
    suspend fun getBranchList(
        @Path("owner")owner: String,
        @Path("repo") repo: String
    ): List<BranchData>

    @GET("repos/{owner}/{repo}/commits")
    suspend fun getCommitList(
        @Path("owner")owner: String,
        @Path("repo") repo: String,
        @Query("sha") branch: String
    ): List<CommitData>

    @GET("repos/{owner}/{repo}/issues")
    suspend fun getIssueList(
        @Path("owner")owner: String,
        @Path("repo") repo: String,
        @Query("state") state: String
    ): List<IssueData>

}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object GithubNetwork {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val networkRepo: GithubService = retrofit.create(GithubService::class.java)
}