package shop;

/**
 * Identity and personal parameters for the take-home assignment (Tugas).
 * 1. Write your NIM and name.
 * 2. Run: java -cp out tools.NimParams <your NIM>
 * 3. Copy the three printed values below.
 */
public final class StudentInfo {
    public static final String NIM = "2511533007";   // TODO: e.g. "2401010057"
    public static final String NAME = "Rifki Maulana";  // TODO: your full name

    public static final int CARD_ADMIN_PERCENT = 2;   // TODO: from tools.NimParams
    public static final long TRANSFER_ADMIN_FEE = 3500;  // TODO: from tools.NimParams
    public static final int SMS_MAX_LENGTH = 107;       // TODO: from tools.NimParams

    private StudentInfo() {
        // constants only; no objects
        
    }
}
