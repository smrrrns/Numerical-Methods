package Task4;

import java.util.Scanner;

public class Task4_1 {

    double A, B, J;
    int n;

    Task4_1(double A, double B, int n){
        this.A = A;
        this.B = B;
        this.n = n;
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
        else return (3.3 / 2 * Math.pow(x, 2)) - 3 * x;
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
            return  2 * Math.sin(2 * x) + 3 * Math.cos(3 * x) * Math.pow(x, 3);
        else return -Math.cos(2 * x) + Math.pow(x, 3) * Math.sin(3 * x) + Math.pow(x, 2) * Math.cos(3 * x) - (2 * x * Math.sin(3 * x))/3 - (2 * Math.cos(3 * x))/9;
    }

    void calcIntegral(){
        if(this.n == 0){
            System.out.println("f(x) = 7");
            this.J = p0(this.B, true) - p0(this.A, true);
            System.out.println("Точное значние интеграла от f(x) по [" + this.A + ", " + this.B + "]: J = " + J);
        }
        else if(this.n == 1){
            System.out.println("f(x) = 3.3x - 3");
            this.J = p1(this.B, true) - p1(this.A, true);
            System.out.println("Точное значние интеграла от f(x) по [" + this.A + ", " + this.B + "]: J = " + J);
        }

        else if(this.n == 2){
            System.out.println("f(x) = 9x^2 - 7x + 1.3");
            this.J = p2(this.B, true) - p2(this.A, true);
            System.out.println("Точное значние интеграла от f(x) по [" + this.A + ", " + this.B + "]: J = " + J);
        }

        else if(this.n == 3){
            System.out.println("f(x) = 3x^3 + 1.7x^2 + 3.9x - 17");
            this.J = p3(this.B, true) - p3(this.A, true);
            System.out.println("Точное значние интеграла от f(x) по [" + this.A + ", " + this.B + "]: J = " + J);
        }

        else {
            System.out.println("f(x) = 2sin(2x) + 3cos(3x)*x^3");
            this.J = f(this.B, true) - f(this.A, true);
            System.out.println("Точное значние интеграла от f(x) по [" + this.A + ", " + this.B + "]: J = " + J);
        }

        System.out.println();

        double lJ = left_rectangle();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] при помощи  КФ левого прямоугольника: " + lJ);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(lJ-this.J));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double rJ = right_rectangle();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] при помощи  КФ правого прямоугольника: " + rJ);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(rJ-this.J));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double mJ = mid_rectangle();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] при помощи  КФ среднего прямоугольника: " + mJ);
        System.out.println("Абсолютная фактическая погрешность: " + Math.abs(mJ-this.J));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double tJ = trapeze();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] при помощи  КФ трапеции: " + tJ);
        System.out.println("Абсолютная фактическая погрешность для КФ трапеции: " + Math.abs(tJ-this.J));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double sJ = simpson();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] при помощи  КФ Симпсона (парабол): " + sJ);
        System.out.println("Абсолютная фактическая погрешность для КФ Симпсона (парабол): " + Math.abs(sJ-this.J));
        System.out.println("-----------------------------------------------------------------------------------------------------");

        double J_38 = kf38();
        System.out.println("Значение интеграла от f(x) по [" + this.A + ", " + this.B + "] при помощи  КФ 3/8: " + J_38);
        System.out.println("Абсолютная фактическая погрешность для КФ 3/8: " + Math.abs(J_38-this.J));
        System.out.println("-----------------------------------------------------------------------------------------------------");

    }

    double left_rectangle(){
        if(this.n == 0)
            return (this.B - this.A) * p0(this.A, false);
        else if(this.n == 1)
            return (this.B - this.A) * p1(this.A, false);
        else if(this.n == 2)
            return (this.B - this.A) * p2(this.A, false);
        else if(this.n == 3)
            return (this.B - this.A) * p3(this.A, false);
        else
            return (this.B - this.A) * f(this.A, false);
    }

    double right_rectangle(){
        if(this.n == 0)
            return (this.B - this.A) * p0(this.B, false);
        else if(this.n == 1)
            return (this.B - this.A) * p1(this.B, false);
        else if(this.n == 2)
            return (this.B - this.A) * p2(this.B, false);
        else if(this.n == 3)
            return (this.B - this.A) * p3(this.B, false);
        else
            return (this.B - this.A) * f(this.B, false);
    }

    double mid_rectangle(){
        if(this.n == 0)
            return (this.B - this.A) * p0((this.A + this.B)/2, false);
        else if(this.n == 1)
            return (this.B - this.A) * p1((this.A + this.B)/2, false);
        else if(this.n == 2)
            return (this.B - this.A) * p2((this.A + this.B)/2, false);
        else if(this.n == 3)
            return (this.B - this.A) * p3((this.A + this.B)/2, false);
        else
            return (this.B - this.A) * f((this.A + this.B)/2, false);
    }

    double trapeze(){
        if(this.n == 0)
            return (this.B - this.A) * (p0(this.A, false) + p0(this.B, false))/2;
        else if(this.n == 1)
            return (this.B - this.A) * (p1(this.A, false) + p1(this.B, false))/2;
        else if(this.n == 2)
            return (this.B - this.A) * (p2(this.A, false) + p2(this.B, false))/2;
        else if(this.n == 3)
            return (this.B - this.A) * (p3(this.A, false) + p3(this.B, false))/2;
        else
            return (this.B - this.A) * (f(this.A, false) + f(this.B, false))/2;
    }

    double simpson(){
        if(this.n == 0)
            return (this.B - this.A) * (p0(this.A, false) + 4 * p0((this.A + this.B)/2, false) + p0(this.B, false))/6;
        else if(this.n == 1)
            return (this.B - this.A) * (p1(this.A, false) + 4 * p1((this.A + this.B)/2, false) + p1(this.B, false))/6;
        else if(this.n == 2)
            return (this.B - this.A) * (p2(this.A, false) + 4 * p2((this.A + this.B)/2, false) + p2(this.B, false))/6;
        else if(this.n == 3)
            return (this.B - this.A) * (p3(this.A, false) + 4 * p3((this.A + this.B)/2, false) + p3(this.B, false))/6;
        else
            return (this.B - this.A) * (f(this.A, false) + 4 * f((this.A + this.B)/2, false) + f(this.B, false))/6;
    }

    double kf38(){
        double h = (this.B - this.A)/3;
        if(this.n == 0)
            return  (this.B - this.A) * (p0(this.A, false) / 8 + 3 * p0(this.A + h, false)/8 + 3 * p0(this.A + 2 * h, false)/8 + p0(this.B, false)/8);
        else if(this.n == 1)
            return  (this.B - this.A) * (p1(this.A, false) / 8 + 3 * p1(this.A + h, false)/8 + 3 * p1(this.A + 2 * h, false)/8 + p1(this.B, false)/8);
        else if(this.n == 2)
            return  (this.B - this.A) * (p2(this.A, false) / 8 + 3 * p2(this.A + h, false)/8 + 3 * p2(this.A + 2 * h, false)/8 + p2(this.B, false)/8);
        else if(this.n == 3)
            return  (this.B - this.A) * (p3(this.A, false) / 8 + 3 * p3(this.A + h, false)/8 + 3 * p3(this.A + 2 * h, false)/8 + p3(this.B, false)/8);
        else
            return (this.B - this.A) * (f(this.A, false) / 8 + 3 * f(this.A + h, false)/8 + 3 * f(this.A + 2 * h, false)/8 + f(this.B, false)/8);
    }

    public static void main(String[] args) {
        int n;
        double A, B;

        System.out.println("Задание 4.1 Приближённое вычисление интеграла по квадратурным формулам");
        System.out.println("-------------------------------------------------------------------------");
        Scanner input = new Scanner(System.in);

        boolean t = true;
        while (t){
            System.out.println("0) f(x) = 7");
            System.out.println("1) f(x) = 3.3x - 3");
            System.out.println("2) f(x) = 9x^2 - 7x + 1.3");
            System.out.println("3) f(x) = 3x^3 + 1.7x^2 + 3.9x - 17");
            System.out.println("4) f(x) = 2sin(2x) + 3cos(3x)*x^3\n");

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

            System.out.println();

            Task4_1 integralCalculation = new Task4_1(A, B, n);

            integralCalculation.calcIntegral();

            System.out.println("продолжить работу? да - 1, нет - 0");

            if(input.nextInt() == 0)
                t = false;
        }
    }

}
