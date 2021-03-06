/*
 * CreditCardView.java
 */

package creditcard;

import business.Card;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The application's main frame.
 */
public class CreditCardView extends FrameView {
    Card cc;

    public CreditCardView(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = CreditCardApp.getApplication().getMainFrame();
            aboutBox = new CreditCardAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        CreditCardApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtxtAcctNo = new javax.swing.JTextField();
        jbtnNew = new javax.swing.JButton();
        jbtnExisting = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtxtChgAmt = new javax.swing.JTextField();
        jtxtPmt = new javax.swing.JTextField();
        jtxtCrInc = new javax.swing.JTextField();
        jtxtRate = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jtxtChgDesc = new javax.swing.JTextField();
        jbtnChg = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jbtnPmt = new javax.swing.JButton();
        jbtnCrInc = new javax.swing.JButton();
        jbtnIntChg = new javax.swing.JButton();
        jbtnLog = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtxtCLimit = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtBalDue = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jtxtAvailCr = new javax.swing.JTextField();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(creditcard.CreditCardApp.class).getContext().getResourceMap(CreditCardView.class);
        jLabel1.setFont(resourceMap.getFont("jLabel1.font")); // NOI18N
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jtxtAcctNo.setText(resourceMap.getString("jtxtAcctNo.text")); // NOI18N
        jtxtAcctNo.setName("jtxtAcctNo"); // NOI18N

        jbtnNew.setFont(resourceMap.getFont("jbtnNew.font")); // NOI18N
        jbtnNew.setText(resourceMap.getString("jbtnNew.text")); // NOI18N
        jbtnNew.setName("jbtnNew"); // NOI18N
        jbtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnNewActionPerformed(evt);
            }
        });

        jbtnExisting.setFont(resourceMap.getFont("jbtnExisting.font")); // NOI18N
        jbtnExisting.setText(resourceMap.getString("jbtnExisting.text")); // NOI18N
        jbtnExisting.setActionCommand(resourceMap.getString("jbtnExisting.actionCommand")); // NOI18N
        jbtnExisting.setName("jbtnExisting"); // NOI18N
        jbtnExisting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExistingActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel1.setName("jPanel1"); // NOI18N

        jLabel2.setFont(resourceMap.getFont("jLabel2.font")); // NOI18N
        jLabel2.setText(resourceMap.getString("jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jtxtChgAmt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtChgAmt.setText(resourceMap.getString("jtxtChgAmt.text")); // NOI18N
        jtxtChgAmt.setName("jtxtChgAmt"); // NOI18N

        jtxtPmt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtPmt.setText(resourceMap.getString("jtxtPmt.text")); // NOI18N
        jtxtPmt.setName("jtxtPmt"); // NOI18N

        jtxtCrInc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtCrInc.setText(resourceMap.getString("jtxtCrInc.text")); // NOI18N
        jtxtCrInc.setName("jtxtCrInc"); // NOI18N

        jtxtRate.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtRate.setText(resourceMap.getString("jtxtRate.text")); // NOI18N
        jtxtRate.setName("jtxtRate"); // NOI18N

        jLabel6.setFont(resourceMap.getFont("jLabel6.font")); // NOI18N
        jLabel6.setText(resourceMap.getString("jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        jtxtChgDesc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtChgDesc.setText(resourceMap.getString("jtxtChgDesc.text")); // NOI18N
        jtxtChgDesc.setName("jtxtChgDesc"); // NOI18N

        jbtnChg.setFont(resourceMap.getFont("jbtnChg.font")); // NOI18N
        jbtnChg.setText(resourceMap.getString("jbtnChg.text")); // NOI18N
        jbtnChg.setName("jbtnChg"); // NOI18N
        jbtnChg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnChgActionPerformed(evt);
            }
        });

        jLabel7.setFont(resourceMap.getFont("jLabel7.font")); // NOI18N
        jLabel7.setText(resourceMap.getString("jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        jLabel8.setFont(resourceMap.getFont("jLabel8.font")); // NOI18N
        jLabel8.setText(resourceMap.getString("jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        jLabel9.setFont(resourceMap.getFont("jLabel9.font")); // NOI18N
        jLabel9.setText(resourceMap.getString("jLabel9.text")); // NOI18N
        jLabel9.setName("jLabel9"); // NOI18N

        jbtnPmt.setFont(resourceMap.getFont("jbtnPmt.font")); // NOI18N
        jbtnPmt.setText(resourceMap.getString("jbtnPmt.text")); // NOI18N
        jbtnPmt.setName("jbtnPmt"); // NOI18N
        jbtnPmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPmtActionPerformed(evt);
            }
        });

        jbtnCrInc.setFont(resourceMap.getFont("jbtnCrInc.font")); // NOI18N
        jbtnCrInc.setText(resourceMap.getString("jbtnCrInc.text")); // NOI18N
        jbtnCrInc.setName("jbtnCrInc"); // NOI18N
        jbtnCrInc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCrIncActionPerformed(evt);
            }
        });

        jbtnIntChg.setFont(resourceMap.getFont("jbtnIntChg.font")); // NOI18N
        jbtnIntChg.setText(resourceMap.getString("jbtnIntChg.text")); // NOI18N
        jbtnIntChg.setName("jbtnIntChg"); // NOI18N
        jbtnIntChg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnIntChgActionPerformed(evt);
            }
        });

        jbtnLog.setFont(resourceMap.getFont("jbtnLog.font")); // NOI18N
        jbtnLog.setText(resourceMap.getString("jbtnLog.text")); // NOI18N
        jbtnLog.setName("jbtnLog"); // NOI18N
        jbtnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(145, 145, 145)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtChgDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jtxtPmt, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jtxtCrInc, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jtxtRate, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addComponent(jtxtChgAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jbtnCrInc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnPmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jbtnIntChg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnChg)
                    .addComponent(jbtnLog))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jtxtChgAmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jtxtChgDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnChg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jtxtPmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnPmt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jtxtCrInc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(jbtnCrInc))
                        .addGap(11, 11, 11))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jbtnLog)
                        .addGap(3, 3, 3)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(jbtnIntChg))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jPanel2.setName("jPanel2"); // NOI18N

        jLabel3.setFont(resourceMap.getFont("jLabel3.font")); // NOI18N
        jLabel3.setText(resourceMap.getString("jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jtxtCLimit.setEditable(false);
        jtxtCLimit.setBackground(resourceMap.getColor("jtxtCLimit.background")); // NOI18N
        jtxtCLimit.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtCLimit.setText(resourceMap.getString("jtxtCLimit.text")); // NOI18N
        jtxtCLimit.setName("jtxtCLimit"); // NOI18N

        jLabel4.setFont(resourceMap.getFont("jLabel4.font")); // NOI18N
        jLabel4.setText(resourceMap.getString("jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jtxtBalDue.setEditable(false);
        jtxtBalDue.setBackground(resourceMap.getColor("jtxtBalDue.background")); // NOI18N
        jtxtBalDue.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtBalDue.setText(resourceMap.getString("jtxtBalDue.text")); // NOI18N
        jtxtBalDue.setName("jtxtBalDue"); // NOI18N

        jLabel5.setFont(resourceMap.getFont("jLabel5.font")); // NOI18N
        jLabel5.setText(resourceMap.getString("jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jtxtAvailCr.setEditable(false);
        jtxtAvailCr.setBackground(resourceMap.getColor("jtxtAvailCr.background")); // NOI18N
        jtxtAvailCr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtAvailCr.setText(resourceMap.getString("jtxtAvailCr.text")); // NOI18N
        jtxtAvailCr.setName("jtxtAvailCr"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jtxtAvailCr, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtCLimit, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtBalDue, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(221, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jtxtCLimit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jtxtBalDue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jtxtAvailCr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addGap(18, 18, 18)
                            .addComponent(jtxtAcctNo, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(27, 27, 27)
                            .addComponent(jbtnNew)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jbtnExisting))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jtxtAcctNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnNew)
                    .addComponent(jbtnExisting))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(creditcard.CreditCardApp.class).getContext().getActionMap(CreditCardView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 372, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnNewActionPerformed
        statusMessageLabel.setText("");
        cc = new Card();
        if(!cc.getErrMsg().isEmpty())
        {
            statusMessageLabel.setText(cc.getErrMsg());
        }
        else
        {
            jtxtAcctNo.setText(String.valueOf(cc.getAcctNo()));//because acctno is an int, we need to change to a string
            DisplayValues();
        }
    }//GEN-LAST:event_jbtnNewActionPerformed

    private void jbtnChgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnChgActionPerformed
        statusMessageLabel.setText("");
        double chgamt;
        try
        {
            chgamt = Double.parseDouble(jtxtChgAmt.getText());
        }
        catch(NumberFormatException e)
        {
            statusMessageLabel.setText("Bad charge amount: " + e.getMessage());
            return ;
        }
        
        cc.setCharge(chgamt, jtxtChgDesc.getText());
        if(!cc.getErrMsg().isEmpty())
        {
            statusMessageLabel.setText(cc.getErrMsg());
        }
        else
        {
            DisplayValues();
        }
        jtxtChgAmt.setText("");
        jtxtChgDesc.setText("");
        jtxtChgAmt.requestFocusInWindow();
    }//GEN-LAST:event_jbtnChgActionPerformed

    private void jbtnExistingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExistingActionPerformed
        statusMessageLabel.setText("");
        int acno;
        try
        {
            acno = Integer.parseInt(jtxtAcctNo.getText()); 
        }
        catch(NumberFormatException e)
        {
            statusMessageLabel.setText("Illegal account number.");
            return ; 
        }
        cc = new Card(acno); //second type of constructor for Card.java
        if(!cc.getErrMsg().isEmpty())
        {
            statusMessageLabel.setText(cc.getErrMsg());
        }
        else
        {
            DisplayValues();
        }
    }//GEN-LAST:event_jbtnExistingActionPerformed

    private void jbtnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLogActionPerformed
        statusMessageLabel.setText("");
        ArrayList<String> log = cc.getLog();
        if (!cc.getErrMsg().isEmpty())
        {
            statusMessageLabel.setText(cc.getErrMsg());
            return ;
        }
        statusMessageLabel.setText(cc.getActMsg());
        JTextArea t = new JTextArea();
        for(int i=0; i < log.size(); i++)
        {
            t.append(log.get(i) + "\n");
        }
        JScrollPane sp = new JScrollPane(t);
        JDialog dg = new JDialog();
        dg.add(sp);
        dg.setTitle("Log for account: " + cc.getAcctNo());
        dg.setBounds(150,400,800,300);
        dg.setVisible(true);
    }//GEN-LAST:event_jbtnLogActionPerformed

    private void jbtnPmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPmtActionPerformed
        statusMessageLabel.setText("");
        double pmt; 
        try
        {
            pmt = Double.parseDouble(jtxtPmt.getText());
        }
        catch(Exception e)
        {
            statusMessageLabel.setText("Invalid payment." + e.getMessage());
            return ;
        }
        
        cc.getPmt(pmt);
        if(!cc.getErrMsg().isEmpty())
        {
            statusMessageLabel.setText(cc.getErrMsg());
        }
        else
        {
            DisplayValues();
            statusMessageLabel.setText("Payment: " + pmt + " posted.");
        }
        jtxtPmt.setText("");
        jtxtPmt.requestFocusInWindow();
    }//GEN-LAST:event_jbtnPmtActionPerformed

    private void jbtnCrIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCrIncActionPerformed
        statusMessageLabel.setText("");
        double crinc;
        try
        {
            crinc = Double.parseDouble(jtxtCrInc.getText());
        }
        catch(Exception e)
        {
            statusMessageLabel.setText("Invalid input.");
            return ;
        }
        cc.setCreditInc(crinc);
        if(!cc.getErrMsg().isEmpty())
        {
            statusMessageLabel.setText(cc.getErrMsg());
        }
        else
        {
            DisplayValues();
            statusMessageLabel.setText(cc.getActMsg());
        }
        jtxtCrInc.setText("");
        jtxtCrInc.requestFocusInWindow();
    }//GEN-LAST:event_jbtnCrIncActionPerformed

    private void jbtnIntChgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnIntChgActionPerformed
        statusMessageLabel.setText("");
        double intR;
        try
        {
            intR = Double.parseDouble(jtxtRate.getText());
        }
        catch(Exception e)
        {
            statusMessageLabel.setText("Unaccepted rate.");
            return ;
        }
        cc.setRate(intR);
        if(!cc.getErrMsg().isEmpty())
        {
            statusMessageLabel.setText(cc.getErrMsg());
        }
        else
        {
            DisplayValues();
            statusMessageLabel.setText("Rate applied to Balance Due");
        }
        jtxtRate.setText("");
        jtxtRate.requestFocusInWindow();
    }//GEN-LAST:event_jbtnIntChgActionPerformed
private void DisplayValues()
    {
        NumberFormat curr = NumberFormat.getCurrencyInstance();
        jtxtCLimit.setText(curr.format(cc.getCLimit()));
            jtxtBalDue.setText(curr.format(cc.getBalDue()));
            jtxtAvailCr.setText(curr.format(cc.getAvailCr()));           
            statusMessageLabel.setText(cc.getActMsg());
            
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbtnChg;
    private javax.swing.JButton jbtnCrInc;
    private javax.swing.JButton jbtnExisting;
    private javax.swing.JButton jbtnIntChg;
    private javax.swing.JButton jbtnLog;
    private javax.swing.JButton jbtnNew;
    private javax.swing.JButton jbtnPmt;
    private javax.swing.JTextField jtxtAcctNo;
    private javax.swing.JTextField jtxtAvailCr;
    private javax.swing.JTextField jtxtBalDue;
    private javax.swing.JTextField jtxtCLimit;
    private javax.swing.JTextField jtxtChgAmt;
    private javax.swing.JTextField jtxtChgDesc;
    private javax.swing.JTextField jtxtCrInc;
    private javax.swing.JTextField jtxtPmt;
    private javax.swing.JTextField jtxtRate;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    // End of variables declaration//GEN-END:variables

    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;

    private JDialog aboutBox;
}
