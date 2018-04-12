package multithreading;

import java.math.BigDecimal;

/**
 * 关于取钱的线程(加上同步代码块锁)
 *
 * @author cheng.jin
 * @since 2018年04月01日
 */
public class DrawCashThreadSynchronizedBlock extends Thread {

    private Account account;

    private BigDecimal drawCash;

    public DrawCashThreadSynchronizedBlock(String name,Account account,BigDecimal drawCash){
        super(name);
        this.account = account;
        this.drawCash = drawCash;
    }

    @Override
    public void run() {
        //通常选取共享资源来做同步监视器,所以选取acccount
        //todo 共享资源必须是同一个对象，不能出现地址不同的对象
        synchronized(account) {
            if(account.getBalance().compareTo(drawCash) >= 0) {
                System.out.println(getName() + "取钱成功，取款金额" + drawCash);
                //强制切换线程
                /*try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                //修改余额
                account.setBalance(account.getBalance().subtract(drawCash));
                System.out.println(getName() + "取钱成功，剩余金额" + account.getBalance());
            } else {
                System.out.println(getName() + "取钱失败，余额不足！");
            }
        }
    }
    //模拟多个线程进行取钱操作
    public static void main(String[] args) {
        Account account = new Account("张三",new BigDecimal(1000));
        DrawCashThreadSynchronizedBlock drawCashThread = new DrawCashThreadSynchronizedBlock("王二",account,new BigDecimal(1000));
        drawCashThread.start();
        //todo join方法把多线实现同步，即串行执行(等待当前线程终止，然后执行下一个线程)
        /*try {
            drawCashThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        new DrawCashThreadSynchronizedBlock("麻子",account,new BigDecimal(1000)).start();
    }
}


