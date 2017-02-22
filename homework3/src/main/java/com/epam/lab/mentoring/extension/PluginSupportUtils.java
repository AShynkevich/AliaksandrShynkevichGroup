package com.epam.lab.mentoring.extension;

import com.epam.lab.mentoring.ApplicationConstants;
import com.epam.lab.mentoring.api.IPluggable;

public final class PluginSupportUtils {

    public static Plugin createPlugin(Class<? extends IPluggable> pluggable) {
        Plugin plugin = new Plugin();
        plugin.setId(pluggable.getName());
        plugin.setVersion(ApplicationConstants.INITIAL_PLUGIN_VERSION);
        plugin.setClazz(pluggable);
        return plugin;
    }

    public static Plugin updatePlugin(Plugin plugin, Class<? extends IPluggable> pluggable) {
        plugin.setClazz(pluggable);
        plugin.setVersion(plugin.getVersion() + 1);
        return plugin;
    }
}
