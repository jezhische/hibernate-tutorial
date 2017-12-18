package com.jezh.jdbc.golovach.mainProbes;

import com.jezh.jdbc.golovach.daoImpl.QuizDaoJdbcImpl;
import com.jezh.jdbc.golovach.entity.Quiz;
import com.jezh.jdbc.golovach.tx.TransactionManager;
import com.jezh.jdbc.golovach.tx.TransactionManagerImpl;

import java.util.List;
import java.util.concurrent.Callable;

public class SimpleQuizExample {

    public static void main(String[] args) throws Exception {

        final QuizDaoJdbcImpl quizDao = new QuizDaoJdbcImpl();
//        quizDao.setQuestionDao(new QuestionDaoJdbcImpl());
        TransactionManager txManager = new TransactionManagerImpl();

        List<Quiz> quizList = txManager.doInTransaction(new Callable<List<Quiz>>() {
            @Override
            public List<Quiz> call() throws Exception {
                // there can be many calls of database here (kind of selectAll() etc)
                return null /*quizDao.selectAll()*/;
            }
        });
    }
}
