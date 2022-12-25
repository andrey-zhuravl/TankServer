package nn.radio.mapper;

import nn.radio.dto.ChargeDto;
import nn.radio.server.model.ServerCharge;

public class ChargeMapper {

    public static ServerCharge fromChargeDto (ChargeDto t) {
        ServerCharge dto = new ServerCharge(t.X, t.Y, t.alpha);
        dto.id = t.id;
        dto.X = t.X;
        dto.Y = t.Y;
        dto.alpha = t.alpha;
        dto.alive = t.alive;
        return dto;
    }

    public static ChargeDto fromServerCharge (ServerCharge t) {
        ChargeDto dto = new ChargeDto();
        dto.id = t.id;
        dto.X = t.X;
        dto.Y = t.Y;
        dto.alpha = t.alpha;
        dto.alive = t.alive;
        return dto;
    }
}
