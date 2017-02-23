package com.epam.lab.mentoring.extension;

import com.epam.lab.mentoring.api.IPluggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Registry for extension modules - plugins.
 */
    public enum ExtensionRegistry {
        REGISTRY;

        private static final Logger LOGGER = LoggerFactory.getLogger(ExtensionRegistry.class);

        // key is a class name
        private Map<String, Plugin> extensions = new HashMap<>();

        public synchronized void registerPlugin(Plugin plugin) {
            LOGGER.info("Register plugin: [{}].", plugin);
            extensions.put(plugin.getId(), plugin);
        }

        public synchronized Plugin getPlugin(String id) {
            return extensions.get(id);
        }

        public synchronized List<String> listAvailablePlugins() {
            return extensions.values().stream()
                    .map(Plugin::getId)
                    .collect(Collectors.toList());
        }

        public synchronized IPluggable getRegisteredClass(String id) {
            Plugin plugin = extensions.get(id);

            if (null == plugin) {
                LOGGER.debug("No class with id [{}] found in the registry!", id);
                return null;
            } else {
                IPluggable instance = null;
                try {
                    instance = (IPluggable) Class.forName(plugin.getId()).newInstance(); // should exist!
                } catch (Exception exc) {
                    LOGGER.error("Failed to create instance of class with id [{]].", id, exc);
                }
                return instance;
            }
        }
    }
