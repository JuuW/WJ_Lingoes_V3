/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.wj.lingoes.v2.utility;

import java.awt.Component;

/**
 *
 * @author qphan
 */
public class SizeUtils {

    private SizeUtils() {
    }

    public static int getPreWidth(Component comp) {
        return (int) comp.getPreferredSize().getWidth();
    }

    public static int getPreHeight(Component comp) {
        return (int) comp.getPreferredSize().getHeight();
    }

    public static int getWidth(Component comp) {
        return (int) comp.getSize().getWidth();
    }
    
    public static int getHeight(Component comp) {
        return (int) comp.getSize().getHeight();
    }
}
