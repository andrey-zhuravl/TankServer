package nn.radio.server;

import nn.radio.dto.KeyEventDto;
import nn.radio.dto.MouseEventDto;
import nn.radio.dto.TankDto;
import nn.radio.mapper.TankMapper;
import nn.radio.server.connection.EventClientConnection;
import nn.radio.server.connection.TankClientConnection;
import nn.radio.server.model.ServerTank;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ServerThread extends Thread implements KeyEventListener,
                                                    MouseClickedListener{


    private boolean alive = true;
    public Map<String, ServerTank> tankMap = new ConcurrentHashMap<>();

    List<TankClientConnection> tankClientConnectionList;
    List<EventClientConnection> eventClientConnectionList;

    public ServerThread () {
    }

    public void updateTankMap (Map<String, TankDto> tMap) {
        tankMap.clear();
        tMap.entrySet().forEach(t -> {
            tankMap.put(t.getKey(), TankMapper.fromTank(t.getValue()));
        });
    }

    public void updateClientList (List<TankClientConnection> tankClientConnectionList) {
        this.tankClientConnectionList = tankClientConnectionList;
        this.tankClientConnectionList.forEach(cc -> cc.start());
    }

    @Override
    public void run () {
        while (alive) {
            tankMap.values().forEach(t -> {
                t.move();
            });
            intersectChargeAndTanks();
            Map<String, TankDto> map = tankMap.values()
                    .stream()
                    .collect(Collectors.toMap(t1 -> t1.id,
                            k1 -> TankMapper.fromServerTank(k1)
                            )
                    );
            tankClientConnectionList.stream().forEach(sc ->
                    sc.updateTankMapWithDto(map));
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void intersectChargeAndTanks () {
        tankMap.values().forEach(clientTank -> {
            tankMap.values().forEach(t -> {
                if(!t.equals(clientTank)) {
                    t.getTore().clientChargeList.forEach(charge -> {
                        if(clientTank.intersect(charge)){
                            clientTank.alive = false;
                            charge.alive = false;
                        }
                    });
                }
            });
        });
    }

    @Override
    public void keyPressed (KeyEventDto e) {
        tankMap.values().stream().filter(t -> t.isFocusable())
                .findFirst()
                .get()
                .keyEventPressed(e);
    }

    @Override
    public void keyReleased (KeyEventDto e) {
        tankMap.values().stream().filter(t -> t.isFocusable())
                .findFirst()
                .get()
                .keyEventReleased(e);
    }

    @Override
    public void mouseClicked (MouseEventDto e) {
        tankMap.values().forEach(t -> t.mouseEventClicked(e));
    }

    public void setAlive (boolean alive) {
        this.alive = alive;
    }

    public void updateEventClientList (List<EventClientConnection> eventClientConnectionList) {
        this.eventClientConnectionList = eventClientConnectionList;
        this.eventClientConnectionList.forEach(cc -> cc.setKeyEventListener(this));
        this.eventClientConnectionList.forEach(cc -> cc.setMouseClickedListener(this));
        this.eventClientConnectionList.forEach(cc -> cc.start());
    }
}
