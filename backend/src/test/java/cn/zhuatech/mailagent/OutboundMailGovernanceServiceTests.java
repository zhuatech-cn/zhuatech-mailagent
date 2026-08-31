/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.mailagent;
import cn.zhuatech.mailagent.service.OutboundMailGovernanceService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class OutboundMailGovernanceServiceTests {
    private final OutboundMailGovernanceService service = new OutboundMailGovernanceService();
    @Test void sendsControlledOutboundMail() {
        var r = service.evaluate(new OutboundMailGovernanceService.Request("MSG-001", 2, 2, false, true, false, true, true));
        assertEquals("SEND", r.decision()); assertTrue(r.sendAllowed());
    }
    @Test void blocksMailThatFailsSecurityControls() {
        var r = service.evaluate(new OutboundMailGovernanceService.Request("MSG-002", 3, 12, true, false, false, false, false));
        assertEquals("BLOCKED", r.decision()); assertEquals(3, r.blockers().size()); assertEquals(2, r.requiredApprovals().size());
    }
}
