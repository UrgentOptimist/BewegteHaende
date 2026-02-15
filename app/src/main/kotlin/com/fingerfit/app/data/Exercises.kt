package com.fingerfit.app.data

data class Exercise(
    val emoji: String,
    val name: String,
    val description: String,
    val hand: Hand = Hand.EITHER
)

enum class Hand {
    LEFT, RIGHT, BOTH, EITHER
}

object FingerExercises {
    val basic = listOf(
        Exercise("👍", "Daumen hoch", "Zeigen Sie den Daumen nach oben"),
        Exercise("👎", "Daumen runter", "Zeigen Sie den Daumen nach unten"),
        Exercise("✊", "Faust", "Ballen Sie eine Faust"),
        Exercise("✋", "Offene Hand", "Öffnen Sie die Hand komplett"),
        Exercise("🤚", "Handrücken", "Zeigen Sie den Handrücken"),
    )
    
    val intermediate = basic + listOf(
        Exercise("👉", "Zeigefinger", "Strecken Sie den Zeigefinger aus"),
        Exercise("👆", "Nach oben zeigen", "Zeigen Sie nach oben"),
        Exercise("👇", "Nach unten zeigen", "Zeigen Sie nach unten"),
        Exercise("🤞", "Gekreuzte Finger", "Kreuzen Sie Zeige- und Mittelfinger"),
        Exercise("✌️", "Victory", "Zeigen Sie das Victory-Zeichen"),
        Exercise("🤟", "Ich liebe dich", "Zeigen Sie das Liebeszeichen"),
    )
    
    val advanced = intermediate + listOf(
        Exercise("🤙", "Ruf mich an", "Daumen und kleiner Finger gestreckt"),
        Exercise("🤏", "Kneifen", "Daumen und Zeigefinger zusammen"),
        Exercise("👌", "OK Zeichen", "Formen Sie einen Kreis"),
        Exercise("🤘", "Rock'n'Roll", "Zeige- und kleiner Finger hoch"),
        Exercise("🖖", "Vulkanischer Gruß", "Finger in der Mitte teilen"),
        Exercise("🙏", "Zusammen", "Beide Hände zusammen"),
    )
    
    fun getForDifficulty(difficulty: Int): List<Exercise> = when(difficulty) {
        1 -> basic
        2 -> intermediate
        else -> advanced
    }
}

data class ArmExercise(
    val emoji: String,
    val name: String,
    val description: String,
    val animation: ArmAnimation
)

enum class ArmAnimation {
    RAISE_ARMS,
    LOWER_ARMS,
    ARMS_SIDE,
    ARMS_FRONT,
    WAVE_LEFT,
    WAVE_RIGHT,
    CIRCLE_ARMS,
    CLAP,
    TOUCH_SHOULDERS,
    STRETCH_UP
}

object ArmExercises {
    val basic = listOf(
        ArmExercise("🙆", "Arme hoch", "Heben Sie beide Arme über den Kopf", ArmAnimation.RAISE_ARMS),
        ArmExercise("🙅", "Arme zur Seite", "Strecken Sie die Arme seitlich aus", ArmAnimation.ARMS_SIDE),
        ArmExercise("👏", "Klatschen", "Klatschen Sie in die Hände", ArmAnimation.CLAP),
        ArmExercise("💪", "Arme beugen", "Beugen Sie die Arme an", ArmAnimation.TOUCH_SHOULDERS),
    )
    
    val intermediate = basic + listOf(
        ArmExercise("👋", "Winken links", "Winken Sie mit der linken Hand", ArmAnimation.WAVE_LEFT),
        ArmExercise("👋", "Winken rechts", "Winken Sie mit der rechten Hand", ArmAnimation.WAVE_RIGHT),
        ArmExercise("🤸", "Arme nach vorne", "Strecken Sie beide Arme nach vorne", ArmAnimation.ARMS_FRONT),
        ArmExercise("🧘", "Arme senken", "Senken Sie die Arme langsam", ArmAnimation.LOWER_ARMS),
    )
    
    val advanced = intermediate + listOf(
        ArmExercise("🔄", "Armkreise", "Kreisen Sie mit beiden Armen", ArmAnimation.CIRCLE_ARMS),
        ArmExercise("⬆️", "Hochstrecken", "Strecken Sie sich nach oben", ArmAnimation.STRETCH_UP),
    )
    
    fun getForDifficulty(difficulty: Int): List<ArmExercise> = when(difficulty) {
        1 -> basic
        2 -> intermediate
        else -> advanced
    }
}
