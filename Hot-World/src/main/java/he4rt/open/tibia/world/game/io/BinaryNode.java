package he4rt.open.tibia.world.game.io;

import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BinaryNode {

    private static final int NODE_START = 0xFE;
    private static final int NODE_END = 0xFF;
    private static final int ESCAPE_CHAR = 0xFD;

    private List<BinaryNode> children;
    private int type;
    private ByteArrayOutputStream data;

    public static BinaryNode load(String file) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        BinaryNode ret = load(in);
        in.close();
        return ret;
    }

    public static BinaryNode load(InputStream in) throws IOException {

        InputBaosVO inputBaosVO = new InputBaosVO(in.readAllBytes(), 0);
        //CDataInputStream cin = new CDataInputStream(in);
        long version = inputBaosVO.readInt(); //cin.readU32();
        if(version > 0) {
            throw new IOException("Invalid version!");
        } else {
            BinaryNode root = new BinaryNode();
            if(inputBaosVO.readByte() == NODE_START) {
                root.parseNode(inputBaosVO);
                return root;
            } else {
                throw new IOException("Invalid format.");
            }
        }
    }

    private BinaryNode() {
        children = new ArrayList();
        data = new ByteArrayOutputStream();
    }

    private void parseNode(InputBaosVO in) throws IOException {
        type = in.readByte();
        int b = in.readByte();
        while(b != NODE_END) {

            if(b == NODE_START) {
                BinaryNode child = new BinaryNode();
                addChild(child);
                child.parseNode(in);
            } else {
                if(b == ESCAPE_CHAR) {
                    b = in.readByte();
                }
                writeByte(data, b);
            }
            b = in.readByte();
        }
    }

    public static void writeByte(OutputStream out, int value) throws IOException {
        out.write(value & 0xFF);
    }

    private void addChild(BinaryNode node) {
        children.add(node);
    }

    public InputBaosVO generateInputBaos() {
        return new InputBaosVO(data.toByteArray(), 0);
    }

    public BinaryNode getFirstChild() {
        return children.get(0);
    }
}
