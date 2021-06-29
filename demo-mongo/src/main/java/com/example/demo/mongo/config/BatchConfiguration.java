package com.example.demo.mongo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.example.demo.mongo.domain.User;
import com.example.demo.mongo.listener.JobCompletionNotificationListener;
import com.example.demo.mongo.model.UserDetail;
import com.example.demo.mongo.processor.UserItemProcessor;


@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    MongoTemplate mongoTemplate;


   /* @Bean
    public FlatFileItemReader<UserDetail> reader() {
        return new FlatFileItemReaderBuilder<UserDetail>().name("userItemReader")
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names(new String[] {"nombre", "cedula", "fecha_nac"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<UserDetail>() {
                    {
                        setTargetType(UserDetail.class);
                    }
                }).build();
    }*/
    
    @Bean
    public FlatFileItemReader<UserDetail> reader() {
      FlatFileItemReader<UserDetail> reader = new FlatFileItemReader<>();
      reader.setResource(new ClassPathResource("sample-data.csv"));
      reader.setLineMapper(new DefaultLineMapper<UserDetail>() {{
        setLineTokenizer(new DelimitedLineTokenizer() {{
          setNames(new String[] {"nombre", "cedula", "fecha_nac"});
   
        }});
        setFieldSetMapper(new BeanWrapperFieldSetMapper<UserDetail>() {{
          setTargetType(UserDetail.class);
        }});
      }});
      return reader;
    }
    
    
    @Bean
    public MongoItemWriter<User> writer(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<User>()
        		.template(mongoTemplate)
        		.collection("user")
                .build();
    }


    @Bean
    public UserItemProcessor processor() {
        return new UserItemProcessor();
    }


    @Bean
    public Step step1(FlatFileItemReader<UserDetail> itemReader, MongoItemWriter<User> itemWriter)  throws Exception {

        return this.stepBuilderFactory.get("step1")
        		.<UserDetail, User>chunk(1000)
        		.reader(itemReader)
                .processor(processor())
                .writer(itemWriter)
                .build();
    }

    @Bean
    public Job updateUserJob(JobCompletionNotificationListener listener, Step step1) throws Exception {

        return this.jobBuilderFactory
        		.get("updateUserJob")
        		.incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1)
                .build();
    }
}