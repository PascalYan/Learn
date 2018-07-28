package com.pascalx.algorithm;

/**
 * 输入一个整数，输出去除重复数字后的最大数
 * huawei社招编程题
 * Created by young on 2018/7/28.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Deduplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();

        List<Character> inputs = new ArrayList<>();
        for (int i = 0; i < inputStr.length(); i++) {
            inputs.add(inputStr.charAt(i));
        }

        for (int i = 0; i < inputs.size(); i++) {
            for (int j = 0; j < i; j++) {
//                有重复数字
                if (inputs.get(i).equals(inputs.get(j))) {
//                    去除且大数在高位
                    if (Integer.valueOf(inputs.get(j + 1).toString()) > Integer.valueOf(inputs.get(j).toString())) {
                        inputs.remove(j);
                        i--;
                        break;
                    } else {
                        inputs.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
        for (Character character : inputs) {
            System.out.print(character);
        }
    }
}
