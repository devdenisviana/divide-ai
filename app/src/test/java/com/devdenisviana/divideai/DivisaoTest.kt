package com.devdenisviana.divideai

import org.junit.Assert.assertEquals
import org.junit.Test

class DivisaoTest {

    @Test
    fun divideContaSemGorjeta() {
        val r = calcularDivisao(10000, 4, 0)
        assertEquals(10000L, r.totalComGorjeta)
        assertEquals(2500L, r.valorPorPessoa)
        assertEquals(0L, r.sobra)
    }

    @Test
    fun aplicaGorjetaDeDezPorCento() {
        val r = calcularDivisao(10000, 2, 10)
        assertEquals(11000L, r.totalComGorjeta)
        assertEquals(5500L, r.valorPorPessoa)
    }

    @Test
    fun guardaSobraDeCentavos() {
        val r = calcularDivisao(1000, 3, 0)
        assertEquals(333L, r.valorPorPessoa)
        assertEquals(1L, r.sobra)
    }

    @Test
    fun consumoDivideDiferencaIgualmente() {
        val r = calcularPorConsumo(20000, listOf(8000L, 5000L, 4000L), 10)
        assertEquals(3000L, r.diferenca)
        assertEquals(22000L, r.totalComGorjeta)
        assertEquals(listOf(9900L, 6600L, 5500L), r.valoresPorPessoa)
    }

    @Test
    fun consumoFechaExatoComCentavos() {
        val r = calcularPorConsumo(10000, listOf(3333L, 3333L, 3333L), 10)
        assertEquals(11000L, r.valoresPorPessoa.sum())
        assertEquals(11000L, r.totalComGorjeta)
    }

    @Test
    fun consumoMaiorQueAContaDevolveListaVazia() {
        val r = calcularPorConsumo(5000, listOf(6000L), 10)
        assertEquals(-1000L, r.diferenca)
        assertEquals(emptyList<Long>(), r.valoresPorPessoa)
    }
}