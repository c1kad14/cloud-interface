package com.cloudinterface.lift;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.cloudinterface.XmlProcessor;

public class InterfaceServerEndpoint implements WebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(InterfaceServerEndpoint.class);

	@Override public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("Opened session for: {}", session.getId());
	}

	@Override public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
		Object payload = message.getPayload();
		if (payload instanceof String) {
			String stringMessage = (String) payload;
			if (stringMessage.equals("test")) {
				log.debug("Received test message from interface");
			}
			else {
				// process incoming message here
				log.debug("Received file content: {}", stringMessage);
				XmlProcessor processor = new XmlProcessor();
				File f = new File("temp.xml");
				BufferedWriter out = null;
				try {
					out = new BufferedWriter(new FileWriter(f));
					out.write(stringMessage);
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				finally {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				processor.process(f);
			}
		}
		else {
			log.error("Received unknown message");
		}
	}

	@Override public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.error("Error at endpoint: {}", exception.getMessage());

	}

	@Override public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		log.debug("Closed session for: {}", session.getId());
	}

	@Override public boolean supportsPartialMessages() {
		return false;
	}
}
