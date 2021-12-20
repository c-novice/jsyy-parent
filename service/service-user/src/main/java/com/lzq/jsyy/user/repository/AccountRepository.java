package com.lzq.jsyy.user.repository;

import com.lzq.jsyy.model.user.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lzq
 */
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    /**
     * 根据学号、密码查询校园信息
     *
     * @param studentNumber
     * @param password
     * @return
     */
    Account getAccountByStudentNumberAndPassword(String studentNumber, String password);
}
