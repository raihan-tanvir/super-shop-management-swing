package javaapplication1;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
public class EmployeeUI extends javax.swing.JFrame {

    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    int currentUnit;
    int sellUnit;
    String ts;

    public void tickTock() {
        pDateLabel.setText(DateFormat.getDateTimeInstance().format(new Date()));
        sDateLabel.setText(DateFormat.getDateTimeInstance().format(new Date()));
    }

    public static String showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("MMM dd yyyy hh:mm ");

        String str = s.format(d);
        return str;
    }

    public float getSum() {
        int rowsCount = JTable_Sales.getRowCount();
        float sum = 0;
        for (int i = 0; i < rowsCount; i++) {
            sum = sum + Float.parseFloat(JTable_Sales.getValueAt(i, 4).toString());
        }
        return sum;
    }

    public float getCurrentSales() {
        int row = JTable_Sales.getSelectedRow();
        return Float.parseFloat(JTable_Sales.getValueAt(row, 4).toString());
    }

    public EmployeeUI() {
        initComponents();
        Show_Products_In_JTable();
        Show_Sales_In_JTable_Sales();
        // pDateLabel.setText(showDate());
        totalSalesLabel.setText("Total:" + " " + String.valueOf(getSum()));
        tickTock();

        javax.swing.Timer timer = new javax.swing.Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tickTock();
            }
        });
        timer.setRepeats(true);
        timer.setCoalesce(true);
        timer.setInitialDelay(0);
        timer.start();

    }

    String ImgPath = null;
    int pos = 0;

    public boolean checkInputs() {
        if (pNameTextField.getText() == null || pUnitTextField.getText() == null) {
            return false;
        } else {
            try {
                Float.parseFloat(pPriceTextField.getText());
                return true;
            } catch (Exception ex) {
                return false;
            }
        }
    }

    public ImageIcon ResizeImage(String imagePath, byte[] pic) {
        ImageIcon myImage = null;

        if (imagePath != null) {
            myImage = new ImageIcon(imagePath);
        } else {
            myImage = new ImageIcon(pic);
        }

        Image img = myImage.getImage();
        Image img2 = img.getScaledInstance(pImageBoxLabel.getWidth(), pImageBoxLabel.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(img2);
        return image;

    }

    public ArrayList<Product> getProductList() {
        ArrayList<Product> productList = new ArrayList<Product>();
        con = MySqlConnect.ConnectDB();
        String query = "SELECT * FROM products";

        Statement st;
        ResultSet rs;

        try {

            st = con.createStatement();
            rs = st.executeQuery(query);
            Product product;

            while (rs.next()) {
                product = new Product(rs.getInt("id"), rs.getString("name"), Float.parseFloat(rs.getString("price")), rs.getInt("unit"), rs.getBytes("image"), rs.getString("date"), Float.parseFloat(rs.getString("sales")));
                productList.add(product);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return productList;

    }

    public void Show_Products_In_JTable() {
        ArrayList<Product> list = getProductList();
        DefaultTableModel model = (DefaultTableModel) JTable_Products.getModel();
        // clear jtable content
        model.setRowCount(0);
        Object[] row = new Object[5];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getId();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getPrice();
            row[3] = list.get(i).getUnit();
            row[4] = list.get(i).getAddDate();

            model.addRow(row);
        }

    }

    public void ShowItem(int index) {
        pIdTextField.setText(Integer.toString(getProductList().get(index).getId()));
        pNameTextField.setText(getProductList().get(index).getName());
        pPriceTextField.setText(Float.toString(getProductList().get(index).getPrice()));
        pUnitTextField.setText(Integer.toString(getProductList().get(index).getUnit()));

        pImageBoxLabel.setIcon(ResizeImage(null, getProductList().get(index).getImage()));

    }

    public ArrayList<UserTemplate> getUserList() {
        ArrayList<UserTemplate> userList = new ArrayList<UserTemplate>();
        con = MySqlConnect.ConnectDB();
        String query = "SELECT * FROM user_data";

        Statement st;
        ResultSet rs;

        try {

            st = con.createStatement();
            rs = st.executeQuery(query);
            UserTemplate ust;

            while (rs.next()) {
                ust = new UserTemplate(rs.getString("username"), rs.getString("name"), rs.getInt("age"), rs.getString("gender"), rs.getBytes("image"), rs.getString("access"));
                userList.add(ust);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userList;

    }

  
    public void Show_Sales_In_JTable_Sales() {
        ArrayList<Product> list2 = getProductList();
        DefaultTableModel model = (DefaultTableModel) JTable_Sales.getModel();
        // clear jtable content
        model.setRowCount(0);
        Object[] row = new Object[5];
        for (int i = 0; i < list2.size(); i++) {
            row[0] = list2.get(i).getId();
            row[1] = list2.get(i).getName();
            row[2] = list2.get(i).getPrice();
            row[3] = list2.get(i).getUnit();
            row[4] = list2.get(i).getSales();

            model.addRow(row);
        }

    }

    public void ShowSell(int index) {
        sIdTextField.setText(Integer.toString(getProductList().get(index).getId()));
        sNameTextField.setText(getProductList().get(index).getName());
        sPriceTextField.setText(Float.toString(getProductList().get(index).getPrice()));
        pUnitTextField.setText(Integer.toString(getProductList().get(index).getUnit()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        productsTab = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pIdTextField = new javax.swing.JTextField();
        pImageBoxLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JTable_Products = new javax.swing.JTable();
        productImageChooserButton = new javax.swing.JButton();
        pupdateButton = new javax.swing.JButton();
        premoveButton = new javax.swing.JButton();
        paddButton = new javax.swing.JButton();
        pTopButton = new javax.swing.JButton();
        pBottomButtom = new javax.swing.JButton();
        pDateLabel = new javax.swing.JLabel();
        pSearchButton = new javax.swing.JButton();
        pSearchBox = new javax.swing.JTextField();
        pClearButton = new javax.swing.JButton();
        pPriceTextField = new javax.swing.JTextField();
        pUnitTextField = new javax.swing.JTextField();
        pNameTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        sIdTextField = new javax.swing.JTextField();
        sUnitTextField = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        JTable_Sales = new javax.swing.JTable();
        pSaleButton = new javax.swing.JButton();
        pTopButton2 = new javax.swing.JButton();
        pBottomButtom2 = new javax.swing.JButton();
        sDateLabel = new javax.swing.JLabel();
        sClearButton = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        sTotalTextField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        sNetTotalTextField = new javax.swing.JTextField();
        discountComboBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        sNameTextField = new javax.swing.JTextField();
        sPriceTextField = new javax.swing.JTextField();
        totalSalesLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jMenuItem5.setText("jMenuItem5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Super Shop Manager V_1.0");
        setLocation(new java.awt.Point(20, 10));

        productsTab.setPreferredSize(new java.awt.Dimension(900, 620));
        productsTab.setRequestFocusEnabled(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("ID:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Name:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Price:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Unit:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Image:");

        pIdTextField.setEditable(false);
        pIdTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        pIdTextField.setPreferredSize(new java.awt.Dimension(59, 50));
        pIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pIdTextFieldActionPerformed(evt);
            }
        });

        pImageBoxLabel.setBackground(new java.awt.Color(204, 255, 255));
        pImageBoxLabel.setOpaque(true);

        JTable_Products.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        JTable_Products.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price", "Unit", "Last Updated"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        JTable_Products.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_ProductsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JTable_Products);
        if (JTable_Products.getColumnModel().getColumnCount() > 0) {
            JTable_Products.getColumnModel().getColumn(0).setMinWidth(50);
            JTable_Products.getColumnModel().getColumn(0).setPreferredWidth(60);
            JTable_Products.getColumnModel().getColumn(0).setMaxWidth(80);
            JTable_Products.getColumnModel().getColumn(2).setMinWidth(70);
            JTable_Products.getColumnModel().getColumn(2).setPreferredWidth(80);
            JTable_Products.getColumnModel().getColumn(2).setMaxWidth(100);
            JTable_Products.getColumnModel().getColumn(3).setMinWidth(40);
            JTable_Products.getColumnModel().getColumn(3).setPreferredWidth(60);
            JTable_Products.getColumnModel().getColumn(3).setMaxWidth(80);
        }

        productImageChooserButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        productImageChooserButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/Camera.png"))); // NOI18N
        productImageChooserButton.setText("Choose Image");
        productImageChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productImageChooserButtonActionPerformed(evt);
            }
        });

        pupdateButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pupdateButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/Renew.png"))); // NOI18N
        pupdateButton.setText("Update");
        pupdateButton.setIconTextGap(5);
        pupdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pupdateButtonActionPerformed(evt);
            }
        });

        premoveButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        premoveButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/close_delete_2.png"))); // NOI18N
        premoveButton.setText("Remove");
        premoveButton.setIconTextGap(5);
        premoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                premoveButtonActionPerformed(evt);
            }
        });

        paddButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        paddButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/plus.png"))); // NOI18N
        paddButton.setText("Add");
        paddButton.setIconTextGap(5);
        paddButton.setInheritsPopupMenu(true);
        paddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paddButtonActionPerformed(evt);
            }
        });

        pTopButton.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pTopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/arrow_up.png"))); // NOI18N
        pTopButton.setText("Top");
        pTopButton.setIconTextGap(5);
        pTopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pTopButtonActionPerformed(evt);
            }
        });

        pBottomButtom.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        pBottomButtom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/arrow_down.png"))); // NOI18N
        pBottomButtom.setText("Bottom");
        pBottomButtom.setIconTextGap(5);
        pBottomButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pBottomButtomActionPerformed(evt);
            }
        });

        pDateLabel.setBackground(new java.awt.Color(153, 255, 153));
        pDateLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        pDateLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        pDateLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/calendar.png"))); // NOI18N

        pSearchButton.setBackground(new java.awt.Color(204, 204, 255));
        pSearchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/Find.png"))); // NOI18N
        pSearchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pSearchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pSearchButtonMouseEntered(evt);
            }
        });
        pSearchButton.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pSearchButtonComponentShown(evt);
            }
        });
        pSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pSearchButtonActionPerformed(evt);
            }
        });

        pSearchBox.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        pSearchBox.setText("Search By Name");
        pSearchBox.setPreferredSize(new java.awt.Dimension(59, 50));
        pSearchBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pSearchBoxMouseClicked(evt);
            }
        });
        pSearchBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pSearchBoxActionPerformed(evt);
            }
        });

        pClearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/refresh.png"))); // NOI18N
        pClearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pClearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pClearButtonMouseEntered(evt);
            }
        });
        pClearButton.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                pClearButtonComponentShown(evt);
            }
        });
        pClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pClearButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2)
                    .addComponent(paddButton)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(pupdateButton)
                        .addGap(36, 36, 36)
                        .addComponent(premoveButton))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pNameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(pIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                            .addComponent(pClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(pImageBoxLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pPriceTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
                        .addComponent(pUnitTextField, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(productImageChooserButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pTopButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pBottomButtom))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pDateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pSearchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pSearchBox, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pClearButton)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(pIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel1)))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(pNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(pPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(pUnitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(pImageBoxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(productImageChooserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(paddButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(pupdateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(premoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 7, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pTopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pBottomButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );

        productsTab.addTab("Inventory", new javax.swing.ImageIcon(getClass().getResource("/IconImg/Inventory-maintenance.png")), jPanel1); // NOI18N

        jPanel4.setBackground(new java.awt.Color(204, 255, 204));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("ID:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Name:");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setText("Price:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setText("Unit:");

        sIdTextField.setEditable(false);
        sIdTextField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        sIdTextField.setPreferredSize(new java.awt.Dimension(59, 50));
        sIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sIdTextFieldActionPerformed(evt);
            }
        });

        JTable_Sales.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        JTable_Sales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Price", "Available Unit", "Sales"
            }
        ));
        JTable_Sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JTable_SalesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(JTable_Sales);

        pSaleButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        pSaleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/sale.png"))); // NOI18N
        pSaleButton.setText("Sell");
        pSaleButton.setIconTextGap(5);
        pSaleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pSaleButtonActionPerformed(evt);
            }
        });

        pTopButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        pTopButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/arrow_up.png"))); // NOI18N
        pTopButton2.setText("Top");
        pTopButton2.setIconTextGap(5);
        pTopButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pTopButton2ActionPerformed(evt);
            }
        });

        pBottomButtom2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        pBottomButtom2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/arrow_down.png"))); // NOI18N
        pBottomButtom2.setText("Bottom");
        pBottomButtom2.setIconTextGap(5);
        pBottomButtom2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pBottomButtom2ActionPerformed(evt);
            }
        });

        sDateLabel.setBackground(new java.awt.Color(153, 255, 153));
        sDateLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sDateLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sDateLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/calendar.png"))); // NOI18N

        sClearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/refresh.png"))); // NOI18N
        sClearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sClearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                sClearButtonMouseEntered(evt);
            }
        });
        sClearButton.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                sClearButtonComponentShown(evt);
            }
        });
        sClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sClearButtonActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setText("Total:");

        sTotalTextField.setEditable(false);

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setText("Discount:");

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setText("Net Total:");

        sNetTotalTextField.setEditable(false);
        sNetTotalTextField.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sNetTotalTextField.setPreferredSize(new java.awt.Dimension(59, 50));

        discountComboBox.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        discountComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "3", "5", "7", "10", "12", "15", "20", "25", "30" }));
        discountComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                discountComboBoxActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("%");

        sNameTextField.setEditable(false);
        sNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sNameTextFieldActionPerformed(evt);
            }
        });

        sPriceTextField.setEditable(false);

        totalSalesLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        totalSalesLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        totalSalesLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/Earning-statement.png"))); // NOI18N
        totalSalesLabel.setText("Total :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(pSaleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(187, 187, 187))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel23)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel18)
                                    .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(50, 50, 50)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(sIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(83, 83, 83)
                                                .addComponent(sClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(sNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(sPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(discountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(10, 10, 10)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(sUnitTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                        .addComponent(sNetTotalTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                        .addComponent(sTotalTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel22)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(pTopButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pBottomButtom2))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sDateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(66, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(totalSalesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(sIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17))
                            .addComponent(sClearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(sNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(sPriceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sUnitTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel21)
                                    .addComponent(discountComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(sNetTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(pSaleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(sDateLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5)
                        .addComponent(totalSalesLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(pTopButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pBottomButtom2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))))
        );

        productsTab.addTab("Sales", new javax.swing.ImageIcon(getClass().getResource("/IconImg/Sales-report32.png")), jPanel4); // NOI18N

        fileMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/options_2.png"))); // NOI18N
        fileMenu.setText("Tools");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/document_file.png"))); // NOI18N
        jMenuItem2.setText("Notepad");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/calculator.png"))); // NOI18N
        jMenuItem3.setText("Calculator");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuItem3);

        jMenuBar1.add(fileMenu);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/new_email.png"))); // NOI18N
        jMenu2.setText("Mail");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/comment.png"))); // NOI18N
        jMenuItem4.setText("Send");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/information.png"))); // NOI18N
        jMenu5.setText("About");

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/help.png"))); // NOI18N
        jMenuItem6.setText("FAQ");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/bug.gif"))); // NOI18N
        jMenuItem7.setText("Report Bug");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/webpage.png"))); // NOI18N
        jMenuItem8.setText("Credits");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenuBar1.add(jMenu5);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IconImg/Exit.png"))); // NOI18N
        jMenu1.setText("Logout");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu1MouseClicked(evt);
            }
        });
        jMenu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu1ActionPerformed(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(productsTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1005, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(productsTab, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pClearButtonActionPerformed
        // TODO add your handling code here:
        pNameTextField.setText(null);
        pIdTextField.setText(null);
        pPriceTextField.setText(null);
        pUnitTextField.setText(null);
        pImageBoxLabel.setIcon(null);
    }//GEN-LAST:event_pClearButtonActionPerformed

    private void pClearButtonComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pClearButtonComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_pClearButtonComponentShown

    private void pClearButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pClearButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pClearButtonMouseEntered

    private void pSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pSearchButtonActionPerformed
        // TODO add your handling code here:
        if (!pSearchBox.getText().equals("")) {
            try {
                con = MySqlConnect.ConnectDB();
                PreparedStatement ps = con.prepareStatement("Select *FROM products WHERE name = ?");
                ps.setString(1, pSearchBox.getText());
                //int id = Integer.parseInt(searchBox.getText());
                // ps.setInt(1, id);
                // Show_Products_In_JTable();
                rs = ps.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Found\nID : " + rs.getInt("id") + "\nName : " + rs.getString("name") + "\nPrice : " + Float.parseFloat(rs.getString("price")) + "\nUnit :" + rs.getInt("unit"));
                    pSearchBox.setText("Search By Name");
                } else {
                    JOptionPane.showMessageDialog(null, "Not Found");
                }
            } catch (Exception e) {
                JOptionPane.showConfirmDialog(null, "Failed", "Try Again!", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Product Not Not Found : Invalid Search!");
        }
    }//GEN-LAST:event_pSearchButtonActionPerformed

    private void pSearchButtonComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_pSearchButtonComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_pSearchButtonComponentShown

    private void pSearchButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pSearchButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pSearchButtonMouseEntered

    private void pBottomButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pBottomButtomActionPerformed
        pos = getProductList().size() - 1;
        ShowItem(pos);
    }//GEN-LAST:event_pBottomButtomActionPerformed

    private void pTopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pTopButtonActionPerformed
        pos = 0;

        ShowItem(pos);
    }//GEN-LAST:event_pTopButtonActionPerformed


    private void paddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paddButtonActionPerformed

        if (checkInputs() && ImgPath != null) {
            try {
                con = MySqlConnect.ConnectDB();
                PreparedStatement ps = con.prepareStatement("INSERT INTO products(name,price,unit,image,date,sales)"
                        + "values(?,?,?,?,?,?) ");
                ps.setString(1, pNameTextField.getText().toUpperCase());
                ps.setString(2, pPriceTextField.getText());
                ps.setString(3, pUnitTextField.getText());

                if (ImgPath != null) {
                    InputStream img = new FileInputStream(new File(ImgPath));
                    ps.setBlob(4, img);
                } else {
                    InputStream img = null;
                    ps.setBlob(4, img);
                }

                ps.setString(5, showDate());
                ps.setString(6, "0");
                ps.executeUpdate();
                Show_Products_In_JTable();
                Show_Sales_In_JTable_Sales();

                JOptionPane.showMessageDialog(null, "Product Enlisted");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "One Or More Field Are Empty");
        }
    }//GEN-LAST:event_paddButtonActionPerformed


    private void premoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_premoveButtonActionPerformed

        if (!(pIdTextField.getText().equals(""))) {
            try {
                con = MySqlConnect.ConnectDB();
                PreparedStatement ps = con.prepareStatement("DELETE FROM products WHERE id =?");
                int id = Integer.parseInt(pIdTextField.getText());
                ps.setInt(1, id);
                ps.executeUpdate();
                Show_Products_In_JTable();
                Show_Sales_In_JTable_Sales();
                JOptionPane.showMessageDialog(null, "Product Deleted");
                pClearButtonActionPerformed(evt);
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Product Could Not Deleted");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Select a product first");
        }
    }//GEN-LAST:event_premoveButtonActionPerformed

    private void pupdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pupdateButtonActionPerformed

        if (checkInputs() && pIdTextField.getText() != null) {
            con = MySqlConnect.ConnectDB();
            String UpdateQuery = null;
            PreparedStatement ps = null;

            if (ImgPath == null) {
                try {
                    UpdateQuery = "UPDATE products SET name = ?, price = ?"
                            + ", unit = ? ,date=? WHERE id = ?";
                    ps = con.prepareStatement(UpdateQuery);

                    ps.setString(1, pNameTextField.getText());
                    ps.setString(2, pPriceTextField.getText());
                    ps.setString(3, pUnitTextField.getText());
                    ps.setString(4, showDate());
                    ps.setInt(5, Integer.parseInt(pIdTextField.getText()));

                    ps.executeUpdate();
                    Show_Products_In_JTable();
                    Show_Sales_In_JTable_Sales();
                    JOptionPane.showMessageDialog(null, "Product Updated");

                } catch (SQLException ex) {
                    Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            } // update With Image
            else {
                try {
                    InputStream img = new FileInputStream(new File(ImgPath));

                    UpdateQuery = "UPDATE products SET name = ?, price = ?"
                            + ", unit = ?, image = ? , date=? WHERE id = ?";

                    ps = con.prepareStatement(UpdateQuery);

                    ps.setString(1, pNameTextField.getText());
                    ps.setString(2, pPriceTextField.getText());

                    ps.setString(3, pUnitTextField.getText());

                    ps.setBlob(4, img);
                    ps.setString(5, showDate());

                    ps.setInt(6, Integer.parseInt(pIdTextField.getText()));

                    ps.executeUpdate();
                    Show_Products_In_JTable();
                    JOptionPane.showMessageDialog(null, "Product Updated");

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "One Or More Fields Are Empty Or Invalid Input");
        }
    }//GEN-LAST:event_pupdateButtonActionPerformed


    private void JTable_ProductsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_ProductsMouseClicked

        int index = JTable_Products.getSelectedRow();
        ShowItem(index);
    }//GEN-LAST:event_JTable_ProductsMouseClicked

    private void pIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pIdTextFieldActionPerformed

    private void sIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sIdTextFieldActionPerformed

    private void JTable_SalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JTable_SalesMouseClicked

        int index = JTable_Sales.getSelectedRow();
        ShowSell(index);
    }//GEN-LAST:event_JTable_SalesMouseClicked

    private void pSaleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pSaleButtonActionPerformed

        // sellUnit = Integer.parseInt(sUnitTextField.getText());
        if (sIdTextField.getText().equals("") || sUnitTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Invalid Sell Request!");
        } else {
            try {
                con = MySqlConnect.ConnectDB();
                PreparedStatement ps1 = con.prepareStatement("SELECT *FROM products WHERE id=? and unit >= ? ");

                ps1.setString(1, sIdTextField.getText());
                ps1.setString(2, sUnitTextField.getText());
                rs = ps1.executeQuery();
                if (rs.next()) {
                    currentUnit = rs.getInt("unit");
                    float total = Integer.parseInt(sUnitTextField.getText()) * Float.parseFloat(sPriceTextField.getText());
                    sTotalTextField.setText(String.valueOf(total));

                    String dsc = String.valueOf(discountComboBox.getSelectedItem());
                    float discount = Float.parseFloat(dsc);
                    discount = (discount / 100) * total;
                    float netTotal = total - discount;

                    sNetTotalTextField.setText(String.valueOf(netTotal));
                    int sCon = JOptionPane.showConfirmDialog(null, "Payment Accepted!", "Confirmation", JOptionPane.YES_NO_OPTION);
                    if (sCon == 0) {
                        JOptionPane.showMessageDialog(null, "Product Sold!");

                        String UpdateQuery = null;
                        PreparedStatement ps = null;
                        try {
                            UpdateQuery = "UPDATE products SET  unit = ? , sales=? WHERE id = ?";
                            ps = con.prepareStatement(UpdateQuery);

                            int updatedUnit = currentUnit - Integer.parseInt(sUnitTextField.getText());
                            String upUnit = String.valueOf(updatedUnit);

                            ps.setString(1, upUnit);
                            float sum=getCurrentSales()+ Float.parseFloat(sNetTotalTextField.getText());
                            
                            ps.setString(2, String.valueOf(sum) );
                            ps.setInt(3, Integer.parseInt(sIdTextField.getText()));

                            ps.executeUpdate();
                            Show_Products_In_JTable();
                            Show_Sales_In_JTable_Sales();
                            totalSalesLabel.setText("Total:" + " " + String.valueOf(getSum()));

                        } catch (SQLException ex) {
                            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Purchase Cancelled!");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Product Not Avaialble", "Error!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_pSaleButtonActionPerformed

    }
    private void pTopButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pTopButton2ActionPerformed
        pos = 0;

        ShowSell(pos);
    }//GEN-LAST:event_pTopButton2ActionPerformed

    private void pBottomButtom2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pBottomButtom2ActionPerformed
        pos = getProductList().size() - 1;
        ShowSell(pos);
    }//GEN-LAST:event_pBottomButtom2ActionPerformed

    private void sClearButtonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sClearButtonMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_sClearButtonMouseEntered

    private void sClearButtonComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_sClearButtonComponentShown
        // TODO add your handling code here:
    }//GEN-LAST:event_sClearButtonComponentShown

    private void sClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sClearButtonActionPerformed
        // TODO add your handling code here:
        sNameTextField.setText(null);
        sIdTextField.setText(null);
        sPriceTextField.setText(null);
        sUnitTextField.setText(null);
        sTotalTextField.setText(null);
        sNetTotalTextField.setText(null);
        discountComboBox.setSelectedIndex(0);
    }//GEN-LAST:event_sClearButtonActionPerformed

    private void productImageChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productImageChooserButtonActionPerformed

        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.images", "jpg", "png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = file.getSelectedFile();
            String path = selectedFile.getAbsolutePath();
            pImageBoxLabel.setIcon(ResizeImage(path, null));
            ImgPath = path;
        } else {
            System.out.println("No File Selected");
        }
    }//GEN-LAST:event_productImageChooserButtonActionPerformed

    private void pSearchBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pSearchBoxMouseClicked
        // TODO add your handling code here:
        pSearchBox.setText(null);
    }//GEN-LAST:event_pSearchBoxMouseClicked

    private void pSearchBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pSearchBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pSearchBoxActionPerformed

    private void discountComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_discountComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_discountComboBoxActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("C:\\Windows\\System32\\notepad.exe");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("C:\\Windows\\System32\\calc.exe");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        try {
            // TODO add your handling code here:
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            new MailBox().setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu1ActionPerformed


    }//GEN-LAST:event_jMenu1ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec("C:\\Windows\\System32\\Notepad.exe D:\\test.txt");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        try {
            // TODO add your handling code here:
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            BugReport br = new BugReport();
            br.setVisible(true);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void sNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sNameTextFieldActionPerformed

    private void jMenu1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu1MouseClicked
        try {
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            int exitConfirm = JOptionPane.showConfirmDialog(null, "Do You Really  Want To Sign Out!", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (exitConfirm == 0) {

                JOptionPane.showMessageDialog(null, "Confirmed!");
                EmployeeUI.this.setVisible(false);

                LogInPage lgn = new LogInPage();
                lgn.setVisible(true);
            }
            // TODO add your handling code here:
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenu1MouseClicked

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        new AUSTProject().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    /**/
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {

            //   UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
            UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            new EmployeeUI().setVisible(true);

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
            java.util.logging.Logger.getLogger(EmployeeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmployeeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmployeeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmployeeUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminUI().setVisible(true);

            }
        });
         */
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JTable_Products;
    private javax.swing.JTable JTable_Sales;
    private javax.swing.JComboBox<String> discountComboBox;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton pBottomButtom;
    private javax.swing.JButton pBottomButtom2;
    private javax.swing.JButton pClearButton;
    private javax.swing.JLabel pDateLabel;
    private javax.swing.JTextField pIdTextField;
    private javax.swing.JLabel pImageBoxLabel;
    private javax.swing.JTextField pNameTextField;
    private javax.swing.JTextField pPriceTextField;
    private javax.swing.JButton pSaleButton;
    private javax.swing.JTextField pSearchBox;
    private javax.swing.JButton pSearchButton;
    private javax.swing.JButton pTopButton;
    private javax.swing.JButton pTopButton2;
    private javax.swing.JTextField pUnitTextField;
    private javax.swing.JButton paddButton;
    private javax.swing.JButton premoveButton;
    private javax.swing.JButton productImageChooserButton;
    private javax.swing.JTabbedPane productsTab;
    private javax.swing.JButton pupdateButton;
    private javax.swing.JButton sClearButton;
    private javax.swing.JLabel sDateLabel;
    private javax.swing.JTextField sIdTextField;
    private javax.swing.JTextField sNameTextField;
    private javax.swing.JTextField sNetTotalTextField;
    private javax.swing.JTextField sPriceTextField;
    private javax.swing.JTextField sTotalTextField;
    private javax.swing.JTextField sUnitTextField;
    private javax.swing.JLabel totalSalesLabel;
    // End of variables declaration//GEN-END:variables
}
