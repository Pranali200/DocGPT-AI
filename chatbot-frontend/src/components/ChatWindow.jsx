import { useEffect, useRef } from "react";

import MessageBubble from "./MessageBubble";


function ChatWindow({
    messages,
    loading,
    selectedDocument
}) {

    const messagesEndRef =
        useRef(null);


    // =====================================================
    // AUTO SCROLL
    // =====================================================

    useEffect(() => {

        messagesEndRef.current?.scrollIntoView({
            behavior: "smooth"
        });

    }, [messages, loading]);


    // =====================================================
    // RENDER
    // =====================================================

    return (

        <main className="chat-window">


            {/* =================================================
                NO DOCUMENT SELECTED
            ================================================= */}

            {!selectedDocument ? (

                <div className="chat-empty-state">

                    <h1>
                        Chat with your PDF
                    </h1>

                    <p>
                        Select a document from the sidebar
                        to start asking questions.
                    </p>

                </div>


            ) : (


                /* =================================================
                   DOCUMENT SELECTED
                ================================================= */

                <>

                    {/* DOCUMENT HEADER */}

                    <div className="selected-document">

                        <span>
                            📄
                        </span>

                        <strong>
                            {selectedDocument.fileName}
                        </strong>

                    </div>


                    {/* MESSAGES */}

                    <div className="messages">

                        {/* No messages yet */}

                        {messages.length === 0 && (

                            <div className="empty-chat">

                                <h2>
                                    Ask anything about this PDF
                                </h2>

                                <p>
                                    The AI will search the document
                                    using RAG before answering.
                                </p>

                            </div>

                        )}


                        {/* Messages */}

                        {messages.map(message => (

                            <MessageBubble
                                key={message.id}
                                role={message.role}
                                content={message.content}
                            />

                        ))}


                        {/* Loading */}

                        {loading && (

                            <div className="message assistant">

                                <div className="message-bubble">

                                    <div className="loading-message">

                                        <span>
                                            AI is thinking
                                        </span>

                                        <span className="loading-dot" />
                                        <span className="loading-dot" />
                                        <span className="loading-dot" />

                                    </div>

                                </div>

                            </div>

                        )}


                        <div
                            ref={messagesEndRef}
                        />

                    </div>

                </>

            )}

        </main>
    );
}


export default ChatWindow;