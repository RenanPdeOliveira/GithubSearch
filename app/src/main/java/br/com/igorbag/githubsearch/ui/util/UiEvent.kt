package br.com.igorbag.githubsearch.ui.util

sealed class UiEvent {
    data class ShowSnackBar(val message: String) : UiEvent()
}