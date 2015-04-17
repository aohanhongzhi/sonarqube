/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2014 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.server.es;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.elasticsearch.common.joda.time.format.ISODateTimeFormat;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.sonar.server.search.BaseDoc;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;
import java.util.*;

public class EsUtils {

  private EsUtils() {
    // only static methods
  }

  public static <D extends BaseDoc> List<D> convertToDocs(SearchHits hits, Function<Map<String, Object>, D> converter) {
    List<D> docs = new ArrayList<>();
    for (SearchHit hit : hits.getHits()) {
      docs.add(converter.apply(hit.getSource()));
    }
    return docs;
  }

  public static LinkedHashMap<String, Long> termsToMap(Terms terms) {
    LinkedHashMap<String, Long> map = new LinkedHashMap<>();
    List<Terms.Bucket> buckets = terms.getBuckets();
    for (Terms.Bucket bucket : buckets) {
      map.put(bucket.getKey(), bucket.getDocCount());
    }
    return map;
  }

  public static List<String> termsKeys(Terms terms) {
    return Lists.transform(terms.getBuckets(), new Function<Terms.Bucket, String>() {
      @Override
      public String apply(Terms.Bucket bucket) {
        return bucket.getKey();
      }
    });
  }

  @CheckForNull
  public static Date parseDateTime(@Nullable String s) {
    if (s != null) {
      return ISODateTimeFormat.dateTime().parseDateTime(s).toDate();
    }
    return null;
  }

  @CheckForNull
  public static String formatDateTime(@Nullable Date date) {
    if (date != null) {
      return ISODateTimeFormat.dateTime().print(date.getTime());
    }
    return null;
  }

}
