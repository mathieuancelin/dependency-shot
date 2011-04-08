package com.sample.osgi.paint.square;

import com.sample.osgi.paint.api.Shape;
import com.sample.osgi.paint.api.ShapeProvider;
import cx.ath.mancel01.dependencyshot.dynamic.config.Publish;
import javax.inject.Singleton;

@Publish
@Singleton
public class SquareShapeProvider implements ShapeProvider {

    @Override
    public Shape getShape() {
        return new Square();
    }

    @Override
    public String getId() {
        return Square.class.getName();
    }

}
