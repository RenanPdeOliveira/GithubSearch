package br.com.igorbag.githubsearch.data.remote

import br.com.igorbag.githubsearch.domain.RepositoryItem
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {

    @GET("users/{user}/repos")
    suspend fun getAllRepositoriesByUser(@Path("user") user: String): List<RepositoryItem>

}
