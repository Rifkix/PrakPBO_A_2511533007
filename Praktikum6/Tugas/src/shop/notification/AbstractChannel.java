package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount;
    private String lastDelivered = "";

    @Override
    public final boolean send(CustomerContact contact, String message) {
        // 1. tolak pesan kosong
        if (contact == null || message == null || message.isBlank()) {
            return false;
        }
        // 2. cari alamat tujuan (null = kontak tidak cocok dengan kanal ini)
        String recipient = resolveRecipient(contact);
        if (recipient == null) {
            return false;
        }
        // 3. siapkan pesan
        String prepared = prepare(message);
        // 4. kirim
        if (!deliver(recipient, prepared)) {
            return false;
        }
        // 5. catat
        sentCount++;
        lastDelivered = prepared;
        return true;
    }

    public int getSentCount() {
        return sentCount;
    }

    /** Pesan terakhir yang berhasil dikirim, setelah disiapkan. */
    public String getLastDelivered() {
        return lastDelivered;
    }

    /** @return alamat tujuan, atau null jika kontak tidak punya alamat yang cocok */
    protected abstract String resolveRecipient(CustomerContact contact);

    /** Mengirim pesan yang sudah disiapkan. */
    protected abstract boolean deliver(String recipient, String message);

    /** Hook: secara default pesan tidak diubah. Boleh di-override. */
    protected String prepare(String message) {
        return message;
    }
}