$(function(){
	buiRegisterDialog();
	chooseImages();
	chooseDescribeImages();
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
	var pType = $("#product-type").find("option:selected").text();
	var pPrice = $("#product-price").val();
	var pCount = $("#product-count").val();
	var pDescribe = $("#product-decribe").val();
	var pStandard = new Array();
	var length = $("#product-standard-containder").children().length;
	var items = $("#product-standard-containder").children();
	
	for(var i = 0;i < length;++i){
		if(items.eq(i).text() != "新建规格"){
			pStandard[i] = items.eq(i).text();
		}
	}
	
	var isSubmit = true;
	if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(pPrice))){
		isSubmit = false;
		alert("请输入合适的价格");
	}
	
	if(isSubmit){
		var formData = new FormData();
		formData.append("pId",pId);
		formData.append("name",pName);
		formData.append("type",pType);
		formData.append("price",pPrice);
		formData.append("count",pCount);
		formData.append("describe",pDescribe);
		formData.append("standard",pStandard);
		formData.append("images",images);
		formData.append("desImages",describeImages);
		
		$.ajax({
			type: "post",
			url: "http://localhost:8080/FuyaoManagementServer/manager/upload",
			data: formData,
			contentType: "application/json; charset=utf-8",
			async: true,
			success: function(result){
				
			},
			error: function(){
				
			}
		});
	}
	
}

images = new Array();
function chooseImages(){
	$("#product-imgs").change(function(){
		var imgs = new Array();
		for(var i = 0;i < this.files.length;++i){
			imgs.push(this.files[i]);
		}
		if(typeof FileReader == "undefined"){
			alert("不支持图片预览");
		} else{
			for (var i = 0;i < imgs.length;++i){
				if(!/image\/\w+/.test(imgs[i].type)) {
					alert("请上传图片类型的文件");
					return false;
				}
			}
			
			for(var i = 0;i < images.length;++i){
				for(var j = 0;j < imgs.length;++j){
					if(imgs[j].name == images[i].name){
						images.splice(i,1,imgs[j]);
						imgs.splice(j,1);
					}
				}
			}
			for(var i = 0;i < imgs.length;++i){
				images.push(imgs[i]);
			}
			$("#product-imgs-list").children().remove();
			for(var i = 0;i < images.length;++i){
				var reader = new FileReader();
				reader.readAsDataURL(images[i]);
				reader.onload = function(e){
					var li = document.createElement("li");
					li.innerHTML = "<img src=" + this.result +" />";
					$("#product-imgs-list").append(li);
				}
			}
		}
	});
}

describeImages = new Array();
function chooseDescribeImages(){
	$("#imgs-describe").change(function(){
		var imgs = new Array();
		for(var i = 0;i < this.files.length;++i){
			imgs.push(this.files[i]);
		}
		if(typeof FileReader == "undefined"){
			alert("不支持图片预览");
		} else{
			for (var i = 0;i < imgs.length;++i){
				if(!/image\/\w+/.test(imgs[i].type)) {
					alert("请上传图片类型的文件");
					return false;
				}
			}
			
			for(var i = 0;i < describeImages.length;++i){
				for(var j = 0;j < imgs.length;++j){
					if(imgs[j].name == describeImages[i].name){
						describeImages.splice(i,1,imgs[j]);
						imgs.splice(j,1);
					}
				}
			}
			for(var i = 0;i < imgs.length;++i){
				describeImages.push(imgs[i]);
			}
			$("#imgs-describe-list").children().remove();
			for(var i = 0;i < describeImages.length;++i){
				var reader = new FileReader();
				reader.readAsDataURL(describeImages[i]);
				reader.onload = function(e){
					var li = document.createElement("li");
					li.innerHTML = "<img src=" + this.result +" />";
					$("#imgs-describe-list").append(li);
				}
			}
		}
	});
}
