package dk.sdu.cbse.main;

import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.IGamePluginService;
import dk.sdu.cbse.common.IPostEntityProcessorService;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfig {

    @Bean
    public ModuleLayer pluginLayer() {
        return PluginLayerLoader.createPluginLayer();
    }

    @Bean
    public List<IGamePluginService> gamePluginServices(ModuleLayer pluginLayer) {
        List<IGamePluginService> plugins = new ArrayList<>();

        for (IGamePluginService plugin : ServiceLoader.load(pluginLayer, IGamePluginService.class)) {
            plugins.add(plugin);
            System.out.println("Loaded game plugin: " + plugin.getClass().getName());
        }

        System.out.println("Loaded game plugins: " + plugins.size());
        return plugins;
    }

    @Bean
    public List<IEntityProcessorService> entityProcessorServices(ModuleLayer pluginLayer) {
        List<IEntityProcessorService> processors = new ArrayList<>();

        for (IEntityProcessorService processor : ServiceLoader.load(pluginLayer, IEntityProcessorService.class)) {
            processors.add(processor);
            System.out.println("Loaded entity processor: " + processor.getClass().getName());
        }

        System.out.println("Loaded entity processors: " + processors.size());
        return processors;
    }

    @Bean
    public List<IPostEntityProcessorService> postEntityProcessorServices(ModuleLayer pluginLayer) {
        List<IPostEntityProcessorService> processors = new ArrayList<>();

        for (IPostEntityProcessorService processor : ServiceLoader.load(pluginLayer, IPostEntityProcessorService.class)) {
            processors.add(processor);
            System.out.println("Loaded post processor: " + processor.getClass().getName());
        }

        System.out.println("Loaded post processors: " + processors.size());
        return processors;
    }

    @Bean
    public Game game(
            List<IGamePluginService> gamePluginServices,
            List<IEntityProcessorService> entityProcessorServices,
            List<IPostEntityProcessorService> postEntityProcessorServices
    ) {
        return new Game(
                gamePluginServices,
                entityProcessorServices,
                postEntityProcessorServices
        );
    }
}