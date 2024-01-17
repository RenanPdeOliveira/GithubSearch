package br.com.igorbag.githubsearch.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.igorbag.githubsearch.data.remote.RetrofitModule
import br.com.igorbag.githubsearch.databinding.ActivityMainBinding
import br.com.igorbag.githubsearch.ui.adapter.RepositoryAdapter
import br.com.igorbag.githubsearch.ui.event.MainEvent
import br.com.igorbag.githubsearch.ui.util.UiEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var adapter = RepositoryAdapter(openLink = ::openBrowser, shareLink = ::shareRepositoryLink)

    private val viewModel: RepositoryViewModel by viewModels {
        RepositoryViewModel.provideViewModelFactory(RetrofitModule.provideGithubService())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvRepositories.adapter = adapter
        lifecycleScope.launch {
            viewModel.uiEvent.collect { event ->
                when (event) {
                    is UiEvent.ShowSnackBar -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
        setupRetrofit()
        binding.btnConfirm.setOnClickListener {
            val user = binding.etUserName.text.toString()
            viewModel.onEvent(MainEvent.OnConfirmClick(user))

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.RESUMED) {
                    viewModel.repositoryList.collect { list ->
                        if (list?.size != 0) {
                            adapter.submitList(list)
                            binding.linearEmptyState.visibility = View.GONE
                            binding.rvRepositories.visibility = View.VISIBLE
                        } else {
                            binding.linearEmptyState.visibility = View.VISIBLE
                            binding.rvRepositories.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun setupRetrofit() {
        RetrofitModule.provideGithubService()
    }

    private fun shareRepositoryLink(url: String) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, url)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(intent, null)
        startActivity(shareIntent)
    }

    private fun openBrowser(url: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        )

    }

}