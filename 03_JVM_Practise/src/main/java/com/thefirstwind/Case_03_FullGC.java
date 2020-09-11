package com.thefirstwind;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Case_03_FullGC {

    private static class CardInfo{
        BigDecimal price = new BigDecimal(0.0);
        String name = "bob";
        int age = 5;
        Date birthdate = new Date();

        public void m(){

        }

        private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(50,
                new ThreadPoolExecutor.DiscardOldestPolicy());

        /**
         *  执行的时候添加 jvm参数
         *  java -Xms200M -Xmx200M -XX:+PrintGC com.thefirstwind.Case_03_FullGC
         * @param args
         * @throws Exception
         */
        public static void main(String[] args) throws Exception{
            executor.setMaximumPoolSize(50);

            for(;;){
                modelFit();
                Thread.sleep(100);
            }
        }

        private static void modelFit(){

            List<CardInfo> taskList = getAllCardInfo();
            taskList.forEach(info->{
                executor.scheduleWithFixedDelay(()->{
                    info.m();
                }, 2, 3, TimeUnit.SECONDS);
            });

        }

        private static List<CardInfo> getAllCardInfo(){
            List<CardInfo> taskList = new ArrayList<>();

            for(int i=0; i< 100; i++){
                CardInfo ci = new CardInfo();
                taskList.add(ci);
            }
            return taskList;
        }
    }
}
