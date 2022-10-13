package com.alibaba.nacos.config.server.service.repository.dialect;

import com.alibaba.nacos.config.server.configuration.ConditionOnExternalStorage;
import com.alibaba.nacos.config.server.model.ConfigInfo;
import com.alibaba.nacos.config.server.service.repository.PersistService;
import com.alibaba.nacos.config.server.service.repository.extrnal.ExternalStoragePaginationHelperImpl;
import com.alibaba.nacos.config.server.utils.LogUtil;
import com.alibaba.nacos.multidatasource.condition.ConditionOnExternalPostgresqlStorage;
import org.springframework.context.annotation.Conditional;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

@SuppressWarnings(value = {"PMD.MethodReturnWrapperTypeRule", "checkstyle:linelength"})
@Conditional(value = {ConditionOnExternalStorage.class, ConditionOnExternalPostgresqlStorage.class})
@Component
public class PostgresqlExternalStoragePersistServiceImpl extends DefaultDialectExternalStoragePersistServiceImpl implements PersistService {

    /**
     * 清除配置历史彪
     * @param startTime start time 开始时间
     * @param limitSize limit size 结束时间
     */
    @Override
    public void removeConfigHistory(final Timestamp startTime, final int limitSize) {
        //to do develop limit control
        String sql = "DELETE FROM his_config_info WHERE gmt_modified < ? ";
        ExternalStoragePaginationHelperImpl<ConfigInfo> paginationHelper = (ExternalStoragePaginationHelperImpl<ConfigInfo>) createPaginationHelper();
        int count;
        try {
            count = paginationHelper.updateLimitWithResponse(sql, new Object[] {startTime});
            while (count > 0) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    LogUtil.FATAL_LOG.error("[interrupt-error] " + e, e);
                }
                count = paginationHelper.updateLimitWithResponse(sql, new Object[] {startTime});
            }
        } catch (CannotGetJdbcConnectionException e) {
            LogUtil.FATAL_LOG.error("[db-error] " + e, e);
            throw e;
        }
    }

}
