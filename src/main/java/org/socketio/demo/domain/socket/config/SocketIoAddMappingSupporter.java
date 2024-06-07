package org.socketio.demo.domain.socket.config;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.sanghan.repository.annotation.SocketSupporter.SocketController;
import org.sanghan.repository.annotation.SocketSupporter.SocketOn;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class  SocketIoAddMappingSupporter {

    private final ConfigurableListableBeanFactory beanFactory;
    private SocketIOServer socketIOServer;


    public void addListener(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        final List<Class<?>>classes  =
                beanFactory.getBeansWithAnnotation(SocketController.class).values().stream()
                        .map(Object::getClass).collect(Collectors.toList());
        // 대충 SocketController 어노테이션이 빈으로 등록된 클래스들을 순회하면서 가져옴

        for(Class<?> cls : classes) {
            List<Method> methods = findSocketMappingMethods(cls);
            // 해당 클래스를 순회하면서 클래스내에서 SocketMapping이 된 즉 SocketOn 어노테이션이 선언이
            // 되어있는 메서드들을 가져옴
            addSocketServerEventListener(cls, methods);
        }
    }
    private List<Method> findSocketMappingMethods(Class<?> cls) {
        return Arrays.stream(cls.getMethods()).filter(method ->
                method.getAnnotation(SocketOn.class)!=null).collect(Collectors.toList());
    }
    private void addSocketServerEventListener(Class<?> controller, List<Method> methods ){
        for (Method method : methods) {
            SocketOn socketon = method.getAnnotation(SocketOn.class);
            String url_Endpoint= socketon.endPoint();
            Class<?> requestTypeCls = socketon.requestDto();


            socketIOServer.addEventListener(url_Endpoint, requestTypeCls,((client, data, ackrequest)->{
                List<Object> args = new ArrayList<>();
                for (Class<?> params: method.getParameterTypes()){
                    if(params.equals(SocketIOServer.class)) args.add(socketIOServer);
                    else if (params.equals(SocketIOClient.class))args.add(client);
                    else if(params.equals(requestTypeCls))args.add(data);
                }
                method. invoke(beanFactory.getBean(controller),args.toArray());

            }));

        }
    }


}
