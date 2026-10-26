package Pekan5;
import java.util.ArrayList;

public class Rekening {
	// 1. Mengunci atribut dengan 'private'
	private String nomorRekening;
	private String namaPemilik;
	private String pin; // Data sensitif
	
	// Gunakan protected agar subclass bisa mengaksesnya langsung
	protected double saldo;
	protected ArrayList<Transaksi> riwayatTransaksi;
	
	// 2. Modifikasi Constructor untuk menerima PIN awal
	public Rekening(String nomor, String nama, double saldoAwal, String pinAwal) {
		this.nomorRekening = nomor;
		this.namaPemilik = nama;
		this.saldo = saldoAwal;
		// Validasi PIN di dalam Constructor
		if (pinAwal.length() == 6) {
			this.pin = pinAwal;
		} else {
			System.out.println("Peringatan: PIN harus 6 digit! Menggunakan PIN default 123456");
			this.pin = "123456";
		}
		// Wajib menginisialisasi ArrayList di dalam constructor agar tidak NullPointerException
		this.riwayatTransaksi = new ArrayList<>();
		System.out.println("Rekening atas nama " + namaPemilik + " berhasil dibuat.");
	}
	
	// 3. Getter untuk atribut yang diizinkan dibaca publik
	public String getNomorRekening() { return nomorRekening;}
	public String getNamaPemilik() { return namaPemilik;}
	
	// 4. Method otentikasi internal (Validasi Enkapsulasi)
	public boolean otentikasi(String inputPin) {
		return this.pin.equals(inputPin);
	}
	
	public void setorTunai(double nominal) {
		if (nominal >= 0) {
			saldo += nominal;
			// Merekam riwayat (Pembuatan objek Transaksi di dalam method)
			String idTrx = "TRX-S-" + System.currentTimeMillis();
			Transaksi trxBaru = new Transaksi(idTrx, "Kredit", nominal);
			riwayatTransaksi.add(trxBaru);
			
			System.out.println("Setor tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp " + saldo);
		} else {
			System.out.println("Gagal: Nominal setor harus lebih dari 0!");
		}
	}
	
	public void cekInformasi() {
		System.out.println("--- INFO REKENING ---");
		System.out.println("No. Rekening : " + nomorRekening);
		System.out.println("Nama Pemilik :" + namaPemilik);
		System.out.println("Saldo Akhir  : Rp" + saldo);
		System.out.println("---------------------");
	}
	
	public boolean tarikTunai(double nominal) {
		if (nominal <= 0) {
			System.out.println("Transaksi Gagal : minimal nominal penarikan 0");
			return false;
		} else if (nominal > saldo) {
			System.out.println("Transaksi Gagal: saldo tidak mencukupi. Saldo Anda: Rp" + saldo);
			return false;
		} else {
			saldo = saldo - nominal;
			
			String idTrx = "TRX-T-" + System.currentTimeMillis();
			riwayatTransaksi.add(new Transaksi(idTrx, "Debit", nominal));
			
			System.out.println("Tarik tunai Rp" + nominal + " berhasil. Saldo saat ini: Rp" + saldo);
			return true;
		}
	}
	
	public void transfer(Rekening tujuan, double nominal, String pinInput) {
		if (!otentikasi(pinInput)) {
			System.out.println("Transfer dibatalkan: PIN yang anda masukkan salah!");
			return;
		}
		
		boolean berhasil = this.tarikTunai(nominal);
		
		if (berhasil) {
			tujuan.setorTunai(nominal);
			System.out.println("Transfer Rp" + nominal + " ke " + tujuan.getNamaPemilik() + " berhasil");
		}
	}
	public void cetakMutasi() {
		System.out.println("--- Mutasi Rekening " + nomorRekening + " ---");
		if (riwayatTransaksi.isEmpty()) {
			System.out.println("Tidak ada transaksi pada rekening ini");
		} else {
			for (Transaksi transaksi : riwayatTransaksi) {
				transaksi.cetakDetail();
			}
			
		}
		
	}
	
}
