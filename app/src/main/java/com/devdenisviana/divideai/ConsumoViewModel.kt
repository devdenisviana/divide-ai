package com.devdenisviana.divideai

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/** Aceita só números e uma vírgula com até 2 casas. Devolve null se for inválido. */
fun filtrarValor(novo: String): String? {
    val limpo = novo.replace('.', ',')
    val valido = limpo.length <= 9 &&
            limpo.all { it.isDigit() || it == ',' } &&
            limpo.count { it == ',' } <= 1 &&
            limpo.substringAfter(',', "").length <= 2
    return if (valido) limpo else null
}

class PessoaConsumo(nome: String, consumo: String = "") {
    var nome by mutableStateOf(nome)
    var consumo by mutableStateOf(consumo)
}

class ConsumoViewModel : ViewModel() {
    var totalTexto by mutableStateOf("")
        private set
    var gorjeta by mutableIntStateOf(10)
        private set
    val pessoas = mutableStateListOf(
        PessoaConsumo("Pessoa 1"),
        PessoaConsumo("Pessoa 2")
    )

    fun atualizarTotal(novo: String) {
        filtrarValor(novo)?.let { totalTexto = it }
    }

    fun escolherGorjeta(percentual: Int) {
        gorjeta = percentual
    }

    fun atualizarNome(indice: Int, novo: String) {
        if (novo.length <= 20) pessoas[indice].nome = novo
    }

    fun atualizarConsumo(indice: Int, novo: String) {
        filtrarValor(novo)?.let { pessoas[indice].consumo = it }
    }

    fun adicionarPessoa() {
        if (pessoas.size < 30) {
            pessoas.add(PessoaConsumo("Pessoa ${pessoas.size + 1}"))
        }
    }

    fun removerPessoa(indice: Int) {
        if (pessoas.size > 1) pessoas.removeAt(indice)
    }

    fun resultado(): ResultadoConsumo = calcularPorConsumo(
        textoParaCentavos(totalTexto),
        pessoas.map { textoParaCentavos(it.consumo) },
        gorjeta
    )
}