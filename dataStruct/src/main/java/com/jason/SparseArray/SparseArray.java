package com.jason.SparseArray;

/**
 * ClassName SparseArray
 * Description
 * Create by Jason
 * Date 2020/7/17 12:49
 * <p>
 * 优点：
 * <p>
 * 避免了基本数据类型的装箱操作
 * 不需要额外的结构体，单个元素的存储成本更低
 * 数据量小的情况下，随机访问的效率更高
 * 缺点：
 * <p>
 * 插入操作需要复制数组，增删效率降低
 * 数据量巨大时，复制数组成本巨大，gc()成本也巨大
 * 数据量巨大时，查询效率也会明显下降
 */
public class SparseArray {
    public static void main(String[] args) {
        //创建一个原始的二维数组   11*11

        // 0: 表示没有棋子,  1表示黑子    2 表示蓝子


        int[][] chessArr1 = new int[11][11];


        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;
        //输出
        System.out.println("输出原始二维数组");
        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d \t ", data);
            }
            System.out.println();
        }
        /**
         * 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 1 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 2 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 0 	 0 	 2 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         * 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0 	 0
         */

        //二維数组转化成为稀疏数组
        // 1.遍历二维数组,得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }


        //2 创建对应的稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        //给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;
        //遍历二维数组,将非0的值存到sparseArr中
        int count = 0;//记录第几个非0的数据
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                }
            }
        }
        //输出稀疏数组
        System.out.println();
        System.out.println("输出稀疏数组");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1], sparseArr[i][2]);
        }
        /**
         * 11	11	3
         * 1	2	1
         * 2	3	2
         * 4	5	2
         */


        //稀疏数组  -> 二维数组
        /**
         * 首先 读取稀疏数组的第一行,根据第一行的数据,创建原始的二维数组
         *
         * 在读取稀疏数组后几行的数据,并赋值给原始的二维数组即可
         */
        int[][] chessArr2 = new int[sparseArr[0][0]][sparseArr[0][1]];
        for (int i = 1; i < sparseArr.length; i++) {
            chessArr2[sparseArr[i][0]][sparseArr[i][1]] = sparseArr[i][2];
        }
        //输出恢复后的二维数组
        System.out.println();
        System.out.println("恢复后的二维数组");
        for (int[] row : chessArr2) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }
        /**
         * 0	0	0	0	0	0	0	0	0	0	0
         * 0	0	1	0	0	0	0	0	0	0	0
         * 0	0	0	2	0	0	0	0	0	0	0
         * 0	0	0	0	0	0	0	0	0	0	0
         * 0	0	0	0	0	2	0	0	0	0	0
         * 0	0	0	0	0	0	0	0	0	0	0
         * 0	0	0	0	0	0	0	0	0	0	0
         * 0	0	0	0	0	0	0	0	0	0	0
         * 0	0	0	0	0	0	0	0	0	0	0
         * 0	0	0	0	0	0	0	0	0	0	0
         * 0	0	0	0	0	0	0	0	0	0	0
         */


    }
}
