package pl.edu.agh.video_repo.app;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import java.util.Arrays;
import javax.persistence.Entity;
import org.reflections.Reflections;
import pl.edu.agh.video_repo.resources.HelloWorldResource;

public class VideoRepositoryApplication extends Application<VideoRepositoryConfiguration> {

    public static void main(String[] args) throws Exception {
        new VideoRepositoryApplication().run(args);
    }

    private Class[] c = new Reflections("pl.edu.agh.video_repo.model").getTypesAnnotatedWith(Entity.class, true).toArray(new Class[]{});

    @Override
    public String getName() {
        return "video-repository";
    }

    @Override
    public void initialize(Bootstrap<VideoRepositoryConfiguration> bootstrap) {
        bootstrap.addBundle(createHibernateBundle());
    }

    @Override
    public void run(VideoRepositoryConfiguration configuration,
            Environment environment) throws ClassNotFoundException {
        environment.jersey().register(new HelloWorldResource());
    }

    private HibernateBundle<VideoRepositoryConfiguration> createHibernateBundle() {
        Class[] entityClasses = new Reflections("pl.edu.agh.video_repo.model")
                .getTypesAnnotatedWith(Entity.class, true)
                .toArray(new Class[]{});
        
        return new HibernateBundle<VideoRepositoryConfiguration>(
                entityClasses[0],
                Arrays.stream(entityClasses).skip(1).toArray(length -> new Class[length])) {
                    @Override
                    public DataSourceFactory getDataSourceFactory(VideoRepositoryConfiguration configuration) {
                        return configuration.getDataSourceFactory();
                    }
                };
    }

}
