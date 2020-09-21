package com.lovedata.question;


import java.util.HashMap;

/**
 * ClassName _387_字符串中的第一个唯一字符_简单
 * Description
 * Create by Jason
 * Date 2020/9/21 9:47
 */
public class _387_字符串中的第一个唯一字符_简单 {
    public static void main(String[] args) {
        String s = "aavtvbbccdd";
//        String substring = s.substring(0,1);
//        System.out.println(substring);
        int index = firstUniqChar2(s);
        System.out.println(index);
    }

    /**
     * 不适用数据结构 hashmap
     * 秒
     * @param s
     * @return
     */
    public static int firstUniqChar2(String s) {
        int[] res = new int[128];
        for (int i = 0; i < s.length(); i++) {
            res[s.charAt(i) - 'a']++;
        }
        for (int j = 0; j < s.length(); j++) {
            if (res[s.charAt(j) - 'a'] == 1) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 效率最高的
     * @param s
     * @return
     */
    public static int firstUniqChar3(String s){
        int res = s.length();
        for (char c = 'a'; c <= 'z'; c++) {
            int first = s.indexOf(c);
            res = (first != -1 && first < res && first == s.lastIndexOf(c)) ? first : res;
        }
        return res == s.length() ? -1 : res;
    }

    /**
     * 使用hashmap
     * 索引从0开始
     *
     * @param s
     * @return
     */
    public static int firstUniqChar1(String s) {
        int length = s.length();
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        for (int i = 0; i < length; i++) {
            if (map.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }
}
