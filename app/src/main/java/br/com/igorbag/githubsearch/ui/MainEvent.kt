package br.com.igorbag.githubsearch.ui

sealed class MainEvent {
    data class OnConfirmClick(val user: String): MainEvent()
}