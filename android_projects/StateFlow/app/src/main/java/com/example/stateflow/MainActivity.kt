package com.example.stateflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stateflow.ui.theme.StateFlowTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StateFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
//                        ProblemScreen(ProblemViewModel())
//                        SolutionScreen(SolutionViewModel())
                        Button(onClick = { viewModel.triggerError() }) {
                            Text("Trigger Snackbar Event")
                        }
                    }

                }
            }
        }
    }
}

// DO NOT DO THIS: IT IS WRONG
class ProblemViewModel : ViewModel() {
    private val _showSnackbar = MutableStateFlow(false)
    val showSnackbar : StateFlow<Boolean> = _showSnackbar.asStateFlow()

    fun onErrorOccurred() {
        _showSnackbar.value = true // State persists in memory!
    }
}

@Composable
fun ProblemScreen(viewModel: ProblemViewModel) {
    val showSnackbar by viewModel.showSnackbar.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // capturing onErrorOccurred
    viewModel.onErrorOccurred()

    LaunchedEffect(showSnackbar) {
        println("launched effect")
        println("showSnackbar: $showSnackbar")
        if (showSnackbar) {
            println("showSnackbar: $showSnackbar")
            snackbarHostState.showSnackbar("Something went wrong")
        }
    }
}

// ✅ RECOMMENDED APPROACH using SharedFlow
class SolutionViewModel : ViewModel() {
    // 1-time event bus with zero replay buffer
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    fun onErrorOccurred() {
        viewModelScope.launch {
            _eventFlow.emit(UiEvent.ShowSnackbar("Something went wrong"))
        }
    }

    sealed interface UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent
    }
}

@Composable
fun SolutionScreen(viewModel: SolutionViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Listen for incoming events only when active
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is SolutionViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }
}
