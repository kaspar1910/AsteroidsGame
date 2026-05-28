package dk.sdu.cbse.main;

import dk.sdu.cbse.common.IEntityProcessorService;
import dk.sdu.cbse.common.IGamePluginService;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleConfig {

    @Bean
    public Game game() {
        return new Game(gamePluginServices(), entityProcessorServices());
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        List<IGamePluginService> plugins = new ArrayList<>();

        for (IGamePluginService plugin : ServiceLoader.load(IGamePluginService.class)) {
            plugins.add(plugin);
        }

        System.out.println("Loaded game plugins: " + plugins.size());

        return plugins;
    }

    @Bean
    public List<IEntityProcessorService> entityProcessorServices() {
        List<IEntityProcessorService> processors = new ArrayList<>();

        for (IEntityProcessorService processor : ServiceLoader.load(IEntityProcessorService.class)) {
            processors.add(processor);
        }

        System.out.println("Loaded entity processors: " + processors.size());

        return processors;
    }
}