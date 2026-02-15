package com.fingerfit.app.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.fingerfit.app.data.Exercise
import com.fingerfit.app.data.FingerExercises
import com.fingerfit.app.data.Hand
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FingerTrainingScreen(
    difficulty: Int,
    onComplete: (score: Int, total: Int) -> Unit,
    onBack: () -> Unit
) {
    val exercises = remember { FingerExercises.getForDifficulty(difficulty).shuffled().take(8) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableIntStateOf(getTimeForDifficulty(difficulty)) }
    var isRunning by remember { mutableStateOf(true) }
    var showFeedback by remember { mutableStateOf(false) }
    var feedbackPositive by remember { mutableStateOf(true) }
    
    val currentExercise = exercises.getOrNull(currentIndex)
    
    // Timer
    LaunchedEffect(currentIndex, isRunning) {
        if (isRunning && currentExercise != null) {
            timeLeft = getTimeForDifficulty(difficulty)
            while (timeLeft > 0 && isRunning) {
                delay(1000)
                timeLeft--
            }
            if (isRunning) {
                // Auto-advance when time runs out
                score++
                feedbackPositive = true
                showFeedback = true
                delay(800)
                showFeedback = false
                
                if (currentIndex < exercises.size - 1) {
                    currentIndex++
                } else {
                    onComplete(score, exercises.size)
                }
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finger-Training", fontSize = 22.sp) },
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (currentExercise != null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Progress
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Übung ${currentIndex + 1} / ${exercises.size}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            "⭐ $score",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Timer Bar
                    LinearProgressIndicator(
                        progress = { timeLeft.toFloat() / getTimeForDifficulty(difficulty) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                    
                    Text(
                        "$timeLeft Sekunden",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    // Hand Indicator
                    val handText = when (currentExercise.hand) {
                        Hand.LEFT -> "🫲 Linke Hand"
                        Hand.RIGHT -> "🫱 Rechte Hand"
                        Hand.BOTH -> "🙌 Beide Hände"
                        Hand.EITHER -> "✋ Eine Hand"
                    }
                    
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            handText,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Main Exercise Display
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        shape = RoundedCornerShape(32.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            // Animated Emoji
                            val scale by animateFloatAsState(
                                targetValue = if (timeLeft % 2 == 0) 1f else 1.05f,
                                animationSpec = tween(500),
                                label = "scale"
                            )
                            
                            Text(
                                currentExercise.emoji,
                                fontSize = (140 * scale).sp
                            )
                            
                            Spacer(modifier = Modifier.height(24.dp))
                            
                            Text(
                                currentExercise.name,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                currentExercise.description,
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 30.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Done Button
                    Button(
                        onClick = {
                            score++
                            feedbackPositive = true
                            showFeedback = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(
                            "✅ Geschafft!",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Feedback Overlay
            AnimatedVisibility(
                visible = showFeedback,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (feedbackPositive)
                                MaterialTheme.colorScheme.primaryContainer
                            else
                                MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = if (feedbackPositive) "🎉 Super!" else "💪 Weiter so!",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(32.dp)
                        )
                    }
                }
                
                LaunchedEffect(showFeedback) {
                    if (showFeedback) {
                        delay(800)
                        showFeedback = false
                        if (currentIndex < exercises.size - 1) {
                            currentIndex++
                        } else {
                            onComplete(score, exercises.size)
                        }
                    }
                }
            }
        }
    }
}

private fun getTimeForDifficulty(difficulty: Int): Int = when(difficulty) {
    1 -> 8  // Easy: 8 seconds
    2 -> 6  // Medium: 6 seconds
    else -> 4 // Hard: 4 seconds
}
