package Project;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by S.D.Z on 2017/5/5 0005.
 */
public class Passenger {
    private int passengerID;
    private String realName;
    private String identityID;
    private String password;
    private ArrayList<Order> orderList = new ArrayList<>();

    public static boolean checker;


    public Passenger(int passengerID, String realName, String identityID, String password, ArrayList<Order> orderList){
        this.passengerID = passengerID;
        this.realName = realName;
        this.identityID = identityID;
        this.password = password;
        this.orderList = orderList;
    }


    //初始化乘客
    public static void initializePassenger(ArrayList<Passenger> PList) throws IOException, ParseException {
        TXT.turnTXTtoPList("D:\\JAVA\\workspace\\Final\\src\\Project\\PassengerList.txt", PList);//初始化、但未初始化OrderList
        TXT.TXT_add_OrderList_to_P(PList);//初始化orderList
    }

    //乘客注册
    public static void signUpP( ArrayList<Passenger> PList) throws IOException {
        Scanner in = new Scanner(System.in);

        int passengerID = PList.size()+1;//乘客ID为当前乘客注册量的下一个
        System.out.println("Please input your real Name: ");
        String realName = in.nextLine();
        System.out.println("Please input your identity ID: ");
        String identityID = in.nextLine();
        System.out.println("Please input the password: ");
        String password = in.nextLine();

        ArrayList<Order> OrderList = new ArrayList<>();

        Passenger New = new Passenger( passengerID, realName, identityID, password, OrderList);
        PList.add(New);

        TXT.coverTXT_P("D:\\JAVA\\workspace\\Final\\src\\Project\\PassengerList.txt", PList);

        System.out.printf("Congratulations  %s, you have sign up successfully.\n", realName);
    }


    /**
     * 未完成 与其他方法的套用以及直接登入会报错
     * @param PList
     */
    //乘客登入
    public static void logIn(ArrayList<Passenger> PList, Checker checker) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println(checker.isChecker());
        if (!checker.isChecker()){
            System.out.println("Please log in first: ");
            System.out.printf(
                    "【1】Log in.\n" +
                    "【2】Sign up.\n" +
                    "【3】Quite.\n");

            int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字


            if (d > 0 && d <= 3 ) {
                switch (d) {
                    case 1:
                        LoginChecker(PList, checker);
                        break;
                    case 2:
                        signUpP(PList);
                        logIn(PList, checker);
                        break;
                    case 3:
                        break;
                }
            }else {
                System.out.println("Please enter the correct number!\n");
                logIn(PList, checker);
            }

        }
        else
            System.out.printf("Welcome, %s !\n", PList.get(checker.getP()-1).getRealName());

    }

    //乘客登入for reserve 用于预定
    public static void logInForReserve(ArrayList<Passenger> PList,  ArrayList<Flight> flights, Checker checker) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);
        System.out.println(checker.isChecker());//用于调试，看登入状态是否更改
        if (!checker.isChecker()){
            System.out.println("Please log in first: ");
            System.out.printf(
                    "【1】Log in.\n" +
                            "【2】Sign up.\n" +
                            "【3】Quite.\n");
            int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字


            if (d > 0 && d <= 3 ) {
                switch (d) {
                    case 1:
                        LoginChecker(PList, checker);
                        Order.addOrder(PList, flights, checker);
                        break;
                    case 2:
                        signUpP(PList);
                        logInForReserve(PList, flights, checker);
                        break;
                    case 3:
                        break;
                }
            }else {
                System.out.println("Please enter the correct number!\n");
                logInForReserve(PList, flights, checker);
            }
        }
        else
            System.out.printf("Welcome, %s !\n", PList.get(checker.getP()-1).getRealName());

    }

    //验证登入 没登入的话这里登入
    public static void LoginChecker(ArrayList<Passenger> PList, Checker checker){
        Scanner in = new Scanner(System.in);

        System.out.println("Please input your identity ID: ");//输入账号
        String identityID = in.nextLine();
        String password = null;
        boolean flag = false;

        if(PList.isEmpty()) {//没人注册的话，就直接跳过
            System.out.println("Eo...Not find the ID!");
        }


        for(Passenger e: PList){

            if(e.getIdentityID().equals( identityID)){//找到账号的话
                flag = true;
                System.out.println("Please input the password: ");
                password = in.next();//输入密码

                if(e.getPassword().equals( password)){//密码符合的话
                    System.out.printf("Welcome, %s !\n", e.getRealName());
                    checker.setChecker(true);//将更改登入状态
                    checker.setP(e.getPassengerID());//记录登入乘客ID

                }
                else {
                    System.out.printf("Uncorrect password!\n\n");
                }
            }
        }
        if(flag == false){
            System.out.printf("Not find the ID!\n\n");
        }

    }


    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityID() {
        return identityID;
    }

    public void setIdentityID(String identityID) {
        this.identityID = identityID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public void setChecker(boolean checker) {
        this.checker = checker;
    }
}
