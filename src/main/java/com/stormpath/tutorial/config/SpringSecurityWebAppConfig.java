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

package com.stormpath.tutorial.config;

import com.paypal.base.util.OAuthSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.stormpath.spring.config.StormpathWebSecurityConfigurer.stormpath;

@EnableWebSecurity
@Configuration
public class SpringSecurityWebAppConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .antMatcher("/users/**").
//                csrf().disable();

        http
                .apply(stormpath()).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", "/login", "/users", "/users/**", "/group/users/*", "users/*/*",
                        "users/**/skills", "/isLoggedIn", "/registerFacebookAccount/**", "/register/**", "/registerAccount")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,  "/login", "/registerFacebookAccount/**", "/register/**", "/registerAccount")
                .permitAll()
                .and()
                .antMatcher("/users/**").csrf().disable()
                .antMatcher("/register/**").csrf().disable()
                .antMatcher("/registerAccount").csrf().disable()
                .antMatcher("/register").csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/templates/**", "/assets/**");
    }
}