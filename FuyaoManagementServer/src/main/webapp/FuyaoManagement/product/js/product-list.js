$(function(){
	Store = BUI.Data.Store,
	Grid = BUI.Grid,
	columns = [
	    {title:'商品编号',dataIndex:"id",width:150,renderer:function(v){
	    	return Search.createLink({
	    		id : 'detail' + v,
              	title : '商品详情',
              	text : v,
              	href : 'detail/product-detail.html'
	    	});
	   		}},
	    {title:"商品名称",dataIndex:"pName",width:120},
	    {title:"商品描述",dataIndex:"pDescribe",width:200},
	    {title:"商品价格",dataIndex:"pPrice",width:80},
	    {title:"商品数量",dataIndex:"pName",width:80},
	    {title:"商品规格",dataIndex:"pStandard",width:100},
	    {title:"操作",dataIndex:"",width:100,renderer:function(){	
	    }}
	],
	store = new Store({
	   	url: "http://localhost:8080/FuyaoManagementServer/product/list",
	    proxy: {
	    	method: "post",
	    	limit: 15,
	    	start: 1
	    }
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
 
    form.on('beforesubmit',function(ev) {
        //序列化成对象
        var obj = form.serializeToObject();
        obj.start = 0; //返回第一页
        store.load(obj);
        return false;
    });
})
