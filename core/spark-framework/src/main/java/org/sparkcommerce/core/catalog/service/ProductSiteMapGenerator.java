/*
 * #%L
 * SparkCommerce Framework
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
package org.sparkcommerce.core.catalog.service;

import org.sparkcommerce.common.file.service.SparkFileUtils;
import org.sparkcommerce.common.sitemap.domain.SiteMapGeneratorConfiguration;
import org.sparkcommerce.common.sitemap.service.SiteMapBuilder;
import org.sparkcommerce.common.sitemap.service.SiteMapGenerator;
import org.sparkcommerce.common.sitemap.service.type.SiteMapGeneratorType;
import org.sparkcommerce.common.sitemap.wrapper.SiteMapURLWrapper;
import org.sparkcommerce.core.catalog.dao.ProductDao;
import org.sparkcommerce.core.catalog.domain.Product;
import org.hibernate.tool.hbm2x.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

/**
 * Responsible for generating site map entries for Product.
 * 
 * @author Joshua Skorton (jskorton)
 */
@Component("blProductSiteMapGenerator")
public class ProductSiteMapGenerator implements SiteMapGenerator {

    @Resource(name = "blProductDao")
    protected ProductDao productDao;

    @Value("${product.site.map.generator.row.limit}")
    protected int pageSize;

    @Override
    public boolean canHandleSiteMapConfiguration(SiteMapGeneratorConfiguration siteMapGeneratorConfiguration) {
        return SiteMapGeneratorType.PRODUCT.equals(siteMapGeneratorConfiguration.getSiteMapGeneratorType());
    }

    @Override
    public void addSiteMapEntries(SiteMapGeneratorConfiguration smgc, SiteMapBuilder siteMapBuilder) {

        int pageNum = 0;
        List<Product> products;

        do {
            products = productDao.readAllActiveProducts(pageNum++, pageSize);
            for (Product product : products) {
                if (StringUtils.isEmpty(product.getUrl())) {
                    continue;
                }

                SiteMapURLWrapper siteMapUrl = new SiteMapURLWrapper();

                // location
                siteMapUrl.setLoc(generateUri(siteMapBuilder, product));

                // change frequency
                siteMapUrl.setChangeFreqType(smgc.getSiteMapChangeFreq());

                // priority
                siteMapUrl.setPriorityType(smgc.getSiteMapPriority());

                // lastModDate
                siteMapUrl.setLastModDate(generateDate(product));

                siteMapBuilder.addUrl(siteMapUrl);
            }
        } while (products.size() == pageSize);

    }

    protected String generateUri(SiteMapBuilder smb, Product product) {
        return SparkFileUtils.appendUnixPaths(smb.getBaseUrl(), product.getUrl());
    }

    protected Date generateDate(Product product) {
        return new Date();
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
