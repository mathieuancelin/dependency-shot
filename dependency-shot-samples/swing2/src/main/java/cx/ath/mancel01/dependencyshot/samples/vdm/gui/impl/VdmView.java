/*
 *  Copyright 2009-2010 Mathieu ANCELIN.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package cx.ath.mancel01.dependencyshot.samples.vdm.gui.impl;

import cx.ath.mancel01.dependencyshot.samples.vdm.gui.Controller;
import cx.ath.mancel01.dependencyshot.samples.vdm.gui.View;
import cx.ath.mancel01.dependencyshot.utils.annotations.Log;
import java.util.Observable;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.inject.Inject;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * The view of the app (not really great).
 *
 * @author Mathieu ANCELIN
 */
@ManagedBean
public class VdmView extends JFrame implements View {

    /**
     * Start width of the app.
     */
    private static final int APP_WIDTH = 400;
    /**
     * Start height of the app.
     */
    private static final int APP_HEIGHT = 300;

    /**
     * The panel to show the VDM.
     */
    private JTextPane jTextPane1;

    /**
     * The unique controller of the app.
     */
    @Inject
    private Controller controller;

    /**
     * The logger of the view;
     */
    @Inject @Log
    private Logger logger;

    /**
     * The constructor of the view.
     */
    public VdmView() {
        super();
        initComponents();
    }

    /**
     * Init of the graphical component of the view.
     */
    private void initComponents() {
        this.setTitle("Random VDM : Click on the text to update it ");
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jScrollPane1.setName("jScrollPane1");
        jTextPane1.setEditable(false);
        jTextPane1.setName("jTextPane1");
        jScrollPane1.setViewportView(jTextPane1);
        jTextPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vdmClicked();
            }
        });
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, APP_WIDTH, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, APP_HEIGHT, Short.MAX_VALUE)
        );
        pack();
    }

    /**
     * Action for a click on the JTextPanel
     */
    private void vdmClicked() {
        logger.info("Need new VDM");
        this.controller.updateModel(); 
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public final void update(Observable o, Object o1) {
        logger.info("Model was updated, updating view");
        this.jTextPane1.setText(controller.getModel().toString());
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public final void start() {
        setVisible(true);
    }
}
