import org.aeonbits.owner.Config;

@Config.Sources("classpath:app.properties")
public interface Configuration extends Config {

    @Key("project.path")
    String path();

    @Key("project.name")
    String project();
}
