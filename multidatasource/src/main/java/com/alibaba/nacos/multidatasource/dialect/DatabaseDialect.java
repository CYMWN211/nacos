/*
 * Copyright 1999-2022 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.multidatasource.dialect;

/**
 * DatabaseDialect interface.
 * @author Minsk.Chen
 */
public interface DatabaseDialect {

    /**
     * get database type.
     * @return return database type name
     */
    String getType();

    /**
     * get test query connect sql.
     * @return query connect sql
     */
    String getTestQuery();

    /**
     * get first index page param.
     * @param page current pageNo
     * @param pageSize current pageSize
     * @return offset val or maxRange
     */
    int getPagePrevNum(int page, int pageSize);

    /**
     * get second index page param.
     * @param page current pageNo
     * @param pageSize current pageSize
     * @return limit val or minRange
     */
    int getPageLastNum(int page, int pageSize);

    /**
     * get page limit top data sql,contain  plactholder.
     * @param sql orign sql
     * @return append limit sql
     */
    String getLimitTopSql(String sql);

    /**
     * get page limit page data sql,contain  plactholder.
     * @param sql origin sql
     * @return append limit sql
     */
    String getLimitPageSql(String sql);

    /**
     * get page limit page data sql,using number.
     * @param sql origin sql
     * @param pageNo current pageNo
     * @param pageSize current pageSize
     * @return contain page number param sql
     */
    String getLimitPageSql(String sql, int pageNo, int pageSize);

    /**
     * get database return primary keys.
     * @return
     */
    String[] getReturnPrimaryKeys();

}
