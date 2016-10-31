<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jstl/core_rt"  prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>NBA007</title>

<!-- Bootstrap -->
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	 
	<h2 align ="center">球队列表</h2>
        <hr>
	<div class="main-content">

		<div class="btn-toolbar list-toolbar">
			<button class="btn btn-primary"
				onclick="location='${pageContext.request.contextPath }/nbaget007?op=saicheng'">
				<i class="fa fa-plus"></i>NBA
			</button>
			<a class="btn btn-danger" href="${pageContext.request.contextPath }/nbaget007?op=updatedata">更新赛程</a>
			<a class="btn btn-danger" href="${pageContext.request.contextPath }/nbaget007?op=updatescore">更新比分</a>
			<div class="btn-group"></div>
		</div>
		 <hr>
		<table class="table">
			<thead>
				<tr>					
					<th nowrap style="text-align:center;">球队编号</th>
					<th nowrap>赛事</th>
					<th nowrap>比赛时间</th>					
					<th nowrap>客队</th>	
					<th nowrap>比分</th>	
					<th nowrap>主队</th>
					<th nowrap>特征码</th>		
					<th style="width: 3.5em;"></th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty teamlist}">
						<tr>
							<td colspan="6" align="center">暂时没有数据</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach items="${teamlist }" var="c">
							<tr>								
								<td style="text-align:center;">${c.id }</td>
								<td style="text-align:center;">${c.title }</td>	
													
								<td style="text-align:center;"><a href = "${pageContext.request.contextPath }/nbaget007?op=fenxi&name=${c.player1 }"  target="_blank">${c.player1 }</a></td>
								<td style="text-align:center;">${c.score }</td>
								<td style="text-align:center;"><a href = "${pageContext.request.contextPath }/nbaget007?op=fenxi&name=${c.player2 }" target="_blank">${c.player2 }</a></td>
								<td style="text-align:center;">${c.time }</td>																																																			
							</tr>

						</c:forEach>

					</c:otherwise>

				</c:choose>

			</tbody>
		</table>
		

		<footer>
			<hr>
			<p class="pull-right">
				Collect from <a href="#" title="网页模板" target="_blank">huyoulinger</a>
			</p>
			<p>
				© 2014 <a href="#" target="_blank">Portnine</a>
			</p>
		</footer>
	</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
	
</body>
</html>