package com.epam.lab.mentoring.extension;

import com.epam.lab.mentoring.ApplicationConstants;
import com.epam.lab.mentoring.api.IPluggable;

public final class PluginSupportUtils {

    public static Plugin createPlugin(String id) {
        Plugin plugin = new Plugin();
        plugin.setId(id);
        plugin.setVersion(ApplicationConstants.INITIAL_PLUGIN_VERSION);
        return plugin;
    }

    public static Plugin updatePlugin(Plugin plugin) {
        plugin.setVersion(plugin.getVersion() + 1);
        return plugin;
    }
}
