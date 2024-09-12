import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// Пользовательский класс исключения для обработки ошибок формата данных
class InvalidDataFormatException extends Exception {
    public InvalidDataFormatException(String message) {
        super(message);
    }
}

public class DataProcessor {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в произвольном порядке, разделенные пробелом: Фамилия Имя Отчество датарождения номертелефона пол");
        String input = scanner.nextLine();

        try {
            // Разбиваем введенные данные на массив строк
            String[] data = input.split(" ");
            if (data.length != 6) {
                throw new InvalidDataFormatException("Неверное количество данных. Требуется 6 параметров.");
            }

            // Инициализация переменных для хранения данных
            String surname = null;
            String name = null;
            String patronymic = null;
            String birthDate = null;
            String phoneNumber = null;
            char gender = ' ';

            // Парсинг данных
            for (String item : data) {
                if (item.matches("\\d{2}\\.\\d{2}\\.\\d{4}")) {
                    birthDate = item;
                } else if (item.matches("\\d+")) {
                    phoneNumber = item;
                } else if (item.matches("[fm]")) {
                    gender = item.charAt(0);
                } else {
                    if (surname == null) {
                        surname = item;
                    } else if (name == null) {
                        name = item;
                    } else if (patronymic == null) {
                        patronymic = item;
                    } else {
                        throw new InvalidDataFormatException("Неверный формат данных.");
                    }
                }
            }

            // Проверка наличия всех данных
            if (surname == null || name == null || patronymic == null || birthDate == null || phoneNumber == null || gender == ' ') {
                throw new InvalidDataFormatException("Неверный формат данных.");
            }

            // Запись данных в файл
            writeToFile(surname, name, patronymic, birthDate, phoneNumber, gender);
            System.out.println("Данные успешно записаны в файл.");

        } catch (InvalidDataFormatException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Метод для записи данных в файл
    private static void writeToFile(String surname, String name, String patronymic, String birthDate, String phoneNumber, char gender) throws IOException {
        try (FileWriter writer = new FileWriter(surname, true)) {
            writer.write(String.format("%s %s %s %s %s %c%n", surname, name, patronymic, birthDate, phoneNumber, gender));
        }
    }
}