package kr.movements.vertical.entity.baseEntity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class BaseIdEntity {

    @Id
    @Column(nullable = false, columnDefinition = "bigint", length = 16)
    private final Long id = this.UUID();

    private String getRandom() {
        String size = "1000";

        double d = Math.random() * Double.parseDouble(size);
        String result = Integer.toString((int)d);
        int lenResult = result.length();
        if (lenResult < 3) {
            for(int i = 0; i < 3 - lenResult; ++i) {
                result = "0" + result;
            }
        }
        return result;
    }

    private long UUID() {
        String num = Long.toString(System.currentTimeMillis());
        if (num.length() <= 13) {
            num += getRandom();
        }

        return Long.parseLong(num);
    }
}
