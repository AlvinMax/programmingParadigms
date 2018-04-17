package expression;

import expression.parser.ExpressionParser;

/**
 * Created by Maxim on 12.04.2017.
 */
public class Main {
    public static void main (String[] args) {
        ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse("(1 << x)").evaluate(2, 0, 0));
    }
}
