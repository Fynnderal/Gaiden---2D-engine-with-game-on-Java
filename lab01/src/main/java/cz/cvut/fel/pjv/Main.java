package cz.cvut.fel.pjv;

import java.sql.SQLOutput;
import java.util.Scanner;
import java.lang.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();

        System.out.println(task5(num));
    }
    static void task1()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Zadej dve cisla: ");
        double numA = scanner.nextDouble();
        double numB = scanner.nextDouble();

        String output = String.format("%f + %f = %f\n", numA, numB, numA + numB);
        System.out.println(output);
        System.out.println(numA - numB);
        System.out.println(numA * numB);
        System.out.println(numA / numB);
    }
    static void task3()
    {
        String days[] = new String[] {"Mon", "Tue", "Wed", "Thu", "Fri", "Sut", "Sut"};
        Scanner scanner = new Scanner(System.in);
        System.out.println("Zadej cislo: ");
        if (!scanner.hasNextInt())
        {
            System.out.println("Spatny vstup");
            return;
        }
        int index = scanner.nextInt();
        if (index > 7 || index < 1)
        {
            System.out.println("Spatny vstup");
            return;
        }
        System.out.println(days[scanner.nextInt() - 1]);

    }
    static String task5(int num)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(num);
        stringBuilder.append("=");
        int divisor = 2;
        while (num != 1) {
            while (num % divisor == 0) {
                num /= divisor;
                if (num == 1)
                    stringBuilder.append(divisor);
                else
                    stringBuilder.append(divisor);
                    stringBuilder.append("*");
            }
            divisor++;
        }
        return stringBuilder.toString();

    }
}
