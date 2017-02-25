package com.epam.lab.mentoring.api;

/**
 * Common interface for components that can be used as plugins.
 */
public interface IPluggable {
    void executeCommand(String command);
}
