package com.example.caravanasfutebol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.caravanasfutebol.model.Caravana
import com.example.caravanasfutebol.repository.CaravanaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CaravanaViewModel : ViewModel() {
    private val repository = CaravanaRepository()

    private val _caravanas = MutableStateFlow<List<Caravana>>(emptyList())
    val caravanas: StateFlow<List<Caravana>> = _caravanas

    init {
        observarCaravanasEmTempoReal()
    }

    private fun observarCaravanasEmTempoReal() {
        viewModelScope.launch {
            repository.observarCaravanas().collect { lista ->
                _caravanas.value = lista
            }
        }
    }

    fun salvarCaravana(id: String, estadio: String, rota: String, passageiros: Int, valor: Double, horario: String) {
        viewModelScope.launch {
            val caravana = Caravana(
                id = id,
                estadio = estadio,
                rota = rota,
                passageirosConfirmados = passageiros,
                valorPagamento = valor,
                horarioPartida = horario
            )
            if (id.isEmpty()) {
                repository.adicionarCaravana(caravana)
            } else {
                repository.atualizarCaravana(caravana)
            }
        }
    }

    fun deletarCaravana(id: String) {
        viewModelScope.launch {
            repository.deletarCaravana(id)
        }
    }
}