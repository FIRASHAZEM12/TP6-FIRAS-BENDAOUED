package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsphotos.MarsApi
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface MarsUiState {
    data class Success(val message: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {
    /** Le State mutable qui stocke l'état de la dernière requête */
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    /**
     * Appelle getMarsPhotos() à l'initialisation pour afficher l'état immédiatement.
     */
    init {
        getMarsPhotos()
    }

    /**
     * Récupère les informations des photos de Mars depuis l'API Mars et met à jour
     * [marsUiState] avec la réponse ou un message d'erreur.
     */
    fun getMarsPhotos() {
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()
                marsUiState = MarsUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                marsUiState = MarsUiState.Error
            }
        }
    }
}
