package nn.radio.dto;

import nn.radio.mapper.ChargeMapper;
import nn.radio.server.model.ServerTore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ToreDto implements Serializable {
    private static final long serialVersionUID = 6529685098267757691L;
    public String id;
    public float Y;
    public float X;
    public float alpha;
    public java.util.List<ChargeDto> clientChargeList = new ArrayList<>();

}
