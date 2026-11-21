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
}