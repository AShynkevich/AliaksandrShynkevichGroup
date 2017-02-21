package com.epam.lab.mentoring.extension;

import com.epam.lab.mentoring.api.IPluggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Registry for extension modules - plugins.
 */
public enum ExtensionRegistry {
    REGISTRY;

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionRegistry.class);

    // key is a class name
    private Map<String, Plugin> extensions = new HashMap<>();

    public void registerPlugin(Plugin plugin) {
        extensions.put(plugin.getId(), plugin);
    }

    public Plugin getPlugin(String id) {
        return extensions.get(id);
    }

    public IPluggable getRegisteredClass(String id) {
        Plugin plugin = extensions.get(id);

        if (null == plugin) {
            LOGGER.debug("No class with id [{}] found in the registry!", id);
            return null;
        } else {
            IPluggable instance = null;
            try {
                instance = plugin.getClazz().newInstance();
            } catch (Exception exc) {
                LOGGER.error("Failed to create instance of class with id [{]].", id, exc);
            }
            return instance;
        }
    }
}
