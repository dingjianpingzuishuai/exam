<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
		  /*
		    request.getSchema()可以返回当前页面使用的协议，如“http”
			request.getServerName()可以返回当前页面所在的服务器的名字，如“localhost"
			request.getServerPort()可以返回当前页面所在的服务器使用的端口,如8088，
			request.getContextPath()可以返回当前页面所在的应用的名字，如login.jsp
			这四个拼装起来，就是当前应用的跟路径了
			http://localhost:8088/exam/login.jsp*/
%>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/admin.css"><!-- stylesheet要导入的样式表的 URL -->
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/pintuer.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/bootstrap.css">
    <title>学生登录</title>
</head>

<body>
    <div class="bg"></div>
    <div class="container">
        <div class="line bouncein">
            <div class="xs6 xm4 xs3-move xm4-move">
                <div style="height:80px;"></div>
                <div class="media media-y margin-big-bottom">
                </div>
                <form action="<%=basePath%>user?cmd=stulogin" method="post" class="login-form">
                    <div class="panel loginbox">
                        <div class="text-center margin-big padding-big-top">
                            <h1>在线考试系统</h1>
                            <div class="form-top-left">
                                <a data-type="student" href="javascript:void(0);" style="color: red;">学生登录</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                &nbsp;&nbsp;&nbsp;
							<!-- href=”javascript:void(0);”这个的含义是，让超链接去执行一个js函数，而不是去跳转到一个地址，
							而void(0)表示一个空的方法，也就是不执行js函数。
							javascript:是伪协议，表示url的内容通过javascript执行。
							void(0)表示不作任何操作，这样会防止链接跳转到其他页面。这么做往往是为了保留链接的样式，但不让链接执行实际操作， -->
                                <a data-type="admin" href="javascript:void(0);">管理员登录</a><br>
                                <br>
                                <p id="mes" style="color: red;">${msg}</p>
                            </div>
                        </div>
                        <div class="panel-body" style="padding:30px; padding-bottom:10px; padding-top:10px;">
                            <div class="form-group">
                                <div class="field field-icon-right">
                                    <input type="text" class="input input-big" name="username" placeholder="登录账号" data-validate="required:请填写账号" />
                                    <!-- placeholder 属性提供可描述输入字段预期值的提示信息(hint),如此处的登录账号和密码 -->
                                    <span class="icon icon-user margin-small"></span>
                                    <!-- <span>在CSS定义中属于一个行内元素 -->
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="field field-icon-right">
                                    <input type="password" class="input input-big" name="userpwd" placeholder="登录密码" data-validate="required:请填写密码" />
                                    <span class="icon icon-key margin-small"></span>
                                </div>
                            </div>
                        </div>
                        <div style="padding:30px;"><button type="submit" class="button button-block bg-main text-big input-big">登录</button></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <script src="<%=basePath%>js/jquery.js"></script>    
    <script src="<%=basePath%>js/bootstrap.js"></script>
    <script src="<%=basePath%>js/pintuer.js"></script>
    <script>
		var basePath = '<%=basePath%>'
		$('.form-top-left a').click(function () {
			/*
			 $("a").click(function(){...}) 就是在点击页面上的任何一个链接时的触发事件。
			确切地说，就是jQuery用<a/>这个标签构建了一个对象$("a")，函数 click()是这个jQuery对象的一个（事件）方法。
			*/
		    $('.form-top-left').children().removeAttr("style");
			//获取id为form-top-left的form的子元素,移除这些子元素中的style属性。这种写法属于jquery中的链式写法。
		    var type = $(this).css('color', 'red').attr("data-type");
		    if (type == 'student') {
		        $(document).attr("title", "学生登录");
		        $("#mes").html('');
		        $(".login-form").attr("action", basePath + "user?cmd=stulogin");
		    }else {
		        $(document).attr("title", "管理员登录");
		        $("#register").html('');
		        $("#mes").html('');
		        $(".login-form").attr("action", basePath + "sys/user?cmd=login");
		        //设置属性的值,定义action的属性值为login(在xml中寻找)
		    }
		})
	</script>
</body>

</html>