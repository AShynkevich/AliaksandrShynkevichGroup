package com.epam.lab.mentoring.watcher.resolver;

import java.nio.file.Path;

public interface IEventResolver {
    void resolve(Path file);
}
