import { useState } from "react";

function ChatInput({
    onSend,
    disabled
}) {

    const [question, setQuestion] = useState("");

    async function handleSubmit(event) {

        event.preventDefault();

        const trimmedQuestion =
            question.trim();

        if (!trimmedQuestion || disabled) {
            return;
        }

        setQuestion("");

        await onSend(trimmedQuestion);
    }

    function handleKeyDown(event) {

        if (
            event.key === "Enter" &&
            !event.shiftKey
        ) {

            event.preventDefault();

            handleSubmit(event);
        }
    }

    return (
        <div className="input-wrapper">

            <form
                className="chat-input-form"
                onSubmit={handleSubmit}
            >

                <textarea
                    value={question}
                    onChange={(event) =>
                        setQuestion(event.target.value)
                    }
                    onKeyDown={handleKeyDown}
                    placeholder={
                        disabled
                            ? "Select a PDF first..."
                            : "Ask anything about your PDF..."
                    }
                    disabled={disabled}
                    rows={1}
                />

                <button
                    type="submit"
                    disabled={
                        disabled ||
                        !question.trim()
                    }
                >
                    ➤
                </button>

            </form>

            <p className="input-hint">
                Enter to send • Shift + Enter for new line
            </p>

        </div>
    );
}

export default ChatInput;