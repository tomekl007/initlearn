package com.stormpath.tutorial.utils;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;
import org.junit.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.times;

public class AccountUtilsTest {

    @Test
    public void shouldAddCustomListFieldToAccountIfPreviouslyWasNull() {
        //given
        String fieldName = "f1";
        Account account = Mockito.mock(Account.class);
        List<String> values = Arrays.asList("a","b");
        Map<String, Object> customData = new LinkedHashMap<>();
        
        //when
        AccountUtils.addCustomListFieldToAccount(account, fieldName, values, customData);
        
        //then
        List<String> strings = (List<String>) customData.get(fieldName);
        Mockito.verify(account, times(1)).save();
        assertThat(strings).isEqualTo(values);
    }

    @Test
    public void shouldAddCustomListFieldToAccountIfPreviouslyWasNotNull() {
        //given
        String fieldName = "f1";
        Account account = Mockito.mock(Account.class);
        List<String> values = Arrays.asList("a","b");
        Map<String, Object> customData = new LinkedHashMap<>();
        customData.put(fieldName, Arrays.asList("x"));

        //when
        AccountUtils.addCustomListFieldToAccount(account, fieldName, values, customData);

        //then
        List<String> strings = (List<String>) customData.get(fieldName);
        Mockito.verify(account, times(1)).save();
        assertThat(strings).isEqualTo(Arrays.asList("x", "a", "b"));
    }

}