function MessageBubble({ role, content }) {

    const isUser = role === "USER";

    return (
        <div
            className={`message-row ${
                isUser
                    ? "user-message"
                    : "assistant-message"
            }`}
        >

            <div className="message-avatar">

                {isUser ? "You" : "AI"}

            </div>

            <div className="message-bubble">

                {content}

            </div>

        </div>
    );
}

export default MessageBubble;