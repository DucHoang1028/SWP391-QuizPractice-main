
function showPagination(currentPage, totalPages) {
    const maxVisiblePages = 5;
    let startPage, endPage;

    //check if the totalPages is smaller than the max visible pages
    if (totalPages <= maxVisiblePages) {
        startPage = 1;
        endPage = totalPages;
    } else {
        const halfVisiblePages = Math.floor(maxVisiblePages / 2);
        // if current page is less than half of max visible page
        if (currentPage <= halfVisiblePages) {
            startPage = 1;
            endPage = maxVisiblePages;
        }
        // if current page is at the ending 
        else if (currentPage + halfVisiblePages >= totalPages) {
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
        // display the pages in range of start -> end
        if (pageNumber >= startPage && pageNumber <= endPage) {
            page.style.display = 'inline-block';
        } else {
            page.style.display = 'none';
        }
    });
}

function handlePageClick(page) {

    // if page is less than 1, do nothing
    console.log('page is', page);
    if (page < 1)
        return;
    document.getElementById('page-input').value = page;
    document.getElementById('form').submit();
}


document.addEventListener('DOMContentLoaded', () => {
    const currentPage = parseInt(document.getElementById('pagination').getAttribute('data-current-page'), 10);
    const totalPages = parseInt(document.getElementById('pagination').getAttribute('data-total-pages'), 10);
    showPagination(currentPage, totalPages);
});

