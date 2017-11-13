
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class UserRegistrationWindow extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public UserRegistrationWindow() {
        initComponents();
        radio_solver();
    }

    void radio_solver() {
        ButtonGroup genderButton = new ButtonGroup();

        genderButton.add(maleButton);
        genderButton.add(femaleButton);

    }
    String ImgPath = null;
    int pos = 0;
    String gender = null;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPasswordField1 = new javax.swing.JPasswordField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        usernameLabel = new javax.swing.JLabel();
        passLabel = new javax.swing.JLabel();
        rePassLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        refreshButton = new javax.swing.JButton();
        previewButton = new javax.swing.JButton();
        submitButton = new javax.swing.JButton();
        passTextField = new javax.swing.JPasswordField();
        rePassTextField = new javax.swing.JPasswordField();
        fNameLabel = new javax.swing.JLabel();
        lNameLabel = new javax.swing.JLabel();
        ageLabel = new javax.swing.JLabel();
        ageComboBox = new javax.swing.JComboBox<>();
        genderLabel = new javax.swing.JLabel();
        maleButton = new javax.swing.JRadioButton();
        femaleButton = new javax.swing.JRadioButton();
        lNameTextField = new javax.swing.JTextField();
        fNameTextField = new javax.swing.JTextField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        userLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        userImage = new javax.swing.JLabel();
        userImageChooser = new javax.swing.JButton();
        backToLogIn = new javax.swing.JButton();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("User Registration");
        setLocation(new java.awt.Point(50, 50));
        setResizable(false);

        usernameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usernameLabel.setText("User Name : ");

        passLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        passLabel.setText("Password :");

        rePassLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rePassLabel.setText("Confirm Password :");

        usernameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usernameTextField.setToolTipText("");
        usernameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameTextFieldActionPerformed(evt);
            }
        });

        refreshButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        refreshButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/refresh.png"))); // NOI18N
        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        previewButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        previewButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/Contact.png"))); // NOI18N
        previewButton.setText("PREVIEW");
        previewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previewButtonActionPerformed(evt);
            }
        });

        submitButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        submitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/OK.png"))); // NOI18N
        submitButton.setText("SUBMIT");
        submitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButtonActionPerformed(evt);
            }
        });

        passTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        passTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passTextFieldActionPerformed(evt);
            }
        });

        rePassTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        fNameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fNameLabel.setText("First Name : ");

        lNameLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lNameLabel.setText("Last Name : ");

        ageLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ageLabel.setText("Age :");

        ageComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45" }));
        ageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ageComboBoxActionPerformed(evt);
            }
        });

        genderLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        genderLabel.setText("Gender");

        maleButton.setText("Male");
        maleButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                maleButtonMouseClicked(evt);
            }
        });
        maleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleButtonActionPerformed(evt);
            }
        });

        femaleButton.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        femaleButton.setText("Female");
        femaleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleButtonActionPerformed(evt);
            }
        });

        lNameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lNameTextFieldActionPerformed(evt);
            }
        });

        fNameTextField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        fNameTextField.setToolTipText("");
        fNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fNameTextFieldActionPerformed(evt);
            }
        });

        jLayeredPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.blue, java.awt.Color.cyan));

        userLabel.setFont(new java.awt.Font("Copperplate Gothic Bold", 1, 24)); // NOI18N
        userLabel.setForeground(new java.awt.Color(255, 0, 0));
        userLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        userLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/Sign-up64.png"))); // NOI18N
        userLabel.setText("USER REGISTRATION ");

        jLayeredPane1.setLayer(userLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jLayeredPane1Layout = new javax.swing.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(userLabel)
                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(userLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setText("Image:");

        userImage.setBackground(new java.awt.Color(204, 255, 255));
        userImage.setOpaque(true);

        userImageChooser.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        userImageChooser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/Camera.png"))); // NOI18N
        userImageChooser.setText("Choose Image");
        userImageChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userImageChooserActionPerformed(evt);
            }
        });

        backToLogIn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        backToLogIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/go-back32.png"))); // NOI18N
        backToLogIn.setText("Back");
        backToLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backToLogInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passLabel)
                            .addComponent(rePassLabel)
                            .addComponent(ageLabel)
                            .addComponent(lNameLabel)
                            .addComponent(fNameLabel)
                            .addComponent(usernameLabel)
                            .addComponent(genderLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(refreshButton))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backToLogIn)))
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(maleButton)
                                .addGap(18, 18, 18)
                                .addComponent(femaleButton))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(rePassTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                .addComponent(passTextField, javax.swing.GroupLayout.Alignment.LEADING))
                            .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(ageComboBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lNameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                                    .addComponent(fNameTextField, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(75, 75, 75)
                                .addComponent(jLabel10)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(userImage, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(userImageChooser, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(previewButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(submitButton)
                        .addGap(77, 77, 77)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backToLogIn)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(fNameLabel)
                                    .addComponent(fNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lNameLabel)
                                    .addComponent(lNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(26, 26, 26)
                                .addComponent(ageComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ageLabel))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(genderLabel)
                            .addComponent(maleButton)
                            .addComponent(femaleButton))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usernameLabel)
                            .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(userImage, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(passTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(passLabel)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(userImageChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rePassLabel)
                    .addComponent(rePassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(submitButton)
                    .addComponent(previewButton)
                    .addComponent(refreshButton))
                .addGap(54, 54, 54))
        );

        backToLogIn.getAccessibleContext().setAccessibleName("back");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void passTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passTextFieldActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        fNameTextField.setText(null);
        lNameTextField.setText(null);
      //  ageComboBox.setSelectedIndex(0);
        ageComboBox.setSelectedItem(null);
        usernameTextField.setText("");
        passTextField.setText("");
        rePassTextField.setText("");
        userImage.setIcon(null);
        maleButton.setSelected(false);
       // maleButton.setRolloverEnabled(true);
        femaleButton.setSelected(false);
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void previewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previewButtonActionPerformed
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            Preview regPrvw = new Preview();
            regPrvw.setVisible(true);
            
            regPrvw.setNameText(fNameTextField.getText() + " " + lNameTextField.getText());
            String age = String.valueOf(ageComboBox.getSelectedItem());
            regPrvw.setAgeText(age);
            regPrvw.setGenderText(gender);
            regPrvw.setUName(usernameTextField.getText());
            regPrvw.setPassText(passTextField.getText());
            
            regPrvw.setUserIMage(ResizeImage(ImgPath, null));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UserRegistrationWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(UserRegistrationWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(UserRegistrationWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(UserRegistrationWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
             
    }//GEN-LAST:event_previewButtonActionPerformed

    private void submitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitButtonActionPerformed
        if (fNameTextField.getText().equals(null) || lNameTextField.getText().equals(null)
                || usernameTextField.getText().equals("") || passTextField.getText().equals("")
                || gender.equals("") || ImgPath==null
                ) {
            JOptionPane.showMessageDialog(null, "All Fields Are Required!");
        } else if (!passTextField.getText().equals(rePassTextField.getText())) {
            
            JOptionPane.showMessageDialog(null, "Password Did Not Match!", "Try Again!", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                con = MySqlConnect.ConnectDB();
                PreparedStatement ps = con.prepareStatement("INSERT INTO user_data(username,password,name,age,gender,image,access)"
                        + "values(?,?,?,?,?,?,?) ");

                ps.setString(1, usernameTextField.getText());
                ps.setString(2, passTextField.getText());

                String name = fNameTextField.getText() + " " + lNameTextField.getText();
                ps.setString(3, name.toUpperCase());

                String age = String.valueOf(ageComboBox.getSelectedItem());
                ps.setString(4, age);

                ps.setString(5, gender.toUpperCase());
                if (ImgPath != null) {
                    InputStream img = new FileInputStream(new File(ImgPath));
                    ps.setBlob(6, img);
                } else {

                    InputStream img = null;
                    ps.setBlob(6, img);
                }
                PreparedStatement ps1 = con.prepareStatement("SELECT *FROM user_data");

                rs = ps1.executeQuery();
                if (rs.next()) {
                    ps.setString(7, "User");
                } else {
                    ps.setString(7, "Admin");

                }
                ps.executeUpdate();

                refreshButtonActionPerformed(evt);

                //   UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
                UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                JOptionPane.showMessageDialog(null, "Registration Successful");
                UserRegistrationWindow.this.setVisible(false);
                LogInPage lgn = new LogInPage();
                lgn.setVisible(true);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Recheck All Fields!");
            }
            // JOptionPane.showMessageDialog(null, "Regestration Successful!");
        }
    }//GEN-LAST:event_submitButtonActionPerformed

    private void usernameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameTextFieldActionPerformed

    private void lNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lNameTextFieldActionPerformed

    private void fNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fNameTextFieldActionPerformed

    private void femaleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleButtonActionPerformed
        // TODO add your handling code here:
        gender = "Female";
    }//GEN-LAST:event_femaleButtonActionPerformed

    private void maleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleButtonActionPerformed
        // TODO add your handling code here:
        gender = "Male";
    }//GEN-LAST:event_maleButtonActionPerformed
    public ImageIcon ResizeImage(String imagePath, byte[] pic) {
        ImageIcon myImage = null;

        if (imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }

        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(userImage.getWidth(), userImage.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;

    }
    private void userImageChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userImageChooserActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("images", "png", "jpg", "jpeg");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            userImage.setIcon(ResizeImage(path, null));

            ImgPath = path;
        } else {
            System.out.println("No File Selected");
        }
    }//GEN-LAST:event_userImageChooserActionPerformed

    private void maleButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_maleButtonMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_maleButtonMouseClicked

    private void backToLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backToLogInActionPerformed
        // TODO add your handling code here:
        UserRegistrationWindow.this.setVisible(false);
        LogInPage lg=new LogInPage();
        lg.setVisible(true);
    }//GEN-LAST:event_backToLogInActionPerformed

    private void ageComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ageComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ageComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {

            // select Look and Feel
            //   UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            new UserRegistrationWindow().setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserRegistrationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserRegistrationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserRegistrationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserRegistrationWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /*a.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserRegistrationWindow().setVisible(true);
            }
        });
         */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ageComboBox;
    private javax.swing.JLabel ageLabel;
    private javax.swing.JButton backToLogIn;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JLabel fNameLabel;
    private javax.swing.JTextField fNameTextField;
    private javax.swing.JRadioButton femaleButton;
    private javax.swing.JLabel genderLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JLabel lNameLabel;
    private javax.swing.JTextField lNameTextField;
    private javax.swing.JRadioButton maleButton;
    private javax.swing.JLabel passLabel;
    private javax.swing.JPasswordField passTextField;
    private javax.swing.JButton previewButton;
    private javax.swing.JLabel rePassLabel;
    private javax.swing.JPasswordField rePassTextField;
    private javax.swing.JButton refreshButton;
    private javax.swing.JButton submitButton;
    private javax.swing.JLabel userImage;
    private javax.swing.JButton userImageChooser;
    private javax.swing.JLabel userLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
