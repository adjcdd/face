$(function () {
    // alert("Test Success!");
});

var indexObj = {
    "catchData" : function () {
        // alert("catch");
        alert(commonObj.getContextPath());
        $.ajax({
            type : "get",
            url : "/grgbanking/time/hytrix",
            success : function(data){
                $("#content").html(data);
            },
            error : function(jqXHR, textStatus, errorThrown){

            }
        });
    },
    "toSample1" : function () {
        location.href = "/grgbanking/time/toSample1";
    },
    "toSample2" : function () {
        location.href = "/grgbanking/time/toSample2";
    }


}