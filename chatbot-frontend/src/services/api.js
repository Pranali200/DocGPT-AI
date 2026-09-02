const API_BASE_URL =
    `${import.meta.env.VITE_API_URL}`;

export async function getDocuments() {

    const token = localStorage.getItem("token");

    const response = await fetch(
    `${API_BASE_URL}/documents`,
    {
        headers: {
            Authorization: `Bearer ${token}`
        }
    }
);

    if (!response.ok) {
        throw new Error("Failed to fetch documents");
    }

    return response.json();
}


export async function uploadDocument(file) {

    const formData = new FormData();

    formData.append("file", file);

    const token = localStorage.getItem("token");

    const response = await fetch(
    `${API_BASE_URL}/documents/upload`,
    {
        method: "POST",
        headers: {
            Authorization: `Bearer ${token}`
        },
        body: formData
    }
);

    if (!response.ok) {
        throw new Error("Failed to upload document");
    }

    return response.json();
}


export async function sendMessage(
    documentId,
    sessionId,
    question
) {
    const token = localStorage.getItem("token");

    const response = await fetch(
        `${API_BASE_URL}/chat`,
        {
            method: "POST",

            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },

            body: JSON.stringify({
                documentId,
                sessionId,
                question
            })
        }
    );

    if (!response.ok) {
        throw new Error("Failed to send message");
    }

    return response.json();
}


export async function getChatMessages(
    sessionId
) {
    const token = localStorage.getItem("token");

    const response = await fetch(
        `${API_BASE_URL}/chat/sessions/${sessionId}/messages`,
        {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }
    );

    if (!response.ok) {
        throw new Error(
            "Failed to fetch chat messages"
        );
    }

    return response.json();
}


export async function deleteDocument(documentId) {

    const token =
        localStorage.getItem("token");

    const response = await fetch(
        `${API_BASE_URL}/documents/${documentId}`,
        {
            method: "DELETE",

            headers: {
                "Authorization": `Bearer ${token}`
            },

            credentials: "include"
        }
    );


    if (!response.ok) {

        const errorText =
            await response.text();

        console.error(
            "Delete document failed:",
            response.status,
            errorText
        );

        throw new Error(
            "Failed to delete document"
        );
    }


    return true;
}

