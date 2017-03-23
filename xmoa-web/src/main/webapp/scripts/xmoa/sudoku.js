var getListByForm = function () {
    var itemList = {};
    $("#sudoku").find("input").each(function () {
        var name = this.name.replace("name", "");
        var value = null;
        if (this.value != null)
            value = this.value.substring(0, 1);
        else
            return;
        if (name.match(/^[1-9]{2}/g)) {
            if (value.match(/^[1-9]/g))
                itemList[name] = parseInt(value);
        }
    })
    return itemList;
}

var removeValue = function (valList, val) {
    var index = valList.indexOf(val);
    if (index > -1) {
        valList.splice(index, 1);
    }
};
//未完成空格 还能使用的数字
var notUsedNumber = function (itemList, key) {
    var x_key = key.substring(1, 2);
    var y_key = key.substring(0, 1);
    var xList = [];
    for (var x = 1; x <= 9; x++) {
        if (itemList["" + y_key + x] != null && itemList["" + y_key + x] != "") {
            xList.push(itemList["" + y_key + x]);
        }
    }

    var yList = [];
    for (var y = 1; y <= 9; y++) {
        if (itemList["" + y + x_key] != null && itemList["" + y + x_key] != "")
            yList.push(itemList["" + y + x_key]);
    }

    var areaList = [];
    var areaKeyList = areaData[getAreaByKey(x_key, y_key)];
    for (var i = 0; i < areaKeyList.length; i++) {
        if (itemList[areaKeyList[i]] != null && itemList[areaKeyList[i]] != "")
            areaList.push(itemList[areaKeyList[i]]);
    }
    var resultList = [];
    a: for (var i = 1; i <= 9; i++) {
        for (var x = 0; x < xList.length; x++) {
            if (xList[x] == i)
                continue a;
        }
        for (var x = 0; x < yList.length; x++) {
            if (yList[x] == i)
                continue a;
        }
        for (var x = 0; x < areaList.length; x++) {
            if (areaList[x] == i)
                continue a;
        }
        resultList.push(i);
    }
    return resultList;
}


var areaData = {};
var getAreaByKey = function (x, y) {
    var x_area = Math.ceil(x / 3);
    var y_area = Math.ceil(y / 3);
    return x_area + (y_area - 1) * 3;
}
var getAreaData = function () {
    for (var x = 1; x <= 9; x++) {
        for (var y = 1; y <= 9; y++) {
            var area = getAreaByKey(x, y);
            if (areaData[area] == null) {
                areaData[area] = [];
            }
            areaData[area].push("" + y + x);
        }
    }
}
getAreaData();


//检测结果唯一性
var checkResultUnique = function (resultList) {
    //x轴检测
    for (var y = 1; y <= 9; y++) {
        var xList = [];
        for (var x = 1; x <= 9; x++) {
            if (resultList["" + y + x] != null && resultList["" + y + x] != "")
                xList.push(resultList["" + y + x]);
        }
        if (isRepeat(xList)) {
            console.log("第" + y + "条x轴数据重复")
            return false;
        }
    }
    //y轴检测
    for (var x = 1; x <= 9; x++) {
        var yList = [];
        for (var y = 1; y <= 9; y++) {
            if (resultList["" + y + x] != null && resultList["" + y + x] != "")
                yList.push(resultList["" + y + x]);
        }
        if (isRepeat(yList)) {
            console.log("第" + x + "条y轴数据重复")
            return false;
        }

    }

    //域检测
    for (var area = 1; area <= 9; area++) {
        var keyList = areaData[area];
        var valueList = [];
        for (var key in keyList) {
            if (resultList[keyList[key]] != null && resultList[keyList[key]] != "")
                valueList.push(resultList[keyList[key]]);
        }
        if (isRepeat(valueList)) {
            console.log("第" + area + "个域数据重复")
            return false;
        }
    }

    return true;
}
//检查list是否重复
function isRepeat(list) {
    var hash = {};
    for (var i in list) {
        if (hash[list[i]])
            return true;
        hash[list[i]] = true;
    }
    return false;
}
//填入第一类简单的数字  （简单数字定义为  如果该格能填的数字只有一个数字时 ）
function fillSimpleUniqueNumber(resultList, tryList) {
    var flag = true;
    while (flag) {
        flag = false;
        for (var x = 1; x <= 9; x++) {
            for (var y = 1; y <= 9; y++) {
                if (resultList["" + y + x] == null || resultList["" + y + x] == "") {
                    var notUsedList = notUsedNumber(resultList, "" + y + x);
                    if (notUsedList.length == 1) {
                        resultList["" + y + x] = notUsedList[0];
                        if (tryList != null) {
                            var tryDto = tryList[tryList.length - 1];
                            if (tryDto['keys'] == null)
                                tryDto['keys'] = [];
                            tryDto['keys'].push("" + y + x);
                        }
                        if (!flag)
                            flag = true;
                    }
                }
            }
        }
    }

}


//第二类唯一数字（第二类数字定义为  如果该行 列 域 有某个数字只存在一个格子时 ）
function fillSecondUniqueNumber(resultList, tryList) {
    //临时保存 某一个key还能有哪些可用数字的map
    var tempList = {};
    for (var x = 1; x <= 9; x++) {
        for (var y = 1; y <= 9; y++) {
            if (resultList["" + y + x] == null || resultList["" + y + x] == "") {
                tempList["" + y + x] = notUsedNumber(resultList, "" + y + x);
            }
        }
    }

    for (var num = 1; num <= 9; num++) {
        //x轴检测有没有一个数字 只存在于一个位置
        for (var y = 1; y <= 9; y++) {
            var a = 0;
            var b = null;
            for (var x = 1; x <= 9; x++) {
                if (tempList["" + y + x] != null)
                    if (tempList["" + y + x].indexOf(num) >= 0) {
                        a++;
                        b = x;
                    }
            }
            if (a > 2)
                continue;
            if (a == 1) {
                resultList["" + y + b] = num;

                if (tryList != null) {
                    var tryDto = tryList[tryList.length - 1];
                    if (tryDto['keys'] == null)
                        tryDto['keys'] = [];
                    tryDto['keys'].push("" + y + b);
                }
                return true;
            }
        }

        //y轴检测
        for (var x = 1; x <= 9; x++) {
            var a = 0;
            var b = null;
            for (var y = 1; y <= 9; y++) {
                if (tempList["" + y + x] != null)
                    if (tempList["" + y + x].indexOf(num) >= 0) {
                        a++;
                        b = y;
                    }
            }
            if (a > 2)
                continue;
            if (a == 1) {
                resultList["" + b + x] = num;
                if (tryList != null) {
                    var tryDto = tryList[tryList.length - 1];
                    if (tryDto['keys'] == null)
                        tryDto['keys'] = [];
                    tryDto['keys'].push("" + b + x);
                }
                return true;
            }
        }

        //域检测
        for (var area = 1; area <= 9; area++) {
            var keyList = areaData[area];
            var a = 0;
            var b = null;
            for (var key in keyList) {
                if (tempList[keyList[key]] != null)
                    if (tempList[keyList[key]].indexOf(num) >= 0) {
                        a++;
                        b = keyList[key];
                    }

            }
            if (a > 2)
                continue;
            if (a == 1) {
                resultList["" + b] = num;
                if (tryList != null) {
                    var tryDto = tryList[tryList.length - 1];
                    if (tryDto['keys'] == null)
                        tryDto['keys'] = [];
                    tryDto['keys'].push("" + b);
                }
                return true;
            }
        }
    }

    return false;
}

//数据无法继续
function isUnableGoOn(resultList) {
    for (var x = 1; x <= 9; x++) {
        for (var y = 1; y <= 9; y++) {
            if (resultList["" + y + x] == null || resultList["" + y + x] == "") {
                var notUsedList = notUsedNumber(resultList, "" + y + x);
                if (notUsedList.length == 0) {
                    return true;
                }
            }
        }
    }
    return false;
}


function isComplete(resultList) {
    for (var x = 1; x <= 9; x++) {
        for (var y = 1; y <= 9; y++) {
            if (resultList["" + y + x] == null || resultList["" + y + x] == "") {
                return false;
            }
        }
    }
    return true;
}

function getShortUnDo(resultList) {
    var shortest = 9;
    for (var x = 1; x <= 9; x++) {
        for (var y = 1; y <= 9; y++) {
            if (resultList["" + y + x] == null || resultList["" + y + x] == "") {
                var notUsedList = notUsedNumber(resultList, "" + y + x);
                if (notUsedList.length < shortest) {
                    shortest = notUsedList.length;
                }
            }
        }
    }
    return shortest;
}

//根据可以使用的数字数量 得到一个key
var getKey4ShortestUnDoNumber = function (resultList) {
    var shortest = 10;
    var keyNumberMap = {};
    for (var x = 1; x <= 9; x++) {
        for (var y = 1; y <= 9; y++) {
            if (resultList["" + y + x] == null || resultList["" + y + x] == "") {
                var notUsedList = notUsedNumber(resultList, "" + y + x);
                if (notUsedList.length < shortest) {
                    shortest = notUsedList.length;
                    keyNumberMap["" + y + x] = notUsedList.length;
                }
            }
        }
    }

    if (shortest >= 2) {
        for (var key in keyNumberMap) {
            if (keyNumberMap[key] == shortest)
                return key;
        }
    } else {
        if (shortest == 1)
            console.log("最短待填空唯一，存在可以推理数");
        if (shortest == 0)
            console.log("最短待填空为空 数据已无解");

    }
}


var fillUniqueNumber = function (resultList, tryList) {
    fillSimpleUniqueNumber(resultList, tryList);
    while (fillSecondUniqueNumber(resultList, tryList)) {
        fillSimpleUniqueNumber(resultList, tryList);
    }
}


var getNewTryRandom = function (notUsedList, hasTryValue) {
    var newList = null;
    if (hasTryValue != null && hasTryValue.length > 0) {
        newList = [];
        for (var a in notUsedList) {
            if (hasTryValue.indexOf(notUsedList[a]) < 0)
                newList.push(notUsedList[a]);
        }
    } else {
        newList = notUsedList;
    }
    if (newList != null && newList.length > 0)
        return newList[Math.floor(Math.random() * newList.length)]
    else
        return null;
}

var getNewTry = function (notUsedList, hasTryValue) {
    c:  for (var a in notUsedList) {
        for (var b in hasTryValue) {
            if (notUsedList[a] == hasTryValue[b])
                continue c;
        }
        return notUsedList[a];
    }
    return null;
}


//尝试暴力解决
function tryViolentSolution(resultList) {
    //首先执行一遍 把简单的唯一数字填好
    fillUniqueNumber(resultList);
    if (isComplete(resultList)) {
        return "true";
    }
    var tryList = [];
    var flag_a = true;
    while (flag_a) {

        var tryKey = getKey4ShortestUnDoNumber(resultList);
        if (tryKey == null)
            return null;
        var notUsedList = notUsedNumber(resultList, tryKey);
        resultList[tryKey] = getNewTryRandom(notUsedList);
        var tryDto = {};
        tryDto['key'] = tryKey;
        tryDto['hasTryValue'] = [];
        tryDto['hasTryValue'].push(resultList[tryKey]);
        tryDto['tryValueList'] = notUsedList;
        tryDto['keys'] = [];
        tryList.push(tryDto);

        var flag_b = true;
        while (flag_b) {
            fillUniqueNumber(resultList, tryList);
            if (isComplete(resultList)) {
                return tryList;
            }
            if (isUnableGoOn(resultList)) {
                var flag_c = true;
                while (flag_c) {
                    var tryDto = tryList[tryList.length - 1];

                    if (tryDto['keys'] != null)
                        for (var a in tryDto['keys']) {
                            delete resultList['' + tryDto['keys'][a]];
                        }
                    delete resultList['' + tryDto['key']];
                    //   var notUsedList = notUsedNumber(resultList, tryDto['key']);
                    var newTry = getNewTryRandom(tryDto['tryValueList'], tryDto['hasTryValue']);
                    if (newTry == null) {
                        tryList.pop();
                        if (tryList == null || tryList.length == 0) {
                            return null;
                        }
                    } else {
                        resultList['' + tryDto['key']] = newTry;
                        if (tryDto['hasTryValue'] == null)
                            tryDto['hasTryValue'] = [];
                        tryDto['hasTryValue'].push(newTry);
                        tryDto['keys'] = [];
                        flag_c = false;
                    }
                }
            } else
                flag_b = false;
        }
    }
    return null;
}

var calculateResult = function () {
    var resultList = getListByForm();

    var tryList = tryViolentSolution(resultList);

    if (tryList == null) {
        console.log("数独无解");
        return;
    } else
        console.log(tryList);
    if (checkResultUnique(resultList))
        fillIn(resultList);
}


var fillIn = function (resultList, color) {
    $("#sudoku").find("input").each(function () {
        var name = this.name.replace("name", "");
        if (resultList[name] != null) {
            this.value = resultList[name];
            if (color) {
                $(this).addClass("original");
            }
        }
    });
}

var fillInByOne = function (key, value) {
    $("#sudoku").find("input[name='name" + key + "']")[0].value = value;

}

var clearData = function () {
    $("#sudoku").find("input").each(function () {
        this.value = '';
        $(this).removeClass("original");
    });
}
var markData = function () {
    $("#sudoku").find("input").each(function () {
        if (this.value != '')
            $(this).addClass("original");
    });
}


var clearDataNotOriginal = function () {
    $("#sudoku").find("input:not('.original')").each(function () {
        this.value = '';
    });
}

var generateSudoku = function () {
    clearData();
    var resultList = {};
    tryViolentSolution(resultList);
    var resultListCopy;
    var count = 0;
    while (true) {
        resultListCopy = {};
        for (var key in resultList) {
            resultListCopy[key] = resultList[key];
        }
        var keyList = [];
        for (var key in resultListCopy) {
            if (typeof (resultListCopy[key]) != 'function') {
                keyList.push(key);
            }
        }
        var deleteNumbers = 56;

        for (var i = 0; i < deleteNumbers; i++) {
            var key = keyList[Math.floor(Math.random() * keyList.length)];
            removeValue(keyList, key);
            delete resultListCopy[key];
        }


        var resultListCopy2 = {};
        for (var key in resultListCopy) {
            resultListCopy2[key] = resultListCopy[key];
        }
        if (checkAnswerUnique(resultListCopy2))
            break;
        count++;
        if (count > 100)
            break
    }
    fillIn(resultListCopy, "red");
}

var checkAnswerUniqueButton = function () {
    var resultList = getListByForm();
    if (checkAnswerUnique(resultList))
        console.log("答案唯一解");
    else
        console.log("答案不唯一");
}


var checkAnswerUnique = function (resultList) {

    var resultListCopy = {};
    for (var key in resultList) {
        resultListCopy[key] = resultList[key];
    }


    var tryList = tryViolentSolution(resultList);
    if (tryList == null) {
        //此数独无解
        return false;
    }
    if (typeof (tryList) == 'string') {
        //此数独为简单数独
        console.log("有唯一解：简单数独");
        return true;
    }

    if (tryList.length <= 0) {
        //此数独无解
        return false;
    }
    console.log(tryList);
    c:   for (var i in tryList) {
        var tryDto = tryList[i];
        a:   while (true) {
            var newTry = getNewTry(tryDto["tryValueList"], tryDto["hasTryValue"]);
            if (newTry == null)
                break a;

            var resultListCopyTry = {};
            for (var key in resultListCopy) {
                resultListCopyTry[key] = resultListCopy[key];
            }
            resultListCopyTry[tryDto["key"]] = newTry;
            console.log("尝试唯一解key:" + tryDto['key'] + "=" + resultListCopyTry[tryDto['key']]);
            tryDto["hasTryValue"].push(newTry);


            if (tryViolentSolution(resultListCopyTry)) {
                return false;
            }
        }
    }
    return true;
}