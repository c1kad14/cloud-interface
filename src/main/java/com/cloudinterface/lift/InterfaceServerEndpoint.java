package com.cloudinterface.lift;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class InterfaceServerEndpoint implements WebSocketHandler {

	private static final Logger log = LoggerFactory.getLogger(InterfaceServerEndpoint.class);

	@Override public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		log.debug("Opened session for: {}", session.getId());
	}

	@Override public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		Object payload = message.getPayload();
		if (payload instanceof String) {
			String stringMessage = (String) payload;
			if (stringMessage.equals("test")) {
				log.debug("Received test message from interface");
			}
			else {
				// process incoming message here
				log.debug("Received file content: {}", stringMessage);
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
