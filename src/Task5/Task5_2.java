package Task5;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;

import java.util.ArrayList;
import java.util.Scanner;

public class Task5_2 {
    double eps = Math.pow(10, -12);

    Task5_2(){}


    void gauss(int N){
        for (int n = 1; n <= N; n++) {
            System.out.println("N = " + n);
            System.out.println("            x           |            C   ");
            System.out.println("-----------------------------------------------------");

            ArrayList<Double> roots = this.secantMethod(this.rootSeparation(n), n);
            for (int k = 0; k < n; k++) {
                System.out.printf("%-23s | %-23s \n", roots.get(k), this.coefficient(roots, n, k) );
                System.out.println();
            }
            System.out.println("-----------------------------------------------------\n");
        }
    }

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
                System.out.printf("%-23s | %-23s \n", roots.get(k) , this.coefficient(roots, N, k));
                System.out.println();
                Jgc += this.coefficient(roots, N, k) * f.value(h / 2 * roots.get(k) + A + j * h + h/2);
            }
        }

        Jgc *= h / 2;


        System.out.println("-----------------------------------------------------\n");
        System.out.println("Значение интеграла от f(x) по [" + A + "," + B + "], полученное при помощи  КФ Гаусса при N = " + N + ":   Jg = " + Jgc);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(J - Jgc));

    }
    void calcInt(UnivariateFunction f, double A, double B, int[] N){
        double J = fInt(f, A, B);
        System.out.println("Точное значние интеграла от f(x) по [" + A + "," + B + "]:                                J = " + J);

        if(A == -1 && B == 1){
            for (int n: N) {
                double Jg = 0;
                System.out.println("N = " + n);
                System.out.println("            x           |            C          ");
                System.out.println("------------------------------------------------");

                ArrayList<Double> roots = this.secantMethod(this.rootSeparation(n), n);

                for (int k = 0; k < n; k++) {
                    System.out.printf("%-23s | %-23s \n", roots.get(k), this.coefficient(roots, n, k) );
                    System.out.println();

                    Jg += this.coefficient(roots, n, k) * fValue(roots.get(k), f);
                }
                System.out.println("-----------------------------------------------------\n");
                System.out.println("Значение интеграла от f(x) по [" + A + "," + B + "], полученное при помощи  КФ Гаусса при N = " + n + ":   Jg = " + Jg);
                System.out.println("Абсолютная фактическая погрешность: " + Math.abs(J - Jg));
            }
        }
        else {
            for (int n: N) {
                double Jg = 0;
                System.out.println("\n N = " + n);
                System.out.println("           x            |           C            ");
                System.out.println("-------------------------------------------------");

                ArrayList<Double> roots = this.secantMethod(this.rootSeparation(n), n);
                for (int k = 0; k < n; k++) {
                    System.out.printf("%-23s | %-23s \n", ((B - A) / 2 * roots.get(k) + (B + A) / 2), (B - A) / 2 * this.coefficient(roots, n, k));
                    System.out.println();
                    Jg += (B - A) / 2 * this.coefficient(roots, n, k) * fValue((B - A) / 2 * roots.get(k) + (B + A) / 2, f);
                }
                System.out.println("-----------------------------------------------------\n");
                System.out.println("Значение интеграла от f(x) по [" + A + "," + B + "], полученное при помощи  КФ Гаусса при N = " + n + ":   Jg = " + Jg);
                System.out.println("Абсолютная фактическая погрешность: " + Math.abs(J - Jg));
            }
        }
    }

    double rootsMeler(int k, int n){
        return Math.cos(Math.PI * (2.0 * k - 1) / (2 * n));
    }
    void meler(UnivariateFunction f, int[] N){
        for(int n: N){
            System.out.println("\nN = " + n);
            System.out.println("            x           |            C          ");
            System.out.println("------------------------------------------------");
            double Jm = 0;

            for(int k = 1; k <= n; k++){

                if (n % 2 != 0 && k == (n / 2 + 1)){
                    Jm += f.value(0);
                    System.out.printf("%-23s | %-23s \n", 0, Math.PI / n );
                    System.out.println();
                }

                else{
                    Jm += f.value(rootsMeler(k, n));
                    System.out.printf("%-23s | %-23s \n", rootsMeler(k,n) , Math.PI / n );
                    System.out.println();
                }
            }
            Jm *= Math.PI / n;
            System.out.println("-----------------------------------------------------\n");
            System.out.println("Значение интеграла от g(x) по [-1, 1], полученное при помощи  КФ Мелера при N = " + n + ":   Jm = " + Jm);

        }
    }

    double fValue(double x, UnivariateFunction f){
        return f.value(x);
    }

    double fInt(UnivariateFunction f, double A, double B){
        UnivariateIntegrator l = new SimpsonIntegrator();
        return l.integrate(1000, f, A, B);
    }

    void test(UnivariateFunction function, int N){

        double J = fInt(function, -1, 1);

        ArrayList<Double> roots = this.secantMethod(this.rootSeparation(N), N);

        double Jg = 0;
        for (int k = 0; k < N; k++) {
            Jg += this.coefficient(roots, N, k) * fValue(roots.get(k), function);
        }
        System.out.println("Точное значние интеграла от f(x) по [-1, 1]:                                J = " + J);
        System.out.println("Значение интеграла от f(x) по [-1, 1], полученное при помощи  КФ Гаусса:   Jg = " + Jg);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(J - Jg));

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

            System.out.println("Введите цифру от 1 до 4:");
            System.out.println("1 - определить и вывести на печать узлы и коэффициенты КФ Гаусса при N = 1,2,...,8");
            System.out.println("2 - осуществить проверку точности на многочлене наивысшей степени, для которого КФ Гаусса должна быть точна");
            System.out.println("3 - вычислить заданный интеграл при помощи КФ Гаусса с заданным числом узлов");
            System.out.println("4 - вычислить заданный интеграл при помощи КФ Меллера с N1, N2, N3 узлами");

            int toDo = input.nextInt();

            if(toDo == 1){
                int N = 8;
                Task5_2 t1 = new Task5_2();
                t1.gauss(N);
            }
            else if(toDo == 2){
                System.out.println("Проверка точности на многочлене 5 степени: f(x) = 5x^5  + 3x^3 - 9x^2 + x\n");
                UnivariateFunction fTest = x -> 5 * Math.pow(x,5) + 3 * Math.pow(x,3) - 9 * Math.pow(x, 2) + x;
                int N = 3;
                Task5_2 t2 = new Task5_2();
                t2.test(fTest, N);
            }
            else if(toDo == 3){
                System.out.println("f(x) = 1 / sqrt((1 + x^2)(4 + 3x^2)),   [A, B] = [0, 1],  N = 4, 6, 7, 8");
                UnivariateFunction f = x ->  1 / Math.pow((1 + x * x) * (4 + 3 * x * x), 0.5);
                int[] N = new int[4];
                N[0] = 4;
                N[1] = 6;
                N[2] = 7;
                N[3] = 8;
                double A = 0, B = 1;
                System.out.println("Ввести другие значения A и B? Да - 1, нет - 0");
                if (input.nextInt() == 1) {
                    System.out.print("Введите начало отрезка интегрирования: A = ");
                    A = input.nextDouble();

                    System.out.print("Введите конец отрезка интегрирования: B = ");
                    B = input.nextDouble();
                }

                Task5_2 t3 = new Task5_2();
                t3.calcInt(f, A, B, N);
            }
            else {
                int[] N = new int[3];
                System.out.println("g(x) = f(x) / sqrt(1 - x^2), f(x) = exp(2x) ");
                UnivariateFunction f = x -> Math.exp(2 * x);
                System.out.print("Введите количество узлов: N1 = ");
                N[0] = input.nextInt();
                if(N[0] < 1) {
                    System.out.println("Количество узлов должно быть не меньше 1. Повторите ввод.");
                    System.out.print("N1 = ");
                    N[0] = input.nextInt();
                }
                System.out.print("Введите количество узлов: N2 = ");
                N[1] = input.nextInt();
                if(N[1] < 1) {
                    System.out.println("Количество узлов должно быть не меньше 1. Повторите ввод.");
                    System.out.print("N2 = ");
                    N[1] = input.nextInt();
                }
                System.out.print("Введите количество узлов: N3 = ");
                N[2] = input.nextInt();
                if(N[2] < 1) {
                    System.out.println("Количество узлов должно быть не меньше 1. Повторите ввод.");
                    System.out.print("N3 = ");
                    N[2] = input.nextInt();
                }

                Task5_2 t4 = new Task5_2();
                t4.meler(f, N);
            }

            System.out.println("\nПродолжить работу? да - 1, нет - 0");

            if(input.nextInt() == 0)
                t = false;
        }
    }
}
