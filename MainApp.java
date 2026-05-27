package project;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        char[] alphabet = {
                'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м',
                'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
                'ъ', 'ы', 'ь', 'э', 'ю', 'я',
                '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
        };

        FileManager fileManager = new FileManager();
        Cipher cipher = new Cipher(alphabet);
        Validator validator = new Validator();
        BruteForce bruteForce = new BruteForce();
        StatisticalAnalyzer statisticalAnalyzer = new StatisticalAnalyzer();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Шифр Цезаря ===");
            System.out.println("1. Шифровка текста");
            System.out.println("2. Расшифровка текста с помощью ключа");
            System.out.println("3. Расшифровка текста с помощью brute force");
            System.out.println("4. Расшифровка с помощью статистического анализа");
            System.out.println("0. Выход");
            System.out.print("Выберите режим: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("До свидания!");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Введите адрес файла с оригинальным текстом: ");
                    String inputFile = scanner.nextLine();
                    System.out.print("Введите адрес файла для зашифрованного текста: ");
                    String outputFile = scanner.nextLine();
                    System.out.print("Введите сдвиг (ключ): ");
                    int key = scanner.nextInt();
                    scanner.nextLine();

                    if (validator.isFileExists(inputFile) && validator.isValidKey(key, alphabet)) {
                        try {
                            String text = fileManager.readFile(inputFile);
                            String encrypted = cipher.encrypt(text, key);
                            fileManager.writeFile(encrypted, outputFile);
                            System.out.println("Шифрование завершено!");
                        } catch (Exception e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Ошибка: файл не существует или ключ неверный");
                    }
                    break;

                case 2:
                    System.out.print("Введите адрес зашифрованного файла: ");
                    String encryptedFile = scanner.nextLine();
                    System.out.print("Введите адрес для расшифрованного файла: ");
                    String decryptedFile = scanner.nextLine();
                    System.out.print("Введите сдвиг (ключ): ");
                    int decryptKey = scanner.nextInt();
                    scanner.nextLine();

                    if (validator.isFileExists(encryptedFile) && validator.isValidKey(decryptKey, alphabet)) {
                        try {
                            String encryptedText = fileManager.readFile(encryptedFile);
                            String decrypted = cipher.decrypt(encryptedText, decryptKey);
                            fileManager.writeFile(decrypted, decryptedFile);
                            System.out.println("Расшифровка завершена!");
                        } catch (Exception e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Ошибка: файл не существует или ключ неверный");
                    }
                    break;

                case 3:
                    System.out.print("Введите адрес зашифрованного файла: ");
                    String bruteInput = scanner.nextLine();
                    System.out.print("Введите адрес для расшифрованного файла: ");
                    String bruteOutput = scanner.nextLine();
                    System.out.print("Введите адрес файла с примером текста (или Enter для пропуска): ");
                    String sampleFile = scanner.nextLine();
                    if (sampleFile.isEmpty()) sampleFile = null;

                    if (validator.isFileExists(bruteInput)) {
                        try {
                            String encryptedText = fileManager.readFile(bruteInput);
                            String sampleText = null;
                            if (sampleFile != null && validator.isFileExists(sampleFile)) {
                                sampleText = fileManager.readFile(sampleFile);
                            }
                            String result = bruteForce.decryptByBruteForce(encryptedText, alphabet, sampleText);
                            fileManager.writeFile(result, bruteOutput);
                            System.out.println("Brute force завершен!");
                        } catch (Exception e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Ошибка: файл не существует");
                    }
                    break;

                case 4:
                    System.out.print("Введите адрес зашифрованного файла: ");
                    String statInput = scanner.nextLine();
                    System.out.print("Введите адрес для расшифрованного файла: ");
                    String statOutput = scanner.nextLine();
                    System.out.print("Введите адрес файла с репрезентативным текстом: ");
                    String repFile = scanner.nextLine();

                    if (validator.isFileExists(statInput) && validator.isFileExists(repFile)) {
                        try {
                            String encryptedText = fileManager.readFile(statInput);
                            String representativeText = fileManager.readFile(repFile);
                            int shift = statisticalAnalyzer.findMostLikelyShift(encryptedText, alphabet, representativeText);
                            System.out.println("Найден сдвиг: " + shift);
                            String decrypted = cipher.decrypt(encryptedText, shift);
                            fileManager.writeFile(decrypted, statOutput);
                            System.out.println("Статистический анализ завершен!");
                        } catch (Exception e) {
                            System.out.println("Ошибка: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Ошибка: один из файлов не существует");
                    }
                    break;

                default:
                    System.out.println("Неверный выбор");
            }
        }
        scanner.close();
    }
}