package com.liang.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liang.dto.Result;
import com.liang.entity.SeckillVoucher;
import com.liang.entity.User;
import com.liang.entity.Voucher;
import com.liang.mapper.SeckillVoucherMapper;
import com.liang.mapper.VoucherMapper;
import com.liang.service.ISeckillVoucherService;
import com.liang.service.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import static com.liang.utils.RedisConstants.*;

import javax.annotation.Resource;


@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements IVoucherService {

    @Autowired
    private SeckillVoucherServiceImpl seckillVoucherService;
    @Resource
    private  StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryVoucherOfShop(Long shopId) {
        return null;
    }

    public void addSeckillVoucher(Voucher voucher){
        save(voucher);
        //User user = query().eq("phone", phone).one();//mybatis-plus的用法把
        SeckillVoucher seckillVoucher=new SeckillVoucher();
        seckillVoucher.setVoucherId(voucher.getId());
        seckillVoucher.setStock(voucher.getStock());
        seckillVoucher.setBeginTime(voucher.getBeginTime());
        seckillVoucher.setEndTime(voucher.getEndTime());
        seckillVoucherService.save(seckillVoucher);
        // 保存秒杀库存到Redis中
        stringRedisTemplate.opsForValue().set(SECKILL_STOCK_KEY + voucher.getId(), voucher.getStock().toString());

    }
}
