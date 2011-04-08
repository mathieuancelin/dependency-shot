package com.sample.osgi.paint;

import com.sample.osgi.paint.api.ShapeProvider;
import com.sample.osgi.paint.circle.CircleShapeProvider;
import com.sample.osgi.paint.gui.PaintFrame;
import com.sample.osgi.paint.square.SquareShapeProvider;
import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.event.Observes;
import cx.ath.mancel01.dependencyshot.dynamic.event.BundleContainerInitialized;
import cx.ath.mancel01.dependencyshot.dynamic.registry.ServiceRegistry;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class App {

    @Inject PaintFrame frame;

    @PostConstruct
    public void start() {
        frame.start();
    }

    @PreDestroy
    public void stop() {
        frame.stop();
    }

    public void onStartup(@Observes BundleContainerInitialized event) {
        System.out.println("CDI Container for bundle "
                + event.getBundleContext().getBundle() + " started");
    }

    public static void main(String... args) {
        DSInjector injector = DependencyShot.getInjector(new AppBinder());
        injector.fire(new BundleContainerInitialized(null));
        ServiceRegistry registry = injector.getInstance(ServiceRegistry.class);
        registry.registerService(ShapeProvider.class, ShapeProvider.class);
        registry.registerService(ShapeProvider.class, CircleShapeProvider.class);
        registry.registerService(ShapeProvider.class, SquareShapeProvider.class);
    }
}
