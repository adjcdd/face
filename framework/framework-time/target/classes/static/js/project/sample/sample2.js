var sample2Obj = {
    
    "loadVal" : function () {
        $.ajax({
            type : "get",
            url : "/grgbanking/time/testDb",
            success : function(data){
                $("#valTab").append($("#showValueRender").render(data));
            },
            error : function(jqXHR, textStatus, errorThrown){

            }
        });
    }
    
}