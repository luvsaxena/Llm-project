package cloudinfraautitoragent;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloudAgent {

    private static final Logger logger = LoggerFactory.getLogger(CloudAgent.class);

    public static void main(String[] args) {
        // 3. Initialize the Gemini Model
        GoogleAiGeminiChatModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(System.getenv("GEMINI_API_KEY")) // Replace with your real key
                .modelName("gemini-2.5-flash")
                .maxRetries(3)
                .build();

//        ChatLanguageModel model = OllamaChatModel.builder()
//                .baseUrl("http://localhost:11434") // Local address
//                .modelName("llama3.1")             // Model name jo aapne run kiya
//                .temperature(0.0)
//                .build();

        // 4. Create the Agent (The Bridge between Model and Tools)
        CloudArchitectAgent architect = AiServices.builder(CloudArchitectAgent.class)
                .chatLanguageModel(model)
                .tools(new CloudInfrastructureTools())
                .build();

        // 5. Run a test query
        logger.info("--- Agent is thinking... ---");

        String query = "Check my cloud buckets and tell me if I have any security risks.";
        String response = architect.auditAndHeal(query);

        logger.info("User query: " + query);
        logger.info("\nAI Architect Response:\n" + response);

    }
}
