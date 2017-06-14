package Project;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

import static Project.Main.flights;

/**
 * Created by S.D.Z on 2017/5/3 0003.
 */
public class Admin {
    private String userName;
    private String password;

    public Admin(String userName, String password){
        this.userName = userName;
        this.password = password;
    }


    //注册管理员
    public static ArrayList<Admin> signUp(ArrayList<Admin> AdminList){
        Scanner in = new Scanner(System.in);

        System.out.println("Please input the UserName: ");
        String userName = in.nextLine();

        System.out.println("Please input the password: ");
        String password = in.nextLine();
        Admin New = new Admin(userName, password);//新建一个
        AdminList.add(New);//添加
        System.out.printf("You hava add a new Admin %s.\n", userName);

        return AdminList;
    }

    //登入管理员
    public static void logIn(ArrayList<Admin> AdminList,  ArrayList<Flight> flights) throws ParseException, IOException {
        if (Admin.Checker(AdminList))//登入
            Main.AdminFunction(AdminList, flights);
    }


    //验证登入
    public static boolean Checker(ArrayList<Admin> AdminList){
        Scanner in = new Scanner(System.in);

        System.out.println("Please input the UserName: ");
        String userName = in.next();
        String password = null;

        for(Admin e: AdminList){

            if(e.getUserName().equals(userName)){
                System.out.println("Please input the password: ");
                password = in.next();


                if(e.getPassword().equals( password)){
                    System.out.println("Success !");
                    return true;
                }
                else {
                    System.out.println("Uncorrect password!");
                    return  false;
                }
            }
            else{
                System.out.println("Not find the user name!");
                return false;
            }
        }return false;
    }

    //用户管理
    public static void userManagement(ArrayList<Admin> AdminList) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Please input the number: ");

        System.out.printf(
                        "【1】 Create new Admin\n" +
                        "【2】 Update information\n" +
                        "【3】 Quit\n");
        int d = Main.inputDecide(in.nextLine());//判断输入的是否为数字


        if (d > 0 && d <= 3 ) {
            switch (d){
                case 1 :
                    Admin.signUp(AdminList);
                    Admin.userManagement(AdminList);
                case 2 :
                    Admin.updateAdmin(AdminList);
                    Admin.userManagement(AdminList);
                case 3 :
                    Main.AdminFunction(AdminList, flights);
                default:
                    System.out.println("Please enter the correct number!");
                    Admin.userManagement(AdminList);
            }
        }
        else {
            System.out.println("Please make sure your input is correct number!");
            userManagement(AdminList);
        }



    }

    //查询所有管理员
    public static void queryAdmin(ArrayList<Admin> AdminList){
        System.out.printf(
                    "【N】\tUserName\tPassword\n" );
        for(Admin e : AdminList) {
            System.out.printf(
                    "【%d】\t%10s\t\t%10s\n",
                    AdminList.indexOf(e) + 1, e.getUserName(), e.getPassword());
        }
    }

    //跟新管理员信息
    public static void updateAdmin(ArrayList<Admin> AdminList) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);

        queryAdmin(AdminList);
        System.out.println("Choose the one you want to change :  (Input 0 to quite)");
        int a = Main.inputDecide(in.nextLine());//判断输入的是否为数字

        if(a > 0 && a <= AdminList.size()) {
            Admin changed = AdminList.get(a - 1);
            C_or_Q_U(AdminList, changed, a);
        }
        else if(a == 0){
            Admin.userManagement(AdminList);
        }
        else{
            System.out.println("Please enter the correct number!");
            updateAdmin(AdminList);
        }

    }

    //Continued or quite 用于上个方法
    public static void C_or_Q_U(ArrayList<Admin> AdminList, Admin changed, int a) throws ParseException, IOException {
        Scanner in = new Scanner(System.in);

        System.out.printf("Do you want to change\n" +
                "【1】Password\n" +
                "【2】UserName\n" +
                "【3】Delete\n" +
                "【4】Quit\n");

        int d1 = Main.inputDecide(in.nextLine());//判断输入的是否为数字

        if(d1 > 0 && d1 <=4 ){
            switch (d1) {
                case 1:
//                    in.nextLine();//for scanner bug
                    System.out.println("New password :");
                    String newP = in.nextLine();
                    changed.setPassword(newP);//
                    AdminList.set(a - 1, changed);//替换
                    break;
                case 2:
//                    in.nextLine();//for scanner bug
                    System.out.println("New userName :");
                    String newU = in.nextLine();
                    changed.setUserName(newU);
                    AdminList.set(a - 1, changed);
                    break;
                case 3 :
                    //删除
                    if(AdminList.size() > 1) {
                        for (int n = 0, len = AdminList.size(); n < len; ++n) {
                            if (AdminList.get(n).equals(changed)) {
                                AdminList.remove(n);
                                --len;//减少一个
                                --n;
                                System.out.printf("Delete success!\n");
                            }
                        }
                    }
                    else
                        System.out.println("Can't delete this Admin account, for this is the last one!!!");
                    break;
                case 4 :
                    Admin.userManagement(AdminList);
                    break;
            }
        }
        else {
            System.out.println("Please make sure your input is correct number!");
            C_or_Q_U(AdminList, changed, a);
        }
    }





    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}