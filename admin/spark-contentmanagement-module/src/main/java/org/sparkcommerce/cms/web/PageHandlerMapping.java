/*
 * #%L
 * SparkCommerce CMS Module
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
package org.sparkcommerce.cms.web;

import org.sparkcommerce.cms.page.service.PageService;
import org.sparkcommerce.cms.web.controller.SparkPageController;
import org.sparkcommerce.common.RequestDTO;
import org.sparkcommerce.common.TimeDTO;
import org.sparkcommerce.common.page.dto.NullPageDTO;
import org.sparkcommerce.common.page.dto.PageDTO;
import org.sparkcommerce.common.time.SystemTime;
import org.sparkcommerce.common.web.SCAbstractHandlerMapping;
import org.sparkcommerce.common.web.SparkRequestContext;
import org.springframework.beans.factory.annotation.Value;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * This handler mapping works with the Page entity to determine if a page has been configured for
 * the passed in URL.   
 * 
 * If the URL represents a valid PageUrl, then this mapping returns 
 * 
 * Allows configuration of the controller name to use if a Page was found.
 *
 * @author bpolster
 * @since 2.0
 * @see org.sparkcommerce.cms.page.domain.Page
 * @see SparkPageController
 */
public class PageHandlerMapping extends SCAbstractHandlerMapping {
    
    private final String controllerName="blPageController";
    public static final String SC_RULE_MAP_PARAM = "blRuleMap";

    // The following attribute is set in SparkProcessURLFilter
    public static final String REQUEST_DTO = "blRequestDTO";
    
    @Resource(name = "blPageService")
    private PageService pageService;
    
    public static final String PAGE_ATTRIBUTE_NAME = "SC_PAGE";

    @Value("${request.uri.encoding}")
    public String charEncoding;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        SparkRequestContext context = SparkRequestContext.getSparkRequestContext();
        if (context != null && context.getRequestURIWithoutContext() != null) {
            String requestUri = URLDecoder.decode(context.getRequestURIWithoutContext(), charEncoding);
            PageDTO page = pageService.findPageByURI(context.getLocale(), requestUri, buildMvelParameters(request), context.isSecure());

            if (page != null && ! (page instanceof NullPageDTO)) {
                context.getRequest().setAttribute(PAGE_ATTRIBUTE_NAME, page);
                return controllerName;
            }
        }
        return null;
    }
    
     /**
     * MVEL is used to process the content targeting rules.
     *
     *
     * @param request
     * @return
     */
    private Map<String,Object> buildMvelParameters(HttpServletRequest request) {
        TimeDTO timeDto = new TimeDTO(SystemTime.asCalendar());
        RequestDTO requestDto = (RequestDTO) request.getAttribute(REQUEST_DTO);

        Map<String, Object> mvelParameters = new HashMap<String, Object>();
        mvelParameters.put("time", timeDto);
        mvelParameters.put("request", requestDto);

        Map<String,Object> blcRuleMap = (Map<String,Object>) request.getAttribute(SC_RULE_MAP_PARAM);
        if (blcRuleMap != null) {
            for (String mapKey : blcRuleMap.keySet()) {
                mvelParameters.put(mapKey, blcRuleMap.get(mapKey));
            }
        }

        return mvelParameters;
    }
}
