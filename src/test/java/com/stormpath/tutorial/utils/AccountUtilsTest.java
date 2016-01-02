package com.stormpath.tutorial.utils;

import com.stormpath.sdk.account.Account;
import com.stormpath.tutorial.model.User;
import com.stormpath.tutorial.model.UserBuilder;
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

    @Test
    public void shouldSortUsersHourRate(){
        //given
        List<User> users = Arrays.asList(new UserBuilder().setHourRate(2).createUser(), new UserBuilder().setHourRate(1).createUser());

        //when
        AccountUtils.sortUsersBy(users, AccountUtils.HOUR_RATE_FIELD, 1);

        //then
        assertThat(users.get(0).hourRate).isEqualTo(1);
        assertThat(users.get(1).hourRate).isEqualTo(2);
    }


    @Test
    public void shouldSortUsersWhenValueIsNullHourRate(){
        //given
        List<User> users = Arrays.asList(new UserBuilder().setHourRate(1).createUser(), new UserBuilder().createUser());

        //when
        AccountUtils.sortUsersBy(users, AccountUtils.HOUR_RATE_FIELD, 1);

        //then
        assertThat(users.get(0).hourRate).isEqualTo(null);
        assertThat(users.get(1).hourRate).isEqualTo(1);
    }

    @Test
    public void shouldSortUsersAverage(){
        //given
        List<User> users = Arrays.asList(new UserBuilder().setAverage(2.1d).createUser(), new UserBuilder().setAverage(1.3d).createUser());

        //when
        AccountUtils.sortUsersBy(users, AccountUtils.AVERAGE_FIELD, 1);

        //then
        assertThat(users.get(0).average).isEqualTo(1.3d);
        assertThat(users.get(1).average).isEqualTo(2.1d);
    }


    @Test
    public void shouldSortUsersWhenValueIsNullAverage(){
        //given
        List<User> users = Arrays.asList(new UserBuilder().setAverage(1d).createUser(), new UserBuilder().createUser());

        //when
        AccountUtils.sortUsersBy(users, AccountUtils.AVERAGE_FIELD, 1);

        //then
        assertThat(users.get(0).average).isEqualTo(null);
        assertThat(users.get(1).average).isEqualTo(1d);
    }

    @Test
    public void shouldSortUsersWhenValueIsNullAverageReversed(){
        //given
        List<User> users = Arrays.asList(new UserBuilder().setAverage(1d).createUser(), new UserBuilder().createUser());

        //when
        AccountUtils.sortUsersBy(users, AccountUtils.AVERAGE_FIELD, -1);

        //then
        assertThat(users.get(0).average).isEqualTo(1d);
        assertThat(users.get(1).average).isEqualTo(null);
    }

    @Test
    public void shouldSortUsersAverageReversed(){
        //given
        List<User> users = Arrays.asList(new UserBuilder().setAverage(2.1d).createUser(), new UserBuilder().setAverage(1.3d).createUser());

        //when
        AccountUtils.sortUsersBy(users, AccountUtils.AVERAGE_FIELD, -1);

        //then
        assertThat(users.get(0).average).isEqualTo(2.1d);
        assertThat(users.get(1).average).isEqualTo(1.3d);
    }


}