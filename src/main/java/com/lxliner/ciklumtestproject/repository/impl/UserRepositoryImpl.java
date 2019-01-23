package com.lxliner.ciklumtestproject.repository.impl;

import com.lxliner.ciklumtestproject.domain.User;
import com.lxliner.ciklumtestproject.repository.UserRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public User findByLogin(String login) {
        try {
            return entityManager.createQuery( "from User where login = :login", User.class).setParameter("login", login).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }
}
