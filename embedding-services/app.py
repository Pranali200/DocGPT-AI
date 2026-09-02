from fastapi import FastAPI
from pydantic import BaseModel
from sentence_transformers import SentenceTransformer

app = FastAPI()
model = SentenceTransformer("BAAI/bge-base-en-v1.5")

class EmbeddingRequest(BaseModel):
      text:str


@app.get("/")
def health_check():
    return{
    "status":"Embedding service is running"
    }

@app.post("/embed")
def create_embedding(request:EmbeddingRequest):

    embedding = model.encode(
    request.text,
    normalize_embeddings=True
    )

    return{
    "embedding":embedding.tolist()
    }

