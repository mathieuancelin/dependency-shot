package com.sample.osgi.paint;

import com.sample.osgi.paint.api.ShapeProvider;
import com.sample.osgi.paint.circle.CircleShapeProvider;
import com.sample.osgi.paint.gui.PaintFrame;
import cx.ath.mancel01.dependencyshot.dynamic.config.DynamicBinder;

/**
 *
 * @author mathieuancelin
 */
public class AppBinder extends DynamicBinder {

    @Override
    public void configure() {
        bind(ShapeProvider.class).to(CircleShapeProvider.class);
        registerEventListener(App.class);
        registerEventListener(PaintFrame.class);
    }
}
