package sample;

import java.util.IllegalFormatException;

/**
 * Created by N1kita on 09.03.2017.
 */
public class Model {

    private static final Model model = new Model();
    private Model(){ }

    public static Model getInstance() {
        return model;
    }

    public final Number calculate(Number a, Number b, String operator){

        switch (operator){
            case "+":
                return sum(a,b);
            case "-":
                return subtraction(a,b);
            case "*":
                return multiplication(a,b);
            case "/":
                return division(a,b);

        }
        System.out.println("Undefined operator " + operator);
        return 0;
    }

    public final Number calculate(Number a, String operator){

        switch (operator) {
            case "sqrt":
                return sqrt(a);
            case "sqr":
                return multiplication(a, a);
            case "invert":
                return invert(a);
        }
        System.out.println("Undefined operator " + operator);
        return 0;
    }

    private double sum(Number a, Number b){
        return a.doubleValue() + b.doubleValue();
    }

    private double subtraction(Number a, Number b){
        return a.doubleValue() - b.doubleValue();
    }

    private double multiplication(Number a, Number b) {
        return a.doubleValue() * b.doubleValue();
    }

    private double division(Number a, Number b) {
        if (b.doubleValue() != 0d)
            return a.doubleValue() / b.doubleValue();
        else
            throw new ArithmeticException("Divide by zero is forbidden!");
    }

    private Number sqrt(Number a){

        if(a.doubleValue() < 0) throw new ArithmeticException("Input positive number!");
        if(a.doubleValue() == 0) return 0;

        Number eps = 0.000000001;
        Number left = 0;
        Number right = max (a.doubleValue(), 1.0);
        Number result = 0;

        while (abs(a.doubleValue() - (multiplication(result.doubleValue(),
                result.doubleValue()))).doubleValue() >= eps.doubleValue()){

            result = division(sum(left,right),2.0);

            if(multiplication(result.doubleValue(), result.doubleValue()) > a.doubleValue()){
                right = result;
            } else {
                left = result;
            }
        }

        return result;
    }

    private Number max(Number a, Number b){
        if(a.doubleValue() >= b.doubleValue()){
            return a;
        }
            return b;
    }

    private Number abs(Number a){
        if (a.doubleValue() < 0d){
            return -a.doubleValue();
        }
        return a;
    }

    private Number invert(Number a){
        return -a.doubleValue();
    }
}
