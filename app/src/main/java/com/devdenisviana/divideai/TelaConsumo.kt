package com.devdenisviana.divideai

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun TelaConsumo(
    viewModel: ConsumoViewModel,
    modifier: Modifier = Modifier
) {
    val contexto = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Dividir por consumo",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        OutlinedTextField(
            value = viewModel.totalTexto,
            onValueChange = { viewModel.atualizarTotal(it) },
            label = { Text("Valor total da conta (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        Text("Gorjeta", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(0, 10, 12, 15).forEach { opcao ->
                FilterChip(
                    selected = viewModel.gorjeta == opcao,
                    onClick = { viewModel.escolherGorjeta(opcao) },
                    label = { Text("$opcao%") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondary,
                        selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
        }

        Text("Pessoas e consumo", style = MaterialTheme.typography.titleMedium)
        viewModel.pessoas.forEachIndexed { indice, pessoa ->
            key(pessoa) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = pessoa.nome,
                        onValueChange = { viewModel.atualizarNome(indice, it) },
                        label = { Text("Nome") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = pessoa.consumo,
                        onValueChange = { viewModel.atualizarConsumo(indice, it) },
                        label = { Text("R$") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.width(110.dp)
                    )
                    TextButton(onClick = { viewModel.removerPessoa(indice) }) {
                        Text("✕")
                    }
                }
            }
        }

        Button(onClick = { viewModel.adicionarPessoa() }) {
            Text("+ Adicionar pessoa")
        }

        val resultado = viewModel.resultado()
        if (resultado.diferenca < 0) {
            Text(
                text = "Os consumos passam do total da conta em " +
                        formatarReais(-resultado.diferenca),
                color = MaterialTheme.colorScheme.error
            )
        } else {
            Text("Total com gorjeta: ${formatarReais(resultado.totalComGorjeta)}")
            if (resultado.diferenca > 0) {
                Text(
                    "Itens compartilhados: ${formatarReais(resultado.diferenca)} " +
                            "(dividido igualmente)"
                )
            }
            Text("Cada pessoa paga", style = MaterialTheme.typography.titleMedium)
            viewModel.pessoas.forEachIndexed { indice, pessoa ->
                Text(
                    text = "${pessoa.nome}: ${formatarReais(resultado.valoresPorPessoa[indice])}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Button(
                onClick = {
                    val resumo = buildString {
                        appendLine("Divide Aí 🍻")
                        appendLine(
                            "Conta com gorjeta: ${formatarReais(resultado.totalComGorjeta)} " +
                                    "(gorjeta de ${viewModel.gorjeta}%)"
                        )
                        if (resultado.diferenca > 0) {
                            appendLine(
                                "Itens compartilhados: ${formatarReais(resultado.diferenca)} " +
                                        "(dividido igualmente)"
                            )
                        }
                        viewModel.pessoas.forEachIndexed { indice, pessoa ->
                            appendLine(
                                "${pessoa.nome}: ${formatarReais(resultado.valoresPorPessoa[indice])}"
                            )
                        }
                    }.trim()
                    val envio = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, resumo)
                    }
                    contexto.startActivity(Intent.createChooser(envio, "Compartilhar resumo"))
                },
                enabled = resultado.totalComGorjeta > 0
            ) { Text("Compartilhar resumo") }
        }
    }
}