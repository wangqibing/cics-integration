<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
    <link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="scripts/authority/commonAll.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.js"></script>
    <script type="text/javascript" src="scripts/fancybox/jquery.fancybox-1.3.4.pack.js"></script>
    <script type="text/javascript" src="scripts/integration.js"></script>
    <link rel="stylesheet" type="text/css" href="style/authority/jquery.fancybox-1.3.4.css" media="screen"></link>
    <script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
    <script type="text/javascript" src="scripts/integration.js"></script>
    <title>信息管理系统</title>
    <script type="text/javascript">
        function fancyboxInit() {
            /** 新增   **/
            $("#addBtn").fancybox({
                'href': 'house_edit.html',
                'width': 733,
                'height': 530,
                'type': 'iframe',
                'hideOnOverlayClick': false,
                'showCloseButton': false,
                'onClosed': function () {
                    window.location.href = 'house_list.html';
                }
            });

            /** 导入  **/
            $("#importBtn").fancybox({
                'href': '/xngzf/archives/importFangyuan.action',
                'width': 633,
                'height': 260,
                'type': 'iframe',
                'hideOnOverlayClick': false,
                'showCloseButton': false,
                'onClosed': function () {
                    window.location.href = 'question_list';
                }
            });

            /**编辑   **/
            $("a").fancybox({
                'width': 700,
                'height': 1200,
                'type': 'iframe',
                'hideOnOverlayClick': false,
                'showCloseButton': false,
                'onClosed': function () {
                    window.location.href = 'question_list';
                }
            });
        }

        /** 用户角色   **/
        var userRole = '';

        /**
         *加载初始化数据加载
         **/
        function loadInitData() {
            getDistinctSysName();
            getCode();
            searchQuestionList();
            fancyboxInit();//加载fancybox相关效果
        }
        /**
         *  初始化获取码值
         **/
        function getCode() {
            var data = ajaxPost("/integration/codetable/findcodebycodearea", "questionResolveStatus");
            for (var index in data) {
                $('#questionStatus').append("<option value=" + data[index].codeValue + ">" + data[index].codeName + "</option>");
            }
        }

        /**
         * 获取不同系统的码值
         * **/
        function getDistinctSysName() {
            var data = ajaxPost("/integration/sys/finddistinct");
            for (var index in data.sysInfoVos) {
                if (data.sysInfoVos[index].id == sessionStorage.getItem("userid")) {
                    $('#systemSelect').append("<option value=" + data.sysInfoVos[index].id + " selected>" + data.sysInfoVos[index].sysCname + "</option>");
                } else {
                    $('#systemSelect').append("<option value=" + data.sysInfoVos[index].id + ">" + data.sysInfoVos[index].sysCname + "</option>");
                }
            }
        }
        //将码值转化对应的码值翻译
        function formatCode(codeList, code) {
            for (var index in codeList) {
                if (codeList[index].codeValue == code) {
                    return codeList[index].codeName;
                }
            }
        }
        //获取系统对应人员的列表
        function getDistinctUser() {
            $('#dutyUser').html("<option value=''>--请选择--</option>");
            var data = ajaxPost("/integration/sys/finduserbysys", $("#systemSelect").val());
            for (var index in data.userInfoVos) {
                $('#dutyUser').append("<option value=" + data.userInfoVos[index].id + ">" + data.userInfoVos[index].userCname + "</option>");
            }
        }

        /** 初始化加载页面问题 **/
        function searchQuestionList(page) {
            //获取请求数据
            var userid = sessionStorage.getItem("userid");
            var authority = ajaxPost("/integration/user/searchauthority", userid).authority;
            var systemSelect = $("#systemSelect").val();
            var dutyUser = $("#dutyUser").val();
            var presentTime = $("#presentTime").val();
            var questionStatus = $("#questionStatus").val();
            var jsonData = {};
            if (systemSelect) {
                jsonData.userSys = systemSelect;
            }
            if (dutyUser) {
                jsonData.userid = dutyUser;
            }
            if (presentTime) {
                jsonData.presentTime = presentTime;
            }
            if (questionStatus) {
                jsonData.status = questionStatus;
            }
            if (page) {
                if (page < 1) {
                    page = 1;
                }
                jsonData.page = page;
            }
            var allData = ajaxPost("/integration/question/pagelist", JSON.stringify(jsonData));
            var data = allData.questionList;
            var questionValideStatus = ajaxPost("/integration/codetable/findcodebycodearea", "questionValideStatus");
            var questionResolveStatus = ajaxPost("/integration/codetable/findcodebycodearea", "questionResolveStatus");
            $("#question_list").html("");
            for (var index in data) {
                $("#question_list").append("<tr id='tr_" + index + "' style='background: rgb(255, 255, 255)'></tr>");
                $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'><input type='checkbox' name='question_id' value=" + data[index].id + "/></td>");
                $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + data[index].questionSysCname + "</td>");
                $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + data[index].questionPersonCname + "</td>");
                $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + formatCode(questionValideStatus, data[index].questionValideStatus) + "</td>");
                $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + data[index].resolveSysCname + "</td>");
                $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + data[index].questionSysCname + "</td>");
                $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + formatCode(questionResolveStatus, data[index].resolveStatus) + "</td>");
                if (undefined == data[index].resolveDutyPersonCname) {
                    $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>无</td>");
                } else {
                    $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + data[index].resolveDutyPersonCname + "</td>");
                }

                if (undefined == data[index].resolveDepPersonCname) {
                    $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>无</td>");
                } else {
                    $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + data[index].resolveDepPersonCname + "</td>");
                }
                $("#question_list > #tr_" + index).append("<td style='color: rgb(144, 144, 144);'>" + data[index].createTime + "</td>");
                if(0 == authority || userid == data[index].resolveDutyPerson){
                    $("#question_list > #tr_" + index).append("<td> <a class='detail' href='question_edit?question_id="+data[index].id+"&&operator=search&&userid="+userid+"' style='color:blue'>详情</a> &nbsp;&nbsp;&nbsp;<a class='edit' href='question_edit?question_id="+data[index].id+"&&operator=edit&&userid="+userid+"' style='color:blue'>编辑</a> </td>");
                }else{
                    $("#question_list > #tr_" + index).append("<td> <a class='detail' href='question_edit?question_id="+data[index].id+"&&operator=search&&userid="+userid+"' style='color:blue'>详情</a> &nbsp;&nbsp;&nbsp;<a style='#909090:'>编辑</a> </td>");
                }
            }
            $("#totalElements").html(allData.totalElements);
            $("#page").html(allData.currentPage + "/" + allData.totalPage);
            //添加翻页功能 注意点动态添加点击时间，正常的写法会存在重复添加事件的情况发生
            $("#firstPage").die().live("click",function () {
                searchQuestionList();
            })
            $("#upPage").die().live("click",function () {
                searchQuestionList(allData.currentPage-1)
            })
            $("#nextPage").die().live("click",function () {
                searchQuestionList(allData.currentPage+1)
            })
            $("#endPage").die().live("click",function () {
                searchQuestionList(allData.totalPage)
            })


        }

        function jumpInputPage() {
            var page = $("#jumpNumTxt").val();
            searchQuestionList(page);
        }
    </script>
    <style>
        .alt td {
            background: black !important;
        }
    </style>
</head>
<body onload="loadInitData()">
<form id="submitForm" name="submitForm" action="" method="post">
    <input type="hidden" name="allIDCheck" value="" id="allIDCheck"/>
    <input type="hidden" name="fangyuanEntity.fyXqName" value="" id="fyXqName"/>

    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center" name="searchParam">
                        系统
                        <select name="systemSelect" id="systemSelect" class="ui_select01" onchange="getDistinctUser();">
                            <option value=""
                            >--请选择--
                            </option>
                        </select>
                        问题提出人员
                        <select name="dutyUser" id="dutyUser" class="ui_select01">
                            <option value="">--请选择--</option>
                        </select>
                        提出时间
                        <input id="presentTime" name="presentTime" type="date"/>
                        问题解决状态
                        <select name="questionStatus" id="questionStatus" class="ui_select01">
                            <option value="">--请选择--</option>
                        </select>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01" onclick="searchQuestionList();"/>
                        <input type="button" value="新增" class="ui_input_btn01" id="addBtn"/>
                        <input type="button" value="导入" class="ui_input_btn01" id="importBtn"/>
                        <input type="button" value="导出" class="ui_input_btn01" onclick="exportExcel();"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" id="question_table" cellspacing="0" cellpadding="0" width="100%" align="center"
                       border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" onclick="selectOrClearAllCheckbox(this);"/>
                        </th>
                        <th>问题提出系统</th>
                        <th>问题提出人</th>
                        <th>验证结果</th>
                        <th>问题解决系统</th>
                        <th>问题描述</th>
                        <th>解决状态</th>
                        <th>责任人</th>
                        <th>依赖人员</th>
                        <th>提出时间</th>
                        <th>备注信息</th>
                    </tr>
                    <tbody id="question_list"></tbody>

                </table>
            </div>
            <div class="ui_tb_h30">
                <div class="ui_flt" style="height: 30px; line-height: 30px;">
                    共有
                    <span class="ui_txt_bold04" id="totalElements">90</span>
                    条记录，当前第
                    <span class="ui_txt_bold04" id="page">1
						/
						9</span>
                    页
                </div>
                <div class="ui_frt">
                    <!--    如果是第一页，则只显示下一页、尾页 -->
                    <input type="button" value="首页" class="ui_input_btn01" id="firstPage"/>
                    <input type="button" value="上一页" class="ui_input_btn01"  id="upPage"/>
                    <input type="button" value="下一页" class="ui_input_btn01" id="nextPage"/>
                    <input type="button" value="尾页" class="ui_input_btn01" id="endPage"/>
                    <!--     如果是最后一页，则只显示首页、上一页 -->
                    转到第<input type="text" id="jumpNumTxt" class="ui_input_txt01"/>页
                    <input type="button" class="ui_input_btn01" value="跳转" onclick="jumpInputPage();"/>
                </div>
            </div>
        </div>
    </div>
</form>

</body>
</html>
