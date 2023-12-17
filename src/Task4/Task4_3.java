package Task4;

import java.util.Scanner;

public class Task4_3 {

    double A, B, J, h, w, q, z;
    int n, N, l;

    Task4_3(double A, double B, int n, int N){
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
    double p1(double x,boolean integrate){
        if(!integrate)
            return 3.3 * x - 3;
        else return (3.3 * Math.pow(x, 2)) / 2 - 3 * x;
    }
    double p2(double x, boolean integrate){
        if(!integrate)
            return 9 * Math.pow(x, 2) - 7 * x + 1.3;
        else return (3 * Math.pow(x, 3)) - 3.5 * Math.pow(x, 2) + 1.3 * x;
    }
    double p3(double x, boolean integrate){
        if(!integrate)
            return 3 * Math.pow(x, 3) + 1.7 * Math.pow(x, 2) + 3.9 * x - 17;
        return 3 * Math.pow(x, 4) / 4 + 17 * Math.pow(x, 3) / 30 + 39 * Math.pow(x, 2) / 20 - 17*x;
    }
    double f(double x, boolean integrate){
        if(!integrate)
            return 3 * Math.exp(x) + 3 * Math.pow(x, 3);
        else return 3 * Math.exp(x) + 3 * Math.pow(x, 4)/4;
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

    double [] calcIntegral(){
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

        double [] res = new double[5];

        res[0] = left_rectangle() ;
        res[1]= right_rectangle();
        res[2]= mid_rectangle();
        res[3] = trapeze();
        res[4]= simpson();

        return res;
    }


    public void rungePrinciple(double[] Jh, double[] Jhl) {

        System.out.println("\n [A,B] = [" + this.A + ", " + this.B + "]");
        System.out.println("\nТочное значние интеграла от f(x): " + this.J);

        double lJr = (this.l * Jhl[0] - Jh[0]) / (this.l - 1);

        System.out.println("\nСКФ левых прямоугольников");
        System.out.println("\nЗначение интеграла от f(x):       " + Jh[0]);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(Jh[0]-this.J));

        System.out.println("\nУточнённое по принципу Рунге значение интеграла от f(x): " + lJr);
        System.out.println("Абсолютная фактическая погрешность:                        " + Math.abs(this.J - lJr));
        System.out.println("-------------------------------------------------------------------------");

        double rJr = (this.l * Jhl[1] - Jh[1]) / (this.l - 1);
        System.out.println("СКФ правых прямоугольников");
        System.out.println("Значение интеграла от f(x):         " + Jh[1]);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(Jh[1]-this.J));
        System.out.println("\nУточнённое по принципу Рунге значение интеграла от f(x): " + rJr);
        System.out.println("Абсолютная фактическая погрешность:                        " + Math.abs(this.J - rJr));
        System.out.println("-------------------------------------------------------------------------");

        double mJr = (Math.pow(this.l,2) * Jhl[2] - Jh[2]) / (Math.pow(this.l,2) - 1);
        System.out.println("СКФ средних прямоугольников");
        System.out.println("Значение интеграла от f(x):         " + Jh[2]);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(Jh[2]-this.J));
        System.out.println("\nУточнённое по принципу Рунге значение интеграла от f(x): " + mJr);
        System.out.println("Абсолютная фактическая погрешность:                        " + Math.abs(this.J - mJr));
        System.out.println("-------------------------------------------------------------------------");

        double tJr = (Math.pow(this.l,2) * Jhl[3] - Jh[3]) / (Math.pow(this.l,2) - 1);
        System.out.println("СКФ трапеций");
        System.out.println("Значение интеграла от f(x):         " + Jh[3]);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(Jh[3]-this.J));
        System.out.println("Уточнённое по принципу Рунге значение интеграла от f(x): " + tJr);
        System.out.println("Абсолютная фактическая погрешность:                      " + Math.abs(this.J - tJr));
        System.out.println("-------------------------------------------------------------------------");

        double sJr = (Math.pow(this.l,4) * Jhl[4] - Jh[4]) / (Math.pow(this.l,4) - 1);
        System.out.println("Значение интеграла от f(x):         " + Jh[4]);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(Jh[4]-this.J));
        System.out.println("Уточнённое по принципу Рунге значение интеграла от f(x): " + sJr);
        System.out.println("Абсолютная фактическая погрешность:                      " + Math.abs(this.J - sJr));
        System.out.println("-------------------------------------------------------------------------");
    }


    public static void main(String[] args) {
        int n, N, l;
        double A, B;
        double[] Jh, Jhl;

        System.out.println("Задание 4.3 Приближённое вычисление интеграла по составным квадратурным формулам");
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

            System.out.print("Введите число, на которое хотите домножить количество промежутков N: l = ");
            l = input.nextInt();

            if(l < 0){
                System.out.println("Значение l должно быть > 0, повторите ввод.");
                l = input.nextInt();
            }

            System.out.println();
            Task4_3 t4_3 = new Task4_3(A, B, n, N);
            Jh = t4_3.calcIntegral();

            Task4_3 t4_3l = new Task4_3(A, B, n, N*l);
            t4_3.l = l;
            Jhl = t4_3l.calcIntegral();

            t4_3.rungePrinciple(Jh, Jhl);


            System.out.println("продолжить работу? да - 1, нет - 0");

            if(input.nextInt() == 0)
                t = false;
        }
    }
}
