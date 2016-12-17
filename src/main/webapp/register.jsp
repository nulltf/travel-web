<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <title>注册</title>
   <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/reg.css"/>
 <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resource/css/common.css"/>
 <script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery-1.3.1.js"></script>


  </head>
  
  <body>
  <div class="head">
  <span class="head_span">已有账号？<a href="/trip/login.jsp">登录</a></span>
  </div>
 
  <div class="reg_main">
	 
	  <div class = "form_data">
			
			
				<table>
				<tr><td></td><td><span class="font3">现在就加入我们</span></td><td></td></tr>
					<tr height="65">
						<td class="tab_td1">真实姓名:</td>
						<td style="width: 250px"><input type="text" name="name" id="userName" /></td>
						<td style="width: 250px"></td>
					</tr>
					<tr >
						<td class="tab_td1">账号名：</td>
						<td><input type="text" id="userAccount" /></td>
						<td></td>
					</tr>
					<tr >
						<td class="tab_td1">电话号码：</td>
						<td><input type="text" id="mobileNum" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="tab_td1">性别:</td>
						<td><input type="radio" name="sex" checked="checked"  value="男"/>男<input type="radio"
							name="sex" value="女"/>女</td>
						<td></td>
					</tr>

					<tr class="work_spe">
						<td class="tab_td1">居住地:</td>
						<td><select id="province" type="text">
								
						</select>
						<select id="city" type="text">
								<option>--选择城市--</option>
								
						</select>
						</td>
						<td></td>
					</tr>
					
					<tr class="work_spe">
						<td class="tab_td1">公司:</td>
						<td><input type="text" id="company" /></td>
						<td></td>
					</tr>
					<tr >
						<td class="tab_td1">学校：</td>
						<td><input type="text" id="university" /></td>
						<td></td>
					</tr>
					<!-- 考虑大学生要输入的特别信息 end -->
					<tr>
						<td class="tab_td1">电子邮箱:<br />&nbsp;
						</td>
						<td><input type="text" name="email" id="email" /><br /> 
						</td>
						<td></td>
					</tr>
					<tr>
						<td class="tab_td1">设置密码:</td>
						<td><input type="password" name="pwd" id="password" /></td>
						<td></td>
					</tr>
					<tr>
						<td class="tab_td1">确认密码:</td>
						<td><input type="password" id="password_com" /></td>
						<td></td>
					</tr>
					
					
					<tr>
						<td></td>
						<td><input type="button" value="注册" class="button1" onclick="register()"/></td>
						<td></td>
					</tr>
				</table>
			
		</div>
	  
  
  
  </div>
  
<!-- 准备显示的大学表格 end -->

<script type="text/javascript">
$(document).ready(function(){
	 $.getJSON('/trip/listAllProvice.do',function(json){
	
	initSelect($("#province"),json.object);
	 });
	 
	 $("#province").change(function(){
		 req="province="+$(province).val();
		 $.getJSON("/trip/listAllCity.do",req,function(json){
			 initSelect($("#city"),json.object);
		 });
	 });
	 }) ;
	 

function initSelect(select,object){
	var html = "<option value=''>请选择</option>";
	$.each(object,function(i,value){
		html+='<option value='+value+'>'+value+'</option>';
	}); 
	select.html(html);
}


function register(){
	if($("#password").val()!=$("#password_com").val()){
		alert("两次输入密码不一致");
		return;
	}
	if($("#password").val().length<6){
		alert("密码字符过短，请输入6位以上的密码");
		return;
	}
	var check = 0;
	param = '';
	$("input[type='text']").each(function(){
		if($(this).val()==""){
			check = 1;
			return false;
		}
		param+='&'+this.id+'='+this.value;
	});
	if(check==1){
		alert("请输入要填的注册信息");
		return ;
	}
	
	param=param+'&sex='+$("input[type='radio']:checked").val()+'&password='+$("#password").val();
	param = param.substring(1);
	$.getJSON("/trip/user/register.do",param,function(json){
		if(json.success){
			location.href="/trip/user/login.jspx";
		}
		else{alert(json.message);}
	});
	
}
</script>
 
  
  
  </body>
</html>
