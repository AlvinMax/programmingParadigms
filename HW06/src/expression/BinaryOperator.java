package expression;

abstract public class BinaryOperator implements TripleExpression {
    protected TripleExpression firstOp;
    protected TripleExpression secondOp;
    
    BinaryOperator(TripleExpression first, TripleExpression second) {
        firstOp = first;
        secondOp = second;
    }

    abstract protected int apply(int a, int b);
    public int evaluate(int x, int y, int z) {
        return apply(firstOp.evaluate(x, y, z), secondOp.evaluate(x, y, z));
    }
}