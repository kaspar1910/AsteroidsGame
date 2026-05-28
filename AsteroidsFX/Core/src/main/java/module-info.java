module Core {
    requires Common;
    requires javafx.graphics;
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    exports dk.sdu.cbse.main;

    opens dk.sdu.cbse.main to javafx.graphics, spring.core, spring.beans, spring.context;

    uses dk.sdu.cbse.common.IGamePluginService;
    uses dk.sdu.cbse.common.IEntityProcessorService;
}