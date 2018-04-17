package expression;

abstract public class BinaryOperator implements CommonInterface {
    protected CommonInterface firstOp;
    protected CommonInterface secondOp;
    
    BinaryOperator(CommonInterface first, CommonInterface second) {
        firstOp = first;
        secondOp = second;
    }

    abstract protected int apply(int a, int b);
    abstract protected double apply(double a, double b);
    
    public int evaluate(int x) {
        return apply(firstOp.evaluate(x), secondOp.evaluate(x));
    }

    public double evaluate(double x) {
        return apply(firstOp.evaluate(x), secondOp.evaluate(x));
    }

    public int evaluate(int x, int y, int z) {
        return apply(firstOp.evaluate(x, y, z), secondOp.evaluate(x, y, z));
    }
}