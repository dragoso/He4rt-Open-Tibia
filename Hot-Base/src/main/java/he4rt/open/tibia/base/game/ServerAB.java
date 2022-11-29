package he4rt.open.tibia.base.game;

import he4rt.open.tibia.base.core.io.AGameHandlerCode;
import he4rt.open.tibia.base.core.io.GameHandlerBase;
import he4rt.open.tibia.base.core.io.codec.CodecConfig;
import he4rt.open.tibia.base.core.io.enums.RecvOpcodeEnum;
import he4rt.open.tibia.base.data.repository.WorldRepository;
import lombok.Getter;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetSocketAddress;
import java.util.*;

import static he4rt.open.tibia.base.core.io.constants.IOConstants.USER_SESSION;

@Getter
@Slf4j
public class ServerAB extends IoHandlerAdapter {

    private Integer port;

    private Integer version;

    @Getter(onMethod_ = {@Synchronized})
    private List<UserAB> connecteds = new ArrayList();

    public static final Map<RecvOpcodeEnum, GameHandlerBase> handlers = new HashMap();

    @Bean
    public NioSocketAcceptor serverBootstrap(AutowireCapableBeanFactory autowireCapableBeanFactory, CodecConfig codecConfig, Properties configProperties, WorldRepository worldRepository) throws IOException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        port = Integer.parseInt(configProperties.getProperty("serverPort"));
        version = Integer.parseInt(configProperties.getProperty("serverVersion"));

        Reflections reflections = new Reflections("he4rt");
        Set<Class<?>> importantClasses = reflections.getTypesAnnotatedWith(AGameHandlerCode.class);
        for (Class<?> clazz : importantClasses) {

            Object hb = clazz.getDeclaredConstructor().newInstance();
            autowireCapableBeanFactory.autowireBean(hb);

            RecvOpcodeEnum[] annotation = clazz.getAnnotation(AGameHandlerCode.class).code();
            for (RecvOpcodeEnum enumRecvyOpCode : annotation) {
                handlers.put(enumRecvyOpCode, (GameHandlerBase) hb);
            }
        }

        log.info("Operadores do jogo registrados. Total: <{}> .", handlers.size());

        NioSocketAcceptor acceptor = new NioSocketAcceptor();

        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(codecConfig));

        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
        acceptor.setHandler(this);
        acceptor.bind(new InetSocketAddress(port));
        acceptor.getSessionConfig().setTcpNoDelay(true);

        log.info("Server online on port: <{}>", port);
        return acceptor;

    }

    @Override
    public void sessionClosed(IoSession ioSession) {
        log.debug("Session <{}> closed.", ioSession.getRemoteAddress().toString());
        UserAB userAB = getConnecteds().stream().filter(c -> Objects.equals(c.getIoSession(), ioSession)).findFirst().orElse(null);
        if (Objects.nonNull(userAB)) {
            getConnecteds().remove(userAB);
        }
    }

    @Override
    public void exceptionCaught(IoSession ioSession, Throwable exception) {
        log.error("Unhandled error in connection to client at address <{}> .", ioSession.getRemoteAddress().toString(), exception);
        UserAB userAB = (UserAB) ioSession.getAttribute(USER_SESSION);
        if (Objects.nonNull(userAB) && userAB.getIoSession().isConnected()) {
            userAB.close();
        }
    }
}

