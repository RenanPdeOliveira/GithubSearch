package br.com.igorbag.githubsearch.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.igorbag.githubsearch.data.RetrofitApi
import br.com.igorbag.githubsearch.databinding.ActivityMainBinding
import br.com.igorbag.githubsearch.ui.adapter.RepositoryAdapter
import br.com.igorbag.githubsearch.ui.util.UiEvent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var adapter = RepositoryAdapter(::openBrowser)

    private val viewModel: RepositoryViewModel by viewModels {
        RepositoryViewModel.provideViewModelFactory(RetrofitApi.provideGithubService())
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

            viewModel.repositoryList.collect { list ->
                adapter.submitList(list)
            }
        }
        //showUserName()
        setupRetrofit()
        //getAllReposByUserName()
        binding.btnConfirm.setOnClickListener {
            val user = binding.etUserName.text.toString()
            viewModel.onEvent(MainEvent.OnConfirmClick(user))
        }
    }

    //metodo responsavel por configurar os listeners click da tela
    /*private fun setupListeners() {
        binding.btnConfirm.setOnClickListener {
            val user = binding.etUserName.text.toString()
            viewModel.onEvent(MainEvent.OnConfirmClick(user))
        }
    }*/


    // salvar o usuario preenchido no EditText utilizando uma SharedPreferences
    /*private fun saveUserLocal() {
        //@TODO 3 - Persistir o usuario preenchido na editText com a SharedPref no listener do botao salvar
    }

    private fun showUserName() {
        //@TODO 4- depois de persistir o usuario exibir sempre as informacoes no EditText  se a sharedpref possuir algum valor, exibir no proprio editText o valor salvo
    }*/

    //Metodo responsavel por fazer a configuracao base do Retrofit
    private fun setupRetrofit() {
        RetrofitApi.provideGithubService()
    }

    //Metodo responsavel por buscar todos os repositorios do usuario fornecido
    /*fun getAllReposByUserName() {
        // TODO 6 - realizar a implementacao do callback do retrofit e chamar o metodo setupAdapter se retornar os dados com sucesso
    }*/

    // Metodo responsavel por realizar a configuracao do adapter
    /*fun setupAdapter(list: List<RepositoryItem>) {
        binding.rvRepositories.adapter = adapter
    }*/


    // Metodo responsavel por compartilhar o link do repositorio selecionado
    // @Todo 11 - Colocar esse metodo no click do share item do adapter
    /*fun shareRepositoryLink(urlRepository: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }*/

    private fun openBrowser(urlRepository: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository)
            )
        )

    }

}