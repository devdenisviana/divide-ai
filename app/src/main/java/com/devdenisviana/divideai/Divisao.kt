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