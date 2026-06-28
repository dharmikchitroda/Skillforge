package com.example.assignmentskillforge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmentskillforge.data.SkillforgeResponse
import com.example.assignmentskillforge.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val data: SkillforgeResponse) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

class HomeViewModel(
    private val repository: Repository = Repository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Change this URL to your real API endpoint if needed.
        // If left empty, it will fall back to local mock data.
        loadData(url = "")
    }

    fun loadData(url: String) {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            repository.getSkillforgeData(url)
                .onSuccess { response ->
                    _uiState.value = HomeUiState.Success(response)
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState.Error(error.localizedMessage ?: "Unknown Error occurred")
                }
        }
    }
}
