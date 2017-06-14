package Project;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Project.Main.AdminList;
import static Project.Main.PList;
import static Project.Main.checker;

/**
 * Created by S.D.Z on 2017/5/3 0003.
 */
public class Flight {
    private String FlightID;
    private String startTime;
    private String arrivalTime;
    private String startCity;
    private String arrivalCity;
    private String departureDate;
    private String arrivalDate;
    private int price;
    private int currentPassengers;
    private int seatCapacity;
    private ArrayList<Passenger> passengers;
    private FlightStatus flightStatus;
    private String Seat[];

    public enum FlightStatus {UNPUBLISHED, AVAILABLE, FULL, TERMINATE}

    Flight(String FlightID, String startTime, String arrivalTime, String startCity, String arrivalCity, String departureDate, String arrivalDate,
           int price, int currentPassengers, int seatCapacity, FlightStatus flightStatus, ArrayList<Passenger> passengers, String Seat[]) {
        this.FlightID = FlightID;
        this.startTime = startTime;
        this.arrivalTime = arrivalTime;
        this.startCity = startCity;
        this.arrivalCity = arrivalCity;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
        this.currentPassengers = currentPassengers;
        this.seatCapacity = seatCapacity;
        this.passengers = passengers;
        this.flightStatus = flightStatus;
        this.Seat = Seat;
    }

    public String[] getSeat() {
        return Seat;
    }

    public void setSeat(String[] seat) {
        Seat = seat;
    }

    public String getFlightID() {
        return FlightID;
    }

    public void setFlightID(String flightID) {
        FlightID = flightID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCurrentPassengers() {
        return currentPassengers;
    }

    public void setCurrentPassengers(int currentPassengers) {
        this.currentPassengers = currentPassengers;
    }

    public int getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(int seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;
    }
}

//public static class Seat{
//    private String s;
//    private int n;
//
//    Seat(String s, int n){
//        this.s = s;
//        this.n = n;
//    }
//
//    public String getS() {
//        return s;
//    }
//
//    public void setS(String s) {
//        this.s = s;
//    }
//
//    public int getN() {
//        return n;
//    }
//
//    public void setN(int n) {
//        this.n = n;
//    }
//
//    public static initialSeat(){
//
//    }
//
//    public static Seat chooseSeat(){
//        System.out.println("");
//
//    }
////}





