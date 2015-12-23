package com.stormpath.tutorial.user;


import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import com.stormpath.sdk.client.Client;
import com.stormpath.tutorial.model.User;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class UserServiceTest {
    @Test
    public void limitDoNotWork(){
        Client client = Mockito.mock(Client.class);
        AccountList accountList = Mockito.mock(AccountList.class);
        Mockito.when(accountList.getSize()).thenReturn(100);
        Mockito.when(client.getAccounts()).thenReturn(accountList);
        
        //when
//        List<User> allUsers = UserService.getAllUsers(Optional.empty(), Optional.of(0), Optional.of(1), client);

//        System.out.println("allUsers : " + allUsers.size());

    }

}