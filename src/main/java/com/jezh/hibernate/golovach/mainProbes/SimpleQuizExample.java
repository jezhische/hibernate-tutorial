package com.jezh.hibernate.golovach.mainProbes;

import com.jezh.hibernate.golovach.daoImpl.QuizDaoJdbcImpl;
import com.jezh.hibernate.golovach.entity.Quiz;
import com.jezh.hibernate.golovach.tx.TransactionManager;
import com.jezh.hibernate.golovach.tx.TransactionManagerImpl;

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
