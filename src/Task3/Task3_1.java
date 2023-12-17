package Task3;

import java.util.ArrayList;
import java.util.Scanner;

public class Task3_1 {

    double A, B, F, eps, h;
    int n, M, N = 10;
    double[] arrayX;
    double[] arrayFx;

    Task3_1(double A, double B, int M, double eps) {
        this.A = A;
        this.B = B;
        this.M = M;
        this.arrayX = new double[M];
        this.arrayFx = new double[M];
        this.eps = eps;
        this.h = (B-A)/N;
    }

    double f(double x){
        return Math.log(1+x);
    }

    /**
     Заполнение таблицы узлов с шагом h = (A - B)/m
     */
    void table(){
        System.out.println("         x           |       f(x)   ");
        System.out.println("----------------------------------------------------------");
        double h = (this.B-this.A)/(this.M-1);
        for (int i = 0; i < this.M; i++) {
            this.arrayX[i] = this.A + h*i;
            this.arrayFx[i] = f(this.arrayX[i]);
            System.out.printf("%-20s | %-20s \n", this.arrayX[i], this.arrayFx[i]);
        }
        System.out.println("----------------------------------------------------------\n");
    }

    /**
     Проверка на монотонность
     */

    boolean monotone(){
        if (this.arrayFx[0] < this.arrayFx[this.M-1]){
            for (int i = 1; i < this.M; i++) {
                if(this.arrayFx[i - 1] > this.arrayFx[i]) return false;
            }
        }
        else if(this.arrayFx[0] > this.arrayFx[this.M-1]){
            for (int i = 1; i < this.M; i++) {
                if(this.arrayFx[i - 1] < this.arrayFx[i]) return false;
            }
        }
        return true;
    }


    void sortFunction (int low, int high){
        if (this.arrayX.length == 0 ) return;
        if (low < high) {
            int p = low + (high - low)/2;
            double middle = Math.abs(this.arrayX[p]-this.F);
            int beg = low;
            int end = high;
            while (beg <= end){
                while(Math.abs(this.arrayX[beg]-this.F) < middle) beg++;
                while(middle < Math.abs(this.arrayX[end]-this.F)) end--;
                if (beg <= end) {
                    double h = this.arrayX[beg];
                    this.arrayX[beg] = this.arrayX[end];
                    this.arrayX[end] = h;
                    h=this.arrayFx[beg];
                    this.arrayFx[beg] = this.arrayFx[end];
                    this.arrayFx[end] = h;
                    beg++;
                    end--;
                }
            }
            if (low < end) sortFunction(low,end);
            if (beg < high) sortFunction(beg,high);
        }
    }

    void sort(){
        sortFunction(0,(this.M-1));
        System.out.println("Отсортированная таблица:");
        System.out.println("         x           |       f(x)   ");
        System.out.println("----------------------------------------------------------\n");
        for (int i = 0; i < this.M; i++) {
            System.out.printf("%-20s | %-20s \n", arrayX[i], this.arrayFx[i]);
        }
        System.out.println("----------------------------------------------------------\n");
    }


    /**
     Реализация первого способа решения
     */

    void firstMethod() {                 // lagrange for reversed table
        if(!this.monotone()) {
            System.out.println("Возможно решение только 2 способом");
            this.secondMethod();
        }
        System.out.println("1 способ решения");
        System.out.println("----------------------------------------------------------\n");
        this.sort();
        double res = 0;
        for (int i = 0; i < n; i++) {
            double p = this.arrayX[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    p *= (this.F - this.arrayFx[j]) / (this.arrayFx[i] - this.arrayFx[j]);
                }
            }
            res += p;
        }
        System.out.println("Значение аргумента, полученное способом 1: x = " + res);
        System.out.println("------------------------------------------------------------------");
        System.out.println("Абсолютный модуль невязки: " + Math.abs(f(res) - F));
        System.out.println("------------------------------------------------------------------\n");
    }

    /**
     Реализация второго способа решения
     */
    void secondMethod() {

        System.out.println("2 способ решения");

        ArrayList<Double> roots = this.bisectionMethod(this.rootSeparation());

        for (Double root : roots) {
            System.out.println(" x = " + root);
            System.out.println("Модуль невязки: " + Math.abs(getIntPolyVal(root) - F));
            System.out.println("------------------------------------------------------------------");
        }
    }
    double getIntPolyVal(double x){
        double res = 0, product = 1;
        for (int i = 0; i < n; i++ ){
            res += getCoefficient(i)*product;
            product *= (x - arrayX[i]);
        }
        return res - F;
    }

    double getCoefficient(int ind){
        if(ind == 0)
            return arrayFx[0];
        double res = 0;
        for (int i = 0; i <= ind; i++){
            double product = 1;
            for (int j = 0; j<= ind; j++){
                if (i != j)
                    product *= (arrayX[i] - arrayX[j]);
            }
            res += f(arrayX[i])/product;
        }
        return res;
    }

    ArrayList<Double> rootSeparation() {
        ArrayList<Double> segments = new ArrayList<>();
        double x1 = A, x2 = x1 + h;
        double y1 = getIntPolyVal(x1), y2;
        int count = 0;
        while (x2 <= this.B){
            y2 = getIntPolyVal(x2);
            if(y1 * y2 <= 0){
                segments.add(x1);
                System.out.println("[" + x1 + ", " + x2 + "]\n");
                count++;
            }
            x1 = x2;
            x2 = x1 + h;
            y1 = y2;
        }
        if (count == 0)
            System.out.println("Для заданного значения F на заданном отрезке [A, B] решение не найдено, попробуйте изменить значения F и [A, B]");

        return segments;
    }

    ArrayList<Double> bisectionMethod(ArrayList<Double> segments) {

//        int len = segments.size();
        ArrayList<Double> roots = new ArrayList<>();

        for (double a : segments) {
            double b = a + h;
            double center;

            while ((b - a) > 2 * this.eps) {
                center = (a + b) / 2;
                if (getIntPolyVal(a) * getIntPolyVal(center) <= 0)
                    b = center;
                else
                    a = center;

            }
            roots.add((a + b) / 2);
        }
        return roots;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double A = 0, B = 1, eps = Math.pow(10, -8), F;
        int n = 7, M = 11;  // M = m+1

        System.out.println("Задание 3.1 Задача обратного интерполирования");
        System.out.println("Вариант 2");
        System.out.println("Функция f(x) = ln(1 + x)");
        System.out.println("Отрезок [" + A + ", " + B + "]; m+1 = " + M + "; n = " + n + "; eps = 10^(-8)");
        System.out.println("----------------------------------------------------------");

        boolean t = true;
        while (t) {
            System.out.println("Ввести другие параметры? Да - 1, нет - 0");
            if (input.nextInt() == 1) {
                System.out.println("Введите следующие параметры задачи:");

                System.out.println("Введите нижнюю границу");
                System.out.print("A = ");
                A = input.nextDouble();

                System.out.println("Введите верхнюю границу");
                System.out.print("B = ");
                B = input.nextDouble();

                System.out.println("Введите число значений в таблице");
                System.out.print("m+1 = ");
                M = input.nextInt();

                System.out.println("Введите погрешность eps = 10^(-*)");
                System.out.print("* = ");
                eps = Math.pow(10, input.nextDouble());
            }

            Task3_1 reverseInterpolation = new Task3_1(A, B, M, eps);
            reverseInterpolation.table();


            boolean t1 = true;
            while (t1){
                System.out.println("Введите степень интерполяционного многочлена");
                System.out.print("n = ");
                n = input.nextInt();
                while (n <= 0 || n > M - 1) {
                    System.out.println("Значение n должно быть больше 0 и меньше значения m, повторите ввод");
                    System.out.println("n = ");
                    n = input.nextInt();
                }
                System.out.println("Степень интерполяционного многочлена: n = " + n);

                System.out.println("Введите значение F:");
                System.out.print("F = ");
                F = input.nextDouble();
                System.out.println("Значение F: F = " + F);

                reverseInterpolation.F = F;
                reverseInterpolation.n = n;
                System.out.println("----------------------------------------------------------");

                reverseInterpolation.firstMethod();
                reverseInterpolation.secondMethod();

                System.out.println("Хотите ввести новые значения n и x? да - 1, нет - 0");
                if (input.nextInt() == 0) {
                    t1 = false;
                }

            }

            System.out.println("продолжить работу? да - 1, нет - 0");

            if(input.nextInt() == 0)
                t = false;

        }

    }


}
