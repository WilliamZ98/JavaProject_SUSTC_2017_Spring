package Project;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by S.D.Z on 2017/5/12 0012.
 */
public class Test {
    public static void main(String[] args) throws ParseException, IOException {
        //初始化order
        ArrayList<Order> OrderList = new ArrayList<>();

        //初始化passenger
        ArrayList<Passenger> PList = new ArrayList<>();
        Passenger pa = new Passenger(1, "123", "123", "123", OrderList );
        PList.add(pa);


        String[] Seat = FlightData.createSeat(199);
        Order.showSeats(Seat);

        //初始化fights
        ArrayList<Flight> flights = new ArrayList<>();
        FlightData.initializeFlightData(flights);
        ArrayList<Passenger> passengers = new ArrayList<>();//先用于测试
        Flight f1 = new Flight("CZ1000", "11:10:10", "15:45:00",
                "ShenZhen", "Beijing", "2017-5-15", "2017-5-15",
                1000, 0, 250, Flight.FlightStatus.AVAILABLE, passengers, Seat);
        flights.add(f1);

        Checker checker = new Checker(true, 1);

        //初始化order
        Order order = new Order(1, "12", f1, new Date(), Order.OrderStatus.PAID);
        OrderList.add(order);




//        TXT.coverTXT_O("D:\\JAVA\\workspace\\Final\\src\\Project\\OrderList.txt", OrderList);

        TXT.turnTXTtoOrderList("D:\\JAVA\\workspace\\Final\\src\\Project\\OrderList.txt", OrderList);

//        Order.orderDisply(PList);

        pa.setOrderList(OrderList);//替换


        Order.orderQuery(PList, flights, checker);


        //初始化 登入乘客的ID
//        int p = 0;
//
//        ArrayList<Admin> AdminList = new ArrayList<>();
//        Admin initial = new Admin("Admin", "123456");
//
//        AdminList.add(initial);
//
//        Admin.C_or_Q_U(AdminList, initial, 1);

//        Checker checker = new Checker(false, 0);


//        Flight.FlightData.orderDisplyFlight(flights);

//        Order.reserveFlight(PList, flights, checker);//pay的问题多多
//        Order.orderQuery(PList, flights, checker);
//        PList = Order.unsubsribeFlight(PList, flights, checker);
//        Order.orderQuery(PList, flights, checker);

//        Flight f = new Flight("CZ1000", "2:48:00", "15:45:00",
//                "ShenZhen", "Beijing", "2017-5-19", "2017-5-19",
//                1000, 0, 250, Flight.FlightStatus.AVAILABLE, passengers);
//
//        System.out.printf(
//                "【N】\tFlightID\tStartTime\tArrivalTime\t\tStartCity\tArrivalCity\t\tDepartureDate\tPrice\tCurrentPassengers\tSeatCapacity\tFlightStatus\n" );
//        System.out.printf(
//                "【%d】\t%8s\t%8s\t%10s\t%12s\t%10s\t\t%10s\t%8d\t%8d\t\t\t%8d\t\t%8s\t\n",
//                flights.indexOf(f) + 1, f.getFlightID(), f.getStartTime(), f.getArrivalTime(), f.getStartCity(), f.getArrivalCity(),
//                f.getDepartureDate(), f.getPrice(), f.getCurrentPassengers(), f.getSeatCapacity(), f.getFlightStatus());


//        Flight.FlightData.oneFlight(flights, PList);

//        Order.unsubsribeFlight(PList, flights, checker);// 还未

//        Order.reserveFlight(PList, flights, Main.checker);



//        //初始化fights
//        ArrayList<Flight> flights = new ArrayList<>();
//        FlightData.initializeFlightData(flights);
//
//        //初始化Admin
//        ArrayList<Admin> AdminList = new ArrayList<>();
//        Admin initial = new Admin("Admin", "123456");
//        AdminList.add(initial);
//
//        FlightData.deleteFlight(AdminList, flights);




//        ArrayList<Admin> AdminList = new ArrayList<>();
//        Admin initial = new Admin("Admin", "123456");
//        AdminList.add(initial);
//
//        Admin.userManagement(AdminList);




//        Scanner in = new Scanner(System.in);
//        boolean d = true;
//        while(d){System.out.println("Please input the number: ");
//        System.out.printf(
//                "【1】 Login for Admin\n" +
//                        "【2】 Query Flight Information\n" +
//                        "【3】 Query Order List\n" +
//                        "【4】 Reserve Flight\n" +
//                        "【5】 Unsubsribe Flight\n");
//        switch (in.nextInt()){
//            case 1 : d = false;
//            case 2 : ;
//        }}
    }
}