package com.devdenisviana.divideai

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TelaInicio(
    aoEscolherIgual: () -> Unit,
    aoEscolherConsumo: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Divide Aí",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Como você quer dividir a conta?",
            style = MaterialTheme.typography.titleMedium
        )

        Button(onClick = aoEscolherIgual, modifier = Modifier.fillMaxWidth()) {
            Text("Dividir por igual")
        }
        Text(
            text = "Todos pagam o mesmo valor",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = aoEscolherConsumo, modifier = Modifier.fillMaxWidth()) {
            Text("Dividir por consumo")
        }
        Text(
            text = "Cada um paga o que consumiu",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}