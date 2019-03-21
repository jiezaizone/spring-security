package com.isuwang.security.core.proxy;

import com.github.dapeng.core.InvocationContextImpl;
import com.github.dapeng.core.helper.DapengUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Soa Header Proxy
 *
 * @author craneding
 * @date 16/3/15
 */
@Component
public class SoaHeaderProxy implements InvocationContextImpl.InvocationContextProxy {
    @Autowired
    private HttpServletRequest request; //通过注入获取到request

    public void init() {
        InvocationContextImpl.Factory.setInvocationContextProxy(this);
    }

    public void destroy() {
    }

    @Override
    public Optional<String> callerFrom() {
        return Optional.of("security");
    }

    @Override
    public Optional<Integer> customerId() {
//        TCustomer customer = CustomerUtil.queryCustomerByToken(request);
//        if (customer != null) {
//            return Optional.of(customer.getId());
//        }

        return Optional.empty();
    }

    @Override
    public Optional<String> customerName() {
//        TCustomer customer = CustomerUtil.queryCustomerByToken(request);
//        if (customer != null) {
//            return Optional.of(customer.getName());
//        }

        return Optional.empty();
    }

    @Override
    public Optional<Long> sessionTid() {
        return Optional.ofNullable(DapengUtil.generateTid());
    }

    @Override
    public Optional<Integer> userIp() {
        return Optional.empty();
    }

    @Override
    public Optional<Long> userId() {
        return Optional.empty();
    }

    @Override
    public Optional<Integer> operatorId() {
        return Optional.empty();
    }

    @Override
    public Optional<String> operatorName() {
        return Optional.empty();
    }

    @Override
    public Optional<String> sessionId() {
        return Optional.empty();
    }

    @Override
    public Optional<String> callerMid() {
        return Optional.empty();
    }

    @Override
    public Map<String, String> cookies() {
        return new HashMap<>();
    }
}
