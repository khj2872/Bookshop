package com.kobobook.www.kobobook.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UploadFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FILE_ID")
    private Integer id;

    private String path;

    private String originalFileName;

    private String storedFileName;

}
