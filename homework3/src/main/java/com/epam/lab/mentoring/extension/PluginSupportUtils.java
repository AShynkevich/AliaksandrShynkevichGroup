package com.epam.lab.mentoring.extension;

import com.epam.lab.mentoring.ApplicationConstants;
import com.epam.lab.mentoring.api.IPluggable;

import java.time.LocalDateTime;

public final class PluginSupportUtils {

    public static Plugin createPlugin(IPluggable pluggable, LocalDateTime timestamp) {
        Plugin plugin = new Plugin();
        plugin.setId(pluggable.getClass().getName());
        plugin.setVersion(ApplicationConstants.INITIAL_PLUGIN_VERSION);
        plugin.setTimestamp(timestamp);
        plugin.setClazz(pluggable.getClass());
        return plugin;
    }

    public static Plugin updatePlugin(Plugin plugin, IPluggable pluggable, LocalDateTime timestamp) {
        plugin.setClazz(pluggable.getClass());
        plugin.setVersion(plugin.getVersion() + 1);
        plugin.setTimestamp(timestamp);
        return plugin;
    }
}
