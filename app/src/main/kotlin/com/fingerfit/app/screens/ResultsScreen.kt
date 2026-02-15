package com.fingerfit.app.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultsScreen(
    score: Int,
    total: Int,
    mode: String,
    onHomeClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    val percentage = if (total > 0) (score * 100) / total else 0
    
    val (emoji, message, color) = when {
        percentage >= 90 -> Triple("🏆", "Ausgezeichnet!", MaterialTheme.colorScheme.primary)
        percentage >= 70 -> Triple("🌟", "Sehr gut gemacht!", MaterialTheme.colorScheme.primary)
        percentage >= 50 -> Triple("👍", "Gut gemacht!", MaterialTheme.colorScheme.secondary)
        else -> Triple("💪", "Weiter üben!", MaterialTheme.colorScheme.tertiary)
    }
    
    val infiniteTransition = rememberInfiniteTransition(label = "celebration")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Celebration Emoji
        Text(
            text = emoji,
            fontSize = (120 * scale).sp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Message
        Text(
            text = message,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = color,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Score Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (mode == "finger") "🤞 Finger-Training" else "💪 Arm-Training",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "$score / $total",
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
                
                Text(
                    text = "Übungen geschafft",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Star Rating
                Row {
                    repeat(5) { index ->
                        val starFilled = (index + 1) * 20 <= percentage
                        Text(
                            text = if (starFilled) "⭐" else "☆",
                            fontSize = 40.sp
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Motivational Text
        Text(
            text = getMotivationalText(percentage),
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            lineHeight = 28.sp
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Buttons
        Button(
            onClick = onRetryClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "🔄 Nochmal üben",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = onHomeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                "🏠 Zum Startbildschirm",
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

private fun getMotivationalText(percentage: Int): String = when {
    percentage >= 90 -> "Fantastisch! Sie haben heute großartig trainiert. Weiter so! 🎉"
    percentage >= 70 -> "Sehr gut! Regelmäßiges Üben macht den Meister. 💪"
    percentage >= 50 -> "Gute Arbeit! Jede Übung zählt für Ihre Gesundheit. 🌟"
    else -> "Der Anfang ist gemacht! Mit etwas Übung wird es immer besser. 🌱"
}
