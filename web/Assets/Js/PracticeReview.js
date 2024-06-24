document.addEventListener('DOMContentLoaded', () => {
    const percentages = document.querySelectorAll('.percentage');
    percentages.forEach(percentageElement => {
        const percentageText = percentageElement.innerText;
        const percentageValue = parseFloat(percentageText);
        if (!isNaN(percentageValue)) {
            if (percentageValue >= 65) {
                percentageElement.style.color = '#0F7820'; // Green
            } else if (percentageValue >= 45) {
                percentageElement.style.color = '#B9C718'; // Yellow
            } else {
                percentageElement.style.color = '#F00'; // Red
            }
        }
    });
});
