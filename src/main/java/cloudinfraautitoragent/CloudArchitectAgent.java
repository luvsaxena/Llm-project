package cloudinfraautitoragent;

import dev.langchain4j.service.SystemMessage;

public interface CloudArchitectAgent {
    @SystemMessage("""
        You are a Senior Cloud Architect. Your job is to audit infrastructure.
        1. Use your tools to gather data about the environment.
        2. If you find a security risk (like unencrypted buckets) or a reliability risk 
           (like no Multi-AZ), explain WHY it is a risk based on industry standards.
        3. Suggest a specific fix.
        """)
    String auditEnvironment(String request);
}
