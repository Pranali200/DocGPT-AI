import { useEffect, useState } from "react";

import Navbar from "../components/Navbar";
import Sidebar from "../components/Sidebar";
import ChatWindow from "../components/ChatWindow";
import ChatInput from "../components/ChatInput";

import {
    getDocuments,
    sendMessage,
    deleteDocument
} from "../services/api";

import "./auth.css";


function Chat() {

    const [documents, setDocuments] = useState([]);

    const [selectedDocument, setSelectedDocument] =
        useState(null);

    const [messages, setMessages] =
        useState([]);

    const [sessionId, setSessionId] =
        useState(null);

    const [loading, setLoading] =
        useState(false);

    const [sidebarOpen, setSidebarOpen] =
        useState(false);


    /* =====================================================
       LOAD DOCUMENTS
    ===================================================== */

    useEffect(() => {

        async function loadDocuments() {

            try {

                const data =
                    await getDocuments();

                setDocuments(data);

            } catch (error) {

                console.error(
                    "Failed to load documents:",
                    error
                );

                /*
                 * If authentication fails,
                 * remove JWT and go to login.
                 */

                if (
                    error?.message?.includes("401") ||
                    error?.message?.includes("403")
                ) {

                    localStorage.removeItem("token");

                    window.location.href =
                        "/login";
                }
            }
        }

        loadDocuments();

    }, []);


    /* =====================================================
       SELECT DOCUMENT
    ===================================================== */

    function handleDocumentSelect(document) {

        setSelectedDocument(document);

        setMessages([]);

        setSessionId(null);

        setSidebarOpen(false);
    }


    /* =====================================================
       DELETE DOCUMENT
    ===================================================== */

    async function handleDeleteDocument(documentId) {

        const confirmed =
            window.confirm(
                "Are you sure you want to delete this document?"
            );

        if (!confirmed) {
            return;
        }

        try {

            /*
             * IMPORTANT:
             *
             * deleteDocument() sends:
             *
             * Authorization:
             * Bearer <JWT>
             *
             * This prevents the previous DELETE 403
             * caused by an unauthenticated request.
             */

            await deleteDocument(documentId);


            /*
             * Remove deleted document
             * from the frontend.
             */

            setDocuments(
                previousDocuments =>
                    previousDocuments.filter(
                        document =>
                            document.id !== documentId
                    )
            );


            /*
             * If the deleted document was
             * currently selected, reset chat.
             */

            if (
                selectedDocument?.id ===
                documentId
            ) {

                setSelectedDocument(null);

                setMessages([]);

                setSessionId(null);
            }

        } catch (error) {

            console.error(
                "Delete failed:",
                error
            );

            alert(
                "Failed to delete document."
            );
        }
    }


    /* =====================================================
       SEND MESSAGE
    ===================================================== */

    async function handleSend(question) {

        if (!selectedDocument) {
            return;
        }

        if (
            !question ||
            question.trim() === ""
        ) {
            return;
        }

        try {

            setLoading(true);


            const response =
                await sendMessage(
                    selectedDocument.id,
                    sessionId,
                    question
                );


            /*
             * Store returned session ID.
             */

            setSessionId(
                response.sessionId
            );


            /*
             * User message.
             */

            const userMessage = {

                id:
                    `user-${Date.now()}`,

                role: "USER",

                content: question
            };


            /*
             * AI response.
             */

            const assistantMessage = {

                id:
                    `assistant-${Date.now()}`,

                role: "ASSISTANT",

                content: response.answer
            };


            /*
             * Add both messages.
             */

            setMessages(
                previous => [
                    ...previous,

                    userMessage,

                    assistantMessage
                ]
            );

        } catch (error) {

            console.error(
                "Chat error:",
                error
            );

            alert(
                "Failed to get an answer from AI."
            );

        } finally {

            setLoading(false);
        }
    }


    /* =====================================================
       RENDER
    ===================================================== */

    return (
        <div className="chat-layout">

            {/* =========================================
                NAVBAR
            ========================================= */}

            <Navbar
                onMenuClick={() =>
                    setSidebarOpen(true)
                }
            />


            {/* =========================================
                CHAT BODY
            ========================================= */}

            <div className="chat-body">

                {/* =====================================
                    SIDEBAR
                ===================================== */}

                <Sidebar

                    documents={
                        documents
                    }

                    selectedDocument={
                        selectedDocument
                    }

                    onDocumentSelect={
                        handleDocumentSelect
                    }

                    onDocumentsChange={
                        setDocuments
                    }

                    onDeleteDocument={
                        handleDeleteDocument
                    }

                    isOpen={
                        sidebarOpen
                    }

                    onClose={() =>
                        setSidebarOpen(false)
                    }
                />


                {/* =====================================
                    CHAT AREA
                ===================================== */}

                <main className="chat-content">

                    <ChatWindow

                        messages={
                            messages
                        }

                        loading={
                            loading
                        }

                        selectedDocument={
                            selectedDocument
                        }
                    />


                    <ChatInput

                        onSend={
                            handleSend
                        }

                        disabled={
                            !selectedDocument ||
                            loading
                        }
                    />

                </main>

            </div>

        </div>
    );
}

export default Chat;