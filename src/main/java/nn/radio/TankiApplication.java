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

        ConfigReader configReader = new ConfigReader();
        int I = configReader.getConfigSize();

        for (int i = 0; i < I; i++) {
            tankMap.put(configReader.tankValues(i,0),
                            createTank( configReader.tankValues(i,1),
                                configReader.tankValues(i,2),
                                configReader.tankValues(i,3),
                                Float.parseFloat(configReader.tankValues(i,4).trim()),
                                Float.parseFloat(configReader.tankValues(i,5).trim())));

        }

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
