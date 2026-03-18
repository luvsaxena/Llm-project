package cloudinfraautitoragent;

import dev.langchain4j.agent.tool.Tool;

import java.util.List;
import java.util.Scanner;

public class CloudInfrastructureTools {

    @Tool("Fetches a list of all S3 buckets and their encryption status")
    public List<String> checkS3Buckets() {
        // In a real scenario, use AWS SDK here: s3Client.listBuckets()
        return List.of("prod-data-bucket: Encrypted", "public-assets: Unencrypted");
    }

    @Tool("Checks if a specific service has Multi-AZ deployment enabled")
    public boolean isMultiAzEnabled(String serviceName) {
        // Logic to check RDS or EC2 configuration
        return false;
    }

    // HITL Verification Tool
    @Tool("Call this EXACT method to ask the human for permission to fix a bucket. " +
            "Input: A description of the fix. Output: true if allowed, false if denied.")
    public boolean askPermission(String actionDescription) {
        System.out.println("\n[ACTION REQUIRED]: The Agent wants to: " + actionDescription);
        System.out.print("Do you approve this action? (yes/no): ");

        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine();

        return response.equalsIgnoreCase("yes");
    }

    // Remediation Tool
    @Tool("Enables server-side encryption for a specific S3 bucket")
    public String enableS3Encryption(String bucketName) {
        // Real-world: Use AWS/Azure SDK
        return "SUCCESS: Encryption enabled for " + bucketName;
    }
}
