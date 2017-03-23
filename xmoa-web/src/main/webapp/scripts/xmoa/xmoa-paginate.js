//Make sure jQuery has been loaded before app.js
if (typeof jQuery === "undefined") {
    throw new Error("xmoa-paginate 需要依赖jQuery");
}


$.fn.xmoa_paginate = function (options) {
    if (!options)
        options = {};
    var paginator = $(this);
    paginator.empty();
    var paginatorInfo=$("#"+paginator.attr("id")+"_info");
    paginatorInfo.empty();
    /* --------------------
     * - xmoa_paginate Options -
     * --------------------
     */
    defaultOptions = {
        pageSize: 10,
        buttonNum: 7,
        page: 1, //当前页数
        totalPage:0,//页总数
        sPageButtonDisabled: "",
        sPageButton: "paginate_button",
        sPageButtonActive: "active",
        sPageButtonDisabled: "disabled",
        sPrevious:"<<",
        sNext:">>",
        ellipsis:"...",
        refresh:function (e) {
            console.log(e);
        }
    }
    options=    $.extend(defaultOptions,options);

    var _range = function (len, start) {
        var out = [];
        var end;
        if (start === undefined) {
            start = 0;
            end = len;
        }
        else {
            end = start;
            start = len;
        }
        for (var i = start; i < end; i++) {
            out.push(i);
        }
        return out;
    };

    var _numbers = function (page, pages) {
        var
            numbers = [],
            buttons = options.buttonNum,
            half = Math.floor(buttons / 2);
        if (pages <= buttons) {
            numbers = _range(0, pages);
        }
        else if (page <= half) {
            numbers = _range(0, buttons - 2);
            numbers.push('ellipsis');
            numbers.push(pages - 1);
        }
        else if (page >= pages - 1 - half) {
            numbers = _range(pages - (buttons - 2), pages);
            numbers.splice(0, 0, 'ellipsis'); // no unshift in ie6
            numbers.splice(0, 0, 0);
        }
        else {
            numbers = _range(page - half + 2, page + half - 1);
            numbers.push('ellipsis');
            numbers.push(pages - 1);
            numbers.splice(0, 0, 'ellipsis');
            numbers.splice(0, 0, 0);
        }
        return numbers;
    }

    var getLiHtml=function (btnDisplay,btnClass,counter) {
        var li=$('<li/>',{'class': options.sPageButton + ' ' + btnClass,'page-num': counter });
        $('<a>').html(btnDisplay).appendTo(li);
      return li;
    }

    var initHtml = function () {
        if(options.totalPage==0)
            return;
        var ul = $("<ul class='pagination xmoa_pagination' ></ul>");
        var buttons = _numbers(options.page-1, options.totalPage);
        var btnDisplay, btnClass, counter = 1;
        var i, ien,page=options.page;

        btnDisplay = options.sPrevious;
        btnClass =  (page > 1 ? '' : ' ' + options.sPageButtonDisabled)+" sPrevious";
        getLiHtml(btnDisplay,btnClass,page-1).appendTo(ul);

        for (i = 0, ien = buttons.length; i < ien; i++) {

            var button = buttons[i];
            switch (button) {
                case 'ellipsis':
                    btnDisplay = options.ellipsis;
                    btnClass =  options.sPageButtonDisabled+" ellipsis";
                    break;
                default:
                    btnDisplay = button + 1;
                    btnClass = page === btnDisplay ?
                        options.sPageButtonActive : '';
                    break;
            }
            getLiHtml(btnDisplay,btnClass,counter).appendTo(ul);
            counter++;
        }
        btnDisplay = options.sNext;
        btnClass =  (page <  options.totalPage  ?  '' : ' ' + options.sPageButtonDisabled)+" sNext";
        getLiHtml(btnDisplay,btnClass,page+1).appendTo(ul);
        paginator.append(ul);
    }

    var initInfoHtml = function () {
        var str;
        if(options.totalNum<=0){
            str ="无数据.";
        }else{
            var first=(options.page-1)*options.pageSize+1;
            var last=(first+options.pageSize-1);
            if(last>options.totalNum)
                last=options.totalNum;
            str=" 共"+options.totalNum+"条数据,显示"+first+"-"+last+"条.";
        }

        paginatorInfo.html(str);
    }
    var bindFunciton=function(){
        paginator.find("li").each(function () {
           if($(this).hasClass("disabled")||$(this).hasClass("ellipsis")||$(this).hasClass("active"))
               return;
            $(this).bind('click',options.refresh)
        })
    }
    initHtml();
    initInfoHtml();
    bindFunciton();
};