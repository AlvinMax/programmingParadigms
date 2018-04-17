package expression;

public class Max extends BinaryOperator implements TripleExpression {
    public Max(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    protected int apply(int a, int b) throws Exception {
        return a <= b ? b : a;
    }
}