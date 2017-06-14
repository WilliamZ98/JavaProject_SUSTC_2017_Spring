package Project;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

import static Project.Main.AdminList;
import static Project.Main.PList;
import static Project.Main.checker;

/**
 * Created by S.D.Z on 2017/5/20 0020.
 */

public class FlightData {

    public static void initializeFlightData(ArrayList<Flight> flights) throws IOException, ParseException {

        TXT.turnTXTtoFlighs("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);
        TXT.TXT_add_PList_to_F(flights);
//        Flight f1 = new Flight("CZ1000", "11:10:10", "15:45:00",
//                "ShenZhen", "Beijing", "2017-5-15", "2017-5-15",
//                1000, 0, 250, Flight.FlightStatus.UNPUBLISHED, passengers);
//        Flight f2 = new Flight("CZ1002", "11:10:10", "15:45:00",
//                "ShenZhen", "Neimeng", "2017-5-15", "2017-5-15",
//                1000, 0, 250, Flight.FlightStatus.UNPUBLISHED, passengers);
//        Flight f3 = new Flight("CZ1003", "11:10:10", "15:45:00",
//                "ShenZhen", "Hangzhou", "2017-5-15", "2017-5-15",
//                1000, 0, 250, Flight.FlightStatus.UNPUBLISHED, passengers);
//
//        flights.add(f1);
//        flights.add(f2);
//        flights.add(f3);
    }

    //创建航班
    public static ArrayList<Flight> createFlight(ArrayList<Flight> flights) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.println("Please input the FlightID : ");
        String FlightID = in.nextLine();
        System.out.println("Please input the start Time : (in the form \"HH:mm:ss\")");
        String startTime = in.nextLine();
        System.out.println("Please input the arrival Time :  (in the form \"HH:mm:ss\")");
        String arrivalTime = in.nextLine();
        System.out.println("Please input the start City : ");
        String startCity = in.nextLine();
        System.out.println("Please input the arrival City : ");
        String arrivalCity = in.nextLine();
        System.out.println("Please input the departure Date : (in the form \"yyyy-MM-dd\")");
        String departureDate = in.nextLine();
        System.out.println("Please input the Arrival Date : (in the form \"yyyy-MM-dd\")");
        String arrivalDate = in.nextLine();
        System.out.println("Please input the price : ");
        int price = in.nextInt();
        System.out.println("Please input the seat Capacity : ");
        int seatCapacity = in.nextInt();
        String Seat[] = createSeat(seatCapacity);
        int currentPassengers = 0;
        ArrayList<Passenger> passengers = new ArrayList<>();

        Flight New = new Flight(FlightID, startTime, arrivalTime, startCity, arrivalCity, departureDate, arrivalDate,
                price, currentPassengers, seatCapacity, Flight.FlightStatus.UNPUBLISHED, passengers, Seat);

        flights.add(New);
//        String S = TXT.FlightToString(New);
//        TXT.contentToTxt("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", S);

        TXT.coverTXT_F("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);//重写TXT


        System.out.printf("You have create Flight %s !\n", FlightID);

        return flights;
    }


    public static String[] createSeat(int a){
        String[] S = new String[a];

            //排列座位 每行五个 依次排列
            for (int i = 1; i <= a/5 + 1; i++) {
                for (int n = 1; n <= 5 && 5*i + n <= a; n++) {
                    switch (n) {
                        case 1:
                            S[5 * (i - 1) + n - 1] = "A" + i;
                            break;
                        case 2:
                            S[5 * (i - 1) + n - 1] = "B" + i;
                            break;
                        case 3:
                            S[5 * (i - 1) + n - 1] = "C" + i;
                            break;
                        case 4:
                            S[5 * (i - 1) + n - 1] = "D" + i;
                            break;
                        case 5:
                            S[5 * (i - 1) + n - 1] = "E" + i;
                            break;
                    }
                }
            }

        return S;
    }


    //发布航班
    public static void publish(ArrayList<Admin> AdminList, ArrayList<Flight> flights) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);

        FlightData.publishDisplyFlight(flights);
        System.out.println("Choose the one you want to publish :   (Input 0 to quit)");
        int a = Main.inputDecide(in.nextLine());//判断输入的是否为数字

        if (a > 0 && flights.get(a - 1).getFlightStatus().equals(Flight.FlightStatus.UNPUBLISHED)) {//判断为未发布的航班
            Flight published = flights.get(a - 1);//得到航班
            published.setFlightStatus(Flight.FlightStatus.AVAILABLE);//更改状态
            flights.set(a-1, published);//替换原来的published

            TXT.coverTXT_F("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);//重写TXT

            System.out.println("Update Success !");

            C_or_Q_P(AdminList, flights);



        }
        else if (a == 0) {
            Main.AdminFunction(AdminList, flights);
        } else {
            System.out.println("Please make sure your input is correct number!");
            publish(AdminList, flights);
        }


    }


    //Continued or quite 用于上个方法
    public static void C_or_Q_P(ArrayList<Admin> AdminList, ArrayList<Flight> flights) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);

        System.out.printf("Continue or quite :\n" +
                "【1】Continue.\n" +
                "【2】Quite.\n");
        int d1 = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (d1 > 0 && d1 <= 2) {
            switch (d1) {
                case 1:
                    FlightData.publish(AdminList, flights);
                    break;
                case 2:
                    Main.AdminFunction(AdminList, flights);
                    break;
                default:
                    System.out.println("Please enter the correct number!");
                    FlightData.publish(AdminList, flights);
            }
        } else {
            System.out.println("Please make sure your input is correct number!");
            C_or_Q_P(AdminList, flights);
        }


    }


    //更新航班
    public static void updateFlight(ArrayList<Flight> flights) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);

        queryAll(flights);//显示所有航班
        System.out.println("Choose the one you want change: 【Input 0 to quite】");
        int a = Main.inputDecide(in.nextLine());//判断输入的是否为数字
//        System.out.println(AdminList.size());
        if (a > 0 && a <= flights.size()) {
            Flight changed = flights.get(a - 1);
//                Flight(String FlightID, String startTime, String arrivalTime, String startCity, String arrivalCity, String departureDate,
//                int price, int currentPassengers, int seatCapacity, FlightStatus flightStatus, ArrayList<Passenger> passengers)

            updateFunction(changed, a, flights);//选择某个航班更新

            TXT.coverTXT_F("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);//重写TXT

            updateFlight(flights);
        }
        else if (a == 0){
            Main.AdminFunction(AdminList, flights);//返回上一界面

        }else {
            System.out.println("Please make sure your input is correct number!");
            updateFlight(flights);
        }


    }


    //判断所选航班状态  确定可更改属性
    public static void updateFunction(Flight changed, int a, ArrayList<Flight> flights) throws IOException, ParseException {
        if(changed.getFlightStatus().equals(Flight.FlightStatus.UNPUBLISHED)||
                changed.getFlightStatus().equals(Flight.FlightStatus.TERMINATE )){
            updateFunction_U(changed, a, flights);//选择某个航班更新
        }
        else {
            updateFunction_P(changed, a, flights);//选择某个航班更新
        }
    }

    //用于上面 跟新航班信息选择
    public static void updateFunction_P(Flight changed, int a, ArrayList<Flight> flights) throws IOException {
        Scanner in = new Scanner(System.in);

        System.out.printf("Do you want to change\n" +
                "【1】Arrival Time\n" +
                "【2】Arrival Date\n" +
                "【3】Price\n" +
                "【4】Seat Capacity\n" +
                "【5】Quit\n");

        int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (d > 0 && d <= 5) {
            switch (d) {
                case 1:
                    System.out.println("New Arrival Time :");
                    String s2 = in.nextLine();
                    changed.setArrivalTime(s2);
                    flights.set(a - 1, changed);
                    break;
                case 2:
                    System.out.println("New Arrival Date :");
                    String s4 = in.nextLine();
                    changed.setArrivalDate(s4);
                    flights.set(a - 1, changed);
                    break;
                case 3:
                    System.out.println("New Price :");
                    int s6 = in.nextInt();
                    changed.setPrice(s6);
                    flights.set(a - 1, changed);
                    break;
                case 4:
                    System.out.println("New Seat Capacity :");
                    int s7 = in.nextInt();
                    changed.setSeatCapacity(s7);
                    flights.set(a - 1, changed);
                    break;
                case 5:
                    break;
            }

            TXT.coverTXT_F("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);//重写TXT

        } else {
            System.out.println("Please make sure your input is correct number!");
            updateFunction_P(changed, a, flights);
        }
        System.out.println("Change success!");

    }


    //用于上面 跟新航班信息选择
    public static void updateFunction_U(Flight changed, int a, ArrayList<Flight> flights) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);

        System.out.printf("Do you want to change\n" +
                "【1】Start Time\n" +
                "【2】Arrival Time\n" +
                "【3】Start City\n" +
                "【4】Arrival City\n" +
                "【5】Departure Date\n" +
                "【6】Arrival Date\n" +
                "【7】Price\n" +
                "【8】Seat Capacity\n" +
                "【9】Delete\n" +
                "【10】Quit\n");

        int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (d > 0 && d <= 10) {
            switch (d) {
                case 1:
                    System.out.println("New Start Time :");
                    String s1 = in.nextLine();
                    changed.setStartTime(s1);
                    flights.set(a - 1, changed);
                    break;
                case 2:
                    System.out.println("New Arrival Time :");
                    String s2 = in.nextLine();
                    changed.setArrivalTime(s2);
                    flights.set(a - 1, changed);
                    break;
                case 3:
                    System.out.println("New Start City :");
                    String s3 = in.nextLine();
                    changed.setStartCity(s3);
                    flights.set(a - 1, changed);
                    break;
                case 4:
                    System.out.println("New Arrival City :");
                    String s4 = in.nextLine();
                    changed.setArrivalCity(s4);
                    flights.set(a - 1, changed);
                    break;
                case 5:
                    System.out.println("New Departure  Date :");
                    String s5 = in.nextLine();
                    changed.setDepartureDate(s5);
                    flights.set(a - 1, changed);
                    break;
                case 6:
                    System.out.println("New Arrival  Date :");
                    String s10 = in.nextLine();
                    changed.setArrivalDate(s10);
                    flights.set(a - 1, changed);
                    break;
                case 7:
                    System.out.println("New Price :");
                    int s6 = in.nextInt();
                    changed.setPrice(s6);
                    flights.set(a - 1, changed);
                    break;
                case 8:
                    System.out.println("New Seat Capacity :");
                    int s7 = in.nextInt();
                    changed.setSeatCapacity(s7);
                    flights.set(a - 1, changed);
                    break;
                case 9:
                    if (flights.size() > 1) {
                        for (int n = 0, len = flights.size(); n < len; ++n) {
                            if (flights.get(n).equals(changed)) {
                                flights.remove(n);
                                --len;//减少一个
                                --n;
                                System.out.printf("Delete success!\n");
                            }
                        }
                    } else
                        System.out.println("Can't delete this Admin account, for this is the last one!!!");
                    break;
                case 10:
                    break;

                default:
                    System.out.println("Please enter the correct number!");
                    break;
            }

            TXT.coverTXT_F("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);//重写TXT


        }
        else if(d == 0){
            Main.AdminFunction(AdminList, flights);
        }
        else {
            System.out.println("Please make sure your input is correct number!");
            updateFunction_U(changed, a, flights);
        }
        System.out.println("Change success!");


    }


    //删除航班
    public static void deleteFlight(ArrayList<Admin> AdminList, ArrayList<Flight> flights) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);

        FlightData.deleteDisplyFlight(flights);//显示可删除航班

        System.out.println("Choose the one you want to delete (Press 0 to quite):");
        int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (d > 0 && (flights.get(d - 1).getFlightStatus().equals(Flight.FlightStatus.UNPUBLISHED) ||
                flights.get(d - 1).getFlightStatus().equals(Flight.FlightStatus.TERMINATE ))){

            Flight changed = flights.get(d - 1);
            for (int i = 0, len = flights.size(); i < len; ++i) {
                if (flights.get(i) == changed) {
                    flights.remove(i);
                    --len;//减少一个
                    --i;
                    System.out.printf("Success!\n");
                }
            }

            TXT.coverTXT_F("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);//重写TXT

            C_or_Q_d(AdminList, flights);

        }
        else if (d == 0) {
            Main.AdminFunction(AdminList, flights);
        } else {
            System.out.println("Please make sure your input is correct number!");
            Main.AdminFunction(AdminList, flights);
        }


    }

    //Continued or quite 用于上个方法
    public static void C_or_Q_d(ArrayList<Admin> AdminList, ArrayList<Flight> flights) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);

        System.out.printf("Continue or quite :\n" +
                "【1】Continue.\n" +
                "【2】Quite.\n");
        int d1 = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (d1 > 0 && d1 <= 2) {
            switch (d1) {
                case 1:
                    FlightData.deleteFlight(AdminList, flights);
                case 2:
                    Main.AdminFunction(AdminList, flights);
                default:
                    System.out.println("Please enter the correct number!");
                    FlightData.deleteFlight(AdminList, flights);
            }


        } else {
            System.out.println("Please make sure your input is correct number!");
            C_or_Q_d(AdminList, flights);
        }


    }


    /**
     * 未完成
     *
     * @param AdminList
     * @param flights
     */
    //super查询航班
    public static void superQuery(ArrayList<Admin> AdminList, ArrayList<Flight> flights, ArrayList<Passenger> PList) throws ParseException, IOException {
        Main.terminate(flights);

        Scanner in = new Scanner(System.in);

        System.out.println("Please input the number: ");
        System.out.printf(
                "【1】 Query all orders of one flight.\n" +
                        "【2】 Query Order.\n" +
                        "【3】 Normal query\n" +
                        "【4】Quit.\n");

        int i = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (i > 0 && i <= 4) {
            switch (i) {
                case 1:
                    oneFlight(flights, PList);
                    FlightData.superQuery(AdminList, flights, PList);


                case 2:
                    Order.orderDisply(PList);
                    FlightData.superQuery(AdminList, flights, PList);

                case 3:
                    FlightData.queryFlight(flights);
                    FlightData.superQuery(AdminList, flights, PList);

                case 4:
                    Main.AdminFunction(AdminList, flights);
                default:
                    System.out.println("Please enter the correct number!");
                    FlightData.superQuery(AdminList, flights, PList);
            }
        } else {
            System.out.println("Please make sure your input is correct number!");
            superQuery(AdminList, flights, PList);
        }


    }


    //查询航班  乘客功能
    public static void queryFlight(ArrayList<Flight> flights) throws ParseException, IOException {
        Main.terminate(flights);//检验是否有航班过期

        Scanner in = new Scanner(System.in);

        System.out.println("Please input the number: ");
        System.out.printf(
                "【1】 Query by start city, arrival city and start date.\n" +
                        "【2】 Query by start city.\n" +
                        "【3】 Query by arrival city.\n" +
                        "【4】 Query by start date.\n" +
                        "【5】 Query by fight ID.\n" +
                        "【6】 All flights.\n" +
                        "【7】Quit.\n");

        int i = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (i > 0 && i <= 7) {
            switch (i) {
                case 1://准确
                    FlightData.accuracyQuery(flights);
                    break;
                case 2://准确 起飞城市
                    FlightData.accuracyQuery1(flights);
                    break;

                case 3://准确
                    FlightData.accuracyQuery2(flights);
                    break;

                case 4://准确
                    FlightData.accuracyQuery3(flights);
                    break;

                case 5://模糊
                    FlightData.fuzzySearch(flights);
                    break;
                case 6://所有
                    FlightData.orderDisplyFlight(flights);
                    break;
                case 7:
                    Main.first(AdminList, flights, PList, checker);//不知道直接调用可不可以
                    break;
            }
            turnToOrder(flights);//判断是否要去预订

        } else {
            System.out.println("Please make sure your input is correct number!");
            FlightData.queryFlight(flights);
        }
    }


    //用于上面循环
    //判断
    public static void turnToOrder(ArrayList<Flight> flights) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);

        System.out.println("Do you want to order？【 Please input Y or N 】");
        String s = in.nextLine();
        if(s.equals("Yes") || s.equals("Y")){
            Order.reserveFlight(PList, flights, checker);//跳转到预订航班
            //直接这样用不知道虚不虚

            FlightData.queryFlight(flights);//预订完之后、返回查询页面

        }
        else if(s.equals("No") || s.equals("N")){
            FlightData.queryFlight(flights);//返回查询页面
        }
        else {
            System.out.println("**** Please input Y or N ****");
            turnToOrder(flights);
        }
    }


    //精确搜索
    public static void accuracyQuery(ArrayList<Flight> flights) {
        Scanner in = new Scanner(System.in);

        //        String null1 = in.nextLine();//for scanner bug
        System.out.println("The start city: ");
        String startcity = in.nextLine();
        System.out.println("The arrival city: ");
        String arrivalcity = in.nextLine();
        System.out.println("The start date: ");
        String departuredate = in.nextLine();


        titleBrushP();

        for (Flight e : flights) {
            if (e.getStartCity().equals(startcity) &&
                    e.getArrivalCity().equals(arrivalcity) &&
                    e.getDepartureDate().equals(departuredate) &&
                    e.getFlightStatus().equals(Flight.FlightStatus.AVAILABLE)) {

                brushP(flights, e);
            }
        }


    }

    //精确搜索 起始城市
    public static void accuracyQuery1(ArrayList<Flight> flights) {
        Scanner in = new Scanner(System.in);

        System.out.println("The start city: ");
        String startcity = in.nextLine();
        titleBrushP();

        for (Flight e : flights) {
            if (e.getStartCity().equals(startcity) &&
                    e.getFlightStatus().equals(Flight.FlightStatus.AVAILABLE)) {
                brushP(flights, e);
            }
        }


    }

    //精确搜索 到达城市
    public static void accuracyQuery2(ArrayList<Flight> flights) {
        Scanner in = new Scanner(System.in);

        System.out.println("The arrival city: ");
        String arrivalcity = in.nextLine();
        titleBrushP();

        for (Flight e : flights) {
            if (e.getArrivalCity().equals(arrivalcity) &&
                    e.getFlightStatus().equals(Flight.FlightStatus.AVAILABLE)) {
                brushP(flights, e);
            }
        }


    }

    //精确搜索 出发时间
    public static void accuracyQuery3(ArrayList<Flight> flights) {
        Scanner in = new Scanner(System.in);

        System.out.println("The start date: ");
        String departuredate = in.nextLine();
        titleBrushP();
        for (Flight e : flights) {
            if (e.getDepartureDate().equals(departuredate) &&
                    e.getFlightStatus().equals(Flight.FlightStatus.AVAILABLE)) {
                brushP(flights, e);
            }
        }


    }


    //模糊搜索
    public static void fuzzySearch(ArrayList<Flight> flights) {
        Scanner in = new Scanner(System.in);
        //        String null1 = in.nextLine();//for scanner bug
        System.out.println("The similar ID: ");
        String ID = in.nextLine();//输入相似的ID
        titleBrushP();
        for (Flight e : flights) {
//            Pattern p = Pattern.compile(e.getFlightID());
//            Matcher matcher = p.matcher(ID);
            if (e.getFlightID().contains(ID)&&
                    e.getFlightStatus().equals(Flight.FlightStatus.AVAILABLE)) {//如果航班ID中包涵有输入的ID
                brushP(flights, e);
            }
        }


    }


    //全搜索
    public static void queryAll(ArrayList<Flight> flights) {
        titleBrushAdmin();
        for (Flight e : flights) {
            brushAdmin(flights, e);
        }
    }


    //显示航班 用于publish
    public static void publishDisplyFlight(ArrayList<Flight> flights) {
        titleBrushAdmin();
        for (Flight e : flights) {
            if (e.getFlightStatus().equals(Flight.FlightStatus.UNPUBLISHED)) {
                brushAdmin(flights, e);
            }

        }
    }

    //显示航班 用于delete
    public static void deleteDisplyFlight(ArrayList<Flight> flights) {
        titleBrushAdmin();
        for (Flight e : flights) {
            if (e.getFlightStatus().equals(Flight.FlightStatus.UNPUBLISHED)||
                    e.getFlightStatus().equals(Flight.FlightStatus.TERMINATE)) {
                brushAdmin(flights, e);
            }
        }
    }

    //显示航班属名称 无status
    public static void titleBrushP() {
        System.out.printf(
                "【N】\tFlightID\tStartTime\tArrivalTime\t\tStartCity\tArrivalCity\t\tDepartureDate\tArrivalDate\t\tPrice\tRemain Seats\t\n");
    }


    //显示航班属名称 有status
    public static void titleBrushAdmin() {
        System.out.printf(
                "【N】\tFlightID\tStartTime\tArrivalTime\t\tStartCity\tArrivalCity\t\tDepartureDate\tArrivalDate\t\tPrice\tCurrentPassengers\tSeatCapacity\tFlightStatus\n");
    }


    //刷出航班 Admin 可见航班状态的
    public static void brushAdmin(ArrayList<Flight> flights, Flight f) {
        System.out.printf(
                "【%d】\t%8s\t%8s\t%10s\t%12s\t%10s\t\t%10s\t\t%10s\t%8d\t%8d\t\t\t%8d\t\t%8s\t\n",
                flights.indexOf(f) + 1, f.getFlightID(), f.getStartTime(), f.getArrivalTime(), f.getStartCity(), f.getArrivalCity(),
                f.getDepartureDate(), f.getArrivalDate(), f.getPrice(), f.getCurrentPassengers(), f.getSeatCapacity(), f.getFlightStatus());
    }

    //刷出航班 Passengers 不可见航班状态的
    public static void brushP(ArrayList<Flight> flights, Flight f) {
        System.out.printf(
                //航班编号/ 航班ID/ 起飞具体时间/ 降落时间/ 起飞城市/ 到达城市/ 起飞日期/ 价格/ 可预定数量
                "【%d】\t%8s\t%8s\t%10s\t%12s\t%10s\t\t%10s\t\t%10s\t%8d\t%8d\t\n",
                flights.indexOf(f) + 1, f.getFlightID(), f.getStartTime(), f.getArrivalTime(), f.getStartCity(), f.getArrivalCity(),
                f.getDepartureDate(), f.getArrivalDate(), f.getPrice(), f.getSeatCapacity() - f.getCurrentPassengers());
    }

    //用于乘客
    //显示航班 用于预订/列出所有可预定航班
    // i为输出航班数
    public static int orderDisplyFlight(ArrayList<Flight> flights) {
        int i = 0;
        titleBrushP();
        for (Flight e : flights) {
            if (e.getSeatCapacity() - e.getCurrentPassengers() > 0 && e.getFlightStatus().equals(Flight.FlightStatus.AVAILABLE)) {
                i++;
                brushP(flights, e);
            }

        }
        return i;
    }


    //显示某一航班的所有预订信息
    public static void oneFlight(ArrayList<Flight> flights, ArrayList<Passenger> PList) {
        Scanner in = new Scanner(System.in);
        queryAll(flights);//显示所有航班
        System.out.println("Choose the one you want detail :");
        int i = Main.inputDecide(in.nextLine());//判断输入的是否为数字
        if (i > 0 && (flights.get(i-1).getFlightStatus().equals(Flight.FlightStatus.AVAILABLE) ||
                flights.get(i-1).getFlightStatus().equals(Flight.FlightStatus.FULL))||
                (flights.get(i-1).getFlightStatus().equals(Flight.FlightStatus.TERMINATE))) {
            Flight f = flights.get(i - 1);//得到这个航班
            String FID = f.getFlightID();

            String pattern = "yyyy-MM-dd HH:mm:ss";//时间格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            System.out.printf(
                    "【N】\tpassengerID\tseat\tflight\t\tcreateDate\t\t\t\tstatus\n");
            for (int n = 0; n < PList.size(); n++) {
                Passenger passenger = PList.get(n);
                ArrayList<Order> orderList = passenger.getOrderList();


                for (Order o : orderList) {
                    if (o.getFlight().getFlightID().equals(FID)) {

                        String date = simpleDateFormat.format(o.getCreateDate());//格式化时间输出年月日

                        System.out.printf(
                                "【%d】\t%s\t\t\t%s\t\t%s\t\t%s\t%10s\n",
                                passenger.getOrderList().indexOf(o) + 1, o.getPassengerID(), o.getSeat(), o.getFlight().getFlightID(), date, o.getStatus());
                    }
                }
            }
        } else {
            System.out.println("Please make sure your input is correct number!");
            oneFlight(flights, PList);
        }


    }


}


