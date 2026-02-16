// Hand exercises with emojis and names
const exercises = [
    { emoji: '👍', name: 'Daumen hoch', instruction: 'Zeigen Sie Daumen hoch' },
    { emoji: '👎', name: 'Daumen runter', instruction: 'Zeigen Sie Daumen runter' },
    { emoji: '✌️', name: 'Victory-Zeichen', instruction: 'Zeigen Sie das V-Zeichen' },
    { emoji: '🤞', name: 'Gekreuzte Finger', instruction: 'Kreuzen Sie Zeige- und Mittelfinger' },
    { emoji: '🤙', name: 'Hang Loose', instruction: 'Daumen und kleiner Finger raus' },
    { emoji: '👌', name: 'OK-Zeichen', instruction: 'Formen Sie ein O mit Daumen und Zeigefinger' },
    { emoji: '🤘', name: 'Rock-Zeichen', instruction: 'Zeige- und kleiner Finger hoch' },
    { emoji: '👋', name: 'Winken', instruction: 'Winken Sie mit der Hand' },
    { emoji: '✊', name: 'Faust', instruction: 'Machen Sie eine Faust' },
    { emoji: '✋', name: 'Stopp', instruction: 'Zeigen Sie die offene Hand' },
    { emoji: '🖐️', name: 'Fünf Finger', instruction: 'Spreizen Sie alle Finger' },
    { emoji: '🖖', name: 'Vulkanier-Gruß', instruction: 'Teilen Sie die Finger in der Mitte' },
    { emoji: '👆', name: 'Nach oben zeigen', instruction: 'Zeigen Sie mit dem Finger nach oben' },
    { emoji: '👇', name: 'Nach unten zeigen', instruction: 'Zeigen Sie mit dem Finger nach unten' },
    { emoji: '👈', name: 'Nach links zeigen', instruction: 'Zeigen Sie nach links' },
    { emoji: '👉', name: 'Nach rechts zeigen', instruction: 'Zeigen Sie nach rechts' },
    { emoji: '🤏', name: 'Kleine Prise', instruction: 'Halten Sie Daumen und Zeigefinger nah zusammen' },
    { emoji: '👏', name: 'Klatschen', instruction: 'Klatschen Sie in die Hände' },
    { emoji: '🙌', name: 'Hände hoch', instruction: 'Heben Sie beide Hände' },
    { emoji: '🤝', name: 'Händedruck', instruction: 'Falten Sie die Hände ineinander' },
];

// Difficulty settings
const difficulties = {
    easy: { rounds: 5, timePerExercise: 10, pointsPerSuccess: 10 },
    medium: { rounds: 7, timePerExercise: 7, pointsPerSuccess: 15 },
    hard: { rounds: 10, timePerExercise: 5, pointsPerSuccess: 20 }
};

// Game state
let currentDifficulty = 'easy';
let currentRound = 0;
let score = 0;
let completedCount = 0;
let timeLeft = 0;
let timerInterval = null;
let currentExercises = [];

// DOM Elements
const screens = {
    start: document.getElementById('start-screen'),
    game: document.getElementById('game-screen'),
    result: document.getElementById('result-screen')
};

function showScreen(screenName) {
    Object.values(screens).forEach(s => s.classList.remove('active'));
    screens[screenName].classList.add('active');
}

function shuffleArray(array) {
    const shuffled = [...array];
    for (let i = shuffled.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [shuffled[i], shuffled[j]] = [shuffled[j], shuffled[i]];
    }
    return shuffled;
}

function startGame(difficulty) {
    currentDifficulty = difficulty;
    const settings = difficulties[difficulty];
    
    currentRound = 0;
    score = 0;
    completedCount = 0;
    
    // Select random exercises for this game
    currentExercises = shuffleArray(exercises).slice(0, settings.rounds);
    
    showScreen('game');
    nextRound();
}

function nextRound() {
    const settings = difficulties[currentDifficulty];
    
    if (currentRound >= settings.rounds) {
        endGame();
        return;
    }
    
    const exercise = currentExercises[currentRound];
    
    // Update display
    document.getElementById('round-display').textContent = 
        `Runde ${currentRound + 1}/${settings.rounds}`;
    document.getElementById('score-display').textContent = `⭐ ${score}`;
    document.getElementById('hand-emoji').textContent = exercise.emoji;
    document.getElementById('hand-name').textContent = exercise.name;
    document.getElementById('instruction').textContent = exercise.instruction;
    
    // Reset and start timer
    timeLeft = settings.timePerExercise;
    updateTimerDisplay();
    
    if (timerInterval) clearInterval(timerInterval);
    timerInterval = setInterval(updateTimer, 100);
}

function updateTimer() {
    timeLeft -= 0.1;
    updateTimerDisplay();
    
    if (timeLeft <= 0) {
        clearInterval(timerInterval);
        skipExercise();
    }
}

function updateTimerDisplay() {
    const settings = difficulties[currentDifficulty];
    const percentage = (timeLeft / settings.timePerExercise) * 100;
    const timerBar = document.getElementById('timer-bar');
    const timerText = document.getElementById('timer-text');
    
    timerBar.style.width = `${percentage}%`;
    timerText.textContent = Math.ceil(timeLeft);
    
    // Color changes
    timerBar.classList.remove('warning', 'danger');
    if (percentage < 30) {
        timerBar.classList.add('danger');
        timerText.style.color = '#f44336';
    } else if (percentage < 60) {
        timerBar.classList.add('warning');
        timerText.style.color = '#ff9800';
    } else {
        timerText.style.color = '#388e3c';
    }
}

function exerciseDone() {
    clearInterval(timerInterval);
    
    const settings = difficulties[currentDifficulty];
    
    // Calculate bonus for time left
    const timeBonus = Math.floor(timeLeft);
    const points = settings.pointsPerSuccess + timeBonus;
    score += points;
    completedCount++;
    
    // Visual feedback
    const btn = document.getElementById('done-btn');
    btn.textContent = `+${points} ⭐`;
    btn.style.background = 'linear-gradient(135deg, #FFD700, #FFA000)';
    
    setTimeout(() => {
        btn.textContent = '✅ Geschafft!';
        btn.style.background = 'linear-gradient(135deg, #4CAF50, #43A047)';
        currentRound++;
        nextRound();
    }, 800);
}

function skipExercise() {
    clearInterval(timerInterval);
    currentRound++;
    nextRound();
}

function endGame() {
    clearInterval(timerInterval);
    
    const settings = difficulties[currentDifficulty];
    const maxScore = settings.rounds * (settings.pointsPerSuccess + settings.timePerExercise);
    const percentage = (score / maxScore) * 100;
    
    // Determine stars
    let stars, title, motivation;
    if (percentage >= 80) {
        stars = '⭐⭐⭐';
        title = '🎉 Fantastisch!';
        motivation = 'Hervorragend! Sie sind ein Meister! 🏆';
    } else if (percentage >= 50) {
        stars = '⭐⭐';
        title = '👏 Gut gemacht!';
        motivation = 'Sehr gut! Weiter so! 💪';
    } else if (percentage >= 20) {
        stars = '⭐';
        title = '👍 Guter Anfang!';
        motivation = 'Übung macht den Meister! 🌱';
    } else {
        stars = '🌟';
        title = '💪 Weiter üben!';
        motivation = 'Jeder Anfang ist schwer. Sie schaffen das! 🙌';
    }
    
    document.getElementById('result-title').textContent = title;
    document.getElementById('stars-display').textContent = stars;
    document.getElementById('final-score').textContent = score;
    document.getElementById('completed-count').textContent = completedCount;
    document.getElementById('total-count').textContent = settings.rounds;
    document.getElementById('motivation').textContent = motivation;
    
    showScreen('result');
}

function restartGame() {
    startGame(currentDifficulty);
}

function goHome() {
    showScreen('start');
}

// Prevent zoom on double tap (for mobile)
document.addEventListener('touchend', (e) => {
    e.preventDefault();
    e.target.click();
}, { passive: false });
