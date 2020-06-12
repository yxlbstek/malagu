/**
 * 统一提示
 * msg 提示信息
 * title 提示标题
 */
window.tip = function(msg, title) {
	dorado.MessageBox.show({
		title: title ? title : "操作提示",
		message: msg,
		icon: "QUESTION",
		buttons: ["ok"]
	});
}

/**
 * 异常拦截显示处理
 */
dorado.Exception.processException = function (e) {
	if (e.message.indexOf("HTTP 0") != -1) {
		window.location.reload();
	} else {
		window.tip(e.message, "系统提示");
	}
}

/**
 * 浮点数 格式化 字符串
 * value 需转换的浮点数
 * format 转换格式
 * return
 * 
 * 示例：
 * formatFloat(123456789.789, "#,##0.00"); // 123,456,789.79
 * formatFloat(123456789.789, "#"); // 123456790
 * formatFloat(123456789.789, "0"); // 123456790
 * formatFloat(123, "#.##"); // 123
 * formatFloat(123, "0.00"); // 123.00
 * formatFloat(0.123, "0.##"); // 0.12
 * formatFloat(0.123, "#.##"); // .12
 * formatFloat(-0.123, "0.##"); // -0.12
 * formatFloat(-0.123, "#.##"); // -.12
 * formatFloat(1234.567, "$#,##0.00/DAY"); // $1,234.57/DAY
 * formatFloat(02145375683, "(###)########"); // (021)45375683
 */
window.floatToString = function(value, format) {
	if (value) {
		return dorado.util.Common.formatFloat(value, format);
	}
	return null;
};

/**
 * 字符串 转 浮点数
 * value 需转换的字符串
 * return
 */
window.StringToFloat = function(value) {
	if (value) {
		return dorado.util.Common.parseFloat(value);
	}
	return null;
}

/**
 * 日期 转换成 字符成
 * date 日期
 * format 转换格式
 * return
 * 
 * 示例：
 * Y-m-d -- 2000-09-25 
 * H:i:s -- 23:10:30 
 * Y年m月d日 H点i分s秒 -- 2000年09月25日 23点10分30秒 
 */
window.formatDate = function(date, format) {
	if (date && format) {
		return date.formatDate(format);
	}
	return null;
}

/**
 * 字符成 转换成 日期
 * date 日期字符串
 * format 转换格式
 * return 
 * 
 * 格式示例：
 * Y-m-d 
 * H:i:s 
 * Y-m-d H:i:s 
 */
window.parseDate = function(date, format) {
	if (date && format) {
		return date.parseDate(date, format);
	}
	return null;
}





