/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package General;
import Outils.*;
/**
 *
 * @author victo
 */
public class app {
    public static void main(String[] args) {

        IPv4 ip3 = new IPv4();

        MAC mac1 = new MAC();

        CarteReseau carte1 = new CarteReseau();
        //carte1.setAdresseMAC(mac1);

        Machine machine1 = new Machine();

        System.out.println(machine1.affichesCartesR());
        //System.out.println(machine1.toString());
    }
}
