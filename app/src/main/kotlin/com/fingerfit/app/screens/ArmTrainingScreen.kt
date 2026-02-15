package com.fingerfit.app.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fingerfit.app.data.ArmAnimation
import com.fingerfit.app.data.ArmExercise
import com.fingerfit.app.data.ArmExercises
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArmTrainingScreen(
    difficulty: Int,
    onComplete: (score: Int, total: Int) -> Unit,
    onBack: () -> Unit
) {
    val exercises = remember { ArmExercises.getForDifficulty(difficulty).shuffled().take(6) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var timeLeft by remember { mutableIntStateOf(getArmTimeForDifficulty(difficulty)) }
    var isRunning by remember { mutableStateOf(true) }
    var showFeedback by remember { mutableStateOf(false) }
    
    val currentExercise = exercises.getOrNull(currentIndex)
    
    // Timer
    LaunchedEffect(currentIndex, isRunning) {
        if (isRunning && currentExercise != null) {
            timeLeft = getArmTimeForDifficulty(difficulty)
            while (timeLeft > 0 && isRunning) {
                delay(1000)
                timeLeft--
            }
            if (isRunning) {
                score++
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
                title = { Text("Arm-Training", fontSize = 22.sp) },
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
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondary
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
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Timer
                    LinearProgressIndicator(
                        progress = { timeLeft.toFloat() / getArmTimeForDifficulty(difficulty) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(12.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                    
                    Text(
                        "$timeLeft Sekunden",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Exercise Card
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
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                currentExercise.name,
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            
                            // Animated Figure
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                ArmAnimationView(
                                    animation = currentExercise.animation,
                                    modifier = Modifier.size(200.dp)
                                )
                            }
                            
                            Text(
                                currentExercise.emoji,
                                fontSize = 80.sp
                            )
                            
                            Text(
                                currentExercise.description,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                lineHeight = 32.sp
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Done Button
                    Button(
                        onClick = {
                            score++
                            showFeedback = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
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
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Text(
                            text = "🎉 Sehr gut!",
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

@Composable
fun ArmAnimationView(
    animation: ArmAnimation,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "arm")
    val animProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "progress"
    )
    
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    
    Canvas(modifier = modifier) {
        val centerX = size.width / 2
        val centerY = size.height / 2
        val headRadius = size.width * 0.12f
        val bodyLength = size.height * 0.25f
        val armLength = size.width * 0.25f
        val strokeWidth = size.width * 0.04f
        
        // Head
        drawCircle(
            color = primaryColor,
            radius = headRadius,
            center = Offset(centerX, centerY - bodyLength - headRadius)
        )
        
        // Body
        drawLine(
            color = primaryColor,
            start = Offset(centerX, centerY - bodyLength),
            end = Offset(centerX, centerY + bodyLength * 0.3f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        
        // Animated Arms based on exercise type
        val (leftArmAngle, rightArmAngle) = when (animation) {
            ArmAnimation.RAISE_ARMS -> {
                val angle = -90f + (180f * (1 - animProgress))
                Pair(angle, -angle)
            }
            ArmAnimation.LOWER_ARMS -> {
                val angle = 90f - (180f * animProgress)
                Pair(angle, -angle)
            }
            ArmAnimation.ARMS_SIDE -> {
                val angle = 180f - (90f * animProgress)
                Pair(angle, -angle + 180f)
            }
            ArmAnimation.ARMS_FRONT -> {
                Pair(90f - (90f * animProgress), -(90f - (90f * animProgress)))
            }
            ArmAnimation.WAVE_LEFT -> {
                val leftAngle = -45f + (30f * animProgress)
                Pair(leftAngle, 45f)
            }
            ArmAnimation.WAVE_RIGHT -> {
                val rightAngle = 45f - (30f * animProgress)
                Pair(-45f, rightAngle)
            }
            ArmAnimation.CIRCLE_ARMS -> {
                val angle = 360f * animProgress
                Pair(angle, angle)
            }
            ArmAnimation.CLAP -> {
                val angle = 90f * animProgress
                Pair(angle, -angle)
            }
            ArmAnimation.TOUCH_SHOULDERS -> {
                val angle = 45f + (90f * animProgress)
                Pair(angle, -angle)
            }
            ArmAnimation.STRETCH_UP -> {
                val angle = -90f + (45f * animProgress)
                Pair(angle, -angle - 180f)
            }
        }
        
        val shoulderY = centerY - bodyLength + headRadius
        
        // Left Arm
        val leftEndX = centerX - armLength * cos(Math.toRadians(leftArmAngle.toDouble())).toFloat()
        val leftEndY = shoulderY - armLength * sin(Math.toRadians(leftArmAngle.toDouble())).toFloat()
        drawLine(
            color = secondaryColor,
            start = Offset(centerX - strokeWidth, shoulderY),
            end = Offset(leftEndX, leftEndY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        
        // Right Arm
        val rightEndX = centerX + armLength * cos(Math.toRadians(rightArmAngle.toDouble())).toFloat()
        val rightEndY = shoulderY - armLength * sin(Math.toRadians(rightArmAngle.toDouble())).toFloat()
        drawLine(
            color = secondaryColor,
            start = Offset(centerX + strokeWidth, shoulderY),
            end = Offset(rightEndX, rightEndY),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        
        // Hands (circles at arm ends)
        drawCircle(
            color = secondaryColor,
            radius = strokeWidth * 1.2f,
            center = Offset(leftEndX, leftEndY)
        )
        drawCircle(
            color = secondaryColor,
            radius = strokeWidth * 1.2f,
            center = Offset(rightEndX, rightEndY)
        )
    }
}

private fun getArmTimeForDifficulty(difficulty: Int): Int = when(difficulty) {
    1 -> 10  // Easy: 10 seconds
    2 -> 7   // Medium: 7 seconds
    else -> 5 // Hard: 5 seconds
}
