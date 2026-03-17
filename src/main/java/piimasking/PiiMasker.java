package piimasking;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public class PiiMasker {

    private static final String API_KEY = "AIzaSyAE2fwfUM-ZHvMAEWvBt5rJLUjxVk6Tn54";

    interface MaskingService {
        @SystemMessage("""
            You are a privacy expert. Mask all PII in the user's text 
            using tags like [NAME], [PHONE], and [EMAIL]. 
            Keep the rest of the sentence exactly the same.
            """)
        String mask(@UserMessage String text);
    }

    public static void main(String[] args) {
        // Model setup (Gemini, OpenAI, etc.)
//        OpenAiChatModel model = OpenAiChatModel.withApiKey(API_KEY);
        ChatLanguageModel model = GoogleAiGeminiChatModel.builder()
                .apiKey(API_KEY) // Google AI Studio se lein
                .modelName("gemini-2.5-flash")
                .build();

        MaskingService service = AiServices.create(MaskingService.class, model);

        String input = "Mera naam Rahul hai aur mera phone number 9876543210 hai.";
        String result = service.mask(input);

        System.out.println("Original: " + input);
        System.out.println("Masked: " + result);
    }
}