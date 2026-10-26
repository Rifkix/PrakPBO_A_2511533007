package Pekan5;

public class RekeningGiro extends Rekening{
	private double batasOverdraft;
	
	public RekeningGiro(String nomor, String nama, double saldoAwal, String pinAwal, double batasOverdraft ) {
		// Memanggil inisialisasi dasar dari Superclass
		super(nomor, nama, saldoAwal, pinAwal);
		this.batasOverdraft = batasOverdraft;
	}
	
	// implementasi polimorfisme overriding
	@Override
	public boolean tarikTunai(double nominal) {
		if (nominal <= 0) {
			System.out.println("Gagal: Nominal tarik Giro harus lebih dari 0!");
			return false;
		}
		// Logika khusus giro: Boleh ditarik asalkan nominal tidak melebihi (saldo + batasOverdraft)
		else if (nominal > (saldo + batasOverdraft)) {
			System.out.println("Transaksi Gagal: melebihi batas Overdraft! Maksimal penarikan: Rp" + (saldo + batasOverdraft));
			return false;
		}
		else {
			saldo -= nominal; // saldo bisa bernilai negatif disini (memakan overdraft)
			
			String idTrx = "TRX-G-" + System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx, "Debit Giro", nominal));
			
			System.out.println("Tarik tunai giro Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
			return true;
		}
	}
	
	// Getter khusus Giro
	public double getBatasOverdraft() {
		return batasOverdraft;
	}
	// penarikan hingga limit overdraft diselesaikan di modul 5
}
