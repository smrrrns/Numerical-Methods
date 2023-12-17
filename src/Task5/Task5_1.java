package Task5;

import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.UnivariateFunction;

import java.util.Scanner;

public class Task5_1 {
    double A, B;
    Task5_1(double A, double B){
        this.A = A;
        this.B = B;
    }


    double integrate(UnivariateFunction f, double A, double B){
        UnivariateIntegrator l = new SimpsonIntegrator();
        return l.integrate(10000, f, A, B);
    }

    double moment0(double A, double B){
        return (4 * B * Math.pow(B, 0.25)) / 5 - B * Math.pow(A, 0.25)/5;
    }

    double moment1(double A, double B){
        return (4 * Math.pow(B, 2) * Math.pow(B, 0.25))/9 - ((4 * Math.pow(A, 2) * Math.pow(A, 0.25))/9);
    }

    double moment2(double A, double B){
        return (4 * Math.pow(B, 3) * Math.pow(B, 0.25))/13 - ((4 * Math.pow(A, 3) * Math.pow(A, 0.25))/13);
    }

    double moment3(double A, double B){
        return (4 * Math.pow(B, 4) * Math.pow(B, 0.25))/17 - ((4 * Math.pow(A, 4) * Math.pow(A, 0.25))/17);
    }

    double testPoly1(double A, double B){
        return ((4 * B * B * Math.pow(B, 0.25)) / 3) - ((4 * Math.pow(B, 0.25) * B) / 5) - (((4 * A * A * Math.pow(A, 0.25)) / 3) - (4 * Math.pow(A, 0.25) * A) / 5);
    }

    double testPoly2(double A, double B){
        return ((12 * Math.pow(B,4) * Math.pow(B, 0.25)) / 17) - ((4 * Math.pow(B,2) * Math.pow(B, 0.25)) / 9) + ((4 * Math.pow(B, 0.25) * B) / 5) -
                ((12 * Math.pow(A,4) * Math.pow(A, 0.25)) / 17) - ((4 * Math.pow(A,2) * Math.pow(A, 0.25)) / 9) + ((4 * Math.pow(A, 0.25) * A) / 5);
    }

    double checkIQF(){
        UnivariateFunction f = x ->  (3 * x - 1);
        System.out.println("\nПроверка точности ИКФ на многочлене (N - 1) степени q(x) =  3 * x - 1\n");
        System.out.println("-------------------------------------------------------------------------");
        return this.IQF(f);
    }

    double checkQFHADA(){
        UnivariateFunction f = x ->  (3 * Math.pow(x, 3) - x + 1);
        System.out.println("\nПроверка точности КФ НАСТ на многочлене (2N - 1) степени q(x) =  3x^3 - x + 1\n");
        System.out.println("-------------------------------------------------------------------------");
        return this.QFHADA(f);
    }

    double QFHADA(UnivariateFunction f){
        double m0 = moment0(this.A, this.B);
        double m1 = moment1(this.A, this.B);
        double m2 = moment2(this.A, this.B);
        double m3 = moment3(this.A, this.B);


        double a0 = (m2 * m2 - m1 * m3) / (m1 * m1 - m0 * m2);
        double a1 = (m0 * m3 - m1 * m2) / (m1 * m1 - m0 * m2);

        double D = Math.sqrt(a1 * a1 - 4 * a0);

        double x1 = (- a1 - D)/2;
        double x2 = (- a1 + D)/2;

        double A1 = (m0 * x2 - m1) / (x2 - x1);
        double A2 = (m1 - m0 * x1) / (x2 - x1);

        System.out.println("            x           |            C          ");
        System.out.println("------------------------------------------------");
        System.out.printf("%-23s | %-23s \n", x1 , A1);
        System.out.println();
        System.out.printf("%-23s | %-23s \n", x2 , A2);
        System.out.println();
        System.out.println("\nМоменты весовой функции: " + m0 + ", " + m1);

        double res = A1 * f.value(x1) + A2 * f.value(x2);
        System.out.println("\nЗначение интеграла по [" + this.A + ", " + this.B + "] полученное при помощи КФ НАСТ: " + res);

        return res;
    }

    double IQF(UnivariateFunction f){
        double m0 = moment0(this.A, this.B);
        double m1 = moment1(this.A, this.B);

        double x1 = this.A + (this.B-this.A)/2;
        double x2 = this.B;

        double A1 = (m0 * x2 - m1) / (x2 - x1);
        double A2 = (m1 - m0 * x1) / (x2 - x1);

        System.out.println("            x           |            C          ");
        System.out.println("------------------------------------------------");
        System.out.printf("%-23s | %-23s \n", x1 , A1);
        System.out.println();
        System.out.printf("%-23s | %-23s \n", x2 , A2);
        System.out.println();
        System.out.println("Моменты весовой функции: " + m0 + ", " + m1);

        double res = A1 * f.value(x1) + A2 * f.value(x2);
        System.out.println("\nЗначение интеграла по [" + A + ", " + B + "] полученное при помощи ИКФ: " + res);

        return res;
    }



    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double A = 0, B = 1;
        System.out.println("Задание 5.1 Приближённое вычисление интегралов при помощи квадратурных формул Наивысшей Алгебраической Степени Точности");
        System.out.println("Вариант 2");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------\n");

        System.out.println("f(x) = sin(x),  p(x) = x^(1/4), [A, B] = [0, 1], N = 2");
        UnivariateFunction f = Math::sin;

        boolean t = true;
        while (t){
            System.out.println("Ввести другие значения A и B? Да - 1, нет - 0");
            if (input.nextInt() == 1) {

                System.out.print("Введите начало отрезка интегрирования: A = ");
                A = input.nextDouble();

                if(A < 0){
                    System.out.println("Значение A должно быть > 0. Повторите ввод.");
                    System.out.print("A = ");
                    A = input.nextInt();
                }
                System.out.print("Введите конец отрезка интегрирования: B = ");
                B = input.nextDouble();

                if(B < 0){
                    System.out.println("Значение B должно быть > 0. Повторите ввод.");
                    System.out.print("B = ");
                    B = input.nextInt();
                }
            }

            Task5_1 t5_1 = new Task5_1(A, B);

            double exactInt = t5_1.integrate(f, A, B);
            double IQFInt = t5_1.IQF(f);

            System.out.println("Точное значение интеграла  p(x)f(x) по [" + A + ", " + B + "] = " + exactInt);
            System.out.println("Абсолютная фактическая погрешность: " + Math.abs(exactInt - IQFInt));

            System.out.println("\n-------------------------------------------------------------------------\n");

            // проверка ИКФ
            double exactPolyInt1 = t5_1.testPoly1(A, B);
            double IQFPolyInt = t5_1.checkIQF();
            System.out.println("Точное значение интеграла p(x)q(x) по [" + A + ", " + B + "] = " + exactPolyInt1);
            System.out.println("Абсолютная фактическая погрешность: " + Math.abs(exactPolyInt1 - IQFPolyInt));

            double HADAInt = t5_1.QFHADA(f);

            System.out.println("Точное значение интеграла  p(x)f(x) по [" + A + ", " + B + "] = " + exactInt);
            System.out.println("Абсолютная фактическая погрешность: " + Math.abs(exactInt - HADAInt));

            //проверка Кф НАСТ
            double exactPolyInt2 = t5_1.testPoly2(A, B);
            double QFHADAPolyInt = t5_1.checkQFHADA();
            System.out.println("Точное значение интеграла p(x)q(x) по [" + A + ", " + B + "] = " + exactPolyInt2);
            System.out.println("Абсолютная фактическая погрешность: " + Math.abs(exactPolyInt2 - QFHADAPolyInt));

            System.out.println("\nПродолжить работу? да - 1, нет - 0");
            if(input.nextInt() == 0)
                t = false;
        }
    }

}
