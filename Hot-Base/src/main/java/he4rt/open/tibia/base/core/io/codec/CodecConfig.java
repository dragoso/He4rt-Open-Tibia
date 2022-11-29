package he4rt.open.tibia.base.core.io.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodecConfig implements ProtocolCodecFactory {

    private final ProtocolEncoder encoder;
    private final ProtocolDecoder decoder;

    public CodecConfig(PacketEncoderConfig encoder, PacketDecoderConfig decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }


    @Override
    public ProtocolEncoder getEncoder(IoSession ioSession) {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession ioSession) {
        return decoder;
    }
}
