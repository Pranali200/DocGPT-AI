FROM python:3.10-slim

WORKDIR /app

# Install dependencies first (better Docker layer caching)
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the embedding service code
COPY . .

# Cloud Run injects the PORT env var (usually 8080) — the app MUST listen on it,
# not a hardcoded port like 8001 (that was fine locally, not here)
ENV PORT=8080
EXPOSE 8080

# Adjust "app.main:app" if your actual entrypoint differs
CMD exec uvicorn app:app --host 0.0.0.0 --port ${PORT}
