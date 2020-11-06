import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CheckingApp1040EZ {

    public static void main(String[] args) {


        //initialize all input lines as -1.  If a line is not = -1, then it has been changed
        int line1 = -1;
        int line2 = -1;
        int line3 = -1;
        int line5A = -1;  //input 0 or 1 --> number that can be claimed as dependents
        int line7 = -1;
        int line10 = -1;   //this can be calculated, but you may ask the user to look it up in the table (your choice)

        //if args contains the name of a file, fill the input variables from the file
        if (args.length > 0) {
            Scanner inputFile = null;
            try {
                inputFile = new Scanner(new File(args[0]));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("\nA file name has been detected, but that file does not exist.");
                System.exit(1);
            }
            try {
                line1 = inputFile.nextInt();
                line2 = inputFile.nextInt();
                line3 = inputFile.nextInt();
                line5A = inputFile.nextInt();
                line7 = inputFile.nextInt();
                line10 = inputFile.nextInt();
            } catch (NoSuchElementException e) {
                e.printStackTrace();
                System.out.println("\nA file has been detected and read, but the contents are not formatted correctly.");
                System.exit(2);
            }
        }
        //****************************************************************************************************************
        //Student code starts below this point
        //  NOTE: Do not change the value of any of the line variables above if they are not -1 - the value has come from file
        //       In other words, instead of just writing
        //                  line1 = kb.nextInt();
        //       you need to write:
        //					if(line1 == -1){
        //						line1 = kb.nextInt();
        //					}
        //*****************************************************************************************************************
        /** Requirements:
         *     Check back daily for updates on the assignment page.
         *     		1.  All calculations MUST occur in a method (no matter how small)
         *     		2.  All user input must be verified as acceptable
         *     		3.  ...see assignment page for updates
         */

        //File and scanner setup
        File path = new File("src/case"); //directory & needs to be in a nonempty directory for program to work
        File allFiles[] = path.listFiles(); //puts all the files in the directory into a list
        Scanner fileCheck = null;
        Scanner kb = new Scanner(System.in);

        //Ask user if they want to input into console or analyze the directory
        System.out.println("Input in console (1) or analyze files (0)?");
        boolean type = false;
        int determine = -1;
        while (!type) {
            try {
                determine = kb.nextInt();
                if (determine != 0 && determine != 1) {
                    System.out.println("Input either 0 or 1!");
                    kb.nextLine();
                } else {
                    type = true;
                }

            } catch (InputMismatchException e) {
                System.out.println("Input either 0 to analyze files or 1 to input in console!");
                kb.nextLine();
            }
        }
        type = false;

        //If the user asked to analyze a directory, asked if tax tables are provided
        //!!!!!!IMPORTANT MY PERSONAL TEST CASES DO NOT HAVE LINE 10 PROVIDED!!!!!!
        int table = -1;
        if (determine == 0) {
            System.out.println("Tax tables provided (1) or not (0)?");
            while (!type) {
                try {
                    table = kb.nextInt();
                    if (table != 0 && table != 1) {
                        System.out.println("Input either 0 or 1!");
                        kb.nextLine();
                    } else {
                        type = true;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Input either 1 if tax table is provided or 0 if not!");
                    kb.nextLine();
                }
            }
        }

        //for each loop that runs through every file in the directory and runs only once if manual input
        for (File person : allFiles) {
            if (determine == 0) { //runs this block if the user decided to analyze files
                try {
                    fileCheck = new Scanner(person);
                } catch (FileNotFoundException e) {
                    System.out.println("Files not found");
                }
                try {
                    System.out.println("File Name: " + person.getName());
                    line1 = fileCheck.nextInt();
                    line2 = fileCheck.nextInt();
                    line3 = fileCheck.nextInt();
                    line5A = fileCheck.nextInt();
                    line7 = fileCheck.nextInt();
                    if (table == 1) {
                        line10 = fileCheck.nextInt();
                    } else {
                        line10 = -1;
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("\nA file has been detected and read, but the contents are not formatted correctly.");
                    System.exit(2);
                }
            }

            //Line 1
            if (line1 == -1) {
                System.out.println("Input Line 1");
                line1 = console(kb);
            }

            //Line 2
            if (line2 == -1) {
                System.out.println("Input Line 2");
                line2 = console(kb);
            }

            //Line 2 - fail case
            if (line2 > 1500) {
                System.out.println("Can't use the 1040EZ because taxable interest is $" + line2 + "!");
                if (determine == 0) { //If analyzing files immediately jumps to the next file
                    System.out.println("Going to next case...\n");
                    continue;
                } else {
                    System.exit(0);
                }
            }

            //Line 3
            if (line3 == -1) {
                System.out.println("Input Line 3");
                line3 = console(kb);
            }

            //Line 4 method call
            int line4 = line4(line1, line2, line3);

            //Line 5A loop
            boolean right = false;
            if (line5A == -1) {
                System.out.println("If dependent (1), else (0)");
                while (!right) {
                    try {
                        line5A = kb.nextInt();
                        if (line5A != 0 && line5A != 1) {
                            System.out.println("Input either 0 or 1!");
                            kb.nextLine();
                        } else {
                            right = true;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Input either 0 or 1!");
                        kb.nextLine();
                    }

                }
            }

            //Line 5 calculations
            int line5 = line5(line5A, line1);

            //Line 6
            int line6 = 0;
            if (line5 < line4) {
                line6 = line6(line4, line5);
            }

            //Line 6 fail case
            if (line6 > 100000) {
                System.out.println("You can't use the 1040EZ as taxable income is $" + line6 + "!");

                if (determine == 0) { //If analyzing files it immediately jumps to next case
                    System.out.println("Going to next case...\n");
                    continue;
                } else {
                    System.exit(0);
                }
            }

            //Line 7
            if (line7 == -1) {
                System.out.println("Input Line 7");
                line7 = console(kb);
            }

            //Line 10
            if (line10 == -1) {
                line10 = line10(line6);
            }

            //Output all data
            System.out.println("Wages, Salaries, & Tips: $" + line1);
            System.out.println("Taxable Interest: $" + line2);
            System.out.println("Unemployment Compensation & Alaska Permanent Fund Dividends: $" + line3);
            System.out.println("Adjusted Gross Income: $" + line4);
            System.out.println("Deduction: $" + line5);
            System.out.println("Taxable Income: $" + line6);
            System.out.println("Withheld Federal Income Tax: $" + line7);
            System.out.println("Earned Income Credit: $" + 0);
            System.out.println("Total Payments & Credits: $" + line7);
            System.out.println("Tax: $" + line10);
            System.out.println("Health Care: $" + 0);
            System.out.println("Total Tax: $" + line10);

            //Line 14
            if (line10 > line7) {
                int owed = line14(line7, line10);
                System.out.println("Tax Owed: $" + owed);
            }

            //Line 13
            else {
                int refund = line13(line7, line10);
                System.out.println("Tax Refund: $" + refund);
            }
            if (determine == 1) { //stops the loop if manual input
                System.exit(0);
            }
            System.out.println(); //makes it look organized
        }
        //Happens only if analyzing files
        System.out.println("Done!");
    }


    //Calculations
    public static int line4(int line1, int line2, int line3) {
        return line1 + line2 + line3;
    }

    public static int line5(int line5A, int line1) {
        int deduction = 10400;

        if (line5A == 1) {
            if (line1 > 700) {
                if (line1 + 350 > 6350) {
                    deduction = 6350;
                } else {
                    deduction = line1 + 350;
                }
            } else {
                deduction = 1050;
            }
        }
        return deduction;
    }

    public static int line6(int line4, int line5) {
        int line6 = line4 - line5;
        return line6;
    }

    public static int line10(int line6) {
        int hundreds = line6 % 100;
        int tax = 0;
        if (line6 < 3000) {
            if (line6 < 25) {
                if (line6 < 5) return 0;
                else if (line6 < 15) return 1;
                else return 2;
            } else {
                if (hundreds < 25) {
                    line6 = line6 - hundreds;
                    tax = line6 / 10 + 1;
                } else if (hundreds < 50) {
                    line6 = line6 - (hundreds - 25);
                    tax = line6 / 10 + 2;
                } else if (hundreds < 75) {
                    line6 = line6 - (hundreds - 50);
                    tax = line6 / 10 + 1;
                } else {
                    line6 = line6 - (hundreds - 75);
                    tax = line6 / 10 + 2;
                }
            }
        } else {
            if (hundreds >= 50) {
                line6 = line6 - (hundreds - 50);
            } else {
                line6 = line6 - hundreds;
            }

            if (line6 < 9350) {
                tax = line6 / 10 + 3; //10%
            } else if (line6 < 37950) {
                tax = (line6 * 3) / 20 - 462; //15%
            } else if (line6 < 91900) {
                tax = (line6 / 4) - 4255; //25%
            } else if (line6 < 191650) {
                tax = (line6 * 7) / 25 - 7011; //28%
            }
        }
        return tax;
    }

    public static int line13(int line7, int line10) {
        return line7 - line10;
    }

    public static int line14(int line7, int line10) {
        return line10 - line7;
    }

    //Line inputs
    public static int console(Scanner kb) {
        boolean right = false;
        int line = 0;
        while (!right) {
            try {
                line = kb.nextInt();
                if (line >= 0) {
                    right = true;
                } else {
                    System.out.println("Input a positive integer!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input a positive integer!");
                kb.nextLine();
            }
        }
        return line;
    }
}
