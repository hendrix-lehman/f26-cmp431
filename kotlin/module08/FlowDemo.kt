import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class LiveMarketRepository {

    // 1. FLOW BUILDER: Cold stream emitting live stock ticks
    fun getStockStream(): Flow<Double> = flow {
        val prices = listOf(100.0, 102.5, 98.0, 105.0, 110.0, 94.0)
        for (price in prices) {
            delay(500) // Simulates time between price updates
            emit(price) // Push value to the flow
        }
    }
}

class MarketViewModel(private val repository: LiveMarketRepository) {

    // 2. STATEFLOW: Holds current state for UI rendering
    private val _uiState = MutableStateFlow<String>("Waiting for data...")
    val uiState: StateFlow<String> = _uiState.asStateFlow()

    // 3. SHAREDFLOW: Broadcasts one-time events (e.g. Price Alerts)
    private val _alertEvent = MutableSharedFlow<String>()
    val alertEvent: SharedFlow<String> = _alertEvent.asSharedFlow()

    fun startMonitoring(scope: CoroutineScope) {
        scope.launch {
            repository.getStockStream()
                // 4. MAP & FILTER OPERATORS
                .filter { price -> price > 95.0 } // Filter out anomalous low prices
                .map { price -> "AAPL: $$price" }  // Transform raw double to display string
                .collect { formattedPrice ->
                    _uiState.value = formattedPrice // Update StateFlow state

                    // Trigger a one-time event on SharedFlow if price hits target
                    if (formattedPrice.contains("110.0")) {
                        _alertEvent.emit("🚨 ALERT: AAPL hit target high of $110.0!")
                    }
                }
        }
    }
}

fun main() = runBlocking {
    val repository = LiveMarketRepository()
    val viewModel = MarketViewModel(repository)

    // Collector 1: Collect One-Time SharedFlow Events
    launch {
        viewModel.alertEvent.collect { alert ->
            println("[EVENT RECEIVED] $alert")
        }
    }

    // Collector 2: Collect Persistent StateFlow State
    launch {
        viewModel.uiState.collect { state ->
            println("[UI STATE UPDATED] $state")
        }
    }

    // Start streaming data inside this coroutine scope
    viewModel.startMonitoring(this)
}
