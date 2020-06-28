/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utils;

import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 *
 * @author darwin
 */
public class FrameUtils {
    public static void exit(JFrame frame) {
        WindowEvent winClosingEvent = new WindowEvent(frame,WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClosingEvent);
    }
}
