package com.moonyue.sleeve.model;

import com.moonyue.sleeve.common.util.MapAndJson;
import lombok.*;

import javax.persistence.*;
import java.util.Map;


@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String openid;
    private String nickname;
    private Long unifyUid;
    private String email;
    private String password;
    private String mobile;

    @Convert(converter = MapAndJson.class)
    private Map<String, Object> wxProfile;

}
