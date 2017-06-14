package Project;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by S.D.Z on 2017/5/20 0020.
 */
public class TXT {
    public static void main(String[] args) throws ParseException, IOException {
        ArrayList<Flight> flights = new ArrayList<>();
//        FlightData.initializeFlightData(flights);
//        FlightData.queryAll(flights);
//
//        System.out.println();

//        turnTXTtoFlighs("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);
//        FlightData.queryAll(flights);
//        readTXT("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt");



    }


    //将TXT 转化为flights
    public static void turnTXTtoFlighs(String filePath, ArrayList<Flight> flights) throws IOException {
        File a = new File(filePath);
        if (a.exists()) {
            FileInputStream fi = new FileInputStream(a);
            InputStreamReader isr = new InputStreamReader(fi, "GBk");
            BufferedReader bfin = new BufferedReader(isr);
            String rLine = "";
            while ((rLine = bfin.readLine()) != null) {
                String[] flight = rLine.split(", ");//用“, ”将String分为一个数组

                String FlightID = flight[0];
                String startTime = flight[1];
                String arrivalTime = flight[2];
                String startCity = flight[3];
                String arrivalCity = flight[4];
                String departureDate = flight[5];
                String arrivalDate = flight[6];
                int price = Integer.parseInt(flight[7]);
                int currentPassengers = Integer.parseInt(flight[8]);
                int seatCapacity = Integer.parseInt(flight[9]);
                Flight.FlightStatus status = flightStatusChoose(Integer.parseInt(flight[10]));

                ArrayList<Passenger> passengers = new ArrayList<>();//先初始化
                String[] Seat = FlightData.createSeat(seatCapacity);//先初始化

                Flight New = new Flight(FlightID, startTime, arrivalTime, startCity, arrivalCity, departureDate, arrivalDate,
                        price, currentPassengers, seatCapacity, status, passengers, Seat);


                flights.add(New);//将航班添加进去
            }

        }
    }


    //可多次调用
    //读取TXT
    public static String readTXT(String filePath) throws IOException {
        String S = "";//初始化
        File a = new File(filePath);
        if (a.exists()) {
            FileInputStream fi = new FileInputStream(a);
            InputStreamReader isr = new InputStreamReader(fi, "GBk");
            BufferedReader bfin = new BufferedReader(isr);
            String rLine = "";
            while ((rLine = bfin.readLine()) != null) {
                S += rLine + "\n";
            }
        }
        return S;
    }


    //Flight
    //覆盖TXT的
    public static void coverTXT_F(String filePath, ArrayList<Flight> flights) throws IOException {
        File f = new File(filePath);

        String rewrite = "";


        if (f.exists()) {
            for (Flight flight : flights) {
                String s = FlightToString(flight);
                rewrite += s + "\n";
            }
        }else {
            f.createNewFile();//找不到就新建文件
            for (Flight flight : flights) {
                String s = FlightToString(flight);
                rewrite += s + "\n";
            }
        }


        BufferedWriter output = new BufferedWriter(new FileWriter(f));//创建写入对象
        output.write(rewrite);//将rewrite 写入
        output.close();
    }


    //Flight
    //讲一个航班转化成 一行String
    public static String FlightToString(Flight flight) {
//
//        Flight(String FlightID, String startTime, String arrivalTime, String startCity, String arrivalCity, String departureDate, String arrivalDate,
//        int price, int currentPassengers, int seatCapacity, FlightStatus flightStatus, ArrayList<Passenger> passengers)

        String s[] = new String[12];
        s[0] = "";
        s[1] = flight.getFlightID();
        s[2] = flight.getStartTime();
        s[3] = flight.getArrivalTime();
        s[4] = flight.getStartCity();
        s[5] = flight.getArrivalCity();
        s[6] = flight.getDepartureDate();
        s[7] = flight.getArrivalDate();
        s[8] = String.valueOf(flight.getPrice());
        s[9] = String.valueOf(flight.getCurrentPassengers());
        s[10] = String.valueOf(flight.getSeatCapacity());
        s[11] = String.valueOf(flightStatusSet(flight.getFlightStatus()));

        String S = "";//初始化S

        for (int i = 1; i < 11; i++) {
            S += s[i] + ", "; //将每个String加起来
        }
        S += s[11];
        return S;//返回一行
    }

    //Flight
    //读取TXT中的航班状态
    public static Flight.FlightStatus flightStatusChoose(int a) {
        Flight.FlightStatus status;
        switch (a) {
            case 1:
                status = Flight.FlightStatus.UNPUBLISHED;
                break;
            case 2:
                status = Flight.FlightStatus.AVAILABLE;
                break;
            case 3:
                status = Flight.FlightStatus.FULL;
                break;
            default:
                status = Flight.FlightStatus.TERMINATE;
                break;
        }
        return status;
    }

    //Flight
    //读取航班中的状态 转化成TXT
    public static int flightStatusSet(Flight.FlightStatus status) {
        int a;
        if (status.equals(Flight.FlightStatus.UNPUBLISHED)) {
            a = 1;
        }//unpublished 代 1
        else if (status.equals(Flight.FlightStatus.AVAILABLE)) {
            a = 2;
        }//available 代 2
        else if (status.equals(Flight.FlightStatus.FULL)) {
            a = 3;
        }//Full 代 3
        else {
            a = 0;//其他代0
        }
        return a;
    }

    //Flight
    //用于initialize Flight的PList
    //用TXT给每个flight附上PList
    public static void TXT_add_PList_to_F(ArrayList<Flight> flights) throws IOException, ParseException {

        for(Flight the : flights) {
            int p = flights.indexOf(the);//获取the的位置
            ArrayList<Passenger> PList = the.getPassengers();//得到对应航班的PList
            String filePath = "D:\\JAVA\\workspace\\Final\\src\\Project\\PassengerList_In_Flight\\" + p + ".txt";//设置文件位置
            turnTXTtoPList(filePath, PList);//读取乘客列表
            the.setPassengers(PList);//替换替换乘客列表
            flights.set(p, the);//替换航班
        }
    }

    //Flight
    //输入checker和Plist 得到PList的TXT文件 每个文件代表相应passengerID的PList
    public static void createTXT_P(ArrayList<Flight> flights) throws IOException {
        for(Flight the : flights) {
            int p = flights.indexOf(the);//获取the的位置
            ArrayList<Passenger> PList = the.getPassengers();//得到对应航班的PList
            String filePath = "D:\\JAVA\\workspace\\Final\\src\\Project\\PassengerList_In_Flight\\" + p + ".txt";//设置文件位置
            coverTXT_P(filePath, PList);//写入TXT
        }
    }




    //Passengers
    //将TXT 转化为flights
    public static void turnTXTtoPList(String filePath, ArrayList<Passenger> PList) throws IOException {
        //        Passenger(int passengerID, String realName, String identityID, String password, ArrayList<Order> orderList){


        File a = new File(filePath);
        if (a.exists()) {
            FileInputStream fi = new FileInputStream(a);
            InputStreamReader isr = new InputStreamReader(fi, "GBk");
            BufferedReader bfin = new BufferedReader(isr);
            String rLine = "";
            while ((rLine = bfin.readLine()) != null) {
                String[] p = rLine.split(", ");//用“, ”将String分为一个数组

                //一个个赋值
                int passengerID = Integer.parseInt(p[0]);
                String realName = p[1];
                String identityID = p[2];
                String password = p[3];

                ArrayList<Order> OrderList = new ArrayList<>();//先初始化，后面再加

                Passenger New = new Passenger( passengerID, realName, identityID, password, OrderList);

                PList.add(New);
            }

        }
    }




    //Passenger
    //覆盖TXT的 写入passenger数据
    public static void coverTXT_P(String filePath, ArrayList<Passenger> PList) throws IOException {
        File f = new File(filePath);
        String rewrite = "";//初始化

        if (f.exists()) {
            for (Passenger passenger : PList) {
                String s = PassengerToString(passenger);
                rewrite += s + "\n";
            }
        }else {
            f.createNewFile();//找不到就新建文件
            for (Passenger passenger : PList) {
                String s = PassengerToString(passenger);
                rewrite += s + "\n";
            }
        }

        BufferedWriter output = new BufferedWriter(new FileWriter(f));//创建写入对象
        output.write(rewrite);//将rewrite 写入
        output.close();
    }



    private static String PassengerToString(Passenger passenger) {
        //        Passenger(int passengerID, String realName, String identityID, String password, ArrayList<Order> orderList){

        String s[] = new String[5];
        s[0] = "";
        s[1] = String.valueOf(passenger.getPassengerID());
        s[2] = passenger.getRealName();
        s[3] = passenger.getIdentityID();
        s[4] = passenger.getPassword();

        String S = "";//初始化S

        for (int i = 1; i <= 4; i++) {
            S += s[i] + ", "; //将每个String加起来
        }
        return S;//返回一行
    }


    //Order
    //输入checker和Plist 得到OrderList的TXT文件 每个文件代表相应passengerID的OrderList
    public static void createTXT_O(ArrayList<Passenger> PList, Checker checker) throws IOException {
        Passenger the = PList.get(checker.getP()-1);//获取乘客
        int p = the.getPassengerID();//获取passengerID
        ArrayList<Order> OrderList = the.getOrderList();//得到对应乘客的OrderList
        String filePath = "D:\\JAVA\\workspace\\Final\\src\\Project\\OrderList\\" + p + ".txt";//设置文件位置
        coverTXT_O(filePath, OrderList);//写入TXT
    }

    //Order
    //用于initialize Passenger的OrderList
    //用TXT给每个Passenger附上OrderList
    public static void TXT_add_OrderList_to_P(ArrayList<Passenger> PList) throws IOException, ParseException {

        for(Passenger the : PList) {
            int p = the.getPassengerID();//获取passengerID
            ArrayList<Order> OrderList = the.getOrderList();//得到对应乘客的OrderList
            String filePath = "D:\\JAVA\\workspace\\Final\\src\\Project\\OrderList\\" + p + ".txt";//设置文件位置
            turnTXTtoOrderList(filePath, OrderList);

            the.setOrderList(OrderList);//
            PList.set(p-1, the);
        }
    }


    //Order
    //read TXT
    public static void turnTXTtoOrderList(String filePath, ArrayList<Order> OrderList) throws IOException, ParseException {
        File a = new File(filePath);
        if (a.exists()) {
            FileInputStream fi = new FileInputStream(a);
            InputStreamReader isr = new InputStreamReader(fi, "GBk");
            BufferedReader bfin = new BufferedReader(isr);
            String rLine = "";
            while ((rLine = bfin.readLine()) != null) {
                String[] order = rLine.split(", ");//用“, ”将String分为一个数组

                //         Order(int passengerID, String seat, Flight flight, Date createDate, OrderStatus status)

                int passengerID = Integer.parseInt(order[0]);
                String seat = order[1];

                String s = S_to_String(order, 2, 12);
                Flight flight = String_to_Flight(s);
                Date createDate = String_to_Date(order[13]);
                Order.OrderStatus status = orderStatusGet(Integer.parseInt(order[14]));

                Order New = new Order(passengerID, seat, flight, createDate, status);//新建一个

                OrderList.add(New);//将航班添加进去
            }

        }
    }


    //Order
    //String [] S to String 从S[a] 到S[b]
    public static String S_to_String(String[] S, int a, int b){
        String s = "";
        for(int i = a; i < b; i++ ){
            s += S[i] + ", ";
        }
        s += S[b];
        return s;
    }


    //Order
    //String to Flight
    public static Flight String_to_Flight(String s){
        String[] flight = s.split(", ");//用“, ”将String分为一个数组

        String FlightID = flight[0];
        String startTime = flight[1];
        String arrivalTime = flight[2];
        String startCity = flight[3];
        String arrivalCity = flight[4];
        String departureDate = flight[5];
        String arrivalDate = flight[6];
        int price = Integer.parseInt(flight[7]);
        int currentPassengers = Integer.parseInt(flight[8]);
        int seatCapacity = Integer.parseInt(flight[9]);
        Flight.FlightStatus status = flightStatusChoose(Integer.parseInt(flight[10]));

        ArrayList<Passenger> passengers = new ArrayList<>();//先初始化
        String[] Seat = FlightData.createSeat(seatCapacity);//先初始化

        Flight New = new Flight(FlightID, startTime, arrivalTime, startCity, arrivalCity, departureDate, arrivalDate,
                price, currentPassengers, seatCapacity, status, passengers, Seat);

        return New;
    }

    //Order
    //覆盖TXT的 写入order数据
    public static void coverTXT_O(String filePath, ArrayList<Order> orders) throws IOException {
        File f = new File(filePath);
        String rewrite = "";//初始化

        if (f.exists()) {
            for (Order order : orders) {
                String s = OrderToString(order);
                rewrite += s + "\n";
            }
        }else {
            f.createNewFile();//找不到就新建文件
            for (Order order : orders) {
                String s = OrderToString(order);
                rewrite += s + "\n";
            }
        }

        BufferedWriter output = new BufferedWriter(new FileWriter(f));//创建写入对象
        output.write(rewrite);//将rewrite 写入
        output.close();
    }


    //Order
    //讲一个Order转化成 一行String
    public static String OrderToString(Order order) {

//         Order(int passengerID, String seat, Flight flight, Date createDate, OrderStatus status)

        String s[] = new String[6];
        s[0] = "";
        s[1] = String.valueOf(order.getPassengerID());
        s[2] = order.getSeat();
        s[3] = FlightToString(order.getFlight());//这个得多多考虑一下
        s[4] = Date_to_String(order.getCreateDate());
        s[5] = String.valueOf(orderStatusSet(order.getStatus()));

        String S = "";//初始化S

        for (int i = 1; i <= 5; i++) {
            S += s[i] + ", "; //将每个String加起来
        }
        return S;//返回一行
    }

    //Order
    //Date to String
    public static String Date_to_String(Date date){
        String pattern = "yyyy-MM-dd HH:mm:ss";//确定格式
        SimpleDateFormat sfm = new SimpleDateFormat(pattern);

        String DATE = sfm.format(date);
        return DATE;
    }

    //Order
    //Date to String
    public static Date String_to_Date(String DATE) throws ParseException {
        String pattern = "yyyy-MM-dd HH:mm:ss";//确定格式
        SimpleDateFormat sfm = new SimpleDateFormat(pattern);

        Date date = sfm.parse(DATE);
        return date;
    }

    //Order
    //读取Order中的状态 转化成TXT
    public static int orderStatusSet(Order.OrderStatus status) {
        int a;
        if (status.equals(Order.OrderStatus.UNPAID)) {
            a = 1;
        }//unpaid 代 1
        else if (status.equals(Order.OrderStatus.PAID)) {
            a = 2;
        }//paid 代 2
        else {
            a = 0;//其他代0
        }
        return a;
    }

    //Order
    //String to status
    public static Order.OrderStatus orderStatusGet(int a) {
        Order.OrderStatus status;
        switch (a) {
            case 1:
                status = Order.OrderStatus.UNPAID;
                break;
            case 2:
                status = Order.OrderStatus.PAID;
                break;
            default:
                status = Order.OrderStatus.CANCEL;
                break;
        }
        return status;
    }




    //新建文件
    public static boolean createFile(File fileName) throws Exception {
        boolean flag = false;
        try {
            if (!fileName.exists()) {//文件不存在的话
                fileName.createNewFile();
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    //更新TXT内容
    public static void contentToTxt(String filePath, String content) throws IOException {
        String str = readTXT(filePath); //原有txt内容
        String s1 = new String();//内容更新
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));

            while ((str = input.readLine()) != null) {
                s1 += str + "\n";//新一行
            }
            System.out.println(s1);
            input.close();
            s1 += content;

            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(s1);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}


