package com.jezh.hibernate.golovach.tx;

import java.util.concurrent.Callable;

public interface TransactionManager {
    <T> T doInTransaction(Callable<T> unitOfWork) throws Exception;
}
