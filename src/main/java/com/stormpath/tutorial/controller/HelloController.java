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
import com.stormpath.sdk.account.AccountStatus;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.directory.Directory;
import com.stormpath.sdk.group.Group;
import com.stormpath.sdk.group.GroupCriteria;
import com.stormpath.sdk.group.Groups;
import com.stormpath.sdk.impl.account.DefaultAccount;
import com.stormpath.tutorial.db.Record;
import com.stormpath.tutorial.db.RecordRepository;
import com.stormpath.tutorial.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

@Controller
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private RecordRepository repository;

    @Autowired
    private Client client;

    @Autowired
    AdminService adminService;

    @RequestMapping("/")
    String home() {
        
        client.getAccounts().forEach(new Consumer<Account>() {
            @Override
            public void accept(Account account) {
                CustomData customData = account.getCustomData();
                customData.put("sc", "field");
                account.save();
            }
        });

        Account account = client.instantiate(Account.class)
                .setUsername("jlpicard")
                .setEmail("capt@enterprise.com")
                .setGivenName("Jean-Luc")
                .setMiddleName("")
                .setSurname("Picard")
                .setPassword("uGhd%a8Kl!")
                .setStatus(AccountStatus.ENABLED);
        Directory directory = account.getDirectory();
        CustomData customData = account.getCustomData();
        customData.put("rank", "Captain");
        customData.put("birthDate", "2305-07-13");
        customData.put("favoriteDrink", "favoriteDrink");
        
        directory.createAccount(account);
        account.save();

        Group teachers = StreamSupport.stream(Spliterators
                .spliteratorUnknownSize(client.getGroups().iterator(), Spliterator.ORDERED), false)
                .filter(g -> g.getName().equalsIgnoreCase("teachers")).findFirst().get();
        account.addGroup(teachers);
        
        return "home";
    }
    
    @RequestMapping("index")
    String index() {return "index"; }

    @RequestMapping("/restricted")
    String restricted() {
        return "restricted";
    }

    @RequestMapping("/admin")
    String admin() {
        adminService.ensureAdmin();
        return "admin";
    }


    @RequestMapping(method = RequestMethod.GET, path = "/db")
    public String home(ModelMap model) {
        List<Record> records = repository.findAll();
        logger.info("records : --" +records);
        Record a = new Record("A");
        System.out.println("should insert a : " + a);
        repository.save(a);
        List<Record> records2 = repository.findAll();
        logger.info("records : --" +records2);
        model.addAttribute("records", records2);
        model.addAttribute("insertRecord", new Record("B"));
        return "correct";
    }


    @RequestMapping(method = RequestMethod.POST, path = "/db")
    public String insertData(ModelMap model,
                             @ModelAttribute("insertRecord") @Valid Record record,
                             BindingResult result) {
        if (!result.hasErrors()) {
            System.out.println("will save record : " + record);
            repository.save(record);
        }
        return home(model);
    }
}