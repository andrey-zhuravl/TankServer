package nn.radio.mapper;

import nn.radio.dto.TankDto;
import nn.radio.dto.ToreDto;
import nn.radio.server.model.ServerTank;

public class TankMapper {
    public static ServerTank fromTank (TankDto t) {
        ServerTank dto = new ServerTank();
        dto.id = t.id;
        dto.X = t.X;
        dto.Y = t.Y;
        dto.alpha = t.alpha;
        dto.setTore(ToreMapper.fromClientTore(t.toreDto));
        return dto;
    }

    public static TankDto fromServerTank (ServerTank t) {
        TankDto dto = new TankDto();
        dto.id = t.id;
        dto.X = t.X;
        dto.Y = t.Y;
        dto.alpha = t.alpha;
        dto.isFocusable = t.isFocusable();
        dto.toreDto = ToreMapper.fromServerTore(t.getTore());
        dto.isAlive = t.alive;
        return dto;
    }
}
