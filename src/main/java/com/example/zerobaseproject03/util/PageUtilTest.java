package com.example.zerobaseproject03.util;

public class PageUtilTest {


    public static void main(String[] args) {

        PageUtil pageUtil = new PageUtil(151, 10, 3, "");
        String htmlPager = pageUtil.pager();

        System.out.println(htmlPager);


    }
}