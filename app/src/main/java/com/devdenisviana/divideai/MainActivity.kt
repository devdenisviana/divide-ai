package com.devdenisviana.divideai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                    Column(modifier = Modifier.padding(innerPadding)) {
                        if (tela != Tela.INICIO) {
                            TextButton(
                                onClick = { tela = Tela.INICIO },
                                modifier = Modifier.padding(horizontal = 8.dp)
                            ) {
                                Text("← Voltar")
                            }
                        }

                        val conteudo = Modifier.weight(1f)
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
}