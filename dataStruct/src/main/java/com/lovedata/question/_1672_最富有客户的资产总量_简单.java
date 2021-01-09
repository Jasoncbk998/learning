package com.lovedata.question;

/**
 * @Classname _1672_最富有客户的资产总量
 * @Description 给你一个 m x n 的整数网格 accounts ，其中 accounts[i][j] 是第 i​​​​​​​​​​​​ 位客户在第 j 家银行托管的资产数量。返回最富有客户所拥有的 资产总量 。
 * <p>
 * 客户的 资产总量 就是他们在各家银行托管的资产数量之和。最富有客户就是 资产总量 最大的客户。
 * <p>
 * 示例 1：
 * <p>
 * 输入：accounts = [[1,2,3],[3,2,1]]
 * 输出：6
 * 解释：
 * 第 1 位客户的资产总量 = 1 + 2 + 3 = 6
 * 第 2 位客户的资产总量 = 3 + 2 + 1 = 6
 * 两位客户都是最富有的，资产总量都是 6 ，所以返回 6 。
 * @Date 2021/1/9 下午9:25
 * @Created by jason
 */
public class _1672_最富有客户的资产总量_简单 {
    public static void main(String[] args) {
        int[][] arr={{1,2,3},{3,2,1}};
        int i = maximumWealth(arr);
        System.out.println(i);
    }


    public static  int maximumWealth(int[][] accounts) {
        int maxSum = 0;
        for(int i=0;i<accounts.length;i++){
            int sum = 0;
            for(int j=0;j<accounts[i].length;j++){
                sum += accounts[i][j];
                if(maxSum < sum){
                    maxSum = sum;
                }
            }
        }
        return maxSum;
    }

}
