package com.example.o2o.dao.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;


/**
 * Interceptor: to intercept mybatis sql statements. to determine
 * Slave/Master interceptor
 * It should extends Mybatis Interceptor.
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //true if the method is transactional
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
        Object[] objects = invocation.getArgs();
        // the first arguments are insert, select ,delete,...
        MappedStatement ms = (MappedStatement) objects[0];
        String lookupKey = DynamicDataSourceHolder.DB_MASTER;
        if (!synchronizationActive) {
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                // if use generated primary key, should use master server cos next operation
                // will use this primary key to update other info
                if (ms.getId().contains((SelectKeyGenerator.SELECT_KEY_SUFFIX))) {
                    lookupKey = DynamicDataSourceHolder.DB_MASTER;
                } else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    // sql statement compressed and for the convenience of Regular Express
                    // Matching.
                    String sql = boundSql.getSql().toLowerCase(Locale.CANADA)
                            .replace("[\\t\\n\\r]", " ");
                    if (sql.matches(REGEX)) {
                        lookupKey = DynamicDataSourceHolder.DB_MASTER;
                    } else {
                        lookupKey = DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        } else {
            lookupKey = DynamicDataSourceHolder.DB_MASTER;
        }

        logger.debug("Setting Method[{}] use[{}] Stratege, SqlCommandType [{}]..",
                ms.getId(), lookupKey, ms.getSqlCommandType().name());

        // set up the MySQL Server  type
        DynamicDataSourceHolder.setDbType(lookupKey);

        // continue to process the sql statement
        return invocation.proceed();

    }

    /**
            * Return object or its proxy after intercepting.
     * Only intercept Executor object(CRUD SQL info);
     *
             * @param target
     * @return
             */
    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) return Plugin.wrap(target, this);
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
