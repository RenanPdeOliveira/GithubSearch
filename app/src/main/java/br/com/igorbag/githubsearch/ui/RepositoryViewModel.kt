package br.com.igorbag.githubsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.igorbag.githubsearch.data.GitHubService
import br.com.igorbag.githubsearch.data.repository.AppRepositoryImpl
import br.com.igorbag.githubsearch.domain.RepositoryItem
import br.com.igorbag.githubsearch.domain.repository.AppRepository
import br.com.igorbag.githubsearch.domain.util.Resource
import br.com.igorbag.githubsearch.ui.util.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RepositoryViewModel(
    private val repository: AppRepository
): ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _repositoryList = MutableStateFlow<List<RepositoryItem>?>(emptyList())
    val repositoryList: StateFlow<List<RepositoryItem>?> = _repositoryList.asStateFlow()

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnConfirmClick -> {
                getRepositoryList(user = event.user)
            }
        }
    }

    private fun getRepositoryList(user: String) = viewModelScope.launch {
        if (user.isNotBlank()) {
            val result = repository.getAllRepositoriesByUser(user)
            when (result) {
                is Resource.Success -> {
                    _uiEvent.send(UiEvent.ShowSnackBar("Success on loading list!"))
                    _repositoryList.value = result.data
                }

                is Resource.Error -> {
                    _uiEvent.send(UiEvent.ShowSnackBar("Could not load the list!"))
                }
            }
        } else {
            _uiEvent.send(UiEvent.ShowSnackBar("Please, fill in user name field!"))
        }
    }

    companion object {
        fun provideViewModelFactory(gitHubService: GitHubService): ViewModelProvider.Factory {
            val repository = AppRepositoryImpl(gitHubService)
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RepositoryViewModel(repository) as T
                }
            }
        }
    }
}