package com.mezzanine.s_expression;

import java.util.ArrayList;

/**
 * Created by alex on 8/8/17.
 */
public class SExpression {
    public String name;
    public ArrayList<String> values;
    public ArrayList<SExpression> children;

    private SExpression(){
        name = "";
        values = new ArrayList<>();
        children = new ArrayList<>();
    }

    /**
     * Creates an empty S-expression to be built to manually.
     */
    public static SExpression empty(){
        return new SExpression();
    }

    /**
     * Creates an SExpression from an S-expression formatted String.
     *
     * @param string a non-null S-expression formatted String, beginning with "(" and ending with ")"
     * @throws SExpressionFormatException when the source String is either null or not formatted correctly
     */
    public static SExpression fromString(String string) throws SExpressionFormatException{
        if(string == null)
            throw new SExpressionFormatException("S-expression source String was null!");

        if(!string.startsWith("(") || !string.endsWith(")"))
            throw new SExpressionFormatException("S-expression source String format incorrect: must be surrounded by \"()\"");

        SExpression sExpression = new SExpression();

        if(string.equals("()")) return sExpression;

        String s = string.substring(1, string.length() - 1);
        int depth = 0;
        StringBuilder name = new StringBuilder();
        StringBuilder child = new StringBuilder();
        StringBuilder value = new StringBuilder();

        boolean nameSet = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c){
                case '(':
                    depth++;
                    child.append(c);
                    break;
                case ')':
                    depth--;
                    child.append(c);
                    if(depth == 0){
                        sExpression.children.add(SExpression.fromString(child.toString()));
                        child = new StringBuilder();
                    }
                    break;
                case ' ':
                    nameSet = true;
                    if(depth > 0){
                        child.append(c);
                    }else if(value.length() > 0){
                        sExpression.values.add(value.toString());
                        value = new StringBuilder();
                    }
                    break;
                default:
                    if(depth > 0){
                        child.append(c);
                    }else if(nameSet){
                        value.append(c);
                    }else{
                        name.append(c);
                    }
                    break;
            }
        }
        sExpression.name = name.toString();

        if(value.length() > 0) sExpression.values.add(value.toString());

        return sExpression;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        String n = name != null ? name : "";
        ArrayList<String> vs = values != null ? values : new ArrayList<>();
        ArrayList<SExpression> cs = children != null ? children : new ArrayList<>();
        builder.append("(");
        builder.append(n);
        for(String value : vs){
            builder.append(" ");
            builder.append(value);
        }
        for(SExpression child : cs){
            builder.append(" ");
            builder.append(child.toString());
        }
        builder.append(")");
        return builder.toString();
    }
}
