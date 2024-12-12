package org.koreait.wishlist.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.koreait.member.entities.Member;
import org.koreait.wishlist.constants.WishType;

@EqualsAndHashCode // 동등성 비교 필수
@NoArgsConstructor
@AllArgsConstructor
public class WishId {
    private Long seq;
    private WishType type;
    private Member member;
}
