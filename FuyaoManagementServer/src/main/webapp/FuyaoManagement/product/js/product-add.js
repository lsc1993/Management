$(function(){
	domain = "http://localhost:8080";
	imgPath = domain + ":1993/ImageResource/";
	requestIP = domain;
	buiRegisterDialog();
	chooseImages();
	chooseDescribeImages();
	chooseIndexImages();
	alert($.cookie("manager_token"));
})

function buiRegisterDialog(){
	BUI.use('bui/overlay',function(Overlay){
		var dialog = new Overlay.Dialog({
			title:"新增商品规格",
			width:280,
			height:125,
			contentId:"dialog-add-standard",
			success:function(){
				var standard = $("#standard-text").val().trim();
				if(standard != null && standard != "") {
					var li = document.createElement("li");
					li.innerHTML = standard;
					$("#product-standard-containder").prepend(li);
					
					var sli = document.createElement("li");
					var content = "<label>" + standard + "</label> <input class='standard-price-input' type='text' data-rules='{required:true}'/>";
					sli.innerHTML = content;
					$("#standard-price-containder").prepend(sli);
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
	var token = $.cookie("manager_token");
	if(token = "" || token == undefined){
		alert("用户认证失败");
		return;
	}
	var isSubmit = true;
	var pId = $("#product-id").val();
	var pName = $("#product-name").val();
	var pType = $("#product-type").find("option:selected").text();
	var pPrice = $("#product-price").val();
	var pCount = $("#product-count").val();
	var pDescribe = $("#product-decribe").val();
	var pStandard = new Array();
	var length = $("#product-standard-containder").children().length;
	var items = $("#product-standard-containder").children();
	
	var formData = new FormData();
	for(var i = 0;i < length;++i){
		if(items.eq(i).text() != "新建规格"){
			pStandard[i] = items.eq(i).text();
		}
	}
	
	var sLength = $("#standard-price-containder").children().length;
	var sItems = $("#standard-price-containder").children();
	for(var i = 0;i < sLength;++i){
		if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(sItems.eq(i).children().eq(1).val()))){
			isSubmit = false;
			alert("请输入合适的价格");
		} else{
			formData.append(sItems.eq(i).children().eq(0).text(),sItems.eq(i).children().eq(1).val());
		}
	}
	
	if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(pPrice))){
		isSubmit = false;
		alert("请输入合适的价格");
	}
	
	if(isSubmit){
		formData.append("managerToken", token);
		formData.append("pId",pId);
		formData.append("name",pName);
		formData.append("type",pType);
		formData.append("price",pPrice);
		formData.append("count",pCount);
		formData.append("describe",pDescribe);
		formData.append("standard",pStandard);
		formData.append("sImage", $("#product-index-imgs")[0].files[0]);
		for(var i = 0;i < images.length;++i){
			formData.append("pImage"+i, images[i]);
		}
		formData.append("pImageLen", images.length);
		for(var i = 0;i < describeImages.length;++i){
			formData.append("dImage"+i, describeImages[i]);
		}
		formData.append("dImageLen", describeImages.length);
		
		$.ajax({
			type: 'POST',
    		cache: false,
    		data: formData,
    		processData: false,
    		contentType: false,
			url: requestIP + "/FuyaoManagementServer/product/upload",
			async: true,
			success: function(data){
				alert(data.message);
			},
			error: function(){
				alert("服务器无响应，请稍后重试");
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

function chooseIndexImages(){
	$("#product-index-imgs").change(function(){
		if(this.files.length > 1){
			alert("首页展示图片只需要一张！");
			return;
		}
		
		var img = this.files[0];
		if(typeof FileReader == "undefined"){
			alert("不支持图片预览");
		} else{
			if(!/image\/\w+/.test(img.type)) {
				alert("请上传图片类型的文件");
				return false;
			}
	
			$("#index-imgs-list").children().remove();
			var reader = new FileReader();
			reader.readAsDataURL(img);
			reader.onload = function(e){
				var li = document.createElement("li");
				li.innerHTML = "<img src=" + this.result +" />";
				$("#index-imgs-list").append(li);
			}
		}
	});
}