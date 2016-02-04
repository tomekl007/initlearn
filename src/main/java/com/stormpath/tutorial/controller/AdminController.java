package com.stormpath.tutorial.controller;

import com.stormpath.tutorial.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by tomasz.lelek on 04/02/16.
 */
@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/admin/invalidateCache")
    public ResponseEntity<String> invalidateCache(){
        boolean res = adminService.invalidateCaches();
        if(res) {
            return new ResponseEntity<>("Successfully invalidate caches", HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
