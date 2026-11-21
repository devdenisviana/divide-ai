package com.devdenisviana.divideai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devdenisviana.divideai.ui.theme.DivideAiTheme

enum class Tela { INICIO, IGUAL, CONSUMO }

class MainActivity : ComponentActivity() {
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
                        Tela.CONSUMO -> Text(
                            text = "Em construção",
                            modifier = conteudo.padding(24.dp)
                        )
                    }
                }
            }
        }
    }
}