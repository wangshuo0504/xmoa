function getSortArray(lst) {
    var lstBeforeSort = [];
    for (var i in lst) {
        lstBeforeSort.push(lst[i]);
    }
    var lstAfterSort = lst.sort(function (a, b) {
        return b - a
    });
    var sortArray = [];

    function isContains(arrayList, o) {
        for (var i in arrayList) {
            if (arrayList[i] == o)
                return true;
        }
        return false;
    }

    for (var i in lstAfterSort) {
        for (var a in lstBeforeSort) {
            if (lstAfterSort[i] == lstBeforeSort[a]) {
                //如果在排序数列里面已经有了这个索引  那么继续往下找
                if (isContains(sortArray, a))
                    continue;
                else {
                    sortArray.push(a);
                    break;
                }
            }
        }
    }
    return sortArray;
}


function getTimeTypeName() {
    var timeType = $('#time-type').val();
    if (timeType == 'year')
        return '年';
    if (timeType == 'month')
        return '月';
    if (timeType == 'day')
        return '天';
    if (timeType == 'week')
        return '周';
}

function convertModuleTreeData(rows, selectedId) {
    if(!rows)
        return [];
    if (!selectedId)
        selectedId = '';

    var keyNodes = {}, parentKeyNodes = {};
    for (var i = 0; i < rows.length; i++) {
        var row_old = rows[i];
        var row = {};
        row.id = row_old["FID"];
        row.parentId = row_old["PARENT_ID"];
        row.text = row_old["NAME"];
        if (row.id == selectedId){
            row.state={};
            row.state.selected = true;
        }
        keyNodes[row.id] = row;

        if (parentKeyNodes[row.parentId]) {
            parentKeyNodes[row.parentId].push(row);
        }
        else {
            parentKeyNodes[row.parentId] = [row];
        }
        var children = parentKeyNodes[row.id];
        if (children) {
            row.nodes = children;
        }
        var pNode = keyNodes[row.parentId];
        if (pNode) {
            if (!pNode.nodes)
                pNode.nodes = [];
            pNode.nodes.push(row);
        }
    }
    return parentKeyNodes[undefined];
}


function convertDeptTreeData(rows, selectedId) {
    if(!rows)
        return [];
    if (!selectedId)
        selectedId = '';

    var keyNodes = {}, parentKeyNodes = {};
    for (var i = 0; i < rows.length; i++) {
        var row_old = rows[i];
        var row = {};
        row.id = row_old["FID"];
        row.parentId = row_old["PARENT_ID"];
        row.text = row_old["NAME"];
        if (row.id == selectedId){
            row.state={};
            row.state.selected = true;
        }

        keyNodes[row.id] = row;

        if (parentKeyNodes[row.parentId]) {
            parentKeyNodes[row.parentId].push(row);
        }
        else {
            parentKeyNodes[row.parentId] = [row];
        }

        var children = parentKeyNodes[row.id];
        if (children) {
            row.nodes = children;
        }

        var pNode = keyNodes[row.parentId];
        if (pNode) {
            if (!pNode.nodes)
                pNode.nodes = [];
            pNode.nodes.push(row);
        }
    }
    return parentKeyNodes[undefined];
}