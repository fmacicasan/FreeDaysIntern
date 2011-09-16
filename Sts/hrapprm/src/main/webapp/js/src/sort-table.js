function getCellVal(cell) {
    var out = '';
    for (var i = 0; i < cell.childNodes.length; i++)
    {
        var obj = cell.childNodes[i];
        switch(obj.nodeType)
        {
            case 1:
                out += getCellVal(obj);
                break;
            case 3:
                out += obj.nodeValue;
                break;
        }
    }
    return out;
}

function lItem(value) {
    this.value = value;
}

function compItems(f, s) {
	var ff = f.value;
	var ss = s.value;

	if (ff > ss) return 1;
    else if (ff == ss) return 0;
    return -1;
}

/**
 * @param row, i (colNumber), valueType
 */
function getVal(row, i, valueType) {
    var obj = new lItem(0);
    var v = getCellVal(row.cells[i]);
    switch(valueType) 
    {
        case 'text':
        {
        	v = v.toLowerCase();
            break;
        }
        case 'number':
        {
            v = v.replace(/,/g, '');
            v = parseInt(v);
            if (isNaN(v)) v = 0;
            break;
        }
        case 'date':
        {
            // tre de prelucrat pt diferite formate de date
            var re = /([0-9]+)\/([0-9]+)\/([0-9]+) (([0-9]+):([0-9]+))?/;
            var a = v.match(re);
            if (a.length > 4)
            {
                v = new Date(a[3], a[1] - 1, a[2], a[5], a[6], 0);
            }
            else if (a.length == 4)
            {
                v = new Date(a[3], a[1] - 1, a[2]);
            }
            else v = 0;
            break;
        }
    }
    
    obj.value = v;
    return obj;
}

var inv = new Array(4);
for (var i = 0; i < 4; i++) inv[i] = 0;
var col = 0;

function sortIt(number,valueType,sortTable) {
    var t = document.getElementById(sortTable);
    if (!t) return;
    var body = t.tBodies[0];

    var invert = (col == number) ? (!inv[col]) : 0;
    if (col >= 0)
		unMarkCol(t.tHead.rows[0].cells[col]);
    col = number;
    inv[col] = invert;

    var j, start, end;
    var v, w;
    var count = body.rows.length;
    for (var i = 1; i < count; i++) {
        v = getVal(body.rows[i], col, valueType);

        start = 0;
        end = i - 1;
        for (; end > start + 1; ) {
            j = start + (end - start) / 2; // does it cast to int ?
            j = parseInt(j); // just in case
            w = getVal(body.rows[j], col, valueType);
            w = (invert ? (-1) : 1) * compItems(v, w);
            if (w < 0) end = j;
            else start = j;  
        }

        w = getVal(body.rows[start], col, valueType);
        w = (invert ? (-1) : 1) * compItems(v, w);
        if (w < 0)
        {
			body.insertBefore(body.rows[i], body.rows[start]);
            continue;
        }

        w = getVal(body.rows[end], col);
        w = (invert ? (-1) : 1) * compItems(v, w);
        if (w < 0) body.insertBefore(body.rows[i], body.rows[end]);
    }

    markOddEvenRows(sortTable, 'odd', 'even');
    markCol(t.tHead.rows[0].cells[col], invert);
}


/**
 * markOddEvenRows
 * @param sortTable
 * @param oddClass, evenClass
 */
function markOddEvenRows(sortTable, oddClass, evenClass) {
	var tbl = document.getElementById(sortTable);
	var tblBody = tbl.tBodies[0];
	var count = tblBody.rows.length;
    for (var i = 0; i < count; i++) {
        tblBody.rows[i].className = (i % 2) ? evenClass : oddClass;
    }
}

function mOut(cell) {
    cell.className = "header";
}
function mHover(cell) {
    cell.className = "headerHover";
}

var img = document.createElement('img');
img.src = 'images/sort_asc.gif';
img.width = 13;
img.height = 5;

var imgI = document.createElement('img');
imgI.src = 'images/sort_desc.gif';
imgI.width = 13;
imgI.height = 5;

function markCol(cell, inv) {
    if (cell.firstChild) {
        if (cell.firstChild.tagName != 'IMG')
            cell.insertBefore((inv ? imgI : img), cell.firstChild);
    }
    else {
        cell.appendChild((inv ? imgI : img));
    }
}

function unMarkCol(cell) {
    if (cell.firstChild && (cell.firstChild.tagName == 'IMG'))
        cell.removeChild(cell.firstChild);
}

function markFirstCol(sortTable) {
    var t = document.getElementById(sortTable);
    if (!t) return;
    markCol(t.tHead.rows[0].cells[0], 0);
}
