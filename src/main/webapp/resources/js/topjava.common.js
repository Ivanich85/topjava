var context, form;

function makeEditable(ctx) {
    context = ctx;
    form = $('#detailsForm');
    $(".delete").click(function () {
        if (confirm('Are you sure?')) {
            deleteRow($(this).attr("id"));
        }
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}

function add() {
    form.find(":input").val("");
    $("#editRow").modal();
}

function deleteRow(id) {
    $.ajax({
        url: context.ajaxUrl + id,
        type: "DELETE"
    }).done(function () {
        updateTable();
        successNoty("Deleted");
    });
}

function updateTable() {
    if (isFilterReset()) {
        updateTableWOFilter();
    } else {
        updateFilteredTable();
    }
}

function updateTableWOFilter() {
    $.get(context.ajaxUrl, function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}

function updateFilteredTable() {
    $.ajax({
        url: context.ajaxUrl + "filter",
        type: "GET",
        data: {
            startDate: $("input[name='startDate']").val(),
            endDate: $("input[name='endDate']").val(),
            startTime: $("input[name='startTime']").val(),
            endTime: $("input[name='endTime']").val(),
        }
    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
        successNoty("Filtered");
    });
}

function isFilterReset() {
    let result = true;
    $("#filters").find($("input")).each(function(){
        if ($(this).val()) {
            result = false;
        }
    });
    return result;
}

function resetFilters() {
    $("#filters").find($("input")).each(function(){
        let elem = $(this);
        if (elem.val()) {
            elem.val("");
        }
    });
    updateTable();
    successNoty("Filters were reset");
}

function save() {
    $.ajax({
        type: "POST",
        url: context.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateTable();
        successNoty("Saved");
    });
}

let failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}