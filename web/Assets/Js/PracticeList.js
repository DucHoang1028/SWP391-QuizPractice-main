document.addEventListener('DOMContentLoaded', function() {
    const percentageSquares = document.querySelectorAll('.percentage-square');

    percentageSquares.forEach(square => {
        const percentageText = square.querySelector('.percentage').textContent.trim();
        const percentage = parseInt(percentageText.replace('%', '').trim(), 10);

        if (percentage >= 65) {
            square.classList.add('percentage-100-65');
        } else if (percentage >= 45) {
            square.classList.add('percentage-64-45');
        } else {
            square.classList.add('percentage-44-0');
        }
    });

    // Handle sort change
    document.getElementById('sort').addEventListener('change', function() {
        document.getElementById('sort-input').value = this.value;
        document.getElementById('form').submit();
    });

    // Handle filter change
    document.getElementById('filter').addEventListener('change', function() {
        document.getElementById('filter-input').value = this.value;
        document.getElementById('form').submit();
    });

    // Handle search
    document.getElementById('search').addEventListener('keypress', function(event) {
        if (event.key === 'Enter') {
            event.preventDefault();
            handleSearch();
        }
    });
});

function handleSearch() {
    document.getElementById('search-input').value = document.getElementById('search').value;
    document.getElementById('form').submit();
}

function clearSearch() {
    document.getElementById('search').value = '';
    document.getElementById('search-input').value = '';
    document.getElementById('form').submit();
}

function handlePageClick(page) {
    document.getElementById('page-input').value = page;
    document.getElementById('form').submit();
}
