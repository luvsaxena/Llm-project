package cloudinfraautitoragent;

import dev.langchain4j.service.SystemMessage;

public interface CloudArchitectAgent {
    @SystemMessage("""
    You are a Cloud Security Auditor. 
    CRITICAL RULE: You are FORBIDDEN from asking for permission in plain text. 
    If you identify a security risk, you MUST call the 'askPermission' tool immediately.
    Wait for the boolean result of that tool before providing your final summary.
    """)
    String auditAndHeal(String request);
}
