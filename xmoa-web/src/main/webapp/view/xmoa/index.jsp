<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/view/common/common.jsp" %>

</head>
<body class="sidebar-mini skin-blue-light" style="min-width: 1400px !important;">
<div class="wrapper">
    <!--载入头部-->
    <%@ include file="/view/xmoa/header.jsp" %>
    <!--载入菜单-->
    <%@ include file="/view/xmoa/menu.jsp" %>
    <!-- 中间内容部分 -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                资源管理
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> 面包</a></li>
                <li class="active">削</li>
            </ol>
        </section>

        <section class="content">
            <!-- Small boxes (Stat box) -->
            <div class="row">
                <div class="col-sm-3">
                    <div id="treeview-checkable" class="treeview">

                    </div>
                </div>
                <div class="col-sm-9">

                </div>
            </div>
            <div class="row">

            </div>
        </section>

    </div>

    <!--载入尾部-->
    <%@ include file="/view/xmoa/footer.jsp" %>
</div>

<!-- AdminLTE App -->
<script type="text/javascript" src="${root}/scripts/xmoa/plugins/adminLet/js/app.js"></script>
<script type="text/javascript">
    var defaultData = [
        {
            text: 'Parent 1',
            href: '#parent1',
            nodes: [
                {
                    text: 'Child 1',
                    href: '#child1',

                    nodes: [
                        {
                            text: 'Grandchild 1',
                            href: '#grandchild1'
                        },
                        {
                            text: 'Grandchild 2',
                            href: '#grandchild2'
                        }
                    ]
                },
                {
                    text: 'Child 2',
                    href: '#child2'
                },
                {
                    text: 'Parent 2',
                    href: '#parent2'
                },
                {
                    text: 'Parent 3',
                    href: '#parent3'
                },
                {
                    text: 'Parent 4',
                    href: '#parent4'
                },
                {
                    text: 'Parent 5',
                    href: '#parent5'
                }
            ]
        }

    ];

    var $checkableTree = $('#treeview-checkable').treeview({
        data: defaultData,
        showIcon: false,
        showCheckbox: false,
        onNodeChecked: function(event, node) {
            $('#checkable-output').prepend('<p>' + node.text + ' was checked</p>');
        },
        onNodeUnchecked: function (event, node) {
            $('#checkable-output').prepend('<p>' + node.text + ' was unchecked</p>');
        }
    });
</script>
</body>
</html>
