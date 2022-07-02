package Calculator2;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    // ВВОД ДАННЫХ И ВЫВОД РЕЗУЛЬТАТА:
    public static void main(String[] args) throws inputExeption {
        while (true){
            System.out.println("input");
            Scanner scanner = new Scanner(System.in);
            String inputExampl = scanner.nextLine();
            String outputResult = calc(inputExampl);
            System.out.println("output");
            System.out.println(outputResult);
        }
    }

    // МЕТОД ПРИНИМАЕТ ВВЕДЁННЫЕ ДАННЫЕ В ВИДЕ СТРОКИ И ВОЗВРАЩАЕТ РЕЗУЛЬТАТ ОПЕРАЦИИ В ВИДЕ СТРОКИ:
    public static String calc(String inputExampl) throws inputExeption {
            String[] inputPart = inputExampl.split(" "); // разбиваем введённые данные по пробелам на массив строк.
            String outputResult = new String(); // строка для возврата.

        // по условию задачи - выбрасываем исключение:
        if (inputPart.length > 3){
            throw new inputExeption("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        // по условию задачи - выбрасываем исключение:
        if (inputPart.length < 3){
            throw new inputExeption("строка не является математической операцией");
        }
        // присваиваем имена частям введённого выражения:
        String inputNumber1 = inputPart[0];
        String inputNumber2 = inputPart[2];
        char symbol = inputPart[1].charAt(0);

        // определяем тип чисел (арабские или римские):
        String typeOfInputNumber1 = getNumberType(inputNumber1);
        String typeOfInputNumber2 = getNumberType(inputNumber2);

        // по условию задачи - выбрасываем исключение:
        if (typeOfInputNumber1 != typeOfInputNumber2){
            throw new inputExeption("используются одновременно разные системы счисления"); //throws Exception т.к. используются одновременно разные системы счисления
        }
        // если введены арабские числа - распарсим их и отправим считаться в калькулятор:
        else if(typeOfInputNumber1 == "Arab") {
            int number1 = Integer.parseInt(inputNumber1);
            int number2 = Integer.parseInt(inputNumber2);
            outputResult = calculate(number1, symbol, number2);

        // если римские - преобразуем их в арабские и отправим в калькулятор. Полученное значение преобразуем в римское число:
        } else if (typeOfInputNumber1 == "Rome") {
            // преобразуем:
            int number1 = getArabFromRome(inputNumber1);
            int number2 = getArabFromRome(inputNumber2);

            // если пользователь ввёл не правильные символы - метод getArabFromRome вернёт нам 0 и мы сообщим ему об ошибке:
            if (number1 == 0 || number2 == 0){
                outputResult = "не верный символ в римском числе";
            }else {

                // по условию задачи - выбрасываем исключение:
                if (number1 < number2 && symbol == '-') {
                    throw new inputExeption("в римской системе нет отрицательных чисел");

                } else {
                    String resultOfCalculateInArab = new String();
                    // отправляем в калькулятор:
                    resultOfCalculateInArab = calculate(number1, symbol, number2);
                    // преобразуем обратно:
                    outputResult = getRomFromArab(resultOfCalculateInArab);
                }
            }
        }else {
            // если пользователь ввёл вообще ни римские ни арабские:
            outputResult = "не верный формат числа";
        }return outputResult;
    }

    // МЕТОД ВЫЧИСЛЯЕТ АРАБСКИЕ ИЛИ РИМСКИЕ ЧИСЛА ВВЁЛ ПОЛЬЗОВАТЕЛЬ:
    public static String getNumberType(String inputNumber){
        // раделяем число по символам в массив:
        char[] figurOfInputNumber = inputNumber.toCharArray();
        // выделяем массив под тип каждого символа:
        int[] figureType = new int[figurOfInputNumber.length];
        String typeOfNumber = new String(); // строка для названия типа всего числа.

        // метод getType определяет тип символа в виде целочисленного числа (9 - цифры, 1 - большие английские буквы):
        for(int i=0;i<figurOfInputNumber.length;i++) {
            figureType[i] = Character.getType(figurOfInputNumber[i]); // заносим типы в массив.
        }
        // проверяем что бы типы соответствовали алабским или римским цифрам:
        for(int i=0;i<figureType.length;i++) {
            if (figureType.length <i + 1 && figureType[i] != figureType[i+1]){
                typeOfNumber = "Не правильный ввод";
            } else if (figureType[i] == 9) {
                typeOfNumber = "Arab";
            }else if(figureType[i] == 1){
                typeOfNumber = "Rome";
            }else {
                typeOfNumber = "Не правильный ввод";
            }
        }return typeOfNumber; // возвращаем тип числа.
    }

    // МЕТОД ПРОВОДИТ АРИФМЕТИЧЕСКУЮ ОПЕРАЦИЮ:
    public static String calculate(int number1,char symbol, int number2) {
        String resultOfCalculate = new String();

        // для соответствия условиям задачи - ограничиваем возможности программы:
//        if (number1 <= 0 || number1 > 10 || number2 <= 0 || number2 > 10){
//            resultOfCalculate = ("числа не соответствуют условиям.");
//        }else {

            // в соответствии с арифметическим символом, который ввел пользователь - вычисляем результат
            switch (symbol) {
                case '+':
                    resultOfCalculate = String.valueOf(number1 + number2);
                    break;
                case '-':
                    resultOfCalculate = String.valueOf(number1 - number2);
                    break;
                case '*':
                    resultOfCalculate = String.valueOf(number1 * number2);
                    break;
                case '/':
                    resultOfCalculate = String.valueOf(number1 / number2);
                    break;
                default:
                    resultOfCalculate = ("введён не верный арифметический символ.");
            }
//        }
        return resultOfCalculate;  // возвращаем результат вычисления
    }

    // МЕТОД ПРЕОБРАЗУЕТ РИМСКИЕ ЧИСЛА В АРАБСКИЕ:
    public static int getArabFromRome(String inputNumber) throws inputExeption {
        char[] romeSymbol = inputNumber.toCharArray(); // разбиваем римское число по символам в массив.

        int[] arabNumber = new int[inputNumber.length()]; // массив арабских чисел, соответствующий римскому.

        // для каждого символа в массиве присваиваем арабское значение:
        for (int arabNumbers : arabNumber) {
            int i = 0; // порядковый номер арабского числа в массиве.
            for (char romeSymbols : romeSymbol) { // для каждого символа в массиве
                switch (romeSymbols) { // берём символ
                    case 'I':  // если такой -
                        arabNumbers = 1; // помещаем соответствующее значение в массив арабских чисел.
                        arabNumber[i++] = arabNumbers; // переходим к следующей позиции в массиве арабских чисел
                        break; // завершаем свич для данного римского символа.
                    case 'V':
                        arabNumbers = 5;
                        arabNumber[i++] = arabNumbers;
                        break;
                    case 'X':
                        arabNumbers = 10;
                        arabNumber[i++] = arabNumbers;
                        break;
                    case 'L':
                        arabNumbers = 50;
                        arabNumber[i++] = arabNumbers;
                        break;
                    case 'C':
                        arabNumbers = 100;
                        arabNumber[i++] = arabNumbers;
                        break;
                    case 'D':
                        arabNumbers = 500;
                        arabNumber[i++] = arabNumbers;
                        break;
                    case 'M':
                        arabNumbers = 1000;
                        arabNumber[i++] = arabNumbers;
                        break;
                    default: // если символ не соответсвует ни одному из перечисленных
                        throw new inputExeption("введён не верный символ в римском числе");
                }
            }
            break;
        }
          // свод правил написания римских чисел:

        for (int i = 0; i<arabNumber.length - 3; i++){
            if(arabNumber[i] == arabNumber[i+1] && arabNumber[i] == arabNumber[i+2] && arabNumber[i] == arabNumber[i+3]){
                throw new inputExeption("в римских числах не бывает подряд 4 одинаковых символа");
            }
        }
        for (int i = 0; i < arabNumber.length - 2; i++) {
            if (arabNumber[i] <= arabNumber[i + 2] && arabNumber[i] != arabNumber[i + 1]){
                throw new inputExeption("неверный формат римского числа");
            }
        }

        for (int i = 0; i < arabNumber.length - 1; i++) {
            if (arabNumber[i] < arabNumber[i + 1]) {
                if ((arabNumber[i] != 100 && (arabNumber[i + 1] == 1000 || arabNumber[i + 1] == 500)) || (arabNumber[i] != 10 && (arabNumber[i + 1] == 100 || arabNumber[i + 1] == 50)) || (arabNumber[i] != 1 && (arabNumber[i + 1] == 10 || arabNumber[i + 1] == 5))) {
                    throw new inputExeption("для написания римского числа можно использовать вычитание только ближайшего равного 1 - I, 10 - X или 100 - C");
                }else{
                    arabNumber[i] = -arabNumber[i];
                    i++;
                }
            }
        }

        int arabFromRome = Arrays.stream(arabNumber).sum(); // суммируем массив,
        return arabFromRome; // возвращаем арабское число.
    }

    // МЕТОД ПРЕОБРАЗУЕТ АРАБСКИЕ ЧИСЛА В РИМСКИЕ:
    public static String getRomFromArab(String resultOfCalculate) {
        int inputArab = Integer.parseInt(resultOfCalculate); // парсим результат из калькулятора.
        String resultCalculateToRome = "";  // строка для написания римского числа.

        // в римском формате пишутся только числа от 1 до 3999
        if (inputArab < 1 || inputArab >= 4000) {
            resultCalculateToRome = "Число не попадает в диапозон римских чисел.\nВведите другое число";
        } else {
            while (true) { // цикл по уменьшению числа из калькулятора, пока оно не станет равно 0.
                if (inputArab >= 1000) {                                 // если число больше или равно 1000:
                    resultCalculateToRome = resultCalculateToRome + "M"; // добавляем в строку М.
                    inputArab -= 1000;                                   // и вычитаем из числа 1000.
                } else if (inputArab >= 900) {
                    resultCalculateToRome = resultCalculateToRome + "CM";
                    inputArab -= 900;
                } else if (inputArab >= 500) {
                    resultCalculateToRome = resultCalculateToRome + "D";
                    inputArab -= 500;
                } else if (inputArab >= 400) {
                    resultCalculateToRome = resultCalculateToRome + "CD";
                    inputArab -= 400;
                } else if (inputArab >= 100) {
                    resultCalculateToRome = resultCalculateToRome + "C";
                    inputArab -= 100;
                } else if (inputArab >= 90) {
                    resultCalculateToRome = resultCalculateToRome + "XC";
                    inputArab -= 90;
                } else if (inputArab >= 50) {
                    resultCalculateToRome = resultCalculateToRome + "L";
                    inputArab -= 50;
                } else if (inputArab >= 40) {
                    resultCalculateToRome = resultCalculateToRome + "XL";
                    inputArab -= 40;
                } else if (inputArab >= 10) {
                    resultCalculateToRome = resultCalculateToRome + "X";
                    inputArab -= 10;
                } else if (inputArab >= 9) {
                    resultCalculateToRome = resultCalculateToRome + "IX";
                    inputArab -= 9;
                } else if (inputArab >= 5) {
                    resultCalculateToRome = resultCalculateToRome + "V";
                    inputArab -= 5;
                } else if (inputArab >= 4) {
                    resultCalculateToRome = resultCalculateToRome + "IV";
                    inputArab -= 4;
                } else if (inputArab >= 1) {
                    resultCalculateToRome = resultCalculateToRome + "I";
                    inputArab -= 1;
                } else {
                    break;
                }
            }
        }
        return resultCalculateToRome;  // возвращаем строку с римским числом.
    }
}




