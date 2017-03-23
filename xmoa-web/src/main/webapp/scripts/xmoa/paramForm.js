//$(function () {  此处不要加 加载完再执行的标示   会影响到angular调用时没有道路和路口数据
var $violationCode = $("#violationCode");
var $roadJunctionSelect = $("#roadJunctionSelect");
var $epRoadJunctionSelect = $("#epRoadJunctionSelect");
var $roadJunctionSelectCompare = $("#roadJunctionSelectCompare");
var $roadSelect = $("#roadSelect");
var $roadSelectCompare = $("#roadSelectCompare");
$areaTownCode = $("#areaTownCode");

$.ajaxSetup({
    async: false
});
if ($roadJunctionSelect.length > 0) {
    $.get(root + '/param/loadRoadJunctionList.do', function (list) {
        var optionStr = '';
        var optionStr2 = '';
        for (var i = 0; i < list.length; i++) {
            var data = list[i];
            if(data.road_name != null && data.road_name!=''){
                optionStr += '<option value="' + data.road_code + '">' + data.road_name + '</option>';
            }
            if ($roadJunctionSelectCompare.length > 0)
                if (i == 1){
                    if(data.road_name != null && data.road_name!=''){
                        optionStr2 += '<option value="' + data.road_code + '" selected>' + data.road_name + '</option>';
                    }
                }
                else{
                    if(data.road_name != null && data.road_name!='') {
                        optionStr2 += '<option value="' + data.road_code + '">' + data.road_name + '</option>';
                    }
                }

        }
        if ($roadJunctionSelectCompare.length > 0) {
            $(optionStr2).appendTo($roadJunctionSelectCompare);
            $roadJunctionSelect.children().remove();
        }
        $(optionStr).appendTo($roadJunctionSelect);
    });
}

if ($epRoadJunctionSelect.length > 0) {
    $.get(root + '/param/loadEpRoadJunctionList.do', function (list) {
        var optionStr = '';
        for (var i = 0; i < list.length; i++) {
            var data = list[i];
            optionStr += '<option value="' + data.road_code + '">' + data.road_name + '</option>';
        }
        $(optionStr).appendTo($epRoadJunctionSelect);
    });
}

if ($roadSelect.length > 0) {
    $.get(root + '/param/loadRoadList.do', function (list) {
        var optionStr = '';
        var optionStr2 = '';
        for (var i = 0; i < list.length; i++) {
            var data = list[i];
            optionStr += '<option value="' + data.road_code + '">' + data.road_name + '</option>';
            if ($roadSelectCompare.length > 0)
                if (i == 1)
                    optionStr2 += '<option value="' + data.road_code + '" selected>' + data.road_name + '</option>';
                else
                    optionStr2 += '<option value="' + data.road_code + '">' + data.road_name + '</option>';
        }
        if ($roadSelectCompare.length > 0) {
            $(optionStr2).appendTo($roadSelectCompare);
            $roadSelect.children().remove();
        }
        $(optionStr).appendTo($roadSelect);
    });
}

if ($violationCode.length > 0) {
    $.get(root + '/param/loadViolationList.do', function (list) {
        for (var i = 0; i < list.length; i++) {
            var data = list[i];
            var $option = $('<option value="' + data.code + '">(' + data.code + ')' + data.name + '</option>');
            $option.appendTo($violationCode);
        }
    });
}
if ($areaTownCode.length > 0) {
    $.get(root + '/param/loadAreaTownList.do', function (list) {
        for (var i = 0; i < list.length; i++) {
            var data = list[i];
            var $option = $('<option value="' + data.code + '">' + data.name + '</option>');
            $option.appendTo($areaTownCode);
        }
    });
}

$.ajaxSetup({
    async: true
});

// 初始化下拉框控件
$('.selectpicker').selectpicker({
    size: 8
});
//});

//获取区域名称
function getAreaName() {
    return $("#paramForm").find("#areaCode option:selected").text();
}
//获取道路名称
function getRoadName() {
    return $("#paramForm").find("#roadSelect option:selected").text();
}
//获取道路比较名称
function getRoadCompareName() {
    return $("#paramForm").find("#roadSelectCompare option:selected").text();
}
//获取路口名称
function getRoadJunctionName() {
    return $("#paramForm").find("#roadJunctionSelect option:selected").text();
}
//获取路口比较名称
function getRoadJunctionCompareName() {
    return $("#paramForm").find("#roadJunctionSelectCompare option:selected").text();
}
//获取区域名称
function getAreaTownName() {
    return $("#paramForm").find("#areaTownCode option:selected").text();
}
//获取违法行为编码
function getSelectedBehavior() {
    return $("#paramForm").find("#violationCode option:selected").text();
}

function getTimeByType(date, type) {

    var str = date.split('-');
    if (type == 'day' || type == 'week') {
        return date;
    } else if (type == 'month') {
        return str[0] + '-' + str[1];
    } else if (type == 'year') {
        return str[0];
    }
}

//获取开始时间(中文)：2016年12月12日
function getDatetimeStart4CH() {
    var type = $("#time-type").val();
    if(type == 'week'){
        return $("#paramForm_weekstart").val();
    }
    var dateStr = $("#paramForm_realstart").val().split('-');
    var interval=['年','月','日'];
    var result='';
    for(var i=0;i<dateStr.length;i++){
        result+=dateStr[i]+interval[i];
    }
    return result;
}

//获取开始时间
function getDatetimeStart() {
    var type = $("#time-type").val();
    var start = $("#paramForm_realstart").val();
    return getTimeByType(start, type);
}
//获取结束时间
function getDatetimeEnd() {
    var type = $("#time-type").val();
    var end = $("#paramForm_realend").val();
    return getTimeByType(end, type);

}

//获取开始周
function getDateWeekStart() {
    return $("#paramForm_weeknum-start").val();
    // return $("#paramForm").find("#"+ $(".dateTypeSwitch").val()+"start").val();
}

//获取结束周
function getDateWeekEnd() {
    return $("#paramForm_weeknum-end").val();
    // return $("#paramForm").find("#"+ $(".dateTypeSwitch").val()+"end").val();
}

function getStartDate4SubTitle() {
    var type = $("#time-type").val();
    var startDateTitle = '';
    if (type == 'week')
        startDateTitle = $("#paramForm_weekstart").val().replace("　", "");
    else {
        var end = $("#paramForm_realstart").val();
        startDateTitle =  getTimeByType(end, type);
    }
    if(startDateTitle!=null){
        startDateTitle =  startDateTitle.replace(/-/g, ".").replace('~', '-');
    }
    return startDateTitle;
}

function getEndDate4SubTitle() {
    var type = $("#time-type").val();
    var end_str = null;
    if (type == 'week') {
        end_str = $("#paramForm_weekend").val();
        if (end_str != null) {
            end_str = end_str.replace("　", "");
        }
    } else {
        var end = $("#paramForm_realend").val();
        if (end != null)
            end_str = getTimeByType(end, type);
    }
    if(end_str!=null){
        end_str =  end_str.replace(/-/g, ".").replace('~', '-');
    }
    return end_str;
}
//统一的获取副标题方法
function getSubTitle(){
    var subTitle = getAreaName() ;
    if(getRoadJunctionName()!=null && getRoadJunctionName()!=''){
        subTitle += ' '+getRoadJunctionName() ;
    }
    if(getRoadName()!=null && getRoadName()!=''){
        subTitle += ' '+getRoadName() ;
    }
    if(getAreaTownName()!=null && getAreaTownName()!=''){
        subTitle += ' '+getAreaTownName() ;
    }
    if(getRoadCompareName()!=null && getRoadCompareName()!=''){
        subTitle += 'VS' + getRoadCompareName()  ;
    }
    if(getRoadJunctionCompareName()!=null && getRoadJunctionCompareName()!=''){
        subTitle += 'VS' + getRoadJunctionCompareName()  ;
    }
    if(getSelectedBehavior()!=null && getSelectedBehavior()!=''){
        subTitle += ' '+getSelectedBehavior();
    }
    if(getStartDate4SubTitle()!=null && getStartDate4SubTitle()!=''){
        subTitle += ' '+getStartDate4SubTitle() ;
    }
    if(getEndDate4SubTitle()!=null && getEndDate4SubTitle()!=''){
        subTitle +=  '~' +getEndDate4SubTitle() ;
    }
    return subTitle;
}

var $charts;
$(document).ready(function(){
    $charts = $("[id*=hart]");  //不是匹配Chart是因为有些是小写的chart
    for (var i=0;i<$charts.length;i++) {
        var $chart = $($charts[i]);
        $chart.addClass("no-print");
        $chart.after("<div id='" + $chart[0].id + "Img' class='print-show'></div>");
    }

});

function printPage(){
    for (var i=0;i<$charts.length;i++) {
        var chart = $charts[i];
        var $canvases = $(chart).find('canvas');
        var $img = $("#" + chart.id + 'Img');
        $img.empty();
        $img.attr("style", $(chart).attr("style"));
        if ($(chart).find("textarea").length>0) {
            //数据视图只显示数据
            $($(chart).find("textarea").clone()).appendTo($img);
        }else{
            for (var j=0;j<$canvases.length;j++) {
                var canvas = $canvases[j];
                var imgSrc = canvas.toDataURL('image/png');
                var style = $(canvas).attr("style");
                $("#"+chart.id+'Img').attr("src",imgSrc);
                $("<img src='"+imgSrc+"' style='"+ style +"'>").appendTo($img);
            }
        }
    }
    print();
}