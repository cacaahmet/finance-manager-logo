package nethesap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;

public class AnaMenu extends JFrame {

    // === ALANLAR ===
    private JPanel sideMenu, menuLabelPanel, overlayPanel;
    private JLabel menuTextLabel;
    private JButton exitButton, homeButton;
    private boolean isMenuOpen = false;
    private Timer menuTimer;
    private int menuWidth = 200, menuSpeed = 20;

    // Orantılı küçültme için referans boyutlar
    private Dimension originalWindowSize;
    private List<Component> originalComponents = new ArrayList<>();
    private List<Rectangle> originalBounds = new ArrayList<>();

    public AnaMenu() {
        // === PENCERE AYARLARI ===
        setTitle("NetHesap - Ana Menü");
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maxBounds = ge.getMaximumWindowBounds(); // Görev çubuğu dikkate alınır
        setMaximizedBounds(maxBounds);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ilk açılışta tam ekran
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        originalWindowSize = maxBounds.getSize();

        // === ARKA PLAN RESMİ ===
        JLabel backgroundLabel = createBackground(maxBounds);

        // === YAN MENÜ (gradient arka plan) ===
        sideMenu = new GradientPanel(
                new Color(0, 0, 128, 200),
                new Color(0, 128, 255, 200),
                new Color(0, 206, 209, 200)
        );
        sideMenu.setBounds(-menuWidth, 0, menuWidth, maxBounds.height);
        sideMenu.setLayout(null);

        // Çıkış & Home butonları
        exitButton = createMenuButton("exit.png", 20, maxBounds.height - 80, e -> System.exit(0));
        homeButton = createMenuButton("home.png", 20, maxBounds.height - 130, e -> {
            toggleMenu(false);
            JOptionPane.showMessageDialog(this, "Ana menü ekranı açıldı!");
        });
        sideMenu.add(exitButton);
        sideMenu.add(homeButton);

        // === OVERLAY PANEL (yan menü dışını karartır) ===
        overlayPanel = createOverlay(maxBounds);

        // === MENÜ BUTONU PANELİ (ikon + yazı) ===
        menuLabelPanel = createMenuLabel();

        // === ARAMA KUTUSU ===
        JPanel topWrapper = createSearchBox(maxBounds);

        // === KISAYOL PANELİ ===
        JPanel shortcutPanel = createShortcutPanel(maxBounds);

        // === BİLEŞENLERİ EKLE ===
        backgroundLabel.add(sideMenu);
        backgroundLabel.add(overlayPanel);
        backgroundLabel.add(menuLabelPanel);
        backgroundLabel.add(topWrapper);
        backgroundLabel.add(shortcutPanel);

        setContentPane(backgroundLabel);

        // === ORANTILI BOYUTLANDIRMA ===
        storeOriginalBounds(backgroundLabel);
        addResizeListener();
    	
        setVisible(true); // ana menüden geçiş
    }

    // === ARKA PLAN ===
    private JLabel createBackground(Rectangle maxBounds) {
        ImageIcon bgIcon = new ImageIcon(getClass().getResource("/assets/login_background.png"));
        Image scaledImage = bgIcon.getImage().getScaledInstance(maxBounds.width, maxBounds.height, Image.SCALE_SMOOTH);
        JLabel bg = new JLabel(new ImageIcon(scaledImage));
        bg.setBounds(0, 0, maxBounds.width, maxBounds.height);
        bg.setLayout(null);
        return bg;
    }

    // === MENÜDEKİ ÇIKIŞ/HOME BUTONU ===
    private JButton createMenuButton(String iconName, int x, int y, ActionListener action) {
        ImageIcon raw = new ImageIcon(getClass().getResource("/assets/" + iconName));
        Image scaled = raw.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        JButton btn = new JButton(new ImageIcon(scaled));
        btn.setBounds(x, y, 40, 40);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setVisible(false);
        btn.addActionListener(action);
        return btn;
    }

    // === OVERLAY PANEL ===
    private JPanel createOverlay(Rectangle maxBounds) {
        JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 50));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlay.setBounds(menuWidth, 0, maxBounds.width - menuWidth, maxBounds.height);
        overlay.setOpaque(false);
        overlay.setVisible(false);
        overlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isMenuOpen) toggleMenu(false);
            }
        });
        return overlay;
    }

    // === MENÜ BUTONU PANELİ ===
    private JPanel createMenuLabel() {
        ImageIcon raw = new ImageIcon(getClass().getResource("/assets/menu.png"));
        Image scaled = raw.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JPanel panel = new RoundedPanel(15, new Color(0, 0, 0, 120));
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panel.setBounds(20, 20, 120, 40);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(new ImageIcon(scaled));
        menuTextLabel = new JLabel("Menü");
        menuTextLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        menuTextLabel.setForeground(Color.WHITE);

        panel.add(iconLabel);
        panel.add(menuTextLabel);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleMenu(!isMenuOpen);
            }
        });
        return panel;
    }

    // === ARAMA KUTUSU ===
    private JPanel createSearchBox(Rectangle maxBounds) {
        JPanel searchBox = new JPanel(new BorderLayout());
        searchBox.setBackground(Color.WHITE);
        searchBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        searchBox.setPreferredSize(new Dimension(400, 40));

        ImageIcon searchIconRaw = new ImageIcon(getClass().getResource("/assets/search.png"));
        Image searchIcon = searchIconRaw.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        JLabel searchIconLabel = new JLabel(new ImageIcon(searchIcon));
        searchIconLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 6));

        JTextField searchField = new JTextField("Arama Yapın");
        searchField.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 6));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Arama Yapın")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Arama Yapın");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        searchBox.add(searchIconLabel, BorderLayout.WEST);
        searchBox.add(searchField, BorderLayout.CENTER);

        JPanel topWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        topWrapper.setOpaque(false);
        topWrapper.setBounds(0, 80, maxBounds.width, 40);
        topWrapper.add(searchBox);

        return topWrapper;
    }

    // === KISAYOL PANELİ ===
    private JPanel createShortcutPanel(Rectangle maxBounds) {
        RoundedPanel panel = new RoundedPanel(30, new Color(255, 255, 255, 60));
        panel.setLayout(new BorderLayout());
        panel.setBounds(maxBounds.width / 2 - 450, 150, 850, 600);

        JLabel title = new JLabel("Kısayollarım", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        panel.add(title, BorderLayout.NORTH);

        JPanel row = new JPanel(new GridLayout(1, 4, 40, 0));
        row.setOpaque(false);
        row.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        String[][] data = {
                {"Kısayol Ekle", "apps.png"},
                {"Cari Hesap Ekstresi", "currentaccount.png"},
                {"Banka Hesap Ekstresi", "creditcards.png"},
                {"Kasa Ekstresi", "cashregister.png"}
        };

        for (String[] d : data) {
            JButton btn = createAnimatedButton(loadIcon(d[1], 48));
            btn.setPreferredSize(new Dimension(80, 80));
            
            btn.addActionListener(e -> {
                String secim = d[0]; // butonun adı

                if (secim.equals("Cari Hesap Ekstresi")) {
                    new CariHesap(); 
                    dispose(); // mevcut pencereyi kapat
                } else if (secim.equals("Banka Hesap Ekstresi")) {
                    new BankaHesap();
                    dispose();
                } else if (secim.equals("Kasa Ekstresi")) {
                    new KasaHesap();
                    dispose();
                }
            });

            JPanel btnPanel = new JPanel(new BorderLayout());
            btnPanel.setOpaque(false);
            btnPanel.add(btn, BorderLayout.CENTER);

            JLabel lbl = new JLabel(d[0], SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            lbl.setForeground(Color.WHITE);
            lbl.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

            btnPanel.add(lbl, BorderLayout.SOUTH);
            row.add(btnPanel);
        }

        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerWrapper.setOpaque(false);
        centerWrapper.add(row);
        panel.add(centerWrapper, BorderLayout.CENTER);

        return panel;
    }

    private ImageIcon loadIcon(String name, int size) {
        ImageIcon raw = new ImageIcon(getClass().getResource("/assets/" + name));
        return new ImageIcon(raw.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    // === MENÜ AÇMA/KAPAMA ===
    private void toggleMenu(boolean open) {
        if (menuTimer != null && menuTimer.isRunning())
            menuTimer.stop();

        isMenuOpen = open;

        menuTimer = new Timer(10, e -> {
            int x = sideMenu.getX();
            if (open) {
                overlayPanel.setVisible(true);
                exitButton.setVisible(true);
                homeButton.setVisible(true);
                if (x < 0)
                    sideMenu.setLocation(Math.min(0, x + menuSpeed), 0);
                else
                    ((Timer) e.getSource()).stop();
            } else {
                if (x > -menuWidth)
                    sideMenu.setLocation(Math.max(-menuWidth, x - menuSpeed), 0);
                else {
                    overlayPanel.setVisible(false);
                    exitButton.setVisible(false);
                    homeButton.setVisible(false);
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        menuTimer.start();
    }

    // === ANİMASYONLU BUTON ===
    private JButton createAnimatedButton(ImageIcon icon) {
        JButton button = new JButton(icon);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        final int normal = 48, hover = (int) (normal * 1.15), step = 2, delay = 15;
        final int[] current = {normal};

        Timer grow = new Timer(delay, null), shrink = new Timer(delay, null);
        grow.addActionListener(e -> resizeIcon(button, icon, current, hover, step, grow));
        shrink.addActionListener(e -> resizeIcon(button, icon, current, normal, -step, shrink));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                shrink.stop();
                grow.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                grow.stop();
                shrink.start();
            }
        });
        return button;
    }

    private void resizeIcon(JButton btn, ImageIcon base, int[] current, int target, int step, Timer timer) {
        if ((step > 0 && current[0] < target) || (step < 0 && current[0] > target)) {
            current[0] += step;
            Image img = base.getImage().getScaledInstance(current[0], current[0], Image.SCALE_SMOOTH);
            btn.setIcon(new ImageIcon(img));
        } else timer.stop();
    }

    // === BOYUTLANDIRMA ===
    private void storeOriginalBounds(JLabel backgroundLabel) {
        SwingUtilities.invokeLater(() -> {
            for (Component c : backgroundLabel.getComponents()) {
                originalComponents.add(c);
                originalBounds.add(c.getBounds());
            }
        });
    }

    private void addResizeListener() {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                double sx = getWidth() / (double) originalWindowSize.width;
                double sy = getHeight() / (double) originalWindowSize.height;
                for (int i = 0; i < originalComponents.size(); i++) {
                    Rectangle b = originalBounds.get(i);
                    originalComponents.get(i).setBounds(
                            (int) (b.x * sx),
                            (int) (b.y * sy),
                            (int) (b.width * sx),
                            (int) (b.height * sy)
                    );
                }
            }
        });
    }

    // === YUVARLATILMIŞ PANEL ===
    class RoundedPanel extends JPanel {
        private int cornerRadius;
        private Color bgColor;

        public RoundedPanel(int r, Color c) {
            cornerRadius = r;
            bgColor = c;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor != null ? bgColor : getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        }
    }

    // === GRADIENT PANEL ===
    class GradientPanel extends JPanel {
        private Color c1, c2, c3;

        public GradientPanel(Color a, Color b, Color c) {
            c1 = a;
            c2 = b;
            c3 = c;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp1 = new GradientPaint(0, 0, c1, getWidth(), getHeight() / 2, c2);
            GradientPaint gp2 = new GradientPaint(0, getHeight() / 2, c2, getWidth(), getHeight(), c3);
            g2.setPaint(gp1);
            g2.fillRect(0, 0, getWidth(), getHeight() / 2);
            g2.setPaint(gp2);
            g2.fillRect(0, getHeight() / 2, getWidth(), getHeight());
        }
    }

    // === MAIN ===
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AnaMenu().setVisible(true));
    }
}