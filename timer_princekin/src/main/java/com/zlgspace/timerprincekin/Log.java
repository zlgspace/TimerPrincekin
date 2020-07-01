package com.zlgspace.timerprincekin;

/**
 * Created by zl on 2020/6/30.
 */
class Log {

    public static void i(String tag,String msg){
        println(tag,msg,"I");
    }

    public static void d(String tag,String msg){
        println(tag,msg,"D");
    }

    public static void w(String tag,String msg){
        println(tag,msg,"W");
    }

    public static void e(String tag,String msg){
        println(tag,msg,"E");
    }

    private static void println(String tag,String msg,String level){
        String logMsg = level+"/"+tag+":"+msg;
        System.out.println(logMsg);
    }
}
