package mp;

import java.util.Scanner;

public class driver{

    static int AT = 0;
    static int CPUBT = 1;
    static int START = 2;
    static int END = 3;
    static int WAIT = 4;
    static int RESPONSE = 5;
    static int TAT = 6;
    static int DONE = 7;
    static int STARTED = 8;
    static int CPUCPY = 9;
    static int PRIORITY = 10;
    static int CPUBT2 = 11;
    static int IO = 12;
    static int SWITCH = 13;
    static int ATCPY = 14;
    static String chart1 = "";
    static String chart2 = "";
    static String chart3 = "";
    static int size = 0;
    static boolean prev = false;
    static int TQ = 0;
    static String reset = "";
    static String color = "";
    static String txtreset = "";
    static double[] TATAvge = new double[2];
    static double[] RTAvge = new double[2];
    static double[] WTAvge = new double[2];
    static String[] names = new String[2];
    static int curr = 0;


    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("CS125 MP:CPU Scheduling Algorithms\n");
   
        System.out.println("Select number of algorithm:\n"
        		+ "1. First Come First Serve\n"
                + "2. Shortest Job First\n"
                + "3. Shortest Remaining Time First\n"
                + "4. Non-Preemptive Priority\n"
                + "5. Preemptive Priority\n"
                + "6. Round Robin\n");

            System.out.print("Enter number of chosen algorithm: ");
            int choice;
            choice = scanner.nextInt();
            switch (choice){
            
                case 1:
                	System.out.print("\nEnter number of processes: ");
                    size = scanner.nextInt();
                    int[][] TABLE = new int[15][size];
                    input(TABLE, size);
                    FCFS(TABLE, size);          
                    break;
                    
                case 2:          
                    System.out.print("\nEnter number of processes: ");
                    size = scanner.nextInt();
                    int[][] TABLE5 = new int[15][size];
                    input(TABLE5, size);
                    SJF(TABLE5, size);            
                    break;
                    
                case 3:
                    System.out.print("\nEnter number of processes: ");
                    size = scanner.nextInt();
                    int[][] TABLE1 = new int[15][size];
                    input(TABLE1, size);
                    SRTF(TABLE1, size);
                    break;
                    
                case 4:           
                    System.out.print("\nEnter number of processes: ");
                    size = scanner.nextInt();
                    int[][] TABLE2 = new int[15][size];
                    inputPrio(TABLE2, size);
                    NPP(TABLE2, size);
                    break;
                    
                case 5:
                    System.out.print("\nEnter number of processes: ");
                    size = scanner.nextInt();
                    int[][] TABLE3 = new int[15][size];
                    inputPrio(TABLE3, size);
                    PP(TABLE3, size);
                    break;
                    
                case 6:
                    System.out.print("\nEnter number of processes: ");
                    size = scanner.nextInt();
                    int[][] TABLE4 = new int[15][size];
                    inputRR(TABLE4, size);
                    RR(TABLE4, size);
                    break;
            }
    
    }

    public static void FCFS(int TABLE[][], int size){
        int i = 0;
        for (int e : TABLE[CPUBT]){
            TABLE[CPUCPY][i] = e;
            i++;
            }

        int time = 0;
        int temp = 0;

        while (true){
            int c = size;
            double min = time;
            if (temp == size){
                break;
                }
            for (i = 0; i < size; i++){
                if ((TABLE[AT][i] <= min) && (TABLE[DONE][i] != 1)){
                    if (i < c){
                        min = TABLE[AT][i];
                        c = i;
                        }
                    }
                }
            if (c == size){
                time++;
                if (prev == false){
                    printIdle("", time, time + 1);
                    prev = true;
                    }
                }
            else{
                prev = false;
                TABLE[START][c] = time;
                ganttchart("P" + (c + 1), time, time + TABLE[CPUBT][c], c);
                time = time + TABLE[CPUBT][c];
                TABLE[END][c] = time;
                TABLE[DONE][c] = 1;
                temp++;
                }
            }

        double RTavg = 0;
        double WTavg = 0;
        double TATavg = 0;
        for (i = 0; i < size; i++){
            TABLE[TAT][i] = TABLE[END][i] - TABLE[AT][i];
            TABLE[WAIT][i] = TABLE[TAT][i] - TABLE[CPUBT][i];
            TABLE[RESPONSE][i] = TABLE[START][i] - TABLE[AT][i];
            WTavg += TABLE[WAIT][i];
            TATavg += TABLE[TAT][i];
            RTavg += TABLE[RESPONSE][i];
            }
        chart1 += time;
        chart3 += time;
        System.out.println("\nFCFS");
        System.out.println(chart1 + reset + "\n" + chart2 + reset + "\n" + chart3 + reset + txtreset + reset);
        RTavg = RTavg / size;
        WTavg = WTavg / size;
        TATavg = TATavg / size;
        TATAvge[curr] = TATavg;
        WTAvge[curr] = WTavg;
        RTAvge[curr] = RTavg;
        names[curr] = "FCFS";
        System.out.println("\n\tAT\tBT\tS\tE\tWT\tRT\tTAT");
        for (i = 0; i < size; i++){
            System.out.println("P" + (i + 1) + "\t" + TABLE[AT][i] + "\t" + TABLE[CPUCPY][i] + "\t" + TABLE[START][i]
                    + "\t" + TABLE[END][i] + "\t" + TABLE[WAIT][i] + "\t" + TABLE[RESPONSE][i] + "\t" + TABLE[TAT][i]);
            }
        System.out.println("Average waiting time = " + WTavg);
        System.out.println("Average response time = " + RTavg);
        System.out.println("Average turnaround time = " + TATavg);
        }

    public static void SJF(int TABLE[][], int size){
        int i = 0;
        for (int e : TABLE[CPUBT]){
            TABLE[CPUCPY][i] = e;
            i++;
            }
        int time = 0;
        int temp = 0;
        while (true){
            int c = size;
            double min = 999;

            if (temp == size){
                break;
                }

            for (i = 0; i < size; i++){
                if ((TABLE[AT][i] <= time) && (TABLE[CPUBT][i] < min) && (TABLE[DONE][i] != 1)){
                    min = TABLE[CPUBT][i];
                    c = i;
                    }
                }

            if (c == size){
                time++;
                if (prev == false){
                    printIdle("", time, time + 1);
                    prev = true;
                    }
                }
            else{
                prev = false;
                TABLE[START][c] = time;
                ganttchart("P" + (c + 1), time, time + TABLE[CPUBT][c], c);
                time = time + TABLE[CPUBT][c];
                TABLE[END][c] = time;
                TABLE[DONE][c] = 1;
                temp++;
                }
            }

        double RTavg = 0;
        double WTavg = 0;
        double TATavg = 0;
        for (i = 0; i < size; i++){
            TABLE[TAT][i] = TABLE[END][i] - TABLE[AT][i];
            TABLE[WAIT][i] = TABLE[TAT][i] - TABLE[CPUCPY][i];
            TABLE[RESPONSE][i] = TABLE[START][i] - TABLE[AT][i];
            WTavg += TABLE[WAIT][i];
            TATavg += TABLE[TAT][i];
            RTavg += TABLE[RESPONSE][i];
            }
        chart1 += time;
        chart3 += time;
        System.out.println("\nSJF");
        System.out.println(chart1 + "\n" + chart2 + "\n" + chart3 + txtreset + reset);
        RTavg = RTavg / size;
        WTavg = WTavg / size;
        TATavg = TATavg / size;
        TATAvge[curr] = TATavg;
        WTAvge[curr] = WTavg;
        RTAvge[curr] = RTavg;
        names[curr] = "SJF";
        System.out.println("\n\tAT\tBT\tS\tE\tWT\tRT\tTAT");
        for (i = 0; i < size; i++){
            System.out.println("P" + (i + 1) + "\t" + TABLE[AT][i] + "\t" + TABLE[CPUCPY][i] + "\t" + TABLE[START][i]
                    + "\t" + TABLE[END][i] + "\t" + TABLE[WAIT][i] + "\t" + TABLE[RESPONSE][i] + "\t" + TABLE[TAT][i]);
            }
        System.out.println("Average waiting time = " + WTavg);
        System.out.println("Average response time = " + RTavg);
        System.out.println("Average turnaround time = " + TATavg);
        }

    public static void SRTF(int TABLE[][], int size){
        int i = 0;
        for (int e : TABLE[CPUBT]){
            TABLE[CPUCPY][i] = e;
            i++;
            }

        int time = 0;
        int temp = 0;

        while (true){
            int c = size;
            double min = 999;

            if (temp == size){
                break;
                }

            for (i = 0; i < size; i++){
                if ((TABLE[AT][i] <= time) && (TABLE[CPUBT][i] < min) && (TABLE[DONE][i] != 1)){
                    min = TABLE[CPUBT][i];
                    c = i;
                    }
                }

            if (c == size){
                time++;
                if (prev == false){
                    printIdle("", time, time + 1);
                    prev = true;}
                }
            else{
                if (TABLE[STARTED][c] == 0){
                    TABLE[STARTED][c] = 1;
                    TABLE[START][c] = time;
                    TABLE[CPUBT][c]--;
                    ganttchart("P" + (c + 1), time, time + 1, c);
                    time++;
                    }
                else{
                    prev = false;
                    TABLE[CPUBT][c]--;
                    ganttchart("P" + (c + 1), time, time + 1, c);
                    time++;
                    }

                if (TABLE[CPUBT][c] == 0){
                    TABLE[END][c] = time;
                    TABLE[DONE][c] = 1;
                    temp++;
                    }
                }
            }
        chart1 += time;
        chart3 += time;
        System.out.println("\nSRTF");
        System.out.println(chart1 + "\n" + chart2 + "\n" + chart3 + txtreset + reset);
        double RTavg = 0;
        double WTavg = 0;
        double TATavg = 0;
        for (i = 0; i < size; i++){
            TABLE[RESPONSE][i] = TABLE[START][i] - TABLE[AT][i];
            TABLE[TAT][i] = TABLE[END][i] - TABLE[AT][i];
            TABLE[WAIT][i] = TABLE[TAT][i] - TABLE[CPUCPY][i];
            WTavg += TABLE[WAIT][i];
            TATavg += TABLE[TAT][i];
            RTavg += TABLE[RESPONSE][i];
            }
        RTavg = RTavg / size;
        WTavg = WTavg / size;
        TATavg = TATavg / size;
        TATAvge[curr] = TATavg;
        WTAvge[curr] = WTavg;
        RTAvge[curr] = RTavg;
        names[curr] = "SRTF";
        System.out.println("\n\tAT\tBT\tS\tE\tWT\tRT\tTAT");
        for (i = 0; i < size; i++){
            System.out.println("P" + (i + 1) + "\t" + TABLE[AT][i] + "\t" + TABLE[CPUCPY][i] + "\t" + TABLE[START][i]+ "\t" + TABLE[END][i] + "\t" + TABLE[WAIT][i] + "\t" + TABLE[RESPONSE][i] + "\t" + TABLE[TAT][i]);
            }
        System.out.println("Average waiting time = " + WTavg);
        System.out.println("Average response time = " + RTavg);
        System.out.println("Average turnaround time = " + TATavg);
    }

    public static void NPP(int TABLE[][], int size){
        int i = 0;
        for (int e : TABLE[CPUBT]){
            TABLE[CPUCPY][i] = e;
            i++;
            }

        int time = 0;
        int temp = 0;
        while (true){
            int c = size;
            double min = 999;

            if (temp == size){
                break;
                }

            for (i = 0; i < size; i++){
                if ((TABLE[AT][i] <= time) && (TABLE[PRIORITY][i] < min) && (TABLE[DONE][i] != 1)){
                    min = TABLE[PRIORITY][i];
                    c = i;
                    }
                }

            if (c == size){
                time++;
                if (prev == false){
                    printIdle("", time, time + 1);
                    prev = true;
                    }
                }
            else{
                prev = false;
                TABLE[START][c] = time;
                ganttchart("P" + (c + 1), time, time + TABLE[CPUBT][c], c);
                time = time + TABLE[CPUBT][c];
                TABLE[END][c] = time;
                TABLE[DONE][c] = 1;
                temp++;
                }
            }
        chart1 += time;
        chart3 += time;
        System.out.println("\nNPP");
        System.out.println(chart1 + "\n" + chart2 + "\n" + chart3 + txtreset + reset);
        double RTavg = 0;
        double WTavg = 0;
        double TATavg = 0;
        for (i = 0; i < size; i++){
            TABLE[TAT][i] = TABLE[END][i] - TABLE[AT][i];
            TABLE[WAIT][i] = TABLE[TAT][i] - TABLE[CPUCPY][i];
            TABLE[RESPONSE][i] = TABLE[START][i] - TABLE[AT][i];
            WTavg += TABLE[WAIT][i];
            TATavg += TABLE[TAT][i];
            RTavg += TABLE[RESPONSE][i];
            }
        RTavg = RTavg / size;
        WTavg = WTavg / size;
        TATavg = TATavg / size;
        TATAvge[curr] = TATavg;
        WTAvge[curr] = WTavg;
        RTAvge[curr] = RTavg;
        names[curr] = "NPP";
        System.out.println("\tAT\tBT\tP\tS\tE\tWT\tRT\tTAT");
        for (i = 0; i < size; i++){
            System.out.println("P" + (i + 1) + "\t" + TABLE[AT][i] + "\t" + TABLE[CPUCPY][i] + "\t" + TABLE[PRIORITY][i]+ "\t" + TABLE[START][i] + "\t" + TABLE[END][i] + "\t" + TABLE[WAIT][i] + "\t" + TABLE[RESPONSE][i] + "\t" + TABLE[TAT][i]);
            }
        System.out.println("Average waiting time = " + WTavg);
        System.out.println("Average response time = " + RTavg);
        System.out.println("Average turnaround time = " + TATavg);
        }

    public static void PP(int TABLE[][], int size){
        int i = 0;
        for (int e : TABLE[CPUBT]){
            TABLE[CPUCPY][i] = e;
            i++;
            }

        int time = 0;
        int temp = 0;
        while (true){
            int c = size;
            double min = 999;

            if (temp == size){
                break;
                }
            for (i = 0; i < size; i++){
                if ((TABLE[AT][i] <= time) && (TABLE[PRIORITY][i] < min) && (TABLE[DONE][i] != 1)){
                    min = TABLE[PRIORITY][i];
                    c = i;
                    }
                }

            if (c == size){
                time++;
                if (prev == false){
                    printIdle("", time, time + 1);
                    prev = true;
                    }
                }
            else{
                prev = false;
                if (TABLE[STARTED][c] == 0){
                    TABLE[STARTED][c] = 1;
                    TABLE[START][c] = time;
                    TABLE[CPUBT][c]--;
                    ganttchart("P" + (c + 1), time, time + 1, c);
                    time++;
                    }
                else{
                    TABLE[CPUBT][c]--;
                    ganttchart("P" + (c + 1), time, time + 1, c);
                    time++;
                    }
                if (TABLE[CPUBT][c] == 0){
                    TABLE[END][c] = time;
                    TABLE[DONE][c] = 1;
                    temp++;
                    }
                }
            }
        System.out.println("\nPP");
        System.out.println(chart1 + "\n" + chart2 + "\n" + chart3 + txtreset + reset);
        double RTavg = 0;
        double WTavg = 0;
        double TATavg = 0;
        for (i = 0; i < size; i++){
            TABLE[RESPONSE][i] = TABLE[START][i] - TABLE[AT][i];
            TABLE[TAT][i] = TABLE[END][i] - TABLE[AT][i];
            TABLE[WAIT][i] = TABLE[TAT][i] - TABLE[CPUCPY][i];
            WTavg += TABLE[WAIT][i];
            TATavg += TABLE[TAT][i];
            RTavg += TABLE[RESPONSE][i];
            }
        RTavg = RTavg / size;
        WTavg = WTavg / size;
        TATavg = TATavg / size;
        TATAvge[curr] = TATavg;
        WTAvge[curr] = WTavg;
        RTAvge[curr] = RTavg;
        names[curr] = "PP";
        System.out.println("\tAT\tBT\tP\tS\tE\tWT\tRT\tTAT");
        for (i = 0; i < size; i++){
            System.out.println("P" + (i + 1) + "\t" + TABLE[AT][i] + "\t" + TABLE[CPUCPY][i] + "\t" + TABLE[PRIORITY][i] + "\t" + TABLE[START][i] + "\t" + TABLE[END][i] + "\t" + TABLE[WAIT][i] + "\t" + TABLE[RESPONSE][i] + "\t" + TABLE[TAT][i]);
            }
        System.out.println("Average waiting time = " + WTavg);
        System.out.println("Average response time = " + RTavg);
        System.out.println("Average turnaround time = " + TATavg);
        }

    public static void RR(int TABLE[][], int size){
        int i = 0;
        for (int e : TABLE[CPUBT]){
            TABLE[CPUCPY][i] = e;
            i++;
            }
        i = 0;
        for (int e : TABLE[AT]){
            TABLE[ATCPY][i] = e;
            i++;
            }
        int time = 0;
        int temp = 0;
        while (true){
            int c = size;
            double min = time;
            if (temp == size){
                break;
                }
            for (i = 0; i < size; i++){
            	if ((TABLE[AT][i] <= min) && (TABLE[DONE][i] != 1)){
                    min = TABLE[AT][i];
                    c = i;
                    }
            	}

            if (c == size){
                time++;
                if (prev == false){
                    printIdle("", time, time + 1);
                    prev = true;
                    }
                }
            else{
                if (TABLE[CPUBT][c] > TQ){
                    if (TABLE[STARTED][c] == 0){
                        TABLE[STARTED][c] = 1;
                        TABLE[START][c] = time;
                        prev = false;
                        ganttchart("P" + (c + 1), time, time + TQ, c);
                        time += TQ;
                        TABLE[CPUBT][c] -= TQ;
                        TABLE[AT][c] = time;
                        }
                    else{
                        prev = false;
                        ganttchart("P" + (c + 1), time, time + TQ, c);
                        time += TQ;
                        TABLE[CPUBT][c] -= TQ;
                        TABLE[AT][c] = time;
                        }
                    }
                else{
                    if (TABLE[STARTED][c] == 0){
                        TABLE[STARTED][c] = 1;
                        TABLE[START][c] = time;
                        prev = false;
                        ganttchart("P" + (c + 1), time, time + TQ, c);
                        time += TABLE[CPUBT][c];
                        TABLE[DONE][c] = 1;
                        TABLE[END][c] = time;
                        temp++;
                        }
                    else{
                        prev = false;
                        ganttchart("P" + (c + 1), time, time + TQ, c);
                        time += TABLE[CPUBT][c];
                        TABLE[DONE][c] = 1;
                        TABLE[END][c] = time;
                        temp++;
                        }
                    }
                }
            }

        double RTavg = 0;
        double WTavg = 0;
        double TATavg = 0;
        for (i = 0; i < size; i++){
            TABLE[TAT][i] = TABLE[END][i] - TABLE[ATCPY][i];
            TABLE[WAIT][i] = TABLE[TAT][i] - TABLE[CPUCPY][i];
            TABLE[RESPONSE][i] = TABLE[START][i] - TABLE[ATCPY][i];
            WTavg += TABLE[WAIT][i];
            TATavg += TABLE[TAT][i];
            RTavg += TABLE[RESPONSE][i];
            }
        chart1 += time;
        chart3 += time;
        System.out.println("\nRR");
        System.out.println(chart1 + reset + "\n" + chart2 + reset + "\n" + chart3 + reset + txtreset + reset);
        RTavg = RTavg / size;
        WTavg = WTavg / size;
        TATavg = TATavg / size;
        TATAvge[curr] = TATavg;
        WTAvge[curr] = WTavg;
        RTAvge[curr] = RTavg;
        names[curr] = "RR";
        System.out.println("\n\tAT\tBT\tS\tE\tWT\tRT\tTAT");
        for (i = 0; i < size; i++){
            System.out.println("P" + (i + 1) + "\t" + TABLE[AT][i] + "\t" + TABLE[CPUCPY][i] + "\t" + TABLE[START][i] + "\t" + TABLE[END][i] + "\t" + TABLE[WAIT][i] + "\t" + TABLE[RESPONSE][i] + "\t" + TABLE[TAT][i]);
            }
        System.out.println("Average waiting time = " + WTavg);
        System.out.println("Average response time = " + RTavg);
        System.out.println("Average turnaround time = " + TATavg);
        }

    private static void ganttchart(String characterString, int start, int end, int c){
        int amount = characterString.length();
        String space = " ";
        
        if (start >= 10){
            space += " ";
            }
        
        if (start >= 100){
            space += " ";
            }

        if (amount % 2 == 1){
            amount = ((amount + 2) / 2) + 1;
            }
        
        else{
            amount = ((amount + 2) / 2);
            }
        chart1 +=start + reset;
        for (int i = 0; i < amount; i++){
            chart1 +=  "==" + reset;
            }
        if (characterString.length() % 2 == 1){
            chart2 +=  space + characterString + "   " + reset;
            }
        else{
            chart2 += space + characterString + "  " + reset;
            }
        chart3 += start + reset;
        for (int i = 0; i < amount; i++){
            chart3 +=  "==" + reset;
            }
        }

    private static void printIdle(String characterString, int start, int end){
        int amount = characterString.length();
        if (characterString.isEmpty()){
            characterString = "IDLE";
            amount = characterString.length();
            }
        String space = " ";
        if ((start - 1) >= 10){
            space += " ";
            }

        if ((start - 1) >= 100){
            space += " ";
            }

        if (amount % 2 == 1){
            amount = ((amount + 2) / 2) + 1;
            }
        else{
            amount = ((amount + 2) / 2);
            }
        
        chart1 +=  (start - 1) + reset;
        for (int i = 0; i < amount; i++){
            chart1 +=  "==" + reset;
            }

        if (characterString.length() % 2 == 1){
            chart2 +=  space + characterString + "   " + reset;
            }
        else{
            chart2 +=  space + characterString + "  " + reset;
            }
        chart3 +=  (start - 1) + reset;
        for (int i = 0; i < amount; i++){
            chart3 +=  "==" + reset;
            }
        
    }

    public static void input(int TABLE[][], int size){
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < size; i++){
            System.out.println("\nP" + (i + 1) + ": ");
            System.out.print("Arrival Time: ");
            TABLE[AT][i] = scanner.nextInt();
            System.out.print("Burst Time: ");
            TABLE[CPUBT][i] = scanner.nextInt();
            }
        }

    public static void inputPrio(int TABLE[][], int size){
    	Scanner scanner = new Scanner(System.in);
    	for (int i = 0; i < size; i++){
            System.out.println("\nP" + (i + 1) + ": ");
            System.out.print("Arrival Time: ");
            TABLE[AT][i] = scanner.nextInt();
            System.out.print("Burst Time: ");
            TABLE[CPUBT][i] = scanner.nextInt();
            System.out.print("Priority: ");
            TABLE[PRIORITY][i] = scanner.nextInt();
            }
    	}




    public static void inputRR(int TABLE[][], int size){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Time Quantum: ");
        TQ = scanner.nextInt();
        for (int i = 0; i < size; i++){
            System.out.println("\nP" + (i + 1) + ": ");
            System.out.print("Arrival Time: ");
            TABLE[AT][i] = scanner.nextInt();
            System.out.print("Burst Time: ");
            TABLE[CPUBT][i] = scanner.nextInt();
            TABLE[START][i] = 0;
            }
        }
    
}
