$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [ o[this.name] ];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};


//ajax的post请求
function  ajaxPost(requestUrl,data,redirectrul){
    var returndata;
    $.ajax({
        url:requestUrl,
        headers: {
            'Token':sessionStorage.getItem("token")
        },
        type:"POST",
        data:data,
        async:false,
        contentType:"application/json",  //缺失会出现URL编码，无法转成json对象
        success:function(data){
            if(data.code == 0){
                returndata = data.data;
                if(redirectrul){
                    window.href = redirectrul;
                    return;
                }
            }else{
                returndata =  data.msg;
            }

        },
        error: function(err) {
            returndata =  err;
        }

    });
    return returndata;
};

//格式化时间
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

//元素绑值
function bindValue(tagName,data) {
    if(tagName &&  data){
        var elements = $("#"+tagName).serializeArray();
        $.each(elements,function (i,element) {
            if(data[element.name]){
                $("[name="+element.name+"]").val(data[element.name]);
            }else{
                $("[name="+element.name+"]").val("");
            }
        })
    }else{
        alert("缺少相关参数");
        return;
    }


   
   
   
   
    // $.each(a, function() {
    //     if (o[this.name]) {
    //         if (!o[this.name].push) {
    //             o[this.name] = [ o[this.name] ];
    //         }
    //         o[this.name].push(this.value || '');
    //     } else {
    //         o[this.name] = this.value || '';
    //     }
    // });
    // return o;
};