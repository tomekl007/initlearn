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

@Controller
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
    @Autowired
    private RecordRepository repository;

    @Autowired
    AdminService adminService;

    @RequestMapping("/")
    String home() {
        return "home";
    }

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
        Record a = new Record("A");
        System.out.println("should insert a : " + a);
        repository.save(a);
        List<Record> records = repository.findAll();
        logger.info("records : --" +records);
        model.addAttribute("records", records);
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