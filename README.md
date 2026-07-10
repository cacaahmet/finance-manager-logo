<div align="center">

<img width="100" height="100" alt="logo" src="https://github.com/user-attachments/assets/29716488-dcc3-489d-a3de-918172a39160" />

# 💼 NetHesap

**Masaüstü Muhasebe ve Cari Hesap Yönetim Sistemi**

Java & Swing ile geliştirilmiş, SQL Server destekli, sade ve kullanıcı dostu bir muhasebe otomasyonu.

[![Java](https://img.shields.io/badge/Java-17+-orange.svg?logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-blue.svg)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![SQL Server](https://img.shields.io/badge/Database-SQL%20Server-CC2927.svg?logo=microsoftsqlserver&logoColor=white)](https://www.microsoft.com/sql-server)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](#lisans)

</div>

---

## 📖 Proje Hakkında

**NetHesap**, küçük ve orta ölçekli işletmelerin temel finansal süreçlerini — cari hesap, banka hesabı ve kasa hareketlerini — tek bir masaüstü uygulaması üzerinden takip edebilmesi amacıyla geliştirilmiş bir muhasebe yazılımıdır.

Proje, **Logo Yazılım** ekosisteminden ilham alınarak; karmaşık kurumsal ERP çözümlerinin aksine, **sade, hızlı ve anlaşılır** bir kullanıcı deneyimi sunmak hedefiyle tasarlanmıştır. Amaç, işletmelerin müşteri bilgilerini güvenli bir şekilde saklayabildiği, finansal hareketlerini düzenli olarak izleyebildiği ve ihtiyaç anında hızlıca raporlayabildiği hafif bir masaüstü çözümü ortaya koymaktır.

## ✨ Özellikler

| Modül | Açıklama |
|---|---|
| 🔐 **Giriş & Kayıt Sistemi** | Kullanıcı adı, şifre ve firma adı ile güvenli giriş; yeni kullanıcı kaydı ve benzersizlik kontrolü |
| 🏠 **Ana Menü** | Kısayollar paneli, hızlı arama kutusu ve modüller arası pratik geçiş |
| 📊 **Cari Hesap Ekstresi** | Müşteri seçimi, tarih aralığına göre filtreleme ve hesap hareketi sorgulama |
| 🏦 **Banka Hesap Ekstresi** | Banka bazlı hesap hareketlerinin takibi ve raporlanması |
| 💰 **Kasa Ekstresi** | Nakit giriş-çıkış hareketlerinin kayıt altına alınması ve görüntülenmesi |
| 🎨 **Uyarlanabilir Arayüz** | Tam ekran / pencere modlarında bozulmadan çalışan responsive form yerleşimi |

## 🛠️ Kullanılan Teknolojiler

- **Java** — Nesne yönelimli programlama yaklaşımıyla yapılandırılmış, platform bağımsız uygulama mantığı
- **Java Swing** — Özel `GradientPaint`, yuvarlatılmış köşeler ve saydamlık efektleriyle modern masaüstü arayüzü
- **Microsoft SQL Server** — Müşteri, hesap ve finansal kayıtların saklandığı ilişkisel veritabanı
- **Microsoft JDBC Driver** (`mssql-jdbc`) — Uygulama ile SQL Server arasındaki veri iletişim katmanı
- **jBCrypt** — Kullanıcı şifrelerinin geri döndürülemez biçimde hash'lenerek güvenli saklanması
- **Eclipse IDE** — Geliştirme, derleme ve hata ayıklama ortamı

## 🧱 Proje Mimarisi

```
nethesap/
├── LoginScreen.java     # Giriş ekranı ve kimlik doğrulama arayüzü
├── Register.java        # Yeni kullanıcı kayıt ekranı
├── AnaMenu.java         # Merkezi menü ve kısayollar paneli
├── CariHesap.java       # Cari hesap ekstresi ekranı
├── BankaHesap.java      # Banka hesap ekstresi ekranı
├── KasaHesap.java       # Kasa ekstresi ekranı
├── DBConnection.java    # SQL Server bağlantı yönetimi
└── TestDB.java          # Veritabanı bağlantı testi
```

Uygulama, her ekranın kendi sınıfında yönetildiği modüler bir yapı izler; bu sayede kod okunabilirliği, hata ayıklama ve yeni özellik eklenebilirliği artırılmıştır.

## 🖥️ Kullanıcı Arayüzü (Ana Menü)

Bu uygulama bir **staj projesi** kapsamında hayata geçirilmiştir. Aşağıda uygulamanın şu anki temel ana ekran (dashboard) arayüzünü görebilirsiniz. 

Sade, modern ve kullanıcıyı yormayan bir tasarım diliyle oluşturulan bu temel görünüm; projenin ilerleyen aşamalarında yeni modüller, dinamik pencereler ve ek özelliklerle **geliştirilmeye devam edecektir**.

<div align="center">
  
  <img width="1919" height="1025" alt="anasayfa" src="https://github.com/user-attachments/assets/711c956e-ca4b-4b54-9e42-4f8d6faf8ee2" />
  
  <br>
  <p><i>Uygulamanın Temel Ana Ekran Görünümü</i></p>
  
</div>


## 🗄️ Veritabanı Şeması

`NETHESAP` veritabanı aşağıdaki tablolardan oluşur:

| Tablo | Amaç |
|---|---|
| `kullanici` | Kullanıcı hesap bilgileri (kullanıcı adı, hash'lenmiş şifre, firma adı) |
| `cari_hesap` / `cari_hareket` | Müşteri cari hesap kayıtları ve hareket geçmişi |
| `banka_hesap` / `banka_hareket` | Banka hesapları ve işlem hareketleri |
| `kasa` / `kasa_hareket` | Kasa bilgileri ve nakit hareketleri |

Bağlantı, `localhost:1433` üzerinde çalışan bir SQL Server örneğine `DriverManager` aracılığıyla kurulur.

## 🚀 Kurulum

1. Depoyu klonlayın:
   ```bash
   git clone https://github.com/cacaahmet/finance-manager-logo.git
   ```
2. Projeyi Eclipse IDE (veya tercih ettiğiniz Java IDE) üzerinde açın.
3. `mssql-jdbc` ve `jbcrypt` `.jar` bağımlılıklarını proje build path'ine ekleyin.
4. SQL Server üzerinde `NETHESAP` veritabanını oluşturun ve gerekli tabloları çalıştırın.
5. `DBConnection.java` içindeki bağlantı bilgilerini (host, kullanıcı adı, şifre) kendi ortamınıza göre güncelleyin.
6. `LoginScreen.java` sınıfını çalıştırarak uygulamayı başlatın.

## 🔭 Vizyon ve Gelecek Planları

NetHesap, şu an için temel düzeyde bir muhasebe uygulamasıdır. Planlanan geliştirmeler şunlardır:

- 🧾 Fatura yönetimi modülü
- 📦 Stok takip sistemi
- 📈 Gelişmiş raporlama araçları
- 🔑 Kullanıcı yetkilendirme (rol bazlı erişim) sistemi
- 🔒 Veritabanı işlemlerinde artırılmış güvenlik katmanları

Uzun vadeli hedef, NetHesap'ı küçük işletmelerin günlük muhasebe ihtiyaçlarına gerçek anlamda cevap verebilen, genişletilebilir ve profesyonel bir yazılıma dönüştürmektir.

## 🤝 Katkıda Bulunma

Katkılarınızı memnuniyetle karşılarız! Bir *issue* açabilir veya *pull request* gönderebilirsiniz.


---

<div align="center">
Made with ☕ and Java Swing
</div>
