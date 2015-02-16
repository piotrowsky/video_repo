package pl.edu.agh.video_repo.app;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import pl.edu.agh.video_repo.model.*;
import pl.edu.agh.video_repo.resources.HelloWorldResource;

public class VideoRepositoryApplication extends Application<VideoRepositoryConfiguration> {

    public static void main(String[] args) throws Exception {
        new VideoRepositoryApplication().run(args);
    }

    private final HibernateBundle<VideoRepositoryConfiguration> hibernateBundle
            = new HibernateBundle<VideoRepositoryConfiguration>(
                    MapProperty.class,
                    User.class,
                    Data.class,
                    PropertiesOptions.class,
                    DataSet.class,
                    Relation.class,
                    Sequence.class) {
                        @Override
                        public DataSourceFactory getDataSourceFactory(VideoRepositoryConfiguration configuration) {
                            return configuration.getDataSourceFactory();
                        }
                    };

            @Override
            public String getName() {
                return "hello-world";
            }

            @Override
            public void initialize(Bootstrap<VideoRepositoryConfiguration> bootstrap) {
                bootstrap.addBundle(hibernateBundle);
            }

            @Override
            public void run(VideoRepositoryConfiguration configuration,
                    Environment environment) throws ClassNotFoundException {
                environment.jersey().register(new HelloWorldResource());
            }
}
