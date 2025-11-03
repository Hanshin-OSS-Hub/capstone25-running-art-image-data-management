package com.aetheriadm.runner.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@NoArgsConstructor
public class Runner {
    /**
     * 사용자의 고유 식별자 (Primary Key)입니다.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 카카오로부터 발급받은 사용자의 고유 식별 ID입니다.
     */
    @Column(name = "kakao_id")
    private Long kakaoId;

    /**
     * 사용자의 이름 또는 닉네임입니다.
     */
    @Column(name = "name")
    private String name;

    /**
     * 새로운 {@code Runner} 엔티티를 생성하기 위한 생성자입니다.
     *
     * <p>주로 카카오 로그인 후 신규 사용자를 등록할 때 사용됩니다.</p>
     *
     * @param kakaoId 카카오 고유 ID입니다.
     * @param name 사용자의 이름 또는 닉네임입니다.
     */
    @Builder
    public Runner(Long kakaoId, String name) {
        this.kakaoId = kakaoId;
        this.name = name;
    }
}