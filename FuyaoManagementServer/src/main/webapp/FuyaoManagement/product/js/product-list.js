$(function(){
	Store = BUI.Data.Store,
	Grid = BUI.Grid,
	columns = [
	    {title:'商品编号',dataIndex:"id",width:150},
	    {title:"商品名称",dataIndex:"name",width:120},
	    {title:"商品描述",dataIndex:"describe",width:200},
	    {title:"商品价格",dataIndex:"price",width:80},
	    {title:"商品数量",dataIndex:"count",width:80},
	    {title:"商品类型",dataIndex:"type",width:100}, 
	    {title:"操作",dataIndex:"",width:100,renderer:function(){	
	    }}
	],
	store = new Store({
	   	url: "http://localhost:8080/FuyaoManagementServer/product/list",
	   	autoLoad: true,
	   	proxy: {
	   		method: "POST",
	   	},
	    start: 0,
	    pageSize: 3
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
