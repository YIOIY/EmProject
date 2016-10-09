$(function(){
	$(showImg).on("click",function(){
		var temp = "upload/emp/" + this.src.substring(this.src.indexOf("sm-") + 3) ;
		$(empImg).attr("src",temp) ;
		$(empImg).attr("style","width:770px;")
		$("#empPhoto").modal("toggle") ;
	}) ;
})