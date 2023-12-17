package Task2;

import java.util.Scanner;

public class Task2 {

    double A, B, x;

     double[] arrayX, arrayFx;
    //double[][] table;
    int M, n; // M = m+1

    Task2(double A, double B, int M) {
        this.A = A;
        this.B = B;
        this.M = M;
        this.arrayX = new double[M];
        this.arrayFx = new double[M];
    }

    double f(double x){
        return Math.log(1+x);
    }

    /**
     Заполнение таблицы узлов с шагом h = (A - B)/m
     */
    void table(){
        System.out.println("         x           |       f(x)   ");
        double h = (this.B-this.A)/(this.M-1);
        for (int i = 0; i < this.M; i++) {
            this.arrayX[i] = this.A + h*i;
            this.arrayFx[i] = f(this.arrayX[i]);
            System.out.printf("%-20s | %-20s \n", this.arrayX[i], this.arrayFx[i]);
        }
    }
    /**
     Сортировка узлов в таблице
     */
    void sortFunction (int low, int high){
        if (this.arrayX.length == 0 ) return;
        if (low < high) {
            int p = low + (high - low)/2;
            double middle = Math.abs(this.arrayX[p]-this.x);
            int beg = low;
            int end = high;
            while (beg <= end){
                while(Math.abs(this.arrayX[beg]-this.x) < middle) beg++;
                while(middle < Math.abs(this.arrayX[end]-this.x)) end--;
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
        for (int i = 0; i < this.M; i++) {
            System.out.printf("%-20s | %-20s \n", arrayX[i], this.arrayFx[i]);
        }
    }

    /**
     Реализация интерполяции методом Лагранжа
     */
    private double lagrange(){
        double res = 0;
        for (int i = 0; i < n; i++) {
            double p = this.arrayFx[i];
            for (int j = 0; j < n; j++) {
                if(i != j){
                    p *= (this.x-this.arrayX[j])/(this.arrayX[i]-this.arrayX[j]);
                }
            }
            res += p;
        }
        return res;
    }

    /**
     Реализация интерполяции методом Ньютона
     */
    double newton(){
        double res = 0, product = 1;

        for (int i = 0; i < n; i++ ){
            res += getCoefficient(i)*product;
            product *= (x - arrayX[i]);
        }
        return res;
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


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double A = 0, B = 1.5, x;
        int n = 7, M = 16;  // M = m+1

        System.out.println("Задание 2. Задача алгебраического интерполирования");
        System.out.println("Вариант 2");
        System.out.println("Функция f(x) = ln(1 + x)");
        System.out.println("Отрезок [" + A + ", " + B + "]; m+1 = " + M + "; n = " + n);
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


            }

            Task2 algebraicInterpolation = new Task2(A, B, M);

            algebraicInterpolation.table();

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

                System.out.println("Введите точку интерполирования");
                System.out.print("x = ");
                x = input.nextDouble();

                System.out.println("Точка интерполирования: x = " + x);
                System.out.println("Степень интерполяционного многочлена: n = " + n);

                if(algebraicInterpolation.x != x){
                    algebraicInterpolation.x = x;
                    algebraicInterpolation.sort();
                }
                algebraicInterpolation.n = n;

                System.out.println("----------------------------------------------------------");

                double lagrange = algebraicInterpolation.lagrange();
                double newton = algebraicInterpolation.newton();
                double fx = algebraicInterpolation.f(x);

                System.out.println("Значение интерполяционного многочлена, найденное при помощи представления в форме Лагранжа: " + lagrange);
                System.out.println("Значение абсолютной фактической погрешности для формы Лагранжа: " + Math.abs(fx - lagrange));
                System.out.println("----------------------------------------------------------");
                System.out.println("Значение интерполяционного многочлена, найденное при помощи представления в форме Ньютона: " + newton);
                System.out.println("Значение абсолютной фактической погрешности для формы Ньютона: " + Math.abs(fx - newton));
                System.out.println("----------------------------------------------------------");
                System.out.println("f(x) = " + fx);


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
