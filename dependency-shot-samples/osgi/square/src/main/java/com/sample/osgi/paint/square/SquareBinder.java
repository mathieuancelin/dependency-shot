package com.sample.osgi.paint.square;

import com.sample.osgi.paint.api.ShapeProvider;
import cx.ath.mancel01.dependencyshot.dynamic.config.DynamicBinder;

/**
 *
 * @author mathieuancelin
 */
public class SquareBinder extends DynamicBinder {

    @Override
    public void configure() {
        bind(ShapeProvider.class).to(SquareShapeProvider.class);
    }
}
