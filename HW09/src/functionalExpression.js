/**
 * Created by Maxim.
 */

"use strict"
var consts = {
    "pi": Math.PI,
    "e": Math.E
}

var vars = {
    "x": 0,
    "y": 1,
    "z": 2,
};

var operation = function (action) {
    return function () {
        var op = arguments;
        return function () {
            var res = [];
            for (var i = 0; i < op.length; i++) {
                res.push(op[i].apply(null, arguments));
            }
            return action.apply(null, res);
        }
    }
}

var cnst = function (value) {
    return function () {
        return value;
    };
};

for (var x in consts) {
    this[x] = cnst(consts[x]);
}

var variable = function (name) {
    return function () {
        return arguments[vars[name]];
    }
}

for (var temp in vars) {
    this[temp] = variable(temp);
}

var add = operation(function (a, b) {
    return a + b;
});

var subtract = operation(function (a, b) {
    return a - b;
});

var multiply = operation(function (a, b) {
    return a * b;
});

var divide = operation(function (a, b) {
    return a / b;
});

var negate = operation(function (a) {
    return -a;
});

var min3 = operation(function () {
    return Math.min.apply(null, arguments);
});

var max5 = operation(function () {
    return Math.max.apply(null, arguments)
});

var parse = function (expr) {
    var tokens = expr.split(" ").filter(function (t) { return t.length > 0; });

    var operations = {
        "+": add,
        "-": subtract,
        "*": multiply,
        "/": divide,
        "negate": negate,
        "max5": max5,
        "min3": min3,
    };
    var args_cnt = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "min3": 3,
        "max5": 5
    };
    var st = [];
    for (var i = 0; i < tokens.length; i++) {
        var token = tokens[i];
        if(token in consts) {
            st.push(cnst(consts[token]));
        } else
        if(token in vars) {
            st.push(variable(token));
        } else
        if(token in operations) {
            var args = [];
            for (var j  = 0 ; j < args_cnt[token] ; ++j) {
                args.push(st.pop());
            }
            args.reverse();
            st.push(operations[token].apply(null, args));
        } else
        st.push(cnst(parseInt(token)));
    }
    return st.pop();
}
