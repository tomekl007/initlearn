package com.stormpath.tutorial.pagination;

/**
 * Created by tomasz.lelek on 23/12/15.
 */
public class PaginationHelper {
    public static int getOffsetForPageAndSize(Integer page, Integer size) {
        return page * size;
    }

}