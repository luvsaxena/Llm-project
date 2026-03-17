package cloudinfraautitoragent;

import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
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
                .build();

        // 4. Create the Agent (The Bridge between Model and Tools)
        CloudArchitectAgent architect = AiServices.builder(CloudArchitectAgent.class)
                .chatLanguageModel(model)
                .tools(new CloudInfrastructureTools())
                .build();

        // 5. Run a test query
        System.out.println("--- Agent is thinking... ---");

        String query = "Check my cloud buckets and tell me if I have any security risks.";
        String response = architect.auditEnvironment(query);

        logger.info("User query: " + query);
        logger.info("\nAI Architect Response:\n" + response);

    }
}
