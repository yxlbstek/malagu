window.currentIndex = 1;
window.pageNo = 1;
window.douNextBtnChange = false;

window.initPage = function(dataSet, pageControl) {
	pageControl.removeAllChildren();
	window.pageNo = dataSet.getData().pageCount;
	//向左翻页
	var leftBtnCon = new dorado.widget.Container({
		className: "pageLeftBtnCon"
	});
	var leftBtn = new dorado.widget.Control({
		className: "pageLeftBtn",
		onCreateDom: function(self, arg) {
			$(arg.dom).hover(
				function(){
					if (window.currentIndex > 1) {
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
			if (window.currentIndex > 1) {
				indexBtnClick(window.currentIndex - 1, dataSet);
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
		id: dataSet.get("id") + "_firstIndexBtn",
		text: "1",
		className: "indexPageBtn",
		onCreateDom: function(self, arg) {
			if (window.currentIndex == 1) {
				$(".indexPageBtn").removeClass("current");
				$(arg.dom).addClass("current");
			}
		},
		onClick: function(self, arg) {
			indexBtnClick(parseInt(self.get("text")), dataSet);
		}
	});
	indexBtnCon.addChild(firstIndexBtn);
	//向左翻5页按钮
	var leftDouNextBtn = new dorado.widget.Label({
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
			leftDouNextClick(dataSet);
		}
	});
	indexBtnCon.addChild(leftDouNextBtn);
	
	if (window.pageNo > 7) {
		//中间页码按钮
		for (var i = 2; i <= 6; i++) {
			var indexBtn = new dorado.widget.Label({
				text: i,
				className: "indexPageBtn",
				tags: "centerIndexBtnTags",
				onCreateDom: function(self, arg) {
					$(arg.dom).addClass("centerIndexBtn");
					if (parseInt(self.get("text")) == window.currentIndex) {
						$(".indexPageBtn").removeClass("current");
						$(arg.dom).addClass("current");
					}
				},
				onClick: function(self, arg) {
					indexBtnClick(parseInt(self.get("text")), dataSet);
				}
			});
			indexBtnCon.addChild(indexBtn);
		}
		
		//向右翻5页按钮
		var rightDouNextBtn = new dorado.widget.Label({
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
				rightDouNextClick(dataSet);
			}
		});
		indexBtnCon.addChild(rightDouNextBtn);
		
		//最后一页页码按钮
		var lastIndexBtn = new dorado.widget.Label({
			id: dataSet.get("id") + "_lastIndexBtn",
			text: window.pageNo,
			className: "indexPageBtn",
			onCreateDom: function(self, arg) {
				if (window.currentIndex == window.pageNo) {
					$(".indexPageBtn").removeClass("current");
					$(arg.dom).addClass("current");
				}
			},
			onClick: function(self, arg) {
				indexBtnClick(parseInt(self.get("text")), dataSet);
			}
		});
		indexBtnCon.addChild(lastIndexBtn);
		
	} else {
		for (var i = 2; i <= window.pageNo; i++) {
			var indexBtn = new dorado.widget.Label({
				text: i,
				className: "indexPageBtn",
				tags: "centerIndexBtnTags",
				onCreateDom: function(self, arg) {
					$(arg.dom).addClass("centerIndexBtn");
					if (parseInt(self.get("text")) == window.currentIndex) {
						$(".indexPageBtn").removeClass("current");
						$(arg.dom).addClass("current");
					}
				},
				onClick: function(self, arg) {
					indexBtnClick(parseInt(self.get("text")), dataSet);
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
					if (window.currentIndex < pageNo) {
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
			if (window.currentIndex < window.pageNo) {
				indexBtnClick(window.currentIndex + 1, dataSet);
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
		id: dataSet.get("id") + "_numEditor",
		value: window.currentIndex,
		className: "numEditor",
		onFocus: function(self, arg) {
			
		},
		onBlur: function(self, arg) {
			var reg = /^\+?[1-9][0-9]*$/;
			if (reg.test(self.get("value"))) {
				if (window.currentIndex != parseInt(self.get("value"))) {
					if (parseInt(self.get("value")) >= window.pageNo) {
						indexBtnClick(window.pageNo, dataSet);
					} else {
						indexBtnClick(parseInt(self.get("value")), dataSet);
					}
				}
			} else {
				self.set("value", window.currentIndex);
			}
		}
	});
	
	//页Label
	var rightLabel = new dorado.widget.Label({
		text: "页",
		className: "yLabel"
	});
	
	pageControl.addChild(leftBtnCon);
	pageControl.addChild(indexBtnCon);
	pageControl.addChild(pageRightBtnCon);
	pageControl.addChild(leftLabel);
	pageControl.addChild(numEditor);
	pageControl.addChild(rightLabel);
}

function indexBtnClick(oldIndex, dataSet) {
	if (window.currentIndex != oldIndex) {
		$(".indexPageBtn").removeClass("current");
		buildCurrentBtn(oldIndex);
		if (window.pageNo > 7) {
			if (oldIndex >= 5) {
				$(".leftDouNext").removeClass("leftHide");
			} else {
				$(".leftDouNext").addClass("leftHide");
			}
				
			if (oldIndex + 2 < window.pageNo - 1) {
				$(".rightDouNext").removeClass("rightHide");
			} else {
				$(".rightDouNext").addClass("rightHide");
			}
			
			if ((window.currentIndex < 5 && oldIndex >= 5)
					|| (window.currentIndex >= 5 && oldIndex <= 5)
					|| (window.currentIndex >= 5 && oldIndex > 5 && oldIndex <= window.pageNo - 3) ) {
				window.douNextBtnChange = true;
			}

			window.currentIndex = oldIndex;
			if (window.douNextBtnChange) {
				if (window.currentIndex > 4 && window.currentIndex < window.pageNo - 3) {
					refreshIndexBtns(window.currentIndex - 2);
				} else if (window.currentIndex > 1 && window.currentIndex <= 4) {
					refreshIndexBtns(2);
				} else if (window.currentIndex < window.pageNo && window.currentIndex >= window.pageNo - 4) {
					refreshIndexBtns(window.pageNo - 5);
				} else if (window.currentIndex == 1) {
					refreshIndexBtns(2);
					$("#d_" + dataSet.get("id") + "_firstIndexBtn").addClass("current");
				} else if (window.currentIndex == window.pageNo) {
					refreshIndexBtns(window.pageNo - 5);
					$("#d_" + dataSet.get("id") + "_lastIndexBtn").addClass("current");
				}
			}
			
		} else {
			window.currentIndex = oldIndex;
		}
		viewMain.get("#" + dataSet.get("id") + "_numEditor").set("value", window.currentIndex);
		dataSet.set("pageNo", window.currentIndex).flush();
	}
}

function leftDouNextClick(dataSet) {
	window.currentIndex -= 5;
	if (window.currentIndex >= 5) {
		$(".leftDouNext").removeClass("leftHide");
	} else {
		$(".leftDouNext").addClass("leftHide");
	}
	
	if (window.currentIndex + 2 < window.pageNo - 1) {
		$(".rightDouNext").removeClass("rightHide");
	} else {
		$(".rightDouNext").addClass("rightHide");
	}
	
	
	if (window.currentIndex > 4) {
		refreshIndexBtns(window.currentIndex - 2);
	} else if (window.currentIndex > 1 && window.currentIndex <= 4) {
		refreshIndexBtns(2);
	} else {
		refreshIndexBtns(2);
		$("#d_" + dataSet.get("id") + "_firstIndexBtn").addClass("current");
	}
	dataSet.set("pageNo", window.currentIndex).flush();
}

function rightDouNextClick(dataSet) {
	window.currentIndex += 5;
	if (window.currentIndex >= 5) {
		$(".leftDouNext").removeClass("leftHide");
	} else {
		$(".leftDouNext").addClass("leftHide");
	}
	
	if (window.currentIndex + 2 < window.pageNo - 1) {
		$(".rightDouNext").removeClass("rightHide");
	} else {
		$(".rightDouNext").addClass("rightHide");
	}
	
	
	if (window.currentIndex < window.pageNo - 3) {
		refreshIndexBtns(window.currentIndex - 2);
	} else if (window.currentIndex < window.pageNo && window.currentIndex >= window.pageNo - 4) {//最后一页
		refreshIndexBtns(window.pageNo - 5);
	} else {
		refreshIndexBtns(window.pageNo - 5);
		$("#d_" + dataSet.get("id") + "_lastIndexBtn").addClass("current");
	}
	dataSet.set("pageNo", window.currentIndex).flush();
}

function refreshIndexBtns(index) {
	$(".indexPageBtn").removeClass("current");
	viewMain.get("^centerIndexBtnTags").each(function(btn) {
		btn.set("text", index);
		if (index == window.currentIndex) {
			$(btn._dom).addClass("current");
		}
		index++;
	});
	viewMain.get("#" + dataSet.get("id") + "_numEditor").set("value", window.currentIndex);
	window.douNextBtnChange = false;
}

function buildCurrentBtn(oldIndex) {
	if (oldIndex == 1) {
		$("#d_" + dataSet.get("id") + "_firstIndexBtn").addClass("current");
	} else if (oldIndex == window.pageNo) {
		$("#d_" + dataSet.get("id") + "_lastIndexBtn").addClass("current");
	} else {
		viewMain.get("^centerIndexBtnTags").each(function(btn) {
			if (oldIndex == parseInt(btn.get("text"))) {
				$(btn._dom).addClass("current");
			}
		});
	}
}
















