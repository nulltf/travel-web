<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath()%>/resource/css/login.css" rel="stylesheet" type="text/css">
<script src = "<%=request.getContextPath()%>/resource/js/jquery-1.4.2.min.js" type="text/javascript"></script>
<title>登录页面</title>
</head>
<body>
<div class = "login_div">
<table height="275">
<tr><td>在路上登录</td></tr>
<tr><td><input type="text" class= "input_" placeholder ="请输入您的用户名" id="userAccount"/></td></tr>
<tr><td><input type="password" class= "input_" placeholder ="请输入您的密码" id= "password"/></td></tr>
<tr><td><input type = "button" class ="button" value ="登录" onclick="_login();">
<input type = "button" class ="button" value ="注册" onclick="_register()"></td></tr>

</table>
<div></div>
</div>
<script type="text/javascript">
function _login(){
	if($("#userAccount").val()==''||$("#password").val()==''){
		alert("请输入用户名和密码");
		return ;
	}
	param='userAccount='+$("#userAccount").val()+'&password='+$("#password").val();
	$.getJSON("/trip/user/login.do",param,function(json){
		if(json.success){
			location.href="/trip/user/index.jspx";	
		}
		else{
			alert("用户账号或密码错误,请重新输入");
			location.href="/trip/user/login.jspx";	
		}
		
	});
}

function _register(){
	location.href="/trip/user/register.jsp";
}

</script>
</body>
</html>