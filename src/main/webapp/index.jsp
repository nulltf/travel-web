<%@page import="com.trip.util.SysConst"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="com.trip.vo.UserVo"%>
<%
	UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎</title>
<link rel="stylesheet" type="text/css" href="/resource/css/index.css" />
<script type="text/javascript" src="/resource/js/jquery-1.3.1.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/ajaxupload.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/jquery.sysop.js"></script>
</head>
<body>
	<div class="head_bar">
		<table>
			<tr>
				<td width="80" height="32"><img
					src="<%=request.getContextPath()%>/resource/img/index/index_01.jpg">&nbsp;<a
					href="/trip/user/index.jspx">主页</a>
				</td>
				<td width="80"><img
					src="<%=request.getContextPath()%>/resource/img/index/index_02.jpg">&nbsp;<a>通知</a>
				</td>
				<td width="80"><img
					src="<%=request.getContextPath()%>/resource/img/index/index_03.jpg">&nbsp;<a>私信</a>
				</td>
				<td width="80"><img
					src="<%=request.getContextPath()%>/resource/img/index/index_04.jpg">&nbsp;<a>发现</a>
				</td>
				<td width="370"><a
					href="/trip/user/homepage.jspx?id=<%=vo.getId()%>"><img
						src="/trip/imgFileView.do?fileName=<%=vo.getPhotoUrl()%>"
						width="35" height="35" /> <span><%=vo.getUserAccount()%></span>
				</a>
				</td>
				<td width="300" class="search_td"><select id="selector">
						<option>请选择</option>
						<option value="1">找人</option>
						<option value="2">找博客</option>
				</select> <input class="search_input" id="search_input" />
				</td>
				<td width="300"><input type="button" value="搜索"
					id="searchAbout" />&nbsp;&nbsp;<input type="button" value="编辑个人资料"
					onclick="editInfo();" />
				</td>
			</tr>

		</table>

	</div>
	<div class="user_all">
		<div class="user_top">
			<img src="/trip/imgFileView.do?fileName=<%=vo.getPhotoUrl()%>" /> <img
				src="" id="block" />
		</div>
		<div class="user_info">
			<table height="80" width="243" class="user_table">
				<tr>
					<td><%=vo.getUserAccount()%></td>
					<td><%=vo.getSex()%></td>
					<td></td>
				</tr>
				<tr>
					<td>说说</td>
					<td>正在关注</td>
					<td>关注者</td>
				</tr>

			</table>
		</div>
	</div>
	<div class="blog" id="blog_detail" style="display:none;">
		<div class="blog_head">
			<div class="blog_headimg">
				<a href="/trip/user/homepage.jspx?id='+this.userid+'"
					name="head_img_a"><img
					src="/trip/imgFileView.do?fileName='+this.photoUrl+'" width="51"
					height="51" name="headimg" />
				</a>
			</div>
			<div class="blog_userInfo">
				<span name="useraccount"></span> <br> <span name="local"></span>
			</div>
		</div>
		<div class="blog_content">
			<span name="content"></span> <br>
			<div class="blog_photo" name="blog_photo"></div>

		</div>
		<div class="comment">
			<img
				src="<%=request.getContextPath()%>/resource/img/index/comment.png"
				height="25" width="30" name="commentshow"> <span
				name="commentcount">this.commentcount</span> <img
				src="<%=request.getContextPath()%>/resource/img/index/love.png"
				height="25" width="35" name="likeit" /><span name="likeitornot"></span><span
				name="likecount"></span>
			<div class="commentAllDetail" style="display : none" ; blogid="">
				<div class="commentDetail">
					<table>
						<tbody></tbody>
					</table>
					<br>
					<input type="text" name="commitInput" /><input type="button"
						blogid="" value="发表" id="commitComment" name="commitComment" />
				</div>
			</div>
		</div>


	</div>
	<div class="main" id="main">
		<div class="release">
			<table>
				<tr>
					<td><img
						src="/trip/imgFileView.do?fileName=<%=vo.getPhotoUrl()%>"
						width="40" height="40" />
					</td>
					<td><textarea placeholder="有什么新鲜事？" id="content" type="search"></textarea>
					</td>
				</tr>
				<tr>
					<td></td>
					<td><img
						src="<%=request.getContextPath()%>/resource/img/index/camera.jpg"
						id="img_upload"> <a id="msg">添加照片</a> <span id="msg"></span>
						
						<select id="province" type="search">
							<option>--选择省份--</option>
						</select> 
						<select id="city" type="search">
							<option></option>
					</select> <input type="button" value="发布" onclick="issue();"></td>
				</tr>
			</table>
		</div>
		<table class="commentmodel">
			<tr style="display:none;" id="commentmodel">
				<td><img src="/trip/imgFileView.do?fileName='+this.photoUrl+'"
					width="25" height="25" name="headimg" />
				</td>
				<td name="username">'+this.userAccount+'回复：</td>
				<td name="content">'+this.content+'</td>
				<td name="time">'+this.createTime+'</td>
				<td><a href="javascript:void(0);" name="reply"><img
						src="/resource/img/index/reply.png" height='40' width='40' />
				</a>
				</td>
			</tr>
		</table>
		<div class="twitter">

			<!--分页导航-->
			<div class="displayNum" id="displayNum"></div>
			<!--每页显示数目导航-->
			<div class="clear"></div>
		</div>

	</div>
	<div class="recommend" style="display:none;">
		<span>推荐关注</span>&nbsp;.&nbsp;<span><a href="">刷新</a>
		</span>&nbsp;.&nbsp;<span><a href="">查看全部</a>
		</span>
	</div>
	<table>
		<tbody>
		</tbody>
	</table>
	<div class="about_me" style="display:none;"></div>
	<div class="searchDiv" style="display:none" id="searchdiv">
		<div class="searchDiv_img">
			<img src="/trip/imgFileView.do?fileName=<%=vo.getPhotoUrl()%>"
				width="100" height="100" name="ser_head_img" />
		</div>
		<div class="searchDiv_detail">
			<div class="searchDiv_info">
				<span name="ser_name">heyanzhi</span><input type="button"
					value="follow" name="ser_action" />
			</div>
			<div class="searchDiv_follow">
				<span name="fans">粉丝</span><span name="follow">关注</span><span
					name="blogs">博文</span>
			</div>
			<div class="searchDiv_hometown">
				<span name="hometown">gd.zq</span>
			</div>
		</div>
	</div>

	<script>
var showType = 1;//1为我的blog，2为查找人
var page=1;
var photoUrl='';
$(document).ready(function(){
	$("#img_upload").click(ajaxUploadInit("/trip/uploadBlogPhoto.do","img_upload","msg"));
	 $.getJSON('/trip/listAllProvice.do',function(json){
	
	initSelect($("#province"),json.object);
	 });
	 
	 $("#province").change(function(){
		 req="province="+$(province).val();
		 $.getJSON("/trip/listAllCity.do",req,function(json){
			 initSelect($("#city"),json.object);
		 });
	 });
	 searchData(); 
	 searchCount();
	 lock=0;
	 
	 $("#searchAbout").click(function(){
		 var param={};
		 var url;
		 showType = $("#selector").val();
		 param.type=$("#selector").val();
		 param.content = $("#search_input").val();
		 if(showType=='1'){
			 url="/trip/user/searchUser.do";
		 }else{
			 url="/trip/user/searchblog.do";
		 }
		 $.getJSON(url,param,function(json){
			 if(json){
				 if(showType=='1'){
					 $("#main").html('');
					 var list =json.object;
					 $.each(list,function(){
					 var curr = $("#searchdiv").clone();
					 curr.show();
					 $("span[name='ser_name']",curr).html(this.userAccount);
					 $("img[name='ser_head_img']",curr).attr("src","/trip/imgFileView.do?fileName="+this.photoUrl);
					 $("span[name='fans']",curr).html("粉丝："+this.fansCount);
					 if(this.friendship!='0'){
						 $("input[name='ser_action']",curr).val("已关注");
					 }else{
						 $("input[name='ser_action']",curr).val("+关注"); 
						 $("input[name='ser_action']",curr).attr("userid",this.id);
						 $("input[name='ser_action']",curr).click(function(){
							 var param={};
							 param.follower = $(this).attr("userid");
							 $.getJSON('/trip/user/addfollow.do',param,function(json){
								 if(json){
									 alert("关注成功");
								 }
							 });
						 });
					 }
					 $("span[name='follow']",curr).html("关注的人："+this.followCount);
					 $("span[name='blogs']",curr).html("博客数："+this.blogCount);
					 $("span[name='hometown']",curr).html(this.province+" "+this.city);
					 $("#main").append(curr);
				 });
			 }else{
				 $(".twitter").html('');
				 dealData(json);
			 }
			 }
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

function editInfo(){
	location.href="/trip/user/editInfo.jspx";
}

function issue(){
	var check = 0;
	var param='';
	$(".main [type='search']").each(function(){
		param=param+'&'+this.id+'='+this.value;
		if(this.value==""){
			check=1;
			return false;
		}
	});
	
	if(check==1){
		alert("请输入内容或者地区");
		return ; 
	}
	param = param+'&photo='+photoUrl;
	param = param.substring(1);
	$.getJSON('/trip/user/addBlog.do',param,function(json){
		location.href='/trip/user/index.jspx';
	});
	
}

function ajaxUploadInit(actionUrl, uploadBtn, msg) {
	
	new AjaxUpload('#' + uploadBtn, {
		action : actionUrl,
		name : 'filedata',
		responseType : 'json',
		autoSubmit : true,
		onSubmit : function(file, ext) {
			if (!(ext && /^(jpg|jpeg|JPG|JPEG|gif|ai|pdg|Gif|AI|PDG)$/.test(ext))) {
                alert('图片格式不正确,请选择 图片格式的文件!', '系统提示');
                return false;
            }
			
			
			$("#msg").html('</br> 图片上传中...');
		},
		onComplete : function(file, response) {
			var result = response;
			if (result.success) {
				$("#msg").html('图片上传成功');
				photoUrl = result.object;
			
			} else {
				$("#msg").show();
				$("#msg").html('上传文件失败，');
			}
		}
	});
}

function searchData(){
	var req ='page='+page+'&displayRecord=10' ;
	$.ajax({
		data:req,
        type: "get",
        async: false,
        url: '/trip/user/getblog.do',
        dataType: "json",
        cache: false,
        success: dealData
    });
	
	}
	
function searchCount(){
	
	$.getJSON("/trip/user/personalCount.do","",function(json){
		
		var blogCount = json.object.blogCount;
		var followerCount = json.object.followerCount;
		var fansCount = json.object.fansCount;
		$(".user_table").append('<tr>
		<td><a href="homepage.jspx?id=<%=vo.getId()%>">'+blogCount+'</a></td>
		<td><a href="/trip/user/follower.jspx?id=<%=vo.getId()%>">'+followerCount+'</a></td>
		<td><a href="/trip/user/fans.jspx?id=<%=vo.getId()%>
		">'
														+ fansCount
														+ '</a></td></tr>');

							});
		}

		window.onscroll = function() {
			var a = document.documentElement.scrollTop == 0 ? document.body.clientHeight
					: document.documentElement.clientHeight;
			var b = document.documentElement.scrollTop == 0 ? document.body.scrollTop
					: document.documentElement.scrollTop;
			var c = document.documentElement.scrollTop == 0 ? document.body.scrollHeight
					: document.documentElement.scrollHeight;

			if (a + b == c) {
				searchData();
			}
		};

		function tocmment(id) {
			var param = {};
			param.blogId = id;
			$("tbody[blogid=" + id + "]").html('');
			$(".commentAllDetail[blogid=" + id + "]").show();
			$.getJSON("/trip/user/getcomment.do", param, function(reply) {
				var list = reply.object;
				$.each(list, function() {
					var curr = $("#commentmodel").clone();
					curr.show();
					$("img[name='headimg']", curr).attr("src",
							"/trip/imgFileView.do?fileName=" + this.photoUrl);
					$("td[name='username']", curr).html(this.userAccount);
					$("td[name='content']", curr).html(this.content + '&nbsp');
					$("td[name='time']", curr).html(this.createTime);
					$("tbody[blogid=" + id + "]").append(curr);
					$("a[name='reply']", curr).attr("userAccount",
							this.userAccount);
					$("a[name='reply']", curr).click(
							function() {
								$("input[blogid=" + id + "][type='text']").val(
										'');
								$("input[blogid=" + id + "][type='text']")
										.attr(
												"placeholder",
												"回复"
														+ $(this).attr(
																"userAccount")
														+ ":");
							});
				});

			});
		}

		function commitComment(id) {
			var param = {};
			var useraccount = $("input[blogid=" + id + "][type='text']").attr(
					"placeholder");
			if (useraccount != "") {
				useraccount = useraccount.substring(2, useraccount.length - 1);
				param.content = "回复" + useraccount + ":"
						+ $("input[blogid=" + id + "][type='text']").val();
			} else {
				param.content = ":"
						+ $("input[blogid=" + id + "][type='text']").val();
			}
			param.blogId = id;
			param.replyblogid = -1;
			$.getJSON("/trip/user/commitComment.do", param, function(json) {
				if (json.success) {
					$(".commentAllDetail[blogid=" + id + "]").hide();
					window.location.reload();
				} else {
					alert("评论失败");
				}
			});
		}

		function dealData(json) {
			if (!json.success) {
				alert(json.message);
				return;
			}
			page = page + 1;
			var list = json.object;
			var $box = $('twitter').show();
			var $tbody = $box.find('tbody').html('');
			$.each(list, function() {
				var curr = $("#blog_detail").clone();
				curr.show();
				$("span[name='useraccount']", curr).html(this.userAccount);
				$("span[name='local']", curr).html(
						this.localtime + ' 我在：' + this.province + ''
								+ this.city);
				$("span[name='content']", curr).html(this.content);
				$("span[name='commentcount']", curr).html(
						'(' + this.commentcount + ')');
				if (this.done == 0) {
					$("span[name='likeitornot']", curr).html('赞');
				} else {
					$("span[name='likeitornot']", curr).html('取消赞');
				}
				$("span[name='likecount']", curr).html(
						'(' + this.likecount + ')');
				$("img[name='headimg']", curr).attr("src",
						"/trip/imgFileView.do?fileName=" + this.photoUrl);
				$(".commentAllDetail", curr).attr("blogid", this.id);
				$("a[name='head_img_a']", curr).attr("href",
						"/trip/user/homepage.jspx?id=" + this.userid);

				//$("div[name='blog_photo']",curr).html('');
				if (this.photo != null && this.photo != '') {
					$("div[name='blog_photo']", curr).append(
							'<img  src="/trip/imgFileView.do?fileName='
									+ this.photo
									+ '" width="400" height="400"/> ');
				}
				$("img[name='commentshow']", curr).attr("blogid", this.id);
				$("img[name='likeit']", curr).attr("blogid", this.id);
				$("tbody", curr).attr("blogid", this.id);
				$("input[name='commitInput']", curr).attr("blogid", this.id);
				$("input[name='commitComment']", curr).attr("blogid", this.id);
				$("img[name='commentshow']", curr).click(function() {
					tocmment($(this).attr("blogid"));
				});
				$("img[name='likeit']", curr).click(function() {
					var blogid = $(this).attr("blogid");
					param = "blogid=" + blogid;
					$.getJSON("/trip/user/addlikeit.do", param, function(json) {
						if (json) {
							alert(json.message);
							window.location.reload();
						}
					});

				});
				$("input[name='commitComment']", curr).click(function() {
					commitComment($(this).attr("blogid"));
				});

				$(".twitter").append(curr);

			});
		}
	</script>

</body>

</html>