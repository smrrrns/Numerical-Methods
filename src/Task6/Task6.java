package Task6;

import java.util.Scanner;

public class Task6 {

    double h = 0.1;
    int N = 10, x0 = 0, y0 = 1;

    Task6(int n){
        N = n;
    }
    double[] nodes = new double[11];
    double[][] teylor = new double[N+3][2];
    double[][] exactValue = new double[N+3][2];

    double[][] euler = new double[N+1][2];
    double[][] euler1 = new double[N+1][2];
    double[][] euler2 = new double[N+1][2];

    double[][] rungeKutta = new double[N+1][2];

    double[][] adams = new double[N+1][N+2];
    double f(double x, double y){
        return -y + x;
    }
    double factorial(int n) {
        if(n==0)
            return 1;
        else return n * factorial(n - 1);
    }

    double exactValue(double x){
        return 2 / Math.exp(x) + x - 1;
    }

    void nodesFill(){
        for(int k = 0; k <= N; k++)
            nodes[k] =  x0 + k * h;
    }
    double[] yDerVal(){
        double[] y = new double[6];
        y[0] = 1;
        y[1] = -1;
        y[2] = 2;
        y[3] = -2;
        y[4] = 2;
        y[5] = 2;
        return y;
    }
    void exactValueFillPrint(){
        double xk;
        System.out.println("\nТаблица значений точного решения в точках x_k = x_0 + k*h,   k = -2,-1,0,...,N");
        System.out.println("---------------------------------------------------");
        System.out.println("           x_k          |            y_k          ");
        System.out.println("--------------------------------------------------");
        for(int k = -2; k <= N; k++){
            xk = x0 + k * h;
            exactValue[k+2][0] = xk;
            exactValue[k+2][1] = exactValue(xk);
            System.out.printf("%-23s | %-23s \n", xk , exactValue(xk));
        }
    }

    double teylor(double x){
        double res = 0;
        double[] y = yDerVal();
        for (int i = 0; i < 6; i++){
            res += (y[i] / factorial(i) * Math.pow((x - x0), i));
        }

        return res;
    }

    void teylorFillPrint(){
        double xk;
        System.out.println("\nМЕТОД РАЗЛОЖЕНИЯ В РЯД ТЕЙЛОРА");
        System.out.println("\nТаблица значений приближенного решения в точках x_k = x_0 + k*h,   k = -2,-1,0,...,N");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("           x_k          |            y_k          |           acc          ");
        System.out.println("---------------------------------------------------------------------------");
        for(int k = -2; k <= N; k++){
            xk = x0 + k * h;
            teylor[k+2][0] = xk;
            teylor[k+2][1] = teylor(xk);
            System.out.printf("%-23s | %-23s | %-23s \n", teylor[k+2][0] , teylor[k+2][1], Math.abs(teylor[k+2][1] - exactValue[k+2][1]));
        }
    }

    double euler(int k){
        if(k == 0)
            return y0;
        else
            return euler(k-1) + h * f(nodes[k-1], euler(k - 1));
    }

    void eulerFillPrint(){
        double xk;
        System.out.println("\nМЕТОД ЭЙЛЕРА");
        System.out.println("\nТаблица значений приближенного решения в точках x_k = x_0 + k*h,   k = 1,...,N");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("           x_k          |            y_k          |           acc          ");
        System.out.println("---------------------------------------------------------------------------");
        for(int k = 0; k <= N; k++){
            xk = x0 + k * h;
            euler[k][0] = xk;
            euler[k][1] = euler(k);
            System.out.printf("%-23s | %-23s | %-23s \n", euler[k][0] , euler[k][1] , Math.abs(euler[k][1] - exactValue[k+2][1]));
        }
    }

    double euler1(int k){
        if(k == 0)
            return y0;
        else
            return euler1(k-1) + h * f(nodes[k-1] + h/2, euler1(k-1) + h/2 * f(nodes[k-1], euler1(k-1)));
    }
    void euler1FillPrint(){
        double xk;
        System.out.println("\nМЕТОД ЭЙЛЕРА I");
        System.out.println("\nТаблица значений приближенного решения в точках x_k = x_0 + k*h,   k = 1,...,N");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("           x_k          |            y_k          |           acc          ");
        System.out.println("---------------------------------------------------------------------------");
        for(int k = 0; k <= N; k++){
            xk = x0 + k * h;
            euler1[k][0] = xk;
            euler1[k][1] = euler1(k);
            System.out.printf("%-23s | %-23s | %-23s \n", euler1[k][0] , euler1[k][1] , Math.abs(euler1[k][1] - exactValue[k+2][1]));
        }
    }

    double euler2(int k){
        if(k == 0)
            return y0;
        else
            return euler1(k-1) + h / 2 * (f(nodes[k-1], euler1(k-1)) + f(nodes[k], euler1(k-1) + h * f(nodes[k-1], euler1(k-1))));
    }

    void euler2FillPrint(){
        double xk;
        System.out.println("\nМЕТОД ЭЙЛЕРА II");
        System.out.println("\nТаблица значений приближенного решения в точках x_k = x_0 + k*h,   k = 1...,N");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("           x_k          |            y_k          |           acc          ");
        System.out.println("---------------------------------------------------------------------------");
        for(int k = 0; k <= N; k++){
            xk = x0 + k * h;
            euler2[k][0] = xk;
            euler2[k][1] = euler2(k);
            System.out.printf("%-23s | %-23s | %-23s \n", euler2[k][0] , euler2[k][1] , Math.abs(euler2[k][1] - exactValue[k+2][1]));
        }
    }

    double rungeKutta(int k){
        double k1, k2, k3, k4;
        if (k == 0)
            return y0;
        else {
            k1 = h * f(nodes[k - 1], rungeKutta(k - 1));
            k2 = h * f(nodes[k - 1] + h / 2, rungeKutta(k - 1) + k1 / 2);
            k3 = h * f(nodes[k - 1] + h / 2, rungeKutta(k - 1) + k2 / 2);
            k4 = h * f(nodes[k - 1] + h, rungeKutta(k - 1) + k3);
            return rungeKutta(k-1) + (k1 + 2 * k2 + 2 * k3 + k4)/6;
        }
    }

    void rungeKuttaFillPrint(){
        double xk;
        System.out.println("\nМЕТОД РУНГЕ-КУТТА 4-ГО ПОРЯДКА");
        System.out.println("\nТаблица значений приближенного решения в точках x_k = x_0 + k*h,   k = 1,...,N");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("           x_k          |            y_k          |           acc          ");
        System.out.println("---------------------------------------------------------------------------");
        for(int k = 0; k <= N; k++){
            xk = x0 + k * h;
            rungeKutta[k][0] = xk;
            rungeKutta[k][1] = rungeKutta(k);
            System.out.printf("%-23s | %-23s | %-23s \n", rungeKutta[k][0] , rungeKutta[k][1] , Math.abs(rungeKutta[k][1] - exactValue[k+2][1]));
        }
    }

    void adamsPrint(){
        adamsMatrixFill();
        System.out.println("\nЭКСТРАПОЛЯЦИОННЫЙ МЕТОД АДАМСА 4-ГО ПОРЯДКА");
        System.out.println("\nТаблица значений приближенного решения в точках x_k = x_0 + k*h,   k = 3,...,N");
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("           x_k          |            y_k          |           acc          ");
        System.out.println("---------------------------------------------------------------------------");
        for(int k = 3; k <= N; k++){
            System.out.printf("%-23s | %-23s | %-23s \n",adams[k][0] , adams[k][1] , Math.abs(adams[k][1] - exactValue[k+2][1]));
        }
    }

    void adamsMatrixFill(){
        for(int i = 0; i <= N; i++)
            adams[i][0] = nodes[i];
        for(int i = 0; i <= 4; i++){
                              // xi
            adams[i][1] = teylor[i][1];                  // yi
            adams[i][2] = h * f(nodes[i], adams[i][1]);  // ni
        }

        for(int k = 3; k <= 6; k++){
            for(int i = 0; i <= 6 - k; i++){
                adams[i][k] = adams[i+1][k-1] - adams[i][k-1];   //delta
            }
        }

        for(int i = 5; i<=N; i++){
            adams[i][1] = adams[i-1][1] + adams[i-1][2] + adams[i-2][3]/2 + 5 * adams[i-3][4] / 12 + 3 * adams[i - 4][5] / 8 + 251 * adams[i-5][6] / 720; //yn
            adams[i][2] = h * f(adams[i][0], adams[i][1]);
            //adams[i-1][3] = adams[i][3] - adams[i-1][3];
            for(int k = 3; k <= 6; k++){
                for(int j = 4; j <= N-1; j++){
                    adams[j][k] = adams[j-1][k-1] - adams[j][k-1];   //delta
                }
            }
        }



        //8return addams(k-1) + b * addams(k - 1);
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = 10;
        System.out.println("Задание 6. Численное решение задачи Коши для ОДУ 1-го порядка");
        System.out.println("Вариант 2");
        System.out.println("---------------------------------------------------------------\n");
        System.out.println("y'(x) = -y(x) + x;  y(0) = 1; h = 0.1, N = 10");

        boolean t = true;
        while (t){
            System.out.println("\nВвести другое значение N? да - 1, нет - 0");
            if(input.nextInt() == 1) {
                System.out.print("Введите количество узлов: N = ");
                n = input.nextInt();
                if (n < 1) {
                    System.out.println("Количество узлов должно быть не меньше 1. Повторите ввод.");
                    System.out.print("N = ");
                    n = input.nextInt();
                }
            }

            Task6 t6 = new Task6(n);
            t6.nodesFill();

            t6.exactValueFillPrint();
            t6.teylorFillPrint();
            t6.rungeKuttaFillPrint();
            t6.adamsPrint();
            t6.eulerFillPrint();
            t6.euler1FillPrint();
            t6.euler2FillPrint();



            System.out.println("\nАбсолютная погрешность для последнего значения y_n для каждого из методов: ");
            System.out.println("-----------------------------------------------------------------------");
            System.out.println("     method     |           y_" + n +"          |           acc         ");
            System.out.println("-----------------------------------------------------------------------");

            System.out.printf("Тейлор          | %-23s | %-23s \n", t6.teylor[n][1] , Math.abs(t6.teylor[n][1] - t6.exactValue[n+2][1]));
            System.out.printf("Адамс           | %-23s | %-23s \n", t6.adams[n][1] , Math.abs(t6.adams[n][1] - t6.exactValue[n+2][1]));
            System.out.printf("Рунге-Кутт      | %-23s | %-23s \n", t6.rungeKutta[n][1] , Math.abs(t6.rungeKutta[n][1] - t6.exactValue[n+2][1]));
            System.out.printf("Эйлер           | %-23s | %-23s \n", t6.euler[n][1] , Math.abs(t6.euler[n][1] - t6.exactValue[n+2][1]));
            System.out.printf("Эйлер 1         | %-23s | %-23s \n", t6.euler1[n][1] , Math.abs(t6.euler1[n][1] - t6.exactValue[n+2][1]));
            System.out.printf("Эйлер 2         | %-23s | %-23s \n", t6.euler2[n][1] , Math.abs(t6.euler2[n][1] - t6.exactValue[n+2][1]));




            System.out.println("\nПродолжить работу? да - 1, нет - 0");
            if(input.nextInt() == 0)
                t = false;
        }

    }
}
