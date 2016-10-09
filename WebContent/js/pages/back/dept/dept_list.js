$(function(){
	var cp = 1 ;	// 当前页是第一页
	var allRecorders = 1 ;	// 总记录数
	var pageSize ;
	$("[id*='showBtn-']").each(function(){	// 取得显示按钮
		var deptno = this.id.split("-")[1];	// 分离出id信息
		$(this).on("click",function(){ 
			// console.log("deptno = " + deptno) ;
			cp = 1 ;	// 打开新的窗口之后，原本的cp的内容设置为1
			$(deptTitleSpan).text($("#dname-" + deptno).text()) ;
			// 编写Ajax异步更新操作，读取所有的权限信息
			loadData(deptno) ;
			$(previousBut).on("click",function() {
				if (cp > 1) {
					$(pLi).attr("class","") ;
					cp -- ;
					loadData(deptno) ;
				} else {
					$(pLi).attr("class","disabled") ;
				}
			}) ;
			$(nextBut).on("click",function() {
				if (cp < pageSize - 1 ) {
					$(nLi).attr("class","") ;
					cp ++ ;
					loadData(deptno) ;
				} else {
					$(nLi).attr("class","disabled") ;
				}
			}) ; 
			$("#empInfo").modal("toggle") ;	// 显示隐藏窗口
		}) ;
	}) ;
	$("#empInfo").on("hidden.bs.modal",function(data){
		$(nextBut).unbind("click") ;
		$(previousBut).unbind("click") ;
	}) ;	// 设置模态窗口显示后的监听控制
	function loadData(deptno) {
		$.post("pages/back/dept/DeptServletBack/listEmp",{cp:cp,deptno:deptno},function(data){
			if (data.flag == true) { 
				allRecorders = data.allRecorders ;
				pageSize = (allRecorders + 5 - 1) / 5 ;
				$("#empBody tr").remove() ;	// 清空“<tbody>”中的所有“<tr>”
				for (var x = 0 ; x < data.allEmps.length ; x ++) {
					var flagSpan = "" ;
					if (data.allEmps[x].flag == 1) {
						flagSpan = "<span class='text-success'>在职</span>" ;
					} else {
						flagSpan = "<span class='text-danger'>离职</span>" ;
					}
					var levelTxt = "" ;
					for (var y = 0 ; y < data.allLevels.length ; y ++) {
						if (data.allLevels[y].lid == data.allEmps[x].lid) {
							levelTxt = data.allLevels[y].title + " - " + data.allLevels[y].flag ;
						}
					}
					var ele = "<tr>" +
							"		<td class='text-center'><img src='upload/emp/"+data.allEmps[x].photo+"' style='width:30px;'></td>" +
							"		<td class='text-center'>"+data.allEmps[x].ename+"</td>" +
							"		<td class='text-center'>"+data.allEmps[x].job+"</td>" +
							"		<td class='text-center'>"+levelTxt+"</td>" +
							"		<td class='text-center'>"+flagSpan+"</td>" +
							"		<td class='text-center'>￥"+data.allEmps[x].sal+"/月</td>" +
							"		<td class='text-center'>￥"+data.allEmps[x].comm+"/月</td>" +
							"		<td class='text-center'>"+new Date(data.allEmps[x].hiredate.time).format("yyyy-MM-dd")+"</td>" +
							"	</tr>" ; 
					$("#empBody").append(ele) ;
				}
			}
		},"json") ;
	}
	$("[id*=editBtn-]").each(function(){
		var deptno = this.id.split("-")[1] ;
		$(this).on("click",function(){
			var maxnum = $("#maxnum-" + deptno).val() ;
			if (parseInt(maxnum) > 0) {	// 表示现在设置的上限有效
				// console.log("***** deptno = " + deptno + "，人数上限：" + maxnum) ;
				if (window.confirm("您确定要修改该部门的人数上限吗？")) {
					$.post("pages/back/dept/DeptServletBack/editMaxnum",{deptno:deptno,maxnum:maxnum},function(data) {
						operateAlert(data.trim()=="true" , "部门人数上限修改成功！","部门人数上限修改失败，部门上限小于当前部门人数！") ;
					},"text") ;
				} 
			} else {
				operateAlert(false , "","部门最大人数设置有错误，请确认后执行！") ;
			}
		}) ;
	})
})