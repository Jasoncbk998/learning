package com.lovedata.question;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Classname _3_无重复字符的最长子串_中等
 * @Description TODO
 * @Date 2020/10/25 8:55 下午
 * @Created by jason
 */
public class _3_无重复字符的最长子串_中等 {
    /**
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * <p>
     * 输入: "bbbbb"
     * 输出: 1
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1
     *
     * @param args
     */
    public static void main(String[] args) {
        String s = "abcabcbb";
        int i = lengthOfLongestSubstring(s);
        int test = test(s);
        System.out.println(test);
        System.out.println(i);
    }

    /**
     * 以 (a)bcabcbb 开始的最长字符串为  (abc)abcbb
     * 以a(b)cabcbb  开始的最长字符串为 a(bca)bcbb
     */
    public static int lengthOfLongestSubstring(String s) {
        // 哈希集合，记录每个字符是否出现过
        Set<Character> occ = new HashSet<Character>();
        int n = s.length();//字符串长度
        // 右指针，初始值为 -1，相当于我们在字符串的左边界的左侧，还没有开始移动
        int rk = -1, ans = 0;
        for (int i = 0; i < n; ++i) {
            if (i != 0) {
                // 左指针向右移动一格，移除一个字符
                //挪动起始位,每个字符都寻找一遍
                occ.remove(s.charAt(i - 1));
            }
            //开始寻找最长子串长度
            while (rk + 1 < n && !occ.contains(s.charAt(rk + 1))) {
                // 不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            // 第 i 到 rk 个字符是一个极长的无重复字符子串
            //为何rk - i + 1
            /**
             * 假设,abcabd 可以看到最长子串长度是3
             * 那么为何是rk-i+1呢
             * 因为rk代表了子串的长度,i则是代表了从第几个字符开始查找
             * 假设此时,我们从第一个字符a开始查找,找到了c,那么rk=2 i=0  那么是从0开始计位所以+1
             * 假设我们从第二个字符b开始查找,此时i=1,那么我们rk=3,也就是第四个字符a的位置,我们需要rk-i+1,才能得到最长无重复子串
             */
            ans = Math.max(ans, rk - i + 1);
        }
        return ans;
    }

    /**
     * abcabcbb
     *
     * @param s
     * @return
     */
    public static int test(String s) {
        int n = s.length();
        int ans = 0;

        Map<Character, Integer> map = new HashMap<>();
        for (int end = 0, start = 0; end < n; end++) {
            //取出字符
            char alpha = s.charAt(end);
            //判断map中有此字符
            if (map.containsKey(alpha)) {
                start = Math.max(map.get(alpha), start);
            }
            ans = Math.max(ans, end - start + 1);
            map.put(s.charAt(end), end + 1);
        }
        return ans;

    }

}
