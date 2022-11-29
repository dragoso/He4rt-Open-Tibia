package he4rt.open.tibia.world.game.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import he4rt.open.tibia.world.game.channel.dto.ChannelDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
@Component
public class ChannelStorage {

    @Getter(onMethod_ = {@Synchronized})
    private AtomicInteger channelIdGenerator = new AtomicInteger(1);

    @Getter(onMethod_ = {@Synchronized})
    private Map<Integer, ChannelVO> channels = new HashMap();

    public ChannelStorage(ObjectMapper objectMapper, Properties configProperties) throws IOException {

        ChannelDTO[] channels = objectMapper.readValue(new File(String.format("%s%s", configProperties.getProperty("directoryData"), "channels.json")), ChannelDTO[].class);

        for (ChannelDTO channelDTO : channels) {
            ChannelVO channelVO = createChannel();
            channelVO.setChannelDTO(channelDTO);
        }
    }

    public ChannelVO createChannel() {

        if (getChannelIdGenerator().incrementAndGet() > 21000000) {
            getChannelIdGenerator().set(1);
        }

        int channelId = getChannelIdGenerator().get();

        while (getChannels().containsKey(channelId)) channelId = getChannelIdGenerator().getAndIncrement();

        ChannelVO channelVO = ChannelVO.builder().id(channelId).connecteds(new HashMap()).build();

        getChannels().put(channelId, channelVO);

        return channelVO;
    }
}
