# DocGPT-AI
AI-powered document research assistant using RAG, Spring Boot, React, PostgreSQL and pgvector.

### AI-Powered Document Research Assistant

DocGPT is an AI-powered document research assistant that allows users to upload multiple PDF documents and interact with their content using natural language.

Instead of manually searching through large documents, users can ask questions and receive relevant, context-aware answers powered by Retrieval-Augmented Generation (RAG).

The application combines a React frontend, Spring Boot backend, dedicated embedding service, PostgreSQL with pgvector, and an AI language model to build an end-to-end document question-answering system.

---

## 🚀 Features

### 🔐 Authentication

- User registration and login
- JWT-based authentication
- Protected REST APIs
- User-specific document access
- Secure document deletion

### 📄 Document Management

- Upload multiple PDF documents
- Extract text from uploaded documents
- Split documents into smaller chunks
- Store document metadata
- Delete documents
- Automatically manage associated document chunks

### 🧠 AI-Powered RAG

- Generate vector embeddings for document chunks
- Store embeddings using PostgreSQL + pgvector
- Perform semantic similarity search
- Retrieve the most relevant document context
- Generate answers using an AI language model

### 💬 Chat

- Ask questions about uploaded documents
- Maintain chat sessions
- Persist chat messages
- Retrieve previous conversations
- Context-aware responses

### 🎨 Frontend

- Modern React UI
- Responsive design
- Document sidebar
- Upload and delete controls
- Chat interface
- Authentication pages
- Loading and error states

---

# 🏗️ System Architecture

```text
                         ┌──────────────────────┐
                         │      React UI        │
                         │   Frontend / Vite    │
                         └──────────┬───────────┘
                                    │
                                    │ REST API
                                    ▼
                         ┌──────────────────────┐
                         │   Spring Boot API    │
                         │                      │
                         │ Authentication       │
                         │ Documents            │
                         │ Chat                 │
                         │ RAG                  │
                         └───────┬───────┬──────┘
                                 │       │
                     Embeddings  │       │ AI Request
                                 │       │
                                 ▼       ▼
                     ┌──────────────┐  ┌──────────────┐
                     │  Embedding   │  │  AI / LLM    │
                     │   Service    │  │   Provider    │
                     │   Python     │  └──────────────┘
                     └──────┬───────┘
                            │
                            │ Vector
                            ▼
                 ┌────────────────────────┐
                 │ PostgreSQL + pgvector  │
                 │                        │
                 │ Users                  │
                 │ Documents              │
                 │ Document Chunks        │
                 │ Embeddings             │
                 │ Chat Sessions          │
                 │ Chat Messages          │
                 └────────────────────────┘


🔄 RAG Pipeline

DocGPT uses Retrieval-Augmented Generation to answer questions from uploaded documents.

Document Ingestion

When a user uploads a PDF:

PDF
 │
 ▼
Spring Boot
 │
 ▼
Text Extraction
 │
 ▼
Text Chunking
 │
 ▼
Embedding Service
 │
 ▼
Vector Embeddings
 │
 ▼
PostgreSQL + pgvector

Each document is divided into smaller chunks.

Every chunk is converted into a numerical vector representation called an embedding.

The embeddings are stored in PostgreSQL using the pgvector extension.

Question Answering

When the user asks a question:

User Question
      │
      ▼
Spring Boot
      │
      ▼
Generate Question Embedding
      │
      ▼
Embedding Service
      │
      ▼
Vector Similarity Search
      │
      ▼
PostgreSQL + pgvector
      │
      ▼
Relevant Document Chunks
      │
      ▼
RAG Context
      │
      ▼
AI / LLM
      │
      ▼
Generated Answer

This allows DocGPT to answer questions using the actual content of the user's documents instead of relying only on the model's general knowledge.

🧩 Services

DocGPT consists of three main application services.

1. Frontend

Technology:

React
Vite
JavaScript
CSS

Responsibilities:

User authentication UI
Document upload
Document management
Chat interface
API communication
JWT token handling
2. Backend

Technology:

Java
Spring Boot
Spring Security
JWT
Spring Data JPA
Hibernate
Maven

Responsibilities:

Authentication and authorization
REST APIs
PDF processing
Document management
Document chunking
RAG orchestration
Vector search
Chat management
Communication with embedding service
Communication with AI provider
3. Embedding Service

Technology:

Python
FastAPI
Sentence Transformers / Embedding Model

Responsibilities:

Receive text from Spring Boot
Generate vector embeddings
Return embeddings to the backend

Example:

Spring Boot
     │
     │ POST /embed
     ▼
Embedding Service
     │
     │ Generate embedding
     ▼
Vector
     │
     ▼
Spring Boot

The embedding service is intentionally separated from the Spring Boot application so that embedding generation can be independently scaled, replaced, or deployed.

🗄️ Database

DocGPT uses:

PostgreSQL + pgvector

The database stores:

users
 │
 └── documents
       │
       └── document_chunks
              │
              └── embeddings

chat_sessions
 │
 └── chat_messages
Main Entities
User

Stores user account information and authentication data.

Document

Stores uploaded document metadata and ownership information.

DocumentChunk

Stores individual chunks extracted from documents along with their vector embeddings.

ChatSession

Represents a conversation associated with a document.

ChatMessage

Stores user questions and AI-generated responses.

🔌 REST API
Authentication
Signup
POST /api/auth/signup
Login
POST /api/auth/login
Documents
Get all documents
GET /api/documents

Requires authentication.

Upload document
POST /api/documents/upload

Requires authentication.

Content type:

multipart/form-data

Parameter:

file
Get document
GET /api/documents/{id}

Requires authentication.

Delete document
DELETE /api/documents/{id}

Requires authentication.

Chat
Ask a question
POST /api/chat

Example request:

{
  "documentId": 1,
  "sessionId": 1,
  "question": "What are the main conclusions of this document?"
}
Get chat messages
GET /api/chat/sessions/{sessionId}/messages
🔐 Security

DocGPT uses Spring Security with JWT authentication.

The authentication flow is:

Login
  │
  ▼
Spring Boot
  │
  ▼
JWT Token
  │
  ▼
Frontend
  │
  ▼
Authorization: Bearer <token>
  │
  ▼
JWT Authentication Filter
  │
  ▼
Authenticated Request

Protected resources cannot be accessed without a valid JWT.

📁 Project Structure
DocGPT/
│
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/org/pran/aichatbotapp/
│   │       │   ├── config/
│   │       │   ├── controller/
│   │       │   ├── dto/
│   │       │   ├── exception/
│   │       │   ├── filter/
│   │       │   ├── model/
│   │       │   ├── repository/
│   │       │   ├── security/
│   │       │   ├── service/
│   │       │   └── AiChatbotApplication.java
│   │       │
│   │       └── resources/
│   │           └── application.properties
│   │
│   └── pom.xml
│
├── embedding-service/
│   ├── app/
│   │   ├── __init__.py
│   │   ├── main.py
│   │   └── embedding.py
│   │
│   ├── requirements.txt
│   └── .env.example
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── App.jsx
│   │   └── main.jsx
│   │
│   ├── package.json
│   └── vite.config.js
│
├── .gitignore
├── README.md
└── docker-compose.yml
🛠️ Tech Stack
Layer	Technology
Frontend	React
Build Tool	Vite
Backend	Spring Boot
Language	Java
Security	Spring Security + JWT
ORM	Hibernate / JPA
Database	PostgreSQL
Vector Database	pgvector
Embedding Service	Python
Embedding API	FastAPI
RAG	Retrieval-Augmented Generation
AI	OpenAI-compatible LLM API
Build Tool	Maven
API Testing	Postman
Version Control	Git + GitHub
⚙️ Local Development
Prerequisites

Install:

Java
Maven
Node.js
npm
Python
PostgreSQL
pgvector
Git
1. Clone the Repository
git clone https://github.com/YOUR_USERNAME/DocGPT.git
cd DocGPT
2. Configure PostgreSQL

Create a PostgreSQL database:

CREATE DATABASE docgpt;

Enable pgvector:

CREATE EXTENSION IF NOT EXISTS vector;

Configure your database credentials in:

backend/src/main/resources/application.properties

Example:

spring.datasource.url=jdbc:postgresql://localhost:5432/docgpt
spring.datasource.username=postgres
spring.datasource.password=YOUR_PASSWORD
3. Start the Embedding Service

Navigate to:

cd embedding-service

Create a virtual environment:

python -m venv venv

Activate it on Windows:

venv\Scripts\activate

Install dependencies:

pip install -r requirements.txt

Start the service:

uvicorn app.main:app --host 0.0.0.0 --port 8001

The embedding service will run on:

http://localhost:8001
4. Start the Spring Boot Backend

Open another terminal:

cd backend

Run:

./mvnw spring-boot:run

On Windows:

mvnw.cmd spring-boot:run

Backend:

http://localhost:8080
5. Start the React Frontend

Open another terminal:

cd frontend

Install dependencies:

npm install

Start Vite:

npm run dev

Frontend:

http://localhost:5173
🔑 Environment Variables

Do not commit API keys, database passwords, JWT secrets, or other credentials to GitHub.

Use environment variables or .env files.

Example:

DB_USERNAME=postgres
DB_PASSWORD=your_password

JWT_SECRET=your_secret

OPENAI_API_KEY=your_api_key

Embedding service example:

EMBEDDING_MODEL=your_embedding_model

Add secret files to .gitignore.

🧪 Testing

Backend APIs can be tested using Postman.

Typical flow:

1. Signup
      ↓
2. Login
      ↓
3. Copy JWT token
      ↓
4. Upload PDF
      ↓
5. Wait for document processing
      ↓
6. Ask questions
      ↓
7. Verify RAG responses
📈 Future Improvements

Possible future improvements include:

Streaming AI responses
Multi-document conversations
Document comparison
Citation-aware answers
Conversation history management
Redis caching
Background document processing
Asynchronous embedding generation
Rate limiting
Role-based access control
Dockerized deployment
Cloud deployment
Monitoring and logging
Automated tests
CI/CD pipeline
🐳 Docker

The project can be containerized using Docker.

Planned architecture:

                    Docker Environment
┌──────────────────────────────────────────────┐
│                                              │
│   ┌───────────┐                              │
│   │ Frontend  │                              │
│   │   :5173   │                              │
│   └─────┬─────┘                              │
│         │                                    │
│         ▼                                    │
│   ┌───────────┐       ┌─────────────────┐   │
│   │ Backend   │──────▶│ Embedding       │   │
│   │   :8080   │       │ Service :8001   │   │
│   └─────┬─────┘       └─────────────────┘   │
│         │                                    │
│         ▼                                    │
│   ┌─────────────────────┐                    │
│   │ PostgreSQL +        │                    │
│   │ pgvector            │                    │
│   └─────────────────────┘                    │
│                                              │
└──────────────────────────────────────────────┘
🌐 Deployment Architecture

For production deployment, the services can be deployed independently:

                   Internet
                      │
                      ▼
              ┌───────────────┐
              │   Frontend    │
              │ Vercel / CDN  │
              └───────┬───────┘
                      │
                      ▼
              ┌───────────────┐
              │ Spring Boot   │
              │    Backend    │
              └───────┬───────┘
                      │
             ┌────────┴─────────┐
             │                  │
             ▼                  ▼
     ┌───────────────┐   ┌──────────────┐
     │  Embedding    │   │   AI / LLM   │
     │    Service    │   │   Provider   │
     └───────┬───────┘   └──────────────┘
             │
             ▼
     ┌───────────────────┐
     │ PostgreSQL        │
     │ + pgvector        │
     └───────────────────┘

The embedding service can be deployed independently from the Spring Boot backend, allowing each service to scale independently.

🎯 Why This Project?

Traditional document research often requires users to manually search through large amounts of information.

DocGPT demonstrates how modern AI systems can combine:

Large Language Models
Vector embeddings
Semantic search
Retrieval-Augmented Generation
REST APIs
Secure authentication
Relational databases
Vector databases
Microservice-style architecture

to build a practical AI-powered application.

👨‍💻 Author

Pranali Hemane
Software Developer
GitHub:Pranali200
LinkedIn: https://www.linkedin.com/in/pranali-hemane-557997248/


