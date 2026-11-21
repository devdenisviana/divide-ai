package com.devdenisviana.divideai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.devdenisviana.divideai.ui.theme.DivideAiTheme

enum class Tela { INICIO, IGUAL, CONSUMO }

class MainActivity : ComponentActivity() {
    private val consumoViewModel: ConsumoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DivideAiTheme {
                var tela by rememberSaveable { mutableStateOf(Tela.INICIO) }

                BackHandler(enabled = tela != Tela.INICIO) {
                    tela = Tela.INICIO
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val conteudo = Modifier.padding(innerPadding)
                    when (tela) {
                        Tela.INICIO -> TelaInicio(
                            aoEscolherIgual = { tela = Tela.IGUAL },
                            aoEscolherConsumo = { tela = Tela.CONSUMO },
                            modifier = conteudo
                        )
                        Tela.IGUAL -> TelaDivisao(modifier = conteudo)
                        Tela.CONSUMO -> TelaConsumo(
                            viewModel = consumoViewModel,
                            modifier = conteudo
                        )
                    }
                }
            }
        }
    }
}