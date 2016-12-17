<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="com.trip.util.SysConst ,com.trip.vo.*"%>
    <% UserVo vo = (UserVo)session.getAttribute(SysConst.CurrentUser);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/resource/css/follower.css"/>
<script type="text/javascript" src="/resource/js/jquery-1.3.1.js"></script>
<title>follower</title>
</head>
<body>
<div class="head_div">

</div>
<div class="follow_bar">
<table class="detail_table">
<tr><td>说说</td><td>正在关注</td><td>关注者</td></tr>
</table>
</div>
<div class="main">
<div class="main_left">
<table>
<tbody>
<tr><td width="134"><img src="<%=request.getContextPath()%>/resource/img/follow/follow_camera.jpg"/> <span id="blog_count"></span>个照片说说</td><td></td></tr>
<tr><td><img src="<%=request.getContextPath()%>/resource/img/head.jpg" width="84" height="86"/></td><td></td></tr>
</tbody>
</table>
</div>
<div class="main_right">
<div class="user_all" style="display:none" id="user_all">		
<div class="user_top">		
<a href="/trip/user/homepage.jspx?id=14" name="homepage">
<img src="/trip/imgFileView.do?fileName=/head\2015\04\25\05acabf7-fd82-404a-99cf-2b7f2d3c51bc.jpg" name="head_img">
</a>	
<img id="block" src="">		
</div>					
<div class="user_info">
<table width="243" height="40" class="user_table">
<tbody><tr><td name="name">scofield</td><td name="sex">男</td><td><input type="button" value="取消关注" name="action"></td></tr>	
</tbody>
</table>
</div>
</div>
</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	searchCount();
	searchData();
	searchhead();
	
});
function searchCount(){
	param='id='+<%=request.getAttribute("id")%>;
	$.getJSON("/trip/user/homepage/personalCount.do",param,function(json){
		
		var blogCount = json.object.blogCount;
		var followerCount = json.object.followerCount;
		var fansCount = json.object.fansCount;
		$("#blog_count").html(blogCount);
		$(".detail_table").append('<tr>\
		<td>'+blogCount+'</td>\
		<td>'+followerCount+'</td>\
		<td>'+fansCount+'</td></tr>');
			
	});
}
function searchhead(){
	var id = <%=request.getAttribute("id")%>;
	var param = 'id='+id;
	$.getJSON("/trip/user/getPersonById.do",param,function(json){
		if(json.success){
			$(".head_div").append('<img  src="/trip/imgFileView.do?fileName='+json.object.photoUrl+'" width="213" height="208">\
					<img src="" id="block"/>');
		}
	});
}

function searchData(){
	var param='id='+<%=request.getAttribute("id")%>;
	$.ajax({
		data:param,
		 type: "get",
	        async: false,
	        url: '/trip/user/listFollower.do',
	        dataType: "json",
	        cache: false,
	        success: dealData
	});
	
	function dealData(json){
		if(!json.success) {
			alert(json.message);
			return;
		}
		var  id =<%=request.getAttribute("id")%>;
		var userid=<%=vo.getId()%>
		if(id!=userid){
			$("input[name='action']").hide();
		}
		var list = json.object;
		$.each(list,function(){
			var curr = $("#user_all").clone();
			curr.show();
			$("a[name='homepage']",curr).attr("href","/trip/user/homepage.jspx?id="+this.id);
			$("img[name='head_img']",curr).attr("src","/trip/imgFileView.do?fileName="+this.photoUrl);
			$("td[name='name']",curr).html(this.userAccount);
			$("td[name='sex']",curr).html(this.sex);
			$("input[name='action']",curr).attr("userid",this.id);
			$("input[name='action']",curr).click(function(){
				var param = {};
				param.follower =$(this).attr("userid");
				param.type = "1";
				if(confirm("确定要取消关注？")){
					$.getJSON("/trip/user/deletefollowOrFans.do",param,function(json){
						if(json){
							alert("取消关注成功");
							window.location.reload();
						}else{
							alert("取消关注失败");
						}
					});
				}
				
				
			})
			$(".main_right").append(curr);
			
			
		});
			
		
		
	}
}

</script>
</body>
</html>