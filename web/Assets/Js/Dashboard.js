document.addEventListener('DOMContentLoaded', () => {
    let closeBtn = document.querySelector("#marketing-btn");
    let dashboard = document.querySelector(".dashboard");

    closeBtn.addEventListener("click", () => {
        dashboard.classList.toggle("sidebar-open");
    });
});

function ViewAllSubject() {
    document.getElementById("subjectListPopup").style.display = "block";
}

function updateSubjectList() {
    var selectedCategory = document.getElementById("subjects").value;

    $.ajax({
        url: 'dashboard', // Update with your backend URL
        method: 'GET',
        data: {category: selectedCategory, action: 'viewSubject'},
        contentType: 'application/json',
        success: function (data) {
            console.log('Subjects received from servlet:', data);
            
            // Clear previous data from subject-list tbody
            $('#subject-list').empty();

            // Check if data.subjects exists and is an array
            if (Array.isArray(data.subjects)) {
                // Iterate over the subjects array
                data.subjects.forEach(function (subject) {
                    // Format the createdAt date
                    var createdAtDate = new Date(subject.createdAt);
                    var formattedDate = createdAtDate.toLocaleDateString() + ' ' + createdAtDate.toLocaleTimeString();

                    // Append row to tbody for each subject
                    $('#subject-list').append(
                        '<tr><td>' + subject.title + '</td><td>' + subject.status + '</td><td class="datetime">' + formattedDate + '</td></tr>'
                    );
                });
            } else {
                console.error('Invalid data format or empty data received:', data);
            }
            
            // After updating subject-list tbody, you can do additional processing if needed
            console.log('Updated subject-list with new data');
        },
        error: function (error) {
            console.log('Error fetching data:', error);
        }
    });
}

var successfullyRegistration = document.getElementById('success').innerText;
var cancelledRegistration = document.getElementById('cancelled').innerText;
var submittedRegistration = document.getElementById('submitted').innerText;


var ctx = document.getElementById('myPieChart').getContext('2d');
var myPieChart = new Chart(ctx, {
    type: 'pie',
    data: {
        labels: ['Successfully Registration', 'Cancelled Registration', 'Submitted Registration'],
        datasets: [{
                data: [successfullyRegistration, cancelledRegistration, submittedRegistration],
                backgroundColor: ['#8DD6C2', '#F8A1A4', '#F9D369'],
                borderColor: '#ffffff',
                borderWidth: 0,
                width: 100,
                height: 100
            }]
    },
    options: {
        responsive: true,
        plugins: {
            legend: {
                display: false,
            },
            tooltip: {
                callbacks: {
                    label: function (tooltipItem) {
                        return tooltipItem.label + ': ' + tooltipItem.raw;
                    }
                }
            }
        }
    }
});

var revenueList = [];

function addRevenueItem(key, value) {
    let parsedValue = parseFloat(value);

    if (!isNaN(parsedValue)) {
        let item = {
            name: key,
            value: parsedValue
        };
        revenueList.push(item);
        console.log('Added item to Revenue:', item);
    } else {
        console.error('Invalid value format:', value);
    }
}

function ViewAll() {
    document.getElementById("revenuePopup").style.display = "block";
}

function hidePopup() {
    document.getElementById("revenuePopup").style.display = "none";
    document.getElementById("subjectListPopup").style.display = "none";
}

function updateList() {
    var selectedCategory = document.getElementById("categories").value;

    $.ajax({
        url: 'dashboard', // Update with your backend URL
        method: 'GET',
        data: {category: selectedCategory, action: "view"},
        contentType: 'application/json',
        success: function (data) {
            console.log('Revenue received from servlet:', data);

            // Clear previous data from revenue-list tbody
            $('#revenue-list').empty();

            // Check if data.revenueCount exists and is an object
            if (data.revenueCount && typeof data.revenueCount === 'object') {
                // Iterate over the properties of data.revenueCount
                Object.keys(data.revenueCount).forEach(function (subject) {
                    // Append row to tbody for each subject and revenue
                    $('#revenue-list').append('<tr><td>' + subject + '</td><td>$' + data.revenueCount[subject] + '</td></tr>');
                });
            } else {
                console.error('Invalid data format or empty data received:', data);
            }

            // After updating revenue-list tbody, you can do additional processing if needed
            console.log('Updated revenue-list with new data');

        },
        error: function (error) {
            console.log('Error fetching data:', error);
        }
    });
}

var successOrder = [];
var allOrder = [];
var myChart = null;

function addSuccessItem(key, value) {
    let parsedValue = parseInt(value);

    if (!isNaN(parsedValue)) {
        let item = {
            date: key,
            value: parsedValue
        };
        successOrder.push(item);
        console.log('Added item to successOrder:', item);
    } else {
        console.error('Invalid value format:', value);
    }
}

function addAllItem(key, value) {
    let parsedValue = parseInt(value);

    if (!isNaN(parsedValue)) {
        let item = {
            date: key,
            value: parsedValue
        };
        allOrder.push(item);
        console.log('Added item to allOrder:', item);
    } else {
        console.error('Invalid value format:', value);
    }
}

function getFirstAndLastDates(orderArray) {
    orderArray.sort((a, b) => new Date(a.date) - new Date(b.date));
    let firstDate = orderArray[0]?.date || null;
    let lastDate = orderArray[orderArray.length - 1]?.date || null;
    return {firstDate, lastDate};
}

function updateChart(start, end) {
    $('#reportrange span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));

    var dates = {
        start: start.format('YYYY-MM-DD'),
        end: end.format('YYYY-MM-DD')
    };

    var length = end.diff(start, 'days') + 1;
    if (length < 0 || length > 60) {
        const messageLinechart = document.getElementById('message-linechart');
        messageLinechart.style.color = 'red';
        messageLinechart.innerText = "The maximum length for date range picker is 60 days!";
        return;
    } else {
        const messageLinechart = document.getElementById('message-linechart');
        messageLinechart.innerText = "";
    }
    $.ajax({
        type: 'POST',
        url: 'dashboard',
        data: JSON.stringify(dates),
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            console.log('Data received from servlet:', data);
            successOrder = [];
            allOrder = [];
            for (var dateKey in data.successOrderCount) {
                if (data.successOrderCount.hasOwnProperty(dateKey)) {
                    addSuccessItem(dateKey, data.successOrderCount[dateKey]);
                }
            }
            for (var dateKey in data.allOrderCount) {
                if (data.allOrderCount.hasOwnProperty(dateKey)) {
                    addAllItem(dateKey, data.allOrderCount[dateKey]);
                }
            }
            drawLineChart(allOrder, successOrder, start, end);
        },
        error: function (xhr, status, error) {
            console.error('Error sending request to servlet:', error);
            // Handle errors if needed
        }
    });
}

$(document).ready(function () {
    $('#reportrange').daterangepicker({
        startDate: moment().subtract(6, 'days'),
        endDate: moment(),
        ranges: {
            'Today': [moment(), moment()],
            'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
            'Last 7 Days': [moment().subtract(6, 'days'), moment()],
            'Last 30 Days': [moment().subtract(29, 'days'), moment()],
            'This Month': [moment().startOf('month'), moment().endOf('month')],
            'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
        }
    }, function (start, end) {
        updateChart(start, end);
    });

    updateChart($('#reportrange').data('daterangepicker').startDate, $('#reportrange').data('daterangepicker').endDate);
});

function drawLineChart(allOrder, successOrder, start, end) {
    if (myChart) {
        myChart.destroy();
    }
    var length = end.diff(start, 'days') + 1;
    var trimmedData1 = allOrder.slice(-length);
    var trimmedData2 = successOrder.slice(-length);

    var labels = trimmedData2.map(entry => entry.date);
    var values1 = trimmedData1.map(entry => entry.value);
    var values2 = trimmedData2.map(entry => entry.value);
    console.log(values2);

    const ctx = document.getElementById('myChart').getContext('2d');
    myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'All',
                    data: values1,
                    fill: false,
                    borderColor: '#FF9500',
                    tension: 0.1,
                    borderWidth: 2,
                    pointBackgroundColor: '#FF9500',
                    pointBorderColor: '#FFF',
                    pointBorderWidth: 2,
                    pointRadius: 4,
                    pointHoverRadius: 6,
                    pointHoverBackgroundColor: '#FF9500',
                    pointHoverBorderColor: '#FFF',
                    pointHoverBorderWidth: 3
                },
                {
                    label: 'Successful',
                    data: values2,
                    fill: false,
                    borderColor: '#0BA800',
                    tension: 0.1,
                    borderWidth: 2,
                    pointBackgroundColor: '#0BA800',
                    pointBorderColor: '#FFF',
                    pointBorderWidth: 2,
                    pointRadius: 4,
                    pointHoverRadius: 6,
                    pointHoverBackgroundColor: '#0BA800',
                    pointHoverBorderColor: '#FFF',
                    pointHoverBorderWidth: 3
                }
            ]
        },
        options: {
            plugins: {
                legend: {
                    labels: {
                        usePointStyle: true,
                        pointStyle: 'rectRounded',
                        font: {
                            size: 14,
                            weight: 'bold',
                            family: 'Arial'
                        },
                        color: '#333'
                    }
                }
            },
            scales: {
                x: {
                    title: {
                        display: true,
                        text: 'Date',
                        color: '#000',
                        font: {
                            family: 'Arial',
                            size: 16,
                            weight: 'bold',
                            style: 'italic'
                        },
                        padding: {top: 10, left: 0, right: 0, bottom: 0}
                    }
                },
                y: {
                    title: {
                        display: true,
                        text: 'Order',
                        color: '#000',
                        font: {
                            family: 'Arial',
                            size: 16,
                            weight: 'bold',
                            style: 'italic'
                        },
                        padding: {top: 0, left: 0, right: 0, bottom: 10}
                    }
                }
            }
        }
    });
}

