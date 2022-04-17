package com.liang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liang.dto.Result;
import com.liang.entity.Voucher;

public interface IVoucherService extends IService<Voucher> {
    Result queryVoucherOfShop(Long shopId);

    void addSeckillVoucher(Voucher voucher);
}
