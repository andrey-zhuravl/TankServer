package nn.radio.mapper;

import nn.radio.dto.ToreDto;
import nn.radio.server.model.ServerTore;

import java.util.stream.Collectors;

public class ToreMapper {

    public static ServerTore fromClientTore (ToreDto t) {
        ServerTore dto = new ServerTore();
        dto.id = t.id;
        dto.X = t.X;
        dto.Y = t.Y;
        dto.alpha = t.alpha;
        dto.clientChargeList = t.clientChargeList.stream()
                .map(ch -> ChargeMapper.fromChargeDto(ch))
                .collect(Collectors.toList());
        return dto;
    }

    public static ToreDto fromServerTore (ServerTore t) {
        ToreDto dto = new ToreDto();
        dto.id = t.id;
        dto.X = t.X;
        dto.Y = t.Y;
        dto.alpha = t.alpha;
        dto.clientChargeList = t.clientChargeList.stream()
                .map(ch -> ChargeMapper.fromServerCharge(ch))
                .collect(Collectors.toList());
        return dto;
    }
}
