package com.stormpath.tutorial.avarage;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AverageCountStrategyTest {
    
    @Test
    public void shouldCountAverageForFirstResult(){
        //when
        double res = AverageCountStrategy.countApproxAverage(0, 5, 1);
        
        //then
        assertThat(res).isEqualTo(5);

    }

    @Test
    public void shouldCountAverageForSecondResult(){
        //when
        double res = AverageCountStrategy.countApproxAverage(3, 5, 2);

        //then
        assertThat(res).isEqualTo(4);

    }


    @Test
    public void shouldCountAverageForLargerResultSet(){
        //when
        double res = AverageCountStrategy.countApproxAverage(3.5, 5, 101);

        //then
        assertThat(res).isEqualTo(3.514851485148515);
        

    }

}