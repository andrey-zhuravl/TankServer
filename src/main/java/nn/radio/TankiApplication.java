package nn.radio;

import nn.radio.dto.TankDto;
import nn.radio.dto.UserDto;
import nn.radio.server.ServerThread;
import nn.radio.server.connection.ClientListenerThread;

import java.util.HashMap;
import java.util.Map;


public class TankiApplication {

    static TankiProperty tankiProperty = new TankiProperty();
    public static void main(String[] args) {

        tankiProperty.readProperies();
        Map<String, TankDto> tankMap = new HashMap<>();
        Map<String, UserDto> userMap = new HashMap<>();
        tankMap.put("1T", createTank( "111-111-111","Andy","1T",200F, 100F));
        tankMap.put("2T", createTank( "111-111-111","Andy","2T",200F, 200F));
        tankMap.put("3T", createTank( "111-111-222","Kirry","3T",200F, 300F));
        tankMap.put("4T", createTank( "111-111-222","Kirry","4T",200F, 400F));



        ServerThread tankThread = new ServerThread();
        ClientListenerThread clientListenerThread = new ClientListenerThread(tankiProperty, tankThread, tankThread);

        tankThread.updateTankMap(tankMap);
        tankThread.updateFullClientList(clientListenerThread.fullClientConnectionList);

        clientListenerThread.start();
        tankThread.start();
        System.out.println("TankiApplication ServerThread start");
    }

    public static TankDto createTank(String userId,String name, String tankId, float x, float y){
        TankDto clientTank = new TankDto(tankId, createUser(userId, name),x, y);
        return clientTank;
    }

    public static UserDto createUser(String userId, String name){
        UserDto clientUser = new UserDto(userId, name);
        return clientUser;
    }
}
