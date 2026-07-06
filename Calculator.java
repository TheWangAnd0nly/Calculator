import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int windowWidth = 540;
    int windowHeight = 810;

    Color lightGray = new Color(212, 212, 210);
    Color darkGrey = new Color (80, 80, 80);
    Color black = new Color (28, 28, 28);
    Color spaceScape = new Color( 36, 32, 82);

    String[] buttonValues ={
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "x",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√x", "3√x", "x²",
        "x³", "x!", "ln(x)", "e^x",
        "log(x)", "sin(x)", "cos(x)",
        "tan(x)", "Abs", "int", "=" 
    };

    String[] mathSymbols = {"÷", "x", "-", "+", "=", "√x", "3√x", "x²", "x³", "x!", "ln(x)", "e^x", "log(x)", "sin(x)", "cos(x)", "tan(x)", "Abs", "int"};
    String[] topSymbols = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonPanel = new JPanel();

    //for operations such as X+Y, X-Y, X*Y, X/Y
    String X = "0";
    String operator = null;
    String Y = null;

    Calculator(){
        //frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
    
        displayLabel.setBackground(black);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonPanel.setLayout(new GridLayout(8,4));
        buttonPanel.setBackground(black);
        frame.add(buttonPanel);

        for (int i = 0; i < buttonValues.length; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(black));
            if (Arrays.asList(topSymbols).contains(buttonValue)){
                button.setBackground(lightGray);
                button.setForeground(black);
            }
            else if (Arrays.asList(mathSymbols).contains(buttonValue)){
                button.setBackground(spaceScape);
                button.setForeground(Color.white);
            }
            else {
                button.setBackground(darkGrey);
                button.setForeground(Color.white);
            }
            buttonPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();
                    if (Arrays.asList(mathSymbols).contains(buttonValue)){
                        if (buttonValue == "="){
                            if (X != null){
                                double numX = Double.parseDouble((X));
                                Y = displayLabel.getText();
                                double numY = Double.parseDouble(Y);
                                switch (operator) {
                                    case "+":
                                        displayLabel.setText(removeZeroDecimal(numX + numY));
                                        break;

                                    case "-":
                                        displayLabel.setText(removeZeroDecimal(numX - numY));
                                        break;

                                    case "x":
                                        displayLabel.setText(removeZeroDecimal(numX * numY));
                                        break;

                                    case "÷":
                                        displayLabel.setText(removeZeroDecimal(numX / numY));
                                        break;

                                }
                                clearAll();
                            }
                        }
                        else if ("+,-,x,÷".contains(buttonValue)){
                            if (operator == null) {
                                X = displayLabel.getText();
                                displayLabel.setText("0");
                                Y = "0";
                            }
                            operator = buttonValue;
                        }
                        else if ("√x,3√x,x²,x³,x!,log(x,)ln(x),e^x,sin(x),cos(x),tan(x), Abs, int".contains(buttonValue)){
                            if (X != null && operator == null){
                                X = displayLabel.getText();
                                double numX = Double.parseDouble((X));
                                operator = buttonValue;
                                switch (operator){
                                    case "x³":
                                        displayLabel.setText(removeZeroDecimal(Math.pow(numX, 3)));
                                        break;

                                    case "x²":
                                        displayLabel.setText(removeZeroDecimal(Math.pow(numX, 2)));
                                        break;

                                    case "√x":
                                        displayLabel.setText(removeZeroDecimal(Math.sqrt(numX)));
                                        break;

                                    case "∛x":
                                        displayLabel.setText(removeZeroDecimal(Math.cbrt(numX)));
                                        break;
                                    
                                    case "!":
                                        int factorial = 1;
                                        for (int i = 0; i < (int) numX; i++){
                                            factorial *= (i+1);
                                        }
                                        displayLabel.setText(removeZeroDecimal(factorial));
                                        break;

                                    case "log":
                                        displayLabel.setText(removeZeroDecimal(Math.log10(numX)));
                                        break;

                                    case "ln":
                                        displayLabel.setText(removeZeroDecimal(Math.log(numX)));
                                        break;

                                    case "e^x":
                                        displayLabel.setText(removeZeroDecimal(Math.exp(numX)));
                                        break;

                                    case "sin(x)":
                                        displayLabel.setText(removeZeroDecimal(Math.sin(numX)));
                                        break;

                                    case "cos(x)":
                                        displayLabel.setText(removeZeroDecimal(Math.cos(numX)));
                                        break;

                                    case "tan(x)":
                                        displayLabel.setText(removeZeroDecimal(Math.tan(numX)));
                                        break;

                                    case "Abs":
                                        displayLabel.setText(removeZeroDecimal(Math.abs(numX)));
                                        break;

                                    case "int":
                                        displayLabel.setText(removeZeroDecimal(Math.round(numX)));
                                        break;
                                }
                                clearAll();
                            }
                        }
                    }
                    else if (Arrays.asList(topSymbols).contains(buttonValue)){
                        if (buttonValue == "AC"){
                            clearAll();
                            displayLabel.setText("0");
                        }
                        else if (buttonValue == "+/-"){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                        else if (buttonValue == "%"){
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    }
                    else {
                        if (buttonValue == "."){
                            if (!displayLabel.getText().contains(buttonValue)){
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                        else if ("0123456789". contains(buttonValue)){
                            if (displayLabel.getText() == "0"){
                                displayLabel.setText(buttonValue);
                            }
                            else{
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);
        }
    }

    void clearAll(){
        X = "0";
        operator= null;
        Y = null;
    }

    String removeZeroDecimal (double numDisplay){
        if (numDisplay % 1 == 0){
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }

}
