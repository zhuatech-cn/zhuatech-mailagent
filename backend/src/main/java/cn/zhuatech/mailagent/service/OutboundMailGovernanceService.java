/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.mailagent.service;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
@Service
public class OutboundMailGovernanceService {
    public Result evaluate(Request request) {
        List<String> blockers = new ArrayList<>();
        List<String> approvals = new ArrayList<>();
        if (!request.malwareScanPassed()) blockers.add("附件恶意代码扫描未通过");
        if (!request.dlpPassed()) blockers.add("邮件 DLP 检查未通过");
        if (!request.retentionLabelApplied()) blockers.add("未应用邮件保留标签");
        if (request.containsSensitiveData() && request.externalRecipients() > 0 && !request.humanApproved())
            approvals.add("敏感信息外发需要人工批准");
        if (request.attachmentCount() > 10) approvals.add("附件数量超过 10 个，需要复核");
        String decision = !blockers.isEmpty() ? "BLOCKED" : !approvals.isEmpty() ? "APPROVAL_REQUIRED" : "SEND";
        return new Result(request.messageId(), decision, List.copyOf(blockers),
                List.copyOf(approvals), "SEND".equals(decision));
    }
    public record Request(@NotBlank String messageId, @Min(0) int externalRecipients,
                          @Min(0) int attachmentCount, boolean containsSensitiveData,
                          boolean dlpPassed, boolean humanApproved,
                          boolean retentionLabelApplied, boolean malwareScanPassed) {
        public Request {
            if (messageId == null || messageId.isBlank()) throw new IllegalArgumentException("messageId is required");
            if (externalRecipients < 0 || attachmentCount < 0) throw new IllegalArgumentException("counts must be non-negative");
        }
    }
    public record Result(String messageId, String decision, List<String> blockers,
                         List<String> requiredApprovals, boolean sendAllowed) {}
}
