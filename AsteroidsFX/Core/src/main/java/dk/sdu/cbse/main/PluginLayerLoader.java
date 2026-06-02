package dk.sdu.cbse.main;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

public final class PluginLayerLoader {

    private PluginLayerLoader() {
    }

    public static ModuleLayer createPluginLayer() {
        Path pluginPath = Paths.get("plugins");

        if (!Files.exists(pluginPath)) {
            throw new IllegalStateException("plugins folder does not exist: " + pluginPath.toAbsolutePath());
        }

        ModuleFinder pluginFinder = ModuleFinder.of(pluginPath);

        Set<String> pluginModuleNames = pluginFinder.findAll()
                .stream()
                .map(ModuleReference::descriptor)
                .map(ModuleDescriptor::name)
                .collect(Collectors.toSet());

        System.out.println("Plugin modules found: " + pluginModuleNames);

        ModuleLayer parentLayer = ModuleLayer.boot();

        Configuration pluginConfiguration = parentLayer.configuration()
                .resolveAndBind(
                        pluginFinder,
                        ModuleFinder.of(),
                        pluginModuleNames
                );

        return parentLayer.defineModulesWithOneLoader(
                pluginConfiguration,
                ClassLoader.getSystemClassLoader()
        );
    }
}