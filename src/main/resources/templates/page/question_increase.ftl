<!DOCTYPE html>
<html>
<head>
    <title>问题详情界面</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
    <link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts/authority/commonAll.js"></script>
    <script type="text/javascript" src="scripts/jquery/jquery-1.4.4.min.js"></script>
    <script src="scripts/My97DatePicker/WdatePicker.js" type="text/javascript" defer="defer"></script>
    <script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
    <script type="text/javascript" src="scripts/integration.js"></script>
    <script type="text/javascript">
        var userid = sessionStorage.getItem("userid");
        var questionValideStatus = ajaxPost("/integration/codetable/findcodebycodearea", "questionValideStatus");
        var questionResolveStatus = ajaxPost("/integration/codetable/findcodebycodearea", "questionResolveStatus");
        var sysList = ajaxPost("/integration/sys/finddistinct").sysInfoVos;
        var userList = ajaxPost("/integration/user/searchusercode");
        /**
         *加载初始化数据加载
         **/
        function loadInitData() {
            initCodeTableValue();
            initDefaultValue();
            initBindEvent();
//            getDistinctSysName();
//            getCode();
//            searchQuestionList();
        }


        //初始化加载codeTable数据
        function initCodeTableValue() {

            //初始页面下拉框码值
            $("#questionSys").html("<option value=''>--请选择--</option>");
            $("#questionPerson").html("<option value=''>--请选择--</option>");
            $("#questionValideStatus").html("<option value=''>--请选择--</option>");
            $("#resolveSys").html("<option value=''>--请选择--</option>");
            $("#resolveStatus").html("<option value=''>--请选择--</option>");
            $("#resolveDutyPerson").html("<option value=''>--请选择--</option>");
            $.each(sysList, function () {
                $("#questionSys").append("<option value='" + this.id + "'>" + this.sysCname + "</option>")
                $("#resolveSys").append("<option value='" + this.id + "'>" + this.sysCname + "</option>")
            })
            $.each(userList, function () {
                $("#questionPerson").append("<option value='" + this.codeValue + "'>" + this.codeName + "</option>")
                $("#resolveDutyPerson").append("<option value='" + this.codeValue + "'>" + this.codeName + "</option>")
            })
            $.each(questionValideStatus, function () {
                $("#questionValideStatus").append("<option value='" + this.codeValue + "'>" + this.codeName + "</option>")
            })
            $.each(questionResolveStatus, function () {
                $("#resolveStatus").append("<option value='" + this.codeValue + "'>" + this.codeName + "</option>")
            })

        }

        function initDefaultValue() {
            //初始化时间
            var currentdate = new Date().Format("yyyy-MM-dd");
            $("#createTime").val(currentdate)
            //初始化问题提出人
            $("#questionPerson").val(userid)
            //初始化系统

        }


        function getQuestionPerson(){
            $('#resolveDutyPerson').html("<option value=''>--请选择--</option>");
            var data = ajaxPost("/integration/sys/finduserbysys", $("#resolveSys").val());
            for (var index in data.userInfoVos) {
                $('#resolveDutyPerson').append("<option value=" + data.userInfoVos[index].id + ">" + data.userInfoVos[index].userCname + "</option>");
            }
        }

        function initBindEvent(){

            $("#submitbutton").die().live("click",function () {
                var data = ajaxPost("/integration/question/question_increase", JSON.stringify($("#submitForm").serializeObject()));
                window.location.href=data.redirectUrl;
            })


            $("#cancelbutton").die().live("click",function () {
                window.location.href="question_workdesk";
            })
        }
    </script>
</head>
<body onload="loadInitData()">
<form id="submitForm" name="submitForm" action="/xngzf/archives/initFangyuan.action" method="post">
    <input type="hidden" name="id" value="" id="id"/>
    <div id="container">
        <div id="nav_links">
            当前位置：问题跟踪&nbsp;>&nbsp;<span style="color: #1A5CC6;">问题详情</span>
            <div id="page_close">
                <a href="javascript:parent.$.fancybox.close();">
                    <img src="images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">

                <tr>
                    <td class="ui_text_rt">问题提出系统</td>
                    <td class="ui_text_lt">
                        <select name="questionSys" id="questionSys" class="ui_select01">
                            <option value="">--请选择--</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt">问题提出人</td>
                    <td class="ui_text_lt">
                        <select name="questionPerson" id="questionPerson" class="ui_select01">
                            <option value="">--请选择--</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt">问题解决系统</td>
                    <td class="ui_text_lt">
                        <select name="resolveSys" id="resolveSys" class="ui_select01" onchange="getQuestionPerson()">
                            <option value="">--请选择--</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt">问题解决负责人</td>
                    <td class="ui_text_lt">
                        <select name="resolveDutyPerson" id="resolveDutyPerson" class="ui_select01">
                            <option value="">--请选择--</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt">接口名称</td>
                    <td class="ui_text_lt">
                        <input type="text" id="resolveInterface" name="resolveInterface" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt">问题描述</td>
                    <td class="ui_text_lt">
                        <textarea type="text" id="questionDescrbe" name="questionDescrbe"
                                  class="ui_textarea_txt01"></textarea>
                    </td>
                </tr>

                <tr>
                    <td class="ui_text_rt">问题提出时间</td>
                    <td class="ui_text_lt">
                        <input type="text" id="createTime" name="createTime" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt">备注</td>
                    <td class="ui_text_lt">
                        <textarea type="text" id="remark" name="remark" class="ui_textarea_txt01"></textarea>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td class="ui_text_lt">
                        &nbsp;<input id="submitbutton" type="button" value="提交" class="ui_input_btn01"/>
                        &nbsp;<input id="cancelbutton" type="button"  value="取消" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>

</body>
</html>