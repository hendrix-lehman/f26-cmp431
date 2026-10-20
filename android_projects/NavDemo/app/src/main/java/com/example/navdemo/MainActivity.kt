package com.example.navdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.example.navdemo.ui.theme.NavDemoTheme
import java.nio.file.WatchEvent

val users = listOf("Hendrix", "Alec", "Carlos", "Kayla", "Marlyn")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AppNavigation()
                }
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "welcome"
        ) {
            composable(route = "welcome") {
                WelcomeScreen(navController)

            }
            composable(route = "home") {
                HomeScreen(navController)
            }
            composable(route = "details/{user}") { backStackEntry ->
                val userName = backStackEntry.arguments?.getString("user") ?: "Unknown"
                DetailScreen(userName)
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
           onClick = {
               navController.navigate("home") {
                   popUpTo("welcome") {
                       inclusive = true
                   }
                   launchSingleTop = true
               }
           }
        ) {
            Text("Login")
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(users) { user ->
            Text(
                text = user,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable() { navController.navigate("details/$user") }
                    .padding(16.dp)
            )
        }
    }

}

@Composable
fun DetailScreen(name: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "User Details",
            modifier = Modifier
                .padding(
                    bottom = 16.dp
                )
        )
        Text(
            text = "Name: $name"
        )
    }
}
