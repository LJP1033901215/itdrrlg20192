package com.itdr.rlg2;

import org.junit.Test;

public class test4 {
    @Test
    public void aa(){
        Integer a,b,c,d;
        for (int i = 0; i <= 9 ; i++) {
            for (int y = 0; y <= 9 ; y++){
                for (int j = 0; j <= 9 ; j++){
                    a = i * 100 + 80 + y;
                    b= a * 6;
                    if (b.toString().equals("1"+j+"04")){
                        System.out.println("结果是:"+i+"8"+y+"*6="+"1"+j+"04");
                    }

                }
            }
        }
    }
}
