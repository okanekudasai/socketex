package com.app.socke;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Service;

@Service
@ServerEndpoint("/socket")
public class WebSocketChat {
	private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	@OnOpen
    public void onOpen(Session session) throws Exception {
		System.out.println("OnOpen");
		System.out.println(session);
		System.out.println(clients);
		if(!clients.contains(session)) {
            clients.add(session);
        }else{
            System.out.println("이미 연결된 session");
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
    	System.out.println("OnMessage");
    	System.out.println(message);
    	for (Session s : clients) {
    		s.getBasicRemote().sendText(message);
    	}
    }

    @OnClose
    public void onClose(Session session) {
    	System.out.println("OnClose");
    	clients.remove(session);
    }
}
