/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package réseaunnés;

import java.net.UnknownHostException;

/**
 *
 * @author colin
 */
public class Reseaunnes {

    public static void main(String[] args) throws UnknownHostException {
        /* TODO code application logic here */
/* 
        String url = "www.geeksforgeeks.org";
        String url2 = "173.16.2.162";
        Inet4Address ip1 = (Inet4Address) Inet4Address.getByName(url2);
        Inet4Address ip2 = (Inet4Address) InetAddress.getByName("www.yahoo.com");
         
        // equals() method
        System.out.println("ip1==ip2 : " + ip1.equals(ip2));

        // getHostAddress() method
        System.out.println("Host Address : " + ip1.getHostAddress()); */

        IP ip1 = new IP();
        IP ip2 = new IP();

        System.out.println(ip1.toString());
        System.out.println(ip2.toString());

        System.out.println("ip1 == ip2 : " + ip1.equals(ip2));

        MAC mac1 = new MAC();

        System.out.println(mac1.toString());

    }
    
}
