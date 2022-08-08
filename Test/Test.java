// @author Valeriy Sorokin
// For Kate Academy 2022

//Импорты
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.TreeMap;

import static java.lang.String.join;

public class Test {

    //Функция Main
    public static void main(String[] args){

        //romanNumeral(1);
        System.out.println(toRoman(5848453));
    }

    //Функция, которая генерирует Римское число в зависимости от входного значения (Арабского числа)
    //Генерация происходит с помощью подставления новых чисел
    //Максимальное значение 100
    public static void romanNumeral(int input){
        //Основные цифры, которые используются в крупных числах
        String[] numeralConstants = {"I","V","X","L","C"};
        //Тестовое число, которое будет преобразовываться

        String output = "";

        //Сам скрипт
        if(input>0&&input<5){
            System.out.println("В диапазоне от 1 до 4 включительно");
        }
        if(input>=5&&input<10){
            System.out.println("В диапазоне от 5 до 9 включительно");
        }
        if(input>=10&&input<50){
            System.out.println("В диапазоне от 10 до 49 включительно");
        }
        if(input>=50&&input<100){
            System.out.println("В диапазоне от 50 до 99 включительно");
        }
        if(input==100){
            System.out.println("Равно 100");
        }
    }



    public static String toRoman(int number) {
        TreeMap<Integer, String> map = new TreeMap<>();
        {
            map.put(100, "C");
            map.put(90, "XC");
            map.put(50, "L");
            map.put(40, "XL");
            map.put(10, "X");
            map.put(9, "IX");
            map.put(5, "V");
            map.put(4, "IV");
            map.put(1, "I");
        }

        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }


    //Переписать код для обратного преобразования (Римские -> Арабские)
    public static int toArabic(String roman) {
        TreeMap<String, Integer> map = new TreeMap<>();
        {
            map.put("X", 10);
            map.put("IX", 9);
            map.put("VIII", 8);
            map.put("VII", 7);
            map.put("VI", 6);
            map.put("V", 5);
            map.put("IV", 4);
            map.put("III", 3);
            map.put("II", 2);
            map.put("I", 1);
        }
        try{
            return map.get(roman);
        }
        catch(Exception e){
            System.out.println("""
                      Ошибка!
                      Способы решения:
                      1. Используйте цифры в диапазоне 1-10 (I-X)
                      2. Используйте правильный арифметический символ
                      3. Используйте целые числа""");
            System.exit(1);
        }
        return 0;
    }

    //Калькулятор
    public static String calc(String input){
        String[] output = input.split(" "); // [III] [+] [I]   [3] [+] [1]
        input = input.replaceAll("\\s", ""); // III+I   3+1

        String[] operands = input.split("[*/+-]"); //[III] [1]   [3] [1]
        String[] arabic = {};
        String[] answer = new String[1];

        //Булево значение, отвечающее за текующий тип цифр (False -> Arabic | True -> Roman)
        boolean isRoman = false;

        //Проверяет кол-во цифр в инпуте
        //Если количество больше 2, то программа завершается
        try {
            if (output.length > 3){
                throw new Exception();
            }
        }
        catch(Exception e){
            System.out.println("""
                      Ошибка!
                      Используйте не более 2 цифр и 1 арифметического знака в примере""");
            System.exit(1);
        }

        //Проверяет какие цифры используются:
        //1. Если ввод содержит Римские цифры, то в массив заносятся измененные значения (Арабские цифры)
        //При этом проверяется, чтобы обе цифры относились к одному происхождению (Римскому/Арабскому)
        //2. Иначе (если ввод содержит арабские), то в массив дублируются значения без изменений
        if(input.contains("I") || input.contains("X")){
            arabic = checkForNumeral(operands); // [3] [1];
            isRoman = true;
        }
        else{
            try {
                for (String arabicNumeral : operands) {
                    int value = Integer.parseInt(arabicNumeral);
                    if (value <= 0 || value >= 11) {
                        throw new Exception();
                    }
                }
                arabic = operands;
            }
            catch(Exception e){
                System.out.println("""
                      Ошибка!
                      Используйте числа в диапазоне 1-10 включительно""");
                System.exit(1);
            }
        }

        //Операции с числами
        int agregate = Integer.parseInt(arabic[0]); //Переменная, в которую записывается ответ
        for (String s : output) {
            switch (s) {
                case "*" -> agregate *= Integer.parseInt(arabic[1]);
                case "/" -> agregate /= Integer.parseInt(arabic[1]);
                case "+" -> agregate += Integer.parseInt(arabic[1]);
                case "-" -> agregate -= Integer.parseInt(arabic[1]);
            }
        }

        //Если использовались Арабские цифры, то метод возвращает итоговое решение
        //Если использовались Римские цифры, то ответ преобразуется из Арабских цифр в Римские цифры
        if(!isRoman) return String.valueOf(agregate);
        else{
            // Answer: int(4) -> String(IV)
            answer[0] = String.valueOf(agregate); //В массив вносится ответ (int -> str)
            answer = checkForNumeral(answer);
        }
        return answer[0];
    }

    //Проверяет тип вводимых чисел (Арабские или Римские)
    //При необходимости заменяет Римские на Арабские и наоборот
    public static String[] checkForNumeral(String[] input){
        try {
            for(int i = 0;i<input.length;i++){
                switch (input[i]) {
                    //Римские -> Арабские
                    case "X" -> input[i] = input[i].replace("X","10");
                    case "IX" -> input[i] = input[i].replace("IX","9");
                    case "VIII" -> input[i] = input[i].replace("VIII","8");
                    case "VII" -> input[i] = input[i].replace("VII","7");
                    case "VI" -> input[i] = input[i].replace("VI","6");
                    case "V" -> input[i] = input[i].replace("V","5");
                    case "IV" -> input[i] = input[i].replace("IV","4");
                    case "III" -> input[i] = input[i].replace("III","3");
                    case "II" -> input[i] = input[i].replace("II","2");
                    case "I" -> input[i] = input[i].replace("I", "1");
                    //Арабские -> Римские
                    case "1" -> input[i] = input[i].replace("1", "I");
                    case "2" -> input[i] = input[i].replace("2", "II");
                    case "3" -> input[i] = input[i].replace("3", "III");
                    case "4" -> input[i] = input[i].replace("4", "IV");
                    case "5" -> input[i] = input[i].replace("5", "V");
                    case "6" -> input[i] = input[i].replace("6", "VI");
                    case "7" -> input[i] = input[i].replace("7", "VII");
                    case "8" -> input[i] = input[i].replace("8", "VIII");
                    case "9" -> input[i] = input[i].replace("9", "IX");
                    case "10" -> input[i] = input[i].replace("10", "X");
                    //Иначе выбрасывает исключение
                    default -> throw new Exception();
                };
            }
            return input;
        }
        catch(Exception e){
            System.out.println("""
                      Ошибка!
                      Способы решения:
                      1. Используйте цифры в диапазоне 1-10 (I-X)
                      2. Используйте правильный арифметический символ
                      3. Используйте целые числа""");
            System.exit(1);
        }
        return null;
    }
}

//if(input.contains("I") || input.contains("X")){
//        for(String romanNumeral : operands){
//        if(!(romanNumeral.contains("I") || romanNumeral.contains("X"))){
//        throw new Exception();
//        }
//        }
//        arabic = checkForNumeral(operands); // [3] [1];
//        }
//        else arabic = operands;


//        //Подстановка операторов и операндов в зависимости от чисел
//        //Если Римские -> В Массив заносятся вс
//        if(input.contains("I") || input.contains("X")) operators = input.split("");
//        else operators = input.split("[0-9]+");
//Подстановка в инпут измененных чисел (Римские -> Арабские)
//        String str = arabic[0] + output[1] + arabic[1];

//    1.0 Без римских цифр
// @author Valeriy Sorokin
//For Kate Academy 2022
//
//Импорты
//import java.util.Scanner;
//
//public class Main {
//
//    //Функция Main
//    public static void main(String[] args){
//        System.out.println(calc("III + IV"));
//    }
//
//    //Калькулятор
//    public static String calc(String input){
//        try{
//            input = input.replaceAll("\\s", "");
//
//            //[III],[IV]
//            String[] nums = checkForNumeral(input);
//            String operators[] = {};
//            String operands[] = {};
//
//            //Если используются Римские цифры
//            if(nums.length>0){
//                for(String str : checkForNumeral(input)){
//
//                }
//            }
//            //Если используются Арабские цифры
//            else{
//                operators = input.split("[0-9]+");
//                operands = input.split("[*/+-]");
//        }
//
//        checkForExceptionsOperands(operands);
//        int agregate = Integer.parseInt(operands[0]);
//        for(int i=1;i<operands.length;i++){
//        switch (operators[i]) {
//        case "*" -> agregate *= Integer.parseInt(operands[i]);
//        case "/" -> agregate /= Integer.parseInt(operands[i]);
//        case "+" -> agregate += Integer.parseInt(operands[i]);
//        case "-" -> agregate -= Integer.parseInt(operands[i]);
//        }
//        }
//        return String.valueOf(agregate);
//        }
//        catch(NumberFormatException e){
//        System.out.println("""
//                    Ошибка!
//                    Способы решения:
//                    1. Используйте только положительные числа
//                    2. Используйте правильный арифметический символ
//                    3. Используйте целые числа
//                    4. Используйте только арабские/римские цифрами одновременно""");
//        System.exit(1);
//        }
//        return null;
//        }
//
////Метод, проверяющий на ошибки
//public static void checkForExceptionsOperands(String[] array){
//        for(String str : array){
//        int num = Integer.parseInt(str);
//        try{
//        if(num > 10){
//        System.out.println("Введите число больше 10!");
//        throw new Exception();
//        }
//        if(num < 0){
//        System.out.println("Число не может быть отрицательным!");
//        throw new Exception();
//        }
//        }
//        catch(Exception e){
//        System.exit(1);
//        }
//        }
//        }
//
//public static String[] checkForNumeral(String input){
//        try {
//        input = input.replaceAll("\\s", "");
//        // III+IV
//        String operators[] = input.split("I,II,III,IV,V,VI,VII,VIII,IX,X");
//        String operands[] = input.split("[*/+-]");
//        // [III],[IV]
//        String[] output = new String[2];
//        for(int i = 0;i<2;i++){
//        switch (operands[i]) {
//        case "I" -> output[i] = Roman.I.toString();
//        case "II" -> output[i] = Roman.II.toString();
//        case "III" -> output[i] = Roman.III.toString();
//        case "IV" -> output[i] = Roman.IV.toString();
//        case "V" -> output[i] = Roman.V.toString();
//        case "VI" -> output[i] = Roman.VI.toString();
//        case "VII" -> output[i] = Roman.VII.toString();
//        case "VIII" -> output[i] = Roman.VIII.toString();
//        case "IX" -> output[i] = Roman.IX.toString();
//        case "X" -> output[i] = Roman.X.toString();
//        };
//        }
//        return output;
//        }
//        catch(Exception e){
//        System.out.println("Введите правильное число!");
//        System.exit(1);
//        }
//        return null;
//        }
//        }
//
////Enum класс для Римских цифр
//enum Roman{
//    I("1"),
//    II("2"),
//    III("3"),
//    IV("4"),
//    V("5"),
//    VI("6"),
//    VII("7"),
//    VIII("8"),
//    IX("9"),
//    X("10");
//
//    private String title;
//
//    Roman(String title){
//        this.title = title;
//    }
//
//    public String toString(){
//        return title;
//    }
//}




//----------------------Версия 2.0 (Завершенная внутренняя часть)----------------------
//// @author Valeriy Sorokin
//// For Kate Academy 2022
//
////Импорты
//import java.util.Scanner;
//        import java.util.TreeMap;
//
//public class Main {
//
//    //Функция Main
//    public static void main(String[] args){
//
//        System.out.println(calc("IV - V"));
//
//    }
//
//    //Калькулятор
//    public static String calc(String input){
//        String[] output = input.split(" "); // [III] [+] [I]   [3] [+] [1]
//        input = input.replaceAll("\\s", ""); // III+I   3+1
//
//        String[] operands = input.split("[*/+-]"); //[III] [1]   [3] [1]
//        String[] arabic = {};
//        String answer = "";
//
//        //Булево значение, отвечающее за текующий тип цифр (False -> Arabic | True -> Roman)
//        boolean isRoman = false;
//
//        //Проверяет кол-во цифр в инпуте
//        //Если количество больше 2, то программа завершается
//        try {
//            if (output.length > 3){
//                throw new Exception();
//            }
//        }
//        catch(Exception e){
//            System.out.println("""
//                      Ошибка!
//                      Используйте не более 2 цифр и 1 арифметического знака в примере""");
//            System.exit(1);
//        }
//
//        //Проверяет какие цифры используются:
//        //1. Если ввод содержит Римские цифры, то в массив заносятся измененные значения (Арабские цифры)
//        //При этом проверяется, чтобы обе цифры относились к одному происхождению (Римскому/Арабскому)
//        //2. Иначе (если ввод содержит арабские), то в массив дублируются значения без изменений
//        if(input.contains("I") || input.contains("X")){
//            arabic = checkForNumeral(operands); // [3] [1];
//            isRoman = true;
//        }
//        else{
//            try {
//                for (String arabicNumeral : operands) {
//                    int value = Integer.parseInt(arabicNumeral);
//                    if (value <= 0 || value >= 11) {
//                        throw new Exception();
//                    }
//                }
//                arabic = operands;
//            }
//            catch(Exception e){
//                System.out.println("""
//                      Ошибка!
//                      Используйте числа в диапазоне 1-10 включительно""");
//                System.exit(1);
//            }
//        }
//
//        //Операции с числами
//        int agregate = Integer.parseInt(arabic[0]); //Переменная, в которую записывается ответ
//        for (String s : output) {
//            switch (s) {
//                case "*" -> agregate *= Integer.parseInt(arabic[1]);
//                case "/" -> agregate /= Integer.parseInt(arabic[1]);
//                case "+" -> agregate += Integer.parseInt(arabic[1]);
//                case "-" -> agregate -= Integer.parseInt(arabic[1]);
//            }
//        }
//
//        //Если использовались Арабские цифры, то метод возвращает итоговое решение
//        //Если использовались Римские цифры, то ответ преобразуется из Арабских цифр в Римские цифры
//        if(!isRoman) return String.valueOf(agregate);
//        else{
//            // Answer: int(4) -> String(IV)
//            try{
//                answer = String.valueOf(toRoman(agregate));
//            }
//            catch(Exception e){
//                System.out.println("""
//                      Ошибка!
//                      Римские цифры не могут быть отрицательными""");
//                System.exit(1);
//            }
//        }
//        return answer;
//    }
//
//    //Проверяет тип вводимых чисел (Арабские или Римские)
//    //При необходимости заменяет Римские на Арабские и наоборот
//    public static String[] checkForNumeral(String[] input){
//        try {
//            for(int i = 0;i<input.length;i++){
//                switch (input[i]) {
//                    //Римские -> Арабские
//                    case "X" -> input[i] = input[i].replace("X","10");
//                    case "IX" -> input[i] = input[i].replace("IX","9");
//                    case "VIII" -> input[i] = input[i].replace("VIII","8");
//                    case "VII" -> input[i] = input[i].replace("VII","7");
//                    case "VI" -> input[i] = input[i].replace("VI","6");
//                    case "V" -> input[i] = input[i].replace("V","5");
//                    case "IV" -> input[i] = input[i].replace("IV","4");
//                    case "III" -> input[i] = input[i].replace("III","3");
//                    case "II" -> input[i] = input[i].replace("II","2");
//                    case "I" -> input[i] = input[i].replace("I", "1");
//                    //Иначе выбрасывает исключение
//                    default -> throw new Exception();
//                };
//            }
//            return input;
//        }
//        catch(Exception e){
//            System.out.println("""
//                      Ошибка!
//                      Способы решения:
//                      1. Используйте цифры в диапазоне 1-10 (I-X)
//                      2. Используйте правильный арифметический символ
//                      3. Используйте целые числа""");
//            System.exit(1);
//        }
//        return null;
//    }
//
//    //Метод конвертирующий Арабские -> Римские
//    public static String toRoman(int number) {
//        TreeMap<Integer, String> map = new TreeMap<>();
//        {
//            map.put(100, "C");
//            map.put(90, "XC");
//            map.put(50, "L");
//            map.put(40, "XL");
//            map.put(10, "X");
//            map.put(9, "IX");
//            map.put(5, "V");
//            map.put(4, "IV");
//            map.put(1, "I");
//        }
//
//        int l =  map.floorKey(number);
//        if ( number == l ) {
//            return map.get(number);
//        }
//        return map.get(l) + toRoman(number-l);
//    }
//}

//-----------------------Ver 3.0 (Полностью работающая внутренняя часть -> Без интерфейса)--------------------
// @author Valeriy Sorokin
// For Kate Academy 2022
//Ver 2.1
//
////Импорты
//import java.util.TreeMap;
//
////TODO:
////1. Создать интерфейс консоли (ввод пользователем примера с дальнейшим вычислением)
//
//
//public class Main {
//
//    //Функция Main
//    public static void main(String[] args){
//
//        System.out.println(calc("IV * IX"));
//
//    }
//
//    //Калькулятор
//    public static String calc(String input){
//        String[] output = input.split(" "); // [III] [+] [I]   [3] [+] [1]
//        input = input.replaceAll("\\s", ""); // III+I   3+1
//
//        String[] operands = input.split("[*/+-]"); //[III] [1]   [3] [1]
//        String[] arabic = new String[2];
//        String answer = "";
//
//        //Булево значение, отвечающее за текующий тип цифр (False -> Arabic | True -> Roman)
//        boolean isRoman = false;
//
//        //Проверяет кол-во цифр в инпуте
//        //Если количество больше 2, то программа завершается
//        try {
//            if (output.length > 3){
//                throw new Exception();
//            }
//        }
//        catch(Exception e){
//            System.out.println("""
//                      Ошибка!
//                      Используйте не более 2 цифр и 1 арифметического знака в примере""");
//            System.exit(1);
//        }
//
//        //Проверяет какие цифры используются:
//        //1. Если ввод содержит Римские цифры, то в массив заносятся измененные значения (Арабские цифры)
//        //При этом проверяется, чтобы обе цифры относились к одному происхождению (Римскому/Арабскому)
//        //2. Иначе (если ввод содержит арабские), то в массив дублируются значения без изменений
//        if(input.contains("I") || input.contains("X")){
//            //Checkfornumeral String[] "IV" -> String "4"
//            //toRoman int 4 -> String "IV"
//            //arabic = checkForNumeral(operands); // [3] [1];
//            for(int i = 0;i<2;i++){
//                arabic[i] = String.valueOf(toArabic(operands[i]));
//            }
//            isRoman = true;
//        }
//        else{
//            try {
//                for (String arabicNumeral : operands) {
//                    int value = Integer.parseInt(arabicNumeral);
//                    if (value <= 0 || value >= 11) {
//                        throw new Exception();
//                    }
//                }
//                arabic = operands;
//            }
//            catch(Exception e){
//                System.out.println("""
//                      Ошибка!
//                      Используйте числа в диапазоне 1-10 включительно""");
//                System.exit(1);
//            }
//        }
//
//        //Операции с числами
//        int agregate = Integer.parseInt(arabic[0]); //Переменная, в которую записывается ответ
//        for (String s : output) {
//            switch (s) {
//                case "*" -> agregate *= Integer.parseInt(arabic[1]);
//                case "/" -> agregate /= Integer.parseInt(arabic[1]);
//                case "+" -> agregate += Integer.parseInt(arabic[1]);
//                case "-" -> agregate -= Integer.parseInt(arabic[1]);
//            }
//        }
//
//        //Если использовались Арабские цифры, то метод возвращает итоговое решение
//        //Если использовались Римские цифры, то ответ преобразуется из Арабских цифр в Римские цифры
//        if(!isRoman) return String.valueOf(agregate);
//        else{
//            // Answer: int(4) -> String(IV)
//            try{
//                answer = String.valueOf(toRoman(agregate));
//            }
//            catch(Exception e){
//                System.out.println("""
//                      Ошибка!
//                      Римские цифры не могут быть отрицательными""");
//                System.exit(1);
//            }
//        }
//        return answer;
//    }
//
//    //Метод конвертирующий Арабские -> Римские
//    public static String toRoman(int arabic) {
//        TreeMap<Integer, String> map = new TreeMap<>();
//        {
//            map.put(100, "C");
//            map.put(90, "XC");
//            map.put(50, "L");
//            map.put(40, "XL");
//            map.put(10, "X");
//            map.put(9, "IX");
//            map.put(5, "V");
//            map.put(4, "IV");
//            map.put(1, "I");
//        }
//
//        int l =  map.floorKey(arabic);
//        if ( arabic == l ) {
//            return map.get(arabic);
//        }
//        return map.get(l) + toRoman(arabic-l);
//    }
//
//    //Метод конвертирующий Римские -> Арабские
//    public static int toArabic(String roman) {
//        TreeMap<String, Integer> map = new TreeMap<>();
//        {
//            map.put("X", 10);
//            map.put("IX", 9);
//            map.put("VIII", 8);
//            map.put("VII", 7);
//            map.put("VI", 6);
//            map.put("V", 5);
//            map.put("IV", 4);
//            map.put("III", 3);
//            map.put("II", 2);
//            map.put("I", 1);
//        }
//        try{
//            return map.get(roman);
//        }
//        catch(Exception e){
//            System.out.println("""
//                      Ошибка!
//                      Способы решения:
//                      1. Используйте цифры в диапазоне 1-10 (I-X)
//                      2. Используйте правильный арифметический символ
//                      3. Используйте целые числа
//                      4. Используйте только Арабские/Римские цифры одновременно""");
//            System.exit(1);
//        }
//        return 0;
//    }
//}










