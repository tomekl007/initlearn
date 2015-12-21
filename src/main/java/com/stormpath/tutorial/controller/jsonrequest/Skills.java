package com.stormpath.tutorial.controller.jsonrequest;

import java.util.List;

/**
 * Created by tomasz.lelek on 21/12/15.
 */
public class Skills {
    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> skills;


}
