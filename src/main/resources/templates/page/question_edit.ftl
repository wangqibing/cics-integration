<!DOCTYPE html>
<html>
<head>
<title>问题详情界面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="scripts/jquery/jquery-1.7.1.js"></script>
<link href="style/authority/basic_layout.css" rel="stylesheet" type="text/css">
<link href="style/authority/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="scripts/authority/commonAll.js"></script>
<script type="text/javascript" src="scripts/jquery/jquery-1.4.4.min.js"></script>
<script src="scripts/My97DatePicker/WdatePicker.js" type="text/javascript" defer="defer"></script>
<script type="text/javascript" src="scripts/artDialog/artDialog.js?skin=default"></script>
    <script type="text/javascript" src="scripts/integration.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		/*
		 * 提交
		 */
		$("#submitbutton").click(function() {
			if(validateForm()){
				checkFyFhSubmit();
			}
		});
		
		/*
		 * 取消
		 */
		$("#cancelbutton").click(function() {
			/**  关闭弹出iframe  **/
			window.parent.$.fancybox.close();
		});
		
		var result = 'null';
		if(result =='success'){
			/**  关闭弹出iframe  **/
			window.parent.$.fancybox.close();
		}
	});
	
	/** 问题编辑后提交问题 **/
	function checkFyFhSubmit(){
	    var fordata = JSON.stringify($("#submitForm").serializeObject());
//	    ajaxPost()
		if(true){
			// 异步判断该房室是否存在，如果已存在，给用户已提示哦
			$.ajax({
				type:"POST",
				url:"checkFyFhIsExists.action",
				data:{"fangyuanEntity.fyID":fyID,"fangyuanEntity.fyXqCode":fyXqCode, "fangyuanEntity.fyDhCode":fyDh, "fangyuanEntity.fyCh":fyCh, "fangyuanEntity.fyFh":fyFh},
				dataType : "text",
				success:function(data){
// 					alert(data);
					// 如果返回数据不为空，更改“房源信息”
					if(data=="1"){
						 art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'该房室在系统中已经存在哦，\n请维护其他房室数据', ok:true,});
						 $("#fyFh").css("background", "#EEE");
						 $("#fyFh").focus();
						 return false;
					}else{
						$("#submitForm").attr("action", "/xngzf/archives/saveOrUpdateFangyuan.action").submit();
					}
				}
			});
		}
		return true;
	}
	
	/** 表单验证  **/
	function validateForm(){
		if($("#id").val()==""){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'数据异常', ok:true,});
			return false;
		}
		if($("#createTime").val()==""){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'提出问题的提出时间', ok:true,});
			return false;
		}
		if($("#questionDescrbe").val()==""){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'提出问题的描述', ok:true,});
			return false;
		}
		if($("#questionPerson").val()==""){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'提出问题提出人', ok:true,});
			return false;
		}
		if($("#questionSys").val()==""){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'填写问题提出系统', ok:true,});
			return false;
		}
		if($("#questionValideStatus").val()==""){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'填写问题验证状态', ok:true,});
			return false;
		}
		if($("#resolveDutyPerson").val()==""){
			art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'填写问题负责人', ok:true,});
			return false;
		}
        if($("#resolveInterface").val()==""){
            art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'填写问题相关接口', ok:true,});
            return false;
        }
        if($("#resolveStatus").val()==""){
            art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'填写问题解决状态', ok:true,});
            return false;
        }
        if($("#resolveSys").val()==""){
            art.dialog({icon:'error', title:'友情提示', drag:false, resize:false, content:'填写解决系统', ok:true,});
            return false;
        }
		return true;
	}
	//初始化加载数据
	function loadQuestionDetail(){

	    var questionDetail = ${questionDetail};
	    initCodeTableValue();
        bindValue("submitForm",questionDetail);
        authorityOperator("${authority}","${operator}",questionDetail); //权限赋值
	}

	//初始化加载codeTable数据
    function initCodeTableValue() {
        var questionValideStatus = ajaxPost("/integration/codetable/findcodebycodearea", "questionValideStatus");
        var questionResolveStatus = ajaxPost("/integration/codetable/findcodebycodearea", "questionResolveStatus");
        var sysList = ajaxPost("/integration/sys/finddistinct").sysInfoVos;
        var userList = ajaxPost("/integration/user/searchusercode");
		//初始页面下拉框码值
        $("#questionSys").html("<option value=''>--请选择--</option>");
        $("#questionPerson").html("<option value=''>--请选择--</option>");
        $("#questionValideStatus").html("<option value=''>--请选择--</option>");
        $("#resolveSys").html("<option value=''>--请选择--</option>");
        $("#resolveStatus").html("<option value=''>--请选择--</option>");
        $("#resolveDutyPerson").html("<option value=''>--请选择--</option>");
        $.each(sysList,function(){
            $("#questionSys").append("<option value='"+this.id+"'>"+this.sysCname+"</option>")
            $("#resolveSys").append("<option value='"+this.id+"'>"+this.sysCname+"</option>")
		})
        $.each(userList,function(){
            $("#questionPerson").append("<option value='"+this.codeValue+"'>"+this.codeName+"</option>")
            $("#resolveDutyPerson").append("<option value='"+this.codeValue+"'>"+this.codeName+"</option>")
        })
        $.each(questionValideStatus,function(){
            $("#questionValideStatus").append("<option value='"+this.codeValue+"'>"+this.codeName+"</option>")
        })
        $.each(questionResolveStatus,function(){
            $("#resolveStatus").append("<option value='"+this.codeValue+"'>"+this.codeName+"</option>")
        })
    }
    //权限赋值操作
	function authorityOperator(authority,operator,questionDetail){
	    var userid = sessionStorage.getItem("userid");
        var elements = $("#submitForm").serializeArray();
        $.each(elements,function (i,element) {
            //添加颜色属性
            if((element.name == "resolveStatus") &&  ($("[name="+element.name+"]").val() != 0) ){
                $("[name="+element.name+"]").attr("style","color: red");
            }else if((element.name == "questionValideStatus") &&  ($("[name="+element.name+"]").val() != 1) ){
                $("[name="+element.name+"]").attr("style","color: red");
			} //questionValideStatus
			//权限置灰操作
            if(operator == "search"){
                $("[name="+element.name+"]").attr("disabled",true);
			}else if(operator == "edit"){
                if(0 == authority){
                    return true;
				}else{
                    if(element.name == "questionValideStatus"){
                        if(userid == questionDetail.questionPerson){
                            return true;
                        }else{
                            $("[name="+element.name+"]").attr("disabled",true);
                        }
                    }else if(element.name == "resolveStatus"){  //解决状态
                        if(userid == questionDetail.resolveDutyPerson){
                            return true;
                        }else{
                            $("[name="+element.name+"]").attr("disabled",true);
                        }
                    }else if(element.name == "resolveBack"){  //核心反馈
                        if(userid == questionDetail.resolveDutyPerson){
                            return true;
                        }else{
                            $("[name="+element.name+"]").attr("disabled",true);
                        }
                    }else if(element.name == "remark"){  //备注
                        if(userid == questionDetail.resolveDutyPerson){
                            return true;
                        }else{
                            $("[name="+element.name+"]").attr("disabled",true);
                        }
                    }else{
                        $("[name="+element.name+"]").attr("disabled",true);
                    }
				}
			}

        })
	}
</script>
</head>
<body onload="loadQuestionDetail()">
<form id="submitForm" name="submitForm"   action="/xngzf/archives/initFangyuan.action" method="post">
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
			<table  cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
				<tr>
					<td class="ui_text_rt" width="120">问题提出系统</td>
					<td class="ui_text_lt">
						<select  name="questionSys" id="questionSys" class="ui_select01" onchange="getFyDhListByFyXqCode();">
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
					<td class="ui_text_rt">问题验证结果</td>
					<td class="ui_text_lt">
                        <select name="questionValideStatus" id="questionValideStatus" class="ui_select01">
                            <option value="">--请选择--</option>
                        </select>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt">问题解决系统</td>
					<td class="ui_text_lt">
                        <select name="resolveSys" id="resolveSys" class="ui_select01">
                            <option value="">--请选择--</option>
                        </select>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt">接口名称</td>
					<td class="ui_text_lt">
                        <input type="text" id="resolveInterface" name="resolveInterface"  class="ui_input_txt02"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt">问题描述</td>
					<td class="ui_text_lt">
						<textarea type="text" id="questionDescrbe" name="questionDescrbe"  class="ui_textarea_txt01"></textarea>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt">解决状态</td>
					<td class="ui_text_lt">
						<select name="resolveStatus" id="resolveStatus" class="ui_select01">
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
					<td class="ui_text_rt">核心反馈</td>
					<td class="ui_text_lt">
                        <textarea type="text" id="resolveBack" name="resolveBack"  class="ui_textarea_txt01"></textarea>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt">问题提出时间</td>
					<td class="ui_text_lt">
						<input type="text" id="createTime" name="createTime"  class="ui_input_txt02"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt">备注</td>
					<td class="ui_text_lt">
                        <textarea type="text" id="remark" name="remark"  class="ui_textarea_txt01"></textarea>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td class="ui_text_lt">
						&nbsp;<input id="submitbutton" type="button" value="提交" class="ui_input_btn01"/>
						&nbsp;<input id="cancelbutton" type="button" value="取消" class="ui_input_btn01"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>

</body>
</html>