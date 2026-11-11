package shop.app;

import java.util.List;
import shop.StudentInfo;
import shop.model.DigitalProduct;
import shop.model.PhysicalProduct;
import shop.model.Product;
import shop.notification.CustomerContact;
import shop.notification.EmailChannel;
import shop.notification.NotificationChannel;
import shop.notification.NotificationService;
import shop.notification.SmsChannel;
import shop.notification.WhatsAppChannel;
import shop.payment.BankTransferPayment;
import shop.payment.CreditCardPayment;
import shop.payment.EWalletPayment;
import shop.payment.PaymentMethod;
import shop.service.CheckoutService;
import shop.shipping.ExpressShipping;
import shop.shipping.ShippingService;

public class TugasApp {
    public static void main(String[] args) {
        System.out.println("Tugas oleh " + StudentInfo.NAME + " (" + StudentInfo.NIM + ")");

        List<Product> cart = List.of(
                new PhysicalProduct("KOPI-001", "Kopi Arabika Gayo 250g", 85_000, 300),
                new PhysicalProduct("RDG-002", "Rendang Kemasan 250g", 120_000, 400),
                new DigitalProduct("EBK-003", "E-book Resep Kue Nusantara", 50_000,
                        "https://toko-umkm.example/unduh/ebk-003"));
        CheckoutService checkout = new CheckoutService();
        ShippingService express = new ExpressShipping();

        EWalletPayment wallet = new EWalletPayment("DompetKu", 300_000);
        PaymentMethod transfer = new BankTransferPayment("Bank Nagari");
        CreditCardPayment card = new CreditCardPayment("Ando", 1_000_000);

        System.out.println("\n== T1: Total dengan biaya admin (Ekspres) ==");
        printTotal(checkout, cart, express, wallet);
        printTotal(checkout, cart, express, transfer);
        printTotal(checkout, cart, express, card);

        System.out.println("\nKebijakan kartu kredit : " + card.policy());
        System.out.println("Kebijakan e-wallet     : " + wallet.policy());

        NotificationService service = new NotificationService();
        service.register(new EmailChannel());
        service.register(new SmsChannel(StudentInfo.SMS_MAX_LENGTH));
        service.register(new WhatsAppChannel());
        service.register(new NotificationChannel() {
            @Override
            public String channelName() {
                return "LOG";
            }

            @Override
            public boolean send(CustomerContact contact, String message) {
                if (contact == null || message == null || message.isBlank()) {
                    return false;
                }
                System.out.println("[LOG] " + contact.getName() + ": " + message);
                return true;
            }
        });

        String message = "Pesanan ORD-0001 sudah dikirim dengan kurir Ekspres. "
                + "Nomor resi akan muncul di aplikasi dalam 1x24 jam. "
                + "Terima kasih telah berbelanja produk UMKM Sumatera Barat di toko kami! "
                + "Jika ada kendala, silakan hubungi layanan pelanggan kami.";

        CustomerContact andi = new CustomerContact("Andi", "andi@example.com", "081234567890");
        CustomerContact andu = new CustomerContact("Andu", "", "+6281298765432");

        System.out.println("\n== T2: Notifikasi status pesanan ==");
        notifyCustomer(service, andi, message);
        notifyCustomer(service, andu, message);
    }

    private static void printTotal(CheckoutService checkout, List<Product> cart,
            ShippingService shipping, PaymentMethod payment) {
        long grand = checkout.grandTotal(cart, shipping, payment);
        long fee = payment.adminFee(checkout.total(cart, shipping));
        System.out.println(payment.getName() + " : Rp" + grand + " (admin Rp" + fee + ")");
    }

    private static void notifyCustomer(NotificationService service, CustomerContact contact, String message) {
        int channels = service.broadcast(contact, message);
        String firstName = contact.getName().split(" ")[0];
        System.out.println("Terkirim ke " + firstName + " lewat " + channels + " kanal");
    }
}