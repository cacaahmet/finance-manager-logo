package nethesap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginScreen extends JFrame {

    public LoginScreen() {
        setTitle("NetHesap - Giriş");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Tam ekran başlat
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Ana pencere layout olarak BorderLayout kullanıyoruz.
        // Böylece arka plan resmi ortalanabilir.
        setLayout(new BorderLayout());

        // Ekran boyutunu alalım (tam ekran başlatıldığı için tam ekran boyutları)
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Arka plan resmi - assets klasöründen alınıyor
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/assets/login_background.png"));
        Image scaledImage = backgroundIcon.getImage().getScaledInstance(
                screenSize.width, screenSize.height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Arka plan label - BorderLayout ile kullanılacak
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setLayout(new GridBagLayout()); 
        // GridBagLayout ile form panelini ortalarız, pencere küçülse bile ortada kalır.

        // Form paneli
        int formWidth = 420;
        int formHeight = 300;
        JPanel formPanel = new JPanel(null);
        formPanel.setPreferredSize(new Dimension(formWidth, formHeight + 50));
        // +50 alt linkler için
        formPanel.setOpaque(false);
        // transparan olsun, arka plan görünür

        // LOGO - assets klasöründen alınıyor
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/assets/logo.png"));
        Image logoImage = logoIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setBounds((formWidth - 200) / 3, -70, 300, 300);
        formPanel.add(logoLabel);

        // Kullanıcı Adı
        JLabel userLabel = new JLabel("Kullanıcı Adı:");
        userLabel.setBounds(50, 140, 100, 25);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(userLabel);

        JTextField userText = new JTextField();
        userText.setBounds(160, 140, 180, 25);
        userText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(userText);

        // Şifre
        JLabel passLabel = new JLabel("Şifre:");
        passLabel.setBounds(50, 180, 100, 25);
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(passLabel);

        JPasswordField passText = new JPasswordField();
        passText.setBounds(160, 180, 180, 25);
        passText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(passText);

        // Firma adı
        JLabel firmaLabel = new JLabel("Firma Adı:");
        firmaLabel.setBounds(50, 220, 100, 25);
        firmaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(firmaLabel);

        JTextField firmaText = new JTextField();
        firmaText.setBounds(160, 220, 180, 25);
        firmaText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formPanel.add(firmaText);

        // Giriş Butonu
        JButton loginButton = new JButton("Giriş") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0,
                        new Color(0, 153, 255),
                        getWidth(),
                        getHeight(),
                        new Color(0, 102, 204));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }
        };

        loginButton.setBounds(160, 260, 180, 40);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setOpaque(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover efekti
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setForeground(new Color(255, 255, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setForeground(Color.WHITE);
            }
        });

        // Giriş işlemi
        loginButton.addActionListener(e -> {
            String kullaniciAdi = userText.getText();
            String sifre = new String(passText.getPassword());
            String firmaAdi = firmaText.getText();

            if (kullaniciAdi.isEmpty() || sifre.isEmpty() || firmaAdi.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurunuz.");
            } else {
                new AnaMenu();
                dispose();
            }
        });
        formPanel.add(loginButton);

        // Alt Seçenekler: Şifremi Unuttum
        JLabel linkLabel = new JLabel("<HTML><U>Şifremi Unuttum</U></HTML>");
        linkLabel.setForeground(Color.BLUE);
        linkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        linkLabel.setBounds(200, 310, 120, 25);
        formPanel.add(linkLabel);

        // Şifre sıfırlama tıklama olayı
        linkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Şifre sıfırlama ekranına yönlendiriliyorsunuz.");
            }
        });

        // Form panelini arka plan label'in ortasına yerleştiriyoruz
        backgroundLabel.add(formPanel);

        // Arka plan label'i pencerenin içerik paneli olarak ayarla
        setContentPane(backgroundLabel);

        // Pencere boyutu ve görünürlük
        pack();
        // Preferred size hesaplanır (formPanel'in preferredSize'si ile uyumlu)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Tam ekran başlat
        setLocationRelativeTo(null);
        // Ekran ortalama (tam ekran olunca etkisi yok)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}