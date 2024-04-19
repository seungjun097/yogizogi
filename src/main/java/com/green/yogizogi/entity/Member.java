package com.green.yogizogi.entity;

import com.green.yogizogi.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "myMemberSeq", sequenceName = "user_id_seq", allocationSize = 1)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "myMemberSeq")
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String nickname;
    private int point;
    private String phone;
    private String rating;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    private List<Address> addresses;
}
