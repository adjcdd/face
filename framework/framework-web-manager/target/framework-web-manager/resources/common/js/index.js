/**
 * Created by wmm on 2017/12/18 0018.
 */
$(function(){
    $("#menuList>ul>li>i").click(function(){
        var imgNum=$(this).attr("data-type");
        if(imgNum=="1"){
            $(this).attr("data-type","2");
            $(this).css("background-image","url(image/down.png)");
        }else{
            $(this).attr("data-type","1");
            $(this).css("background-image","url(image/right.png)");
        }
        $(this).siblings("ol").slideToggle();
    });
   /* $("#writeList").on("mouseover","li",function(){
        $(this).children("ol").css("display","block");
    });
    $("#writeList").on("mouseout","li",function(){
        $(this).children("ol").css("display","none");
    });*/
    $(".content").mCustomScrollbar({
        theme:"dark-3",
        scrollButtons:{enable:true}
    });
})