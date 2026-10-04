package Pekan4;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Rekening> daftarRekening = new ArrayList();
		Rekening akunAktif = null; //Objek belum diinisialisasi (null)
		boolean isRunning = true;
		
		System.out.println("=== SISTEM PERBANKAN MINI ===");
		
		while (isRunning) {
			System.out.println("\nMenu Utama:");
			System.out.println("1. Buka Rekening Baru");
			System.out.println("2. Setor Tunai");
			System.out.println("3. Tarik Tunai");
			System.out.println("4. Cek Informasi Rekening");
			System.out.println("5. Ganti Akun");
			System.out.println("6. Cetak Mutasi (Riwayat");
			System.out.println("7. Simulasi Akhir Bulan (Khusus Tabungan)");
			System.out.println("0. Keluar");
			System.out.print("Pilih menu: ");
			
			int pilihan = input.nextInt();
			input.nextLine(); // Membersihkan buffer enter
			
			switch (pilihan) {
			case 1:
				System.out.print("Masukkan No Rekening: ");
				String no = input.nextLine();
				System.out.print("Masukkan Nama Pemilik: ");
				String nama = input.nextLine();
				System.out.print("Masukkan PIN(6 digit angka): ");
				String pin = input.nextLine();
				System.out.print("Masukkan Saldo Awal: ");
				double saldo = input.nextDouble();
				System.out.println("Pilih Produk(1/2): \n1. Tabungan Umum \n2. Giro Bisnis");
				int pilProduk = input.nextInt();
				
				if (saldo >= 50000) {
					if (pilProduk == 1) {
						System.out.println("Masukkan suku bunga(dalam persen): ");
						double sukuBunga = input.nextDouble();
						akunAktif = new RekeningTabungan(no, nama, saldo, pin, sukuBunga);
					}
					else if (pilProduk == 2) {
						System.out.println("Masukkan batas overdraft(limit pinjaman): ");
						double batasOverdraft = input.nextDouble();
						akunAktif = new RekeningGiro(no, nama, saldo, pin, batasOverdraft);
					}
					else {
						System.out.println("pilih antara 1 dan 2");
					}
				} 
				else {
					System.out.println("saldo tidak boleh kurang dari 50.000");
				}
				break;
			
			case 2:
				if (akunAktif == null) {
					System.out.println("Error: Mohon maaf, Anda belum memiliki nomor rekening!");				
				} else {
					System.out.print("Masukkan nominal setor: ");
					double setor = input.nextDouble();
					akunAktif.setorTunai(setor); // Memanggil Behavior / method
				}
				break;
				
			case 3:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum memiliki nomor rekening");
				} else {
					System.out.println("Masukkan PIN: ");
					String inputPin = input.nextLine();
					if (akunAktif.otentikasi(inputPin) == true) {
						System.out.println("Masukkan nominal tarik tunai: ");
						double tarik = input.nextDouble();
						akunAktif.tarikTunai(tarik);
					} else {
						System.out.println("Akses DItolak: PIN yang Anda masukkan salah!");
					}
				}
				break;
				
			case 4:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum membuka rekening!");
				} else {
					akunAktif.cekInformasi();
				}
				break;
				
			case 5:
				System.out.println("Masukkan No Rekening Akun yang dicari:");
				String norek = input.nextLine();
				boolean ditemukan = false;
				
				for (Rekening rek : daftarRekening) {
					if (rek.getNomorRekening().equals(norek)) {
						akunAktif = rek;
						ditemukan = true;
						System.out.println("Berhasil Akun aktif saat ini atas nama: " + rek.getNamaPemilik());
						break;
					}
				}
				
				if (!ditemukan) {
					System.out.println("Error: Nomor rekening tidak ditemukan");
				}
				break;
			
			case 6:
				if (akunAktif == null) {
					System.out.println("Error: Anda belum memiliki nomor rekening");
				} else {
					System.out.println("Masukkan PIN: ");
					String inputPin = input.nextLine();
					if (akunAktif.otentikasi(inputPin) == true) {
						akunAktif.cetakMutasi();
					} else {
						System.out.println("Akses Ditolak: PIN yang Anda masukkan salah!");
					}
				}
				break;
				
			case 7:
				if (akunAktif != null) {
					if (akunAktif instanceof RekeningTabungan) {
						RekeningTabungan rek = (RekeningTabungan) akunAktif;
						rek.tambahBungaAkhirBulan();
					}
					else {
						System.out.println("Gagal: Fitur bunga akhir bulan hanya berlaku untuk Rekening Tabungan.");
					}
				}
				else {
					System.out.println("Akun belum aktif");
				}
				break;
				
			case 0:
				isRunning = false;
					System.out.println("Sistem ditutup. Terima Kasih!");
				break;
				
			default:
				System.out.println("Pilihan tidak valid!");
			}
			
		}
		input.close();

	}

}
