package com.study.designPattern;

import com.study.Solution;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TlTest {

    @Test
    public void test1(){
        //Tl tl = new Tl();
        //System.out.println(tl.format( new Date() ));
        Solution solution = new Solution();
        //System.out.println(solution.combine(4,2));
        //System.out.println(solution.binaryBit(new int[]{1,2,3}));
        System.out.println(solution.twoSum(new int[]{2, 7, 11, 15},9));
    }
}
