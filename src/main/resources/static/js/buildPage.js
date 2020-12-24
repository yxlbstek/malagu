window.initPage = function(dataSet, pageInfo) {
	console.log("id: " + pageInfo.get("id"));
	pageInfo.removeAllChildren();
	var entityList = dataSet.getData();
	var pageCount = entityList.pageCount;//总页数
	var pageNo = entityList.pageNo;//当前页
	
	//向左翻页
	var leftBtnCon = new dorado.widget.Container({
		className: "pageLeftBtnCon"
	});
	var leftBtn = new dorado.widget.Control({
		className: "pageLeftBtn",
		onCreateDom: function(self, arg) {
			$(arg.dom).hover(
				function(){
					if (pageNo > 1) {
						$(arg.dom).removeClass("leftUnClick");
						$(arg.dom).addClass("leftClick");
					} else {
						$(arg.dom).removeClass("leftClick");
						$(arg.dom).addClass("leftUnClick");
					}
				},
				function(){
					$(arg.dom).removeClass("leftUnClick");
					$(arg.dom).removeClass("leftClick");
				}
			)
		},
		onClick: function(self, arg) {
			if (pageNo > 1) {
				pageNo = pageNo - 1;
				entityList.previousPage();
				refreshPageInfoBtns(pageNo, pageCount, pageInfo);
			}
		}
	});
	leftBtnCon.addChild(leftBtn);
	
	//页码按钮container
	var indexBtnCon = new dorado.widget.Container({
		className: "indexBtnCon",
		layout: new dorado.widget.layout.HBoxLayout()
	});
	
	//第一页页码按钮
	var firstIndexBtn = new dorado.widget.Label({
		text: "1",
		className: "indexPageBtn",
		tags: "otherIndexBtnTags",
		onCreateDom: function(self, arg) {
			if (pageNo == 1) {
				$(".indexPageBtn").removeClass("current");
				$(arg.dom).addClass("current");
			}
		},
		onClick: function(self, arg) {
			pageNo = 1;
			entityList.firstPage();
			refreshPageInfoBtns(pageNo, pageCount, pageInfo);
		}
	});
	indexBtnCon.addChild(firstIndexBtn);
	//向左翻5页按钮
	var leftDouNextBtn = new dorado.widget.Label({
		id: pageInfo.get("id") + "_leftDouBtn",
		text: "...",
		className: "indexPageBtn",
		onCreateDom: function(self, arg) {
			$(arg.dom).addClass("leftDouNext");
			$(arg.dom).addClass("leftHide");
			$(arg.dom).hover(
				function(){
					$(arg.dom).text("");
				},
				function(){
					$(arg.dom).text("...");
				}
			)
		},
		onClick: function(self, arg) {
			pageNo = pageNo - 5 > 1 ? pageNo -5 : 1;
			entityList.gotoPage(pageNo);
			refreshPageInfoBtns(pageNo, pageCount, pageInfo);
		}
	});
	indexBtnCon.addChild(leftDouNextBtn);
	
	//中间页码
	if (pageCount > 7) {
		for (var i = 2; i <= 6; i++) {
			var indexBtn = new dorado.widget.Label({
				text: i,
				className: "indexPageBtn",
				tags: "centerIndexBtnTags",
				onCreateDom: function(self, arg) {
					$(arg.dom).addClass("centerIndexBtn");
					if (parseInt(self.get("text")) == pageNo) {
						$(".indexPageBtn").removeClass("current");
						$(arg.dom).addClass("current");
					}
				},
				onClick: function(self, arg) {
					pageNo = parseInt(self.get("text"));
					entityList.gotoPage(pageNo);
					refreshPageInfoBtns(pageNo, pageCount, pageInfo);
				}
			});
			indexBtnCon.addChild(indexBtn);
		}
		
		//向右翻5页按钮
		var rightDouNextBtn = new dorado.widget.Label({
			id: pageInfo.get("id") + "_rightDouBtn",
			text: "...",
			className: "indexPageBtn",
			onCreateDom: function(self, arg) {
				$(arg.dom).addClass("rightDouNext");
				$(arg.dom).hover(
					function(){
						$(arg.dom).text("");
					},
					function(){
						$(arg.dom).text("...");
					}
				)
			},
			onClick: function(self, arg) {
				pageNo = pageNo + 5 >= pageCount ? pageCount : pageNo + 5;
				entityList.gotoPage(pageNo);
				refreshPageInfoBtns(pageNo, pageCount, pageInfo);
			}
		});
		indexBtnCon.addChild(rightDouNextBtn);
		
		//最后一页页码按钮
		var lastIndexBtn = new dorado.widget.Label({
			text: pageCount,
			className: "indexPageBtn",
			tags: "otherIndexBtnTags",
			onCreateDom: function(self, arg) {
				if (pageNo == pageCount) {
					$(".indexPageBtn").removeClass("current");
					$(arg.dom).addClass("current");
				}
			},
			onClick: function(self, arg) {
				pageNo = pageCount;
				entityList.lastPage();
				refreshPageInfoBtns(pageNo, pageCount, pageInfo);
			}
		});
		indexBtnCon.addChild(lastIndexBtn);
		
	} else {
		for (var i = 2; i <= pageCount; i++) {
			var indexBtn = new dorado.widget.Label({
				text: i,
				className: "indexPageBtn",
				tags: "centerIndexBtnTags",
				onCreateDom: function(self, arg) {
					$(arg.dom).addClass("centerIndexBtn");
					if (parseInt(self.get("text")) == pageNo) {
						$(".indexPageBtn").removeClass("current");
						$(arg.dom).addClass("current");
					}
				},
				onClick: function(self, arg) {
					pageNo = parseInt(self.get("text"));
					entityList.gotoPage(pageNo);
					refreshPageInfoBtns(pageNo, pageCount, pageInfo);
				}
			});
			indexBtnCon.addChild(indexBtn);
		}
	}
	
	//向右翻页
	var pageRightBtnCon = new dorado.widget.Container({
		className: "pageRightBtnCon"
	});
	var rightBtn = new dorado.widget.Control({
		className: "pageRightBtn",
		onCreateDom: function(self, arg) {
			$(arg.dom).hover(
				function(){
					if (pageNo < pageCount) {
						$(arg.dom).removeClass("rightUnClick");
						$(arg.dom).addClass("rightClick");
					} else {
						$(arg.dom).removeClass("rightClick");
						$(arg.dom).addClass("rightUnClick");
					}
				},
				function(){
					$(arg.dom).removeClass("rightUnClick");
					$(arg.dom).removeClass("rightClick");
				}
			)
		},
		onClick: function(self, arg) {
			if (pageNo < pageCount) {
				pageNo = pageNo + 1;
				entityList.nextPage(pageNo);
				refreshPageInfoBtns(pageNo, pageCount, pageInfo);
			}
		}
	});
	pageRightBtnCon.addChild(rightBtn);
	
	//跳转至Label
	var leftLabel = new dorado.widget.Label({
		text: "跳转至",
		className: "tzLabel"
	});
	
	//跳转页数Editor
	var numEditor = new dorado.widget.TextEditor({
		id: pageInfo.get("id") + "_numEditor",
		value: pageNo,
		className: "numEditor",
		onFocus: function(self, arg) {
			
		},
		onBlur: function(self, arg) {
			var reg = /^\+?[1-9][0-9]*$/;
			if (reg.test(self.get("value"))) {
				if (pageNo != parseInt(self.get("value"))) {
					if (parseInt(self.get("value")) >= pageCount) {
						pageNo = pageCount;
						entityList.lastPage();
						refreshPageInfoBtns(pageNo, pageCount, pageInfo);
					} else {
						pageNo = parseInt(self.get("value"));
						entityList.gotoPage(pageNo);
						refreshPageInfoBtns(pageNo, pageCount, pageInfo);
					}
				}
			} else {
				self.set("value", pageNo);
			}
		}
	});
	
	//页Label
	var rightLabel = new dorado.widget.Label({
		text: "页",
		className: "yLabel"
	});
	
	pageInfo.addChild(leftBtnCon);
	pageInfo.addChild(indexBtnCon);
	pageInfo.addChild(pageRightBtnCon);
	pageInfo.addChild(leftLabel);
	pageInfo.addChild(numEditor);
	pageInfo.addChild(rightLabel);
}

/**
 * 构造pageInfo信息
 * @param pageNo 当前页
 * @param pageCount 总页数
 * @param pageInfo 
 */
function refreshPageInfoBtns(pageNo, pageCount, pageInfo) {
	if (pageCount > 7) {
		//隐藏、显示  左右翻5页按钮
		if (pageNo >= 5) {
			$("#d_" + pageInfo.get("id") + "_leftDouBtn").removeClass("leftHide");
		} else {
			$("#d_" + pageInfo.get("id") + "_leftDouBtn").addClass("leftHide");
		}
			
		if (pageNo < pageCount - 3) {
			$("#d_" + pageInfo.get("id") + "_rightDouBtn").removeClass("rightHide");
		} else {
			$("#d_" + pageInfo.get("id") + "_rightDouBtn").addClass("rightHide");
		}
		
		//给中间按钮重新赋值
		if ((pageNo > 1 && pageNo <= 4) || pageNo == 1) {
			changeCenterIndexBtnsText(2, pageNo);
		} else if (pageNo > 4 && pageNo < pageCount - 3) {
			changeCenterIndexBtnsText(pageNo - 2, pageNo);
		} else if ((pageNo < pageCount && pageNo >= pageCount - 4) || pageNo == pageCount) {
			changeCenterIndexBtnsText(pageCount - 5, pageNo);
		}
	}
	
	//跳转编辑框赋值
	viewMain.get("#" + pageInfo.get("id") + "_numEditor").set("value", pageNo);
	
	//构造当前按钮
	$(".indexPageBtn").removeClass("current");
	viewMain.get("^otherIndexBtnTags").each(function(btn) {
		if (pageNo == parseInt(btn.get("text"))) {
			$(btn._dom).addClass("current");
		}
	});
	viewMain.get("^centerIndexBtnTags").each(function(btn) {
		if (pageNo == parseInt(btn.get("text"))) {
			$(btn._dom).addClass("current");
		}
	});
}

/**
 * 给中间按钮重新赋值Text
 * @param index 起始页
 * @param pageNo 当前页
 */
function changeCenterIndexBtnsText(index, pageNo) {
	$(".indexPageBtn").removeClass("current");
	viewMain.get("^centerIndexBtnTags").each(function(btn) {
		btn.set("text", index);
		if (index == pageNo) {
			$(btn._dom).addClass("current");
		}
		index++;
	});
}

