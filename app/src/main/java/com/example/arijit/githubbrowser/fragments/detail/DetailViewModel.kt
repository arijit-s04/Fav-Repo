package com.example.arijit.githubbrowser.fragments.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.arijit.githubbrowser.R
import com.example.arijit.githubbrowser.database.getDatabase
import com.example.arijit.githubbrowser.models.BranchData
import com.example.arijit.githubbrowser.models.CommitData
import com.example.arijit.githubbrowser.models.GithubRepoData
import com.example.arijit.githubbrowser.models.IssueData
import com.example.arijit.githubbrowser.network.GithubNetwork
import com.example.arijit.githubbrowser.repository.FavRepoRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val app: Application): AndroidViewModel(app) {

    private val repository = FavRepoRepository(getDatabase(app.applicationContext))

    private val _branchList = MutableLiveData<List<BranchData>>()
    val branchList: LiveData<List<BranchData>> = _branchList

    private val _currentRepo = MutableLiveData<GithubRepoData>(null)
    val currentRepo: LiveData<GithubRepoData> = _currentRepo

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _commitList = MutableLiveData<List<CommitData>>()
    val commitList: LiveData<List<CommitData>> = _commitList

    private val _issueList = MutableLiveData<List<IssueData>>()
    val issueList: LiveData<List<IssueData>> = _issueList

    fun setCurrentRepo(repo: GithubRepoData?) {
            if(repo == null) {
                reset()
                return
            }
        viewModelScope.launch {
            _currentRepo.value = repo
            try {
                _branchList.value = repository.getBranchListFromNet(
                    currentRepo.value?.owner?.login!!, currentRepo.value?.name!!
                )
            } catch (e: Exception) {
                _message.value = app.applicationContext.getString(R.string.error_message)
                e.printStackTrace()
                _message.value = null
            }

            try {
                _issueList.value = GithubNetwork.networkRepo.getIssueList(
                    currentRepo.value?.owner?.login!!, currentRepo.value?.name!!, "open"
                )
            } catch (e: Exception) {
                _message.value = app.applicationContext.getString(R.string.error_message)
                e.printStackTrace()
                _message.value = null
            }

        }
    }

    private fun reset() {
        _currentRepo.value = null
        _branchList.value = null
        _commitList.value = null
        _issueList.value = null
    }

    fun navigateToCommit(branch: String) {
        viewModelScope.launch {
            try {
                _commitList.value = GithubNetwork.networkRepo.getCommitList(
                    _currentRepo.value?.owner?.login!!,
                    _currentRepo.value?.name!!,
                    branch
                )
            } catch (e: Exception) {
                _message.value = app.applicationContext.getString(R.string.error_message)
                e.printStackTrace()
                _message.value = null
            }
        }
    }

    fun doneNavigateToCommit() { _commitList.value = null }

    fun deleteRepo() {
        viewModelScope.launch {
            try {
                repository.deleteRepo(currentRepo.value)
                reset()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun doneDetailing() {
        reset()
    }

    class Factory(private val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}