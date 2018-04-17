package expression;

public class Add extends BinaryOperator implements CommonInterface {
    public Add(CommonInterface first, CommonInterface second) {
        super(first, second);
    }

    protected int apply(int a, int b) {
        return a + b;
    }
    protected double apply(double a, double b) {
        return a + b;
    }
}