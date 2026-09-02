package org.pran.aichatbotapp.dto;

public class ChatResponse {

    private Long sessionId;

    private String answer;

    public ChatResponse() {
    }

    public ChatResponse(
            Long sessionId,
            String answer) {

        this.sessionId = sessionId;
        this.answer = answer;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}