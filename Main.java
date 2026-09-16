package Pekan1;
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
				System.out.print("Masukkan Saldo Awal: ");
				double saldo = input.nextDouble();
				if (saldo < 50000) {
					System.out.println("saldo kurang");
				} else {
					akunAktif = new Rekening(no, nama, saldo);
					daftarRekening.add(akunAktif);
					break;
				}
			
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
					System.out.println("Masukkan nominal tarik tunai: ");
					double tarik = input.nextDouble();
					akunAktif.tarikTunai(tarik);
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
					if (rek.nomorRekening.equals(norek)) {
						akunAktif = rek;
						ditemukan = true;
						System.out.println("Berhasil Akun aktif saat ini atas nama: " + rek.namaPemilik);
						break;
					}
				}
				
				if (!ditemukan) {
					System.out.println("Error: Nomor rekening tidak ditemukan");
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
