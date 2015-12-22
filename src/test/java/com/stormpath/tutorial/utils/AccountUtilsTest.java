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


    @Test
    public void shouldNotAddDuplicates() {
        //given
        String fieldName = "f1";
        Account account = Mockito.mock(Account.class);
        List<String> values = Arrays.asList("a","b");
        Map<String, Object> customData = new LinkedHashMap<>();
        customData.put(fieldName, Arrays.asList("a", "x"));

        //when
        AccountUtils.addCustomListFieldToAccount(account, fieldName, values, customData);

        //then
        List<String> strings = (List<String>) customData.get(fieldName);
        Mockito.verify(account, times(1)).save();
        assertThat(strings).isEqualTo(Arrays.asList("a", "x", "b"));
    }
    
    @Test
    public void shouldConvertDoubleToString(){
        //given
        String res = AccountUtils.convertDoubleToString(2.5453535);

        assertThat(res).isEqualTo("2.5453535");
    }
    
    @Test
    public void shouldConvertStringToDouble(){
        String a = "2.3425";

        double res = AccountUtils.convertStringToDouble(a);
        assertThat(res).isEqualTo(2.3425);

    }
        

}