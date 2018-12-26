<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>CICS-集成问题统计系统</title>
    <link href="style/authority/login_css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="scripts/integration.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            /*回车事件*/
            $(document).keydown(function (event) {
                if (event.keyCode == 13) {
                    $("#login_btn").click();
                }
            });
        });


        function loginSubmit() {
            if ("" == $("input[name='userName']").val() | "" == $("input[name='password']").val()) {
                alert("用户名和密码不允许为空");
                return;
            }
            $.ajax({
                url: "${authurl}",
                type: "POST",
                data: JSON.stringify($("#user").serializeObject()),
                contentType: "application/json",  //缺失会出现URL编码，无法转成json对象
                success: function (data) {
                    if (data.code == 0) {
                        sessionStorage.setItem("token", data.data.token);
                        sessionStorage.setItem("userid",data.data.id);
                        $(location).attr("href", data.data.redirectUrl+"?id="+data.data.id);
                    } else {
                        $(location).attr("href", "/integration/login");
                    }
                },
                error: function (error) {
                    $(location).attr("href", "/integration/login");
                }
            });
        };
    </script>
</head>
<body>
<div id="login_center">
    <div id="login_area">
        <div id="login_box">
            <div id="login_form">
                <form id="user">
                    <div id="login_tip">
                        <span id="login_err" class="sty_txt2"></span>
                    </div>
                    <div>
                        用户名：<input type="text" name="userName" class="username" id="name">
                    </div>
                    <div>
                        密&nbsp;&nbsp;&nbsp;&nbsp;码：<input type="password" name="password" class="pwd" id="pwd">
                    </div>
                    <div id="btn_area">
                        <input type="button" class="login_btn" id="login_btn" value="登  录" onClick="loginSubmit()">
                        <input type="reset" class="login_btn" id="login_ret" value="重 置">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>