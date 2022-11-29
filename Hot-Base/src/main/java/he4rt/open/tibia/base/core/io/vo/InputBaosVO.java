package he4rt.open.tibia.base.core.io.vo;

import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.core.security.XTEASecurity;
import he4rt.open.tibia.base.core.tibia.Position;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Setter
@Getter
@Slf4j
public class InputBaosVO {
    private int position = 0;
    private final int length;
    private byte[] bytes;

    private RecvOpcodeEnum opCode;

    public InputBaosVO(byte[] bytes, int length) {
        this.bytes = bytes;
        this.length = length;
    }

    public int readByte() {
        return ((int) bytes[position++]) & 0xFF;
    }

    public long readInt() {
        return ((long) (readByte() | readByte() << 8 | readByte() << 16 | readByte() << 24)) & 0xFFFFFFFFL;
    }

    public short readShort() {
        return (short) (readByte() + (readByte() << 8));
    }

    public byte[] readBytes(int amount) {
        byte[] arr = new byte[amount];
        for (int i = 0; i < amount; i++) {
            arr[i] = (byte) readByte();
        }
        return arr;
    }

    public Position readPosition() {

        return Position.builder().x(readShort()).y(readShort()).z(readByte()).build();

    }

    public String readString() {

        try {
            int l = readShort();
            return new String(readBytes(l), StandardCharsets.ISO_8859_1);
        } catch (Exception e) {

            log.error("Erro ao converter String. Erro: ", e);
            return null;

        }
    }

    public String readString(int length) {

        try {
            return new String(readBytes(length), StandardCharsets.ISO_8859_1);
        } catch (Exception e) {

            log.error("Erro ao converter String. Erro: ", e);
            return null;

        }
    }

    public void decript(XTEASecurity xteaSecurity) {

        try {

            byte[] encoded = new byte[]{bytes[6], bytes[7], bytes[8], bytes[9], bytes[10], bytes[11], bytes[12], bytes[13]};
            byte[] descrypt = xteaSecurity.decrypt(encoded);

            bytes[6] = descrypt[0];
            bytes[7] = descrypt[1];
            bytes[8] = descrypt[2];
            bytes[9] = descrypt[3];
            bytes[10] = descrypt[4];
            bytes[11] = descrypt[5];
            bytes[12] = descrypt[6];
            bytes[13] = descrypt[7];

        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    public void skipBytes(int offset) {
        position += offset;
    }

    public long available() {
        return bytes.length - position;
    }
}
