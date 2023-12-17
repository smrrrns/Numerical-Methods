package Task5;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;

import java.util.ArrayList;
import java.util.Scanner;

public class Task5_3 {
    double eps = Math.pow(10, -12);
    Task5_3(){}

    void gaussCompound(UnivariateFunction f, int N, int m, double A, double B){
        double J = fInt(f, A, B);
        System.out.println("Точное значние интеграла от f(x) по [" + A + "," + B + "]:                                J = " + J);
        double h = (B - A) / m;
        double Jgc = 0;
        System.out.println("\n N = " + N + ",   m = " + m);
        System.out.println("           x            |           C            ");
        System.out.println("-------------------------------------------------");

        ArrayList<Double> roots = this.secantMethod(this.rootSeparation(N), N);
        for (int j = 0; j < m; j++) {
            for (int k = 0; k < N; k++) {
                Jgc += this.coefficient(roots, N, k) * f.value(h / 2 * roots.get(k) + A + j * h + h/2);
            }
        }
        Jgc *= h / 2;

        for (int k = 0; k < N; k++) {
            System.out.printf("%-23s | %-23s \n", roots.get(k) , this.coefficient(roots, N, k));
            System.out.println();
        }


        System.out.println("-----------------------------------------------------\n");
        System.out.println("Значение интеграла от f(x) по [" + A + "," + B + "], полученное при помощи  СКФ Гаусса при N = " + N + ", m = " + m + " :   Jg = " + Jgc);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(J - Jgc));

    }

    double fInt(UnivariateFunction f, double A, double B){
        UnivariateIntegrator l = new SimpsonIntegrator();
        return l.integrate(1000, f, A, B);
    }
    double legendre(double x, int n){
        if(n == 0)
            return 1;
        else if(n == 1)
            return x;
        else
            return (2 * n - 1) * x * legendre(x, n - 1) / n  - (n - 1) * legendre(x, n-2)/ n;
    }
    double coefficient(ArrayList<Double> roots, int n, int k){
        return 2 * (1 - Math.pow(roots.get(k), 2)) / (Math.pow(n,2) * Math.pow(legendre(roots.get(k),n-1),2));
    }
    ArrayList<Double> rootSeparation(int n) {
        double A = -1, B = 1;
        ArrayList<Double> segments = new ArrayList<>();
        double h = (B - A)/1000;
        double x1 = A, x2 = x1 + h;
        double y1 = legendre(x1, n), y2;
        while (x2 <= B){
            y2 = legendre(x2, n);
            if(y1 * y2 <= 0){
                segments.add(x1);
            }
            x1 = x2;
            x2 = x1 + h;
            y1 = y2;
        }

        return segments;
    }
    ArrayList<Double> secantMethod(ArrayList<Double> segments, int n) {
        ArrayList<Double> roots = new ArrayList<>();
        double h = (segments.get(segments.size()-1) - segments.get(0))/1000;
        for (double a : segments) {
            double b = a + h;
            double previous = (a + b) / 2, current = Math.random() * (b - a) + a;

            while (Math.abs(current - previous) >= this.eps) {
                double temp = current;
                current = current - legendre(current, n) * (current - previous) / (legendre(current, n) - legendre(previous, n));
                previous = temp;
            }
            roots.add(current);

        }
        return roots;
    }


    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);

        System.out.println("Задание 5.2 Вычисление интегралов при помощи КФ Гаусса и Меллера");
        System.out.println("Вариант 2");
        System.out.println("-------------------------------------------------------------------------");


        boolean t = true;
        while (t){
            UnivariateFunction f = x ->  Math.sin(x) * Math.pow(x, 0.25);
            double A = 0, B = 1;
            int N, m;

            System.out.println("f(x) = sin(x),   p(x) = x^(1/4),   [A, B] = [0, 1]");

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

            System.out.print("Введите количество узлов: N = ");
            N = input.nextInt();
            if(N < 1) {
                System.out.println("Количество узлов должно быть не меньше 1. Повторите ввод.");
                System.out.print("N = ");
                N = input.nextInt();
            }

            System.out.print("Введите число промежутков деления [" + A + ", " + B + "]: m = ");
            m = input.nextInt();

            if(m <= 0){
                System.out.println("Число промежутков деления должно быть > 0. Повторите ввод.");
                System.out.print("m = ");
                m = input.nextInt();
            }

            Task5_3 t3 = new Task5_3();
            t3.gaussCompound(f, N, m, A, B);


            System.out.println("\nПродолжить работу? да - 1, нет - 0");
            if(input.nextInt() == 0)
                t = false;
        }
    }
}
