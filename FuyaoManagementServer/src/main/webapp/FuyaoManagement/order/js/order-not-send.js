$(function(){
	Store = BUI.Data.Store,
	Grid = BUI.Grid,
	columns = [
	    {title:'订单编号',dataIndex:"orderId",width:150},
	    {title:"商品名称",dataIndex:"name",width:120},
	    {title:"商品总价",dataIndex:"pTotal",width:80},
	    {title:"商品数量",dataIndex:"pCount",width:80},
	    {title:"运费",dataIndex:"sendCost",width:80},
	    {title:"订单总价",dataIndex:"total",width:100}, 
	    {title:"买家留言",dataIndex:"buyerMsg",width:200},
	    {title:"买家姓名",dataIndex:"receiver",width:90},
	    {title:"买家电话",dataIndex:"phone",width:110},
	    {title:"买家地址",dataIndex:"address",width:150},
	    {title:"下单时间",dataIndex:"date",width:150},
	    {title:"状态",dataIndex:"status",width:90},
	    {title:"操作",dataIndex:"",width:100,renderer:function(v){
	    	var sendBtn = "<button class='button-default'>发货</button>";
	    	return sendBtn;
	    }}
	],
	store = new Store({
	   	autoLoad: true,
	   	proxy: {
	   		url: requestIP + "/FuyaoManagementServer/order/list",
	   		method: "POST",
	   	},
	    params: {
	    	start: 0,
	    	limit: 8,
	   		status: "WAITSEND"
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
    /*
     * 处理grid单元格点击事件
     */
    grid.on('cellclick', function(ev){
    	var sender = $(ev.domTarget);
    	if(sender.hasClass("button-default")){
    		sendOrder(ev.record);
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
})

function sendOrder(item) {
	var data = {"id": item.id, "status": "WAITRECEIVE"};
	$.ajax({
		type:"post",
		dataType: "json",
		data: JSON.stringify(data),
		contentType: "application/json; charset=utf-8",
		url: requestIP + "/FuyaoManagementServer/order/change_status",
		async:true,
		success: function(data){
			var param = {"start": 0};
			store.load(param);
			if(data.result == "success"){
				alert("操作成功");
			} else if(data.result == "fault"){
				alert(data.message);
			}
		},
		error: function(){
			alert("服务器处理异常");
		}
	});
}

function find(){
	var id = $("#order-id").val();
	var name = $("#receiver").val();
	var tel = $("#receiver-tel").val();
	var startDate = $("#start-date").val();
	var endDate = $("#end-date").val();
	if(id != ""){
		findById(id);
	}else if(name != ""){
		findByName(name);
	}else if(tel != ""){
		findByTel(tel);
	}else if(startDate != "" && endDate != ""){
		findByDate(startDate, endDate);
	}
}

function findById(id){
	var data = {"status": "WAITSEND", "orderId": id, "start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/find");
	store.load(data);
}

function findByName(name){
	var data = {"status": "WAITSEND", "receiver": name, "start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/find");
	store.load(data);
}

function findByTel(tel){
	var data = {"status": "WAITSEND", "tel": tel, "start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/find");
	store.load(data);
}

function findByDate(startDate, endDate){
	var data = {"status": "WAITSEND", "startDate": startDate, "endDate": endDate, "start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/find");
	store.load(data);
}

function resetLoad(){
	var data = {"status": "WAITSEND","start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/list");
	store.load(data);
}
