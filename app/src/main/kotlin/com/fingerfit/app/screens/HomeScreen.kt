package com.fingerfit.app.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun HomeScreen(
    onFingerTrainingClick: (Int) -> Unit,
    onArmTrainingClick: (Int) -> Unit,
    onSettingsClick: () -> Unit
) {
    var selectedDifficulty by remember { mutableIntStateOf(1) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🏋️ FingerFit",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Welcome Text
            Text(
                text = "Willkommen zum\nKoordinationstraining!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                lineHeight = 34.sp
            )
            
            // Difficulty Selection
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Schwierigkeitsstufe",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        DifficultyButton(
                            emoji = "🌱",
                            label = "Leicht",
                            selected = selectedDifficulty == 1,
                            onClick = { selectedDifficulty = 1 }
                        )
                        DifficultyButton(
                            emoji = "🌿",
                            label = "Mittel",
                            selected = selectedDifficulty == 2,
                            onClick = { selectedDifficulty = 2 }
                        )
                        DifficultyButton(
                            emoji = "🌳",
                            label = "Schwer",
                            selected = selectedDifficulty == 3,
                            onClick = { selectedDifficulty = 3 }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Training Buttons
            TrainingButton(
                emoji = "🤞",
                title = "Finger-Training",
                description = "Üben Sie verschiedene Handzeichen",
                onClick = { onFingerTrainingClick(selectedDifficulty) }
            )
            
            TrainingButton(
                emoji = "💪",
                title = "Arm-Training",
                description = "Bewegungen mit Armen und Händen",
                onClick = { onArmTrainingClick(selectedDifficulty) }
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Info Text
            Text(
                text = "Machen Sie die gezeigten Bewegungen nach.\nNehmen Sie sich Zeit! 🕐",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 26.sp
            )
        }
    }
}

@Composable
fun DifficultyButton(
    emoji: String,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = Modifier.size(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = if (selected) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (selected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 32.sp)
            Text(label, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
fun TrainingButton(
    emoji: String,
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(emoji, fontSize = 56.sp)
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    description,
                    fontSize = 16.sp
                )
            }
        }
    }
}
