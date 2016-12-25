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
package org.apache.camel.idea;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.intellij.codeInsight.lookup.AutoCompletionPolicy;
import com.intellij.codeInsight.lookup.LookupElementBuilder;

public class CamelSmartCompletionEndpointOptions {

    public static List<Object> addSmartCompletionSuggestions(String val, List<Map<String, String>> rows, Map<String, String> existing) {
        List<Object> answer = new ArrayList<>();

        for (Map<String, String> row : rows) {
            String name = row.get("name");
            // should be uri parameters
            String kind = row.get("kind");
            String deprecated = row.get("deprecated");
            String group = row.get("group");
            String javaType = row.get("javaType");
            if ("parameter".equals(kind)) {
                // only add if not already used
                if (existing == null || !existing.containsKey(name)) {
                    // the lookup should prepare for the new option
                    String lookup;
                    if (existing == null) {
                        // none existing options so we need to start with a ? mark
                        lookup = val + "?" + name + "=";
                    } else {
                        lookup = val + "&" + name + "=";
                    }
                    LookupElementBuilder builder = LookupElementBuilder.create(lookup);
                    // only show the option in the UI
                    builder = builder.withPresentableText(name);
                    // we don't want to highlight the advanced options which should be more seldom in use
                    boolean advanced = group != null && group.contains("advanced");
                    builder = builder.withBoldness(!advanced);
                    if (javaType != null) {
                        builder = builder.withTypeText(javaType, true);
                    }
                    if ("true".equals(deprecated)) {
                        // mark as deprecated
                        builder = builder.withStrikeoutness(true);
                    }
                    // TODO: we could nice with an icon for producer vs consumer etc or for secret, or required
                    answer.add(builder.withAutoCompletionPolicy(AutoCompletionPolicy.GIVE_CHANCE_TO_OVERWRITE));
                }
            }
        }

        return answer;
    }

}
