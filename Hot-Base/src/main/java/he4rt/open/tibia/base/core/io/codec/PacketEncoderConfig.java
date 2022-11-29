package he4rt.open.tibia.base.core.io.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PacketEncoderConfig implements ProtocolEncoder {

    @Override
    public void encode(IoSession ioSession, Object o, ProtocolEncoderOutput protocolEncoderOutput) throws Exception {

        protocolEncoderOutput.write(IoBuffer.wrap((byte[]) o));

    }

    @Override
    public void dispose(IoSession ioSession) throws Exception {

    }
}
