package br.com.igorbag.githubsearch.domain.repository

import br.com.igorbag.githubsearch.domain.RepositoryItem
import br.com.igorbag.githubsearch.domain.util.Resource
import retrofit2.http.Path

interface AppRepository {

    fun getAllRepositoriesByUser(@Path("user") user: String): Resource<List<RepositoryItem>>
}