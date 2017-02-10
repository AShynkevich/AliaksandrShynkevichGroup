package com.epam.lab.mentoring.homework.support.properties;

import java.util.Properties;

public class LoadStatus {
    private boolean success;
    private Properties result;

    LoadStatus(boolean success) {
        this.success = success;
    }

    LoadStatus(boolean success, Properties props) {
        this.success = success;
        this.result = props;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Properties getResult() {
        return result;
    }

    public void setResult(Properties result) {
        this.result = result;
    }
}
