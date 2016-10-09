<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/plugins/include_static_head.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<jsp:include page="/pages/plugins/include_javascript_head.jsp" />
<script type="text/javascript" src="js/pages/back/emp/emp_show.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<!-- 导入头部标题栏内容 -->
		<jsp:include page="/pages/plugins/include_title_head.jsp" />
		<!-- 导入左边菜单项 -->
		<jsp:include page="/pages/plugins/include_menu_item.jsp">
			<jsp:param name="role" value="emp"/>
			<jsp:param name="action" value="emp:add"/>
		</jsp:include>
		<div class="content-wrapper">
			<!-- 此处编写需要显示的页面 -->
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
						<!-- /.box-header -->
						<div class="box-body table-responsive no-padding">
							<div class="panel panel-info">
								<div class="panel-heading">
									<strong><span class="glyphicon glyphicon-user"></span>&nbsp;编辑雇员信息</strong>
								</div>
								<div class="panel-body" style="height : 95%;">
									<div class="row">
										<div class="col-md-7">
											<div class="row">
												<div class="col-md-2 text-right"><strong>雇员编号：</strong></div>
												<div class="col-md-5">${emp.empno}</div>
											</div>
											<div class="row">
												<div class="col-md-2 text-right"><strong>雇员姓名：</strong></div>
												<div class="col-md-5">${emp.ename}</div>
											</div> 
											<div class="row">
												<div class="col-md-2 text-right"><strong>雇员职位：</strong></div>
												<div class="col-md-5">${emp.job}</div>
											</div> 
											<div class="row">
												<div class="col-md-2 text-right"><strong>雇员级别：</strong></div>
												<div class="col-md-5">${level.title} - ${level.flag}</div>
											</div> 
											<div class="row">
												<div class="col-md-2 text-right"><strong>基本工资：</strong></div>
												<div class="col-md-5">${emp.sal}</div>
											</div> 
											<div class="row">
												<div class="col-md-2 text-right"><strong>销售佣金：</strong></div>
												<div class="col-md-5">${emp.comm}</div>
											</div> 
											<div class="row">
												<div class="col-md-2 text-right"><strong>雇佣日期：</strong></div>
												<div class="col-md-5">${emp.hiredate}</div>
											</div> 
											<div class="row">
												<div class="col-md-2 text-right"><strong>在职状态：</strong></div>
												<div class="col-md-5">
													<c:if test="${emp.flag == 1}">
														<span class="text-success">在职</span>
													</c:if>
													<c:if test="${emp.flag == 0}">
														<span class="text-danger">离职</span>
													</c:if>
												</div>
											</div> 
										</div>
										<div class="col-md-5"><img id="showImg" src="upload/emp/sm-${emp.photo}"></div>
									</div>
									<div class="row">
										<table class="table table-hover">
										<tr>
											<th width="10%" class="text-center">操作管理员</th> 
											<th width="10%" class="text-center">职位</th> 
											<th width="10%" class="text-center">工资变化</th>
											<th width="10%" class="text-center">工资</th>
											<th width="10%" class="text-center">佣金</th>
											<th width="30%" class="text-center">说明</th>
										</tr>
										<c:forEach items="${allElogs}" var="log">
											<tr>
												<td class="text-center">${log.mid}</td>
												<td class="text-center">${log.job}</td>
												<td class="text-center">
													<c:if test="${log.sflag == 0}">
														<span class="glyphicon glyphicon-check"></span>
													</c:if>
													<c:if test="${log.sflag == 1}">
														<span class="text-success"><span class="glyphicon glyphicon-plus-sign"></span></span>
													</c:if>
													<c:if test="${log.sflag == 2}">
														<span class="text-danger"><span class="glyphicon glyphicon-minus-sign"></span></span>
													</c:if>
													<c:if test="${log.sflag == 3}">
														<span class="text-warning"><span class="glyphicon glyphicon-info-sign"></span></span>
													</c:if>
												</td>
												<td class="text-center">${log.sal}</td>
												<td class="text-center">${log.comm}</td>
												<td class="text-left">${log.note}</td>
											</tr>
										</c:forEach>
									</table>
									</div>
								</div> 
							</div>
						</div>
						<!-- /.box-body -->
					</div>
					<!-- /.box -->
				</div>
			</div>
		</div>
		<!-- 导入公司尾部认证信息 -->
		<jsp:include page="/pages/plugins/include_title_foot.jsp" />
		<!-- 导入右边工具设置栏 -->
		<jsp:include page="/pages/plugins/include_menu_sidebar.jsp" />
		<div class="control-sidebar-bg"></div>
	</div>
	<jsp:include page="/pages/plugins/include_javascript_foot.jsp" />
		<div class="modal fade" id="empPhoto"  tabindex="-1" aria-labelledby="modalTitle" aria-hidden="true" data-keyboard="true">
		<div class="modal-dialog" style="width: 800px">
			<div class="modal-content">
				<div class="modal-header">
					<button class="close" type="button" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h3 class="modal-title">雇员照片</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-md-5"><img id="empImg"></div></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭窗口</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
