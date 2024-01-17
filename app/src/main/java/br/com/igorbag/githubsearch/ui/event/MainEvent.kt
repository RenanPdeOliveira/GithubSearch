package br.com.igorbag.githubsearch.ui.event

sealed class MainEvent {
    data class OnConfirmClick(val user: String): MainEvent()
}