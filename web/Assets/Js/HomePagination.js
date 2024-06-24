function handlePageClick(section, page) {
    const urlParams = new URLSearchParams(window.location.search);
    urlParams.set(section + 'Page', page);
    window.location.search = urlParams.toString();
}

document.addEventListener('DOMContentLoaded', () => {
    const hotPostsCurrentPage = parseInt(document.getElementById('hotPostsPagination').getAttribute('data-current-page'), 10);
    const hotPostsTotalPages = parseInt(document.getElementById('hotPostsPagination').getAttribute('data-total-pages'), 10);
    showPagination(hotPostsCurrentPage, hotPostsTotalPages, 'hotPosts');

    const featuredSubjectsCurrentPage = parseInt(document.getElementById('featuredSubjectsPagination').getAttribute('data-current-page'), 10);
    const featuredSubjectsTotalPages = parseInt(document.getElementById('featuredSubjectsPagination').getAttribute('data-total-pages'), 10);
    showPagination(featuredSubjectsCurrentPage, featuredSubjectsTotalPages, 'featuredSubjects');
});

function showPagination(currentPage, totalPages, section) {
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

    const pages = document.querySelectorAll(`#${section}Pagination .page`);
    pages.forEach(page => {
        const pageNumber = parseInt(page.getAttribute('data-page'), 10);
        if (pageNumber >= startPage && pageNumber <= endPage) {
            page.style.display = 'inline-block';
        } else {
            page.style.display = 'none';
        }
    });
}