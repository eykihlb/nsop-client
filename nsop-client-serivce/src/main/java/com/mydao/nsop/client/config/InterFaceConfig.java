package com.mydao.nsop.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "nsop.interface")
public class InterFaceConfig {

    private String entry;
    private String entry_ex;
    private String full_quantity_black;
    private String full_quantity_white;
    private String exit;
    private String exit_ex;

    public String getFull_quantity_black() {
        return full_quantity_black;
    }

    public void setFull_quantity_black(String full_quantity_black) {
        this.full_quantity_black = full_quantity_black;
    }

    public String getFull_quantity_white() {
        return full_quantity_white;
    }

    public void setFull_quantity_white(String full_quantity_white) {
        this.full_quantity_white = full_quantity_white;
    }

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
}
