package com.example.arijit.githubbrowser.fragments.repolist

import android.app.Application
import androidx.lifecycle.*
import com.example.arijit.githubbrowser.R
import com.example.arijit.githubbrowser.database.GithubDatabase
import com.example.arijit.githubbrowser.database.getDatabase
import com.example.arijit.githubbrowser.models.GithubRepoData
import com.example.arijit.githubbrowser.network.ERROR_404
import com.example.arijit.githubbrowser.network.GithubNetwork
import com.example.arijit.githubbrowser.repository.FavRepoRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.http.HTTP

class RepoListViewModel(private val app: Application): AndroidViewModel(app) {


    private val repository = FavRepoRepository(getDatabase(app.applicationContext))
    val repoList: LiveData<List<GithubRepoData>> = repository.favRepos

    private val _doneAdding: MutableLiveData<Boolean> = MutableLiveData(false)
    val doneAdding: LiveData<Boolean> = _doneAdding

    private val _message = MutableLiveData<String>(null)
    val message: LiveData<String> = _message

    fun checkOkForm(owner: String?, repoName: String?): Boolean {
        if(owner.isNullOrEmpty() || repoName.isNullOrEmpty())
            return false
        return true
    }

    fun navigateToAddFrag() {
        _doneAdding.value = false
    }

    fun addNewRepo(owner: String, repoName: String) {
        viewModelScope.launch {
            try {
                repository.getRepoFromNetwork(owner, repoName)
                _message.value = null
                _doneAdding.value = true
            }
            catch (e: HttpException) {
                _message.value = when (e.code()) {
                    ERROR_404 -> app.applicationContext.getString(R.string.error_404)
                    else -> null
                }
            }
            catch (e: Exception) {
                e.printStackTrace()
                _message.value = e.localizedMessage
            }
        }
    }

    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepoListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepoListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
