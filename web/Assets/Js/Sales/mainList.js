document.addEventListener('DOMContentLoaded', () => {
    $("#data__filter-btn").click(function (event) {
        $("#popup").css("display", "flex");
        $("#clode_popup").click(function (event) {
            $("#popup").css("display", "none");
            $("#clode_popup").off('click');
        });
    });
    $("#all").click(function () {
        var newURL = replaceUrlParam(window.location.href, 'status', '3');

        window.location.href = newURL;
    });
    $("#pending").click(function () {
        var newURL = replaceUrlParam(window.location.href, 'status', '0');

        window.location.href = newURL;
    });
    $("#paid").click(function () {
        var newURL = replaceUrlParam(window.location.href, 'status', '1');

        window.location.href = newURL;
    });
    $("#cancel").click(function () {
        var newURL = replaceUrlParam(window.location.href, 'status', '2');

        window.location.href = newURL;
    });

    function getUrlParams() {
        // Get the URL query string
        const queryString = window.location.search;

        // Parse the query string
        const urlParams = new URLSearchParams(queryString);

        // Create an object to store the parameters
        let params = {};
        urlParams.forEach((value, key) => {
            params[key] = value;
        });

        return params;
    }
    $(".paginition-actions-previous").click(function () {
           var newURL = replaceUrlParam(window.location.href, 'page', Number( (getUrlParams()['page'] == undefined ? 1  : getUrlParams()['page']))-1);

        window.location.href = newURL;
    });
     $(".paginition-actions-next").click(function () {
         var newURL = replaceUrlParam(window.location.href, 'page', Number( (getUrlParams()['page'] == undefined ? 1  : getUrlParams()['page']))+1);

        window.location.href = newURL;
    });
    const monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    const dayEndings = ["st", "nd", "rd, ", "th"];

//function to get ending of day
    function getDayEnding(day) {
        if (day > 3 && day < 21)
            return 'th';
        switch (day % 10) {
            case 1:
                return 'st';
            case 2:
                return 'nd';
            case 3:
                return 'rd';
            default:
                return 'th';
        }
    }

//function that take hour, minute, second and return a string of time in AM/PM format
    function formatTime(hour, minute, second) {
        let time = '';
        if (hour > 12) {
            time += String(hour - 12);
        } else {
            time += String(hour);
        }
        time += ':';
        time += String(minute).padStart(2, '0');
        time += hour >= 12 ? ' PM' : ' AM';
        return time;
    }

    function formatLocalDateTime(dateTimeString) {
        const dateTime = new Date(dateTimeString);
        const year = dateTime.getFullYear();
        const monthName = monthNames[dateTime.getMonth()];
        const day = dateTime.getDate() + getDayEnding(dateTime.getDate());
        const hour = String(dateTime.getHours()).padStart(2, '0');
        const minute = String(dateTime.getMinutes()).padStart(2, '0');
        const second = String(dateTime.getSeconds()).padStart(2, '0');
        const time = formatTime(dateTime.getHours(), dateTime.getMinutes(), dateTime.getSeconds());
        return `${year} ${monthName} ${day} <br/> ${time}`;
    }


    const datetimes = document.querySelectorAll('.datetime');

    datetimes.forEach(datetime => {
        datetime.innerHTML = formatLocalDateTime(datetime.innerText);
    });

    let sidebar = document.querySelector(".marketing-sidebar");
    let closeBtn = document.querySelector("#marketing-btn");
    let main = document.querySelector(".main");

    closeBtn.addEventListener("click", () => {
        main.classList.toggle("main-open");
    });
    oTable = $('#data').DataTable({
        fixedColumns: {
            start: 2,
            end: 2
        },
        paging: false,
        scrollCollapse: false,
        scrollX: true,
        "info": false

    });

    var $container = $(".data__list");
    var $scroller = $(".dt-scroll-body");
    ;

    bindDragScroll($container, $scroller);
    function bindDragScroll($container, $scroller) {

        var $window = $(window);

        var x = 0;
        var y = 0;

        var x2 = 0;
        var y2 = 0;
        var t = 0;

        $container.on("mousedown", down);
        $container.on("click", preventDefault);
        $scroller.on("mousewheel", horizontalMouseWheel); // prevent macbook trigger prev/next page while scrolling

        function down(evt) {
            //alert("down");
            if (evt.button === 0) {

                t = Date.now();
                x = x2 = evt.pageX;
                y = y2 = evt.pageY;

                $container.addClass("down");
                $window.on("mousemove", move);
                $window.on("mouseup", up);

                evt.preventDefault();

            }

        }

        function move(evt) {
            // alert("move");
            if ($container.hasClass("down")) {

                var _x = evt.pageX;
                var _y = evt.pageY;
                var deltaX = _x - x;
                var deltaY = _y - y;

                $scroller[0].scrollLeft -= deltaX;

                x = _x;
                y = _y;

            }

        }

        function up(evt) {

            $window.off("mousemove", move);
            $window.off("mouseup", up);

            var deltaT = Date.now() - t;
            var deltaX = evt.pageX - x2;
            var deltaY = evt.pageY - y2;
            if (deltaT <= 300) {
                $scroller.stop().animate({
                    scrollTop: "-=" + deltaY * 3,
                    scrollLeft: "-=" + deltaX * 3
                }, 500, function (x, t, b, c, d) {
                    // easeOutCirc function from http://gsgd.co.uk/sandbox/jquery/easing/
                    return c * Math.sqrt(1 - (t = t / d - 1) * t) + b;
                });
            }

            t = 0;

            $container.removeClass("down");

        }

        function preventDefault(evt) {
            if (x2 !== evt.pageX || y2 !== evt.pageY) {
                evt.preventDefault();
                return false;
            }
        }

        function horizontalMouseWheel(evt) {
            evt = evt.originalEvent;
            var x = $scroller.scrollLeft();
            var max = $scroller[0].scrollWidth - $scroller[0].offsetWidth;
            var dir = (evt.deltaX || evt.wheelDeltaX);
            var stop = dir > 0 ? x >= max : x <= 0;
            if (stop && dir) {
                evt.preventDefault();
            }
        }

    }



    $('#myInputTextField').keyup(function () {
        oTable.search($(this).val()).draw();
    });
    $('#pageSelect').on("change", function (event) {
        var newURL = replaceUrlParam(window.location.href, 'page', $(this).val());

        window.location.href = newURL;
    });
    $('#pageSelect').on("change", function (event) {
        var newURL = replaceUrlParam(window.location.href, 'page', $(this).val());

        window.location.href = newURL;
    });
});
function replaceUrlParam(url, paramName, paramValue)
{
    if (paramValue === null) {
        paramValue = '';
    }
    var pattern = new RegExp('\\b(' + paramName + '=).*?(&|#|$)');
    if (url.search(pattern) >= 0) {
        return url.replace(pattern, '$1' + paramValue + '$2');
    }
    url = url.replace(/[?#]$/, '');
    return url + (url.indexOf('?') > 0 ? '&' : '?') + paramName + '=' + paramValue;
}