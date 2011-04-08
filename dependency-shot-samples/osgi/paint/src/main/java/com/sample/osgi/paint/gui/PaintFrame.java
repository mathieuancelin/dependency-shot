package com.sample.osgi.paint.gui;

import com.sample.osgi.paint.api.Shape;
import com.sample.osgi.paint.api.ShapeProvider;
import cx.ath.mancel01.dependencyshot.api.event.Observes;
import cx.ath.mancel01.dependencyshot.dynamic.event.ServiceArrival;
import cx.ath.mancel01.dependencyshot.dynamic.event.ServiceDeparture;
import cx.ath.mancel01.dependencyshot.dynamic.integration.Services;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;


@Singleton
public class PaintFrame extends JFrame implements MouseListener {

    private static final int BOX = 54;
    private JToolBar toolbar;
    private String selected;
    private JPanel panel;

    @Inject
    private Services<ShapeProvider> registeredProviders;

    @Inject
    private ShapeProvider defaultProvider;

    private ActionListener actionListener = new ShapeActionListener();

    private Map<String, ShapeProvider> providers = new HashMap<String, ShapeProvider>();

    private Map<String, Collection<ShapeComponent>> goneComponents
            = new HashMap<String, Collection<ShapeComponent>>();

    public PaintFrame() {
        super("PaintFrame");
        toolbar = new JToolBar("Toolbar");
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        panel.setMinimumSize(new Dimension(400, 400));
        panel.addMouseListener(this);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(toolbar, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
        setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
        });
    }

    public void selectShape(String name) {
        selected = name;
    }

    public void bindService(@Observes ServiceArrival event) {
        System.out.println("bind : " + event.getServiceClassNames());
        addShape(event.type(ShapeProvider.class).getService());
    }

    public void unbindService(@Observes ServiceDeparture event) {
        System.out.println("unbind : " + event.getServiceClassNames());
        removeShape(event.type(ShapeProvider.class).getService().getId());
    }

    private void addShape(ShapeProvider provider) {
        if (!providers.containsKey(provider.getId())) {
            providers.put(provider.getId(), provider);
            Shape shape = provider.getShape();
            JButton button = new JButton(shape.getIcon());
            button.setActionCommand(provider.getId());
            button.setToolTipText(shape.getName());
            button.addActionListener(actionListener);
            toolbar.add(button);
            toolbar.validate();
            if (goneComponents.containsKey(provider.getId())) {
                for (ShapeComponent comp : goneComponents.get(provider.getId())) {
                    panel.add(comp);
                }
                panel.validate();
                goneComponents.get(provider.getId()).clear();
            }
            repaint();
        }
    }

    private void removeShape(String name) {
        providers.remove(name);
        if (!goneComponents.containsKey(name)) {
            goneComponents.put(name, new ArrayList<ShapeComponent>());
        }
        for (Component comp : panel.getComponents()) {
            ShapeComponent shapeComp = (ShapeComponent) comp;
            if (shapeComp.getShapeId().equals(name)) {
                goneComponents.get(name).add(shapeComp);
                panel.remove(comp);
            }
            panel.validate();
            panel.repaint();
        }
        if ((selected != null) && selected.equals(name)) {
            selected = null;
        }
        for (int i = 0; i < toolbar.getComponentCount(); i++) {
            JButton sb = (JButton) toolbar.getComponent(i);
            if (sb.getActionCommand().equals(name)) {
                toolbar.remove(i);
                toolbar.invalidate();
                validate();
                repaint();
                break;
            }
        }
        if ((selected == null) && (toolbar.getComponentCount() > 0)) {
            ((JButton) toolbar.getComponent(0)).doClick();
        }
    }

    @Override
    public void mouseClicked(MouseEvent evt) {       
    }

    @Override
    public void mousePressed(MouseEvent evt) {
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
        if (selected == null) {
            return;
        }
        if (panel.contains(evt.getX(), evt.getY())) {
            ShapeComponent sc = null;
            if (providers.containsKey(selected)) {
                sc = new ShapeComponent(providers.get(selected).getShape());
                sc.setBounds(evt.getX() - BOX / 2, evt.getY() - BOX / 2, BOX, BOX);
                panel.add(sc, 0);
                panel.validate();
                panel.repaint(sc.getBounds());
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
    }

    @Override
    public void mouseExited(MouseEvent evt) {
    }

    public void start() {
        addShape(defaultProvider);
        for (ShapeProvider provider : registeredProviders) {
            addShape(provider);
        }
        this.setVisible(true);
    }

    public void stop() {
        this.dispose();
    }

    private class ShapeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            selectShape(evt.getActionCommand());
        }
    }
}
