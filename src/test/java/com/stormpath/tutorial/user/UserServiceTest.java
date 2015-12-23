package com.stormpath.tutorial.user;


import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.impl.client.DefaultClient;
import com.stormpath.sdk.impl.ds.InternalDataStore;
import com.stormpath.tutorial.model.User;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserServiceTest {
    @Test
    public void limitDoNotWork(){
        InternalDataStore mock = Mockito.mock(InternalDataStore.class);
        
//        new DefaultClient(mock)
        //when
//        List<User> allUsers = UserService.getAllUsers(Optional.empty(), Optional.of(0), Optional.of(1), client);

//        System.out.println("allUsers : " + allUsers.size());

    }

}