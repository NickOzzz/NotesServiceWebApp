     function init() {
        var username = document.body.getAttribute("us");
        var receiverUser = document.body.getAttribute("receiver");
        document.getElementById("icon-button").innerHTML = username[0].toUpperCase();
        document.getElementById("main-section-add-note-checkbox").checked = true;
        if (username != receiverUser) {
             document.getElementById("main-section-select-user").value = receiverUser;
        }
        document.getElementById("refresh-users-button-scroll").addEventListener("mouseover", function (){
             document.getElementById("refresh-users-button-scroll").src = "/images/refresh-hover.png";
        });
        document.getElementById("refresh-users-button-scroll").addEventListener("mouseout", function (){
             document.getElementById("refresh-users-button-scroll").src = "/images/refresh.png";
        });
        if (window.scrollY >= 195) {
             $("#refresh-users-button-scroll").css("visibility", "visible");
        }
        window.addEventListener("scroll", function (event){
                if (window.scrollY >= 195) {
                    $("#refresh-users-button-scroll").css("visibility", "visible");
                }
                else {
                    $("#refresh-users-button-scroll").css("visibility", "hidden");
                }
        })
        $("#main-section-select-user-note").css("visibility", "visible");
        $("#main-section-select-user").css("visibility", "visible");
        $("#main-section-add-note-checkbox").change(function() {
            if (this.checked) {
               $("#main-section-select-user-note").css("visibility", "visible");
               $("#main-section-select-user").css("visibility", "visible");
            }
            else {
               $("#main-section-select-user-note").css("visibility", "hidden");
               $("#main-section-select-user").css("visibility", "hidden");
            }
        });

        initMessages();
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

     function editUpdateForm(messageId, message, receiverUser, checked) {
        scrollToTheTop();

        $("#filter-users-field").css("visibility", "hidden");
        $("#filter-users-button").css("visibility", "hidden");
        $("#clear-filter-users-button").css("visibility", "hidden");
        $("#filter-users-field").css("position", "absolute");
        $("#filter-users-button").css("position", "absolute");
        $("#clear-filter-users-button").css("position", "absolute");

        $("body").css("background", "#485248");
        $("#main-section-add-note-button").css("visibility", "hidden");
        $("#main-section-add-note-textarea").css("background-color", "#485248");

        var scrollPosition = (Number(window.scrollY) + 100).toString();
        $("#update-note-popup").css("top", scrollPosition + "px");
        $("#update-note-popup").css("visibility", "visible");

        document.getElementById("update-note-input").value = messageId;
        document.getElementById("update-note-textarea").value = message;

        var checked = (checked === "true");
        document.getElementById("update-note-popup-checkbox").checked = checked;
        document.getElementById("update-note-popup-select-user").value = receiverUser;

        if (checked) {
            $("#update-note-popup-select-user-note").css("visibility", "visible");
            $("#update-note-popup-select-user").css("visibility", "visible");
        }

        $("#update-note-popup-checkbox").change(function() {
                 if (this.checked) {
                     $("#update-note-popup-select-user-note").css("visibility", "visible");
                     $("#update-note-popup-select-user").css("visibility", "visible");
                 }
                 else {
                     $("#update-note-popup-select-user-note").css("visibility", "hidden");
                     $("#update-note-popup-select-user").css("visibility", "hidden");
                 }});

        $("#update-note-popup-checkbox-replace-image").change(function() {
                         if (this.checked) {
                             $("#update-note-popup-pick-file").css("visibility", "visible");
                             $("#update-note-popup-size-limit-text").css("visibility", "visible");
                         }
                         else {
                             $("#update-note-popup-pick-file").css("visibility", "hidden");
                             $("#update-note-popup-size-limit-text").css("visibility", "hidden");
                         }});
    }

    function closeUpdateForm() {
        $("body").css("background", "");
        $("#main-section-add-note-button").css("visibility", "visible");
        $("#main-section-add-note-textarea").css("background-color", "#606b60");
        $("#update-note-popup").css("visibility", "hidden");
        $("#update-note-popup-select-user-note").css("visibility", "hidden");
        $("#update-note-popup-select-user").css("visibility", "hidden");
        $("#update-note-popup-pick-file").css("visibility", "hidden");
        $("#update-note-popup-size-limit-text").css("visibility", "hidden");
        $("#filter-users-field").css("visibility", "visible");
        $("#filter-users-button").css("visibility", "visible");
        $("#clear-filter-users-button").css("visibility", "visible");
        $("#filter-users-field").css("position", "relative");
        $("#filter-users-button").css("position", "relative");
        $("#clear-filter-users-button").css("position", "relative");
        document.getElementById("update-note-popup-checkbox-replace-image").checked = false;
    }

    function scrollToTheTop() {
        $('html, body').animate({
               scrollTop: $("body").offset().top
        }, 400);
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

    function initMessages() {
       var element = document.getElementsByTagName("tbody")[0];

       for (var child of element.children) {
           var colorAttribute = child.getAttribute("color");
           child.style.background = colorAttribute;
       }
    }

    function findChat(creator) {
       document.getElementById("filter-users-field").value = creator;
       document.getElementById("filter-users-button").click();
    }

    function openLink(link) {
        window.open(link, "_blank").focus();
    }

    function refresh() {
        window.location.reload();
    }