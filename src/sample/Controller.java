package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

//                Честно, я очень хотел запилить также и свое округление (вместо BigDecimal), но мне стало влом,
//                т.к. завтра на первую пару, а уже 4 утра. А возвращаться к этому проэкту я не собираюсь,
//                побаловался и хватит.
//                Best wishes, N1kitA.

public class Controller {

    @FXML
    private Text output;
    @FXML
    private Text fullOutput;

    private Number num1 = 0;
    private Number num2 = 0;

    private boolean start = true;
    private boolean first = true;

    private String fullText = "";
    private String operator = "";

    private Model model = Model.getInstance();


    @FXML
    private void processNum(ActionEvent event){

        if (start){
            fullOutput.setText("");
            output.setText("");
            start = false;
        }

        String value = ((Button)event.getSource()).getText();
        output.setText(output.getText() + value);
    }

    @FXML
    private void processOperator(ActionEvent event){

        if(start) return;

        String value = ((Button)event.getSource()).getText();
        if(!"=".equals(value)){

            if(!operator.isEmpty()) {
                return;
            }

            operator = value;
            num1 = Double.parseDouble(output.getText());
            if(fullText.isEmpty()) {
                fullText += output.getText() + operator;
            } else {
                fullText += operator;
            }
            output.setText("");
            fullOutput.setText(fullText);
        } else {

            if (operator.isEmpty()) {
                return;
            }

            num2 = Double.parseDouble(output.getText());
            if ("/".equals(operator) && num2.doubleValue() == 0d){
                output.setText("Divide by zero is forbidden!");
            } else {

                String result = String.valueOf(new BigDecimal(String.valueOf(model.calculate(num1, num2, operator))).
                        setScale(6,RoundingMode.HALF_UP).doubleValue());
                if(result.endsWith(".0")){
                    output.setText(result.substring(0,result.length()-2));
                } else {
                    output.setText(result);
                }
            }
            fullOutput.setText("");
            fullText = "";
            operator = "";
            start = true;
        }
    }

    @FXML
    private void processFunction(ActionEvent event){
        if (start) return;
        String value = ((Button)event.getSource()).getText();
        Number num;

        switch (value){
            case "sqrt":
                if (fullText.isEmpty()) {
                    fullText = "sqrt(" + output.getText() + ")";
                } else if (operator.isEmpty()) {
                    fullText = "sqrt(" + fullText + ")";
                } else if (first) {
//                    TODO: what if output.getText() is empty???
                    int index = fullText.lastIndexOf(operator);
                    fullText = fullText.substring(0,index+1) + "sqrt(" + output.getText() + ")";
                    first = false;
                } else {
                    int index = fullText.lastIndexOf(operator);
                    fullText = fullText.substring(0,index+1) + "sqrt(" + fullText.substring(index+1, fullText.length()) + ")";
                }
                fullOutput.setText(fullText);
                break;
//            TODO: sqr and invert cases
//            TODO: problem with adding point to fullOutput (fullText??)
//            TODO:  boolean var first = true after " = " and maybe in other special situations
//            case "sqr": fullOutput.setText(fullOutput.getText()
//                    .substring(0,fullOutput.getText().length() - output.getText().length())
//                    + "(" + output.getText() + ")^2");
//                break;

            case "invert":
                break;
        }

        num = Double.parseDouble(output.getText());

        String result = String.valueOf(new BigDecimal(String.valueOf(model.calculate(num, value))).
                setScale(6,RoundingMode.HALF_UP).doubleValue());
        if(result.endsWith(".0")){
            output.setText(result.substring(0,result.length()-2));
        } else {
            output.setText(result);
        }
    }

    @FXML
    private void processPoint(ActionEvent event){
        if(output.getText().isEmpty()){
            output.setText("0.");
            start = false;
        } else if (!output.getText().contains(".") && (start == false)){
            output.setText(output.getText() + ".");
            fullText += ".";
        }
    }

    @FXML
    private void clear(ActionEvent event){
        output.setText("");
        fullOutput.setText("");
        fullText = "";
        start = true;
        operator = "";
    }



}
