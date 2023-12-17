package Task3;

import java.util.Scanner;

public class Task3_2 {

    double A, h;
    int M;
    double[] arrayX;
    double[] arrayFx;

    Task3_2(double A, int M, double h) {
        this.A = A;
        this.M = M;
        this.arrayX = new double[M];
        this.arrayFx = new double[M];
        this.h = h;
    }

    double f(double x){
        return Math.exp(1.5 * 3 * x);
    }

    void table(){
        System.out.println("         x           |       f(x)   ");
        System.out.println("----------------------------------------------------------");
        for (int i = 0; i < this.M; i++) {
            this.arrayX[i] = this.A + this.h*i;
            this.arrayFx[i] = f(this.arrayX[i]);
            System.out.printf("%-20s | %-20s \n", this.arrayX[i], this.arrayFx[i]);
        }
        System.out.println("----------------------------------------------------------\n");
    }
    double dFx(double x, int ind){
        if(ind == 0){
            return (-3 * f(x) + 4 * f(x + h) - f(x + 2 * h)) / (2 * h); // Погрешность = f'''(e)/3(h^2)
        }
        else if(ind >= 1 && ind < M -1){
            return (f(x + h) - f(x - h)) / (2 * h);                    // Погрешность = f'''(e)/6(-h^2)
        }
        else{
            return (3 * f(x) - 4 * f(x - h) + f(x - 2 * h)) / (2 * h); // Погрешность = f'''(e)/3(h^2)
        }
    }

    double dFxT(double x){
        return 1.5 * 3 * Math.exp(1.5 * 3 * x);
    }

    double ddFx(double x, int ind){
        if(ind == 0 || ind == M - 1){
            return Double.NaN;
        }
        else return (f(x + h) - 2 * f(x) + f(x - h)) / (h * h);  // Погрешность = h^2/12 * f^(4)(e)
    }

    double ddFxT(double x){
        return Math.pow(1.5 * 3, 2) * Math.exp(1.5 * 3 * x);
    }

    void derTable(){
        System.out.println("           x_i            |           f(x_i)          |        f'(x_i)чд         |  |f'(x_i)т - f'(x_i)чд|  |   |f'(x_i)т - f'(x_i)чд| / f'(x_i)чд   |" +
                "        f''(x_i)чд        |   |f''(x)т - f''(x_i)чд|  |  |f''(x)т - f''(x_i)чд| / f''(x_i)чд |");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < this.M; i++) {
            double x_i = this.arrayX[i];
            double fx_i = this.arrayFx[i];
            double dfx_i = dFx(x_i, i);
            double dfx_iT = dFxT(x_i);
            double ddfx_i = ddFx(x_i, i);
            double ddfx_iT = ddFxT(x_i);
            System.out.printf("%-25s | %-25s | %-25s| %-25s| %-38s | %-25s| %-25s | %-38s \n",
                    x_i , fx_i, dfx_i, Math.abs(dfx_iT - dfx_i),Math.abs(dfx_iT - dfx_i) / dfx_i ,
                                ddfx_i, Math.abs(ddfx_iT - ddfx_i), Math.abs(ddfx_iT - ddfx_i) / ddfx_i );
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }



    public static void main(String[] args) {
        double A, h;
        int M;
        Scanner input = new Scanner(System.in);

        System.out.println("Задание 3.2 Нахождение производных таблично-заданной функции по формулам численного дифференцирования ");
        System.out.println("Вариант 2");
        System.out.println("Функция f(x) = e^(1.5 * 3 * x");
        System.out.println("----------------------------------------------------------");

        boolean t = true;
        while (t) {

            System.out.println("Введите следующие параметры задачи:");

            System.out.println("Введите нижнюю границу");
            System.out.print("A = ");
            A = input.nextDouble();

            System.out.println("Введите число значений в таблице");
            System.out.print("m+1 = ");
            M = input.nextInt();

            System.out.println("Введите шаг:");
            System.out.print("h = ");
            h = input.nextInt();

            while (h <= 0 || M < 1) {
                if(h <= 0) {
                    System.out.println("Значение h должно быть больше 0");
                    System.out.println("h = ");
                    h = input.nextInt();
                }
                if(M < 1){
                    System.out.println("Введите число значений в таблице должно быть не меньше 1");
                    System.out.print("m+1 = ");
                    M = input.nextInt();
                }
            }

            Task3_2 numDiff = new Task3_2(A, M, h);
            numDiff.table();
            numDiff.derTable();




            System.out.println("продолжить работу? да - 1, нет - 0");

            if(input.nextInt() == 0)
                t = false;

        }

    }
}
