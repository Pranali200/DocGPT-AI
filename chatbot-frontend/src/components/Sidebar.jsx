import { useRef, useState } from "react";

import {
    getDocuments,
    uploadDocument,
    deleteDocument
} from "../services/api";


function Sidebar({
    documents,
    selectedDocument,
    onDocumentSelect,
    onDocumentsChange,
    isOpen,
    onClose
}) {

    const fileInputRef = useRef(null);

    const [uploading, setUploading] =
        useState(false);

    const [deletingId, setDeletingId] =
        useState(null);


    // =====================================================
    // UPLOAD PDF
    // =====================================================

    async function handleUpload(event) {

        const file =
            event.target.files[0];

        if (!file) {
            return;
        }

        if (file.type !== "application/pdf") {

            alert(
                "Please select a PDF file."
            );

            event.target.value = "";

            return;
        }

        try {

            setUploading(true);

            await uploadDocument(file);

            const updatedDocuments =
                await getDocuments();

            onDocumentsChange(
                updatedDocuments
            );

        } catch (error) {

            console.error(
                "Upload failed:",
                error
            );

            alert(
                "Failed to upload PDF."
            );

        } finally {

            setUploading(false);

            event.target.value = "";
        }
    }


    // =====================================================
    // DELETE DOCUMENT
    // =====================================================

    async function handleDelete(documentId) {

        const confirmed =
            window.confirm(
                "Are you sure you want to delete this document?"
            );

        if (!confirmed) {
            return;
        }

        try {

            setDeletingId(documentId);

            await deleteDocument(
                documentId
            );


            // Remove document from UI

            const updatedDocuments =
                documents.filter(
                    document =>
                        document.id !== documentId
                );

            onDocumentsChange(
                updatedDocuments
            );


            // Clear selected document

            if (
                selectedDocument?.id ===
                documentId
            ) {

                onDocumentSelect(null);
            }

        } catch (error) {

            console.error(
                "Delete failed:",
                error
            );

            alert(
                "Failed to delete document."
            );

        } finally {

            setDeletingId(null);
        }
    }


    // =====================================================
    // RENDER
    // =====================================================

    return (
        <>

            {/* Mobile overlay */}

            {isOpen && (
                <div
                    className="sidebar-overlay"
                    onClick={onClose}
                />
            )}


            <aside
                className={`sidebar ${
                    isOpen
                        ? "sidebar-open"
                        : ""
                }`}
            >

                {/* =========================================
                    HEADER
                ========================================= */}

                <div className="sidebar-header">

                    <h2>
                        Documents
                    </h2>

                    <button
                        className="close-sidebar"
                        onClick={onClose}
                    >
                        ×
                    </button>

                </div>


                {/* =========================================
                    FILE INPUT
                ========================================= */}

                <input
                    ref={fileInputRef}
                    type="file"
                    accept=".pdf,application/pdf"
                    hidden
                    onChange={handleUpload}
                />


                {/* =========================================
                    UPLOAD BUTTON
                ========================================= */}

                <button
                    className="upload-button"
                    onClick={() =>
                        fileInputRef.current?.click()
                    }
                    disabled={uploading}
                >
                    {uploading
                        ? "Uploading..."
                        : "+ Upload PDF"}
                </button>


                {/* =========================================
                    DOCUMENT LIST
                ========================================= */}

                <div className="document-list">

                    {documents.length === 0 && (

                        <p className="empty-documents">
                            No documents uploaded.
                        </p>

                    )}


                    {documents.map(document => (

                        <div
                            key={document.id}
                            className="document-item-wrapper"
                        >

                            {/* PDF */}

                            <button
                                className={`document-item ${
                                    selectedDocument?.id ===
                                    document.id
                                        ? "selected"
                                        : ""
                                }`}
                                onClick={() => {

                                    onDocumentSelect(
                                        document
                                    );

                                    onClose();
                                }}
                            >

                                <span className="pdf-icon">
                                    📄
                                </span>

                                <span className="document-name">
                                    {document.fileName}
                                </span>

                            </button>


                            {/* DELETE */}

                            <button
                                className="delete-document-button"
                                onClick={(event) => {

                                    event.stopPropagation();

                                    handleDelete(
                                        document.id
                                    );
                                }}
                                disabled={
                                    deletingId ===
                                    document.id
                                }
                                title="Delete document"
                            >

                                {deletingId ===
                                document.id
                                    ? "..."
                                    : "🗑️"}

                            </button>

                        </div>

                    ))}

                </div>

            </aside>

        </>
    );
}


export default Sidebar;