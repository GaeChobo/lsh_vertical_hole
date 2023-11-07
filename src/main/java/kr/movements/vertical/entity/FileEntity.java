package kr.movements.vertical.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import kr.movements.vertical.entity.baseEntity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "FILE")
@Entity
public class FileEntity extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String filePath;

    @Column(nullable = false)
    private Long fileSize; // 바이트

    @Column(nullable = false, length = 45)
    private String fileType;

    @Column(nullable = false, length = 20)
    private String mime;

    @Column(nullable = false, length = 300)
    private String originalName;

    @Column(nullable = false, length = 30)
    private String storedName;

    public void update(Long fileSize, String contentType, String mime, String originalName, String storedName){
        this.fileSize = fileSize;
        this.fileType = contentType;
        this.mime = mime;
        this.originalName = originalName;
        this.storedName = storedName;
    }
}
