/**
 * Copyright (c) 2020 kedacom
 * OpenATC is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 **/
package com.openatc.core.common;

import com.github.filosganga.geogson.gson.GeometryAdapterFactory;
import com.github.filosganga.geogson.jts.JtsAdapterFactory;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Configuration
public class CustomGsonMessageConverter extends GsonHttpMessageConverter {

  private static GsonBuilder builder = new GsonBuilder();

  CustomGsonMessageConverter() {

    //builder.registerTypeAdapter(OffsetDateTime.class, new OffseOffsetDateTimeTypeConverter());
    builder.registerTypeAdapterFactory(new JtsAdapterFactory());
    builder.registerTypeAdapterFactory(new GeometryAdapterFactory());
    builder.registerTypeAdapter(Double.class, new DoubleJsonToGsonAdapter());
    builder.registerTypeAdapter(Integer.class,new IntJsonToGsonAdapter());
    builder.setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    super.setGson(builder.create());
  }
}
