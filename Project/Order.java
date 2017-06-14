package Project;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static Project.Main.AdminList;
import static Project.Main.flights;


/**
 * Created by S.D.Z on 2017/5/3 0003.
 */
public class Order {
    public enum  OrderStatus {UNPAID, PAID, CANCEL};
    private int passengerID;
    private String seat;
    private Flight flight;
    private Date createDate;
    private OrderStatus status;

    Order(int passengerID, String seat, Flight flight, Date createDate, OrderStatus status){
        this.passengerID = passengerID;
        this.seat = seat;
        this.flight = flight;
        this.createDate = createDate;
        this.status = status;
    }






    /**
     * 还未调试好  注册登入那一块
     * @param PList
     * @param flights
     * @param checker
     * @return
     */
    //取消Unsubsribe Flight
    public static void unsubsribeFlight( ArrayList<Passenger> PList,  ArrayList<Flight> flights, Checker checker) throws IOException {
        cancelOrder(PList, flights, checker);

    }


    //删去..,非取消。。已改为取消
    public static void cancelOrder(ArrayList<Passenger> PList, ArrayList<Flight> flights, Checker checker) throws IOException {
        Scanner in = new Scanner(System.in);

        if(checker.isChecker()) {
            Passenger the = PList.get(checker.getP()-1);//获取乘客
            int p = the.getPassengerID();
            ArrayList<Order> OrderList = the.getOrderList();
            Order.unsubscribeOrderDisply(PList, checker);//列出乘客订单 未取消的
            System.out.println("Choose one you want to unsubsribe : 【Input 0 to quite！】");
            int i = Main.inputDecide(in.nextLine());//判断输入的是否为数字

            //如果i为非取消状态
            if (i > 0 &&  ! OrderList.get(i - 1).getStatus().equals(OrderStatus.CANCEL)) {

                Order o = OrderList.get(i - 1);//取得所需订单
                o.setStatus(OrderStatus.CANCEL);//将订单状态跟改为CANCEL
                OrderList.set(i - 1, o);//替换订单
                Flight flight = o.getFlight();//得到航班、这里的航班只是当时存的、并未及时更新
                String flightID = flight.getFlightID();//得到航班ID
                int index = 0;
                for(Flight f: flights){
                    if(f.getFlightID().equals(flightID)){//当两ID相同时
                        flight = f;//取出大的flights、即更新到最新航班
                        index = flights.indexOf(f);
                    }
                }
                flight.setCurrentPassengers(flight.getCurrentPassengers()-1);//航班乘客量减一
                ArrayList<Passenger> changed_p = flight.getPassengers();
                //将乘客从乘客列表删除
                for (int n = 0, len = changed_p.size(); n < len; ++n) {
                    if (changed_p.get(n).equals(the)) {
                        changed_p.remove(n);
                        --len;//减少一个
                        --n;
                    }
                }
                flight.setPassengers(changed_p);//替换乘客
                flights.set(index, flight);//替换航班

                TXT.coverTXT_F("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);//重写TXT
                TXT.createTXT_P(flights);//重写flights里的PList

                System.out.printf("Unsubsribe success!\n" +
                        "Payment had return to your account!\n\n");//输出以还款

                the.setOrderList(OrderList);//替换订单列表
                PList.set(p - 1, the);//替换乘客

                TXT.createTXT_O(PList, checker);//写入TXT

            }else if(i == 0){
                    //空
            }else {
                System.out.println("Please enter the correct number!\n");
                cancelOrder(PList, flights, checker);
            }

        }
        else {
            Passenger.logIn(PList, checker);
            cancelOrder(PList, flights, checker);
        }
    }

    //预订
    public static void reserveFlight( ArrayList<Passenger> PList,  ArrayList<Flight> flights, Checker checker) throws IOException, ParseException {
        addOrder(PList, flights, checker);
        reserveLoop(PList, flights, checker);
    }

    //用于上面循环
    public static void reserveLoop(ArrayList<Passenger> PList,  ArrayList<Flight> flights, Checker checker) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);

        System.out.printf("What do you want to do next?  \n");
        System.out.printf(
                "【1】 Pay for my orders.\n" + //开启支付
                        "【2】 Reserve another flight.\n" +
                        "【3】 Query my order.\n" +
                        "【4】 Unsubsribe Flight.\n" +
                        "【5】 Quite.\n");

        int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字


        if (d > 0 && d <= 5) {

            switch (d) {
                case 1:
                    pay(PList, flights, checker);
                    break;
                case 2:
                    reserveFlight(PList, flights, checker);
                    break;
                case 3:
                    orderQuery(PList, flights, checker);
                    break;
                case 4:
                    unsubsribeFlight(PList, flights, checker);
                    break;
                case 5:
                    Main.first(AdminList, flights, PList, checker);
                    break;
            }
        } else {
            System.out.println("Please input correct number !");
            reserveLoop(PList, flights, checker);
        }
    }

    //支付订单
    public static void pay( ArrayList<Passenger> PList,  ArrayList<Flight> flights, Checker checker) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);

        Passenger the = PList.get(checker.getP() - 1);//获取乘客
        ArrayList<Order> OrderList = the.getOrderList();//获取订单列表
        boolean b = false;//通过b判断是否有账单未支付

        //显示未支付的order
        titleOrderDisply();
        for (Order e : OrderList) {
            if (e.getStatus().equals(OrderStatus.UNPAID)) {//如果订单状态为未支付
                orderDisplyFormat(e, OrderList);//显示满足条件的订单

                b = true;//有账单未支付，跟改状态为true
            }
        }

        //如果有未支付订单
        if (b) {
            System.out.println("Input the number you want to pay : ");

            int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字

            if (d > 0 && OrderList.get(d - 1).getStatus().equals(OrderStatus.UNPAID) ) {//如果订单未支付
                YNLoop(d, OrderList, the, PList, checker);//输入Y、N 确定是否支付
            }
            else {
                System.out.println("Please enter the correct number!\n");
                pay(PList, flights, checker);
            }
        } else {
            System.out.println("You have no unpaid order!");
        }


    }


    //input Y or N
    public static void YNLoop( int d, ArrayList<Order> OrderList, Passenger the, ArrayList<Passenger> PList, Checker checker) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);

        Order order = OrderList.get(d - 1);
        System.out.printf("Are you sure to pay the following order : 【Y/N】\n");
        titleOrderDisply();

        orderDisplyFormat(order, OrderList);

        String s = in.nextLine();//输入Y/N
        if (s.equals("Y") || s.equals("y") || s.equals("Yes") || s.equals("yes")) {
            order.setStatus(OrderStatus.PAID);//更改订单状态为已支付
            OrderList.set(d - 1, order);//将订单替换到原有订单
            the.setOrderList(OrderList);
            PList.set(checker.getP() - 1, the);

            TXT.createTXT_O(PList, checker);//写入TXT

            System.out.println("Pay success!");
            reserveLoop(PList, flights, checker);
        }
        else {
            System.out.println("Please input Y or N !");
            YNLoop(d, OrderList, the, PList, checker);//输入Y、N 确定是否支付

        }
    }


    //显示座位
    public static void showSeats(String Seat[]) {
        for(int i = 0; i < Seat.length/5; i++){
            for(int j = 0; j < 5 && i + j < Seat.length -2; j++){
                System.out.print(Seat[i*5 + j]+"\t");
            }
            System.out.println();
        }
    }

    //预订座位
    public static String reserveSeat(String Seat[]){
        Scanner in = new Scanner(System.in);

        showSeats(Seat);//显示座位
        System.out.println("Choose one :");
        String seat =in.next();
        boolean flag = false;
        for(int i = 0; i < Seat.length; i++){
            if (Seat[i].equals(seat)) {
                Seat[i] = "";//满足条件设为空
                flag = true;
                break;
            }
            else {
                //空
            }
        }
        if(!flag){
            System.out.println("Please input the correct Seat!");
            reserveSeat(Seat);//回到开头
        }
        return seat;
    }




    //添加Order
    public static void addOrder(ArrayList<Passenger> PList, ArrayList<Flight> flights, Checker checker) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);

        if(checker.isChecker()) {
            Passenger the = PList.get(checker.getP()-1);//获取乘客
            int p = the.getPassengerID();
            ArrayList<Order> OrderList = the.getOrderList();
            int n = FlightData.orderDisplyFlight(flights);//列出所有可预定航班//和数量
            if(n > 0){
                //选择要预定的航班
                System.out.println("Choose one you want to reserve : 【Input 0 to quit.】");

                int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字

                //判断输入的航班是否有空位(乘客容量-当前乘客数量)
                if (d > 0 && flights.get(d - 1).getSeatCapacity() - flights.get(d - 1).getCurrentPassengers() > 0 &&
                        flights.get(d - 1).getFlightStatus().equals(Flight.FlightStatus.AVAILABLE)) {//判断航班是否可预订

                    Flight f = flights.get(d - 1);//取出指定航班

                    Order newO = createOrder(PList, p, f);//生成订单信息

                    //将订单舔至乘客信息里
                    OrderList.add(newO);
                    the.setOrderList(OrderList);
                    PList.add(the);

                    TXT.createTXT_O(PList, checker);//写入TXT

                    ArrayList<Passenger> passengers = f.getPassengers();//取出航班乘客
                    passengers.add(the);//加入乘客
                    f.setPassengers(passengers);//改变航班乘客列表
                    f.setCurrentPassengers(f.getCurrentPassengers() + 1);//航班乘客数量加1
                    flights.set(d - 1, f);//替换航班

                    TXT.coverTXT_F("D:\\JAVA\\workspace\\Final\\src\\Project\\Flight.txt", flights);//重写TXT
                    TXT.createTXT_P(flights);//重写flights里的PList

                    System.out.printf("You have ordered the Fight %s, seat %s.\n", f.getFlightID(), newO.getSeat());

                    reserveLoop(PList, flights, checker);//预订成功后直接跳转到是否支付菜单
                }else if (d == 0){
                    Main.first(AdminList, flights, PList, checker);
                }
                else {

                    System.out.println("Please enter the correct number!\n");
                    addOrder(PList, flights, checker);
                }
            }
            else{
                System.out.println("Sorry, there is no flight can be ordered!");
            }
        }
        else {
            Passenger.logInForReserve(PList, flights, checker);//没登入则先登入
            addOrder(PList, flights, checker);//重复此方法、、
        }

    }

    //生成order
    public static Order createOrder(ArrayList<Passenger> PList, int p, Flight f){
        Scanner in = new Scanner(System.in);

//        Order(int passengerID, String seat, Flight flight, Date createDate, OrderStatus status){

        System.out.printf("The numbers of remain seats is %d.\n", f.getSeatCapacity()-f.getCurrentPassengers());//显示剩余座位
        String []S =f.getSeat();
        String s = reserveSeat(S);//输入想要的座位编号
        f.setSeat(S);

        //定义订单的属性        （乘客ID、                 座位号、航班、现在的时间、 状态为未支付）
        Order order = new Order(PList.get(p-1).getPassengerID(), s, f, new Date(), Order.OrderStatus.UNPAID);

        return order;//返回生成的订单
    }



    /**
     * 未完成
     * @param PList
     * @param flights
     */
    //Order 查询for Passenger
    public static void orderQuery(ArrayList<Passenger> PList, ArrayList<Flight> flights, Checker checker) throws IOException, ParseException {
        Scanner in = new Scanner(System.in);

        if (!checker.isChecker()){//如果没登入的话
            System.out.println("Please log in first: ");
            Passenger.LoginChecker(PList, checker);
            orderQuery(PList, flights, checker);
        }
        else {
            Order.orderDisply(PList, checker);
            System.out.printf("What do you want to do next?  \n");
            System.out.printf(
                    "【1】 Pay my order.\n" + //开启支付
                            "【2】 Unsubsribe Flight.\n" +
                            "【3】 Quite.\n");
            int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字
            switch (d){
                case 1: pay(PList, flights, checker); break;
                case 2: unsubsribeFlight(PList, flights, checker); break;
                case 3: break;
                default:
                    System.out.println("Please enter the correct number!\n");
                    orderQuery(PList, flights, checker);
            }
        }
    }

    //特定 乘客的Order显示（乘客+登入状态） for passenger
    public static void orderDisply(ArrayList<Passenger> PList, Checker checker){
        Passenger the = PList.get(checker.getP()-1);//获取乘客

        titleOrderDisply();

        for(Order e : the.getOrderList()) {
            orderDisplyFormat(e, the.getOrderList());//显示所有订单
        }
    }


    //特定 乘客的Order显示 为取消订单
    public static void unsubscribeOrderDisply(ArrayList<Passenger> PList, Checker checker){
        Passenger the = PList.get(checker.getP()-1);//获取乘客

        titleOrderDisply();

        for(Order e : the.getOrderList()) {
            if (!e.getStatus().equals(OrderStatus.CANCEL)) {//所有的取消订单
                orderDisplyFormat(e, the.getOrderList());

            }
        }
    }


    //所有Order显示 for Admin
    public static void orderDisply(ArrayList<Passenger> PList){
        titleOrderDisply();
        for(Passenger P : PList){//取出每一个乘客
            for(Order e : P.getOrderList()) {//取出每一个乘客的订单
                orderDisplyFormat(e, P.getOrderList());//显示订单
            }
        }
    }


    //
    public static void titleOrderDisply(){
        System.out.printf(
                "【N】\tPassengerID\tSeat\tFlight ID\t\tPrice\t\tCreate Date\t\t\t\tStatus\n" );
    }

    //显示订单   格式化时间输出年月日
    public static void orderDisplyFormat(Order e, ArrayList<Order> OrderList){
        String pattern = "yyyy-MM-dd HH:mm:ss";//时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(e.getCreateDate());//格式化时间输出年月日

        System.out.printf(
                "【%d】\t%d\t\t\t%s\t\t%s\t\t\t%s\t\t%s\t%10s\n",
                OrderList.indexOf(e) + 1, e.getPassengerID(), e.getSeat(), e.getFlight().getFlightID(), e.getFlight().getPrice(), date, e.getStatus());

    }


    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
