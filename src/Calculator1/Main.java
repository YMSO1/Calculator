package Calculator1;

import java.util.Objects;
import java.util.Scanner;

public class  Main {
    public static void main(String[] args) throws RuntimeException {
        while (true) {                                                                                                  // что бы не выходила из программы после решения
            System.out.println("input");                                                                                // вывод в консоль слова input
            Scanner scanner = new Scanner(System.in);                                                                   // создали переменную scanner класса Scanner с системой ввода с консоли
            String input = scanner.nextLine();                                                                          // переменной input присваиваем введённое значение в виде строки

            System.out.println("output");                                                                               // выводим в консоль строку output переносим на новую строку результат из метода calc
            System.out.println(calc(input));                                                                            // передаём введённую строку в calc и выводим в консоль результат из метода calc
        }
    }
    public static String calc(String input) {                                                                           // создали метод calc, который принимает строку с именем input
        String[] parts = input.split(" ");                                                                              //  разделяем строку input по пробелам и заполняем массив parts
        if (parts.length > 3){                                                                                          //  если ввели больше параметров выбрасываем ошибку
            throw new RuntimeException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
        if (parts.length < 3){                                                                                          //  если меньше - тоже
            throw new RuntimeException("строка не является математической операцией");
        }
        String value1 = parts[0];                                                                                       //  присваеваем первому элементу массива имя value1
        int digitValue1 = 0;
        String value2 = parts[2];                                                                                       //  присваеваем третьему элементу массива имя value2
        int digitValue2 = 0;
        String operator = parts[1];                                                                                     //  присваеваем второму элементу массива имя operator

        try {                                                                                                           //  пробуем преобразовать строки value1 2 в целочисленные переменные

            digitValue1 = Integer.parseInt(value1);                                                                     // преобразуем строку value1 в целочисленное digitValue1
            digitValue2 = Integer.parseInt(value2);                                                                     // преобразуем строку value2 в целочисленное digitValue2
                                                                                                                        //  или продолжаем выполнять код
            return operation(digitValue1, digitValue2, operator);                                                       // возвращаем строку на место вызова метода calc

        } catch (NumberFormatException e) {                                                                             //  если в строках value1 2 будут не целочисленные значения - выйдет ошибка, которую мы будем обрабатывать следующим образом:
            String output = "";                                                                                         //  создаём строковую переменную output
            for (Rome romeNumber : Rome.values()) {                                                                     //  пройдём по енаму, называя каждое римское число romeNumber
                if (Objects.equals(value1, romeNumber.name())) {                                                        //  если сравнивая значения объектов они будут равны
                    digitValue1 = romeNumber.romeAnalog;                                                                //  присваиваем целочисленной переменной digitValue1 перевод текущей константы
                    break;
                }
            }
            if (digitValue1 == 0){                                                                                      //  если выполняются условия значит введённый символ не соответствует римскому числу
                throw new RuntimeException("Используются одновременно разные системы счисления");                       //  выводим ошибку с описанием
            }
            for (Rome romeNumber : Rome.values()) {                                                                     //  пройдём по енаму, называя каждое римское число romeNumber
                if (Objects.equals(value2, romeNumber.name())) {                                                        //  если сравнивая значения объектов они будут равны
                    digitValue2 = romeNumber.romeAnalog;                                                                //  присваиваем целочисленной переменной digitValue2 перевод текущей константы
                    break;
                }
            }
            if (digitValue2 == 0){                                                                                      //  если выполняются условия значит введённый символ не соответствует римскому числу
                throw new RuntimeException("Используются одновременно разные системы счисления");                       //  выводим ошибку с описанием
            }
            if (digitValue1 <= digitValue2 && operator.equals("-")){                                                    //  на случай если из меньшего римского числа вычитается большее
                throw new RuntimeException("не бывает отрицательных римских чисел");
            }
            if (digitValue1 * 2 <= digitValue2 && operator.equals("/")){                                                //  на случай если при делении после округления получается 0
                throw new RuntimeException("в римских числах нет 0");
            }
            String resultOperation = operation(digitValue1, digitValue2, operator);                                     //  Создаём строку resultOperation и присваиваем ей значение которое вернёт метод operation. Вызываемому методу мы передаём значения в скобках
            int intResultOperation = Integer.parseInt(resultOperation);                                                 //  преобразуем строку в целочисленную переменную
            for (Rome roNumber : Rome.values()) {                                                                       //  пройдём по енаму, называя каждое римское число - roNumber
                if (intResultOperation == roNumber.romeAnalog) {                                                        //  если целочисленный результат вычислений равен переводу текущего числа
                    output = roNumber.name();                                                                           //  присваиваем римское число в виде строки переменной output
                    break;
                }
            }
            return output;                                                                                              //   возвращаем значение строки
        }
    }
    public static String operation(int digitValue1, int digitValue2, String operator) {
        if (digitValue1 < 0 || digitValue2 < 0 || digitValue1 > 10 || digitValue2 > 10) {

            throw new RuntimeException("условие не выполнено");
        }
        else {
            switch (operator) {
                case "+":
                    return String.valueOf(digitValue1 + digitValue2);
                case "-":
                    return String.valueOf(digitValue1 - digitValue2);
                case "*":
                    return String.valueOf(digitValue1 * digitValue2);
                case "/":
                    return String.valueOf(digitValue1 / digitValue2);
                default:
                    throw new RuntimeException("не верный знак операции");
            }
        }
    }
}
enum Rome {
    I(1), II(2), III(3), IV(4), V(5), VI(6), VII(7), VIII(8), IX(9), X(10), XI(11), XII(12), XIII(13), XIV(14), XV(15),
    XVI(16), XVII(17), XVIII(18), XIX(19), XX(20), XXI(21), XXIV(24), XXV(25), XXVII(27), XXVIII(28),
    XXX(30), XXXII(32), XXXV(35), XXXVI(36), XL(40), XLII(42), XLV(45), XLVIII(48), XLIX(49), L(50), LIV(54), LV(55),
    LVI(56), LX(60), LXIII(63), LXIV(64), LXX(70), LXXII(72), LXXX(80), LXXXI(81), XC(90), C(100);
    int romeAnalog;
    String arabToRomeName;
    Rome(String arabToRomeName){
        this.arabToRomeName = arabToRomeName;
    }
    Rome(int romeAnalog) {
        this.romeAnalog = romeAnalog;
    }
    public int convFromRome() {
        return romeAnalog;
    }
    public String getArabToRomeName(){
        return arabToRomeName;
    }
}

