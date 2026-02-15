package com.fingerfit.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Einstellungen", fontSize = 22.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Zurück",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🏋️ FingerFit", fontSize = 32.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Version 1.0", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Koordinationstraining für Finger und Arme",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            
            // How to Use
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        "📖 So funktioniert's",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    InstructionItem(
                        number = "1",
                        text = "Wählen Sie eine Schwierigkeitsstufe"
                    )
                    InstructionItem(
                        number = "2",
                        text = "Wählen Sie Finger- oder Arm-Training"
                    )
                    InstructionItem(
                        number = "3",
                        text = "Machen Sie die gezeigten Bewegungen nach"
                    )
                    InstructionItem(
                        number = "4",
                        text = "Tippen Sie \"Geschafft\" wenn Sie fertig sind"
                    )
                    InstructionItem(
                        number = "5",
                        text = "Sammeln Sie Punkte und verbessern Sie sich!"
                    )
                }
            }
            
            // Tips Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        "💡 Tipps",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        "• Trainieren Sie regelmäßig, am besten täglich\n\n" +
                        "• Beginnen Sie mit der leichten Stufe\n\n" +
                        "• Nehmen Sie sich Zeit - Qualität vor Geschwindigkeit\n\n" +
                        "• Bei Schmerzen sofort aufhören\n\n" +
                        "• Fragen Sie Ihren Arzt bei Unsicherheiten",
                        fontSize = 18.sp,
                        lineHeight = 26.sp
                    )
                }
            }
            
            // Difficulty Explanation
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        "📊 Schwierigkeitsstufen",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    DifficultyExplanation(
                        emoji = "🌱",
                        title = "Leicht",
                        description = "Grundlegende Bewegungen, mehr Zeit zum Nachmachen"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    DifficultyExplanation(
                        emoji = "🌿",
                        title = "Mittel",
                        description = "Mehr Übungen, mittlere Geschwindigkeit"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    DifficultyExplanation(
                        emoji = "🌳",
                        title = "Schwer",
                        description = "Komplexe Bewegungen, schnelleres Tempo"
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "Mit ❤️ entwickelt für Ihre Gesundheit",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun InstructionItem(number: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(50),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = number,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}

@Composable
fun DifficultyExplanation(emoji: String, title: String, description: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(emoji, fontSize = 32.sp)
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                title,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                description,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
