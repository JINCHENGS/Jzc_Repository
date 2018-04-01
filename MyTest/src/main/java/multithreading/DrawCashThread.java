package multithreading;

import java.math.BigDecimal;

/**
 * 关于取钱的线程
 *
 * @author cheng.jin
 * @since 2018年04月01日
 */
public class DrawCashThread extends Thread {

    private Account account;

    private BigDecimal drawCash;

    public DrawCashThread(String name,Account account,BigDecimal drawCash){
        super(name);
        this.account = account;
        this.drawCash = drawCash;
    }

    @Override
    public void run() {
        if(account.getBalance().compareTo(drawCash) >= 0) {
            System.out.println(getName()+"取钱成功，取款金额"+drawCash);
                //强制切换线程
                /*try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //修改余额
                account.setBalance(account.getBalance().subtract(drawCash));
            System.out.println(getName()+"取钱成功，剩余金额"+account.getBalance());
        }else {
            System.out.println(getName()+"取钱失败，余额不足！");
        }
    }
    //模拟多个线程进行取钱操作
    public static void main(String[] args) {
        Account account = new Account("张三",new BigDecimal(1000));
        DrawCashThread drawCashThread = new DrawCashThread("王二",account,new BigDecimal(1000));
        drawCashThread.start();
        //todo join方法把多线实现同步，即串行执行(等待当前线程终止，然后执行下一个线程)
        /*try {
            drawCashThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        new DrawCashThread("麻子",account,new BigDecimal(1000)).start();
    }
}

class Account {

    private String acountNo;

    private BigDecimal balance;

    public Account(String acountNo, BigDecimal balance) {
        this.acountNo = acountNo;
        this.balance = balance;
    }

    public String getAcountNo() {
        return acountNo;
    }

    public void setAcountNo(String acountNo) {
        this.acountNo = acountNo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
