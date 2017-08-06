$(function(){
	buiRegisterDialog();
})

function buiRegisterDialog(){
	BUI.use('bui/overlay',function(Overlay){
		var dialog = new Overlay.Dialog({
			title:"新增商品规格",
			width:400,
			height:150,
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

function submit(){
	var pId = $("#product-id").val();
	var pName = $("#product-name").val();
	//var pType = $("#product-type").options[$("#product-type").selectedIndex].text;
	var pPrice = $("#product-price").val();
	var pCount = $("#product-count").val();
	var pDescribe = $("#product-decribe").val();
	var pStandard = new Array();
	var length = $("#product-standard-containder").children().length;
	var items = $("#product-standard-containder").children();
	for(var i = 0;i < length;++i){
		pStandard[i] = items.eq(i).text;
		alert(items.eq(i).text);
	}
	
	if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(pPrice))){
		alert("请输入合适的价格");	
	}
}
