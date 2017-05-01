$("#queryButton").click(function () {
    // console.log($("#queryStr").val());

    $.ajax({
        type: "POST",
        url: "/query",
        data: $("#queryStr").val(),
        contentType: "application/json"
    }).done(function (results) {
        var res = results[0];
        console.log(res);
        $("#ansText").text(res.ansText);
        $("#matchedQnText").text(res.matchedQnText);
        $("#mainQnText").text(res.mainQnText);
    });
    return false;
});