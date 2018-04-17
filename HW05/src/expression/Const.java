package expression;

public class Const implements CommonInterface {
	private double value;
    public Const(double val) {
        value = val;
    }

    public int evaluate(int x) {
        return (int) value;
    }

    public double evaluate(double x) {
        return value;
    }
    public int evaluate(int x, int y, int z) {
        return (int) value;
    }
}