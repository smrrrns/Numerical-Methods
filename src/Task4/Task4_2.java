package Task4;

import java.util.Scanner;
public class Task4_2 {

    double A, B, J, h, w, q, z;
    int n, N;

    Task4_2(double A, double B, int n, int N){
        this.A = A;
        this.B = B;
        this.n = n;
        this.N = N;
        this.h = (B-A)/N;
        System.out.println("Шаг деления h =" + this.h);

        double w = 0;
        double q = 0;
        double z;

        if(n == 0){
            for(int i = 1; i < N; i++){
                w += p0(A + i * this.h, false);
                q += p0(A + i * this.h + this.h / 2, false);
            }
            q += p0(A + this.h / 2, false);
            z = p0(A, false) + p0(B, false);
        }

        else if (n==1){
            for(int i = 1; i < N; i++){
                w += p1(A + i * this.h, false);
                q += p1(A + i * this.h + this.h / 2, false);
            }
            q += p1(A + this.h / 2, false);
            z = p1(A, false) + p1(B, false);
        }

        else if (n==2){
            for(int i = 1; i < N; i++){
                w += p2(A + i * this.h, false);
                q += p2(A + i * this.h + this.h / 2, false);
            }
            q += p2(A + this.h / 2, false);
            z = p2(A, false) + p2(B, false);
        }

        else if (n==3){
            for(int i = 1; i < N; i++){
                w += p3(A + i * this.h, false);
                q += p3(A + i * this.h + this.h / 2, false);
            }
            q += p3(A + this.h / 2, false);
            z = p3(A, false) + p3(B, false);
        }

        else {
            for(int i = 1; i < N; i++){
                w += f(A + i * this.h, false);
                q += f(A + i * this.h + this.h / 2, false);
            }
            q += f(A + this.h / 2, false);
            z = f(A, false) + f(B, false);
        }

        this.w = w;
        this.q = q;
        this.z = z;
    }

    double p0(double x, boolean integrate){
        if(!integrate)
            return 7;
        else
            return 7 * x;
    }
    double derP0(){
        return 0;
    }
    double p1(double x,boolean integrate){
        if(!integrate)
            return 3.3 * x - 3;
        else return (3.3 * Math.pow(x, 2)) / 2 - 3 * x;
    }
    double derP1(int deg){
        if(deg == 1)
            return 3.3;
        else return 0;
    }
    double p2(double x, boolean integrate){
        if(!integrate)
            return 9 * Math.pow(x, 2) - 7 * x + 1.3;
        else return (3 * Math.pow(x, 3)) - 3.5 * Math.pow(x, 2) + 1.3 * x;
    }
    double derP2(double x, int deg){
        if(deg == 1)
            return 18 * x - 7;
        else if (deg == 2)
            return 18;
        else return 0;
    }
    double p3(double x, boolean integrate){
        if(!integrate)
            return 3 * Math.pow(x, 3) + 1.7 * Math.pow(x, 2) + 3.9 * x - 17;
        return 3 * Math.pow(x, 4) / 4 + 17 * Math.pow(x, 3) / 30 + 39 * Math.pow(x, 2) / 20 - 17*x;
    }
    double derP3(double x, int deg){
        if(deg == 1)
            return 9 * Math.pow(x, 2) + 3.4 * x + 3.9;
        else if (deg == 2)
            return 18 * x + 3.4;
        else
            return 0;
    }
    double f(double x, boolean integrate){
        if(!integrate)
            return 3 * Math.exp(x) + 3 * Math.pow(x, 3);
        else return 3 * Math.exp(x) + 3 * Math.pow(x, 4)/4;
    }
    double derF(double x, int deg){
        if(deg == 1)
            return 3 * Math.exp(x) + 9 * Math.pow(x, 2);
        else if (deg == 2)
            return 3 * Math.exp(x) + 18 * x;
        else if(deg == 4)
            return 3 * Math.exp(x);
        else return 0;
    }
    double derivative(double x, int deg){
        if(this.n == 0)
            return derP0();
        else if(this.n == 1)
            return derP1(deg);
        else if(this.n == 2)
            return derP2(x, deg);
        else if(this.n == 3)
            return derP3(x, deg);
        else
            return derF(x, deg);
    }

    double left_rectangle(){
        if(this.n == 0)
            return this.h * (p0(A, false) + this.w);
        else if(this.n == 1)
            return this.h * (p1(A, false) + this.w);
        else if(this.n == 2)
            return this.h * (p2(A, false) + this.w);
        else if(this.n == 3)
            return this.h * (p3(A, false) + this.w);
        else
            return this.h * (f(A, false) + this.w);

    }
    double right_rectangle(){
        if(this.n == 0)
            return this.h * (this.w + p0(B, false));
        else if(this.n == 1)
            return this.h * (this.w + p1(B, false));
        else if(this.n == 2)
            return this.h * (this.w + p2(B, false));
        else if(this.n == 3)
            return this.h * (this.w + p3(B, false));
        else
            return this.h * (this.w + f(B, false));


    }
    double mid_rectangle(){
        return this.h * q;
    }
    double trapeze(){
        return this.h/2 * (this.z + 2 * this.w);
    }
    double simpson(){
        return this.h/6 * (this.z + 2 * this.w + 4 * this.q);
    }

    void calcIntegral(){
        if(this.n == 0){
            System.out.println("f(x) = 7");
            this.J = p0(this.B, true) - p0(this.A, true);
        }
        else if(this.n == 1){
            System.out.println("f(x) = 3.3x - 3");
            this.J = p1(this.B, true) - p1(this.A, true);
        }
        else if(this.n == 2){
            System.out.println("f(x) = 9x^2 - 7x + 1.3");
            this.J = p2(this.B, true) - p2(this.A, true);
        }
        else if(this.n == 3){
            System.out.println("f(x) = 3x^3 + 1.7x^2 + 3.9x - 17");
            this.J = p3(this.B, true) - p3(this.A, true);
        }
        else{
            System.out.println("f(x) = 3*e^x + 3*x^3");
            this.J = f(this.B, true) - f(this.A, true);
        }

        System.out.println("Точное значние интеграла от f(x) по [" + this.A + ", " + this.B + "]: J = " + this.J);

        System.out.println();

        double lJ = left_rectangle();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] (СКФ левых прямоугольников): " + lJ);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(lJ-this.J));
        System.out.println("Относительная погрешность: " + Math.abs(lJ-this.J) / Math.abs(this.J));
        System.out.println("Теоретическая погрешность: " + Math.pow(this.B-this.A, 2)/(2 * this.N) * Math.abs(derivative(this.B, 1)));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double rJ = right_rectangle();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] (СКФ правых прямоугольников): " + rJ);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(rJ-this.J));
        System.out.println("Относительная погрешность: " + Math.abs(rJ-this.J) / Math.abs(this.J));
        System.out.println("Теоретическая погрешность: " + Math.pow(this.B-this.A, 2)/(2 * this.N) * Math.abs(derivative(this.B, 1)));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double mJ = mid_rectangle();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] (СКФ средних прямоугольников): " + mJ);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(mJ-this.J));
        System.out.println("Относительная погрешность: " + Math.abs(mJ-this.J) / Math.abs(this.J));
        System.out.println("Теоретическая погрешность: " + Math.pow(this.B-this.A, 3)/(24 * Math.pow(this.N, 2)) * Math.abs(derivative(this.B, 2)));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double tJ = trapeze();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] (СКФ трапеций): " + tJ);
        System.out.println("Абсолютная фактическая погрешность для КФ трапеции: " + Math.abs(tJ-this.J));
        System.out.println("Относительная погрешность: " + Math.abs(tJ-this.J) / Math.abs(this.J));
        System.out.println("Теоретическая погрешность: " + Math.pow(this.B-this.A, 3)/(12 * Math.pow(this.N, 2)) * Math.abs(derivative(this.B, 2)));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double sJ = simpson();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] (СКФ Симпсона): " + sJ);
        System.out.println("Абсолютная фактическая погрешность для КФ Симпсона (парабол): " + Math.abs(sJ-this.J));
        System.out.println("Относительная погрешность: " + Math.abs(sJ-this.J) / Math.abs(this.J));
        System.out.println("Теоретическая погрешность: " + Math.pow(this.B-this.A, 5)/(8220 * Math.pow(this.N, 4)) * Math.abs(derivative(this.B, 4)));
        System.out.println("-----------------------------------------------------------------------------------------------------");

    }



    public static void main(String[] args) {
        int n, N;
        double A, B;

        System.out.println("Задание 4.2 Приближённое вычисление интеграла по составным квадратурным формулам");
        System.out.println("-------------------------------------------------------------------------");
        Scanner input = new Scanner(System.in);

        boolean t = true;
        while (t){
            System.out.println("p(x) = 1 \n");
            System.out.println("0) f(x) = 7");
            System.out.println("1) f(x) = 3.3x - 3");
            System.out.println("2) f(x) = 9x^2 - 7x + 1.3");
            System.out.println("3) f(x) = 3x^3 + 1.7x^2 + 3.9x - 17");
            System.out.println("4) f(x) = 3*e^x + 3x^3\n");

            System.out.print("Введите номер функции c которой хотите работать: n = ");
            n = input.nextInt();

            if(n < 0 || n > 4){
                System.out.println("Некорректный номер функции, повторите ввод.");
                System.out.print("Введите номер функции c которой хотите работать: n =");
                n = input.nextInt();
            }
            System.out.println("-------------------------------------------------------------------------");

            System.out.print("Введите начало отрезка интегрирования: A = ");
            A = input.nextDouble();

            System.out.print("Введите конец отрезка интегрирования: B = ");
            B = input.nextDouble();

            System.out.print("Введите число промежутков деления [" + A + ", " + B + "]: N = ");
            N = input.nextInt();

            if(N <= 0){
                System.out.println("Число промежутков деления должно быть > 0. Повторите ввод.");
                System.out.print("N = ");
                N = input.nextInt();
            }

            System.out.println();
            Task4_2 t4_2 = new Task4_2(A, B, n, N);

            t4_2.calcIntegral();
            System.out.println("\n-------------------------------------------------------------------------\n");


            System.out.println("продолжить работу? да - 1, нет - 0");

            if(input.nextInt() == 0)
                t = false;
        }
    }
}
