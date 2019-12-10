var mealAjaxUrl = "ajax/profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "ajax/profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "rowCallback": function( row, data ) {
                $(row).addClass(data.excess ? "excess" : "not-excess");
            },
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type) {
                        if (type === "display") {
                            let mealDate = new Date(date);
                            let day = mealDate.getDate() < 10 ? "0" + mealDate.getDate() : mealDate.getDate();
                            let month = mealDate.getMonth() + 1 < 10 ? "0" + (mealDate.getMonth() + 1) : mealDate.getMonth() + 1;
                            let year = mealDate.getFullYear();
                            let hour = mealDate.getHours() < 10 ? "0" + mealDate.getHours() : mealDate.getHours();
                            let minute = mealDate.getMinutes() < 10 ? "0" + mealDate.getMinutes() : mealDate.getMinutes();
                            return day + "." + month + "." + year + " " + hour + ":" + minute;
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        }),
        updateTable: function () {
            $.get(mealAjaxUrl, updateTableByData);
        }
    });
});