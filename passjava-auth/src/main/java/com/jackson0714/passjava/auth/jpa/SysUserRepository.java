package com.jackson0714.passjava.auth.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统用户 repository
 *
 * @author 悟空聊架构
 * @site www.passjava.cn
 * @date 2022-08-09
 */
@Repository("SysUserRepository")
public interface SysUserRepository extends JpaRepository<SysUser,Long> {

    /**
     * 注意这个方法的名称，JPA 会根据方法名自动生成 SQL 执行，完全不用自己写SQL
     *
     * @param userId 用户姓名
     * @return SysUser
     */
    SysUser findByUserId(String userId);
}
