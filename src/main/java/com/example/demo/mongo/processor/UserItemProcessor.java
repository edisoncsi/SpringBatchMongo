package com.example.demo.mongo.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.example.demo.mongo.domain.User;
import com.example.demo.mongo.model.UserDetail;

public class UserItemProcessor implements ItemProcessor<UserDetail, User>  {
	private static final Logger log = LoggerFactory.getLogger(UserItemProcessor.class);

	@Override
    public User process(UserDetail item) throws Exception {

        log.info("processing user data.....{}", item);

        return User.builder()
                .cedula(item.getCedula())
                .nombre(item.getNombre())
                .fecha_nac(item.getFecha_nac())
                .build();
    }
}
