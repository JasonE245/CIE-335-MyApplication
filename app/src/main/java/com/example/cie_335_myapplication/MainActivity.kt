package com.example.cie_335_myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                WelcomeCardApp()
            }
        }
    }
}


@Composable
fun WelcomeCardApp() {
    var userName by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf("Heureux") }
    var showCard by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var currentScreen by remember { mutableStateOf("form") }

    when (currentScreen) {
        "form" -> {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "\nWelcome Card App",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Entrez votre prénom, choisissez votre humeur, puis générez " +
                        "une carte personnalisée.")

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Votre prénom") },
                    modifier = Modifier.fillMaxWidth()
                )
                Text("Nombre de caractères : ${userName.length}")
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(onClick = { selectedMood = "Heureux" }) {
                        Text("Heureux")
                    }
                    Button(onClick = { selectedMood = "Concentré" }) {
                        Text("Concentré")
                    }
                    Button(onClick = { selectedMood = "Fatigué" }) {
                        Text("Fatigué")
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    FilterChip(
                        selected = selectedMood == "Heureux",
                        onClick = { selectedMood = "Heureux" },
                        label = { Text("Heureux") }
                    )
                    FilterChip(
                        selected = selectedMood == "Concentré",
                        onClick = { selectedMood = "Concentré" },
                        label = { Text("Concentré") }
                    )
                    FilterChip(
                        selected = selectedMood == "Fatigué",
                        onClick = { selectedMood = "Fatigué" },
                        label = { Text("Fatigué") }
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Button(
                        enabled = userName.isNotBlank(),
                        onClick = {
                            showCard = true
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Créer la carte")
                    }
                    Button(
                        onClick = {
                            userName = ""
                            selectedMood = "Heureux"
                            showCard = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Reset")
                    }
                }
                if (showCard) {
                    WelcomeCard(
                        userName = userName,
                        selectedMood = selectedMood,
                        onOpenDetails = {
                            currentScreen = "details"
                        })
                }
            }


        }
        "details" -> {
            DetailsScreen(
                userName = userName,
                selectedMood = selectedMood,
                onBack = {
                    currentScreen = "form"
                }
            )
        }
    }
}

@Composable
fun WelcomeCard(
    userName: String,
    selectedMood: String,
    onOpenDetails: () -> Unit
) {
    val moodMessage = when (selectedMood) {
        "Heureux" -> "Continue à partager cette énergie positive."
        "Concentré" -> "Reste concentré et continue à progresser."
        "Fatigué" -> "Prends une courte pause puis reprends calmement."
        else -> "Bienvenue."
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Prénom : $userName")
            Text(text = "Humeur : $selectedMood")
            Text(text = moodMessage)
        }
        Button(
            onClick = onOpenDetails
        ) {
            Text("Ouvrir les détails")
        }
    }
}

@Composable
fun DetailsScreen(
    userName: String,
    selectedMood: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "\nDétails du profil")
        Text(text = "Prénom : $userName")
        Text(text = "Humeur : $selectedMood")
        Button(onClick = onBack) {
            Text("Retour")
        }
    }
}