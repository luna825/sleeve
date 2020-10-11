package com.moonyue.sleeve.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.moonyue.sleeve.common.util.EncryptUtil;
import com.moonyue.sleeve.common.util.MapAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Map;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openid;
    private String nickname;
    private Long unifyUid;
    private String email;

    @Getter(AccessLevel.NONE)
    @JsonIgnore
    private String password;
    private String mobile;

    @Convert(converter = MapAndJson.class)
    private Map<String, Object> wxProfile;

    public void setPassword(String password){
        this.password = EncryptUtil.encrypt(password);
    }

    public Boolean verifyPassword(String password){
        return EncryptUtil.verify(this.password, password);
    }

}
