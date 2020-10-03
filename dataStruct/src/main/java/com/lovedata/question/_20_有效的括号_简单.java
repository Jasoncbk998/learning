package com.lovedata.question;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @Classname _20_有效的括号_简单
 * @Description TODO
 * @Date 2020/9/29 11:28 上午
 * @Created by jason
 */
public class _20_有效的括号_简单 {
    public static void main(String[] args) {
        boolean valid = isValid("()[]{}");
        System.out.println(valid);

    }

    public static boolean isValid(String s) {
        int n = s.length();
        if (n % 2 == 1) {
            return false;
        }

        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        /**
         * 
         */
        Deque<Character> stack = new LinkedList<Character>();
        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            if (pairs.containsKey(ch)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(ch)) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        return stack.isEmpty();


    }





}
