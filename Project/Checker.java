package Project;

/**
 * Created by S.D.Z on 2017/5/16 0016.
 */


public class Checker {
    private boolean checker;//乘客登入状态， 登入则为true
    private int p;//记录乘客ID，用于显示欢迎界面和订单信息


    public Checker(boolean checker, int p){
        this.checker = checker;
        this.p = p;
    }

    public boolean isChecker() {
        return checker;
    }

    public void setChecker(boolean checker) {
        this.checker = checker;
    }

    public int getP() {
        return p;
    }

    public void setP(int p) {
        this.p = p;
    }
}
