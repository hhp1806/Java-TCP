package TCPMath;

import java.util.Arrays;
import java.util.Stack;

public class StringHandler {
    public static int priority(char c) {
        if (c == '-' || c == '+') return 1;
        else if (c== '*' || c == '/') return 2;
        else return 0;
    }

    public static boolean isOperator(char c) {
        char operators[] = {'+', '-', '*', '/', '(', ')'};
        Arrays.sort(operators);
        if (Arrays.binarySearch(operators, c) > -1) return true;
        else return false;
    }

    public static String[] processString(String sMath){
        String s1 = "", elementMath[] = null;
        sMath = sMath.trim();
        sMath = sMath.replaceAll("\\s+"," ");
        for (int i=0; i<sMath.length(); i++){
            char c = sMath.charAt(i);
            if (!StringHandler.isOperator(c))
                s1 = s1 + c;
            else s1 = s1 + " " + c + " ";
        }
        s1 = s1.trim();
        s1 = s1.replaceAll("\\s+"," ");
        elementMath = s1.split(" ");
        return elementMath;
    }

    public static String[] convertToPostfix(String s) {
        Stack<String> st = new Stack<>();
        String s1 = "", arrayString[];
        String[] elementMath = StringHandler.processString(s);
        for (int i=0; i < elementMath.length; i++){
            char c = elementMath[i].charAt(0);
            if (!StringHandler.isOperator(c))
                s1 = s1 + " " + elementMath[i];
            else {
                if (c == '(') st.push(elementMath[i]);
                else{
                    if (c == ')') {
                        char c1;
                        do {
                            c1 = st.peek().charAt(0);
                            if (c1 != '(') s1 = s1 + " " + st.peek();
                            st.pop();
                        } while (c1 != '(');
                    }
                    else{
                        while (!st.isEmpty() && StringHandler.priority(st.peek().charAt(0)) >= StringHandler.priority(c)){
                            s1 = s1 + " " + st.peek();
                            st.pop();
                        }
                        st.push(elementMath[i]);
                    }
                }
            }
        }
        while (!st.isEmpty()){
            s1 = s1 + " " + st.peek();
            st.pop();
        }
        arrayString = s1.split(" ");
        return arrayString;
    }

    public static double calculate(String s) {
        String[] postfixString = StringHandler.convertToPostfix(s);
        Stack<String> st = new Stack<String>();
        for (int i = 1; i < postfixString.length; i++) {
            char c = postfixString[i].charAt(0);
            if (!StringHandler.isOperator(c)) st.push(postfixString[i]);
            else {
                double num = 0;
                double num1 = Float.parseFloat(st.pop());
                double num2 = Float.parseFloat(st.pop());
                switch(c) {
                    case '+': num = num1 + num2; break;
                    case '-': num = num2 - num1; break;
                    case '*': num = num1 * num2; break;
                    case '/': num = num2 / num1; break;
                    default: break;
                }
                st.push(Double.toString(num));
            }
        }
        return Float.parseFloat(st.pop());
    }
}
