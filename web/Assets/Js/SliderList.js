/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


let sliders = [
    { id: 1, title: 'Slider 1', image: './Assets/Images/Slider/image1.jpg', backlink: 'http://example.com/1', status: 'show' },
    { id: 2, title: 'Slider 2', image: './Assets/Images/Slider/image1.jpg', backlink: 'http://example.com/2', status: 'hide' },
    { id: 3, title: 'Slider 3', image: './Assets/Images/Slider/image1.jpg', backlink: 'http://example.com/3', status: 'show' },
    { id: 4, title: 'Slider 4', image: './Assets/Images/Slider/image1.jpg', backlink: 'http://example.com/4', status: 'hide' },
    { id: 5, title: 'Slider 5', image: './Assets/Images/Slider/image1.jpg', backlink: 'http://example.com/5', status: 'show' },
    { id: 6, title: 'Slider 6', image: './Assets/Images/Slider/image1.jpg', backlink: 'http://example.com/6', status: 'hide' },
    { id: 7, title: 'Slider 7', image: './Assets/Images/Slider/image1.jpg', backlink: 'http://example.com/7', status: 'show' },
    { id: 8, title: 'Slider 8', image: './Assets/Images/Slider/image1.jpg', backlink: 'http://example.com/8', status: 'hide' },
];

let currentPage = 1;
const itemsPerPage = 3;

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('search-input').addEventListener('input', renderList);
    document.getElementById('status-filter').addEventListener('change', renderList);
    document.getElementById('prev-page').addEventListener('click', () => changePage(-1));
    document.getElementById('next-page').addEventListener('click', () => changePage(1));
    
    renderList();
});

function renderList() {
    const searchInput = document.getElementById('search-input').value.toLowerCase();
    const statusFilter = document.getElementById('status-filter').value;
    
    const filteredSliders = sliders.filter(slider => {
        const matchesSearch = slider.title.toLowerCase().includes(searchInput) || slider.backlink.toLowerCase().includes(searchInput);
        const matchesStatus = !statusFilter || slider.status === statusFilter;
        return matchesSearch && matchesStatus;
    });

    const totalItems = filteredSliders.length;
    const totalPages = Math.ceil(totalItems / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const endIndex = Math.min(startIndex + itemsPerPage, totalItems);
    const visibleSliders = filteredSliders.slice(startIndex, endIndex);

    const sliderList = document.getElementById('slider-list');
    sliderList.innerHTML = '';

    visibleSliders.forEach(slider => {
        const sliderItem = document.createElement('div');
        sliderItem.classList.add('slider-item');
        sliderItem.innerHTML = `
            <img src="${slider.image}" alt="${slider.title}">
            <div>
                <h3>${slider.title}</h3>
                <p>${slider.backlink}</p>
            </div>
            <div class="slider-actions">
                <button onclick="toggleStatus(${slider.id})">${slider.status === 'show' ? 'Hide' : 'Show'}</button>
                <button onclick="editSlider(${slider.id})">Edit</button>
            </div>
        `;
        sliderList.appendChild(sliderItem);
    });

    document.getElementById('page-info').textContent = `Page ${currentPage} of ${totalPages}`;
    document.getElementById('prev-page').disabled = currentPage === 1;
    document.getElementById('next-page').disabled = currentPage === totalPages;
}

function changePage(direction) {
    currentPage += direction;
    renderList();
}

function toggleStatus(id) {
    const slider = sliders.find(slider => slider.id === id);
    if (slider) {
        slider.status = slider.status === 'show' ? 'hide' : 'show';
        renderList();
    }
}

function editSlider(id) {
    // Implement the logic to edit the slider
    alert('Edit slider: ' + id);
}
