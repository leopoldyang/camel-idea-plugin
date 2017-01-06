/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.idea.catalog;

import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.apache.camel.idea.util.CamelService;


/**
 * Test the Camel Catalog is instantiated when camel is present
 */
public class CamelCatalogServiceTestIT extends LightCodeInsightFixtureTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ServiceManager.getService(myModule.getProject(), CamelService.class).setCamelPresent(false);
    }

    @Override
    protected String getTestDataPath() {
        return "src/test/resources/testData/";
    }

    public void testNoCatalogInstance() {
        myFixture.configureByFiles("CompleteJavaEndpointConsumerTestData.java", "CompleteYmlPropertyTestData.java",
             "CompleteJavaPropertyTestData.properties", "CompleteYmlPropertyTestData.java", "CompleteYmlPropertyTestData.yml");
        myFixture.complete(CompletionType.BASIC, 1);
        assertEquals(false, ServiceManager.getService(myModule.getProject(), CamelCatalogService.class).isInstantiated());
    }

    //TODO : Figure out how we can make a clean run between test cases and remove the CamelCatalogService service instance
   /* public void testCatalogInstance() {
        ServiceManager.getService(myModule.getProject(), CamelService.class).setCamelPresent(true);
        myFixture.configureByFiles("CompleteJavaEndpointConsumerTestData.java", "CompleteYmlPropertyTestData.java",
            "CompleteJavaPropertyTestData.properties", "CompleteYmlPropertyTestData.java", "CompleteYmlPropertyTestData.yml");
        myFixture.complete(CompletionType.BASIC, 1);
        assertEquals(true, ServiceManager.getService(myModule.getProject(), CamelCatalogService.class).isInstantiated());
    }*/

}