package com.pascalx.reflection;

/**
 * @author yanghui10
 * @date 2018/8/16.
 */
public class Goal {

    String attribute;


    //    利用反射对属性赋值
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        Goal goal = Goal.class.newInstance();
        Goal.class.getDeclaredField("attribute").set(goal, "aaaaaa");

        System.out.println(goal.attribute);
    }
}
