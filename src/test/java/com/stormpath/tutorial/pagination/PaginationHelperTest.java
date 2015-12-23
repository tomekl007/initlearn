package com.stormpath.tutorial.pagination;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaginationHelperTest {
    
    @Test
    public void shouldGetProperOffsetForZeroPage(){
        int offsetForPageAndSize = PaginationHelper.getOffsetForPageAndSize(0, 50);
        
        assertThat(offsetForPageAndSize).isEqualTo(0);
    }

    @Test
    public void shouldGetProperOffsetForFirstPage(){
        int offsetForPageAndSize = PaginationHelper.getOffsetForPageAndSize(1, 50);

        assertThat(offsetForPageAndSize).isEqualTo(50);
    }

}