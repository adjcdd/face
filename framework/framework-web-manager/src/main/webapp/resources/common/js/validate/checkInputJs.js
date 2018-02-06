//去除input框的空格
$(function () {
    $("input").blur(function () {
        //判断文件框和日期框不去除空格
        var classList = $(this)[0].classList;
        var type = $(this)[0].type;
        var result = classList[1]=="wicon";
        if(type!="file"&&!result){
            var trimVal = $(this).val().replace(/\s/g,'');
            $(this).val(trimVal);
        }
    });
});