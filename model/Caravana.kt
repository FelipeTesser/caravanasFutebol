package com.example.caravanasfutebol.model

data class Caravana(
    val id: String = "",
    val userId: String = "", // Guarda o ID do usuário dono
    val estadio: String = "",
    val rota: String = "",
    val passageirosConfirmados: Int = 0,
    val valorPagamento: Double = 0.0,
    val horarioPartida: String = ""
)