package multithreading;

import java.math.BigDecimal;

/**
 * 关于取钱的线程(加上同步方法锁)
 *
 * @author cheng.jin
 * @since 2018年04月01日
 */
public class DrawCashThreadSynchronizedMethod extends Thread {

    private final static BigDecimal drawCash = new BigDecimal(1000);

    public DrawCashThreadSynchronizedMethod(Runnable runnable) {
        super(runnable);
    }


    //模拟多个线程进行取钱操作
    public static void main(String[] args) {
        final Account account = new Account("张三", new BigDecimal(1000));
        new DrawCashThreadSynchronizedMethod(new Runnable() {

            public void run() {
                account.drawCash(drawCash);
            }
        }).start();

        new DrawCashThreadSynchronizedMethod(new Runnable() {

            public void run() {
                account.drawCash(drawCash);
            }
        }).start();


    }

    static class Account {
        private String accountNo;

        private BigDecimal balance;

        public Account(String accountNo, BigDecimal balance) {
            this.balance = balance;
            this.accountNo = accountNo;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        //因为账户余额不能随便修改，所以取消这个方法
        /*public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }*/

        public synchronized void drawCash(BigDecimal drawCash) {//对于同步方法而言，同步监视器就是this,及对象本身，所以同步方法应该在共享资源对象里创建
            if(balance.compareTo(drawCash) >= 0) {
                System.out.println(Thread.currentThread().getName() + "取钱成功，取款金额" + drawCash);
                //强制切换线程
                /*try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //修改余额
                balance = balance.subtract(drawCash);
                System.out.println(Thread.currentThread().getName() + "取钱成功，剩余金额" + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + "取钱失败，余额不足！");
            }
        }
    }
}


