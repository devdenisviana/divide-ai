package com.devdenisviana.divideai

data class ResultadoDivisao(
    val totalComGorjeta: Long,
    val valorPorPessoa: Long,
    val sobra: Long
)

fun calcularDivisao(
    totalCentavos: Long,
    pessoas: Int,
    gorjetaPercentual: Int
): ResultadoDivisao {
    require(pessoas > 0) { "Precisa de pelo menos 1 pessoa" }
    val gorjeta = (totalCentavos * gorjetaPercentual + 50) / 100
    val totalComGorjeta = totalCentavos + gorjeta
    return ResultadoDivisao(
        totalComGorjeta = totalComGorjeta,
        valorPorPessoa = totalComGorjeta / pessoas,
        sobra = totalComGorjeta % pessoas
    )
}

data class ResultadoConsumo(
    val valoresPorPessoa: List<Long>,
    val totalComGorjeta: Long,
    val diferenca: Long
)

fun calcularPorConsumo(
    totalCentavos: Long,
    consumos: List<Long>,
    gorjetaPercentual: Int
): ResultadoConsumo {
    require(consumos.isNotEmpty()) { "Precisa de pelo menos 1 pessoa" }
    val pessoas = consumos.size
    val diferenca = totalCentavos - consumos.sum()
    if (diferenca < 0) {
        return ResultadoConsumo(emptyList(), 0L, diferenca)
    }

    // 1) A diferença (itens compartilhados) é dividida igualmente
    val parteCada = diferenca / pessoas
    val resto = (diferenca % pessoas).toInt()
    val bases = consumos.mapIndexed { i, consumo ->
        consumo + parteCada + if (i < resto) 1 else 0
    }

    // 2) A gorjeta é proporcional ao valor de cada um, fechando exato
    val numeradores = bases.map { it * gorjetaPercentual }
    val gorjetas = numeradores.map { it / 100 }.toMutableList()
    val gorjetaTotal = (totalCentavos * gorjetaPercentual + 50) / 100
    val faltam = (gorjetaTotal - gorjetas.sum()).toInt()
    numeradores.withIndex()
        .sortedByDescending { it.value % 100 }
        .take(faltam)
        .forEach { gorjetas[it.index] += 1 }

    val valores = bases.indices.map { bases[it] + gorjetas[it] }
    return ResultadoConsumo(valores, totalCentavos + gorjetaTotal, diferenca)
}