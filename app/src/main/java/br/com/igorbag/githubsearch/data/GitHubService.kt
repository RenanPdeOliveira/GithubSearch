package br.com.igorbag.githubsearch.data

import br.com.igorbag.githubsearch.domain.RepositoryItem
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{user}/repos")
    fun getAllRepositoriesByUser(@Path("user") user: String): List<RepositoryItem>

}
