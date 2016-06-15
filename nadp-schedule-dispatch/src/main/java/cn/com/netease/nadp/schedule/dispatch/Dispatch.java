package cn.com.netease.nadp.schedule.dispatch;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by bianlanzhou on 16/6/8.
 * Description
 */
public class Dispatch {
    public void run(){
        System.out.println("task is start");
    }
    public static void main(String args[]){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
    }
}
