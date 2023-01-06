package nn.radio.dto;

import java.io.Serializable;

public class ChargeDto extends EventDto implements Serializable {
    private static final long serialVersionUID = 6529685098267757692L;
    public String id;
    public float Y;
    public float X;
    public float alpha;
    public boolean alive = true;

}
