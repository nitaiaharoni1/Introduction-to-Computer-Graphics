// 
// Decompiled by Procyon v0.5.30
// 

package edu.cg.menu.components;

import java.util.Calendar;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.border.Border;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextArea;
import edu.cg.Logger;
import javax.swing.JPanel;

public class LogField extends JPanel implements Logger
{
    private JTextArea txtLog;
    
    public LogField() {
        this.setBorder(new EtchedBorder(1, null, null));
        this.setLayout(new BorderLayout(0, 0));
        final JLabel lblLog = new JLabel(" Log:   ");
        this.add(lblLog, "West");
        (this.txtLog = new JTextArea()).setEditable(false);
        this.txtLog.setLineWrap(true);
        final JScrollPane scrollLog = new JScrollPane(this.txtLog);
        this.add(scrollLog);
    }
    
    @Override
    public synchronized void log(String s) {
        if (s == null) {
            s = "null";
        }
        final Calendar cal = Calendar.getInstance();
        final String hh = convertTime(cal.get(11));
        final String mm = convertTime(cal.get(12));
        final String ss = convertTime(cal.get(13));
        final String time = "[" + hh + ":" + mm + ":" + ss + "] ~ ";
        final String msg = String.valueOf(time) + s + System.lineSeparator();
        this.txtLog.append(msg);
    }
    
    private static String convertTime(final int t) {
        return String.valueOf((t < 10) ? "0" : "") + t;
    }
}
