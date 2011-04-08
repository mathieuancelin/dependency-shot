package com.sample.osgi.paint.triangle;

import com.sample.osgi.paint.api.ShapeProvider;
import cx.ath.mancel01.dependencyshot.dynamic.config.DynamicBinder;

/**
 *
 * @author mathieuancelin
 */
public class TriangleBinder extends DynamicBinder {

    @Override
    public void configure() {
        bind(ShapeProvider.class).to(TriangleShapeProvider.class);
    }
}
