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
package org.sparkcommerce.cms.web.deeplink;

import org.sparkcommerce.cms.structure.domain.StructuredContent;
import org.sparkcommerce.common.structure.dto.StructuredContentDTO;
import org.sparkcommerce.common.web.deeplink.DeepLink;
import org.sparkcommerce.common.web.deeplink.DeepLinkService;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides deep links for {@link StructuredContent} items.
 * 
 * @author Andre Azzolini (apazzolini)
 */
public class ContentDeepLinkServiceImpl extends DeepLinkService<StructuredContentDTO> {

    protected String structuredContentAdminPath;

    @Override
    protected List<DeepLink> getLinksInternal(StructuredContentDTO item) {
        List<DeepLink> links = new ArrayList<DeepLink>();

        links.add(new DeepLink()
            .withAdminBaseUrl(getAdminBaseUrl())
            .withUrlFragment(structuredContentAdminPath + item.getId())
            .withDisplayText("Edit")
            .withSourceObject(item));

        return links;
    }

    public String getStructuredContentAdminPath() {
        return structuredContentAdminPath;
    }

    public void setStructuredContentAdminPath(String structuredContentAdminPath) {
        this.structuredContentAdminPath = structuredContentAdminPath;
    }

}
