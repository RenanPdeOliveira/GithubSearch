package br.com.igorbag.githubsearch.domain.repository

import br.com.igorbag.githubsearch.domain.RepositoryItem
import br.com.igorbag.githubsearch.domain.util.Resource

interface AppRepository {

    suspend fun getAllRepositoriesByUser(user: String): Resource<List<RepositoryItem>>
}