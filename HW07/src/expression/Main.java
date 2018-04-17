package expression;

import expression.parser.ExpressionParser;

/**
 * Created by Maxim on 12.04.2017.
 */
public class Main {
    public static void main (String[] args) {
        ExpressionParser parser = new ExpressionParser();
        try {
            System.out.println(parser.parse("absx").evaluate(-10, 0, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
