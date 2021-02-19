/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digitalsignature.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Admin
 */
public class ViewHelper {

    // Get JButton from panel
    public List<JButton> getJButtonFromPanel(JPanel panel) {
        List<JButton> btns = new ArrayList<JButton>();
        Component[] comps = panel.getComponents();
        for (Component btn : comps) {
            if (btn instanceof JButton) {
                btns.add((JButton) btn);
            }
        }
        return btns;
    }

    // Remove focus painted on button
    public void removeFocusPaintedOnButton(List<JButton> btns) {
        for (JButton btn : btns) {
            btn.setFocusPainted(false);
        }
    }
    
    public void setCursorOnButton(List<JButton> btns) {
        for (JButton btn : btns) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
    
    public void setEffectButton(List<JButton> btns, Color mouseEntered, Color mouseClicked, Color mouseExited){
        removeFocusPaintedOnButton(btns);
        setCursorOnButton(btns);
        setEffectColorOnButton(btns, new Color(0,102,204), new Color(51,105,225), new Color(51,102,255));
    }
    
    public void setEffectColorOnButton(List<JButton> btns, Color mouseEntered, Color mouseClicked, Color mouseExited) {
        for (JButton btn : btns) {
            btn.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if(((JButton)e.getSource()).getModel().isPressed()){
                        ((JButton)e.getSource()).setBackground(mouseClicked);
                    }else if(((JButton)e.getSource()).getModel().isRollover()){
                        ((JButton)e.getSource()).setBackground(mouseEntered);
                    }else{
                        ((JButton)e.getSource()).setBackground(mouseExited);
                    }
                }
            });
        }
    }
    
    public void addJPanelToAnotherJPanel(JPanel parentPanel, JPanel panelAdded){
        parentPanel.removeAll();
        parentPanel.add(panelAdded);
        parentPanel.revalidate();
        parentPanel.repaint();
    }
    
    // When click to button (key) int map then parentPanel with set child panel (value) int map corresponding with button (key)
    public void mapBtnToPanel(Map<JButton, JPanel> mapBtnToPanel, JPanel parentPanel){
        for (Map.Entry<JButton, JPanel> entry : mapBtnToPanel.entrySet()) {
            JButton btn = entry.getKey();
            JPanel panel = entry.getValue();
            btn.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if(((JButton)e.getSource()).getModel().isPressed()){
                        addJPanelToAnotherJPanel(parentPanel, panel);
                    }
                }
            });
            
        }
    }
}
