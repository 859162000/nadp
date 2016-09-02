$(document).ready(function () {
    $('#pagination-demo-Small').customPagination({
        totalPages: "35",
        visiblePages: "10",
        onPageClick: function (event, page) {
            $('#page-content').text('Page ' + page);
        }
    });

    $('#pagination-demo-Medium').customPagination({
        totalPages: 35,
        visiblePages: 10,
        onPageClick: function (event, page) {
            $('#page-content').text('Page ' + page);
        }
    });

    $('#pagination-demo-Large').customPagination({
        totalPages: 35,
        visiblePages: 10,
        onPageClick: function (event, page) {
            $('#page-content').text('Page ' + page);
        }
    });

    $('.sync-pagination').customPagination({
        totalPages: 20,
        onPageClick: function (evt, page) {
            $('#sync-example-page-content').text('Page ' + page);
        }
    });
});

