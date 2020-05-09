
//***页面存在编辑， 刷新、关闭页面、浏览器提示***
window.addEventListener("beforeunload", function(event) {
	var frameTab = tabControl.getTab(window.oldCurrentNode.get("data.url"));
	var frameWindow = frameTab.get("control.iFrameWindow");
	if (frameWindow.viewMain && frameWindow.viewMain.entityStateChange) {
		frameWindow.viewMain.entityStateChange();
	}
    if (window.entityStateChange) {
    	(event || window.event).returnValue = "";
    }
    return "";
});

//***表格后台过滤, 输入搜索...***
window.commonSearch = function(arg, self, dataGrid) {
	if (arg.keyCode === 13) {
		return;
	}
	window.clearTimeout(self.urlQueryTimeout);
	self.urlQueryTimeout = window.setTimeout(function() {
		var key = self.get("value");
		if (!key) {
			dataGrid.filter();
		} else {
			dataGrid.filter([{
				junction: "or",
				criterions: [{
					property: "name",
					operator: "like",
					value: key
				}, {
					property: "description",
					operator: "like",
					value: key
				}]
			}]);
		}
	}, 250);
}



/**
 * ****获取IFrame页面中的控件*****
setTimeout(function(){
	var iFrame  = view.id("iFrame").getIFrameWindow();
	if(iFrame){
		iFrame.$id("memo").objects[0].set("readOnly", true);
		iFrame.$id("dsFile").objects[0].set("parameter", {reftable:"${configure['file.reftable.budget']}","businessId": businessId}).flushAsync();
	}
},500)
*/


/*
 * ******渲染表格按钮*******
var btns = [];
if (window['modifyBtn']) {
	var mb = {
		tagName: "label",
		style: {
			color: "#2196ff",
			fontSize: "13px",
			cursor: "pointer",
			visibility: "visible",
			marginLeft: "20px"
		},
		content: "修改",
		onclick: function() {
			dialog.show();
		}
	}
	btns.push(mb);
}

if (window['deleteBtn']) {
	var db = {
		tagName: "label",
		style: {
			color: "#FF5723",
			fontSize: "13px",
			cursor: "pointer",
			visibility: "visible",
			marginLeft: "20px"
		},
		content: "删除",
		onclick: function() {
			var entity = dgEquipmentCategory.get("currentEntity");
			dorado.MessageBox.confirm("确认删除？", function() {
				entity.remove();
				updateAction.execute();
			});
		}
	}
	btns.push(db);
}
$(arg.dom).empty().xCreate(btns);

****a标签******
if (window['showBtn']) {
	if (arg.data.get("path")) {
		var index = arg.data.get("path").indexOf("static");
		var path = arg.data.get("path").substring(index, arg.data.get("path").length);
		if (path.indexOf("project") != -1) {
			path = path.substring(path.indexOf("project"), arg.data.get("path").length);
		}
		
		var name = arg.data.get("fileName");
		var index = name.lastIndexOf(".");
		var suf = name.substring(index + 1, name.length);
		if (suf == "doc"
				|| suf == "docx" || suf == "xls"
				|| suf == "xlsx" || suf == "ppt") {
			var pathIndex = path.lastIndexOf(".");
			path = path.substring(0, pathIndex + 1) + "pdf";
		}
		var h = "<a href='" + path + "' target='_Blank'>查看</a>";
		$(h).appendTo(arg.dom);
	}
}
*/


