"use strict"

var VARIABLES = {"x": 0, "y": 1, "z": 2};

function New(constructor, args) {
    var temp = Object.create(constructor.prototype);
    constructor.apply(temp, args);
    return temp;
}
var primitive  = {
    simplify: function () {
        return this
    }
};

/*----------------------------CONST----------------------------*/

function Const(x) {
    this.getValue = function () {
        return x;
    }
}
Const.prototype = Object.create(primitive);
Const.prototype.toString = function () {
    return this.getValue().toString();
}
Const.prototype.evaluate = function () {
    return this.getValue();
}
Const.prototype.diff = function (v) {
    return ZERO;
}

var ZERO = new Const(0);
var ONE = new Const(1);
var TWO = new Const(2);

function isZero(a) {
    return (a instanceof Const && a.getValue() === 0);
}
function isOne(a) {
    return (a instanceof Const && a.getValue() === 1);
}

/*----------------------------VARIABLE------------------------------*/

function Variable(s) {
    var ind = VARIABLES[s];
    this.getName = function () {
        return s;
    }
    this.getInd = function () {
        return ind;
    }
}

Variable.prototype = Object.create(primitive);
Variable.prototype.toString = function () {
    return this.getName();
}
Variable.prototype.evaluate = function () {
    return arguments[this.getInd()];
}
Variable.prototype.diff = function (v) {
    return v === this.getName() ? ONE : ZERO;
}

/*-------------------------------------------------------------------------*/

function Operation() {
    var operands = [].slice.call(arguments);
    this.getOperands = function () {
        return operands;
    }
}
Operation.prototype.toString = function () {
    return this.getOperands().join(" ") + " " + this.getSymbol();
}
Operation.prototype.evaluate = function () {
    var args = arguments;
    var res = this.getOperands().map(function(value) {
        return value.evaluate.apply(value, args)
    });
    return this.Action.apply(null, res);
}
Operation.prototype.diff = function (v) {
    var ops = this.getOperands();
    return this.Diff.apply(this, ops.concat(ops.map(function (value) {
        return value.diff(v)
    })));
}
Operation.prototype.simplify = function () {
    var ops = this.getOperands().map(function (item) {
        return item.simplify()
    });
    var f = true;
    ops.forEach(function (value) {
        if (!(value instanceof Const)) { f = false; }
    });
    var res = New(this.constructor, ops);
    if (f) {
        return new Const(res.evaluate());
    }
    if (this.Simplify !== undefined) {
        return this.Simplify.apply(this, ops);
    }
    return res;
}

function DefineOperation(maker, action, symbol, howToDiff, howToSimplify) {
    this.constructor = maker;
    this.Action = action;
    this.getSymbol = function () {
        return symbol;
    }
    this.Diff = howToDiff;
    this.Simplify = howToSimplify;
}
DefineOperation.prototype = Operation.prototype;

function operationFactory(action, symbol, howToDiff, howToSimplify) {
    var result = function () {
        var args = arguments;
        Operation.apply(this, args);
    }
    result.prototype = new DefineOperation(result, action, symbol, howToDiff, howToSimplify);
    return result;
}

var Add = operationFactory(
    function (a, b) {
        return a + b;
    },
    "+",
    function (a, b, da, db) {
        return new Add(da, db);
    },
    function (a, b) {
        if (isZero(a)) {
            return b;
        }
        if (isZero(b)) {
            return a;
        }
        return new Add(a, b);
    }
);

var Subtract = operationFactory(
    function (a, b) {
        return a - b;
    },
    "-",
    function (a, b, da, db) {
        return new Subtract(da, db);
    },
    function (a, b) {
        if (isZero(b)) {
            return a;
        }
        return new Subtract(a, b);
    }
);

var Multiply = operationFactory(
    function (a, b) {
        return a * b;
    },
    "*",
    function (a, b, da, db) {
        return new Add(new Multiply(da, b), new Multiply(a, db));
    },
    function (a, b) {
        if (isZero(a) || isZero(b)) {
            return ZERO;
        }
        if (isOne(a)) {
            return b;
        }
        if (isOne(b)) {
            return a;
        }
        return new Multiply(a, b);
    }
);

var Divide = operationFactory(
    function (a, b) {
        return a / b;
    },
    "/",
    function (a, b, da, db) {
        return new Divide(new Subtract(new Multiply(da, b), new Multiply(a, db)), new Multiply(b, b));
    },
    function (a, b) {
        if (isZero(a)) {
            return ZERO;
        }
        if (isOne(b)) {
            return a;
        }
        return new Divide(a, b);
    }
);

var Negate = operationFactory(
    function (a) {
        return -a;
    },
    "negate",
    function (a, da) {
        return new Negate(da);
    }
);

var Square = operationFactory(
    function (a) {
        return a * a;
    },
    "square",
    function (a, da) {
        return new Multiply(new Multiply(TWO, a), da);
    }
);

var Sqrt = operationFactory(
    function (a) {
        return Math.sqrt(Math.abs(a));
    },
    "sqrt",
    function (a, da) {
        var x = new Multiply(a, da);
        var y = new Multiply(TWO, new Sqrt(new Multiply(new Square(a), a)));
        return new Divide(x, y);
    }
);

var OPERATIONS = {
    "+": Add,
    "-": Subtract,
    "*": Multiply,
    "/": Divide,
    "negate": Negate,
    "sqrt": Sqrt,
    "square": Square,
};
var ARGS_COUNT = {
    "+": 2,
    "-": 2,
    "*": 2,
    "/": 2,
    "negate": 1,
    "sqrt": 1,
    "square": 1,
};

function parse (s) {
    var stack = [];
    var tokens = s.trim().split(/\s+/);
    for (var i = 0; i < tokens.length; i++) {
        if (tokens[i] in VARIABLES) {
            stack.push(new Variable(tokens[i]));
        } else if (tokens[i] in OPERATIONS) {
            var l = ARGS_COUNT[tokens[i]];
            stack.push(New(OPERATIONS[tokens[i]], stack.splice(-l, l)));
        } else {
            stack.push(new Const(parseInt(tokens[i])));
        }
    }
    return stack.pop();
}