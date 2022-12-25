package nn.radio;

import nn.radio.dto.TankDto;
import nn.radio.dto.UserDto;
import nn.radio.server.ServerThread;
import nn.radio.server.connection.EventClientConnection;
import nn.radio.server.connection.TankClientConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TankiApplication {
    public static void main(String[] args) {

        List<TankClientConnection> tankClientConnectionList = new ArrayList<>();
        List<EventClientConnection> eventClientConnectionList = new ArrayList<>();
        Map<String, TankDto> tankMap = new HashMap<>();
        Map<String, UserDto> userMap = new HashMap<>();
        tankMap.put("U1", createTank( "U1","Andy","1T",200F, 100F));
        //tankMap.put("U2", createTank( "U2","Kirry","2T",200F, 200F));

        tankClientConnectionList.add(new TankClientConnection());
        eventClientConnectionList.add(new EventClientConnection());

        ServerThread tankThread = new ServerThread();
        tankThread.updateTankMap(tankMap);
        tankThread.updateClientList(tankClientConnectionList);
        tankThread.updateEventClientList(eventClientConnectionList);

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
