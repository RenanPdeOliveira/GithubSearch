package br.com.igorbag.githubsearch.data.repository

import br.com.igorbag.githubsearch.data.remote.GitHubService
import br.com.igorbag.githubsearch.domain.repository.AppRepository
import br.com.igorbag.githubsearch.domain.RepositoryItem
import br.com.igorbag.githubsearch.domain.util.Resource
import kotlinx.coroutines.CancellationException

class AppRepositoryImpl(
    private val gitHubService: GitHubService
): AppRepository {
    override suspend fun getAllRepositoriesByUser(user: String): Resource<List<RepositoryItem>> {
        return try {
            Resource.Success(data = gitHubService.getAllRepositoriesByUser(user))
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            e.printStackTrace()
            Resource.Error(e.message ?: "Api not respond!")
        }
    }
}