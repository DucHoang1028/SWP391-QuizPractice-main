document.addEventListener('DOMContentLoaded', () => {
    const scores = document.querySelectorAll('.score');
    scores.forEach(scoreElement => {
        const scoreText = scoreElement.innerText;
        const scoreValue = parseFloat(scoreText);
        if (!isNaN(scoreValue)) {
            scoreElement.innerText = `${Math.floor(scoreValue)} Correct`;
        }
    });

    const percentages = document.querySelectorAll('.percentage');
    percentages.forEach(percentageElement => {
        const percentageText = percentageElement.innerText;
        const percentageValue = parseFloat(percentageText);
        if (!isNaN(percentageValue)) {
            percentageElement.innerText = `${Math.round(percentageValue)} %`;
        }
    });
});
