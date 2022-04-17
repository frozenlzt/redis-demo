package com.liang.controller;

import com.liang.dto.Result;
import com.liang.entity.Voucher;
import com.liang.service.Impl.VoucherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("")
public class VoucherController {
    @Autowired
    private VoucherServiceImpl voucherService;
    public Result addSeckillVoucher(@RequestBody Voucher voucher){
        voucherService.addSeckillVoucher(voucher);
        return Result.ok(voucher.getId());
    }
}
