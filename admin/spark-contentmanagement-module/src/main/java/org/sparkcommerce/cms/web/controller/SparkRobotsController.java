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
package org.sparkcommerce.cms.web.controller;

import org.sparkcommerce.cms.page.service.PageService;
import org.sparkcommerce.common.RequestDTO;
import org.sparkcommerce.common.TimeDTO;
import org.sparkcommerce.common.file.service.SparkFileUtils;
import org.sparkcommerce.common.page.dto.PageDTO;
import org.sparkcommerce.common.time.SystemTime;
import org.sparkcommerce.common.web.BaseUrlResolver;
import org.sparkcommerce.common.web.SparkRequestContext;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class serves up the Robots.txt file.    The default contents can be overridden by 
 * adding a Page named "/robots.txt" in the SC admin or DB. 
 *
 * @author bpolster
 */
public class SparkRobotsController {

    public static final String SC_RULE_MAP_PARAM = "blRuleMap";

    // The following attribute is set in SparkProcessURLFilter
    public static final String REQUEST_DTO = "blRequestDTO";

    @Resource(name = "blBaseUrlResolver")
    private BaseUrlResolver baseUrlResolver;

    @Resource(name = "blPageService")
    private PageService pageService;

    public String getRobotsFile(HttpServletRequest request, HttpServletResponse response) {
        SparkRequestContext context = SparkRequestContext.getSparkRequestContext();

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        PageDTO page = pageService.findPageByURI(null,
                "/robots.txt", buildMvelParameters(request), isSecure(request));

        if (page != null && page.getPageFields().containsKey("body")) {
            String body = page.getPageFields().get("body");
            body = body.replace("${siteBaseUrl}", baseUrlResolver.getSiteBaseUrl());
            return body;
        } else {
            return getDefaultRobotsTxt();
        }
    }
    
    public boolean isSecure(HttpServletRequest request) {
        boolean secure = false;
        if (request != null) {
             secure = ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
        }
        return secure;
    }

    /**
     * Used to produce a working but simple robots.txt.    Can be overridden in code or by defining a page
     * managed in the Spark CMS named  "/robots.txt"
     * 
     * @return
     */
    protected String getDefaultRobotsTxt() {
        StringBuilder sb = new StringBuilder();
        sb.append("# Using default Spark Commerce robots.txt file").append("\n");
        sb.append("User-agent: *").append("\n");
        sb.append("Disallow:").append("\n");
        String fileLoc = SparkFileUtils.appendUnixPaths(baseUrlResolver.getSiteBaseUrl(), "/sitemap.xml.gz");

        sb.append("Sitemap:").append(fileLoc);
        return sb.toString();
    }

    /**
    *
    * @param request
    * @return
    */
    private Map<String, Object> buildMvelParameters(HttpServletRequest request) {
        TimeDTO timeDto = new TimeDTO(SystemTime.asCalendar());
        RequestDTO requestDto = (RequestDTO) request.getAttribute(REQUEST_DTO);

        Map<String, Object> mvelParameters = new HashMap<String, Object>();
        mvelParameters.put("time", timeDto);
        mvelParameters.put("request", requestDto);

        Map<String, Object> blcRuleMap = (Map<String, Object>) request.getAttribute(SC_RULE_MAP_PARAM);
        if (blcRuleMap != null) {
            for (String mapKey : blcRuleMap.keySet()) {
                mvelParameters.put(mapKey, blcRuleMap.get(mapKey));
            }
        }

        return mvelParameters;
    }
}
