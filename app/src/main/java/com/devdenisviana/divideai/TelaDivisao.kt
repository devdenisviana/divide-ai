package com.devdenisviana.divideai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

fun textoParaCentavos(texto: String): Long {
    val partes = texto.split(",")
    val reais = partes[0].toLongOrNull() ?: 0L
    val decimais = (partes.getOrNull(1) ?: "").padEnd(2, '0').take(2)
    return reais * 100 + (decimais.toLongOrNull() ?: 0L)
}

fun formatarReais(centavos: Long): String =
    NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(centavos / 100.0)

@Composable
fun TelaDivisao(modifier: Modifier = Modifier) {
    var valorTexto by rememberSaveable { mutableStateOf("") }
    var pessoas by rememberSaveable { mutableIntStateOf(2) }
    var gorjeta by rememberSaveable { mutableIntStateOf(10) }
    val contexto = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Divide Aí",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        OutlinedTextField(
            value = valorTexto,
            onValueChange = { novo ->
                val limpo = novo.replace('.', ',')
                val valido = limpo.length <= 9 &&
                        limpo.all { it.isDigit() || it == ',' } &&
                        limpo.count { it == ',' } <= 1 &&
                        limpo.substringAfter(',', "").length <= 2
                if (valido) valorTexto = limpo
            },
            label = { Text("Valor da conta (R$)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = { if (pessoas > 1) pessoas-- }) { Text("−") }
            Text(
                text = if (pessoas == 1) "1 pessoa" else "$pessoas pessoas",
                style = MaterialTheme.typography.titleMedium
            )
            Button(onClick = { if (pessoas < 50) pessoas++ }) { Text("+") }
        }

        Text("Gorjeta", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(0, 10, 12, 15).forEach { opcao ->
                FilterChip(
                    selected = gorjeta == opcao,
                    onClick = { gorjeta = opcao },
                    label = { Text("$opcao%") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondary,
                        selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
        }

        val resultado = calcularDivisao(textoParaCentavos(valorTexto), pessoas, gorjeta)
        Text("Total com gorjeta: ${formatarReais(resultado.totalComGorjeta)}")
        Text("Cada pessoa paga", style = MaterialTheme.typography.titleMedium)
        Text(
            text = formatarReais(resultado.valorPorPessoa),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.secondary
        )
        if (resultado.sobra > 0) {
            Text("Sobram ${formatarReais(resultado.sobra)} na divisão")
        }

        Button(
            onClick = {
                val resumo = "Divide Aí 🍻\n" +
                        "Conta com gorjeta: ${formatarReais(resultado.totalComGorjeta)}\n" +
                        "$pessoas pessoas, gorjeta de $gorjeta%\n" +
                        "Cada um paga: ${formatarReais(resultado.valorPorPessoa)}"
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