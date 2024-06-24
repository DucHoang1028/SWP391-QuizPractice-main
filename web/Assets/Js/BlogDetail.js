function handleSearchByTitle() {
    const searchValue = document.getElementById('search-input-view').value;
    document.getElementById('search-value-input').value = searchValue;
    document.getElementById('form').submit();
}

document.addEventListener('DOMContentLoaded', () => {
    // Set thumbnail images for subjects
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
document.getElementById('back-to-top').addEventListener('click', function () {
    window.scrollTo({top: 0, behavior: 'smooth'});
});
  function toggleCategories() {
                const categoryList = document.getElementById('category-list');
                categoryList.classList.toggle('expanded');
                const toggleButton = document.getElementById('toggle-button');
                toggleButton.innerHTML = categoryList.classList.contains('expanded')
                        ? 'Show Less <span class="arrow">&#9650;</span>'
                        : 'Show More <span class="arrow">&#9660;</span>';
            }

            function handleCategoryFilter() {
                const checkboxes = document.querySelectorAll('.category-checkbox:checked');
                const categoryIds = Array.from(checkboxes).map(checkbox => checkbox.value);
                const categoryInput = document.getElementById('category-id-input');
                categoryInput.value = categoryIds.join(' ');
                document.getElementById('form').submit();
            }