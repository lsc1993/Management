$(function(){
	buiRegisterDialog();
})

function buiRegisterDialog(){
	BUI.use('bui/overlay',function(Overlay){
		var dialog = new Overlay.Dialog({
			title:"新增商品规格",
			width:400,
			height:300,
			contentId:"dialog-add-standard",
			success:function(){
				var standard = $("#standard-text").val().trim();
				if(standard != null && standard != "") {
					var li = document.createElement("li");
					li.innerHTML = standard;
					$("#product-standard-containder").prepend(li);
				}
				this.close();
			}
		});
		$("#add-standard").click(function(){
			dialog.show();
		});
	});
	
}
