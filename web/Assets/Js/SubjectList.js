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

function filterByCategory() {
    // get all ticked checkboxes 
    const checkboxes = document.querySelectorAll('.category-column input[type="checkbox"]:checked');
    const categories = document.getElementById('category-input');
    categories.value = '';
    checkboxes.forEach(checkbox => {
        categories.value += checkbox.value + ' ';
    });
    document.getElementById('page-input').value = 1;
    document.getElementById('form').submit();

}

function checkCategories() {
    const categories = document.getElementById("category-input").value.split(' ');
    const checkboxes = document.querySelectorAll('input[type="checkbox"]');
    categories.forEach(category => {
        checkboxes.forEach(checkbox => {
            if (checkbox.value === category) {
                checkbox.checked = true;
            }
        });
    });

}

function showRegisterPopup(subject_id, title, category, pricePackage) {
    $("#subject_id").val(subject_id);
    $("#title").val(title);
    $("#category").val(category);

    // replace all the ' to " in pricePackage
    pricePackage = pricePackage.replace(/'/g, '"');
    pricePackage = JSON.parse(pricePackage);
    console.log(pricePackage);
    

    populatePricePackages(pricePackage);
    

    $("#subject-register-component").css("display", "flex");
    $("#close-subject-register-popup").click(function (event) {
        $("#subject-register-component").css("display", "none");
        $("#close-subject-register-popup").off('click');
    });
}

function populatePricePackages(selectedPackage) {
            const pricePackageSelect = $("#pricePackage");
            pricePackageSelect.empty(); // Clear any existing options

            selectedPackage.forEach(package => {
                const option = new Option(`${package.name} - $${package.sale_price} -  ${package.duration} days`);
                pricePackageSelect.append(option);
            });

            if (selectedPackage) {
                pricePackageSelect.val(selectedPackage);
            }
        }



function toggleCategories() {
    const categoryList = document.getElementById('category-list');
    const toggleButton = document.getElementById('toggle-button');
    const arrow = toggleButton.querySelector('.arrow');
    if (categoryList.classList.contains('expanded')) {
        categoryList.classList.remove('expanded');
        toggleButton.textContent = 'Show More ';
        arrow.innerHTML = '&#9660;';
        toggleButton.appendChild(arrow);
    } else {
        categoryList.classList.add('expanded');
        toggleButton.textContent = 'Hide ';
        arrow.innerHTML = '&#9650;';
        toggleButton.appendChild(arrow);
    }
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

checkCategories();

