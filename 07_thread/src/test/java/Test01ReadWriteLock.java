import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test01ReadWriteLock {

    private static int value = 1;
    static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();
    static Lock writeLock = readWriteLock.writeLock();

    public static void read(Lock lock){
        try{
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read " + value);
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public static void write(Lock lock, int v){
        try{
            lock.lock();
            Thread.sleep(500);
            value = v;
            System.out.println("write " + value);
        }catch(InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args){

        // 调用static方法，没有办法new出来类，用runable
        Runnable r = ()->read(readLock);
        Runnable w = ()->write(writeLock, new Random().nextInt());

        for(int i = 0 ; i < 2; i++) new Thread(w).start() ;
        for(int i = 0 ; i < 18; i++) new Thread(r).start();

    }

}
