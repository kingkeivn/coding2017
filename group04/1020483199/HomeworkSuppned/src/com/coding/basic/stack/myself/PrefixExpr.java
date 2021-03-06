package com.coding.basic.stack.myself;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.coding.basic.stack.myself.Token;

public class PrefixExpr {
	String expr = null;
	
	public PrefixExpr(String expr) {
		this.expr = expr;
	}

	public float evaluate() {
		TokenParser parser = new TokenParser();
		List<Token> tokens = parser.parse(expr);
		
		Stack<Token> exprStack = new Stack<>();
		Stack<Float> numStack = new Stack<>();
		for(Token token : tokens){
			exprStack.push(token);
		}
		
		while(!exprStack.isEmpty()){
			Token t = exprStack.pop();
			if(t.isNumber()){
				numStack.push(new Float(t.getIntValue()));
			}else{
				Float f1 = numStack.pop();
				Float f2 = numStack.pop();
				numStack.push(calculate(t.toString(),f1,f2));
				
			}
		}
		return numStack.pop().floatValue();
		
	}

	
	public float calculate(String op, float f1, float f2){
		if(op.equals("+")){
			return f1+f2;
		}
		if(op.equals("-")){
			return f1-f2;
		}
		if(op.equals("*")){
			return f1*f2;
		}
		if(op.equals("/")){
			return f1/f2;
		}
		throw new RuntimeException(op + " is not supported");
	}
	
	
}
