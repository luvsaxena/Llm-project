package Rag;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RAGProject {

    static Logger logger = LoggerFactory.getLogger(RAGProject.class);

    // 1. Interface define karein (AI Service)
    interface Assistant {
        @SystemMessage({
                "You are a helpful assistant.",
                "Use ONLY the provided context to answer.",
                "If the answer isn't there, say you don't know."
        })
        String chat(String message);
    }

    public static void main(String[] args) {

        // 1. Local LLM Setup (Ollama default port 11434 par chalta hai)
        OllamaChatModel model = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.2")
                .build();

        // 2. Local Embedding Model Setup
        OllamaEmbeddingModel embeddingModel = OllamaEmbeddingModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("nomic-embed-text")
                .build();

        // 3. Vector Store (In-memory hi rakhte hain filhal)
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // 4. Ingest: Local data store karna
        TextSegment segment = TextSegment.from("Luv Saxena is a Senior Software Architect with 12.5 years of professional experience. He lives and works in Bangalore.");
        embeddingStore.add(embeddingModel.embed(segment).content(), segment);

        // 5. Retriever & AI Service
        ContentRetriever retriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build();

        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(model)
                .contentRetriever(retriever)
                .build();

        logger.info(assistant.chat("Who is Luv Saxena?"));

    }
}
