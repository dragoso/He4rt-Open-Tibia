package he4rt.open.tibia.base.core.io.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Setter
@Getter
@Slf4j
@SuperBuilder
public class BufferProcessVO {

    private int length;
    private int missing;
    private byte[] buffer;

}
