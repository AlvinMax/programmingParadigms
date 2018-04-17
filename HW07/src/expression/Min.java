package expression;

public class Min extends BinaryOperator implements TripleExpression {
    public Min(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) throws Exception {
        return a <= b ? a : b;
    }
}