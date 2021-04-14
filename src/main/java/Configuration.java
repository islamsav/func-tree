import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({
        "system:properties",
        "classpath:app.properties",
})
public interface Configuration extends Config {

    @Key("project.path")
    String path();

    @Key("project.name")
    @DefaultValue("web-plugin")
    String project();

    @Key("project.map.route")
    @DefaultValue("graph TD;")
    String route();
}
