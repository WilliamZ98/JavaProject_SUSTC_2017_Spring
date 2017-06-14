package Project;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Created by S.D.Z on 2017/5/3 0003.
 */
public class Main {
    public static ArrayList<Admin> AdminList;
    public static ArrayList<Flight> flights;
    public static ArrayList<Passenger> PList;
    public static Checker checker;

    public static void main(String[] args) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);
        //初始化Admin
        AdminList = new ArrayList<>();
        Admin initial = new Admin("Admin", "123456");
        AdminList.add(initial);

//        for(Admin e: AdminList){
//            System.out.println(e.getPassword());
//        }

        //初始化fights
        flights = new ArrayList<>();
        FlightData.initializeFlightData(flights);


        //初始化passenger
        PList = new ArrayList<>();
        Passenger.initializePassenger(PList);

        //初始化乘客是否登入
        checker = new Checker(false, 0);


        Main.first(AdminList, flights, PList, checker);

        in.close();
    }




    //第一个界面
    public static void first(ArrayList<Admin> AdminList, ArrayList<Flight> flights, ArrayList<Passenger> PList, Checker checker) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);

        if(checker.isChecker()){
            System.out.printf("Please input the number:   【Account ：%s】\n", PList.get(checker.getP()-1).getRealName());}//没用。。。
        else
            System.out.printf("Please input the number:   \n");

        System.out.printf(
                "【1】 Login for Admin\n" +
                        "【2】 Query Flight Information\n" +
                        "【3】 Query Order List\n" +
                        "【4】 Reserve Flight\n" +
                        "【5】 Unsubsribe Flight\n" +
                        "【6】 Passengers sign up\n" +
                        "【7】 Passengers log in.\n" +
                        "【8】 Quite.\n");

        int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (d > 0 && d <= 8 ) {
            switch (d) {
                case 1: //登入Admin
                    Admin.logIn(AdminList, flights);
                    Main.first(AdminList, flights, PList, checker);
                case 2:
                    FlightData.queryFlight(flights);
                    Main.first(AdminList, flights, PList, checker);
                case 3:
                    Order.orderQuery(PList, flights, checker);
                    Main.first(AdminList, flights, PList, checker);
                case 4:
                    Order.reserveFlight(PList, flights, checker);
                    Main.first(AdminList, flights, PList, checker);
                case 5:
                    Order.unsubsribeFlight(PList, flights, checker);
                    Main.first(AdminList, flights, PList, checker);
                case 6:
                    Passenger.signUpP(PList);
                    Main.first(AdminList, flights, PList, checker);
                case 7:
                    Passenger.LoginChecker(PList, checker);
                    Main.first(AdminList, flights, PList, checker);
                case 8:
                    break;
                default:
                    System.out.println("Please enter the correct number!\n");
                    Main.first(AdminList, flights, PList, checker);

            }
        }else{
            System.out.println("Please make sure your input is correct number!");
            first(AdminList,flights, PList, checker);
        }


    }

    //管理员功能
    public static void AdminFunction(ArrayList<Admin> AdminList, ArrayList<Flight> flights) throws ParseException, IOException {
        terminate(flights);

        Scanner in = new Scanner(System.in);

        System.out.println("Please input the number: ");
        System.out.printf(
                        "【1】 Create a flight.\n" +
                        "【2】 Update flights.\n" +
                        "【3】 Publish flight..\n"+
                        "【4】 Delete flight.\n" +
                        "【5】 Super Query.\n" +
                        "【6】 Manage users.\n" +
                        "【7】 Quite.\n");

        while(in.hasNext()) {
            String s = in.nextLine();
            int d = Main.inputDecide(s);//判断输入的是否为数字
            if (d > 0 && d <= 7) {
                switch (d) {
                    case 1:
                        FlightData.createFlight(flights);
                        AdminFunction(AdminList, flights);
                    case 2:
                        FlightData.updateFlight(flights);
                        AdminFunction(AdminList, flights);
                    case 3:
                        FlightData.publish(AdminList, flights);
                        AdminFunction(AdminList, flights);
                    case 4:
                        FlightData.deleteFlight(AdminList, flights);
                        AdminFunction(AdminList, flights);
                    case 5:
                        FlightData.superQuery(AdminList, flights, PList);//虚。。。
                        AdminFunction(AdminList, flights);
                    case 6:
                        Admin.userManagement(AdminList);
                        AdminFunction(AdminList, flights);
                    case 7:
                        Main.first(AdminList, flights, PList, checker);
                }
            } else {
                System.out.println("Please make sure your input is correct number!");
                AdminFunction(AdminList, flights);
            }
        }



    }

    //时间判断  真为超过两小时
    public static boolean timeDecide(Flight flight) throws ParseException {
        String departureDate = flight.getDepartureDate();
        String startTime = flight.getStartTime();

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sfm = new SimpleDateFormat(pattern);

        Date date = sfm.parse(String.format("%s %s", departureDate, startTime));//将String转化为Date
        Date now = new Date();

        Calendar cdate = Calendar.getInstance();
        Calendar cnow = Calendar.getInstance();

        cdate.setTime(date);
        cnow.setTime(now);

        long hour = cdate.getTimeInMillis();
        long hour_now = cnow.getTimeInMillis();
        long hour_diff = hour - hour_now;

        if(hour_diff > 2*(60 * 60 * 1000)){
            return false;
        }
        else {
            return true;
        }
    }

    //修改
    public static void terminate(ArrayList<Flight> flights) throws ParseException {
        for(Flight f: flights){
            if(timeDecide(f)){
                f.setFlightStatus(Flight.FlightStatus.TERMINATE);
            }
        }
    }


    //判断输入的是否为数字
    // 是 为返回输入的数字，否 则输出 -1
    public static int inputDecide(String s){

        if (Pattern.matches("\\d+", s)){//判断s是否为数字
            return Integer.parseInt(s);
        }
        else {
            return -1;
        }
    }
}