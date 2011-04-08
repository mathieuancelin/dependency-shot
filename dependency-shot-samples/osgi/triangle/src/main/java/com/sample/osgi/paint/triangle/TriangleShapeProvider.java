package com.sample.osgi.paint.triangle;

import com.sample.osgi.paint.api.Shape;
import com.sample.osgi.paint.api.ShapeProvider;
import cx.ath.mancel01.dependencyshot.dynamic.config.Publish;
import javax.inject.Singleton;

@Publish
@Singleton
public class TriangleShapeProvider implements ShapeProvider {

    @Override
    public Shape getShape() {
        return new Triangle();
    }

    @Override
    public String getId() {
        return Triangle.class.getName();
    }

}
