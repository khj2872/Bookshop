package com.kobobook.www.kobobook.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class DataModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Item item;

    private Integer count = 0;

    public static DataModel createDataModel(Member member, Item item) {
        DataModel dataModel = new DataModel();
        dataModel.setMember(member);
        dataModel.setItem(item);
        dataModel.setCount(dataModel.getCount() + 1);

        return dataModel;
    }

}
