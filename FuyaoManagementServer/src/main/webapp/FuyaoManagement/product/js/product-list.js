$(function(){
	/*var checkCookie = $.cookie("manager_token");
	if(checkCookie != undefined && checkCookie.length == 32){
		initPage();
	}else{
        window.location = "/FuyaoManagement/login/login.html";
	}*/
	
	initPage();
})

function initPage(){
	BUI.use('common/page');
	Store = BUI.Data.Store,
	Grid = BUI.Grid,
	columns = [
	    {title:'商品编号',dataIndex:"pId",width:150},
	    {title:"商品名称",dataIndex:"name",width:120},
	    {title:"商品描述",dataIndex:"describe",width:200},
	    {title:"商品价格",dataIndex:"price",width:80},
	    {title:"商品数量",dataIndex:"count",width:80},
	    {title:"商品类型",dataIndex:"type",width:100}, 
	    {title:"商品状态",dataIndex:"type",width:100},
	    {title:"上架时间",dataIndex:"date",width:150},
	    {title:"操作",dataIndex:"",width:180,renderer:function(){	
	    	var editText = '<p class="grid-command-span edit">编辑<p>';
	    	var sale =  '<p class="grid-command-span sale">上架<p>';
	    	var unsale = '<p class="grid-command-span unsale">下架<p>';
	    	return editText + sale + unsale;
	    }}
	],
	store = new Store({
	   	url: requestIP + "/FuyaoManagementServer/product/list",
	   	autoLoad: true,
	   	proxy: {
	   		method: "POST",
	   	},
	   	params: {
	    	start: 0,
	    	limit: 8,
	   	},
	    pageSize: 8
	}),
	grid = new Grid.Grid({
        render:'#grid',
        columns : columns,
        loadMask: true,
        store: store,
        plugins : [Grid.Plugins.CheckSelection,Grid.Plugins.AutoFit], //勾选插件、自适应宽度插件
        // 底部工具栏
        bbar:{ 
            // pagingBar:表明包含分页栏
            pagingBar:true
        }
    });
    grid.render();
    grid.on("cellclick", function(ev){
    	var sender = $(ev.domTarget);
    	if(sender.hasClass("edit")){
    		editProduct(ev.record);
    	}else if(sender.hasClass("sale")){
    		saleProduct(ev.record);
    	}else if(sender.hasClass("unsale")){
    		unsaleProduct(ev.record);
    	}
    });
    
	//创建表单，表单中的日历，不需要单独初始化
    var form = new BUI.Form.HForm({
        srcNode : '#searchForm'
    }).render();
 
    form.on('beforesubmit',function(ev){
        //序列化成对象
        var obj = form.serializeToObject();
        obj.start = 0; //返回第一页
        store.load(obj);
        return false;
    });
}

function resetLoad(){
	var data = {"start": 0, "limit": 8};
	store.set('url',requestIP + "/FuyaoManagementServer/product/list");
	store.load(data);
}

function find(){ 
	var pid = $("#product-id").val();
	var type = $("#product-type").val();
	var startDate = $("#start-date").val();
	var endDate = $("#end-date").val();
	alert(pid+" " +type+" "+startDate+" "+endDate);
	if(pid != ""){
		findByPid(pid);
	}else if(type != ""){
	}
}

function findByPid(pid){
	var result = store.getResult();
	for(var i=0;i<result.length;++i){
		alert(result[i].pId);
		alert(result.results);
		if(pid == result[i].pId){
			var data = result[i];
			grid.setItems(data);
			break;
		}
	}
}

function editProduct(product){
	window.location.href = "product-edit.html?pId=" + product.pId;
}

function saleProduct(product){
	if(product.status == "上架"){
		alert("该商品已上架");
	}else{
		var data = {"id": product.id, "status": "SALE"};
		$.ajax({
			type: "post",
			dataType: "json",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			url: requestIP + "/FuyaoManagementServer/product/status",
			async:true,
			success: function(data){
				alert(data.message);
				var param = {"start": 0, "limit": 8};
				store.set('url',requestIP + "/FuyaoManagementServer/product/list");
				store.load(param);
			},
			error: function(){
				
			}
		});
	}
}

function unsaleProduct(product){
	if(product.status == "下架"){
		alert("该商品已下架");
	}else{
		var data = {"id": product.id, "status": "UNSALE"};
		$.ajax({
			type: "post",
			dataType: "json",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			url: requestIP + "/FuyaoManagementServer/product/status",
			async:true,
			success: function(data){
				alert(data.message);
				var param = {"start": 0, "limit": 8};
				store.set('url',requestIP + "/FuyaoManagementServer/product/list");
				store.load(param);
			},
			error: function(){
				
			}
		});
	}
}