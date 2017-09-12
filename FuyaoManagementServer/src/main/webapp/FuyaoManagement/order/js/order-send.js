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
	    	
	    }}
	],
	store = new Store({
	   	url: requestIP + "/FuyaoManagementServer/order/list",
	   	autoLoad: true,
	   	proxy: {
	   		method: "POST",
	   	},
	    params: {
	    	start: 0,
	    	limit: 8,
	   		status: "WAITRECEIVE"
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
	var data = {"status": "WAITRECEIVE", "orderId": id, "start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/find");
	store.load(data);
}

function findByName(name){
	var data = {"status": "WAITRECEIVE", "receiver": name, "start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/find");
	store.load(data);
}

function findByTel(tel){
	var data = {"status": "WAITRECEIVE", "tel": tel, "start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/find");
	store.load(data);
}

function findByDate(startDate, endDate){
	var data = {"status": "WAITRECEIVE", "startDate": startDate, "endDate": endDate, "start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/find");
	store.load(data);
}

function resetLoad(){
	var data = {"status": "WAITRECEIVE","start": 0};
	store.get('proxy').set('url',requestIP + "/FuyaoManagementServer/order/list");
	store.load(data);
}