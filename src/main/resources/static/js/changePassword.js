function init() {
    var username = document.body.getAttribute("us");
    document.getElementById("icon-button").innerHTML = username[0].toUpperCase();
}

function loading(username) {
    setUsername(username);
    $("#main-password-holder").css("visibility", "hidden");
    $("body").css("background", "#485248");
    $("#loading-spinner").css("visibility", "visible");
}

function setUsername(username) {
      document.getElementById("username-input").value = username;
}

function openInfo() {
        var visible = ($("#user-info-box").css("visibility") === "visible");
        if (visible) {
           $("#user-info-box").css("visibility", "hidden");
           $("#icon-button").css("background-color", "#41c93a");
           $("#icon-button").css("border-color", "#41c93a");
           resetDeleteUserOption();
        } else {
           $("#user-info-box").css("visibility", "visible");
           $("#icon-button").css("background-color", "#32a02c");
           $("#icon-button").css("border-color", "#32a02c");
        }
}

function showDeleteButton() {
        if ($("#user-info-box-delete-button").css("visibility") === "hidden") {
            $("#user-info-box-delete-button").css("position", "relative");
            $("#user-info-box-delete-button").css("visibility", "visible");
            document.getElementById("user-info-box-pre-delete-button").innerHTML = "don't delete user";
        }
        else {
            resetDeleteUserOption();
        }
    }

function resetDeleteUserOption() {
    $("#user-info-box-delete-button").css("visibility", "hidden");
    $("#user-info-box-delete-button").css("position", "absolute");
    document.getElementById("user-info-box-pre-delete-button").innerHTML = "delete user";
}