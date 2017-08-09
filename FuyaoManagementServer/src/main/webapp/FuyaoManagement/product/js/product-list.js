$(function(){
	 BUI.use(['bui/grid','bui/data'],function(Grid,Data){
		Store = Data.Store,
		Grid = Grid,
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
	    //store = Search.createStore('../data/student.json'),
	    store = new Store({
	    	url: "http://localhost:8080/FuyaoManagementServer/product/list",
	    	proxy: {
	    		method: "post",
	    				
	    	}
	    }),
	    grid = new Grid.Grid({
        	render:'#grid',
            columns : columns,
            loadMask: true,
            store: store,
            // 底部工具栏
            bbar:{
                // pagingBar:表明包含分页栏
                pagingBar:true
            }
        });
        grid.render();
	    /*gridCfg = Search.createGridCfg(cloumns,{
	    	plugins : [BUI.Grid.Plugins.CheckSelection] // 插件形式引入多选表格
	    });
	    		
	    var search = new Search({
	    	gridCfg:gridCfg,
	    	store: store
	    }),
	    grid = search.get('grid');
	    grid.on('cellclick',function(ev){
	    			
	    })*/
	});
})
