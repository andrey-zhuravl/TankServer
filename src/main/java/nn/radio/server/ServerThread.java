package nn.radio.server;

import nn.radio.dto.KeyEventDto;
import nn.radio.dto.MouseEventDto;
import nn.radio.dto.TankDto;
import nn.radio.mapper.TankMapper;
import nn.radio.server.connection.FullClientConnection;
import nn.radio.server.model.ServerTank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class ServerThread extends Thread implements KeyEventListener,
        MouseClickedListener {

    private boolean alive = true;
    public Map<String, Map<String, ServerTank>> tankMap = new ConcurrentHashMap<>();

    List<FullClientConnection> fullClientConnectionList;

    public ServerThread () {
    }

    public void updateTankMap (Map<String, TankDto> tMap) {
        tankMap.clear();
        Map<String, Map<String, TankDto>> userIdTankIdTankMap = tMap.values()
                .stream()
                .collect(HashMap::new, (map, tank) -> {
                    Map<String, TankDto> tankIdMap = map.get(tank.userId);
                    if (tankIdMap == null) {
                        tankIdMap = new HashMap<>();
                    }
                    tankIdMap.put(tank.id, tank);
                    map.put(tank.userId, tankIdMap);
                }, (map1, map2) -> {
                });


        userIdTankIdTankMap.forEach((key, value) ->
                tankMap.put(key, TankMapper.fromDtoTankMap(value))
        );
    }

    public void updateFullClientList (List<FullClientConnection> fullClientConnection) {
        this.fullClientConnectionList = fullClientConnection;
    }

    @Override
    public void run () {
        while (alive) {
            tankMap.values().forEach(t -> {
                t.forEach((id, tank) -> tank.move());
            });
            intersectChargeAndTanks();

            Map<String, TankDto> map = tankMap.values()
                    .stream()
                    .flatMap(entry -> entry.values().stream())
                    .collect(Collectors.toMap(t1 -> t1.id,
                            k1 -> TankMapper.fromServerTank(k1)
                            )
                    );
            fullClientConnectionList.stream()
                    .filter(sc -> sc.tankClientConnection.isAlive)
                    .forEach(sc ->
                            sc.tankClientConnection.updateTankMapWithDto(map));
            try {
                Thread.sleep(20);
            } catch (Exception e) {
                throw new Error();
            }
        }
    }

    private void intersectChargeAndTanks () {
        tankMap.values()
                .stream()
                .flatMap(entry -> entry.values().stream())
                .forEach(clientTank -> {
                    tankMap.values()
                            .stream()
                            .flatMap(entry -> entry.values().stream())
                            .forEach(t -> {
                                if (!t.equals(clientTank)) {
                                    t.getTore().clientChargeList.forEach(charge -> {
                                        if (clientTank.intersect(charge)) {
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
        tankMap.values().stream()
                .flatMap(entry -> entry.values().stream())
                .filter(t -> t.isFocusable() && e.userId.equals(t.userId))
                .limit(1)
                .forEach(t -> t.keyEventPressed(e));
    }

    @Override
    public void keyReleased (KeyEventDto e) {
        tankMap.values().stream()
                .flatMap(entry -> entry.values().stream())
                .filter(t -> t.isFocusable() && e.userId.equals(t.userId))
                .limit(1)
                .forEach(t -> t.keyEventReleased(e));
    }

    @Override
    public void mouseClicked (MouseEventDto e) {
        tankMap.forEach((u, tMap) -> {
            if (e.userId.equals(u)) {
                AtomicBoolean isMatch = new AtomicBoolean(false);
                tMap.forEach(
                        (id, tank) -> {
                            if (tank.isMatch(e)) {
                                isMatch.set(true);
                            }
                        }
                );
                if (isMatch.get()) {
                    tMap.forEach(
                            (id, tank) -> {
                                tank.mouseEventClicked(e);
                            }
                    );
                }
            }
        });
    }

    public void setAlive (boolean alive) {
        this.alive = alive;
    }

}
