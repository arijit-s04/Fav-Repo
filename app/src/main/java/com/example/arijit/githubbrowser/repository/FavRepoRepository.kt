package com.example.arijit.githubbrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.arijit.githubbrowser.database.GithubDatabase
import com.example.arijit.githubbrowser.database.asDatabaseModel
import com.example.arijit.githubbrowser.database.asDomainModel
import com.example.arijit.githubbrowser.models.BranchData
import com.example.arijit.githubbrowser.models.GithubRepoData
import com.example.arijit.githubbrowser.network.GithubNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavRepoRepository(private val database: GithubDatabase) {

    val favRepos: LiveData<List<GithubRepoData>> =
        Transformations.map(database.databaseDao.getAllFavRepo()) {
            it.asDomainModel()
        }

    suspend fun getRepoFromNetwork(owner: String, repoName: String) {
        val newRepo = GithubNetwork.networkRepo.getRepoFromNetwork(owner, repoName)
        withContext(Dispatchers.IO) {
            database.databaseDao.insertIntoFavRepo(newRepo.asDatabaseModel())
        }
    }

    suspend fun getBranchListFromNet(
        owner: String, repoName: String): List<BranchData>
            = GithubNetwork.networkRepo.getBranchList(owner, repoName)

    suspend fun deleteRepo(repo: GithubRepoData?) {
        if(repo == null)
            return

        withContext(Dispatchers.IO) {
            database.databaseDao.deleteFavRepoBye(repo.asDatabaseModel())
        }

    }

}