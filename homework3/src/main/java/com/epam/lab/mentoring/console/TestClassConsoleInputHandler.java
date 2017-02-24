package com.epam.lab.mentoring.console;

import com.epam.lab.mentoring.api.IPluggable;
import com.epam.lab.mentoring.classloader.ExtensionLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class TestClassConsoleInputHandler extends SafeConsoleInputHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestClassConsoleInputHandler.class);

    public TestClassConsoleInputHandler(BufferedReader br) { super(br); }

    @Override
    public void handleInput() throws IOException {
        String classId = this.handleInput("Provide class name:>");
        try {
            Class<?> clz = new ExtensionLoader().loadClass(classId);
            if (null != clz) {
                IPluggable pluggable = (IPluggable) clz.newInstance();
                pluggable.executeCommand("my command");
            }
        } catch (ClassNotFoundException exc) {
            LOGGER.info("No class [{}] found!", classId);
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.error("Failed to instantiate class [{}].", classId);
        }
    }
}
