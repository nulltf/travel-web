<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="com.trip.vo.* , com.trip.util.SysConst"%>
<%
	UserVo vo = (UserVo) session.getAttribute(SysConst.CurrentUser);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/resource/css/reg.css" />
<link rel="stylesheet" type="text/css" href="/resource/css/common.css" />
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/resource/css/follower.css" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/js/jquery-1.3.1.js"></script>
<script src="<%=request.getContextPath()%>/resource/js/ajaxupload.js"></script>
<title>编辑个人资料</title>
</head>
<body>
	<div class="head_div">
		<img title="上传头像" id="img_upload"
			src="/trip/imgFileView.do?fileName=<%=vo.getPhotoUrl()%>" width="213"
			height="208" onmouseover="showcamera(this,0);"
			onmouseout="showcamera(this,1);"> <br> <span id="msg"></span>
		<span id="msg"></span> <img src="" id="block" />
	</div>
	<div class="reg_main">

		<div class="form_data">


			<table>

				<tr height="65">
					<td class="tab_td1">真实姓名:</td>
					<td style="width: 250px"><input type="text" name="name"
						id="userName" />
					</td>

				</tr>

				<tr>
					<td class="tab_td1">电话号码：</td>
					<td><input type="text" id="mobileNum" />
					</td>

				</tr>
				<tr>
					<td class="tab_td1">性别:</td>
					<td><input type="radio" name="sex" checked="checked" value="男" />男<input
						type="radio" name="sex" value="女" />女</td>

				</tr>

				<tr class="work_spe">
					<td class="tab_td1">居住地:</td>
					<td><select id="province" type="text">

					</select> <select id="city" type="text">
							<option>选择城市</option>

					</select></td>

				</tr>

				<tr class="work_spe">
					<td class="tab_td1">公司:</td>
					<td><input type="text" id="company" />
					</td>

				</tr>
				<tr>
					<td class="tab_td1">学校：</td>
					<td><input type="text" id="university" />
					</td>

				</tr>
				<!-- 考虑大学生要输入的特别信息 end -->
				<tr>
					<td class="tab_td1">电子邮箱:<br />&nbsp;</td>
					<td><input type="text" name="email" id="email" /><br /></td>

				</tr>
				<tr>
					<td></td>
					<td><input type="button" value="修改" class="button1"
						onclick="editInfo()" />
					</td>
				</tr>
			</table>

		</div>



	</div>

	<!-- 准备显示的大学表格 end -->
	<script>
		$(document).ready(
				function() {
					loadInfo();
					$("#img_upload").click(
							ajaxUploadInit("/trip/upload_head.do",
									"img_upload", "msg"));
					$.getJSON('/trip/listAllProvice.do', function(json) {

						initSelect($("#province"), json.object);
					});

					$("#province").change(function() {
						req = "province=" + $(province).val();
						$.getJSON("/trip/listAllCity.do", req, function(json) {
							initSelect($("#city"), json.object);
						});
					});
				});

		function initSelect(select, object) {
			var html = "<option value=''>请选择</option>";
			$.each(object, function(i, value) {
				html += '<option value='+value+'>' + value + '</option>';
			})
			select.html(html);
		}

		function showcamera(cur, param) {

			if (param == 0) {
				$(cur).addClass("filter");
			} else {
				$(cur).removeClass("filter");
			}

		}

		function ajaxUploadInit(actionUrl, uploadBtn, msg) {

			new AjaxUpload('#' + uploadBtn, {
				action : actionUrl,
				name : 'filedata',
				responseType : 'json',
				autoSubmit : true,
				onSubmit : function(file, ext) {
					if (!(ext && /^(jpg|jpeg|JPG|JPEG|gif|ai|pdg|Gif|AI|PDG)$/
							.test(ext))) {
						alert('图片格式不正确,请选择 图片格式的文件!', '系统提示');
						return false;
					}

					$('#' + msg).html('</br> 图片上传中...');
				},
				onComplete : function(file, response) {
					$('#' + msg).hide();
					var result = response;
					if (result.success) {
						photoUrl = result.object;
						$.getJSON("/trip/user/upload_head.do?photoUrl="
								+ photoUrl, photoUrl, function() {
							$("#msg").html("图片上传成功");
							$("#img_upload")
									.attr(
											"src",
											"/trip/imgFileView.do?fileName="
													+ photoUrl);
							//location.href="/trip/user/index.jspx";

						});
					} else {
						$("#msg").html("图片上传失败");
					}
				}
			});
		}
		function editInfo() {
			var param = {};
			param.userName = $("#userName").val();
			param.mobileNum = $("#mobileNum").val();
			param.sex = $("input[type='radio']:checked").val();
			param.province = $("#province").val();
			param.city = $("#city").val();
			param.company = $("#company").val();
			param.university = $("#university").val();
			param.email = $("#email").val();
			$.getJSON("/trip/user/editInfo.do", param, function(json) {

				if (json.success) {
					alert("个人资料更新成功");
					window.location.href = '/trip/user/index.jspx';
				} else {
					alert("个人资料更新失败");
				}

			});
		};
		function loadInfo() {
			$.getJSON("/trip/user/getPersonById.do", "", function(json) {
				if (json) {
					var list = json.object;
					$("#userName").val(list.userName);
					$("#mobileNum").val(list.mobileNum);
					$("#province").val(list.province);
					$("#city").val(list.city);
					$("#company").val(list.company);
					$("#email").val(list.email);
					$("#university").val(list.university);

				}
			});
		}
	</script>


</body>
</html>