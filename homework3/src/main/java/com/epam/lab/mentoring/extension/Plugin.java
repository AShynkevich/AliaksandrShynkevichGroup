package com.epam.lab.mentoring.extension;

import com.epam.lab.mentoring.api.IPluggable;

public class Plugin {
    private String id;
    private Class<? extends IPluggable> clazz;
    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class<? extends IPluggable> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends IPluggable> clazz) {
        this.clazz = clazz;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
