package he4rt.open.tibia.base.core.io.vo;

import he4rt.open.tibia.base.core.io.enums.IEnums;
import he4rt.open.tibia.base.core.io.enums.SendOpcodeEnum;
import he4rt.open.tibia.base.core.security.XTEASecurity;
import he4rt.open.tibia.base.core.tibia.Position;
import he4rt.open.tibia.base.data.enums.LocationEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@Setter
@Getter
@Slf4j
public class OutputBaosVO {

    private final LocationEnum locationEnum;
    private int position = 0;
    private byte[] bytes;
    private boolean encode = true;

    public OutputBaosVO(LocationEnum locationEnum) {
        this.bytes = new byte[16394];
        this.locationEnum = locationEnum;
    }

    public OutputBaosVO(byte[] b, LocationEnum locationEnum) {
        this.bytes = b;
        this.locationEnum = locationEnum;
    }

    public OutputBaosVO(SendOpcodeEnum b, LocationEnum locationEnum) {
        this(locationEnum);
        write(b);
    }

    public void write(int b) {

        if (bytes.length <= position)
            bytes = ByteBuffer.allocate(position + 1).put(bytes, 0, position).array();

        bytes[position++] = (byte) b;
    }

    public void write(IEnums b) {
        write(b.getValue());
    }

    public void write(boolean b) {
        write(b ? 1 : 0);
    }

    public void write(byte[] b) {
        for (int x = 0; x < b.length; x++) {
            write(b[x]);
        }
    }

    public void addPaddingBytes(int count) {
        for (int i = 0; i < count; i++) {
            write(51);
        }
    }

    public void writeShort(short i) {
        write(i & 0xFF);
        write((i >>> 8) & 0xFF);
    }

    public void writeShort(int i) {
        writeShort((short) i);
    }

    public void writeShort(long i) {
        writeShort((short) i);
    }

    public void writeShort(IEnums i) {
        writeShort((short) i.getValue());
    }

    public void writeDouble(double d, int precision) {

        write(precision);
        writeInt((long) ((d * Math.pow(10, precision)) + Integer.MAX_VALUE));

    }

    public void writeLong(long l) {
        write((int) (l & 0xFF));
        write((int) ((l >>> 8) & 0xFF));
        write((int) ((l >>> 16) & 0xFF));
        write((int) ((l >>> 24) & 0xFF));
        write((int) ((l >>> 32) & 0xFF));
        write((int) ((l >>> 40) & 0xFF));
        write((int) ((l >>> 48) & 0xFF));
        write((int) ((l >>> 56) & 0xFF));
    }

    public void writeInt(long i) {
        write((int) (i & 0xFF));
        write((int) ((i >>> 8) & 0xFF));
        write((int) ((i >>> 16) & 0xFF));
        write((int) ((i >>> 24) & 0xFF));
    }

    public void writePosition(Position position) {

        writeShort(position.getX());
        writeShort(position.getY());
        write(position.getZ());

    }

    public void writeString(String string) {
        try {
            byte[] b = string.getBytes(locationEnum.getValue());
            writeShort(b.length);
            write(b);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao serializar string " + string);
        }
    }

    public byte[] encode(XTEASecurity xteaSecurity) throws IOException {

        if (encode) {

            writeLength();

            var pad = getPosition() % 8;
            if (pad != 0) addPaddingBytes(8 - pad);

            ByteBuffer b = ByteBuffer.allocate(getPosition());

            b.put(xteaSecurity.encrypt(getBytes(), getPosition()));

            setBytes(b.array());

        }

        writeHeader();

        return getBytes();
    }

    public void writeLength() {

        bytes = ByteBuffer.allocate(position + 50).order(ByteOrder.LITTLE_ENDIAN).putShort((short) position).put(bytes, 0, position).array();
        position += 2;

    }

    public void writeHeader() {

        int checkSumValue = adlerChecksum(bytes, 0, position);

        byte[] checkSumBytes = ByteBuffer
                .allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(checkSumValue)
                .array();

        bytes = ByteBuffer
                .allocate(position + 6)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort((short) (position + checkSumBytes.length))
                .put(checkSumBytes).put(bytes, 0, position)
                .array();

    }

    public int adlerChecksum(byte[] data, int off, int length) {
        int adler = 65521;

        int a = 1, b = 0;

        length--;
        while (length > 0) {
            int tmp = length > 5552 ? 5552 : length;
            length -= tmp;

            do {
                a += data[off++] & 0xff;
                b += a;
            } while (tmp-- > 0);

            a %= adler;
            b %= adler;
        }
        return (b << 16) | a;
    }
}
