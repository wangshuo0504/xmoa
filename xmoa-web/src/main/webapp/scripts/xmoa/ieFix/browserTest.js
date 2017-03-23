var BrowserDetect = {
      init: function () {
         this.browser = this.searchString(this.dataBrowser) || "一个未知的浏览器类型";
         this.version = this.searchVersion(navigator.userAgent)
         || this.searchVersion(navigator.appVersion)
         || "一个未知的浏览器版本";
      },
      searchString: function (data) {
		  
         for (var i=0;i<data.length;i++) {
            var dataString = data[i].string;
            var dataProp = data[i].prop;
            this.versionSearchString = data[i].versionSearch || data[i].identity;
            if (dataString) {
               if (dataString.indexOf(data[i].subString) != -1)
                  return data[i].identity;
            }
            else if (dataProp)
               return data[i].identity;
         }
      },
      searchVersion: function (dataString) {
         var index = dataString.indexOf(this.versionSearchString);
         if (index == -1) return;
         return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
      },
      dataBrowser:[
         {
            string: navigator.userAgent,
            subString: "Chrome",
            identity: "Chrome"
         },
         {
            string: navigator.vendor,
            subString: "Apple",
            identity: "Safari",
            versionSearch: "Version"
         },
         {
            prop: window.opera,
            identity: "Opera",
            versionSearch: "Version"
         },
         { 
            string: navigator.userAgent,
            subString: "Firefox",
            identity: "Firefox"
         },
         {
            string: navigator.userAgent,
            subString: "MSIE",
            identity: "Explorer",
            versionSearch: "MSIE"
         },
         {
            string: navigator.userAgent,
            subString: "Gecko",
            identity: "Mozilla",
            versionSearch: "rv"
         },
         { // for older Netscapes (4-)
            string: navigator.userAgent,
            subString: "Mozilla",
            identity: "Netscape",
            versionSearch: "Mozilla"
         }
      ]
   };

var DownloadBrowser= {
	isWinXP:navigator.userAgent.indexOf("Windows NT 5.1") > -1 || navigator.userAgent.indexOf("Windows XP") > -1,
	isWin2003:navigator.userAgent.indexOf("Windows NT 5.2") > -1 || navigator.userAgent.indexOf("Windows 2003") > -1,
	isWinVista:navigator.userAgent.indexOf("Windows NT 6.0") > -1 || navigator.userAgent.indexOf("Windows Vista") > -1,
	isWin7:navigator.userAgent.indexOf("Windows NT 6.1") > -1 || navigator.userAgent.indexOf("Windows 7") > -1,
	init:function(basePath){
		this.browserDownBasePath=basePath;
		this.setIEhref()
		},
	setIEhref:function(){
			var ieURL
			var browserDownBasePath
			if(this.isWinXP || this.isWinVista || this.isWin2003){
				ieURL=this.browserDownBasePath+'IE8-WindowsXP-x86-CHS.2728888507.exe';
				$("#versionIE").attr('href',ieURL)
				}
			else if(this.isWin7){
				ieURL=this.browserDownBasePath+'IE11-Windows6.1-x86-zh-cn.exe';
				$("#versionIE").attr('href',ieURL)
				}	
			}
	}; 
var BrowserTest={
	init:function(option){
		BrowserDetect.init();
		if(BrowserDetect.version <= 8 && BrowserDetect.browser == "Explorer"){
			layer.open({
			type: 1,
			title:'升级提醒',
			skin: 'layui-layer-lan',
			shadeClose: false,
			shade: 0.7,
			area: ['550px', 'auto'],
			content:'<section id="updateTips" class="box-space-a"><p class="text-center font18">您的IE浏览器版本过低，请安装更高IE浏览器版本！</p><div class="text-center"><a class="btn btn-primary space-t" id="versionIE">立即安装</a></div></section>'
			})
			DownloadBrowser.init(option.basePath);
		}
	}
}
