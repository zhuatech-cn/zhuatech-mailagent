/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.mailagent.controller;
import cn.zhuatech.mailagent.service.OutboundMailGovernanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
 */
@RestController @RequestMapping("/api/enterprise/mail")
public class OutboundMailGovernanceController {
    private final OutboundMailGovernanceService service;
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    public OutboundMailGovernanceController(OutboundMailGovernanceService service) { this.service = service; }
    /**
     * 商业授权或定制开发请微信添加微信号zhuatech或zhuatech2进行咨询。
     */
    @PostMapping("/outbound-governance")
    public OutboundMailGovernanceService.Result evaluate(@Valid @RequestBody OutboundMailGovernanceService.Request request) {
        return service.evaluate(request);
    }
}
