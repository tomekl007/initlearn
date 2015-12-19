/*
 * Copyright 2015 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stormpath.tutorial.controller;

import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.servlet.account.AccountResolver;
import com.stormpath.tutorial.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletRequest;
import java.util.function.Consumer;

@Controller
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private Client client;

    @Autowired
    AdminService adminService;

    //todo after login quick guide, how to login, and fill extra data about user
    @RequestMapping("/")
    String home(ServletRequest servletRequest) {

        client.getAccounts().forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {
//                adding custom data
//                CustomData customData = account.getCustomData();
//                customData.put("sc", "field");
//                account.save();

                /*add group to account
                Group teachers = StreamSupport.stream(Spliterators
                        .spliteratorUnknownSize(client.getGroups().iterator(), Spliterator.ORDERED), false)
                        .filter(g -> g.getName().equalsIgnoreCase("teachers")).findFirst().get();
                logger.info("teachers : " + teachers);
                account.addGroup(teachers);
                account.save();*/
            }
        });

       /*create account and custom data
       Account account = client.instantiate(Account.class)
                .setUsername("jlpicard")
                .setEmail("capt@enterprise.com")
                .setGivenName("Jean-Luc")
                .setMiddleName("")
                .setSurname("Picard")
                .setPassword("uGhd%a8Kl!")
                .setStatus(AccountStatus.ENABLED);
        
        CustomData customData = account.getCustomData();
        customData.put("rank", "Captain");
        customData.put("birthDate", "2305-07-13");
        customData.put("favoriteDrink", "favoriteDrink");

        String href = "https://api.stormpath.com/v1/directories/1Ly7Mnnag7uuPlSzTyCzPA";
        Directory directory = client.getDataStore().getResource(href, Directory.class);
        directory.createAccount(account);
        account.save();
*/
        return "index";
    }

   /* @RequestMapping
    String home() {
        return "home";
    }*/

    @RequestMapping("/restricted")
    String restricted(ServletRequest servletRequest) {
        if (AccountResolver.INSTANCE.hasAccount(servletRequest)) {
            return "restricted";
        } else {
            return "redirect:/login";
        }

    }

    @RequestMapping("/admin")
    String admin() {
        adminService.ensureAdmin();
        return "admin";
    }
}