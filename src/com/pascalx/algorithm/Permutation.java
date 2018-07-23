package com.pascalx.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 求全排列,比较不好写的递归问题
 *
 * @author yanghui10
 * @date 2018/7/23.
 */
public class Permutation {

    /**
     * 问题拆解
     * <p>
     * 如输入：{1,2,3}
     * <p>
     * f(0)={}
     * f(1)={1}
     * f(2)={1,2},{2,1}
     * f(3)={3,1,2},{1,3,2},{1,2,3},{3,2,1},{2,3,1},{2,1,3}
     * ...
     * f(n)=f(n-1)的每一个结果集合，把n插入合适的位置组成新的集合
     *
     * @param strings 输入的集合
     * @return 全排列集合
     */
    public static List<String[]> permutate(String[] strings) {

        List<String[]> result = new ArrayList<>();
//        f(1),f(0)
        if (strings == null || strings.length == 0 || strings.length == 1) {
            result.add(strings);
            return result;
        }

//        f(n),n>1
        return handle(permutate(Arrays.copyOf(strings, strings.length - 1)), strings[strings.length - 1]);
    }


    public static List<String[]> handle(List<String[]> strings, String toAdd) {
        List<String[]> result = new ArrayList<>();
        for (String[] string : strings) {
            for (int i = 0; i < string.length + 1; i++) {//分别插到合适的位置
                String[] newStrings = new String[string.length + 1];
                for (int j = 0, m = 0; j < string.length + 1; j++) {//待插数组下标j，原数组下标m
                    if (i == j) {
                        newStrings[j] = toAdd;
                    } else {
                        newStrings[j] = string[m++];
                    }
                }
                result.add(newStrings);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        List<String[]> result = permutate(new String[]{"1", "2", "3", "4"});
        for (String[] strings : result) {
            System.out.println(Arrays.toString(strings));
        }
    }


}
