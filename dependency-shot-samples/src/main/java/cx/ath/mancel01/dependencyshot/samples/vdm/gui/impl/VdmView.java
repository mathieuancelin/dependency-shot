/*
 *  Copyright 2010 Mathieu ANCELIN.
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
import java.awt.event.MouseEvent;
import java.util.Observable;
import javax.inject.Inject;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * The view of the app (not really great).
 *
 * @author Mathieu ANCELIN
 */
public class VdmView extends JFrame implements View {

    /**
     * A JScrollPanel for the JTextPanel.
     */
	private JScrollPane jScrollPane1;

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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jScrollPane1.setName("jScrollPane1");
        jTextPane1.setEditable(false);
        jTextPane1.setName("jTextPane1");
        jScrollPane1.setViewportView(jTextPane1);
        jTextPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vdmClicked(evt);
            }
        });
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
        pack();
    }

    /**
     * Action for a click on the JTextPanel
     * 
     * @param evt the mouse event.
     */
    private void vdmClicked(MouseEvent evt) {
        this.controller.updateModel();
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void update(Observable o, Object o1) {
        this.jTextPane1.setText(controller.getModel().toString());
    }
    
    /**
     * Configuration of the view.
     * The view link itself with the controller
     */
    private void config() {
        controller.addViewObserver(this);
    }

    /**
     * @{@inheritDoc}
     */
    @Override
    public void start() {
        config();
        setVisible(true);
    }
}
