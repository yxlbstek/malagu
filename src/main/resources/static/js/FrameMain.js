/**
 * 打开页面
 * caption 新页签标题
 * icon 新页签图标
 * path 页面路径
 */
window.openTab = function (caption, icon, path) {
	var tabControl = view.get("#tabControl");
	if (!path) {
		return;
	}
	var tab = tabControl.getTab(path);
	if (!tab) {
		tab = {
			$type: "IFrame",
			name: path,
			caption: caption,
			iconClass: icon,
			path: path,
			closeable: true
		}
		tab = tabControl.addTab(tab);
	} else {
		tab.getControl().reload();
	}
	tabControl.set("currentTab", tab);
	tabControl.closeOtherTabs(tabControl.get("currentTab"));
};

/**
 * 刷新页面
 * path 页面路径
 */
window.refreshTab = function (path) {
	var tab = view.get("#tabControl").getTab(path);
	if (tab) {
		if (tab instanceof dorado.widget.tab.IFrameTab) {
			tab.getControl().reload();
		} else {
			tab.refresh();
		}
	}
}

/**
 * 关闭当前页面
 */
window.closeTab = function () {
	var tab = view.get("#tabControl").get("currentTab");
	if (tab) {
		tabControl.closeTab(tab);
	}	
};

/**
 * 关闭所有页面
 */
window.closeTab = function () {
	view.get("#tabControl").closeAllTabs();	
};

/**
 * 关闭其他所有页面
 */
window.closeTab = function () {
	var tabControl = view.get("#tabControl");
	var tab = tabControl.get("currentTab");
	if (tab) {
		tabControl.closeOtherTabs(tab);
	}
};



