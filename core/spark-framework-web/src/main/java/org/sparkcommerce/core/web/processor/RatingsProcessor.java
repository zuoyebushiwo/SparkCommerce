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
package org.sparkcommerce.core.web.processor;

import org.apache.commons.lang.StringUtils;
import org.sparkcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import org.sparkcommerce.core.rating.domain.RatingSummary;
import org.sparkcommerce.core.rating.domain.ReviewDetail;
import org.sparkcommerce.core.rating.service.RatingService;
import org.sparkcommerce.core.rating.service.type.RatingType;
import org.sparkcommerce.profile.core.domain.Customer;
import org.sparkcommerce.profile.web.core.CustomerState;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import javax.annotation.Resource;

/**
 * A Thymeleaf processor that will add the product ratings and reviews to the model
 *
 * @author jfridye
 */
public class RatingsProcessor extends AbstractModelVariableModifierProcessor {
    
    @Resource(name = "blRatingService")
    protected RatingService ratingService;

    /**
     * Sets the name of this processor to be used in Thymeleaf template
     *
     * NOTE: Thymeleaf normalizes the attribute names by converting all to lower-case
     * we will use the underscore instead of camel case to avoid confusion
     *
     */
    public RatingsProcessor() {
        super("ratings");
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected void modifyModelAttributes(Arguments arguments, Element element) {
        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue("itemId"));
        String itemId = String.valueOf(expression.execute(arguments.getConfiguration(), arguments));
        RatingSummary ratingSummary = ratingService.readRatingSummary(itemId, RatingType.PRODUCT);
        if (ratingSummary != null) {
            addToModel(arguments, getRatingsVar(element), ratingSummary);
        }
        
        Customer customer = CustomerState.getCustomer();
        ReviewDetail reviewDetail = null;
        if (!customer.isAnonymous()) {
            reviewDetail = ratingService.readReviewByCustomerAndItem(customer, itemId);
        }
        if (reviewDetail != null) {
            addToModel(arguments, "currentCustomerReview", reviewDetail);
        }
        
    }
    
    private String getRatingsVar(Element element) {
        String ratingsVar = element.getAttributeValue("ratingsVar");
        if (StringUtils.isNotEmpty(ratingsVar)) {
            return ratingsVar;
        } 
        return "ratingSummary";
    }

}
