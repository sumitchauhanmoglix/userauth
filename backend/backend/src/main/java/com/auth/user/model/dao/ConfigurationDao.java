package com.auth.user.model.dao;

import com.auth.user.enums.ConfigType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "configurations")
public class ConfigurationDao {
    @Id
    private String id;

    @Field(name =  "config_type")
    private ConfigType configType;

    @Field(name = "data")
    private Map<String, Object> data;
}
