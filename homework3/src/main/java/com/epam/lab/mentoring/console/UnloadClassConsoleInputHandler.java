package com.epam.lab.mentoring.console;

import com.epam.lab.mentoring.classloader.ExtensionLoader;
import com.epam.lab.mentoring.classloader.JarResourceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class UnloadClassConsoleInputHandler extends SafeConsoleInputHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnloadClassConsoleInputHandler.class);

    public UnloadClassConsoleInputHandler(BufferedReader br) {
        super(br);
    }

    @Override
    public void handleInput() throws IOException {
        String classId = this.handleInput("Provide class name to remove:>");
        try {
            Class<?> clz = new ExtensionLoader().loadClass(classId);
            if (null != clz) {
                LOGGER.info("Removing class: [{}].", classId);
                JarResourceRegistry.REGISTRY.unloadClass(classId);
            }
        } catch (ClassNotFoundException exc) {
            LOGGER.info("No class [{}] found!", classId);
        }
    }
}
