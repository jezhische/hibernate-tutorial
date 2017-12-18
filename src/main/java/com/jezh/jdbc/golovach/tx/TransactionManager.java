package com.jezh.jdbc.golovach.tx;

import java.util.concurrent.Callable;

public interface TransactionManager {
    <T> T doInTransaction(Callable<T> unitOfWork) throws Exception;
}
