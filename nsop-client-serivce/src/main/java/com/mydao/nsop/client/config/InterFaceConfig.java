package com.mydao.nsop.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "nsop.interface")
public class InterFaceConfig {

    private String entry;
    private String entry_ex;
    private String entry_deny;
    private String pass_reject;
    private String exit;
    private String exit_ex;
    private String add_black;
    private String del_black;
    private String add_white;
    private String del_white;

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getEntry_ex() {
        return entry_ex;
    }

    public void setEntry_ex(String entry_ex) {
        this.entry_ex = entry_ex;
    }

    public String getEntry_deny() {
        return entry_deny;
    }

    public void setEntry_deny(String entry_deny) {
        this.entry_deny = entry_deny;
    }

    public String getPass_reject() {
        return pass_reject;
    }

    public void setPass_reject(String pass_reject) {
        this.pass_reject = pass_reject;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public String getExit_ex() {
        return exit_ex;
    }

    public void setExit_ex(String exit_ex) {
        this.exit_ex = exit_ex;
    }

    public String getAdd_black() {
        return add_black;
    }

    public void setAdd_black(String add_black) {
        this.add_black = add_black;
    }

    public String getDel_black() {
        return del_black;
    }

    public void setDel_black(String del_black) {
        this.del_black = del_black;
    }

    public String getAdd_white() {
        return add_white;
    }

    public void setAdd_white(String add_white) {
        this.add_white = add_white;
    }

    public String getDel_white() {
        return del_white;
    }

    public void setDel_white(String del_white) {
        this.del_white = del_white;
    }
}
