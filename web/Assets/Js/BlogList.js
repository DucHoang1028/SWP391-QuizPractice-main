function handlePageClick(page) {
    document.getElementById('page-input').value = page;
    document.getElementById('form').submit();
}

function handleSortChange() {
    const sortSelect = document.getElementById('sort');
    const sortOption = sortSelect.value;

    // Set the selected sort option in a hidden input for form submission
    document.getElementById('sort-input').value = sortOption;
    document.getElementById('page-input').value = 1;
    document.getElementById('form').submit();
}

function handleSearchByTitle() {
    const searchInput = 2;
    const searchValue = document.getElementById('search-input-view').value;
    document.getElementById('search-input').value = searchInput;
    document.getElementById('search-value-input').value = searchValue;
    document.getElementById('page-input').value = 1;
    document.getElementById('form').submit();

}

document.addEventListener('DOMContentLoaded', () => {
    
    // set thumbnail images for subjects
    const thumbnailDivs = document.querySelectorAll('.thumbnail');
    thumbnailDivs.forEach(thumbnailDiv => {
        const imageUrl = thumbnailDiv.dataset.src;

        if (imageUrl) {
            // Create a new Image object to get the dimensions of the image
            const img = new Image();
            img.src = imageUrl;

            img.onload = () => {

                // Set the background image of the thumbnail div
                thumbnailDiv.style.backgroundImage = `url('${imageUrl}')`;

                // Set the background size to cover the div without stretching
                thumbnailDiv.style.backgroundSize = 'cover';

                // Center the background image
                thumbnailDiv.style.backgroundPosition = 'center';
            };
        }
    });
});
function toggleCategories() {
    const categoryList = document.getElementById('category-list');
    categoryList.classList.toggle('expanded');
    const toggleButton = document.getElementById('toggle-button');
    toggleButton.innerHTML = categoryList.classList.contains('expanded') ? 'Show Less <span class="arrow">&#9650;</span>' : 'Show More <span class="arrow">&#9660;</span>';
}

function enterCategory() {
    const selectedCategories = Array.from(document.querySelectorAll('input[name="category"]:checked')).map(checkbox => checkbox.value);
    document.getElementById('category-id-input').value = selectedCategories.join(' ');
    document.getElementById('form').submit();
}

function showPagination(currentPage, totalPages) {
    const maxVisiblePages = 5;
    let startPage, endPage;

    if (totalPages <= maxVisiblePages) {
        startPage = 1;
        endPage = totalPages;
    } else {
        const halfVisiblePages = Math.floor(maxVisiblePages / 2);
        if (currentPage <= halfVisiblePages) {
            startPage = 1;
            endPage = maxVisiblePages;
        } else if (currentPage + halfVisiblePages >= totalPages) {
            startPage = totalPages - maxVisiblePages + 1;
            endPage = totalPages;
        } else {
            startPage = currentPage - halfVisiblePages;
            endPage = currentPage + halfVisiblePages;
        }
    }

    const pages = document.querySelectorAll('#pagination .page');
    pages.forEach(page => {
        const pageNumber = parseInt(page.getAttribute('data-page'), 10);
        if (pageNumber >= startPage && pageNumber <= endPage) {
            page.style.display = 'inline-block';
        } else {
            page.style.display = 'none';
        }
    });
}

function handlePageClick(page) {
    if (page < 1) return;
    document.getElementById('page-input').value = page;
    document.getElementById('form').submit();
}

document.addEventListener('DOMContentLoaded', () => {
    const currentPage = parseInt(document.getElementById('pagination').getAttribute('data-current-page'), 10);
    const totalPages = parseInt(document.getElementById('pagination').getAttribute('data-total-pages'), 10);
    showPagination(currentPage, totalPages);
});
