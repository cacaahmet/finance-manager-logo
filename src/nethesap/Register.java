package nethesap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Register extends JFrame {

    private JTextField kullaniciAdiText;
    private JPasswordField sifreText;
    private JTextField firmaAdiText;
    private JButton kayitButton;
    private JButton geriDonButton;

    public Register() {
        setTitle("NetHesap - Kayıt Ol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        //  Ekran boyutu ve arka plan 
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("login_background.png"));
        Image scaledImage = backgroundIcon.getImage().getScaledInstance(screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledImage));
        backgroundLabel.setLayout(new GridBagLayout());

        //  Form paneli
        int formWidth = 420;
        int formHeight = 300;
        JPanel formPanel = new JPanel(null);
        formPanel.setPreferredSize(new Dimension(formWidth, formHeight + 50));
        formPanel.setOpaque(false);

        // Başlık 
        JLabel titleLabel = new JLabel("Yeni Kullanıcı Kaydı");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(110, 60, 300, 30);
        formPanel.add(titleLabel);

        // Kullanıcı adı 
        JLabel kullaniciLabel = new JLabel("Kullanıcı Adı:");
        kullaniciLabel.setBounds(50, 120, 100, 25);
        kullaniciLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(kullaniciLabel);

        kullaniciAdiText = new JTextField();
        kullaniciAdiText.setBounds(160, 120, 180, 25);
        formPanel.add(kullaniciAdiText);

        //  Şifre
        JLabel sifreLabel = new JLabel("Şifre:");
        sifreLabel.setBounds(50, 160, 100, 25);
        sifreLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(sifreLabel);

        sifreText = new JPasswordField();
        sifreText.setBounds(160, 160, 180, 25);
        formPanel.add(sifreText);

        //  Firma adı 
        JLabel firmaLabel = new JLabel("Firma Adı:");
        firmaLabel.setBounds(50, 200, 100, 25);
        firmaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(firmaLabel);

        firmaAdiText = new JTextField();
        firmaAdiText.setBounds(160, 200, 180, 25);
        formPanel.add(firmaAdiText);

        //  Kayıt Ol
        kayitButton = new JButton("Kayıt Ol") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Yeşil degrade
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 204, 102),
                        getWidth(), getHeight(), new Color(0, 153, 76));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        kayitButton.setBounds(160, 250, 180, 40);
        kayitButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        kayitButton.setForeground(Color.WHITE);
        kayitButton.setFocusPainted(false);
        kayitButton.setContentAreaFilled(false);
        kayitButton.setBorderPainted(false);
        kayitButton.setOpaque(false);
        kayitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        kayitButton.addActionListener(e -> kullaniciKaydet());
        formPanel.add(kayitButton);

        //  Giriş Ekranına Dön
        geriDonButton = new JButton("Giriş Ekranına Dön") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

               
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0, 180, 255),       // Açık mavi
                        getWidth(), getHeight(), new Color(0, 255, 200)  // Su yeşili
                );
                g2.setPaint(gradient);

                // Yumuşak köşeli dikdörtgen çizimi
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                // Hafif gölge efekti
                g2.setColor(new Color(0, 0, 0, 40));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);

                super.paintComponent(g);
                g2.dispose();
            }
        };
        geriDonButton.setBounds(160, 300, 180, 35);
        geriDonButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        geriDonButton.setForeground(Color.WHITE);
        geriDonButton.setFocusPainted(false);
        geriDonButton.setContentAreaFilled(false);
        geriDonButton.setBorderPainted(false);
        geriDonButton.setOpaque(false);
        geriDonButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // LoginScreen'e dönüş
        geriDonButton.addActionListener(e -> {
            new LoginScreen().setVisible(true);
            dispose();
        });

        formPanel.add(geriDonButton);

        //  Arka plan ve form 
        backgroundLabel.add(formPanel);
        setContentPane(backgroundLabel);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Kullanıcı veritabanına kayıt işlemi
    private void kullaniciKaydet() {
        String kullaniciAdi = kullaniciAdiText.getText().trim();
        String sifre = new String(sifreText.getPassword()).trim();
        String firmaAdi = firmaAdiText.getText().trim();

        if (kullaniciAdi.isEmpty() || sifre.isEmpty() || firmaAdi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {

            // Kullanıcı adı mevcut olan kontrolü
            String kontrolSQL = "SELECT * FROM kullanici WHERE kullanici_adi = ?";
            PreparedStatement kontrolPst = conn.prepareStatement(kontrolSQL);
            kontrolPst.setString(1, kullaniciAdi);
            ResultSet rs = kontrolPst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Bu kullanıcı adı zaten mevcut!");
                return;
            }

            // Yeni kullanıcı ekle
            String sql = "INSERT INTO kullanici (kullanici_adi, sifre, firma_adi) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kullaniciAdi);
            pst.setString(2, sifre);
            pst.setString(3, firmaAdi);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "✅ Kayıt başarıyla oluşturuldu!");
            new LoginScreen().setVisible(true);
            dispose();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage());
        }
    }

    //  Uygulamayı test etmek için ana metot
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Register::new);
    }
}