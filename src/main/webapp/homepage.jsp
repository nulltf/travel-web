<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.trip.util.SysConst ,com.trip.vo.UserVo"%>
<%
	UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resource/css/homePage.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery-1.3.1.js"></script>
<title>follower</title>
</head>
<body>
	<div class="head_div"></div>
	<div class="follow_bar">
		<table class="detail_table">
			<tr>
				<td>说说</td>
				<td>正在关注</td>
				<td>关注者</td>
			</tr>
		</table>
	</div>
	<div class="main">
		<div class="main_left">
			<table>
				<tbody>
					<tr>
						<td width="134"><img
							src="<%=request.getContextPath()%>/resource/img/follow/follow_camera.jpg" />
							<span id="blogcount">x</span>个照片说说</td>
						<td></td>
					</tr>
					<tr>
						<td><img
							src="<%=request.getContextPath()%>/resource/img/head.jpg"
							width="84" height="86" />
						</td>
						<td></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="main_right">
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
			<div class="twitter" id="twitter"></div>
		</div>
	</div>


	<script type="text/javascript">
		var page = 1;
		$(document).ready(function() {
			searchData();
			searchCount();
			searchhead();
		});
		function searchData() {
			var id =
	<%=request.getAttribute("id")%>
		;
			var req = 'id=' + id + '&page=' + page + '&displayRecord=10';
			$.ajax({
				data : req,
				type : "get",
				async : false,
				url : '/trip/user/homepage/blogdata.do',
				dataType : "json",
				cache : false,
				success : dealData
			});

		}

		function searchCount() {
			var id =
	<%=request.getAttribute("id")%>
		;
			var param = 'id=' + id;
			$
					.getJSON(
							"/trip/user/homepage/personalCount.do",
							param,
							function(json) {
								if (json.success) {
									var blogCount = json.object.blogCount;
									var followerCount = json.object.followerCount;
									var fansCount = json.object.fansCount;
									$("#blogcount").html(blogCount);
									$(".detail_table")
											.append(
													'<tr>\
			<td>'
															+ blogCount
															+ '</td>\
			<td><a href="/trip/user/homepage/follower.jspx?id='
															+ id
															+ '">'
															+ followerCount
															+ '</a></td>\
			<td><a href="/trip/user/homepage/fans.jspx?id='
															+ id + '">'
															+ fansCount
															+ '</a></td></tr>');
								}
							});
		}
		function searchhead() {
			var id =
	<%=request.getAttribute("id")%>
		;
			var param = 'id=' + id;
			$
					.getJSON(
							"/trip/user/getPersonById.do",
							param,
							function(json) {
								if (json.success) {
									$(".head_div")
											.append(
													'<img  src="/trip/imgFileView.do?fileName='
															+ json.object.photoUrl
															+ '" width="213" height="208">\
					<img src="" id="block"/>');
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
				$("span[name='likecount']", curr).html(
						'(' + this.likecount + ')');
				$("img[name='likeit']", curr).attr("blogid", this.id);
				if (this.done == 0) {
					$("span[name='likeitornot']", curr).html('赞');
				} else {
					$("span[name='likeitornot']", curr).html('取消赞');
				}
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
				$("tbody", curr).attr("blogid", this.id);
				$("input[name='commitInput']", curr).attr("blogid", this.id);
				$("input[name='commitComment']", curr).attr("blogid", this.id);
				$("img[name='commentshow']", curr).click(function() {
					tocmment($(this).attr("blogid"));
				});
				$("input[name='commitComment']", curr).click(function() {
					commitComment($(this).attr("blogid"));
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

				$(".twitter").append(curr);

			});
		}
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
								$("input[blogid=" + id + "]").attr(
										"placeholder",
										"回复" + $(this).attr("userAccount")
												+ ":");
							});
				});

			});
		}

		function commitComment(id) {
			var param = {};
			var useraccount = $("input[blogid=" + id + "]").attr("placeholder");
			if (useraccount != "") {
				useraccount = useraccount.substring(2, useraccount.length - 1);
				param.content = "回复" + useraccount + ":"
						+ $("input[blogid=" + id + "]").val();
			} else {
				param.content = ":" + $("input[blogid=" + id + "]").val();
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
		}
	</script>

</body>
</html>