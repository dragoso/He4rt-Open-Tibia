package he4rt.open.tibia.base.core.io.codec;

import he4rt.open.tibia.base.core.io.vo.BufferProcessVO;
import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import lombok.AllArgsConstructor;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.springframework.context.annotation.Configuration;

import java.nio.ByteBuffer;
import java.util.Objects;

import static he4rt.open.tibia.base.core.io.constants.IOConstants.DECODER_SESSION;

@AllArgsConstructor
@Configuration
public class PacketDecoderConfig extends CumulativeProtocolDecoder {

    @Override
    protected boolean doDecode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput) {
        BufferProcessVO bufferProcessVO = (BufferProcessVO) ioSession.getAttribute(DECODER_SESSION);

        ByteBuffer buffer = ioBuffer.buf();

        byte[] baos = new byte[buffer.limit()];
        ioBuffer.get(baos, 0, buffer.limit());

        InputBaosVO inputBaosVO = new InputBaosVO(baos, buffer.limit());

        int length = inputBaosVO.readShort() + 2;

        if (Objects.isNull(bufferProcessVO.getBuffer())) {

            if (buffer.limit() < length) {

                bufferProcessVO.setLength(length);
                bufferProcessVO.setMissing(buffer.limit() - length);

                byte[] bytes = new byte[buffer.limit() - length];
                ioBuffer.get(bytes, 0, buffer.limit());
                bufferProcessVO.setBuffer(bytes);

                return false;

            }

            inputBaosVO.setPosition(0);
            protocolDecoderOutput.write(inputBaosVO);

            return true;

        }

        int max = Math.min(bufferProcessVO.getMissing(), buffer.limit());

        bufferProcessVO.setBuffer(ByteBuffer.allocate(bufferProcessVO.getBuffer().length + max).put(bufferProcessVO.getBuffer()).put(ioBuffer.array(), 0, max).array());
        bufferProcessVO.setMissing(bufferProcessVO.getBuffer().length - bufferProcessVO.getLength());

        if (bufferProcessVO.getMissing() != 0) {
            return false;
        }

        protocolDecoderOutput.write(new InputBaosVO(bufferProcessVO.getBuffer(), bufferProcessVO.getLength()));

        return false;

    }
}
