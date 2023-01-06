package nn.radio.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {
    private final String name;
    public String id;
    public List<String> tankIdList;

    public UserDto (String userId, String name) {
        this.id = userId;
        this.name = name;
    }
}
