package com.savoidage.springbootdistributedtransaction.dao;

import com.savoidage.springbootdistributedtransaction.entity.LocalMqMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface LocalMqMessageDao {

    int insert(@Param("pojo") LocalMqMessage pojo);

    int insertSelective(@Param("pojo") LocalMqMessage pojo);

    int insertList(@Param("pojos") List<LocalMqMessage> pojo);

    int update(@Param("pojo") LocalMqMessage pojo);

    Integer updateLocalMqMessageById(Long messageId);

    List<LocalMqMessage> findMessageList();

    void updateMessageToFailed(Long messageId);

    void updateMessageRetryCount(Long messageId);

    LocalMqMessage findById(long messageId);
}
