// @author Valeriy Sorokin
// For Kata Academy 2022
//Ver 4 (Final)

//Импорты
import java.util.TreeMap;
import java.util.Scanner;

//TODO:
//1. (!!!) Калькулятор умеет выполнять операции сложения, вычитания, умножения и деления с двумя числами: a + b, a - b, a * b, a / b. Данные передаются в одну строку (смотри пример)! Решения, в которых каждое число и арифмитеческая операция передаются с новой строки считаются неверными.
//2. (!!!) Калькулятор умеет работать как с арабскими (1,2,3,4,5…), так и с римскими (I,II,III,IV,V…) числами.
//3. (!!!) Калькулятор должен принимать на вход числа от 1 до 10 включительно, не более. На выходе числа не ограничиваются по величине и могут быть любыми.
//4. (!!!) Калькулятор умеет работать только с целыми числами.
//5. (!!!) Калькулятор умеет работать только с арабскими или римскими цифрами одновременно, при вводе пользователем строки вроде 3 + II калькулятор должен выбросить исключение и прекратить свою работу.
//6. (!!!) При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими.
//7. (!!!) При вводе пользователем неподходящих чисел приложение выбрасывает исключение и завершает свою работу.
//8. (!!!) При вводе пользователем строки, не соответствующей одной из вышеописанных арифметических операций, приложение выбрасывает исключение и завершает свою работу.
//9. (!!!) Результатом операции деления является целое число, остаток отбрасывается.
//10. (!!!) Результатом работы калькулятора с арабскими числами могут быть отрицательные числа и ноль. Результатом работы калькулятора с римскими числами могут быть только положительные числа, если результат работы меньше единицы, выбрасывается исключение

public class Main {

    //Функция Main
    public static void main(String[] args){

        //Приветствие в консоль
        System.out.println("""
                ------------Добро пожаловать-----------
                ---Калькулятор Арабскмх/Римских цифр---
                            
                            Введите пример:
                """);

        //Ввод пользователя с клавиатуры
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        //Вывод решения в консоль
        System.out.println("\nОтвет: " + calc(input));
    }

    //Калькулятор
    public static String calc(String input){
        String[] output = {};
        if(input.contains(" ")){
            output = input.split(" "); // [III] [+] [I]   [3] [+] [1]
            input = input.replaceAll("\\s", ""); // III+I   3+1
        }
        else{
            System.out.println("""
                      
                      Ошибка!
                      Проверьте правильность ввода по примеру:
                      1 + 1
                      I + I""");
            System.exit(1);
        }

        String[] operands = input.split("[*/+-]"); //[III] [1]   [3] [1]
        String[] arabic = new String[2];
        String answer = "";

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
            for(int i = 0;i<2;i++){
                arabic[i] = String.valueOf(toArabic(operands[i]));
            }
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
                      Способы решения:
                      1. Используйте числа в диапазоне 1-10 включительно
                      2. Используйте целые числа""");
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
            try{
                answer = String.valueOf(toRoman(agregate));
            }
            catch(Exception e){
                System.out.println("""
                      
                      Ошибка!
                      Римские цифры не могут быть отрицательными""");
                System.exit(1);
            }
        }
        return answer;
    }

    //Метод конвертирующий Арабские -> Римские
    public static String toRoman(int arabic) {
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

        int l =  map.floorKey(arabic);
        if ( arabic == l ) {
            return map.get(arabic);
        }
        return map.get(l) + toRoman(arabic-l);
    }

    //Метод конвертирующий Римские -> Арабские
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
                      3. Используйте целые числа
                      4. Используйте только Арабские/Римские цифры одновременно""");
            System.exit(1);
        }
        return 0;
    }
}

