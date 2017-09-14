$(function(){
	product = new Array();
	sid = new Array();
	getProduct();
	buiRegisterDialog();
})

function getProduct(){
	var url = window.location.href;
	var index = url.indexOf("pId=");
	var param = url.substring(index+4,url.length);
	var data = {"pId": param};
	$.ajax({
		type: "post",
		dataType: "json",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		url: requestIP + "/FuyaoManagementServer/product/detail",
		async: true,
		success: function(data){
			initProduct(data);
		},
		error: function(){
			alert("服务器无响应");
		}
	});
}

function initProduct(data){
	product["id"] = data.product.id;
	product["pId"] = data.product.pId;
	product["type"] = data.product.type;
    var name = data.product.name;
    var price = data.product.price;
    var count = data.product.count;
    var describe = data.product.describe;
    var standard = data.standard;
    var std = new Array();
    var sprice = new Array();
    if(standard.length > 0){
    	for(var i=0;i < standard.length;++i){
    		sid[i] = standard[i].id;
    		std[i] = standard[i].standard; 
    		sprice[i] = standard[i].price;
    		var li = document.createElement("li");
    		var sinput = document.createElement("input");
    		sinput.type = "text";
    		$(sinput).val(standard[i].standard);
    		var pinput = document.createElement("input");
    		pinput.type = "text";
    		$(pinput).val(standard[i].price);
    		li.appendChild(sinput);
    		li.appendChild(pinput);
    		$("#product-standard-containder").append(li);
    	}
    }
    $("#product-id").text(product["pId"]);
    $("#product-name").val(name);
    $("#product-count").val(count);
    $("#product-price").val(price);
    $("#product-decribe").val(describe);
}

function updateProduct(){
	var data = new FormData();
	data.append("id", product["id"]);
	data.append("pId", product["pId"]);
	data.append("type", product["type"]);
	data.append("name", $("#product-name").val());
	data.append("count", $("#product-count").val());
	data.append("price", $("#product-price").val());
	data.append("describe", $("#product-decribe").val());
	var std = new Array();
	var standards = $("#product-standard-containder").children();
	var len = standards.length;
	alert(len);
	for(var i=1;i<len;++i){
		alert(standards.eq(i).children().length);
		alert(standards.eq(i).children().eq(0).val());
		if(standards.eq(i).children().eq(0).val() != "新建规格"){
			std[i-1] = standards.eq(i).children().eq(0).val();
			alert(standards.eq(i).children().eq(0).val());
			if(!(/(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(standards.eq(i).children().eq(1).val()))){
				alert("请输入合适的价格");
				return;
			}else{
				data.append(standards.eq(i).children().eq(0).val(),standards.eq(i).children().eq(1).val());
			}
		}
	}
	data.append("standard", std);
	data.append("sid", sid);
	
	$.ajax({
		type: 'POST',
    	cache: false,
    	data: data,
    	processData: false,
    	contentType: false,
		url: requestIP + "/FuyaoManagementServer/product/change",
		async: true,
		success: function(data){
			alert(data.message);
		},
		error: function(){
			alert("服务器无响应，请稍后重试");
		}
	});
}


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
					var sinput = document.createElement("input");
		    		sinput.type = "text";
		    		$(sinput).val(standard);
		    		var pinput = document.createElement("input");
		    		pinput.type = "text";
		    		li.appendChild(sinput);
		    		li.appendChild(pinput);
		    		$("#product-standard-containder").append(li);
				}
				this.close();
			}
		});
		$("#add-standard").click(function(){
			dialog.show();
		});
	});
}