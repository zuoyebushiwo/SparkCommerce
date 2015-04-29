/*
 * #%L
 * SparkCommerce Framework Web
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
package org.sparkcommerce.core.web.controller.account;

import org.sparkcommerce.common.exception.ServiceException;
import org.sparkcommerce.common.security.util.PasswordChange;
import org.sparkcommerce.common.web.controller.SparkAbstractController;
import org.sparkcommerce.core.web.controller.account.validator.ChangePasswordValidator;
import org.sparkcommerce.profile.core.service.CustomerService;
import org.sparkcommerce.profile.web.core.CustomerState;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * This controller handles password changes for a customer's account
 */
public class SparkChangePasswordController extends SparkAbstractController {

    @Resource(name = "blCustomerService")
    protected CustomerService customerService;
    @Resource(name = "blChangePasswordValidator")
    protected ChangePasswordValidator changePasswordValidator;

    protected String passwordChangedMessage = "Password successfully changed";
    
    protected static String changePasswordView = "account/changePassword";
    protected static String changePasswordRedirect = "redirect:/account/password";

    public String viewChangePassword(HttpServletRequest request, Model model) {
        return getChangePasswordView();
    }

    public String processChangePassword(HttpServletRequest request, Model model, ChangePasswordForm form, BindingResult result, RedirectAttributes redirectAttributes) throws ServiceException {
        PasswordChange passwordChange = new PasswordChange(CustomerState.getCustomer().getUsername());
        passwordChange.setCurrentPassword(form.getCurrentPassword());
        passwordChange.setNewPassword(form.getNewPassword());
        passwordChange.setNewPasswordConfirm(form.getNewPasswordConfirm());
        changePasswordValidator.validate(passwordChange, result);
        if (result.hasErrors()) {
            return getChangePasswordView();
        }
        customerService.changePassword(passwordChange);
        return getChangePasswordRedirect();
    }

    public String getChangePasswordView() {
        return changePasswordView;
    }
    
    public String getChangePasswordRedirect() {
        return changePasswordRedirect;
    }
    
    public String getPasswordChangedMessage() {
        return passwordChangedMessage;
    }
    
}
