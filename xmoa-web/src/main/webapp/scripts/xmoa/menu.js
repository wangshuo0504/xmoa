/**
 * Created by wangshuo on 2017/2/28.
 */

$.get(root + '/scripts/xmoa/json/module.json', function (modules) {
    var pageUrl=    window.location.pathname;
    var sidebar_menu = $(".sidebar-menu");
    if (modules) {
        sidebar_menu.append($("<li class='header'>" + modules.name + "</li>"))


        for (var module in modules.child) {
            var module_li = $("<li class='treeview'></li>");

            if (modules.child[module].child != null && modules.child[module].child.length != 0) {


                module_li.append($(" <a href='#'> <i class='fa " + modules.child[module].icon + "'></i> <span>"
                    + modules.child[module].name + "</span><span class='pull-right-container'><i class='fa fa-angle-left pull-right'></i></span></a>"));

                var m2_ul = $("<ul class='treeview-menu'></ul>");
                for (var m2 in modules.child[module].child) {
                    var childLi=$("<li/>");
                    if(pageUrl.indexOf(modules.child[module].child[m2].url)>=0){
                        childLi.addClass("active");
                        module_li.addClass("active");
                    }

                    childLi.append("<a href='" + modules.child[module].child[m2].url + "'><i class='fa fa-circle-o'></i> " + modules.child[module].child[m2].name + "</a>");
                    m2_ul.append(childLi);
                }
                module_li.append(m2_ul);
                sidebar_menu.append(module_li);
            } else {
                module_li.append($(" <a href='" + modules.child[module].url + "'> <i class='fa " + modules.child[module].icon + "'></i> <span>"
                    + modules.child[module].name + "</span></a>"));
            }
            if(pageUrl.indexOf(modules.child[module].url)>=0)
                module_li.addClass("active");
            sidebar_menu.append(module_li);
        }
    }
})
