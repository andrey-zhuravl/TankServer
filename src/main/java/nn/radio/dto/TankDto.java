package nn.radio.dto;


import nn.radio.server.model.ServerTank;

import java.io.Serializable;

public class TankDto implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    public String id;
    public String userId;
    public float Y;
    public float X;
    public float alpha;
    public boolean isFocusable = false;
    public boolean isAlive = true;
    public ToreDto toreDto;

    public TankDto (String tankId, UserDto user, float x, float y) {
        this.id = tankId;
        this.userId = user.id;
        this.X = x;
        this.Y = y;
        this.toreDto = new ToreDto();
        this.toreDto.X = x;
        this.toreDto.Y = y;
    }

    public TankDto () {
    }

    public String getId(){
        return id;
    }

    public String getUserId(){
        return userId;
    }


}
