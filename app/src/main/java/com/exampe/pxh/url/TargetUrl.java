package com.exampe.pxh.url;

public class TargetUrl {
	public final static String url1 = "http://api.map.baidu.com/telematics/v3/weather?location=";
	public final static String url2 = "&output=json&ak=9cCAXQFB468dsH11GOWL8Lx4";
    /*
    * 下面是JSP后台，是通过post方法进行传值的
    * */
//	public final static String loginer = "http://192.168.31.216:8080/weather/android/sigin.jsp";
//	public final static String registered = "http://192.168.31.216:8080/weather/android/registered.jsp";

    /*
    * 下面是PHP后台的路径，是通过post方法进行传值的
    * */
    public final static  String loginer = "http://192.168.31.216/WeatherServer/signin.php";
    public final static String registered = "http://192.168.31.216/WeatherServer/register.php";
}
