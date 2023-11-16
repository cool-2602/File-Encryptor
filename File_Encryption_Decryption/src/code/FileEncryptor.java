package code;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

public class FileEncryptor {

	private static final String ALGORITHM = "AES";
	private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
	private static final String SECRET_KEY = "My_Secret_Key123";
	private static final String INPUT_FILE_PATH = "F:\\Cources\\Java_Project\\File_Encryption_Decryption\\src\\code\\inputFile\\input.txt";
	private static final String ENCRYPTED_OUTPUT_FILE_PATH = "F:\\Cources\\Java_Project\\File_Encryption_Decryption\\src\\code\\encryptFile\\encrypted_output.enc";
	private static final String DECRYPTED_OUTPUT_FILE_PATH = "F:\\Cources\\Java_Project\\File_Encryption_Decryption\\src\\code\\outputFile\\decrypted_output.txt";

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		boolean exit = false;

		while (!exit) {
			System.out.println("Choose operation:");
			System.out.println("1. Encrypt File");
			System.out.println("2. Decrypt File");
			System.out.println("3. Exit");

			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				encryptFile(INPUT_FILE_PATH, ENCRYPTED_OUTPUT_FILE_PATH);
				break;
			case 2:
				decryptFile(ENCRYPTED_OUTPUT_FILE_PATH, DECRYPTED_OUTPUT_FILE_PATH);
				break;
			case 3:
				exit = true;
				System.out.println("Exiting the program. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please choose 1, 2, or 3.");
			}
		}

		scanner.close();
	}

	public static void encryptFile(String inputFilePath, String outputFilePath) {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
			AlgorithmParameterSpec iv = new IvParameterSpec(SECRET_KEY.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

			try (FileInputStream inputStream = new FileInputStream(inputFilePath);
					FileOutputStream outputStream = new FileOutputStream(outputFilePath);
					CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {

				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) >= 0) {
					cipherOutputStream.write(buffer, 0, bytesRead);
				}
			}

			System.out.println("File encrypted successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void decryptFile(String inputFilePath, String outputFilePath) {
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
			AlgorithmParameterSpec iv = new IvParameterSpec(SECRET_KEY.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			try (FileInputStream inputStream = new FileInputStream(inputFilePath);
					CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
					FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {

				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = cipherInputStream.read(buffer)) >= 0) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}

			System.out.println("File decrypted successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
