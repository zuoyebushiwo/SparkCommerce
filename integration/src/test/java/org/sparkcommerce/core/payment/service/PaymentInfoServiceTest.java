/*
 * #%L
 * SparkCommerce Integration
 * %%
 * Copyright (C) 2009 - 2013 Spark Commerce
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.sparkcommerce.core.payment.service;

import org.sparkcommerce.common.payment.PaymentType;
import org.sparkcommerce.core.order.domain.Order;
import org.sparkcommerce.core.order.service.OrderService;
import org.sparkcommerce.core.payment.PaymentInfoDataProvider;
import org.sparkcommerce.core.payment.domain.OrderPayment;
import org.sparkcommerce.profile.core.dao.CustomerAddressDao;
import org.sparkcommerce.profile.core.domain.Address;
import org.sparkcommerce.profile.core.domain.Customer;
import org.sparkcommerce.profile.core.domain.CustomerAddress;
import org.sparkcommerce.profile.core.service.CustomerService;
import org.sparkcommerce.test.BaseTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.util.List;

import javax.annotation.Resource;

public class PaymentInfoServiceTest extends BaseTest {

    String userName = new String();
    private OrderPayment paymentInfo;

    @Resource
    private OrderPaymentService paymentInfoService;

    @Resource(name = "blOrderService")
    private OrderService orderService;

    @Resource
    private CustomerAddressDao customerAddressDao;

    @Resource
    private CustomerService customerService;

    @Test(groups={"createPaymentInfo"}, dataProvider="basicPaymentInfo", dataProviderClass=PaymentInfoDataProvider.class, dependsOnGroups={"readCustomer", "createOrder"})
    @Rollback(false)
    @Transactional
    public void createPayment(OrderPayment payment){
        userName = "customer1";
        Customer customer = customerService.readCustomerByUsername(userName);
        List<CustomerAddress> addresses = customerAddressDao.readActiveCustomerAddressesByCustomerId(customer.getId());
        Address address = null;
        if (!addresses.isEmpty())
            address = addresses.get(0).getAddress();
        Order salesOrder = orderService.createNewCartForCustomer(customer);

        payment.setBillingAddress(address);
        payment.setOrder(salesOrder);
        payment.setType(PaymentType.CREDIT_CARD);

        assert payment.getId() == null;
        payment = paymentInfoService.save(payment);
        assert payment.getId() != null;
        this.paymentInfo = payment;
    }

    @Test(groups={"readPaymentInfoById"}, dependsOnGroups={"createPaymentInfo"})
    public void readPaymentInfoById(){
        OrderPayment sop = paymentInfoService.readPaymentById(paymentInfo.getId());
        assert sop !=null;
        assert sop.getId().equals(paymentInfo.getId());
    }

    @Test(groups={"readPaymentInfosByOrder"}, dependsOnGroups={"createPaymentInfo"})
    @Transactional
    public void readPaymentInfoByOrder(){
        List<OrderPayment> payments = paymentInfoService.readPaymentsForOrder(paymentInfo.getOrder());
        assert payments != null;
        assert payments.size() > 0;
    }

    @Test(groups={"testCreatePaymentInfo"}, dependsOnGroups={"createPaymentInfo"})
    @Transactional
    public void createTestPayment(){
        userName = "customer1";
        OrderPayment paymentInfo = paymentInfoService.create();
        Customer customer = customerService.readCustomerByUsername(userName);
        List<CustomerAddress> addresses = customerAddressDao.readActiveCustomerAddressesByCustomerId(customer.getId());
        Address address = null;
        if (!addresses.isEmpty())
            address = addresses.get(0).getAddress();
        Order salesOrder = orderService.findCartForCustomer(customer);

        paymentInfo.setBillingAddress(address);
        paymentInfo.setOrder(salesOrder);
        paymentInfo.setType(PaymentType.CREDIT_CARD);

        assert paymentInfo != null;
        paymentInfo = paymentInfoService.save(paymentInfo);
        assert paymentInfo.getId() != null;
        Long paymentInfoId = paymentInfo.getId();
        paymentInfoService.delete(paymentInfo);
        paymentInfo = paymentInfoService.readPaymentById(paymentInfoId);
        assert paymentInfo == null;
    }

}
