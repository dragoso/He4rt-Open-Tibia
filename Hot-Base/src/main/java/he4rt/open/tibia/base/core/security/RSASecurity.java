package he4rt.open.tibia.base.core.security;

import he4rt.open.tibia.base.core.io.vo.InputBaosVO;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.util.Properties;

@Configuration
public class RSASecurity {

    private AsymmetricCipherKeyPair asymmetricCipherKeyPair;

    public RSASecurity(Properties configProperties) throws Exception {

        FileReader fileReader = new FileReader(String.format("%s%s", configProperties.getProperty("directoryData"), "key.pem"));
        PEMParser r = new PEMParser(fileReader);

        PEMKeyPair keyParam = (PEMKeyPair) r.readObject();

        AsymmetricKeyParameter privKey = PrivateKeyFactory.createKey(keyParam.getPrivateKeyInfo());
        AsymmetricKeyParameter pubKey = PublicKeyFactory.createKey(keyParam.getPublicKeyInfo());
        this.asymmetricCipherKeyPair = new AsymmetricCipherKeyPair(pubKey, privKey);

    }

    public InputBaosVO decodeRSA(byte[] encryptedData) {
        InputBaosVO inputBaosVO;
        RSAEngine engine = new RSAEngine();
        engine.init(false, asymmetricCipherKeyPair.getPrivate());

        inputBaosVO = new InputBaosVO(engine.processBlock(encryptedData, 0, encryptedData.length), encryptedData.length);
        return inputBaosVO;
    }

}
